package com.zjyang.mvpframe.module.camera.model;

import com.zjyang.mvpframe.utils.StorageUtils;

/**
 * Created by 74215 on 2018/5/6.
 */

public class CameraModel {

    //拍摄的视频的存储目录
    public static final String LOCAL_RECORD_VIDEO_PATH = StorageUtils.getSDPath() + "//tripvideo";
    //本地视频截图缓存目录
    public static final String LOCAL_VIDEO_THUMB_CACHE_PATH = StorageUtils.getSDPath() + "//tripvideo";

}
