package com.mumu.meipai.bean;

import java.io.Serializable;

/**
 * Created by MuMu on 2016/12/24/0024.
 */

public class MediaEntity implements Serializable {

    /**
     * media : {"caption":"#腐女福利##＃腐基情＃ 腐女福利#他欺负我👿","category":3,"comments_count":339,"cover_pic":"http://mvimg1.meitudata.com/585d00d49b3967889.jpg","created_at":"1482490085","display_source":1,"facebook_share_caption":"","feed_id":6367246434495636870,"has_watermark":1,"id":628893062,"instagram_share_caption":"Sharing 西槿～\u2019s video \u201c#腐女福利##＃腐基情＃ 腐女福利#他欺负我👿\u201d, come and take a look!","is_long":true,"is_popular":false,"likes_count":5436,"locked":false,"pic_size":"480*480","privacy_config":{"allow_save_medias":0,"forbid_stranger_comment":0},"qq_share_caption":"#腐女福利##＃腐基情＃ 腐女福利#他欺负我👿","qq_share_sub_caption":"","qzone_share_caption":"#腐女福利##＃腐基情＃ 腐女福利#他欺负我👿","qzone_share_sub_caption":"","reposts_count":48,"show_controls":true,"time":34,"url":"http://www.meipai.com/media/628893062","user":{"age":19,"avatar":"http://mvavatar1.meitudata.com/57f93db82592b2392.jpg","be_liked_count":439921,"birthday":"1997-09-22","city":2632303,"constellation":"Virgo","country":2630000,"created_at":1402150044,"followers_count":133476,"friends_count":71,"funy_core_user_created_at":0,"gender":"m","has_password":false,"id":33420811,"is_funy_core_user":false,"last_publish_time":0,"level":13,"locked_photos_count":0,"locked_videos_count":0,"photos_count":3,"province":2632300,"real_locked_videos_count":0,"real_videos_count":25,"reposts_count":10,"screen_name":"西槿～","show_pendant":true,"status":"5","url":"http://www.meipai.com/user/33420811","verified":false,"videos_count":28},"video":"http://mvvideo11.meitudata.com/585d14c6dc4843492.mp4","weibo_share_caption":"Check out @西槿殿下 \u2019s Meipai:\u201c#腐女福利##＃腐基情＃ 腐女福利#他欺负我👿\u201d","weixin_friendfeed_share_caption":"#腐女福利##＃腐基情＃ 腐女福利#他欺负我👿","weixin_friendfeed_share_sub_caption":"","weixin_share_caption":"Sharing 西槿～\u2019s videos","weixin_share_sub_caption":"#腐女福利##＃腐基情＃ 腐女福利#他欺负我👿"}
     * recommend_caption : 这口狗粮我吃了
     * recommend_cover_pic : http://mvimg1.meitudata.com/585d00d49b3967889.jpg!thumb320
     * recommend_cover_pic_size : 480*480
     * type : media
     * id : 6085656513116419366
     * name : #圣诞节手工diy#
     */

    public MediaBean media;
    public String recommend_caption;
    public String recommend_cover_pic;
    public String recommend_cover_pic_size;
    public String type;
    public String id;
    public String name;

    @Override
    public String toString() {
        return "MediaEntity{" +
                "media=" + media +
                ", recommend_caption='" + recommend_caption + '\'' +
                ", recommend_cover_pic='" + recommend_cover_pic + '\'' +
                ", recommend_cover_pic_size='" + recommend_cover_pic_size + '\'' +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
