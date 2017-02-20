package com.mumu.meipai.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mumu.meipai.bean.HomeChannel;
import com.mumu.meipai.fragment.MediaFragment;

import java.util.ArrayList;
import java.util.List;

public class MediaPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> mChannelNames = new ArrayList<>();

    public MediaPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    public int getCount() {
        return fragmentList.size();
    }

    public CharSequence getPageTitle(int position) {
        return mChannelNames.get(position);
    }

    public void addItemTab(HomeChannel homeChannel) {
        fragmentList.add(MediaFragment.newInstance(homeChannel.getId(), homeChannel.getType()));
        mChannelNames.add(homeChannel.getName().replaceAll("#", ""));
    }
}