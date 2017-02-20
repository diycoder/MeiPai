package com.mumu.meipai.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import com.mumu.common.utils.NetWorkUtil;
import com.mumu.common.utils.ToastUitl;


/**
 * Created by MuMu on 2016/9/6.
 */
public class MainReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {//网络发生变化
            if (NetWorkUtil.isWifiConnected(context)) {
                ToastUitl.showShort("WIFI网络已连接");
            } else if (NetWorkUtil.is3gConnected(context)) {
                ToastUitl.showShort("手机网络已连接");
            } else if (NetWorkUtil.isNetConnected(context)) {
                ToastUitl.showShort("哎呀，没有网络了");
            }
        }
    }

}
