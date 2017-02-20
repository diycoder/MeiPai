package com.mumu.meipai.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.mumu.common.base.BaseActivity;
import com.mumu.common.utils.LogUtil;
import com.mumu.common.utils.StatusBarUtil;
import com.mumu.meipai.R;
import com.mumu.meipai.adapter.UserMediaAdapter;
import com.mumu.meipai.bean.MediaBean;
import com.mumu.meipai.bean.UserBean;
import com.mumu.meipai.contract.UserMediaContact;
import com.mumu.meipai.model.UserMediaModel;
import com.mumu.meipai.presenter.UserMediaPresenter;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;


/**
 * Created by MuMu on 2017/1/1/0001.
 */

public class UserInfoActivity extends BaseActivity<UserMediaPresenter, UserMediaModel>
        implements UserMediaContact.View, SwipeRefreshLayout.OnRefreshListener {


    private List<MediaBean> data;
    private int id;
    private int page = 1;
    private UserMediaAdapter mAdapter;
    private boolean isRefresh;
    private String TAG = getClass().getSimpleName();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private UserBean user;
    private RelativeLayout mNavigationBar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initPresenter() {
        user = (UserBean) getIntent().getSerializableExtra("user");
        data = new ArrayList<>();
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        mNavigationBar = (RelativeLayout) findViewById(R.id.rl_tool_bar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new UserMediaAdapter(mContext);
        mAdapter.setHeaderData(user);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.addItemDecoration(new SpacesItemDecoration(0, DisplayUtil.dip2px(4.0f), getResources().getColor(R.color.divider_line)));
        mRecyclerView.setAdapter(mAdapter);
        StatusBarUtil.from(this)
                //沉浸状态栏
                .setTransparentStatusbar(true)
                //白底黑字状态栏
                .setLightStatusBar(true)
                //设置toolbar,actionbar等view
                .setActionbarView(mNavigationBar)
                .process();

        if (user != null) {
            mPresenter.lodeUserVideoRequest(user.id, page);
        }
    }

    @Override
    public void returnUserMedias(List<MediaBean> mediaBeens) {
        LogUtil.logd(TAG, mediaBeens.size() + "");
        if (mediaBeens != null && mediaBeens.size() > 0) {
            if (isRefresh) {
                data.clear();
                isRefresh = false;
            }
            data.addAll(mediaBeens);
            mAdapter.bindData(data);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }


    @Override
    public void showErrorTip(String msg) {
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        mSwipeRefreshLayout.setRefreshing(true);
    }
}
