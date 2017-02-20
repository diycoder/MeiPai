package com.mumu.meipai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mumu.common.base.BaseAdapter;
import com.mumu.common.utils.DisplayUtil;
import com.mumu.meipai.R;
import com.mumu.meipai.api.AppConstant;
import com.mumu.meipai.bean.MediaBean;
import com.mumu.meipai.bean.MediaEntity;
import com.mumu.meipai.bean.UserBean;
import com.yixia.camera.FFMpegUtils;

import java.util.Random;

/**
 * Created by MuMu on 2016/12/29/0029.
 */

public class MediaAdapter extends BaseAdapter<MediaEntity, MediaAdapter.ViewHolder> {

    private SparseArray<Float> mHeightRatios;
    private final int media_padding_left_right = DisplayUtil.dip2px(8.0f);
    private final int scheme_padding_left_right = DisplayUtil.dip2px(FFMpegUtils.AUDIO_VOLUME_HIGH);

    private Random mRandom;
    private CallBackListener mCallBack;

    public MediaAdapter(Context context) {
        super(context);
        mRandom = new Random();
        mHeightRatios = new SparseArray();

    }

    public interface CallBackListener {
        void loadMore();

        void onItemClick(int position);
    }

    public void setLoadMoreListener(CallBackListener loadMoreListener) {
        this.mCallBack = loadMoreListener;
    }

    @Override
    public ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.list_item_media, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindItemViewHolder(ViewHolder holder, final int position) {
        if (dataList != null && dataList.size() > 0) {
            if (mCallBack != null) {
                if (dataList.size() - position <= 2) {
                    mCallBack.loadMore();
                }
            }
            final MediaEntity mediaEntity = dataList.get(position);
            if (mediaEntity != null) {
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setUri(mediaEntity.recommend_cover_pic)
                        .setAutoPlayAnimations(true)
                        .build();
                holder.imageView.setAspectRatio(getPositionRatio(mediaEntity.type, position));
                holder.tv_describe.setText(mediaEntity.recommend_caption);
                if (AppConstant.TYPE_SCHEME.equals(mediaEntity.type)) {
                    holder.imageView.setController(controller);
                    holder.tv_describe.setPadding(scheme_padding_left_right, media_padding_left_right, scheme_padding_left_right, media_padding_left_right);
                    holder.ll_header_container.setVisibility(View.GONE);
                } else {
                    holder.tv_describe.setPadding(media_padding_left_right, media_padding_left_right, media_padding_left_right, media_padding_left_right);
                    holder.imageView.setImageURI(mediaEntity.recommend_cover_pic);
                    holder.ll_header_container.setVisibility(View.VISIBLE);
                }
                if (mediaEntity.media != null) {
                    MediaBean mediaBean = mediaEntity.media;
                    if (mediaBean != null) {
                        holder.tv_praiseCount.setText(String.valueOf(mediaBean.likes_count));
                        UserBean userBean = mediaBean.user;
                        if (userBean != null) {
                            if (!TextUtils.isEmpty(userBean.screen_name)) {
                                holder.tv_name.setText(userBean.screen_name);
                            }
                            if (!TextUtils.isEmpty(userBean.avatar)) {
                                holder.headerView.setImageURI(userBean.avatar);
                            }
                            if (userBean.verified) {
                                holder.iv_verified.setVisibility(View.VISIBLE);
                            } else {
                                holder.iv_verified.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                }
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallBack.onItemClick(position);
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView headerView;
        SimpleDraweeView imageView;
        ImageView iv_verified;
        LinearLayout ll_header_container;
        TextView tv_describe;
        TextView tv_name;
        TextView tv_praiseCount;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (SimpleDraweeView) itemView.findViewById(R.id.image_view);
            headerView = (SimpleDraweeView) itemView.findViewById(R.id.headerView);
            tv_praiseCount = (TextView) itemView.findViewById(R.id.tv_praiseCount);
            tv_describe = (TextView) itemView.findViewById(R.id.tv_describe);
            iv_verified = (ImageView) itemView.findViewById(R.id.iv_verified);
            ll_header_container = (LinearLayout) itemView.findViewById(R.id.ll_header_container);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }


    private float getPositionRatio(String type, int position) {
        float ratio = mHeightRatios.get(position, Float.valueOf(0.0f));
        if (ratio != 0.0f) {
            return ratio;
        }
        if (AppConstant.TYPE_SCHEME.equals(type)) {
            ratio = 0.9f;
        } else {
            ratio = getRandomHeightRatio();
        }

        mHeightRatios.append(position, Float.valueOf(ratio));
        return ratio;
    }

    private float getRandomHeightRatio() {
        return (this.mRandom.nextFloat() / 3.0f) + 0.9f;
    }
}
