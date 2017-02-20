package com.mumu.meipai.presenter;

import com.mumu.common.utils.LogUtil;
import com.mumu.meipai.bean.CommentEntity;
import com.mumu.meipai.bean.MediaBean;
import com.mumu.meipai.contract.MediaComment;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by MuMu on 2016/12/24/0024.
 */

public class MediaCommentPresenter extends MediaComment.Presenter {

    public String TAG = getClass().getSimpleName();


    @Override
    public void lodeVideoCommentsRequest(int id, int page) {
        mRxManage.add(mModel.loadComments(id, page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CommentEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.logd(TAG, e.getMessage());
                        mView.showErrorTip(e.getMessage());
                    }

                    @Override
                    public void onNext(List<CommentEntity> commentEntities) {
                        mView.returnComments(commentEntities);
                    }
                }));
    }

    @Override
    public void lodeSuggestVideoRequest(int id) {
        mRxManage.add(mModel.loadSuggestVideo(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<MediaBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.logd(TAG, e.getMessage());
                        mView.showErrorTip(e.getMessage());
                    }

                    @Override
                    public void onNext(List<MediaBean> mediaBeen) {
                        mView.returnSuggestVideo(mediaBeen);
                    }
                }));
    }
}
