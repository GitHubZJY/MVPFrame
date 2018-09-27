package com.zjyang.mvpframe.module.home;


import com.zjyang.mvpframe.module.BaseFragment;

import java.util.List;

/**
 * Created by 74215 on 2018/3/13.
 */

public interface HomeTasksContract {

    interface View {
        void resetFragments();
    }

    interface Presenter {
        List<BaseFragment> getChildPages();
        void destroy();
    }

    interface Model {
        List<BaseFragment> getFragments();
        void requestTabInfo();
    }
}
