package com.zjyang.mvpframe.module.myvideo;

import com.zjyang.mvpframe.module.home.model.bean.VideoInfo;

import java.util.List;

/**
 * Created by zhengjiayang on 2018/7/19.
 */

public interface MyVideoTasksContract {

    interface View {
        void fillDataToListView(List<VideoInfo> videoList);
    }

    interface Presenter {
        void checkCacheDataAndNotify();
        List<VideoInfo> getCurUserCacheVideoData();
        void getMyVideoList();
    }

    interface Model {
        List<VideoInfo> getSpVideoData();
        void getCurUserVideoData();
    }
}
