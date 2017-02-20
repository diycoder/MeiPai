package com.mumu.common.baserx;

import android.content.Context;

import com.mumu.common.R;
import com.mumu.common.base.BaseApplication;
import com.mumu.common.utils.NetWorkUtil;

import rx.Subscriber;

/**
 * 订阅封装
 */

/********************使用例子********************/

public abstract class RxSubscriber<T> extends Subscriber<T> {

    private Context mContext;
    private String msg;
    private boolean showDialog=true;

    /**
     * 是否显示浮动dialog
     */
    public void showDialog() {
        this.showDialog= true;
    }
    public void hideDialog() {
        this.showDialog= true;
    }

    public RxSubscriber(Context context, String msg) {
        this.mContext = context;
        this.msg = msg;

    }
    public RxSubscriber() {
    }

    @Override
    public void onCompleted() {

    }
    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onNext(T t) {
        _onNext(t);
    }
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        //网络
        if (!NetWorkUtil.isNetConnected(BaseApplication.getAppContext())) {
            _onError(BaseApplication.getAppContext().getString(R.string.no_net));
        }
        //服务器
        else if (e instanceof ServerException) {
            _onError(e.getMessage());
        }
        //其它
        else {
            _onError(BaseApplication.getAppContext().getString(R.string.net_error));
        }
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);

}
