package com.zjyang.mvpframe.module.fullscreen.view;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.TableLayout;

import com.example.zjy.player.ui.PlayerListener;
import com.example.zjy.player.ui.VideoFrame;
import com.example.zjy.player.ui.YPlayerView;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.event.FullScreenExitEvent;
import com.zjyang.mvpframe.module.base.BaseActivity;
import com.zjyang.mvpframe.module.base.BasePresenter;
import com.zjyang.mvpframe.module.home.discover.model.VideoFramesModel;
import com.zjyang.mvpframe.module.home.model.bean.VideoInfo;
import com.zjyang.mvpframe.utils.HandlerUtils;
import com.zjyang.mvpframe.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by 74215 on 2018/4/15.
 */

public class FullScreenWatchActivity extends BaseActivity {

    private static final String TAG = "FullScreenWatchActivity";
    private static final String VIDEO_INFO = "VIDEO_INFO";
    private Unbinder unbinder;

    @BindView(R.id.player_view)
    VideoFrame mPlayView;
    @BindView(R.id.hud_view)
    TableLayout mHudView;

    private VideoInfo mVideoInfo;

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    public static void go(Context context, VideoInfo videoInfo){
        Intent intent = new Intent(context, FullScreenWatchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(VIDEO_INFO, videoInfo);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);// 允许使用transitions
        setContentView(R.layout.activity_full_screen_watch);
        unbinder = ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mVideoInfo = bundle.getParcelable(VIDEO_INFO);
        //mVideoInfo = VideoFramesModel.getInstance().getCurPlayVideo();
        mPlayView.setHudView(mHudView);
        mPlayView.setVideoUrl(mVideoInfo.getVideoUrl());
        LogUtil.d(TAG, "URL: " + mVideoInfo.getVideoUrl());

        mPlayView.start();

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
        mPlayView.stopPlayback();
        //EventBus.getDefault().post(new FullScreenExitEvent());
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
