package com.mumu.meipai.presenter;

import com.mumu.common.baserx.RxSubscriber;
import com.mumu.common.utils.LogUtil;
import com.mumu.meipai.bean.HomeChannel;
import com.mumu.meipai.contract.HomeContract;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomePresenter extends HomeContract.Presenter {
    public String TAG = getClass().getSimpleName();

    public void lodeHomeAllChannelsRequest() {
        mRxManage.add((mModel)
                .loadAllChannels()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<List<HomeChannel>>() {
                    protected void _onNext(List<HomeChannel> homeChannels) {
                        mView.returnHomeAllChannels(homeChannels);
                    }

                    protected void _onError(String message) {
                        LogUtil.logd(TAG, message);
                        mView.showErrorTip(message);
                    }
                }));
    }
}