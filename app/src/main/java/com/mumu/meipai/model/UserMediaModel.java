package com.mumu.meipai.model;

import com.mumu.meipai.api.HostType;
import com.mumu.meipai.api.MeiPaiAPI;
import com.mumu.meipai.bean.MediaBean;
import com.mumu.meipai.contract.UserMediaContact;

import java.util.List;

import rx.Observable;

public class UserMediaModel implements UserMediaContact.Model {

    @Override
    public Observable<List<MediaBean>> loadUserMedias(int id, int page) {
        return MeiPaiAPI.getDefault(HostType.MEIPAI).getUserVideoList(id, page);
    }
}