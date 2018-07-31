package com.zjyang.mvpframe.module.share;

/**
 * Created by 74215 on 2018/5/13.
 */

public interface ShareTaskContracts {

    interface View {
        void showUpLoadSuccess();
        void showUpLoadFail();
        void showLocationData(String address);
        void showProgressDialog();
        void dismissProgressDialog();
    }

    interface Model {
        void uploadVideoFile(String videoPath);
        void setLocationData(String address, int id);
    }

    interface Presenter {
        void destroy();
        void shareVideo(String videoPath);
        void startLocation();
    }
}
