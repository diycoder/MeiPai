package com.mumu.common.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mumu.common.baserx.RxManager;
import com.mumu.common.utils.ToastUitl;
import com.mumu.common.utils.TypeUtil;


public abstract class BaseFragment<T extends BasePresenter, E extends BaseModel> extends Fragment {
    protected View rootView;
    public T mPresenter;
    public E mModel;
    public RxManager mRxManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutResource(), container, false);
        mRxManager = new RxManager();
        mPresenter = TypeUtil.getType(this, 0);
        mModel = TypeUtil.getType(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this.getActivity();
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPresenter();
        initView();
    }

    //获取布局文件
    protected abstract int getLayoutResource();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //初始化view
    protected abstract void initView();


    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void showShortToast(String text) {
        ToastUitl.showShort((CharSequence) text);
    }

    public void showShortToast(int resId) {
        ToastUitl.showShort(resId);
    }

    public void showLongToast(int resId) {
        ToastUitl.showLong(resId);
    }

    public void showLongToast(String text) {
        ToastUitl.showLong((CharSequence) text);
    }

    public void showToastWithImg(String text, int res) {
        ToastUitl.showToastWithImg(text, res);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null)
            mPresenter.onDestroy();
        mRxManager.clear();
    }
}