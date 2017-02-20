package com.mumu.meipai.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;

import com.mumu.common.base.BaseActivity;
import com.mumu.common.utils.DisplayUtil;
import com.mumu.common.utils.LogUtil;
import com.mumu.common.widget.NormalTitleBar;
import com.mumu.common.widget.WaterFullItemDecoration;
import com.mumu.meipai.R;
import com.mumu.meipai.adapter.MediaAdapter;
import com.mumu.meipai.bean.MediaBean;
import com.mumu.meipai.bean.MediaEntity;
import com.mumu.meipai.bean.TopicBean;
import com.mumu.meipai.contract.TopicContract;
import com.mumu.meipai.model.TopicModel;
import com.mumu.meipai.presenter.TopicPresenter;

import java.util.ArrayList;
import java.util.List;

import static com.mumu.common.base.BaseApplication.getContext;

/**
 * Created by MuMu on 2017/01/07.
 */

public class TopicActivity extends BaseActivity<TopicPresenter, TopicModel> implements TopicContract.View, SwipeRefreshLayout.OnRefreshListener, MediaAdapter.CallBackListener {

    private int page = 1;
    private int id;
    private String feature;
    private int type;
    private RecyclerView mRecyclerView;
    private List<MediaEntity> dataList;
    private boolean isRefresh;
    private StaggeredGridLayoutManager mLayoutManager;
    private String TAG = getClass().getSimpleName();
    private String mTopic = "搞笑";
    private WaterFullItemDecoration waterFullItemDecoration;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean isLoading;
    private TopicBean topicBean;
    private MediaAdapter mAdapter;
    private NormalTitleBar mNormalTitleBar;
    private Resources mResources;


    @Override
    public int getLayoutId() {
        return R.layout.activity_topic;
    }

    @Override
    public void initPresenter() {
        mTopic = getIntent().getStringExtra("topic");
        dataList = new ArrayList<>();
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        mResources = getResources();
        mNormalTitleBar = (NormalTitleBar) findViewById(R.id.titleBar);
        mNormalTitleBar.setLeftTitle(mResources.getString(R.string.back));
        mNormalTitleBar.setTitleText("#" + mTopic + "#");
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
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
        if (!TextUtils.isEmpty(mTopic)) {
            mPresenter.lodeTopicInfoRequest(mTopic);
        }
    }

    @Override
    public void returnTopicVideos(List<MediaEntity> entityList) {
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
    public void returnTopicInfo(TopicBean topicBean) {
        if (topicBean != null) {
            this.topicBean = topicBean;
            mPresenter.lodeTopicListRequest(topicBean.id, topicBean.type, topicBean.show_feature, page);
        }
    }

    @Override
    public void showErrorTip(String msg) {
        LogUtil.loge(TAG, msg);
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        mPresenter.lodeTopicListRequest(topicBean.id, topicBean.type, topicBean.show_feature, page);
    }

    @Override
    public void loadMore() {
        if (!isLoading) {
            ++page;
            isLoading = true;
            mPresenter.lodeTopicListRequest(topicBean.id, topicBean.type, topicBean.show_feature, page);
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
