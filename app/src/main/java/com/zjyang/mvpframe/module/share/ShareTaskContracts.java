package com.zjyang.mvpframe.module.share;

/**
 * Created by 74215 on 2018/5/13.
 */

public interface ShareTaskContracts {

    interface View {

    }

    interface Model {
        void uploadVideoFile(String videoPath);
    }

    interface Presenter {
        void shareVideo(String videoPath);
    }
}
