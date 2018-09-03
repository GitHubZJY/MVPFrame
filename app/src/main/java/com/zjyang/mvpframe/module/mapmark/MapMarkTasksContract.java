package com.zjyang.mvpframe.module.mapmark;

import com.zjyang.mvpframe.module.login.model.bean.User;
import com.zjyang.mvpframe.module.mapmark.model.bean.MapMark;

import java.util.List;

/**
 * Created by zhengjiayang on 2018/7/17.
 */

public interface MapMarkTasksContract {

    interface View {
        void setMarkDataInMap(List<MapMark> mapMarks);
    }


    interface Model {
        void getMarkDataByUserId(int userId);
    }

    interface Presenter {
        void fillMarkData();
    }
}
