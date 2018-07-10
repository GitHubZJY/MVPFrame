package com.zjyang.mvpframe.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.zjyang.mvpframe.application.AppApplication;

/**
 * Created by 74215 on 2018/7/11.
 * 权限工具类
 */

public class PermissionUtils {

    public static final String TAG = "PermissionUtils";

    private static PermissionUtils mInstance = null;

    public static final int MY_PERMISSION_REQUEST_CODE = 1001;

    public static PermissionUtils newInstance() {
        if(mInstance == null){
            mInstance = new PermissionUtils();
        }
        return mInstance;
    }

    public boolean grantPermission(Activity activity, String permissionName){
        /**
         * 第 1 步: 检查是否有相应的权限
         */
        boolean isAllGranted = checkPermissionAllGranted(
                new String[] {
                        permissionName
                }
        );
        // 如果权限全都拥有
        if (isAllGranted) {
            return true;
        }

        /**
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(
                activity,
                new String[] {
                        permissionName
                },
                MY_PERMISSION_REQUEST_CODE
        );
        return false;
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(AppApplication.getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }


}
