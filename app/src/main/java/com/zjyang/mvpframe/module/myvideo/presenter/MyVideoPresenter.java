package com.zjyang.mvpframe.module.myvideo.presenter;

import com.zjyang.mvpframe.module.base.UserDataManager;
import com.zjyang.mvpframe.module.home.model.bean.VideoInfo;
import com.zjyang.mvpframe.module.login.model.bean.User;
import com.zjyang.mvpframe.module.myvideo.MyVideoTasksContract;
import com.zjyang.mvpframe.module.myvideo.model.MyVideoModel;

import java.util.List;

/**
 * Created by zhengjiayang on 2018/7/19.
 */

public class MyVideoPresenter implements MyVideoTasksContract.Presenter{

    private MyVideoTasksContract.View mView;
    private MyVideoTasksContract.Model mModel;

    public MyVideoPresenter(MyVideoTasksContract.View mView) {
        this.mView = mView;
        mModel = new MyVideoModel();
    }

    @Override
    public List<VideoInfo> getCurUserCacheVideoData() {
        if(mModel == null){
            return null;
        }
        boolean isCacheMatchUser = false;
        User curUser = UserDataManager.getInstance().getCurUser();
        String userId = curUser.getObjectId();
        List<VideoInfo> cacheList = mModel.getSpVideoData();
        for(VideoInfo videoInfo : cacheList){
            if(videoInfo.getUserId().equals(userId)){
                isCacheMatchUser = true;
                break;
            }
        }
        if(isCacheMatchUser){
            return cacheList;
        }
        return null;
    }

    @Override
    public void checkCacheDataAndNotify(){
        if(mView != null){
            mView.fillDataToListView(getCurUserCacheVideoData());
        }
    }

    @Override
    public void getMyVideoList() {
        if(mModel != null){
            mModel.getCurUserVideoData();
        }
    }
}
