package com.mumu.meipai.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.mumu.common.base.BaseHeaderAdapter;
import com.mumu.common.utils.TimeUtil;
import com.mumu.meipai.R;
import com.mumu.meipai.bean.MediaBean;
import com.mumu.meipai.bean.UserBean;
import com.enrique.stackblur.FastBlurPostprocessor;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by MuMu on 2016/12/29/0029.
 */

public class UserMediaAdapter extends BaseHeaderAdapter<MediaBean, UserMediaAdapter.HeaderViewHolder, UserMediaAdapter.ViewHolder> {

    private UserBean mUserBean;

    public UserMediaAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.list_item_user_media, null);
        return new ViewHolder(itemView);
    }

    public void setHeaderData(UserBean userBean) {
        this.mUserBean = userBean;
    }

    @Override
    public void onBindItemViewHolder(ViewHolder holder, int position) {
        if (dataList != null && dataList.size() > 0) {
            MediaBean mediaBean = dataList.get(position-1);
            if (mediaBean != null) {
                holder.tv_describe.setText(mediaBean.caption);
                holder.tv_share_count.setText(String.valueOf(mediaBean.reposts_count));
                holder.tv_comment_count.setText(String.valueOf(mediaBean.comments_count));
                holder.tv_like_count.setText(String.valueOf(mediaBean.likes_count));
                holder.tv_create_time.setText(TimeUtil.formatData(TimeUtil.dateFormatMDHM, mediaBean.time));
                holder.jc_video_player.setUp(mediaBean.video, JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
                holder.jc_video_player.thumbImageView.setImageURI(mediaBean.cover_pic);

                UserBean userBean = mediaBean.user;
                if (userBean != null) {
                    holder.headerView.setImageURI(userBean.avatar);
                    holder.tv_nick.setText(userBean.screen_name);
                }
            }
        }
    }

    @Override
    public UserMediaAdapter.HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.header_user_media_info, null);
        return new HeaderViewHolder(itemView);
    }

    @Override
    public void onBindHeaderViewHolder(UserMediaAdapter.HeaderViewHolder holder, int position) {
        if (mUserBean != null) {
            holder.fansCount.setText("粉丝 " + mUserBean.followers_count);
            holder.folowCount.setText("关注 " + mUserBean.followers_count);
            holder.headerView.setImageURI(mUserBean.avatar);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(mUserBean.avatar))
                    .setPostprocessor(new FastBlurPostprocessor(80f))//设置高斯模糊
                    .build();

            PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder().setImageRequest(request)
                    .setOldController(holder.coverView.getController())
                    .build();

            holder.coverView.setController(controller);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final JCVideoPlayerStandard jc_video_player;
        private final SimpleDraweeView headerView;

        private final ImageView iv_verified;
        private final TextView tv_nick;
        private final TextView tv_create_time;
        private final TextView tv_follow;
        private final TextView tv_describe;
        private final TextView tv_like_count;
        private final TextView tv_comment_count;
        private final TextView tv_share_count;

        public ViewHolder(View itemView) {
            super(itemView);
            headerView = (SimpleDraweeView) itemView.findViewById(R.id.headerView);

            iv_verified = (ImageView) itemView.findViewById(R.id.iv_verified);
            tv_nick = (TextView) itemView.findViewById(R.id.tv_nick);
            tv_create_time = (TextView) itemView.findViewById(R.id.tv_create_time);
            tv_follow = (TextView) itemView.findViewById(R.id.tv_follow);
            tv_describe = (TextView) itemView.findViewById(R.id.tv_describe);
            tv_like_count = (TextView) itemView.findViewById(R.id.tv_like_count);
            tv_comment_count = (TextView) itemView.findViewById(R.id.tv_comment_count);
            tv_share_count = (TextView) itemView.findViewById(R.id.tv_share_count);
            jc_video_player = (JCVideoPlayerStandard) itemView.findViewById(R.id.jc_video_player);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView headerView;

        private final TextView fansCount;
        private final TextView folowCount;
        private final SimpleDraweeView coverView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            coverView = (SimpleDraweeView) itemView.findViewById(R.id.coverView);
            headerView = (SimpleDraweeView) itemView.findViewById(R.id.headerView);
            fansCount = (TextView) itemView.findViewById(R.id.tv_fansCount);
            folowCount = (TextView) itemView.findViewById(R.id.tv_followCount);
        }
    }
}
