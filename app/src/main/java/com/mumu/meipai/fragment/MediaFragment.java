package com.mumu.meipai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.mumu.common.base.BaseFragment;
import com.mumu.common.utils.DisplayUtil;
import com.mumu.common.widget.WaterFullItemDecoration;
import com.mumu.meipai.R;
import com.mumu.meipai.activity.MediaPlayerActivity;
import com.mumu.meipai.adapter.MediaAdapter;
import com.mumu.meipai.api.AppConstant;
import com.mumu.meipai.bean.MediaBean;
import com.mumu.meipai.bean.MediaEntity;
import com.mumu.meipai.contract.MediaContract;
import com.mumu.meipai.model.MediaModel;
import com.mumu.meipai.presenter.MediaPresenter;

import java.util.ArrayList;
import java.util.List;


public class MediaFragment extends BaseFragment<MediaPresenter, MediaModel> implements MediaContract.View, SwipeRefreshLayout.OnRefreshListener, MediaAdapter.CallBackListener {

    private String TAG = getClass().getSimpleName();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private MediaAdapter mAdapter;
    private StaggeredGridLayoutManager mLayoutManager;
    private WaterFullItemDecoration waterFullItemDecoration;

    private int mPage = 1;
    private List<MediaEntity> dataList = new ArrayList<>();
    private boolean isRefresh;
    private boolean isLoading;
    private int id;
    private int type;

    public static MediaFragment newInstance(int id, int type) {
        MediaFragment mediaFragment = new MediaFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putInt("type", type);
        mediaFragment.setArguments(bundle);
        return mediaFragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_media;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        View inflateView = getView();
        mSwipeRefreshLayout = (SwipeRefreshLayout) inflateView.findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) inflateView.findViewById(R.id.recyclerView);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new MediaAdapter(getContext());
        mAdapter.setLoadMoreListener(this);
        if (waterFullItemDecoration == null)
            waterFullItemDecoration = new WaterFullItemDecoration(DisplayUtil.dip2px(1));
        mRecyclerView.removeItemDecoration(waterFullItemDecoration);//移除分割线
        mRecyclerView.addItemDecoration(waterFullItemDecoration);//添加分割线
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");
            type = bundle.getInt("type");
            Log.e(TAG, "id: " + id + " type: " + type);
            mPresenter.loadVideoListRequest(id, type, mPage, AppConstant.PAGE_COUNT);
        }
    }

    @Override
    public void returnVideoList(List<MediaEntity> entityList) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (entityList != null && entityList.size() > 0) {
            if (isRefresh) {
                dataList.clear();
                isRefresh = false;
            }
            isLoading = false;
            if (!dataList.containsAll(entityList))
                dataList.addAll(entityList);
            mAdapter.bindData(dataList);
        }
    }

    @Override
    public void showErrorTip(String msg) {
        mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onRefresh() {
        isRefresh = true;
        mPage = 1;
        mPresenter.loadVideoListRequest(id, type, mPage, AppConstant.PAGE_COUNT);
    }

    @Override
    public void loadMore() {
        if (!isLoading) {
            ++mPage;
            isLoading = true;
            mPresenter.loadVideoListRequest(id, type, mPage, AppConstant.PAGE_COUNT);
        }
    }

    @Override
    public void onItemClick(int position) {
        MediaEntity mediaEntity = dataList.get(position);
        MediaBean mediaBean = mediaEntity.media;
        if (mediaBean != null) {
            Intent intent = new Intent(getContext(), MediaPlayerActivity.class);
            intent.putExtra("mediaInfo",mediaBean);
            startActivity(intent);
        }
    }
}