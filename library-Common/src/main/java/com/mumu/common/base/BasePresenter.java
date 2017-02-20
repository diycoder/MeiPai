package com.mumu.common.base;

/**
 * Created by MuMu on 2016/12/18/0018.
 */

import android.content.Context;

import com.mumu.common.baserx.RxManager;

public abstract class BasePresenter<T, E> {
    public E mModel;
    public static Context mContext;
    public T mView;
    public RxManager mRxManage = new RxManager();

    public void setVM(T v, E m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public void onStart() {
    }

    public void onDestroy() {
        mRxManage.clear();
    }
}
