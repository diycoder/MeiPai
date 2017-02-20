package com.mumu.meipai.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mumu.common.base.BaseActivity;
import com.mumu.common.utils.DisplayUtil;
import com.mumu.common.utils.LogUtil;
import com.mumu.common.utils.TimeUtil;
import com.mumu.common.widget.LayoutItemDecoration;
import com.mumu.common.widget.NormalTitleBar;
import com.mumu.meipai.R;
import com.mumu.meipai.adapter.CommentAdapter;
import com.mumu.meipai.adapter.SuggestMediaAdapter;
import com.mumu.meipai.bean.CommentEntity;
import com.mumu.meipai.bean.MediaBean;
import com.mumu.meipai.bean.UserBean;
import com.mumu.meipai.contract.MediaComment;
import com.mumu.meipai.model.MediaCommentModel;
import com.mumu.meipai.presenter.MediaCommentPresenter;
import com.mumu.meipai.util.SpanUtil;
import java.util.ArrayList;
import java.util.List;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


/**
 * Created by MuMu on 2016/12/26/0026.
 */

public class MediaPlayerActivity extends BaseActivity<MediaCommentPresenter, MediaCommentModel>
        implements MediaComment.View, SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener, CommentAdapter.CallBack, View.OnClickListener {

    private String TAG = getClass().getSimpleName();

    private NormalTitleBar mNormalTitleBar;
    private JCVideoPlayerStandard mJcVideoPlayerStandard;
    private View mHeaderView;
    private TextView tv_nick;
    private ImageView iv_verified;
    private SimpleDraweeView headerIcon;
    private TextView tv_createTime;
    private TextView tv_describe;
    private TextView tv_viewCount;
    private TextView tv_like_count;
    private TextView tv_comment_count;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;

    private MediaBean mediaBean;
    private List<CommentEntity> dataList;
    private List<MediaBean> suggestData;
    private boolean isRefresh;
    private CommentAdapter mAdapter;
    private Resources mResources;
    private boolean isLoading;
    private int mPage = 1;
    private RecyclerView header_recyclerView;
    private SuggestMediaAdapter mSuggestMediaAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_media_player;
    }

    @Override
    public void initPresenter() {
        mediaBean = ((MediaBean) getIntent().getSerializableExtra("mediaInfo"));
        dataList = new ArrayList<>();
        suggestData = new ArrayList<>();
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        initHeaderView();
        mResources = getResources();
        mNormalTitleBar = (NormalTitleBar) findViewById(R.id.titleBar);
        mNormalTitleBar.setLeftTitle(mResources.getString(R.string.back));
        mNormalTitleBar.setTitleText(mResources.getString(R.string.meipai_detail));
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new CommentAdapter(mContext, dataList);
        mAdapter.setCallBack(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(this);
        mListView.addHeaderView(mHeaderView);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        if (mediaBean != null) {
            mPresenter.lodeVideoCommentsRequest(mediaBean.id, mPage);
            mPresenter.lodeSuggestVideoRequest(mediaBean.id);
        }
    }

    private void initHeaderView() {
        mHeaderView = View.inflate(mContext, R.layout.header_video_player, null);
        tv_nick = (TextView) mHeaderView.findViewById(R.id.tv_nick);
        iv_verified = (ImageView) mHeaderView.findViewById(R.id.iv_verified);
        header_recyclerView = (RecyclerView) mHeaderView.findViewById(R.id.header_recyclerView);
        headerIcon = (SimpleDraweeView) mHeaderView.findViewById(R.id.headerView);
        tv_createTime = (TextView) mHeaderView.findViewById(R.id.tv_createTime);
        tv_describe = (TextView) mHeaderView.findViewById(R.id.tv_describe);
        tv_viewCount = (TextView) mHeaderView.findViewById(R.id.tv_viewCount);
        tv_like_count = (TextView) mHeaderView.findViewById(R.id.tv_like_count);
        tv_comment_count = (TextView) mHeaderView.findViewById(R.id.tv_comment_count);
        mJcVideoPlayerStandard = (JCVideoPlayerStandard) mHeaderView.findViewById(R.id.jc_video_player);
        LinearLayoutManager mManager = new LinearLayoutManager(mContext);
        mManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        header_recyclerView.setLayoutManager(mManager);
        mSuggestMediaAdapter = new SuggestMediaAdapter(mContext);
        LayoutItemDecoration itemDecoration = new LayoutItemDecoration(DisplayUtil.dip2px(4));
        header_recyclerView.addItemDecoration(itemDecoration);
        header_recyclerView.setAdapter(mSuggestMediaAdapter);
        headerIcon.setOnClickListener(this);

        bindHeaderData();
    }

    private void bindHeaderData() {
        if (mediaBean != null) {
            tv_viewCount.setText(String.valueOf(mediaBean.likes_count + mediaBean.comments_count) + "播放");
            tv_createTime.setText(TimeUtil.formatData(TimeUtil.dateFormatMDHM, mediaBean.time));
            tv_like_count.setText(String.valueOf(mediaBean.likes_count));
            tv_comment_count.setText(String.valueOf(mediaBean.comments_count));
            if (!TextUtils.isEmpty(mediaBean.caption)) {
                Intent intent = new Intent(mContext, TopicActivity.class);
                tv_describe.setText(SpanUtil.getTopicSpan(getResources().getColor(R.color.color855bff), mediaBean.caption, true, mContext, intent));
                //如果想实现点击，必须要设置这个
                tv_describe.setMovementMethod(LinkMovementMethod.getInstance());
            } else {
                tv_describe.setVisibility(View.GONE);
            }
            UserBean userBean = mediaBean.user;
            if (userBean != null) {
                tv_nick.setText(userBean.screen_name);
                headerIcon.setImageURI(userBean.avatar);
                iv_verified.setVisibility(userBean.verified ? View.VISIBLE : View.INVISIBLE);
            }
            mJcVideoPlayerStandard.setUp(mediaBean.video, JCVideoPlayerStandard.SCROLLBAR_POSITION_DEFAULT, "");
            mJcVideoPlayerStandard.startButton.performClick();//模拟点击效果
        }
    }

    @Override
    public void returnComments(List<CommentEntity> commentEntities) {
        mSwipeRefreshLayout.setRefreshing(isRefresh);
        if (commentEntities != null && commentEntities.size() > 0) {
            if (isRefresh) {
                dataList.clear();
                isRefresh = false;
            }
            isLoading = false;
            dataList.addAll(commentEntities);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void returnSuggestVideo(List<MediaBean> mediaBeanList) {
        if (mediaBeanList != null && mediaBeanList.size() > 0) {
            suggestData.addAll(mediaBeanList);
            mSuggestMediaAdapter.bindData(suggestData);
        }
    }


    @Override
    public void showErrorTip(String msg) {
        setRefresh(false, 100);
        LogUtil.loge(TAG, msg);
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
    public void onRefresh() {
        isRefresh = true;
        if (!isLoading) {
            isLoading = true;
            mPage = 1;
            if (mediaBean != null) {
                mPresenter.lodeVideoCommentsRequest(mediaBean.id, mPage);
            }
        }
    }

    public void setRefresh(final boolean isRefresh, long delay) {
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(isRefresh);
            }
        }, delay);
    }

    @Override
    public void onScrollStateChanged(AbsListView mListView, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView mListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        JCVideoPlayer.onScroll();
    }

    /**
     * 获取ListView滚动高度
     *
     * @param mListView
     * @return
     */
    public int getScrollY(AbsListView mListView) {
        View c = mListView.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = mListView.getFirstVisiblePosition();
        int top = c.getTop();
        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mListView.getHeight();
        }
        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }

    @Override
    public void loadMore() {
        if (!isLoading) {
            isLoading = true;
            ++mPage;
            if (mediaBean != null) {
                mPresenter.lodeVideoCommentsRequest(mediaBean.id, mPage);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, UserInfoActivity.class);
        UserBean userBean = mediaBean.user;
        if (userBean != null) {
            intent.putExtra("user", userBean);
            startActivity(intent);
        }
    }
}
