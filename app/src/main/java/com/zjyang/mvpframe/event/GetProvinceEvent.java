package com.zjyang.mvpframe.event;

import com.zjyang.mvpframe.module.home.discover.model.bean.Province;

import java.util.List;

/**
 * Created by 74215 on 2018/7/28.
 */

public class GetProvinceEvent {

    boolean mIsSuccess;
    List<Province> mProvinceList;

    public GetProvinceEvent(boolean mIsSuccess, List<Province> mProvinceList) {
        this.mProvinceList = mProvinceList;
        this.mIsSuccess = mIsSuccess;
    }

    public boolean isSuccess() {
        return mIsSuccess;
    }

    public void setIsSuccess(boolean mIsSuccess) {
        this.mIsSuccess = mIsSuccess;
    }

    public List<Province> getProvinceList() {
        return mProvinceList;
    }

    public void setProvinceList(List<Province> mProvinceList) {
        this.mProvinceList = mProvinceList;
    }
}
