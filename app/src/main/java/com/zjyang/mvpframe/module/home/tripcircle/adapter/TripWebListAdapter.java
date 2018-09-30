package com.zjyang.mvpframe.module.home.tripcircle.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zjyang.base.ui.WebViewActivity;
import com.zjyang.base.utils.FrescoUtils;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.home.tripcircle.model.bean.TripWebInfo;
import com.zjyang.mvpframe.module.home.tripcircle.model.bean.WonderfulVideo;
import com.zjyang.mvpframe.module.home.tripcircle.view.VideoDetailActivity;

import java.util.List;

/**
 * Created by zhengjiayang on 2018/8/23.
 */

public class TripWebListAdapter extends RecyclerView.Adapter<TripWebListAdapter.TripWebListViewHolder>{

    private List<TripWebInfo> mVideoList;
    private Context mContext;

    public TripWebListAdapter(Context mContext, List<TripWebInfo> videoList) {
        this.mContext = mContext;
        this.mVideoList = videoList;
    }

    @Override
    public TripWebListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip_web_list, parent, false);
        TripWebListViewHolder viewHolder = new TripWebListViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TripWebListViewHolder holder, int position) {
        final TripWebInfo tripWebInfo = mVideoList.get(position);
        String icon = tripWebInfo.getIcon();
        String name = tripWebInfo.getName();
        String describe = tripWebInfo.getDescribe();
        final String url = tripWebInfo.getUrl();
        if(!TextUtils.isEmpty(icon)){
            FrescoUtils.showImgByUrl(icon, holder.mPreviewIv);
        }
        holder.mWebNameTv.setText(TextUtils.isEmpty(name) ? "" : name);
        holder.mDescribeTv.setText(TextUtils.isEmpty(describe) ? "" : describe);

        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.go(mContext, url);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    public class TripWebListViewHolder extends RecyclerView.ViewHolder {
        CardView mRootView;
        SimpleDraweeView mPreviewIv;
        TextView mWebNameTv;
        TextView mDescribeTv;
        public TripWebListViewHolder(View itemView) {
            super(itemView);
            mRootView = (CardView) itemView.findViewById(R.id.item_trip_web_root_view);
            mPreviewIv = (SimpleDraweeView) itemView.findViewById(R.id.web_icon_iv);
            mWebNameTv = (TextView) itemView.findViewById(R.id.web_name_tv);
            mDescribeTv = (TextView) itemView.findViewById(R.id.web_describe_tv);
        }
    }
}
