package com.zjyang.mvpframe.module.home.discover.presenter;

import com.zjyang.mvpframe.event.GetProvinceEvent;
import com.zjyang.mvpframe.module.home.discover.DiscoverTasksContract;
import com.zjyang.mvpframe.module.home.discover.model.DiscoverModel;
import com.zjyang.mvpframe.module.home.discover.model.bean.Province;
import com.zjyang.mvpframe.module.home.model.bean.VideoInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by 74215 on 2018/4/21.
 */

public class DiscoverPresenter implements DiscoverTasksContract.Presenter{

    private DiscoverTasksContract.View.BaseDiscoverView mBaseView;
    private DiscoverTasksContract.View.ItemDiscoverView mItemView;
    private DiscoverTasksContract.Model mDiscoverModel;

    public DiscoverPresenter(DiscoverTasksContract.View.BaseDiscoverView mBaseView) {
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        this.mBaseView = mBaseView;
        mDiscoverModel = new DiscoverModel();
    }

    public DiscoverPresenter(DiscoverTasksContract.View.ItemDiscoverView mItemView) {
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        this.mItemView = mItemView;
        mDiscoverModel = new DiscoverModel();
    }

    @Override
    public void toggleProvince(int index){
        mDiscoverModel.getVideoDataByProvinceId(index);
        mDiscoverModel.setCurSelectTabId(index);
    }

    @Override
    public void initProvinceTabData(){
        mDiscoverModel.queryAllProvince();
    }

    @Override
    public void initDataBeforeRequest(){
        List<VideoInfo> defaultData = mDiscoverModel.getDefaultVideoData();
        int curTabId = mDiscoverModel.getCurSelectTabId();
        for(VideoInfo videoInfo : defaultData){
            if(videoInfo.getProvinceId() == curTabId){
                if(mItemView != null){
                    mItemView.fillDataToList(defaultData);
                }
                break;
            }
        }
        List<Province> defaultProvinceData = mDiscoverModel.getDefaultProvinceData();
        if(mBaseView != null){
            mBaseView.initProvinceFragment(defaultProvinceData);
        }
    }

    @Override
    public void refreshList(){
        int curIndex = mDiscoverModel.getCurSelectTabId();
        mDiscoverModel.getVideoDataByProvinceId(curIndex);
        mDiscoverModel.setCurSelectTabId(curIndex);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProvinceEvent(GetProvinceEvent event){
        if(event.isSuccess()){
            //成功才更新tab数据
            if(mBaseView != null){
                mBaseView.refreshTabData(event.getProvinceList());
            }
        }
    }

    @Override
    public void destroy(){
        EventBus.getDefault().unregister(this);
    }
}
