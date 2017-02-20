package com.mumu.meipai.api;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mumu.common.base.BaseApplication;
import com.mumu.common.utils.DeviceUtil;
import com.mumu.common.utils.NetWorkUtil;
import com.mumu.common.utils.TimeUtil;
import com.mumu.meipai.parse.IntegerDefault0Adapter;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MuMu on 2016/12/24/0024.
 */

public class MeiPaiAPI {

    //读超时长，单位：毫秒
    public static final long READ_TIME_OUT = TimeUtil.ONE_MIN_MILLISECONDS;
    //连接时长，单位：毫秒
    public static final long CONNECT_TIME_OUT = TimeUtil.ONE_MIN_MILLISECONDS;

    public Retrofit retrofit;

    public MeiPaiService meiPaiService;

    private static SparseArray<MeiPaiAPI> sRetrofitManager = new SparseArray<>(HostType.TYPE_COUNT);

    /*************************
     * 缓存设置
     *********************/
    /*

    1. noCache 不使用缓存，全部走网络

    2. noStore 不使用缓存，也不存储缓存

    3. onlyIfCached 只使用缓存

    4. maxAge 设置最大失效时间，失效则不使用 需要服务器配合

    5. maxStale 设置最大失效时间，失效则不使用 需要服务器配合 感觉这两个类似 还没怎么弄清楚，清楚的同学欢迎留言

    6. minFresh 设置有效时间，依旧如上

    7. FORCE_NETWORK 只走网络

    8. FORCE_CACHE 只走缓存

    */

    //设缓存有效期为一天
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;

    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
     */
//    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    private static final String CACHE_CONTROL_CACHE = "maxStale, max-stale=" + CACHE_STALE_SEC;
    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private static final String CACHE_CONTROL_AGE = "max-age=0";


    /**
     * 根据网络状况获取缓存的策略
     */
    @NonNull
    public static String getCacheControl() {
        return true ? CACHE_CONTROL_AGE : CACHE_CONTROL_CACHE;
    }

    private MeiPaiAPI(int hostType) {
        //开启Log
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //缓存
        File cacheFile = new File(BaseApplication.getAppContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        //增加头部信息
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request oldRequest = chain.request();

                // 添加新的参数
                HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                        .newBuilder()
                        .scheme(oldRequest.url().scheme())
                        .host(oldRequest.url().host())
                        .addQueryParameter(AppConstant.ParamKey.LANGUAGE_KEY, AppConstant.ParamDefaultValue.LANGUAGE)
                        .addQueryParameter(AppConstant.ParamKey.CLIENT_ID_KEY, AppConstant.ParamDefaultValue.CLIENT_ID)
                        .addQueryParameter(AppConstant.ParamKey.DEVICE_ID_KEY, DeviceUtil.getDeviceId());


                // 新的请求
                Request newRequest = oldRequest.newBuilder()
                        .method(oldRequest.method(), oldRequest.body())
                        .url(authorizedUrlBuilder.build())
                        .addHeader("Content-Type", "application/json")
                        .build();


                return chain.proceed(newRequest);
            }
        };

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)//设置读取超时时间
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)//设置连接超时时间
                .addInterceptor(mRewriteCacheControlInterceptor)//设置缓存拦截器
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)//设置网络拦截器
                .addInterceptor(headerInterceptor)//设置请求头拦截器
                .addInterceptor(logInterceptor)//设置日志拦截器
                .cache(cache)//设置缓存
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(buildGson()))//添加解析器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加调度器
                .baseUrl(AppConstant.BASE_URL)//设置基本路径 注意：必须以“/”结束
                .build();
        meiPaiService = retrofit.create(MeiPaiService.class);
    }

    public Gson buildGson() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                .create();
        return gson;
    }


    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtil.isNetConnected(BaseApplication.getAppContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetWorkUtil.isNetConnected(BaseApplication.getAppContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };


    public static MeiPaiService getDefault(int hostType) {
        MeiPaiAPI retrofitManager = sRetrofitManager.get(hostType);
        if (retrofitManager == null) {
            retrofitManager = new MeiPaiAPI(hostType);
            sRetrofitManager.put(hostType, retrofitManager);
        }
        return retrofitManager.meiPaiService;
    }
}
