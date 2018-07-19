package com.zjyang.mvpframe.module.home.me;

import com.zjyang.mvpframe.module.login.model.bean.User;

/**
 * Created by zhengjiayang on 2018/7/17.
 */

public interface MeTasksContract {

    interface View {
        void initUserDataView(User user);
    }


    interface Model {
        //获取当前缓存的用户信息
        User getCacheUserData();
    }

    interface Presenter {
        void fillUserDataToView();
    }
}
