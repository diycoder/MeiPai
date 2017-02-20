package com.mumu.vcamera.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.mumu.vcamera.service.AssertService;
import com.yixia.camera.VCamera;
import com.yixia.camera.util.DeviceUtils;

import java.io.File;

/**
 * Created by MuMu on 2016/9/30.
 */

public class VCameraUtils {

    public static void initVitamioRecoder(Context context) {
        // 设置拍摄视频缓存路径
        File videoCache = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (DeviceUtils.isZte()) {
            if (videoCache.exists()) {
                VCamera.setVideoCachePath(videoCache + VCameraConstant.VIDEO_CACHE);
            } else {
                VCamera.setVideoCachePath(videoCache.getPath().replace("/sdcard/", "/sdcard-ext/") + VCameraConstant.VIDEO_CACHE);
            }
        } else {
            VCamera.setVideoCachePath(videoCache + VCameraConstant.VIDEO_CACHE);
        }
        // 开启log输出,ffmpeg输出到logcat
        VCamera.setDebugMode(true);
        // 初始化拍摄SDK，必须
        VCamera.initialize(context);

        // 解压assert里面的文件
        context.startService(new Intent(context, AssertService.class));
    }
}
