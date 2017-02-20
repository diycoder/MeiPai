package com.mumu.meipai.app;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mumu.common.base.BaseApplication;
import com.mumu.common.utils.CrashHandler;
import com.mumu.common.utils.DeviceUtil;
import com.mumu.common.utils.LogUtil;
import com.mumu.vcamera.utils.VCameraUtils;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by MuMu on 2016/9/6.
 */
public class MeiPaiApplication extends BaseApplication {

    @Override
    public void onCreate() {
        LeakCanary.install(this);
        super.onCreate();

        //初始化Fresco
        Fresco.initialize(this);
        //初始化VCamera
        VCameraUtils.initVitamioRecoder(this);
        //初始化Log
        LogUtil.logInit();
        //初始化LeakCanary
        //初始化Android设备信息
        DeviceUtil.init(this);
        //初始化CrashHandler
        CrashHandler.getInstance().init(this);
    }
}
