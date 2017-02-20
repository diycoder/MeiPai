package com.mumu.common.utils;

import android.util.Log;

import com.mumu.common.base.AppConfig;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by MuMu on 2016/9/30.
 */

public class LogUtil {

    /**
     * 在application调用初始化
     */
    private static boolean TAG = true;

    public static void logInit() {
        if (true) {
            Logger.init(AppConfig.DEBUG_TAG)        // default PRETTYLOGGER or use just init()
                    .methodCount(2)                 // default 2
                    .logLevel(LogLevel.FULL)        // default LogLevel.FULL
                    .methodOffset(0);               // default 0
        } else {
            Logger.init()                           // default PRETTYLOGGER or use just init()
                    .methodCount(3)                 // default 2
                    .hideThreadInfo()               // default shown
                    .logLevel(LogLevel.NONE)        // default LogLevel.FULL
                    .methodOffset(2);
        }
    }

    public static void logd(String tag, String message) {
        if (TAG) {
//            Logger.d(tag,message);
            Log.e(tag, message);
        }
    }

    public static void logd(String message) {
        if (TAG) {
            Logger.d(message);
        }
    }

    public static void loge(Throwable throwable, String message, Object... args) {
        if (TAG) {
            Logger.e(throwable, message, args);
        }
    }

    public static void loge(String message, Object... args) {
        if (TAG) {
            Logger.e(message, args);
        }
    }

    public static void logi(String message, Object... args) {
        if (TAG) {
            Logger.i(message, args);
        }
    }

    public static void logv(String message, Object... args) {
        if (TAG) {
            Logger.v(message, args);
        }
    }

    public static void logw(String message, Object... args) {
        if (TAG) {
            Logger.v(message, args);
        }
    }

    public static void logwtf(String message, Object... args) {
        if (TAG) {
            Logger.wtf(message, args);
        }
    }

    public static void logjson(String message) {
        if (TAG) {
            Logger.json(message);
        }
    }

    public static void logxml(String message) {
        if (TAG) {
            Logger.xml(message);
        }
    }
}
