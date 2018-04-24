package com.zjyang.mvpframe.utils;

import com.zjyang.mvpframe.application.AppApplication;

/**
 * Created by 74215 on 2018/3/10.
 */

public class ScreenUtils {

    /**
     * 获取状态栏高度
     * */
    public static int getStatusBarHeight(){

        int statusBarHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = AppApplication.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = AppApplication.getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}
