package com.zjyang.mvpframe.event;

/**
 * Created by 74215 on 2018/5/26.
 */

public class ShareResultEvent {

    private boolean mIsSuccess;

    public ShareResultEvent(boolean mIsSuccess) {
        this.mIsSuccess = mIsSuccess;
    }

    public boolean isSuccess() {
        return mIsSuccess;
    }

    public void setIsSuccess(boolean mIsSuccess) {
        this.mIsSuccess = mIsSuccess;
    }
}
