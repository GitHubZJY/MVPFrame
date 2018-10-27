package com.zjyang.mvpframe.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zjyang.base.BaseApp;
import com.zjyang.mvpframe.db.DBConfig;
import com.zjyang.mvpframe.db.base.DaoMaster;
import com.zjyang.mvpframe.db.base.DaoSession;
import com.zjyang.mvpframe.net.RequestApi;
import com.zjyang.mvpframe.utils.Constants;
import com.zjyang.base.utils.HandlerUtils;
import com.zjyang.base.utils.LogUtil;
import com.zjyang.base.utils.ScreenUtils;

import org.greenrobot.greendao.database.Database;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tv.danmaku.ijk.media.player.PlugInSoHelper;

/**
 * Created by zhengjiayang on 2018/3/1.
 */

public class AppApplication extends Application{

    public static final String TAG = "AppApplication";

    private static Context mContext;
    private static DaoSession mDaoSession;
    private static Gson sGson;
    private static RequestApi sRequestApi;

    @Override
    public void onCreate() {
        super.onCreate();
        long startAppTime = System.currentTimeMillis();
        mContext = this;
        BaseApp.init(this);
        ScreenUtils.init(this);
        if(isMainProcess()){

        }
        initInMainProcess();
        //复制加载ijk so库
        new PlugInSoHelper(this).run();

        initQuPaiSDK();
        
        LogUtil.e(TAG, "Application start time--->" + (System.currentTimeMillis() - startAppTime));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    private void initInMainProcess(){
        LogUtil.e(TAG, "主进程启动初始化操作");
        HandlerUtils.postThread(new Runnable() {
            @Override
            public void run() {
                //放到子线程里提高启动速度
                initBombSDK();
            }
        });
        initDB();
    }

    public static Gson getGson() {
        if (sGson == null) {
            synchronized (new Object()) {
                if (sGson == null) {
                    sGson = new GsonBuilder().setPrettyPrinting()
                            .disableHtmlEscaping()
                            .serializeSpecialFloatingPointValues()
                            .create();
                }
            }
        }
        return sGson;
    }

    public static RequestApi getRequestApi(){
        if (sRequestApi == null) {
            synchronized (new Object()) {
                if (sRequestApi == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .baseUrl(RequestApi.BASE_URL).build();
                    sRequestApi = retrofit.create(RequestApi.class);
                }
            }
        }

        return sRequestApi;
    }

    public void initQuPaiSDK(){
        com.aliyun.common.httpfinal.QupaiHttpFinal.getInstance().initOkHttpFinal();
        com.aliyun.vod.common.httpfinal.QupaiHttpFinal.getInstance().initOkHttpFinal();
    }

    public void initBombSDK(){
        //Bmob.initialize(this, Constants.BMOB_APP_KEY);
        // 注:自v3.5.2开始，数据sdk内部缝合了统计sdk，开发者无需额外集成，传渠道参数即可，不传默认没开启数据统计功能
        //Bmob.initialize(this, "Your Application ID","bmob");
        BmobConfig config = new BmobConfig.Builder(this)
                .setApplicationId(Constants.BMOB_APP_KEY)//设置appkey
                .setConnectTimeout(1000).build();
        Bmob.initialize(config);
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

    public boolean isMainProcess(){
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningAppProcessInfo processInfo : activityManager.getRunningAppProcesses()){
            if(processInfo.pid == pid){
                processName = processInfo.processName;
            }
        }
        if(!TextUtils.isEmpty(processName) && processName.equals(getPackageName())){
            return true;
        }
        return false;
    }
}
