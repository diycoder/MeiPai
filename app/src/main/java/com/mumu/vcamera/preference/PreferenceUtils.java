package com.mumu.vcamera.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import com.mumu.meipai.app.MeiPaiApplication;

public final class PreferenceUtils {

	/** 清空数据 */
	public static void reset(final Context ctx) {
		Editor edit = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
		edit.clear();
		edit.commit();
	}

	private SharedPreferences mPreference;

	public PreferenceUtils() {
		this(MeiPaiApplication.getContext(), PreferenceKeys.PERFERENCE);
	}

	public PreferenceUtils(final Context context, String sharedPreferencesName) {
		this.mPreference = context.getApplicationContext().getSharedPreferences(PreferenceKeys.PERFERENCE, Context.MODE_PRIVATE);
	}

	public String get(String key, String defValue) {
		return mPreference.getString(key, defValue);
	}

	public boolean get(String key, boolean defValue) {
		return mPreference.getBoolean(key, defValue);
	}

	public int get(String key, int defValue) {
		return mPreference.getInt(key, defValue);
	}

	public float get(String key, float defValue) {
		return mPreference.getFloat(key, defValue);
	}

	public static String getString(String key, String defValue) {
		if (MeiPaiApplication.getContext() != null) {
			return PreferenceManager.getDefaultSharedPreferences(MeiPaiApplication.getContext()).getString(key, defValue);
		}
		return defValue;
	}

	public static long getLong(String key, long defValue) {
		if (MeiPaiApplication.getContext() != null) {
			return PreferenceManager.getDefaultSharedPreferences(MeiPaiApplication.getContext()).getLong(key, defValue);
		}
		return defValue;
	}

	public static float getFloat(String key, float defValue) {
		if (MeiPaiApplication.getContext() != null) {
			return PreferenceManager.getDefaultSharedPreferences(MeiPaiApplication.getContext()).getFloat(key, defValue);
		}
		return defValue;
	}

	public static void put(String key, String value) {
		putString(key, value);
	}

	public static void put(String key, int value) {
		putInt(key, value);
	}

	public static void put(String key, float value) {
		putFloat(key, value);
	}

	public static void put(String key, boolean value) {
		putBoolean(key, value);
	}

	public static void putFloat(String key, float value) {
		if (MeiPaiApplication.getContext() != null) {
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MeiPaiApplication.getContext());
			Editor editor = sharedPreferences.edit();
			editor.putFloat(key, value);
			editor.commit();
		}
	}

	public static SharedPreferences getPreferences() {
		return PreferenceManager.getDefaultSharedPreferences(MeiPaiApplication.getContext());
	}

	public static int getInt(String key, int defValue) {
		if (MeiPaiApplication.getContext() != null) {
			return PreferenceManager.getDefaultSharedPreferences(MeiPaiApplication.getContext()).getInt(key, defValue);
		}
		return defValue;
	}

	public static boolean getBoolean(String key, boolean defValue) {
		if (MeiPaiApplication.getContext() != null) {
			return PreferenceManager.getDefaultSharedPreferences(MeiPaiApplication.getContext()).getBoolean(key, defValue);
		}
		return defValue;
	}

	public static void putStringProcess(String key, String value) {
		if (MeiPaiApplication.getContext() != null) {
			SharedPreferences sharedPreferences = MeiPaiApplication.getContext().getSharedPreferences("preference_mu", Context.MODE_MULTI_PROCESS);
			Editor editor = sharedPreferences.edit();
			editor.putString(key, value);
			editor.commit();
		}
	}

	public static void putIntProcess(String key, int value) {
		if (MeiPaiApplication.getContext() != null) {
			SharedPreferences sharedPreferences = MeiPaiApplication.getContext().getSharedPreferences("preference_mu", Context.MODE_MULTI_PROCESS);
			Editor editor = sharedPreferences.edit();
			editor.putInt(key, value);
			editor.commit();
		}
	}

	public static int getIntProcess(String key, int defValue) {
		if (MeiPaiApplication.getContext() != null) {
			SharedPreferences sharedPreferences = MeiPaiApplication.getContext().getSharedPreferences("preference_mu", Context.MODE_MULTI_PROCESS);
			return sharedPreferences.getInt(key, defValue);
		}
		return defValue;
	}
	
	public static void putLongProcess(String key, long value) {
		if (MeiPaiApplication.getContext() != null) {
			SharedPreferences sharedPreferences = MeiPaiApplication.getContext().getSharedPreferences("preference_mu", Context.MODE_MULTI_PROCESS);
			Editor editor = sharedPreferences.edit();
			editor.putLong(key, value);
			editor.commit();
		}
	}
	
	public static long getLongProcess(String key, long defValue) {
		if (MeiPaiApplication.getContext() != null) {
			SharedPreferences sharedPreferences = MeiPaiApplication.getContext().getSharedPreferences("preference_mu", Context.MODE_MULTI_PROCESS);
			return sharedPreferences.getLong(key, defValue);
		}
		return defValue;
	}

	public static String getStringProcess(String key, String defValue) {
		if (MeiPaiApplication.getContext() != null) {
			SharedPreferences sharedPreferences = MeiPaiApplication.getContext().getSharedPreferences("preference_mu", Context.MODE_MULTI_PROCESS);
			return sharedPreferences.getString(key, defValue);
		}
		return defValue;
	}

	public static boolean hasString(String key) {
		if (MeiPaiApplication.getContext() != null) {
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MeiPaiApplication.getContext());
			return sharedPreferences.contains(key);
		}
		return false;
	}

	public static void putString(String key, String value) {
		if (MeiPaiApplication.getContext() != null) {
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MeiPaiApplication.getContext());
			Editor editor = sharedPreferences.edit();
			editor.putString(key, value);
			editor.commit();
		}
	}

	public static void putLong(String key, long value) {
		if (MeiPaiApplication.getContext() != null) {
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MeiPaiApplication.getContext());
			Editor editor = sharedPreferences.edit();
			editor.putLong(key, value);
			editor.commit();
		}
	}

	public static void putBoolean(String key, boolean value) {
		if (MeiPaiApplication.getContext() != null) {
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MeiPaiApplication.getContext());
			Editor editor = sharedPreferences.edit();
			editor.putBoolean(key, value);
			editor.commit();
		}
	}

	public static void putInt(String key, int value) {
		if (MeiPaiApplication.getContext() != null) {
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MeiPaiApplication.getContext());
			Editor editor = sharedPreferences.edit();
			editor.putInt(key, value);
			editor.commit();
		}
	}

	public static void remove(String... keys) {
		if (keys != null && MeiPaiApplication.getContext() != null) {
			
			SharedPreferences sharedPreferences = MeiPaiApplication.getContext().getSharedPreferences("preference_mu", Context.MODE_MULTI_PROCESS);
			Editor editor = sharedPreferences.edit();
			for (String key : keys) {
				editor.remove(key);
			}
			editor.commit();
		}
	}
}
