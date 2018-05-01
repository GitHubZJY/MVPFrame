package com.zjyang.mvpframe.module.home.discover;

/**
 * Created by 74215 on 2018/4/21.
 */

public interface DiscoverTasksContract {

    interface View {
        void toggleTopTab(int index);
    }

    interface Presenter {
        void toggleProvince(int index);
        void refreshList();
    }


    interface Model {
        void getVideoDataByProvinceId(int provinceId);
        void setCurSelectTabIndex(int index);
        int getCurSelectTabIndex();
    }

}
