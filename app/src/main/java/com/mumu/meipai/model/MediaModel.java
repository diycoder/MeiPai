package com.mumu.meipai.model;

import com.mumu.meipai.api.MeiPaiAPI;
import com.mumu.meipai.bean.MediaEntity;
import com.mumu.meipai.contract.MediaContract;

import java.util.List;

import rx.Observable;

public class MediaModel implements MediaContract.Model {
    public Observable<List<MediaEntity>> loadVideoList(int id, int type, int page, int count) {
        if (type == 1 || id == 1) {
            return MeiPaiAPI.getDefault(0).getHotVideoList(page, count);
        } else {
            return MeiPaiAPI.getDefault(0).getVideoList(id, type, page, count);
        }
    }
}