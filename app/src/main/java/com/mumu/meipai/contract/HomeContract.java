package com.mumu.meipai.contract;


import com.mumu.common.base.BaseModel;
import com.mumu.common.base.BasePresenter;
import com.mumu.common.base.BaseView;
import com.mumu.meipai.bean.HomeChannel;

import java.util.List;

import rx.Observable;

/**
 * Created by MuMu on 2016/12/24/0024.
 */

public interface HomeContract {

    interface Model extends BaseModel{
        Observable<List<HomeChannel>> loadAllChannels();
    }

    interface View extends BaseView {
        void returnHomeAllChannels(List<HomeChannel> homeChannels);
    }

    abstract  class Presenter extends BasePresenter<View, Model> {
        public abstract void lodeHomeAllChannelsRequest();
    }
}
