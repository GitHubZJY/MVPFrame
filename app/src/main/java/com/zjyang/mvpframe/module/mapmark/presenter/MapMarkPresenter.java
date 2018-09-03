package com.zjyang.mvpframe.module.mapmark.presenter;

import com.zjyang.mvpframe.event.GetMapMarkEvent;
import com.zjyang.mvpframe.module.base.BasePresenter;
import com.zjyang.mvpframe.module.base.UserDataManager;
import com.zjyang.mvpframe.module.mapmark.MapMarkTasksContract;
import com.zjyang.mvpframe.module.mapmark.model.MapMarkModel;
import com.zjyang.mvpframe.module.mapmark.view.MapMarkActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by zhengjiayang on 2018/9/3.
 */

public class MapMarkPresenter extends BasePresenter<MapMarkActivity, MapMarkModel> implements MapMarkTasksContract.Presenter{

    public MapMarkPresenter() {
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public MapMarkModel createModel() {
        return new MapMarkModel();
    }

    @Override
    public void fillMarkData() {
        mModel.getMarkDataByUserId(1);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMapMarkEvent(GetMapMarkEvent event){
        if(event.isSuccess()){
            mView.setMarkDataInMap(event.getMarkList());
        }
    }

    public void destroy(){
        EventBus.getDefault().unregister(this);
    }
}
