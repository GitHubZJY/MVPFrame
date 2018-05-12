package com.zjyang.mvpframe.module.camera.model;

/**
 * Created by 74215 on 2018/5/10.
 * 当前相机的各项参数配置
 */

public class CameraSetting {

    //最终生成的视频宽度
    public static int VIDEO_WIDTH = 640;
    //最终生成的视频高度
    public static int VIDEO_HEIGHT = 480;
    //设置视频录制的帧率,n帧/s
    public static int VIDEO_FRAME_RATE = 30;
    //设置视频的清晰度，越高越清晰且占用内存越大
    public static int VIDEO_ENCODING_BIT_RATE = 1024*1024;
    //设置录制的最大长度（毫秒）
    public static int VIDEO_MAX_DURATION = 30*1000;
    //设置摄像头的方向（180=横屏 90=竖屏）
    public static int VIDEO_ORIENTATION = 90;
}
