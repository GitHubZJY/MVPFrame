package com.example.zjy.player.ui;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.VideoView;

import com.example.zjy.player.R;
import com.example.zjy.player.controller.PlayerGestureManager;
import com.example.zjy.player.controller.QVMediaController;
import com.example.zjy.player.setting.VideoSetting;
import com.example.zjy.player.utils.ScreenUtils;

import tv.danmaku.ijk.media.example.widget.media.IjkVideoView;

/**
 * Created by 74215 on 2018/3/24.
 */

public class YPlayerView extends RelativeLayout implements QVMediaController.ControllerListener{

    private Context mContext;
    private Activity mActivity;
    private RelativeLayout mRootView;
    //视频播放View
    private VideoFrame mVideoFrame;
    //Ijk需要的hudView
    private TableLayout mHudView;
    //底部控制面板
    private QVMediaController mMediaController;
    //顶部标题栏
    private RelativeLayout mToolBar;
    //手势控制器
    private PlayerGestureManager mPlayerGesture;
    //播放界面的相关监听
    private PlayerListener mListener;


    public YPlayerView(Context context) {
        this(context, null);
    }

    public YPlayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(context);
    }

    public void initView(Context context){

        View view  = LayoutInflater.from(context).inflate(R.layout.layout_yplayer_view, null);
        mRootView = (RelativeLayout) view.findViewById(R.id.root_view);
        mVideoFrame = (VideoFrame) view.findViewById(R.id.video_frame);
        mHudView = (TableLayout) view.findViewById(R.id.hud_view);
        mMediaController = (QVMediaController) view.findViewById(R.id.media_controller);
        mToolBar = (RelativeLayout) view.findViewById(R.id.toolbar);
        mVideoFrame.setHudView(mHudView);

        //RelativeLayout.LayoutParams toolbarParams = (RelativeLayout.LayoutParams)mToolBar.getLayoutParams();
        mToolBar.setPadding(0, ScreenUtils.px2dip(context, ScreenUtils.getStatusBarHeight(context)), 0, 0);

        mMediaController.attachVideoView(mVideoFrame);
        mMediaController.setControllerListener(this);
        mMediaController.setNarrowEnable(true);



        addView(view);

    }

    public void attachActivity(Activity activity){
        mActivity = activity;
        mPlayerGesture = new PlayerGestureManager(mActivity);
    }

    public void addVideoFrame(VideoFrame videoFrame){
        mRootView.removeView(mVideoFrame);
        mVideoFrame = videoFrame;
        mMediaController.attachVideoView(videoFrame);
        ViewGroup parentView = (ViewGroup) videoFrame.getParent();
        if(parentView != null){
            parentView.removeView(videoFrame);
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRootView.addView(videoFrame, 0, params);
    }

    public void removeVideoFrame(VideoFrame videoFrame){
        if(videoFrame.getParent() == mRootView){
            mRootView.removeView(videoFrame);
        }
    }


    public void setVideoUrl(String videoUrl){
        mVideoFrame.setVideoUrl(videoUrl);
    }

    public void setGestureEnable(boolean isEnable){
        if(mPlayerGesture != null){
            mPlayerGesture.setAllGestureEnable(isEnable);
        }
    }

    public void start(){
        mVideoFrame.start();
    }

    public void stop(){
        mVideoFrame.stopPlayback();
    }

    public void setControllerStatus(boolean mIsPlaying){
        mMediaController.initControllerStatus(mIsPlaying);
    }

    public void setPlayerListener(PlayerListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void clickNarrow() {
        if(mListener != null){
            mListener.clickNarrow();
        }
        if(mActivity != null){
            if(ScreenUtils.getScreenOrientation(mContext) == Configuration.ORIENTATION_LANDSCAPE){
                //当前处于横屏，需转换为竖屏
                mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }else{
                //当前处于竖屏，需转换为横屏
                mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }

        }
    }

    @Override
    public void clickPlay(boolean isPlay) {
        if(isPlay){
            mVideoFrame.pause();
        }else{
            mVideoFrame.start();
        }
    }

    @Override
    public void progress(int progress) {

    }

    @Override
    public void clickNext() {

    }

    @Override
    public void clickPre() {

    }

    @Override
    public void forward(String curTime, String durTime, long lTime, long duration) {

    }

    @Override
    public void backward(String curTime, String durTime, long lTime, long duration) {

    }

    @Override
    public void playComplete() {

    }

    @Override
    public void stopScroll() {

    }

    @Override
    public void startScroll() {

    }


}
