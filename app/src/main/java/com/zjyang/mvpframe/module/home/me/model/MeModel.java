package com.zjyang.mvpframe.module.home.me.model;

import com.zjyang.mvpframe.module.base.UserDataManager;
import com.zjyang.mvpframe.module.home.me.MeTasksContract;
import com.zjyang.mvpframe.module.login.model.bean.User;

/**
 * Created by 74215 on 2018/7/18.
 */

public class MeModel implements MeTasksContract.Model{

    @Override
    public User getCacheUserData() {
        return UserDataManager.getInstance().getCurUser();
    }
}
