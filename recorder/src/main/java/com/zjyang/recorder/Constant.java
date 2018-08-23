package com.zjyang.recorder;

import android.os.Environment;

import java.io.File;

/**
 * Created by 74215 on 2018/8/21.
 */

public class Constant {

    public static final String INTENT_VIDEO_PATH = "intent_video_path";

    //加了滤镜之后视频的最终存储地址
    public static final String FINALLY_VIDEO_OUTPUT_PATH = Environment.getExternalStorageDirectory()
            + File.separator
            + "tripvideo"
            + File.separator
            + "output_compose_video.mp4";

}
