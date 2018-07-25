package com.zjyang.mvpframe.module.camera.view;


import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.base.BaseActivity;
import com.zjyang.mvpframe.module.base.BasePresenter;
import com.zjyang.mvpframe.module.camera.model.CameraModel;
import com.zjyang.mvpframe.module.camera.model.CameraSetting;
import com.zjyang.mvpframe.module.share.view.ShareActivity;
import com.zjyang.mvpframe.utils.DateUtils;
import com.zjyang.mvpframe.utils.HandlerUtils;
import com.zjyang.mvpframe.utils.LogUtil;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 74215 on 2018/5/5.
 */

public class CameraActivity extends BaseActivity implements SurfaceHolder.Callback  {

    public static final String TAG = "CameraActivity";
    private Unbinder unbinder;

    @BindView(R.id.surfaceview)
    SurfaceView mSurfaceView;
    @BindView(R.id.btnStartStop)
    Button mBtnStartStop;
    @BindView(R.id.btnPlayVideo)
    Button mBtnPlay;
    @BindView(R.id.cur_record_time)
    TextView textView;
    private boolean mStartedFlg = false;//是否正在录像
    private boolean mIsPlay = false;//是否正在播放录像
    private MediaRecorder mRecorder;
    private SurfaceHolder mSurfaceHolder;
    private Camera camera;
    private MediaPlayer mediaPlayer;
    private String path;

    private int mCurSecond = 0;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mCurSecond++;
            textView.setText(mCurSecond+"");
            HandlerUtils.postDelay(this,1000);
        }
    };

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);// 允许使用transitions
        setContentView(R.layout.activity_camera);

        unbinder = ButterKnife.bind(this);
        initView();
    }

    public void initView(){

        SurfaceHolder holder = mSurfaceView.getHolder();
        holder.addCallback(this);
        // setType必须设置，要不出错.
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    @OnClick(R.id.btnStartStop)
    void clickStartRecord(){
        //假如当前正在播放，则先停止播放
        if (mIsPlay) {
            if (mediaPlayer != null) {
                mIsPlay = false;
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
        //停止播放之后开始录制新的视频
        if (!mStartedFlg) {
            startRecord();
        } else {
            stopRecord();
        }
    }

    @OnClick(R.id.btnPlayVideo)
    void clickPlay(){
        mIsPlay = true;
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.reset();
        Uri uri = Uri.parse(path);
        mediaPlayer = MediaPlayer.create(CameraActivity.this, uri);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDisplay(mSurfaceHolder);
        try{
            mediaPlayer.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    public void startRecord(){
        HandlerUtils.postDelay(runnable,1000);
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
        }


        try {
            // 这两项需要放在setOutputFormat之前
            mRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
            mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

            // Set output file format
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

            // 这两项需要放在setOutputFormat之后
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
            LogUtil.d(TAG, ""+CameraSetting.VIDEO_WIDTH + ", " +CameraSetting.VIDEO_HEIGHT);
            mRecorder.setVideoSize(CameraSetting.VIDEO_WIDTH, CameraSetting.VIDEO_HEIGHT);
            mRecorder.setVideoFrameRate(CameraSetting.VIDEO_FRAME_RATE);
            mRecorder.setVideoEncodingBitRate(CameraSetting.VIDEO_ENCODING_BIT_RATE);
            mRecorder.setOrientationHint(CameraSetting.VIDEO_ORIENTATION);
            //设置记录会话的最大持续时间（毫秒）
            mRecorder.setMaxDuration(CameraSetting.VIDEO_MAX_DURATION);

            path = CameraModel.LOCAL_RECORD_VIDEO_PATH;
            if (path != null) {
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                path = dir + "/" + DateUtils.getDate() + ".mp4";
                mRecorder.setOutputFile(path);
                mRecorder.prepare();
                mRecorder.start();
                mStartedFlg = true;
                mBtnStartStop.setText("Stop");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void stopRecord(){
        if (mStartedFlg) {
            try {
                HandlerUtils.remove(runnable);
                mRecorder.stop();
                mRecorder.reset();
                mRecorder.release();
                mRecorder = null;
                mBtnStartStop.setText("Start");
                if (camera != null) {
                    camera.release();
                    camera = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mStartedFlg = false;
        ShareActivity.go(this, path);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }




    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mSurfaceHolder = surfaceHolder;
        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);

        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
        }
        mRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
        try{
            camera.setPreviewDisplay(mSurfaceHolder);
        }catch (Exception e){
            e.printStackTrace();
        }

        camera.startPreview();
        if (camera != null) {
            camera.setDisplayOrientation(CameraSetting.VIDEO_ORIENTATION);
            camera.unlock();
            mRecorder.setCamera(camera);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        // 将holder，这个holder为开始在onCreate里面取得的holder，将它赋给mSurfaceHolder
        mSurfaceHolder = surfaceHolder;
//        List<Camera.Size> sizeList = camera.getParameters().getSupportedVideoSizes();
//        Camera.Size mPreviewSize = CameraSetting.getOptimalPreviewSize(sizeList, i1, i2);
//        Camera.Parameters mParams = camera.getParameters();
//        mParams.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
//        camera.setParameters(mParams);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mSurfaceView = null;
        mSurfaceHolder = null;
        HandlerUtils.remove(runnable);
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
        if (camera != null) {
            camera.release();
            camera = null;
        }
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
