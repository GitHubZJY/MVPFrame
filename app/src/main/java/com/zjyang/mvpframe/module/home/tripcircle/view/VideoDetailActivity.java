package com.zjyang.mvpframe.module.home.tripcircle.view;

import com.zjyang.mvpframe.module.base.BaseActivity;
import com.zjyang.mvpframe.module.base.BasePresenter;
import com.zjyang.mvpframe.module.home.tripcircle.presenter.VideoDetailPresenter;

/**
 * Created by zhengjiayang on 2018/8/24.
 */

public class VideoDetailActivity extends BaseActivity{

    @Override
    public BasePresenter createPresenter() {
        return new VideoDetailPresenter();
    }
}
