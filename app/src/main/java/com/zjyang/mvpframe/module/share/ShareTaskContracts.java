package com.zjyang.mvpframe.module.share;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by 74215 on 2018/5/13.
 */

public interface ShareTaskContracts {

    interface View {
        void showUpLoadSuccess();
        void showUpLoadFail();
        void showLocationData(String address);
        void showProgressDialog();
        void dismissProgressDialog();
        ImageView getPreviewIv();
    }

    interface Model {
        void uploadVideoFile(String videoPath, Bitmap thumbBm);
        void setLocationData(String address, int id);
    }

    interface Presenter {
        void destroy();
        void shareVideo(String videoPath);
        void startLocation();
    }
}
