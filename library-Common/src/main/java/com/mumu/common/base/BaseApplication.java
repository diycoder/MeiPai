package com.mumu.common.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.multidex.MultiDex;

import java.util.LinkedList;
import java.util.List;

/**
 * APPLICATION
 */
public class BaseApplication extends Application {

    private static BaseApplication baseApplication;
    // 运用list来保存们每一个activity是关键
    private List<Activity> mList = new LinkedList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
    }

    public void addActivity(Activity activity) {
        mList.add(activity);
    }


    public synchronized static BaseApplication getInstance() {
        if (null == baseApplication) {
            baseApplication = new BaseApplication();
        }
        return baseApplication;
    }

    public static Context getContext() {
        return baseApplication;
    }

    public static Context getAppContext() {
        return baseApplication;
    }

    public static Resources getAppResources() {
        return baseApplication.getResources();
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 分包
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
