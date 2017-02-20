package com.mumu.meipai.contract;


import com.mumu.common.base.BaseModel;
import com.mumu.common.base.BasePresenter;
import com.mumu.common.base.BaseView;
import com.mumu.meipai.bean.CommentEntity;
import com.mumu.meipai.bean.MediaBean;

import java.util.List;

import rx.Observable;

/**
 * Created by MuMu on 2016/12/24/0024.
 */

public interface MediaComment {

    interface Model extends BaseModel {
        Observable<List<CommentEntity>> loadComments(int id, int page);

        Observable<List<MediaBean>> loadSuggestVideo(int id);
    }

    interface View extends BaseView {
        void returnComments(List<CommentEntity> commentEntities);

        void returnSuggestVideo(List<MediaBean> mediaBeanList);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void lodeVideoCommentsRequest(int id, int page);

        public abstract void lodeSuggestVideoRequest(int id);
    }
}
