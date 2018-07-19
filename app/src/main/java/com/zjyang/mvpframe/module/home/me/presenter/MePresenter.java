package com.zjyang.mvpframe.module.home.me.presenter;

import com.zjyang.mvpframe.module.home.me.MeTasksContract;
import com.zjyang.mvpframe.module.home.me.model.MeModel;
import com.zjyang.mvpframe.module.login.model.bean.User;

/**
 * Created by 74215 on 2018/7/18.
 */

public class MePresenter implements MeTasksContract.Presenter{

    private MeTasksContract.View mView;
    private MeTasksContract.Model mModel;

    public MePresenter(MeTasksContract.View mView) {
        this.mView = mView;
        mModel = new MeModel();
    }

    @Override
    public void fillUserDataToView() {
        if(mModel == null || mView == null){
            return;
        }
        User curUser = mModel.getCacheUserData();
        mView.initUserDataView(curUser);
    }
}
