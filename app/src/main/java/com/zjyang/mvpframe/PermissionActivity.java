package com.zjyang.mvpframe;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.util.List;

/**
 * Created by 74215 on 2018/10/14.
 */

public class PermissionActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        requestPermission();
    }

    /**
     * 申请相机权限
     */
    public void requestPermission(){
        AndPermission.with(this)
                .runtime()
                .permission(Manifest.permission.CAMERA)
                .rationale(new RequestRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        // 用户同意授予权限
                    }
                }).onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        // 用户拒绝授予权限
                       checkAndShowDialog(permissions);
                    }
        }).start();
    }

    public void checkAndShowDialog(List<String> permissions){
        if(AndPermission.hasAlwaysDeniedPermission(this, permissions)){
            AlertDialog.newBuilder(this)
                    .setCancelable(false)
                    .setTitle("权限申请")
                    .setMessage("我们需要使用相机来进行拍照和录制视频，否则将无法正常使用短视频功能")
                    .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return true;
    }

    /**
     * 自定义权限解释弹框
     */
    public final class RequestRationale implements Rationale<List<String>> {

        @Override
        public void showRationale(Context context, List<String> permissions, final RequestExecutor executor) {
            //获得当前正在申请的危险权限的名字列表
            //List<String> permissionNames = Permission.transformText(context, permissions);
            //遍历拼接
            //String message = "";
            //for(String permission : permissionNames){
                //message = message + " " + permission;
            //}
            //Log.d(TAG, "当前申请的权限名列表: " + message);

            AlertDialog.newBuilder(context)
                    .setCancelable(false)
                    .setTitle("权限申请")
                    .setMessage("我们需要使用相机来进行拍照和录制视频，否则将无法正常使用短视频功能")
                    .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            executor.execute();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            executor.cancel();
                        }
                    })
                    .show();
        }
    }
}
