package com.zjyang.mvpframe.module.share.presenter;

import com.zjyang.mvpframe.event.ShareResultEvent;
import com.zjyang.mvpframe.module.share.ShareTaskContracts;
import com.zjyang.mvpframe.module.share.model.ShareModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by 74215 on 2018/5/26.
 */

public class SharePresenter implements ShareTaskContracts.Presenter{

    private ShareTaskContracts.View mShareView;
    private ShareTaskContracts.Model mShareModel;

    public SharePresenter(ShareTaskContracts.View mShareView) {
        this.mShareView = mShareView;
        mShareModel = new ShareModel();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void shareVideo(String videoPath) {
        if(mShareModel != null){
            mShareModel.uploadVideoFile(videoPath);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ShareResultEvent event){
        if(mShareView == null){
            return;
        }
        if(event.isSuccess()){
            mShareView.showUpLoadSuccess();
        }else{
            mShareView.showUpLoadFail();
        }
    }

    @Override
    public void destroy(){
        EventBus.getDefault().unregister(this);
    }
}
