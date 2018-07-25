package com.zjyang.mvpframe.module.camera.model;

import android.hardware.Camera;

import com.zjyang.mvpframe.utils.ScreenUtils;

import java.util.List;

/**
 * Created by 74215 on 2018/5/10.
 * 当前相机的各项参数配置
 */

public class CameraSetting {

    //最终生成的视频宽度
    public static int VIDEO_WIDTH = ScreenUtils.getsScreenHeight();
    //最终生成的视频高度
    public static int VIDEO_HEIGHT = ScreenUtils.getsScreenWidth();
    //设置视频录制的帧率,n帧/s
    public static int VIDEO_FRAME_RATE = 30;
    //设置视频的清晰度，越高越清晰且占用内存越大
    public static int VIDEO_ENCODING_BIT_RATE = 1024*1024;
    //设置录制的最大长度（毫秒）
    public static int VIDEO_MAX_DURATION = 30*1000;
    //设置摄像头的方向（180=横屏 90=竖屏）
    public static int VIDEO_ORIENTATION = 90;

    public static Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null) {
            return null;
        }
        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;
        int targetHeight = h;
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }
}
