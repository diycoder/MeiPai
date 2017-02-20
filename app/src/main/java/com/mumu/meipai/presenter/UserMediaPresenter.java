package com.mumu.meipai.presenter;

import com.mumu.common.utils.LogUtil;
import com.mumu.meipai.bean.MediaBean;
import com.mumu.meipai.contract.UserMediaContact;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by MuMu on 2016/12/24/0024.
 */

public class UserMediaPresenter extends UserMediaContact.Presenter {

    public String TAG = getClass().getSimpleName();

    @Override
    public void lodeUserVideoRequest(int id, int page) {
        mRxManage.add(mModel.loadUserMedias(id, page)
                .subscribeOn(Schedulers.io())
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
                    public void onNext(List<MediaBean> mediaEntityList) {
                        mView.returnUserMedias(mediaEntityList);
                    }
                }));
    }
}
