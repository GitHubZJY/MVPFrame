package com.zjyang.mvpframe.module.home.discover.model;

import android.widget.RelativeLayout;

import com.example.zjy.player.ui.VideoFrame;

/**
 * Created by 74215 on 2018/4/15.
 * 管理当前播放的View对象的管理类
 */

public class VideoFramesModel {

    //保存当前正在播放的View
    private VideoFrame mCurPlayView;
    //当前正在播放的View的父容器
    private RelativeLayout mCurPlayWindow;
    //保存当前正在播放的Item下标
    private int mCurPlayItemIndex = -1;

    private static VideoFramesModel mInstance;

    public static VideoFramesModel getInstance(){
        if(mInstance == null){
            mInstance = new VideoFramesModel();
        }
        return mInstance;
    }

    public int getCurPlayItemIndex() {
        return mCurPlayItemIndex;
    }

    public void setCurPlayItemIndex(int mCurPlayItemIndex) {
        this.mCurPlayItemIndex = mCurPlayItemIndex;
    }

    public VideoFrame getCurPlayView() {
        return mCurPlayView;
    }

    public void setCurPlayView(VideoFrame mCurPlayView) {
        this.mCurPlayView = mCurPlayView;
    }

    public RelativeLayout getCurPlayWindow() {
        return mCurPlayWindow;
    }

    public void setCurPlayWindow(RelativeLayout mCurPlayWindow) {
        this.mCurPlayWindow = mCurPlayWindow;
    }
}
