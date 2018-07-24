package com.zjyang.mvpframe.module.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zjy.player.controller.ItemVideoController;
import com.example.zjy.player.ui.VideoFrame;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.fullscreen.view.FullScreenWatchActivity;
import com.zjyang.mvpframe.module.home.discover.model.VideoFramesModel;
import com.zjyang.mvpframe.module.home.model.bean.VideoInfo;
import com.zjyang.mvpframe.ui.ShapeUtils;
import com.zjyang.mvpframe.utils.DrawUtils;
import com.zjyang.mvpframe.utils.FrescoUtils;
import com.zjyang.mvpframe.utils.LogUtil;

import java.util.List;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by 74215 on 2018/4/1.
 */

public class GridVideoListAdapter extends RecyclerView.Adapter<GridVideoListAdapter.MyViewHolder>{

    private Context mContext;
    private List<VideoInfo> mVideoData;
    public static final int STOP_STATUS = 0;
    public static final int LOADING_STATUS = 1;
    public static final int PLAYING_STATUS = 2;

    public GridVideoListAdapter(Context context, List<VideoInfo> videoData) {
        this.mVideoData = videoData;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_video_list, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final VideoInfo videoInfo = mVideoData.get(position);
        LogUtil.d("zjy", "onBindView: " + position  +"， "+ holder.mCenterPlayIv);
        holder.mPreviewIv.setVisibility(View.VISIBLE);
        holder.mCenterPlayIv.setVisibility(View.VISIBLE);
        holder.mUserPicIv.setVisibility(View.VISIBLE);
        holder.mUserNameTv.setVisibility(View.VISIBLE);
        holder.mDurationTv.setVisibility(View.VISIBLE);
        holder.mWatchLlyt.setVisibility(View.VISIBLE);
        videoInfo.setStatus(STOP_STATUS);
        String videoThumbUrl = videoInfo.getVideoThumbUrl();
        if(!TextUtils.isEmpty(videoThumbUrl)){
            FrescoUtils.showImgByUrl(videoThumbUrl, holder.mPreviewIv);
        }
        String userPicUrl = videoInfo.getUserPicUrl();
        if(!TextUtils.isEmpty(userPicUrl)){
            FrescoUtils.showImgByUrl(userPicUrl, holder.mUserPicIv);
        }
        String userName = videoInfo.getUserName();
        holder.mUserNameTv.setText(TextUtils.isEmpty(userName) ? "" : userName);
        holder.mCenterPlayIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoInfo.setStatus(LOADING_STATUS);
                VideoFramesModel.getInstance().setCurPlayVideo(videoInfo);
                FullScreenWatchActivity.go(mContext, videoInfo);
            }
        });
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return mVideoData == null ? 0 : mVideoData.size();
    }

    public void exitFullScreenNotify(){
        RelativeLayout videoWindow = VideoFramesModel.getInstance().getCurPlayWindow();
        VideoFrame videoFrame = VideoFramesModel.getInstance().getCurPlayView();
        int curPlayIndex = VideoFramesModel.getInstance().getCurPlayItemIndex();
        if(curPlayIndex == -1){
            return;
        }
        videoWindow.addView(videoFrame, 1, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DrawUtils.dp2px(200)));
        //notifyItemChanged(curPlayIndex);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        private RelativeLayout mRootView;
        private ImageView mCenterPlayIv;
        private SimpleDraweeView mPreviewIv;
        private SimpleDraweeView mUserPicIv;
        private TextView mUserNameTv;
        private TextView mDurationTv;
        private TextView mLikeNumTv;
        private TextView mCommentTv;
        private LinearLayout mWatchLlyt;


        public MyViewHolder(View itemView) {
            super(itemView);
            mRootView = (RelativeLayout) itemView.findViewById(R.id.item_root_view);
            mCenterPlayIv = (ImageView) itemView.findViewById(R.id.item_center_play_iv);
            mPreviewIv = (SimpleDraweeView) itemView.findViewById(R.id.video_preview_iv);
            mUserPicIv = (SimpleDraweeView) itemView.findViewById(R.id.item_user_pic_iv);
            mUserNameTv = (TextView) itemView.findViewById(R.id.item_user_name_tv);
            mDurationTv = (TextView) itemView.findViewById(R.id.item_duration_tv);
            mLikeNumTv = (TextView) itemView.findViewById(R.id.item_like_num_tv);
            mCommentTv = (TextView) itemView.findViewById(R.id.item_comment_num_tv);
            mWatchLlyt = (LinearLayout) itemView.findViewById(R.id.watch_num_llyt);

            mUserNameTv.setBackground(ShapeUtils.getRoundRectDrawable(20, Color.parseColor("#ffd600")));
            mDurationTv.setBackground(ShapeUtils.getRoundRectDrawable(15, Color.parseColor("#66000000")));
            mLikeNumTv.setBackground(ShapeUtils.getRoundRectDrawable(15));
            mCommentTv.setBackground(ShapeUtils.getRoundRectDrawable(15));

        }
    }
}
