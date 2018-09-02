package com.zjyang.mvpframe.module.rank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.home.model.bean.VideoInfo;
import com.zjyang.mvpframe.utils.FrescoUtils;

import java.util.List;

/**
 * Created by 74215 on 2018/9/1.
 */

public class RankListAdapter extends RecyclerView.Adapter<RankListAdapter.RankViewHolder>{

    private Context mContext;
    private List<VideoInfo> mVideoList;

    public RankListAdapter(Context context, List<VideoInfo> mVideoList) {
        this.mVideoList = mVideoList;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    @Override
    public RankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_rank_list, parent, false);
        RankViewHolder viewHolder = new RankViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RankViewHolder holder, int position) {
        VideoInfo videoInfo = mVideoList.get(position);
        holder.mNumTv.setText((position + 4)+"");
        FrescoUtils.showImgByUrl(videoInfo.getVideoThumbUrl(), holder.mVideoPreIv);
        FrescoUtils.showImgByUrl(videoInfo.getUserPicUrl(), holder.mUserPicIv);
        String userName = videoInfo.getUserName();
        String describe = videoInfo.getDescribe();
        holder.mUserNameTv.setText(TextUtils.isEmpty(userName) ? "" : userName);
        holder.mDescribeTv.setText(TextUtils.isEmpty(describe) ? "" : describe);
        holder.mWatchNum.setText(videoInfo.getWatchNum());
    }

    public class RankViewHolder extends RecyclerView.ViewHolder{

        private TextView mNumTv;
        private SimpleDraweeView mVideoPreIv;
        private SimpleDraweeView mUserPicIv;
        private TextView mUserNameTv;
        private TextView mDescribeTv;
        private TextView mWatchNum;

        public RankViewHolder(View itemView) {
            super(itemView);
            mNumTv = (TextView) itemView.findViewById(R.id.rank_num_tv);
            mVideoPreIv = (SimpleDraweeView) itemView.findViewById(R.id.video_preview_iv);
            mUserPicIv = (SimpleDraweeView) itemView.findViewById(R.id.user_pic_iv);
            mUserNameTv = (TextView) itemView.findViewById(R.id.user_name_tv);
            mDescribeTv = (TextView) itemView.findViewById(R.id.describe_tv);
            mWatchNum = (TextView) itemView.findViewById(R.id.watch_num_tv);
        }
    }

}







