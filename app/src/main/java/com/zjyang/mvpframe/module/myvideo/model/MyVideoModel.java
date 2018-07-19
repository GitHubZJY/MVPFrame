package com.zjyang.mvpframe.module.myvideo.model;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.zjyang.mvpframe.application.AppApplication;
import com.zjyang.mvpframe.constant.SpConstant;
import com.zjyang.mvpframe.event.RequestMyVideoListEvent;
import com.zjyang.mvpframe.event.RequestVideoListEvent;
import com.zjyang.mvpframe.module.base.UserDataManager;
import com.zjyang.mvpframe.module.home.model.bean.VideoInfo;
import com.zjyang.mvpframe.module.login.model.bean.User;
import com.zjyang.mvpframe.module.myvideo.MyVideoTasksContract;
import com.zjyang.mvpframe.utils.SpUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by zhengjiayang on 2018/7/19.
 */

public class MyVideoModel implements MyVideoTasksContract.Model{


    public void saveNewDataToCache(List<VideoInfo> newVideoList){
        SpUtils.obtain().save(SpConstant.CUR_USER_VIDEO_DATA, AppApplication.getGson().toJson(newVideoList));
    }

    @Override
    public List<VideoInfo> getSpVideoData() {
        String spString = SpUtils.obtain().getString(SpConstant.CUR_USER_VIDEO_DATA, null);
        if (TextUtils.isEmpty(spString)) {
            return null;
        }
        try {
            return AppApplication.getGson().fromJson(spString, new TypeToken<List<VideoInfo>>() {
            }.getType());
        }catch (Exception e){
            SpUtils.obtain().save(SpConstant.CUR_USER_VIDEO_DATA, "");
            return null;
        }
    }

    @Override
    public void getCurUserVideoData() {
        User curUser = UserDataManager.getInstance().getCurUser();
        if(curUser == null){
            return;
        }
        BmobQuery<VideoInfo> bmobQuery = new BmobQuery<VideoInfo>();
        bmobQuery.addWhereEqualTo("userId", curUser.getObjectId());
        bmobQuery.findObjects(new FindListener<VideoInfo>(){
            @Override
            public void done(List<VideoInfo> list, BmobException e) {
                if(e == null && list.size() > 0){
                    saveNewDataToCache(list);
                    EventBus.getDefault().post(new RequestMyVideoListEvent(true, list));
                }else{
                    e.printStackTrace();
                    EventBus.getDefault().post(new RequestMyVideoListEvent(false, null));
                }
            }
        });
    }
}
