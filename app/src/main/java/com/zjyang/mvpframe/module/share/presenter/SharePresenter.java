package com.zjyang.mvpframe.module.share.presenter;

import com.zjyang.mvpframe.module.share.ShareTaskContracts;
import com.zjyang.mvpframe.module.share.model.ShareModel;

/**
 * Created by 74215 on 2018/5/26.
 */

public class SharePresenter implements ShareTaskContracts.Presenter{

    private ShareTaskContracts.View mShareView;
    private ShareTaskContracts.Model mShareModel;

    public SharePresenter(ShareTaskContracts.View mShareView) {
        this.mShareView = mShareView;
        mShareModel = new ShareModel();
    }

    @Override
    public void shareVideo(String videoPath) {
        if(mShareModel != null){
            mShareModel.uploadVideoFile(videoPath);
        }
    }
}
