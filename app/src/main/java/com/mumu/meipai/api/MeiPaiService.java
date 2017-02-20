package com.mumu.meipai.api;

import com.mumu.meipai.bean.CommentEntity;
import com.mumu.meipai.bean.HomeChannel;
import com.mumu.meipai.bean.MediaBean;
import com.mumu.meipai.bean.MediaEntity;
import com.mumu.meipai.bean.TopicBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

public interface MeiPaiService {
    //获取频道信息
    @GET(AppConstant.HOME_CHANNEL)
    Observable<List<HomeChannel>> getChannelList(@Header("Cache-Control") String str, @Query("language") String language);

    //获取热门视频列表
    @GET(AppConstant.HOT_VIDEO_LIST)
    Observable<List<MediaEntity>> getHotVideoList(@Query("page") int page, @Query("count") int count);

    //获取视频列表
    @GET(AppConstant.VIDEO_LIST)
    Observable<List<MediaEntity>> getVideoList(@Query("id") int id, @Query("type") int type, @Query("page") int page, @Query("count") int count);

    //获取视频评论列表
    @GET(AppConstant.COMMENTS)
    Observable<List<CommentEntity>> getVideoCommentList(@Query("id") int id, @Query("page") int page);

    //获取用户发布的视频
    @GET(AppConstant.USER_MEDIAS)
    Observable<List<MediaBean>> getUserVideoList(@Query("uid") int id, @Query("page") int page);

    //获取推荐的视频
    @GET(AppConstant.SUGGEST_MEDIAS)
    Observable<List<MediaBean>> getSuggestVideoList(@Query("id") int id);


    //获取话题信息
    @GET(AppConstant.TOPIC_INFO)
    Observable<TopicBean> getTopicInfo(@Query("k") String topic);

    //获取话题视频列表
    @GET(AppConstant.TOPIC_MEDIAS)
    Observable<List<MediaEntity>> getTopicVideoList(@Query("id") String id,@Query("type") int type,@Query("feature") String feature,@Query("page") int page);
}