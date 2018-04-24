package com.example.zjy.player.utils;

import android.util.Log;

/**
 * Created by zhengjiayang on 2018/3/30.
 * log工具.
 */
public class LogUtil {

    private static final String TAG = "SystemVideoView";

    public static void d(String message) {
        Log.d(TAG, message);
    }

    public static void i(String message) {
        Log.i(TAG, message);
    }

    public static void e(String message, Throwable throwable) {
        Log.e(TAG, message, throwable);
    }
}
