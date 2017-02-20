package com.mumu.common.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by MuMu on 2016/12/29/0029.
 */

public class WaterFullItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public WaterFullItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        outRect.left = space;//左间距
        outRect.bottom = space;//下间距
        outRect.right = space;//右间距
    }

}
