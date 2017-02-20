package com.mumu.meipai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mumu.meipai.R;
import com.mumu.meipai.bean.MediaBean;
import java.util.List;


/**
 * Created by MuMu on 2017/01/06.
 */

public class SuggestMediaAdapter extends RecyclerView.Adapter<SuggestMediaAdapter.ViewHolder> {

    private Context mContext;
    private List<MediaBean> data;

    public SuggestMediaAdapter(Context mContext) {
        this.mContext = mContext;

    }

    public void bindData(List<MediaBean> suggestData){
        this.data = suggestData;
        notifyDataSetChanged();
    }

    @Override
    public SuggestMediaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.list_item_suggest_media, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SuggestMediaAdapter.ViewHolder holder, int position) {
        if (data != null && data.size() > 0) {
            MediaBean mediaBean = data.get(position);
            if (mediaBean != null) {
                holder.cover.setImageURI(mediaBean.cover_pic);
                if (!TextUtils.isEmpty(mediaBean.caption)) {
                    holder.tv_describe.setText(mediaBean.caption);
                } else {
                    holder.tv_describe.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private  SimpleDraweeView cover;
        private  TextView tv_describe;

        public ViewHolder(View itemView) {
            super(itemView);
            cover = (SimpleDraweeView) itemView.findViewById(R.id.cover);
            tv_describe = (TextView) itemView.findViewById(R.id.tv_describe);
        }
    }
}
