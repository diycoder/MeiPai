package com.mumu.meipai.contract;


import com.mumu.common.base.BaseModel;
import com.mumu.common.base.BasePresenter;
import com.mumu.common.base.BaseView;
import com.mumu.meipai.bean.MediaEntity;
import com.mumu.meipai.bean.TopicBean;

import java.util.List;

import rx.Observable;

/**
 * Created by MuMu on 2016/12/24/0024.
 */

public interface TopicContract {

    interface Model extends BaseModel{
        Observable<List<MediaEntity>> loadTopicVideos(String id,int type,String feature,int page);
        Observable<TopicBean> loadTopicInfo(String topic);
    }

    interface View extends BaseView {
        void returnTopicVideos(List<MediaEntity> mediaEntityList);

        void returnTopicInfo(TopicBean topicBean);
    }

    abstract  class Presenter extends BasePresenter<View, Model> {
        public abstract void lodeTopicListRequest(String id,int type,String feature,int page);

        public abstract void lodeTopicInfoRequest(String topic);
    }
}
