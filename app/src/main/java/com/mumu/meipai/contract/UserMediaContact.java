package com.mumu.meipai.contract;


import com.mumu.common.base.BaseModel;
import com.mumu.common.base.BasePresenter;
import com.mumu.common.base.BaseView;
import com.mumu.meipai.bean.MediaBean;

import java.util.List;

import rx.Observable;

/**
 * Created by MuMu on 2016/12/24/0024.
 */

public interface UserMediaContact {

    interface Model extends BaseModel {
        Observable<List<MediaBean>> loadUserMedias(int id, int page);
    }

    interface View extends BaseView {
        void returnUserMedias(List<MediaBean> mediaBeens);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void lodeUserVideoRequest(int id, int page);
    }
}
