package com.zjyang.mvpframe.event;

import com.zjyang.mvpframe.module.home.model.bean.VideoInfo;

import java.util.List;

/**
 * Created by zhengjiayang on 2018/7/19.
 */

public class RequestMyVideoListEvent {

    private boolean mIsSuccess;
    private List<VideoInfo> mVideoList;

    public RequestMyVideoListEvent(boolean mIsSuccess, List<VideoInfo> mVideoList) {
        this.mIsSuccess = mIsSuccess;
        this.mVideoList = mVideoList;
    }

    public boolean isSuccess() {
        return mIsSuccess;
    }

    public void setIsSuccess(boolean mIsSuccess) {
        this.mIsSuccess = mIsSuccess;
    }

    public List<VideoInfo> getVideoList() {
        return mVideoList;
    }

    public void setVideoList(List<VideoInfo> mVideoList) {
        this.mVideoList = mVideoList;
    }
}
