package com.zjyang.mvpframe.module.fullscreen.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.zjy.player.ui.VideoFrame;
import com.zjyang.mvpframe.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhengjiayang on 2017/10/18.
 * 页面底部播放控制器
 */

public class VideoSeekBar extends FrameLayout {

    /**
     * 同步进度
     */
    private static final int MESSAGE_SHOW_PROGRESS = 1;


    private SeekBar mProgressBar;
    private TextView mDurTimeTv;
    private TextView mCurTimeTv;

    private ControllerListener mControllerListener;

    private VideoFrame mVideoView;

    private boolean mIsPlaying = false;
    private long mDuration;
    private int mCurrentPosition;
    private long mLastPosition;
    private long mReallyDuration = -1;  //用来记录真正的视频时长

    /**
     * 是否在拖动进度条中，默认为停止拖动，true为在拖动中，false为停止拖动
     */
    private boolean isDragging;
    /**
     * 滑动进度条得到的新位置，和当前播放位置是有区别的,newPosition =0也会调用设置的，故初始化值为-1
     */
    private long newPosition = -1;
    /**
     * 设置新位置
     */
    private static final int MESSAGE_SEEK_NEW_POSITION = 3;

    /**
     * 是否显示控制面板，默认为隐藏，true为显示false为隐藏
     */
    private boolean isShowControlPanl;
    /**
     * 控制面板收起或者显示的轮询监听
     */
    private AutoPlayRunnable mAutoPlayRunnable;

    public VideoSeekBar(Context context) {
        this(context, null);
    }

    public VideoSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context mContext){
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.layout_video_seekbar, null);
        mDurTimeTv = (TextView)rootView.findViewById(R.id.duration_time_tv);
        mCurTimeTv = (TextView)rootView.findViewById(R.id.current_time_tv);
        mProgressBar = (SeekBar)rootView.findViewById(R.id.media_controller_progress);
        mProgressBar.setMax(100000);
        mProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //为了判断是快进还是回退
            private int mCurPosition;
            /**数值的改变*/
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser) {
                    if(mControllerListener != null){
                        mControllerListener.progress(progress);
                    }
                    /**不是用户拖动的，自动播放滑动的情况*/
                    return;
                } else {
                    //拖动的时候当前的时间需要同步刷新到滑动的位置
                    long duration = getDuration();
                    int position = (int) ((duration * progress * 1.0) / 100000);
                    //mVideoView.seekTo(position);
                    String curTime = generateTime(position);
                    String durTime = generateTime(duration);
                    mCurTimeTv.setText(curTime);
                    if(mControllerListener != null){
                        if(progress > mCurPosition){
                            mControllerListener.forward(curTime, durTime, position, duration);
                        }else{
                            mControllerListener.backward(curTime, durTime, position, duration);
                        }
                    }
                }
            }

            /**开始拖动*/
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mCurPosition = seekBar.getProgress();
                isDragging = true;
                mHandler.removeMessages(MESSAGE_SHOW_PROGRESS);
                changeBarStatus(true);
                if(mControllerListener != null){
                    mControllerListener.startScroll();
                }
            }

            /**停止拖动*/
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mCurPosition = 0;
                long duration = getDuration();
                mVideoView.seekTo((int) ((duration * seekBar.getProgress() * 1.0) / 100000));
                mHandler.removeMessages(MESSAGE_SHOW_PROGRESS);
                isDragging = false;
                mHandler.sendEmptyMessage(MESSAGE_SHOW_PROGRESS);
                changeBarStatus(false);
                if(mControllerListener != null){
                    mControllerListener.stopScroll();
                }
            }
        });

        mProgressBar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        mAutoPlayRunnable = new AutoPlayRunnable();
        addView(rootView);


    }

    /**
     * 将播放器与控制器关联起来
     * @param videoView
     */
    public void attachVideoView(View videoView){
        if(videoView instanceof VideoFrame){
            mVideoView = (VideoFrame) videoView;
        }
    }


    public void resetPlayStatus(){
        //如果是暂停状态，就先置为正在播放，再发送消息启动循环，如果是播放状态，可直接发送消息
        if(!mIsPlaying){
            mIsPlaying = !mIsPlaying;
        }
        mHandler.removeMessages(MESSAGE_SHOW_PROGRESS);
        mHandler.sendEmptyMessage(MESSAGE_SHOW_PROGRESS);
    }

    public void setPauseStatus(){
        //如果是播放状态，就先置为暂停，再发送消息启动循环，如果是暂停状态，可直接发送消息
        if(mIsPlaying){
            mIsPlaying = !mIsPlaying;
        }
        mHandler.removeMessages(MESSAGE_SHOW_PROGRESS);
        mHandler.sendEmptyMessage(MESSAGE_SHOW_PROGRESS);
    }

    public void resetController(){
        mCurTimeTv.setText("00:00");
        mLastPosition = 0;
        mIsPlaying = false;
        mProgressBar.setProgress(0);
        mHandler.removeMessages(MESSAGE_SHOW_PROGRESS);
    }

    public void setDurationTimeText(String time){
        mDurTimeTv.setText(time);
    }

    /**
     * 改变小球状态是否外发光
     * @param isLight
     */
    private void changeBarStatus(boolean isLight){
        if(isLight){
            mProgressBar.setThumbOffset(22);
            //Drawable drawable = getResources().getDrawable(R.drawable.ic_dot_hold);//新的图片转成drawable对象
            //mProgressBar.setThumb(drawable);//设置新的图片
        }else{
            mProgressBar.setThumbOffset(6);
            Drawable drawable = getResources().getDrawable(R.drawable.seekbar_thumb_normal);//新的图片转成drawable对象
            mProgressBar.setThumb(drawable);//设置新的图片
        }
    }

    /**
     * 获取当前播放位置
     */
    public int getCurrentPosition() {
        mCurrentPosition = mVideoView.getCurrentPosition();
        return mCurrentPosition;
    }

    /**
     * 获取视频播放总时长
     */
    public long getDuration() {

        mDuration = mVideoView.getDuration();
        return mDuration;
    }


    /**
     * 暂停播放
     */
    public void pausePlay() {
        getCurrentPosition();
        mVideoView.pause();
    }

    /**
     * 停止播放
     */
    public void stopPlay() {
        mVideoView.stopPlayback();
        if(mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * 同步进度
     */
    private long syncProgress() {
        if (isDragging) {
            return 0;
        }
        if(!mVideoView.isInPlaybackState()){
            return 0;
        }
        long position = mVideoView.getCurrentPosition();
        long duration = mVideoView.getDuration();
        Log.d("zjyang", "position: " + position + ",  duration: " + duration);
        //如果上一次的位置与此次相同，表示已经达到最大值，直接赋值为duration（为了适配Ijkplayer无法到达duration的问题）
        //小于100是为了防止一开始播放时连续几帧一致的情况  相差小于1000是为了防止手势滑动过程中连续几帧一致的情况
        if(duration != -1){
            //视频一开始播放的时候，会先有几帧的duration是-1状态，等真正开始不为-1时，即记录真正的视频时长
            mReallyDuration = duration;
        }
        if((mLastPosition == position) && (position > 100) && (duration - position < 1000)){
            position = duration;
        }
        if(duration == -1 && mReallyDuration != -1){
            //duration为-1且mReallyDuration不为-1时，说明是播放结束状态
            position = mReallyDuration;
        }
        if (mProgressBar != null) {
            if (duration > 0) {
                long pos = 100000L * position / duration;
                mProgressBar.setProgress((int) pos);
            }
        }
        mCurTimeTv.setText(generateTime(position));
        mDurTimeTv.setText(generateTime(mReallyDuration));
        if (((position == duration) || position == mReallyDuration) && duration != -1) {
            pausePlay();
            mIsPlaying = false;
            if(mControllerListener != null){
                mControllerListener.playComplete();
            }
        }
        mLastPosition = position;
        return position;
    }

    public void scrollEnd(){
        if (newPosition >= 0) {
            mHandler.removeMessages(MESSAGE_SEEK_NEW_POSITION);
            mHandler.sendEmptyMessage(MESSAGE_SEEK_NEW_POSITION);
        } else {
            /**什么都不做(do nothing)*/
        }
    }

    /**
     * 显示或隐藏操作面板
     */
    public void operatorPanel() {
        isShowControlPanl = !isShowControlPanl;
        if (isShowControlPanl) {
            setVisibility(VISIBLE);
            mAutoPlayRunnable.start();
        } else {
            setVisibility(INVISIBLE);
            mAutoPlayRunnable.stop();
        }
    }


    /**
     * 快进或者快退滑动改变进度
     *
     * @param percent
     */
    public void onProgressSlide(float percent) {
        int position = mVideoView.getCurrentPosition();
        long duration = mVideoView.getDuration();
        long deltaMax = Math.min(100 * 1000, duration - position);
        long delta = (long) (deltaMax * percent);
        newPosition = delta + position;
        if (newPosition > duration) {
            newPosition = duration;
        } else if (newPosition <= 0) {
            newPosition = 0;
            delta = -position;
        }
        int showDelta = (int) delta / 1000;
        String curtime = generateTime(newPosition);
        String durtime = generateTime(duration);
        mCurTimeTv.setText(curtime);
        long pos = 100000L * newPosition / duration;
        mProgressBar.setProgress((int) pos);
        if(percent >= 0){
            mControllerListener.forward(curtime, durtime, newPosition, duration);
        }else{
            mControllerListener.backward(curtime, durtime, newPosition, duration);
        }
    }

    public void setIsPlaying(boolean flag){
        mIsPlaying = flag;
    }


    /**
     * 消息处理
     */
    @SuppressWarnings("HandlerLeak")
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                /**滑动中，同步播放进度*/
                case MESSAGE_SHOW_PROGRESS:
                    if(!isDragging && mIsPlaying){
                        long pos = syncProgress();
                        msg = obtainMessage(MESSAGE_SHOW_PROGRESS);
                        sendMessageDelayed(msg, 50 - (pos % 50));
                    }
                    break;
                /**滑动完成，设置播放进度*/
                case MESSAGE_SEEK_NEW_POSITION:
                    if (newPosition >= 0) {
                        mVideoView.seekTo((int) newPosition);
                        String time = generateTime(newPosition);
                        mCurTimeTv.setText(time);
                        if(getDuration() != 0){
                            long pos = 100000L * newPosition / getDuration();
                            mProgressBar.setProgress((int) pos);
                        }
                        newPosition = -1;
                    }
                    break;
            }
        }
    };

    /**
     * 收起控制面板轮询，默认5秒无操作，收起控制面板，
     */
    private class AutoPlayRunnable implements Runnable {
        private int AUTO_PLAY_INTERVAL = 3000;

        /**
         * 五秒无操作，收起控制面板
         */
        public AutoPlayRunnable() {
        }

        public void start() {
            mHandler.removeCallbacks(this);
            mHandler.postDelayed(this, AUTO_PLAY_INTERVAL);
        }

        public void stop() {
            mHandler.removeCallbacks(this);
        }

        @Override
        public void run() {
            mHandler.removeCallbacks(this);
            if (isShowControlPanl) {
                operatorPanel();
            }
        }
    }

    /**
     * 时长格式化显示 xx:xx:xx
     */
    private String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }

    /*****************************外部接口监听******************************/
    public void setControllerListener(ControllerListener mControllerListener) {
        this.mControllerListener = mControllerListener;
    }

    public interface ControllerListener{
        void startScroll();
        void stopScroll();
        void progress(int progress);
        void playComplete();
        void forward(String curTime, String durTime, long lTime, long duration);
        void backward(String curTime, String durTime, long lTime, long duration);
    }
}



