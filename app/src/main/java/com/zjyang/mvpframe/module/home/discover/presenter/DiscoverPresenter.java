package com.zjyang.mvpframe.module.home.discover.presenter;

import com.zjyang.mvpframe.module.home.discover.DiscoverTasksContract;
import com.zjyang.mvpframe.module.home.discover.model.DiscoverModel;

/**
 * Created by 74215 on 2018/4/21.
 */

public class DiscoverPresenter implements DiscoverTasksContract.Presenter{


    private DiscoverTasksContract.View mLoginView;
    private DiscoverTasksContract.Model mLoginModel;

    public DiscoverPresenter(DiscoverTasksContract.View mLoginView) {
        this.mLoginView = mLoginView;
        mLoginModel = new DiscoverModel();
    }

    @Override
    public void toggleProvince(int index){
        mLoginView.toggleTopTab(index);
        mLoginModel.getVideoDataByProvinceId(index);
    }
}
