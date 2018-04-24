package com.zjyang.mvpframe.module.fullscreen.view;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

import com.example.zjy.player.ui.PlayerListener;
import com.example.zjy.player.ui.VideoFrame;
import com.example.zjy.player.ui.YPlayerView;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.event.FullScreenExitEvent;
import com.zjyang.mvpframe.module.base.BaseActivity;
import com.zjyang.mvpframe.module.home.discover.model.VideoFramesModel;
import com.zjyang.mvpframe.utils.HandlerUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 74215 on 2018/4/15.
 */

public class FullScreenWatchActivity extends BaseActivity implements PlayerListener{

    private Unbinder unbinder;

    @BindView(R.id.player_view)
    YPlayerView mPlayView;

    private VideoFrame mVideoFrame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);// 允许使用transitions
        setContentView(R.layout.activity_full_screen_watch);
        unbinder = ButterKnife.bind(this);
        mVideoFrame = VideoFramesModel.getInstance().getCurPlayView();
        mPlayView.setControllerStatus(mVideoFrame.isPlaying());
        mPlayView.attachActivity(this);
        mPlayView.addVideoFrame(mVideoFrame);
        mPlayView.setPlayerListener(this);
    }

    @Override
    public void clickNarrow() {
        exitFullScreen();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            exitFullScreen();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void exitFullScreen(){
        mPlayView.removeVideoFrame(mVideoFrame);
        EventBus.getDefault().post(new FullScreenExitEvent());
        try{
            Thread.sleep(20);
        }catch (Exception e){
            e.printStackTrace();
        }
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
