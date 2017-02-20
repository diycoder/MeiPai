package com.mumu.common.utils;

import android.content.Context;
import android.os.Looper;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by MuMu on 2016/12/17/0017.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // CrashHandler实例
    private static CrashHandler INSTANCE;
    // 程序的Context对象
    private Context mContext;

    //保证只有一个CrashHandler实例
    private CrashHandler() {

    }

    //获取CrashHandler实例 ,单例模式
    public static CrashHandler getInstance() {
        if (INSTANCE == null)
            INSTANCE = new CrashHandler();
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该重写的方法来处理
     */
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果自定义的没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex 异常信息
     * @return true 如果处理了该异常信息;否则返回false.
     */
    public boolean handleException(final Throwable ex) {
        if (ex == null || mContext == null)
            return false;
        new Thread() {
            public void run() {
                Looper.prepare();
                MobclickAgent.reportError(mContext, ex);
                Looper.loop();
            }
        }.start();
        return true;
    }

}
