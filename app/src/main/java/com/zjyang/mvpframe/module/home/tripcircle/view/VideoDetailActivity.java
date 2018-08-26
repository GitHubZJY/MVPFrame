package com.zjyang.mvpframe.module.home.tripcircle.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import com.example.zjy.player.ui.VideoFrame;
import com.example.zjy.player.ui.YPlayerView;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.base.BaseActivity;
import com.zjyang.mvpframe.module.base.BasePresenter;
import com.zjyang.mvpframe.module.home.tripcircle.adapter.CommentListAdapter;
import com.zjyang.mvpframe.module.home.tripcircle.model.bean.CommentInfo;
import com.zjyang.mvpframe.module.home.tripcircle.presenter.VideoDetailPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhengjiayang on 2018/8/24.
 */

public class VideoDetailActivity extends BaseActivity{

    private Unbinder unbinder;

    @BindView(R.id.video_frame)
    YPlayerView mVideoFrame;
    @BindView(R.id.comment_lv)
    RecyclerView mCommentLv;

    CommentListAdapter mCommentAdapter;

    @Override
    public BasePresenter createPresenter() {
        return new VideoDetailPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);// 允许使用transitions
        setContentView(R.layout.activity_video_detail);
        unbinder = ButterKnife.bind(this);

        mVideoFrame.setVideoUrl("http://bmob-cdn-18798.b0.upaiyun.com/2018/08/26/c3e45865408a5b10807865469083b339.mp4");
        mVideoFrame.start();

        List<CommentInfo> commentInfoList = new ArrayList<>();

        for(int i=0; i<5; i++){
            CommentInfo commentInfo = new CommentInfo();
            commentInfoList.add(commentInfo);
        }

        mCommentAdapter = new CommentListAdapter(commentInfoList, this);
        mCommentLv.setLayoutManager(new LinearLayoutManager(this));
        mCommentLv.setAdapter(mCommentAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoFrame.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoFrame.stopPlayback();
        if(unbinder != null){
            unbinder.unbind();
        }
    }
}
