package com.zjyang.mvpframe.db.dao;

import android.util.Log;

import com.zjyang.mvpframe.application.AppApplication;
import com.zjyang.mvpframe.db.base.DBUserDao;
import com.zjyang.mvpframe.db.base.DaoSession;
import com.zjyang.mvpframe.db.bean.DBUser;
import com.zjyang.mvpframe.module.login.model.bean.User;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by 74215 on 2018/6/2.
 */

public class UserDBHelper {

    private static UserDBHelper mInstance;


    public static UserDBHelper getInstance(){
        if(mInstance == null){
            mInstance = new UserDBHelper();
        }
        return mInstance;
    }

    public void insertUser(User user){
        try{
            DaoSession daoSession = AppApplication.getDaoSession();
            DBUser dbUser = user.switchDBUser(user);
            daoSession.getDBUserDao().insert(dbUser);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteUser(User user){
        try{
            DaoSession daoSession = AppApplication.getDaoSession();
            //DBUser dbUser = user.switchDBUser(user);
            daoSession.getDBUserDao().deleteAll();
        }catch (Exception e){
            Log.d("zjy", e.toString());
            e.printStackTrace();
        }
    }

    public User queryUser(){
        User user = null;
        try{
            DaoSession daoSession = AppApplication.getDaoSession();
            DBUserDao userDao = daoSession.getDBUserDao();
            QueryBuilder<DBUser> qb = userDao.queryBuilder();
            List<DBUser> list = qb.list();
            if(list != null && list.size() > 0){
                DBUser dbUser = list.get(0);
                user = dbUser.switchUser(dbUser);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
