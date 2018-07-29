package com.zjyang.mvpframe.event;

import com.zjyang.mvpframe.module.home.model.bean.VideoInfo;

import java.util.List;

/**
 * Created by 74215 on 2018/4/24.
 * 请求视频列表数据结果
 */

public class RequestVideoListEvent {

    private boolean mIsSuccess;
    private int mProvinceId;
    private List<VideoInfo> videoInfoList;

    public RequestVideoListEvent(boolean mIsSuccess, int provinceId, List<VideoInfo> videoInfoList) {
        this.mIsSuccess = mIsSuccess;
        this.mProvinceId = provinceId;
        this.videoInfoList = videoInfoList;
    }

    public int getProvinceId() {
        return mProvinceId;
    }

    public void setProvinceId(int mProvinceId) {
        this.mProvinceId = mProvinceId;
    }

    public boolean ismIsSuccess() {
        return mIsSuccess;
    }

    public void setmIsSuccess(boolean mIsSuccess) {
        this.mIsSuccess = mIsSuccess;
    }

    public List<VideoInfo> getVideoInfoList() {
        return videoInfoList;
    }

    public void setVideoInfoList(List<VideoInfo> videoInfoList) {
        this.videoInfoList = videoInfoList;
    }
}
