package com.mumu.meipai.model;

import com.mumu.meipai.api.HostType;
import com.mumu.meipai.api.MeiPaiAPI;
import com.mumu.meipai.bean.MediaEntity;
import com.mumu.meipai.bean.TopicBean;
import com.mumu.meipai.contract.TopicContract;
import java.util.List;
import rx.Observable;

public class TopicModel implements TopicContract.Model {

    @Override
    public Observable<List<MediaEntity>> loadTopicVideos(String id,int type,String feature,int page) {
        return MeiPaiAPI.getDefault(HostType.MEIPAI).getTopicVideoList(id,type,feature,page);
    }

    @Override
    public Observable<TopicBean> loadTopicInfo(String topic) {
         return MeiPaiAPI.getDefault(HostType.MEIPAI).getTopicInfo(topic);
    }
}