package com.mumu.meipai.model;

import com.mumu.meipai.api.MeiPaiAPI;
import com.mumu.meipai.bean.HomeChannel;
import com.mumu.meipai.contract.HomeContract.Model;
import java.util.List;
import rx.Observable;

public class HomeModel implements Model {
    public Observable<List<HomeChannel>> loadAllChannels() {
        return MeiPaiAPI.getDefault(0).getChannelList(MeiPaiAPI.getCacheControl(), "zh-Hans");
    }
}