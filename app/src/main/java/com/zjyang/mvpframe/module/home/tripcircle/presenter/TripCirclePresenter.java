package com.zjyang.mvpframe.module.home.tripcircle.presenter;

import com.zjyang.mvpframe.module.home.tripcircle.TripCircleTasksContract;
import com.zjyang.mvpframe.module.home.tripcircle.model.TripCircleModel;

/**
 * Created by zhengjiayang on 2018/8/23.
 */

public class TripCirclePresenter implements TripCircleTasksContract.Presenter {

    private TripCircleTasksContract.View mView;
    private TripCircleTasksContract.Model mModel;

    public TripCirclePresenter(TripCircleTasksContract.View mView) {
        this.mView = mView;
        mModel = new TripCircleModel();
    }

    @Override
    public void initTripCircleData(){
        if(mModel == null || mView == null){
            return;
        }
        mView.initBannerView(mModel.getBannerData());
    }
}
