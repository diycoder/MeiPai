package com.mumu.meipai.contract;


import com.mumu.common.base.BaseModel;
import com.mumu.common.base.BasePresenter;
import com.mumu.common.base.BaseView;
import com.mumu.meipai.bean.MediaEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by MuMu on 2016/12/24/0024.
 */

public interface MediaContract {

    interface Model extends BaseModel {
        Observable<List<MediaEntity>> loadVideoList(int id, int type, int page, int count);
    }

    interface View extends BaseView {
        void returnVideoList(List<MediaEntity> homeChannels);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void loadVideoListRequest(int id, int type, int page, int count);
    }
}
