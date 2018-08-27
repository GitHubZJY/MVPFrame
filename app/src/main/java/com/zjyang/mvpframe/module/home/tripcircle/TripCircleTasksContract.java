package com.zjyang.mvpframe.module.home.tripcircle;

import com.zjyang.mvpframe.module.home.tripcircle.model.bean.WonderfulVideo;

import java.util.List;

/**
 * Created by zhengjiayang on 2018/7/17.
 */

public interface TripCircleTasksContract {

    interface View {
        void initBannerView(List<String> data);
        void notifyWonderfulLv(List<WonderfulVideo> wonderfulVideos);
    }


    interface Model {
        List<String> getBannerData();
        void getAllWonderfulVideo();
    }

    interface Presenter {
        void initTripCircleData();
        void destroy();
    }
}
