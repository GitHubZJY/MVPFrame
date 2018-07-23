package com.zjyang.mvpframe.module.base;

import com.zjyang.mvpframe.db.dao.UserDBHelper;
import com.zjyang.mvpframe.module.login.model.bean.User;

/**
 * Created by 74215 on 2018/6/2.
 * 当前登陆用户数据管理类
 */

public class UserDataManager {

    private User mCurUser;

    private static UserDataManager mManager = null;

    public static UserDataManager getInstance(){
        if(mManager == null){
            synchronized (UserDataManager.class){
                if(mManager == null){
                    mManager = new UserDataManager();
                }
            }
        }
        return mManager;
    }

    public User getCurUser() {
        if(mCurUser == null){
            mCurUser = UserDBHelper.getInstance().queryUser();
        }
        return mCurUser;
    }

    public synchronized void setCurUser(User mCurUser) {
        UserDBHelper.getInstance().insertUser(mCurUser);
        this.mCurUser = mCurUser;
    }
}
