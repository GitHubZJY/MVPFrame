package com.zjyang.mvpframe.application;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.zjyang.mvpframe.db.DBConfig;
import com.zjyang.mvpframe.db.base.DaoMaster;
import com.zjyang.mvpframe.db.base.DaoSession;
import com.zjyang.mvpframe.utils.Constants;

import org.greenrobot.greendao.database.Database;

import cn.bmob.v3.Bmob;

/**
 * Created by zhengjiayang on 2018/3/1.
 */

public class AppApplication extends Application{

    private static Context mContext;
    private static DaoSession mDaoSession;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initBombSDK();
        initFresco();
        initDB();
    }

    public void initBombSDK(){
        Bmob.initialize(this, Constants.BMOB_APP_KEY);
        // 注:自v3.5.2开始，数据sdk内部缝合了统计sdk，开发者无需额外集成，传渠道参数即可，不传默认没开启数据统计功能
        //Bmob.initialize(this, "Your Application ID","bmob");
    }

    public void initFresco(){
        Fresco.initialize(this);
    }

    public static Context getContext(){
        return mContext;
    }

    public void initDB(){
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, DBConfig.DATA_BASE_NAME);
        Database database = openHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(database);
        mDaoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession(){
        return mDaoSession;
    }
}
