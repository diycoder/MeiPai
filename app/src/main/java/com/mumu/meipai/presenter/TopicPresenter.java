package com.mumu.meipai.presenter;

import com.mumu.common.baserx.RxSubscriber;
import com.mumu.common.utils.LogUtil;
import com.mumu.meipai.bean.MediaEntity;
import com.mumu.meipai.bean.TopicBean;
import com.mumu.meipai.contract.TopicContract;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TopicPresenter extends TopicContract.Presenter {
    public String TAG = getClass().getSimpleName();

    public void lodeTopicListRequest(String id,int type,String feature,int page) {
        mRxManage.add((mModel)
                .loadTopicVideos(id,type,feature,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<List<MediaEntity>>() {
                    protected void _onNext(List<MediaEntity> mediaEntity) {
                        mView.returnTopicVideos(mediaEntity);
                    }

                    protected void _onError(String message) {
                        LogUtil.logd(TAG, message);
                        mView.showErrorTip(message);
                    }
                }));
    }

    @Override
    public void lodeTopicInfoRequest(String topic) {
        mRxManage.add((mModel)
                .loadTopicInfo(topic)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<TopicBean>() {
                    protected void _onNext(TopicBean topicBean) {
                        mView.returnTopicInfo(topicBean);
                    }

                    protected void _onError(String message) {
                        LogUtil.logd(TAG, message);
                        mView.showErrorTip(message);
                    }
                }));
    }

}