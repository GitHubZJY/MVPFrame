package com.zjyang.mvpframe.module.home.tripcircle.presenter;

import com.zjyang.mvpframe.module.base.BasePresenter;
import com.zjyang.mvpframe.module.home.tripcircle.VideoDetailTasksContract;
import com.zjyang.mvpframe.module.home.tripcircle.model.VideoDetailModel;

/**
 * Created by zhengjiayang on 2018/8/24.
 */

public class VideoDetailPresenter extends BasePresenter<VideoDetailTasksContract.View, VideoDetailModel>{


    @Override
    public VideoDetailModel createModel() {
        return new VideoDetailModel();
    }
}
