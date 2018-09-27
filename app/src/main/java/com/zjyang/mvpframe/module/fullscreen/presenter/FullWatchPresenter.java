package com.zjyang.mvpframe.module.fullscreen.presenter;

import com.zjyang.base.base.BasePresenter;
import com.zjyang.mvpframe.event.FocusResultEvent;
import com.zjyang.mvpframe.module.UserDataManager;
import com.zjyang.mvpframe.module.fullscreen.FullWatchTasksContract;
import com.zjyang.mvpframe.module.fullscreen.model.FullWatchModel;
import com.zjyang.mvpframe.module.login.model.bean.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by 74215 on 2018/9/4.
 */

public class FullWatchPresenter extends BasePresenter<FullWatchTasksContract.View, FullWatchTasksContract.Model> implements FullWatchTasksContract.Presenter{
    public FullWatchPresenter() {
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public FullWatchTasksContract.Model createModel() {
        return new FullWatchModel();
    }

    @Override
    public void focusVideoAuthor(String authorUserId) {
        User curUser = UserDataManager.getInstance().getCurUser();
        if(curUser != null){
            mModel.insertFocusData(curUser.getObjectId(), authorUserId);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFocusResultEvent(FocusResultEvent event){
        if(event.isSuccess()){
            mView.focusSuccess();
        }
    }

    public void destroy(){
        EventBus.getDefault().unregister(this);
    }
}
