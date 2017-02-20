package com.mumu.meipai.model;

import com.mumu.meipai.api.HostType;
import com.mumu.meipai.api.MeiPaiAPI;
import com.mumu.meipai.bean.CommentEntity;
import com.mumu.meipai.bean.MediaBean;
import com.mumu.meipai.contract.MediaComment;

import java.util.List;

import rx.Observable;

public class MediaCommentModel implements MediaComment.Model {

    @Override
    public Observable<List<CommentEntity>> loadComments(int id, int page) {
        return MeiPaiAPI.getDefault(0).getVideoCommentList(id, page);
    }

    @Override
    public Observable<List<MediaBean>> loadSuggestVideo(int id) {
        return MeiPaiAPI.getDefault(HostType.MEIPAI).getSuggestVideoList(id);
    }
}