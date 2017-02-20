package com.mumu.meipai.api;

public class AppConstant {
    public static final String BASE_URL = "https://newapi.meipai.com/";
    public static final String CHANNEL_HOME = "home";
    public static final String COMMENTS = "comments/show.json";
    public static final String CREATE_COMMENTS_LIKE = "comments/create_like.json";
    public static final String DESTORY_COMMENT_LIKE = "comments/destroy_like.json";
    public static final String FOLLOWERS = "friendships/followers.json";
    public static final String FRIENDSHIPS = "friendships/friends.json";
    public static final String HOME_CHANNEL = "channels/header_list.json";
    public static final String HOT_VIDEO_LIST = "hot/feed_timeline.json";
    public static final String LIKES_VIDEO_CREATE = "likes/create.json";
    public static final String LIKES_VIDEO_DESTORY = "likes/destroy.json";
    public static final String MEDIAS = "medias/show.json";
    public static final String OAUTH = "oauth/access_token.json";
    public static final int PAGE_COUNT = 20;
    public static final String RESET_PASSWORD = "users/reset_password.json";
    public static final String SEND_VERIFY_CODE = "common/send_verify_code_to_phone.json";
    public static final String TYPE_SCHEME = "scheme";
    public static final String USERS_UPDATE = "users/update.json";
    public static final String USER_MEDIAS = "medias/user_timeline.json";
    public static final String TOPIC_MEDIAS = "medias/topics_timeline.json";
    public static final String TOPIC_INFO = "channels/show.json";
    public static final String SUGGEST_MEDIAS = "suggestions/medias_by_id.json";
    public static final String USER_REPOSTS = "reposts/user_timeline.json";
    public static final String VIDEO_LIST = "channels/feed_timeline.json";

    public final class ParamKey {
        //client_secret
        public final static String CLIENT_SECRET_KEY = "client_secret";
        //grant_type
        public final static String GRANT_TYPE_KEY = "grant_type";
        //client_id
        public final static String CLIENT_ID_KEY = "client_id";

        public final static String CLIENT_LANGUAGE = "zh-Hans";

        public final static String DEVICE_ID_KEY = "device_id";

        public final static String LANGUAGE_KEY = "language";

        public final static String MODEL_KEY = "model";

        public final static String ACCESS_TOKEN_KEY = "access-token";

        public final static String PHONE_KEY = "phone";

        public final static String PASSWORD_KEY = "password";

        public final static String ID_KEY = "id";

        public final static String TYPE_KEY = "type";

        public final static String PAGE_KEY = "page";

        public final static String COUNT_KEY = "count";

        public final static String VERIFY_CODE_KEY = "verify_code";

        public final static String AUTO_LOGIN_KEY = "auto_login";

        public final static String SCREEN_NAEM_KEY = "screen_name";
        public final static String GENDER_KEY = "gender";

        public final static String DESCRIPTION_KEY = "description";

        public final static String UID_KEY = "uid";

        public final static String WITH_CAPTION_KEY = "with_caption";

    }

    public final class ParamDefaultValue {

        //client_secret
        public final static String CLIENT_SECRET = "38e8c5aet76d5c012e32";
        //client_id
        public final static String CLIENT_ID = "1089857302";

        public final static String LANGUAGE = "zh-Hans";

        //grant_type
        public final static String GRANT_TYPE = "phone";

        public final static String TYPR_RESET_PASSWORD = "reset_password";
        public final static int AUTO_LOGIN = 1;

        public final static int WITH_CAPTION = 1;

    }


    public static String getHost(int hostType) {
        switch (hostType) {
            case 0:
                String str = BASE_URL;
                break;
        }
        return BASE_URL;
    }
}