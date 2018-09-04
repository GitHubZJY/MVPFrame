package com.zjyang.mvpframe.event;

/**
 * Created by 74215 on 2018/9/5.
 */

public class FocusResultEvent {


    private boolean isSuccess;

    public FocusResultEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

}
