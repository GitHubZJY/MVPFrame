package com.zjyang.mvpframe.module.fullscreen.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TableLayout;

import com.example.zjy.player.ui.VideoFrame;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.base.BaseActivity;
import com.zjyang.mvpframe.module.base.BasePresenter;
import com.zjyang.mvpframe.module.home.model.bean.VideoInfo;
import com.zjyang.mvpframe.utils.FrescoUtils;
import com.zjyang.mvpframe.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 74215 on 2018/4/15.
 */

public class FullScreenWatchActivity extends BaseActivity {

    private static final String TAG = "FullScreenWatchActivity";
    private static final String VIDEO_INFO = "VIDEO_INFO";
    private static final String JUMP_ANIM_VIEW = "JUMP_ANIM_VIEW";
    private Unbinder unbinder;

    @BindView(R.id.preview_iv)
    SimpleDraweeView mPreviewIv;
    @BindView(R.id.player_view)
    VideoFrame mPlayView;
    @BindView(R.id.hud_view)
    TableLayout mHudView;

    private VideoInfo mVideoInfo;

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    public static void go(Context context, View previewIv, VideoInfo videoInfo){
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,
                new Pair<View, String>(previewIv, JUMP_ANIM_VIEW));
        Intent intent = new Intent(context, FullScreenWatchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(VIDEO_INFO, videoInfo);
        intent.putExtras(bundle);
        //context.startActivity(intent);
        // ActivityCompat是android支持库中用来适应不同android版本的
        ActivityCompat.startActivity(context, intent, activityOptions.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);// 允许使用transitions
        setContentView(R.layout.activity_full_screen_watch);
        unbinder = ButterKnife.bind(this);
        ViewCompat.setTransitionName(mPreviewIv, JUMP_ANIM_VIEW);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mVideoInfo = bundle.getParcelable(VIDEO_INFO);
        mPlayView.setHudView(mHudView);
        mPlayView.setVideoUrl(mVideoInfo.getVideoUrl());
        LogUtil.d(TAG, "URL: " + mVideoInfo.getVideoUrl());

        mPlayView.start();
        FrescoUtils.showImgByUrl(mVideoInfo.getVideoThumbUrl(), mPreviewIv);
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
