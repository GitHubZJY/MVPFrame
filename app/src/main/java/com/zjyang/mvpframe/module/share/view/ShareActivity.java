package com.zjyang.mvpframe.module.share.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zjy.player.ui.VideoFrame;
import com.example.zjy.player.utils.VideoUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.base.BaseActivity;
import com.zjyang.mvpframe.module.share.ShareTaskContracts;
import com.zjyang.mvpframe.module.share.presenter.SharePresenter;
import com.zjyang.mvpframe.ui.ShapeUtils;
import com.zjyang.mvpframe.utils.LocationUtils;
import com.zjyang.mvpframe.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by 74215 on 2018/5/13.
 */

public class ShareActivity extends BaseActivity<SharePresenter> implements ShareTaskContracts.View, VideoFrame.OnCompleteListener{

    private static final String TAG = "ShareActivity";
    private Unbinder unbinder;

    @BindView(R.id.share_video_view)
    VideoFrame mShareVideoView;
    @BindView(R.id.preview_pic_iv)
    SimpleDraweeView mPreviewIv;
    @BindView(R.id.center_pause_iv)
    ImageView mPlayIv;
    @BindView(R.id.toolbar_right_tv)
    TextView mShareBtn;
    @BindView(R.id.location_view)
    RelativeLayout mLocationView;
    @BindView(R.id.location_tv)
    TextView mLocationTv;

    private ProgressDialog mProgressDialog;

    public static final String VIDEO_PATH = "VIDEO_PATH";
    private String mVideoPath;

    public static void go(Context context, String videoPath){
        Intent intent = new Intent(context, ShareActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(VIDEO_PATH, videoPath);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public SharePresenter createPresenter() {
        return new SharePresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);// 允许使用transitions
        setContentView(R.layout.activity_share);
        unbinder = ButterKnife.bind(this);


        initVideoView();
    }

    public void initVideoView(){
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        mVideoPath = bundle.getString(VIDEO_PATH);
        mShareVideoView.setVideoUrl(mVideoPath);
        mShareVideoView.setOnCompletionListener(this);
        mPreviewIv.setImageBitmap(VideoUtils.getThumbFromVideo(mVideoPath));
    }

    @Override
    public void showLocationData(String address) {
        mLocationTv.setTextColor(Color.BLACK);
        mLocationTv.setText(address);
    }

    @Override
    public void onCompletion(IMediaPlayer imp, MediaPlayer mp) {
        mPlayIv.setVisibility(View.VISIBLE);
        mPreviewIv.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.center_pause_iv)
    void clickPlay(){
        if(mShareVideoView.isPlaying()){
            mShareVideoView.pause();
            mPlayIv.setVisibility(View.VISIBLE);
        }else{
            mPlayIv.setVisibility(View.GONE);
            mPreviewIv.setVisibility(View.GONE);
            mShareVideoView.start();
        }
    }

    @OnClick(R.id.location_view)
    void clickLocation(){
        if(mPresenter != null){
            mPresenter.startLocation();
        }
    }

    @OnClick(R.id.toolbar_right_tv)
    void clickShare(){
        if(mPresenter != null){
            mPresenter.shareVideo(mVideoPath);
        }
    }

    @Override
    public void showProgressDialog() {
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(this);
        }
        if(mProgressDialog.isShowing()){
            return;
        }
        mProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if(mProgressDialog != null){
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showUpLoadSuccess() {
        ToastUtils.showToast(this, getResources().getString(R.string.share_success));
    }

    @Override
    public void showUpLoadFail() {
        ToastUtils.showToast(this, getResources().getString(R.string.share_fail));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
