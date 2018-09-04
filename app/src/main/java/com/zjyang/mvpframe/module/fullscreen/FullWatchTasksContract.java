package com.zjyang.mvpframe.module.fullscreen;

import com.zjyang.mvpframe.module.mapmark.model.bean.MapMark;

import java.util.List;

/**
 * Created by zhengjiayang on 2018/7/17.
 */

public interface FullWatchTasksContract {

    interface View {
        void focusSuccess();
    }


    interface Model {
        void insertFocusData(String curUserId, String authorUserId);
    }

    interface Presenter {
        void focusVideoAuthor(String authorUserId);
    }
}
