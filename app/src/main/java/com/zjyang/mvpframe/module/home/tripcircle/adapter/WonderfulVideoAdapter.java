package com.zjyang.mvpframe.module.home.tripcircle.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.home.tripcircle.model.bean.WonderfulVideo;
import com.zjyang.mvpframe.module.home.tripcircle.view.VideoDetailActivity;
import com.zjyang.mvpframe.utils.FrescoUtils;

import java.util.List;

/**
 * Created by zhengjiayang on 2018/8/23.
 */

public class WonderfulVideoAdapter extends RecyclerView.Adapter<WonderfulVideoAdapter.WonderfulVideoViewHolder>{

    private List<WonderfulVideo> mVideoList;
    private Context mContext;

    public WonderfulVideoAdapter(Context mContext, List<WonderfulVideo> videoList) {
        this.mContext = mContext;
        this.mVideoList = videoList;
    }

    @Override
    public WonderfulVideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wonderful_video, parent, false);
        WonderfulVideoViewHolder viewHolder = new WonderfulVideoViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WonderfulVideoViewHolder holder, int position) {
        WonderfulVideo wonderfulVideo = mVideoList.get(position);
        String previewPic = wonderfulVideo.getPreivewPic();
        if(!TextUtils.isEmpty(previewPic)){
            FrescoUtils.showImgByUrl(previewPic, holder.mPreviewIv);
        }

        holder.mPreviewIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, VideoDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    public class WonderfulVideoViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mPreviewIv;
        public WonderfulVideoViewHolder(View itemView) {
            super(itemView);

            mPreviewIv = (SimpleDraweeView) itemView.findViewById(R.id.preview_iv);
        }
    }
}
