package com.mumu.meipai.api;

import android.text.TextUtils;

import com.mumu.common.utils.DeviceUtil;

import java.util.HashMap;

/**
 * Created by MuMu on 2016/12/27/0027.
 */

public class ParamsMap extends HashMap<String, String> {
    public ParamsMap() {
        put(AppConstant.ParamKey.CLIENT_ID_KEY, AppConstant.ParamDefaultValue.CLIENT_ID);
        put(AppConstant.ParamKey.CLIENT_SECRET_KEY, AppConstant.ParamDefaultValue.CLIENT_SECRET);
        put(AppConstant.ParamKey.DEVICE_ID_KEY, DeviceUtil.getDeviceId());
        put(AppConstant.ParamKey.LANGUAGE_KEY, AppConstant.ParamDefaultValue.LANGUAGE);
        put(AppConstant.ParamKey.MODEL_KEY, DeviceUtil.getModel());
    }

    public void put(String key, int value) {
        super.put(key, value + "");
    }

    @Override
    public String put(String key, String value) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value))
            return  "";
        return super.put(key, value);
    }
}

