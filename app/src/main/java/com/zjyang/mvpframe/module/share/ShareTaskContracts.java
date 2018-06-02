package com.zjyang.mvpframe.module.share;

/**
 * Created by 74215 on 2018/5/13.
 */

public interface ShareTaskContracts {

    interface View {
        void showUpLoadSuccess();
        void showUpLoadFail();
    }

    interface Model {
        void uploadVideoFile(String videoPath);
    }

    interface Presenter {
        void destroy();
        void shareVideo(String videoPath);
    }
}
