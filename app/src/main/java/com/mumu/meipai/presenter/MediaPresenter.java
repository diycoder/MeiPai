package com.mumu.meipai.presenter;

import com.mumu.common.utils.LogUtil;
import com.mumu.meipai.bean.MediaEntity;
import com.mumu.meipai.contract.MediaContract;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by MuMu on 2016/12/24/0024.
 */

public class MediaPresenter extends MediaContract.Presenter {

    public String TAG = getClass().getSimpleName();


    @Override
    public void loadVideoListRequest(int id, int type, int page, int count) {
        mRxManage.add(mModel.loadVideoList(id, type, page, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<MediaEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.logd(TAG, e.getMessage());
                        mView.showErrorTip(e.getMessage());
                    }

                    @Override
                    public void onNext(List<MediaEntity> mediaEntityList) {
                        mView.returnVideoList(mediaEntityList);
                    }
                }));
    }
}
