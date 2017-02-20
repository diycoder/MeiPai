package com.mumu.meipai.bean;

import java.io.Serializable;

/**
 * Created by MuMu on 2016/12/24/0024.
 */

public class MediaBean implements Serializable {
    public String caption;
    public int category;
    public int comments_count;
    public String cover_pic;
    public String created_at;
    public int display_source;
    public String facebook_share_caption;
    public long feed_id;
    public int has_watermark;
    public int id;
    public String instagram_share_caption;
    public boolean is_long;
    public boolean is_popular;
    public int likes_count;
    public boolean locked;
    public String pic_size;
    public PrivacyConfigBean privacy_config;
    public String qq_share_caption;
    public String qq_share_sub_caption;
    public String qzone_share_caption;
    public String qzone_share_sub_caption;
    public int reposts_count;
    public boolean show_controls;
    public long time;
    public String url;
    public UserBean user;
    public String video;
    public String weibo_share_caption;
    public String weixin_friendfeed_share_caption;
    public String weixin_friendfeed_share_sub_caption;
    public String weixin_share_caption;
    public String weixin_share_sub_caption;

    @Override
    public String toString() {
        return "MediaBean{" +
                "caption='" + caption + '\'' +
                ", category=" + category +
                ", comments_count=" + comments_count +
                ", cover_pic='" + cover_pic + '\'' +
                ", created_at='" + created_at + '\'' +
                ", display_source=" + display_source +
                ", facebook_share_caption='" + facebook_share_caption + '\'' +
                ", feed_id=" + feed_id +
                ", has_watermark=" + has_watermark +
                ", id=" + id +
                ", instagram_share_caption='" + instagram_share_caption + '\'' +
                ", is_long=" + is_long +
                ", is_popular=" + is_popular +
                ", likes_count=" + likes_count +
                ", locked=" + locked +
                ", pic_size='" + pic_size + '\'' +
                ", privacy_config=" + privacy_config +
                ", qq_share_caption='" + qq_share_caption + '\'' +
                ", qq_share_sub_caption='" + qq_share_sub_caption + '\'' +
                ", qzone_share_caption='" + qzone_share_caption + '\'' +
                ", qzone_share_sub_caption='" + qzone_share_sub_caption + '\'' +
                ", reposts_count=" + reposts_count +
                ", show_controls=" + show_controls +
                ", time=" + time +
                ", url='" + url + '\'' +
                ", user=" + user +
                ", video='" + video + '\'' +
                ", weibo_share_caption='" + weibo_share_caption + '\'' +
                ", weixin_friendfeed_share_caption='" + weixin_friendfeed_share_caption + '\'' +
                ", weixin_friendfeed_share_sub_caption='" + weixin_friendfeed_share_sub_caption + '\'' +
                ", weixin_share_caption='" + weixin_share_caption + '\'' +
                ", weixin_share_sub_caption='" + weixin_share_sub_caption + '\'' +
                '}';
    }
}
