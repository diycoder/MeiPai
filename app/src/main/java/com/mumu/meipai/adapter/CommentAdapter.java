package com.mumu.meipai.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mumu.common.utils.TimeUtil;
import com.mumu.meipai.R;
import com.mumu.meipai.activity.UserInfoActivity;
import com.mumu.meipai.bean.CommentEntity;
import com.mumu.meipai.bean.UserBean;

import java.util.List;

/**
 * Created by MuMu on 2016/12/30/0030.
 */

public class CommentAdapter extends BaseAdapter {
    private Context mContext;
    private List<CommentEntity> data;
    private CallBack callBack;

    public CommentAdapter(Context mContext, List<CommentEntity> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public CommentEntity getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentHolder holder;
        if (convertView == null) {
            holder = new CommentHolder();
            convertView = View.inflate(mContext, R.layout.list_item_video_comment, null);
            holder.headerIcon = (SimpleDraweeView) convertView.findViewById(R.id.headerView);
            holder.tv_comment_content = ((TextView) convertView.findViewById(R.id.tv_comment_content));
            holder.tv_nick = ((TextView) convertView.findViewById(R.id.tv_nick));
            holder.tv_commentTime = ((TextView) convertView.findViewById(R.id.tv_commentTime));
            holder.tv_like_count = ((TextView) convertView.findViewById(R.id.tv_like_count));
            convertView.setTag(holder);
        } else {
            holder = ((CommentHolder) convertView.getTag());
        }

        if (data != null && data.size() > 0) {
            if (data.size() >= 1 && data.size() - position <= 1) {
                if (callBack != null) {
                    callBack.loadMore();
                }
            }

            CommentEntity commentEntity = getItem(position);
            if (commentEntity != null) {
                Intent intent = new Intent(mContext, UserInfoActivity.class);
                intent.putExtra("user", commentEntity.user);
                holder.tv_comment_content.setText(commentEntity.content);
                holder.tv_comment_content.setMovementMethod(LinkMovementMethod.getInstance());
                holder.tv_commentTime.setText(TimeUtil.getfriendlyTime(commentEntity.created_at));
                int liked_count = commentEntity.liked_count;
                if (liked_count == 0)
                    holder.tv_like_count.setText("");
                else
                    holder.tv_like_count.setText(String.valueOf(liked_count));

                final UserBean userBean = commentEntity.user;
                if (userBean != null) {
                    holder.tv_nick.setText(userBean.screen_name);
                    holder.headerIcon.setImageURI(userBean.avatar);
                    holder.headerIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, UserInfoActivity.class);
                            intent.putExtra("user", userBean);
                            mContext.startActivity(intent);
                        }
                    });
                }
            }
        }
        return convertView;
    }


    public interface CallBack {
        void loadMore();
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public static class CommentHolder {
        SimpleDraweeView headerIcon;
        TextView tv_nick;
        TextView tv_like_count;
        TextView tv_comment_content;
        TextView tv_commentTime;
    }
}
