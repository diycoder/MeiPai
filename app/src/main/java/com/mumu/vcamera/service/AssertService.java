package com.mumu.vcamera.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import com.yixia.camera.util.DeviceUtils;
import java.io.File;
import com.mumu.vcamera.log.Logger;
import com.mumu.vcamera.ui.record.helper.ThemeHelper;

/**
 * 专门用来处理解压资源
 * 
 * @author tangjun
 *
 */
public class AssertService extends Service implements Runnable {

	/** 是否正在运行 */
	private static boolean mIsRunning;

	@Override
	public void onCreate() {
		super.onCreate();
		mIsRunning = true;
		new Thread(this).start();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void run() {
		try {
			File mThemeCacheDir;
			// 获取传入参数
			if (Environment.MEDIA_MOUNTED.equals(Environment
					.getExternalStorageState())
					&& !isExternalStorageRemovable())
				mThemeCacheDir = new File(getExternalCacheDir(), "Theme");
			else
				mThemeCacheDir = new File(getCacheDir(), "Theme");
			ThemeHelper.prepareTheme(getApplication(), mThemeCacheDir);
		} catch (OutOfMemoryError e) {
			Logger.e(e);
		} catch (Exception e) {
			Logger.e(e);
		}
		mIsRunning = false;
		stopSelf();
	}

	public static boolean isRunning() {
		return mIsRunning;
	}

	public static boolean isExternalStorageRemovable() {
		if (DeviceUtils.hasGingerbread())
			return Environment.isExternalStorageRemovable();
		else
			return Environment.MEDIA_REMOVED.equals(Environment
					.getExternalStorageState());
	}
}
