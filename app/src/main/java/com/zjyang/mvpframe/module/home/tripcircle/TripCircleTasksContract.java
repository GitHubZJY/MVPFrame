package com.zjyang.mvpframe.module.home.tripcircle;

import java.util.List;

/**
 * Created by zhengjiayang on 2018/7/17.
 */

public interface TripCircleTasksContract {

    interface View {
        void initBannerView(List<String> data);
    }


    interface Model {
        List<String> getBannerData();
    }

    interface Presenter {
        void initTripCircleData();
    }
}
