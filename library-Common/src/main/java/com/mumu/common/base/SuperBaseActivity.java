package com.mumu.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

/**
 * Created by MuMu on 2016/12/17/0017.
 */

public abstract class SuperBaseActivity extends FragmentActivity {
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initHeaderView();
    }

    private void initHeaderView() {
//        View.inflate(mContext, R.lay)
    }
}
