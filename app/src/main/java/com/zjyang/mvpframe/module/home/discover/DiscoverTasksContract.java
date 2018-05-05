package com.zjyang.mvpframe.module.home.discover;

import com.zjyang.mvpframe.module.home.model.bean.VideoInfo;

import java.util.List;

/**
 * Created by 74215 on 2018/4/21.
 */

public interface DiscoverTasksContract {

    interface View {
        void toggleTopTab(int index);
        void fillDataToList(List<VideoInfo> data);
    }

    interface Presenter {
        void initDataBeforeRequest();
        void toggleProvince(int index);
        void refreshList();
    }


    interface Model {
        List<VideoInfo> getDefaultProvinceData();
        void getVideoDataByProvinceId(int provinceId);
        void setCurSelectTabIndex(int index);
        int getCurSelectTabIndex();
    }

}
