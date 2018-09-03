package com.zjyang.mvpframe.event;

import com.zjyang.mvpframe.module.mapmark.model.bean.MapMark;

import java.util.List;

/**
 * Created by zhengjiayang on 2018/9/3.
 */

public class GetMapMarkEvent {

    private boolean isSuccess;
    private List<MapMark> markList;

    public GetMapMarkEvent(boolean isSuccess, List<MapMark> markList) {
        this.isSuccess = isSuccess;
        this.markList = markList;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<MapMark> getMarkList() {
        return markList;
    }

    public void setMarkList(List<MapMark> markList) {
        this.markList = markList;
    }
}
