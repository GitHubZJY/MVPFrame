package com.zjyang.mvpframe.utils;

import android.content.Context;
import android.util.TypedValue;

import com.zjyang.mvpframe.application.AppApplication;

/**
 * Created by 74215 on 2018/4/16.
 */

public class DrawUtils {


    /**
     * dp转px
     * @param dpVal   dp value
     * @return px value
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                AppApplication.getContext().getResources().getDisplayMetrics());
    }
}
