package com.mumu.common.utils;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

/**
 * Created by MuMu on 2016/12/27/0027.
 */

public class DeviceUtil {

    private static Context context;
    private static TelephonyManager telephonyManager;

    /**
     * 初始化
     *
     * @param _context
     */
    public static void init(Context _context) {
        context = _context;
        telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
    }


    /**
     * 需要权限<uses-permission android:name="android.permission.READ_PHONE_STATE" />
     *
     * @return
     */
    public static String getDeviceId() {
        String deviceId = telephonyManager.getDeviceId();
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = String.valueOf(System
                    .currentTimeMillis());
        }
        return deviceId;
    }


    public static String getModel() {
        return android.os.Build.MODEL;
    }

    public static int getScreenWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }


}
