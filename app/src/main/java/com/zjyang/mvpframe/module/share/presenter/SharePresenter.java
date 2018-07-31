package com.zjyang.mvpframe.module.share.presenter;

import android.text.TextUtils;

import com.zjyang.mvpframe.event.ShareResultEvent;
import com.zjyang.mvpframe.module.base.BasePresenter;
import com.zjyang.mvpframe.module.share.ShareTaskContracts;
import com.zjyang.mvpframe.module.share.model.ShareModel;
import com.zjyang.mvpframe.utils.LocationUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by 74215 on 2018/5/26.
 */

public class SharePresenter extends BasePresenter<ShareTaskContracts.View, ShareModel> implements ShareTaskContracts.Presenter{


    public SharePresenter() {
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public ShareModel createModel() {
        return new ShareModel();
    }

    @Override
    public void shareVideo(String videoPath) {
        if(mView != null){
            mView.showProgressDialog();
        }
        if(mModel != null){
            mModel.uploadVideoFile(videoPath);
        }
    }

    @Override
    public void startLocation() {
        LocationUtils.getInstance().startLocation(new LocationUtils.LocationCallback() {
            @Override
            public void getAddress(String address, int cityCode) {
                LocationUtils.getInstance().stopLocation();
                if(!TextUtils.isEmpty(address)){
                    mView.showLocationData(address);
                    mModel.setLocationData(address, cityCode);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ShareResultEvent event){
        if(mView == null){
            return;
        }
        mView.dismissProgressDialog();
        if(event.isSuccess()){
            mView.showUpLoadSuccess();
        }else{
            mView.showUpLoadFail();
        }
    }

    @Override
    public void destroy(){
        EventBus.getDefault().unregister(this);
    }
}
