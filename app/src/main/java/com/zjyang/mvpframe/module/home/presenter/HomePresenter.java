package com.zjyang.mvpframe.module.home.presenter;

import com.zjyang.base.base.BasePresenter;
import com.zjyang.mvpframe.event.GetHomeTabInfoEvent;
import com.zjyang.mvpframe.module.BaseFragment;
import com.zjyang.mvpframe.module.home.HomeTasksContract;
import com.zjyang.mvpframe.module.home.model.HomeModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by 74215 on 2018/5/12.
 */

public class HomePresenter extends BasePresenter<HomeTasksContract.View, HomeModel> implements HomeTasksContract.Presenter{

    public HomePresenter() {
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public HomeModel createModel() {
        return new HomeModel();
    }

    @Override
    public List<BaseFragment> getChildPages() {
        if(mModel != null){
            return mModel.getFragments();
        }
        return null;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GetHomeTabInfoEvent event){
        //获取tab下发配置成功
        mView.resetFragments();
    }

    @Override
    public void destroy(){
        EventBus.getDefault().unregister(this);
    }
}
