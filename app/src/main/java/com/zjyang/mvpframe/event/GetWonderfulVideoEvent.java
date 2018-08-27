package com.zjyang.mvpframe.event;

import com.zjyang.mvpframe.module.home.discover.model.bean.Province;
import com.zjyang.mvpframe.module.home.tripcircle.model.bean.WonderfulVideo;

import java.util.List;

/**
 * Created by 74215 on 2018/7/28.
 */

public class GetWonderfulVideoEvent {

    boolean mIsSuccess;
    List<WonderfulVideo> mWonderfulList;

    public GetWonderfulVideoEvent(boolean mIsSuccess, List<WonderfulVideo> mWonderfulList) {
        this.mWonderfulList = mWonderfulList;
        this.mIsSuccess = mIsSuccess;
    }

    public boolean isSuccess() {
        return mIsSuccess;
    }

    public void setIsSuccess(boolean mIsSuccess) {
        this.mIsSuccess = mIsSuccess;
    }

    public List<WonderfulVideo> getWonderfulList() {
        return mWonderfulList;
    }

    public void setWonderfulList(List<WonderfulVideo> mProvinceList) {
        this.mWonderfulList = mProvinceList;
    }
}
