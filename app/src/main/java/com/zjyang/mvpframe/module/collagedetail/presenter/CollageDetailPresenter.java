package com.zjyang.mvpframe.module.collagedetail.presenter;

import android.text.TextUtils;

import com.zjyang.base.base.BasePresenter;
import com.zjyang.mvpframe.event.GetMapMarkEvent;
import com.zjyang.mvpframe.module.UserDataManager;
import com.zjyang.mvpframe.module.collagedetail.CollageDetailTasksContract;
import com.zjyang.mvpframe.module.collagedetail.model.CollageDetailModel;
import com.zjyang.mvpframe.module.login.model.bean.User;
import com.zjyang.mvpframe.module.mapmark.MapMarkTasksContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by zhengjiayang on 2018/9/3.
 */

public class CollageDetailPresenter extends BasePresenter<CollageDetailTasksContract.View, CollageDetailTasksContract.Model> implements MapMarkTasksContract.Presenter{

    public CollageDetailPresenter() {
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public CollageDetailModel createModel() {
        return new CollageDetailModel();
    }

    @Override
    public void fillMarkData() {
        User user = UserDataManager.getInstance().getCurUser();
        if(user != null && !TextUtils.isEmpty(user.getObjectId())){
            mModel.getCommentByCollageId(user.getObjectId());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMapMarkEvent(GetMapMarkEvent event){
        if(event.isSuccess()){
            //mView.fillDataToCommentLv(event.getMarkList());
        }
    }

    public void destroy(){
        EventBus.getDefault().unregister(this);
    }
}
