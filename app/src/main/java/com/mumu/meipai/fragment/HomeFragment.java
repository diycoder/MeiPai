package com.mumu.meipai.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.mumu.common.base.BaseFragment;
import com.mumu.common.utils.LogUtil;
import com.mumu.meipai.R;
import com.mumu.meipai.adapter.MediaPagerAdapter;
import com.mumu.meipai.bean.HomeChannel;
import com.mumu.meipai.contract.HomeContract;
import com.mumu.meipai.model.HomeModel;
import com.mumu.meipai.presenter.HomePresenter;
import java.util.List;

public class HomeFragment extends BaseFragment<HomePresenter, HomeModel> implements HomeContract.View {

    private String TAG = getClass().getSimpleName();
    private MediaPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        View inflateView = getView();
        mTabLayout = (TabLayout) inflateView.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) inflateView.findViewById(R.id.viewPager);
        mAdapter = new MediaPagerAdapter(getChildFragmentManager());
        mPresenter.lodeHomeAllChannelsRequest();
    }

    @Override
    public void returnHomeAllChannels(List<HomeChannel> homeChannels) {
        if (homeChannels != null && homeChannels.size() > 0) {
            for (HomeChannel homeChannel : homeChannels) {
                String name = homeChannel.getName().trim().replaceAll("#", "");
                mTabLayout.addTab(mTabLayout.newTab().setText(name));
                mAdapter.addItemTab(homeChannel);
            }
        }
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorTip(String msg) {
        LogUtil.logd(TAG, msg);
    }
}