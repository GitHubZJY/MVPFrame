package com.zjyang.mvpframe.module.enlarge.view;

import com.example.zjy.player.ui.YPlayerView;

/**
 * Created by 74215 on 2018/9/9.
 */

public class BundleViewController {

    private YPlayerView mPlayerView;
    private boolean mIsPlaying;

    private static BundleViewController mController;

    public static BundleViewController getInstance(){
        if(mController == null){
            mController = new BundleViewController();
        }
        return mController;
    }

    public boolean isPlaying() {
        return mIsPlaying;
    }

    public void setIsPlaying(boolean mIsPlaying) {
        this.mIsPlaying = mIsPlaying;
    }

    public YPlayerView getPlayerView() {
        return mPlayerView;
    }

    public void setPlayerView(YPlayerView mPlayerView) {
        this.mPlayerView = mPlayerView;
    }
}
