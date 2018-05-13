package com.zjyang.mvpframe.module.home.presenter;

import com.zjyang.mvpframe.event.GetHomeTabInfoEvent;
import com.zjyang.mvpframe.module.base.BaseFragment;
import com.zjyang.mvpframe.module.home.HomeTasksContract;
import com.zjyang.mvpframe.module.home.model.HomeModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by 74215 on 2018/5/12.
 */

public class HomePresenter implements HomeTasksContract.Presenter{

    private HomeTasksContract.View mHomeView;
    private HomeTasksContract.Model mHomeModel;

    public HomePresenter(HomeTasksContract.View mLoginView) {
        this.mHomeView = mLoginView;
        mHomeModel = new HomeModel();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public List<BaseFragment> getChildPages() {
        if(mHomeModel != null){
            return mHomeModel.getFragments();
        }
        return null;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GetHomeTabInfoEvent event){
        //获取tab下发配置成功
        mHomeView.resetFragments();
    }

    @Override
    public void destroy(){
        EventBus.getDefault().unregister(this);
    }
}
