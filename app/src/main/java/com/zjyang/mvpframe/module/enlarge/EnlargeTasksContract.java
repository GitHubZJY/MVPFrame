package com.zjyang.mvpframe.module.enlarge;

/**
 * Created by zhengjiayang on 2018/7/17.
 */

public interface EnlargeTasksContract {

    interface View {

    }


    interface Model {
        void insertFocusData(String curUserId, String authorUserId);
        void isFocusCurUser(String curUserId, String authorUserId);
    }

    interface Presenter {
        void focusVideoAuthor(String authorUserId);
    }
}
