package com.zjyang.mvpframe.module.home.discover.presenter;

import com.zjyang.mvpframe.module.home.discover.DiscoverTasksContract;
import com.zjyang.mvpframe.module.home.discover.model.DiscoverModel;
import com.zjyang.mvpframe.module.home.model.bean.VideoInfo;

import java.util.List;

/**
 * Created by 74215 on 2018/4/21.
 */

public class DiscoverPresenter implements DiscoverTasksContract.Presenter{


    private DiscoverTasksContract.View mDiscoverView;
    private DiscoverTasksContract.Model mDiscoverModel;

    public DiscoverPresenter(DiscoverTasksContract.View mLoginView) {
        this.mDiscoverView = mLoginView;
        mDiscoverModel = new DiscoverModel();
    }

    @Override
    public void toggleProvince(int index){
        mDiscoverView.toggleTopTab(index);
        mDiscoverModel.getVideoDataByProvinceId(index);
        mDiscoverModel.setCurSelectTabIndex(index);
    }

    @Override
    public void initDataBeforeRequest(){
        List<VideoInfo> defaultData = mDiscoverModel.getDefaultProvinceData();
        mDiscoverView.fillDataToList(defaultData);
    }

    @Override
    public void refreshList(){
        int curIndex = mDiscoverModel.getCurSelectTabIndex();
        mDiscoverModel.getVideoDataByProvinceId(curIndex);
        mDiscoverModel.setCurSelectTabIndex(curIndex);
    }
}
