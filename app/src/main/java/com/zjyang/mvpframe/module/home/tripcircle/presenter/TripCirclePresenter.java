package com.zjyang.mvpframe.module.home.tripcircle.presenter;

import com.zjyang.mvpframe.event.GetWonderfulVideoEvent;
import com.zjyang.mvpframe.module.home.tripcircle.TripCircleTasksContract;
import com.zjyang.mvpframe.module.home.tripcircle.model.TripCircleModel;
import com.zjyang.mvpframe.module.home.tripcircle.model.bean.WonderfulVideo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by zhengjiayang on 2018/8/23.
 */

public class TripCirclePresenter implements TripCircleTasksContract.Presenter {

    private TripCircleTasksContract.View mView;
    private TripCircleTasksContract.Model mModel;

    public TripCirclePresenter(TripCircleTasksContract.View mView) {
        this.mView = mView;
        mModel = new TripCircleModel();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void initTripCircleData(){
        if(mModel == null || mView == null){
            return;
        }
        mModel.getAllWonderfulVideo();
        mView.initBannerView(mModel.getBannerData());
        mView.initTripWebListView(mModel.getTripWebData());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getWonderfulEvent(GetWonderfulVideoEvent event){
        boolean isSuccess = event.isSuccess();
        if(isSuccess){
            List<WonderfulVideo> wonderfulVideos = event.getWonderfulList();
            mView.notifyWonderfulLv(wonderfulVideos);
        }
    }

    @Override
    public void destroy(){
        EventBus.getDefault().unregister(this);
    }
}
