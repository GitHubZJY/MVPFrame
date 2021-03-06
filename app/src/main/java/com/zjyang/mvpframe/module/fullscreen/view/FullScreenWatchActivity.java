package com.zjyang.mvpframe.module.fullscreen.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.zjy.player.ui.VideoFrame;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zjyang.base.base.BaseActivity;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.fullscreen.FullWatchTasksContract;
import com.zjyang.mvpframe.module.fullscreen.presenter.FullWatchPresenter;
import com.zjyang.mvpframe.module.home.model.bean.VideoInfo;
import com.zjyang.base.utils.ShapeUtils;
import com.zjyang.mvpframe.ui.view.FocusButton;
import com.zjyang.base.utils.DrawUtils;
import com.zjyang.base.utils.FrescoUtils;
import com.zjyang.base.utils.LogUtil;
import com.zjyang.base.utils.ToastUtils;
import com.zjyang.mvpframe.utils.KeyboardPatch;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import butterknife.Unbinder;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by 74215 on 2018/4/15.
 */

public class FullScreenWatchActivity extends BaseActivity<FullWatchPresenter> implements FullWatchTasksContract.View, TextWatcher{

    private static final String TAG = "FullScreenWatchActivity";
    private static final String VIDEO_INFO = "VIDEO_INFO";
    private static final String JUMP_ANIM_VIEW = "JUMP_ANIM_VIEW";
    private Unbinder unbinder;

    @BindView(R.id.root_view)
    RelativeLayout mRootView;
    @BindView(R.id.preview_iv)
    SimpleDraweeView mPreviewIv;
    @BindView(R.id.player_view)
    VideoFrame mPlayView;
    @BindView(R.id.hud_view)
    TableLayout mHudView;
    @BindView(R.id.comment_edit)
    EditText mCommentEdit;
    @BindView(R.id.comment_hint_tv)
    TextView mHintTv;
    @BindView(R.id.video_user_pic)
    SimpleDraweeView mVideoUserPic;
    @BindView(R.id.user_group_view)
    LinearLayout mAuthorGroupView;
    @BindView(R.id.focus_btn)
    FocusButton mFocusBtn;
    @BindView(R.id.author_name_tv)
    TextView mAuthorNameTv;
    @BindView(R.id.position_tv)
    TextView mPositionTv;
    @BindView(R.id.video_seek_bar)
    VideoSeekBar mVideoSeekBar;
    @BindView(R.id.close_iv)
    ImageView mCloseIv;
    @BindView(R.id.center_play_iv)
    ImageView mCenterPlayIv;

    private VideoInfo mVideoInfo;

    private KeyboardPatch keyboardPatch;

    @Override
    public FullWatchPresenter createPresenter() {
        return new FullWatchPresenter();
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
        getWindow().setSoftInputMode
                (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_full_screen_watch);
        unbinder = ButterKnife.bind(this);
        keyboardPatch = new KeyboardPatch(this, mRootView);
        keyboardPatch.enable();
        ViewCompat.setTransitionName(mPreviewIv, JUMP_ANIM_VIEW);
        mCommentEdit.setBackground(ShapeUtils.getRoundRectDrawable(DrawUtils.dp2px(20), Color.parseColor("#33000000")));
        mAuthorGroupView.setBackground(ShapeUtils.getRoundRectDrawable(DrawUtils.dp2px(32), Color.parseColor("#33000000")));
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mVideoInfo = bundle.getParcelable(VIDEO_INFO);
        mVideoSeekBar.attachVideoView(mPlayView);
        mVideoSeekBar.resetPlayStatus();
        mPlayView.setHudView(mHudView);
        mPlayView.setVideoUrl(mVideoInfo.getVideoUrl());
        LogUtil.d(TAG, "URL: " + mVideoInfo.getVideoUrl());
        mPlayView.setOnPrePareListener(new VideoFrame.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer, MediaPlayer mediaPlayer) {
                if(mPreviewIv != null){
                    mPreviewIv.setVisibility(View.GONE);
                }
            }
        });
        mPlayView.start();
        FrescoUtils.showImgByUrl(mVideoInfo.getVideoThumbUrl(), mPreviewIv);
        FrescoUtils.showImgByUrl(mVideoInfo.getUserPicUrl(), mVideoUserPic);
        mAuthorNameTv.setText(mVideoInfo.getUserName());

        mCommentEdit.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(TextUtils.isEmpty(s)){
            mHintTv.setVisibility(View.VISIBLE);
        }else{
            mHintTv.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void focusSuccess() {
        mFocusBtn.setIsFocus(true);
        ToastUtils.showToast(this, "关注成功");
    }

    @OnClick(R.id.focus_btn)
    void clickFocus(){
        if(!mFocusBtn.isFocus()){
            mPresenter.focusVideoAuthor(mVideoInfo.getUserId());
        }
    }

    @OnClick(R.id.close_iv)
    void clickClose(){
        exitFullScreen();
    }

    @OnTouch(R.id.player_view)
    boolean touchVideo(){
        if(mPlayView.isPlaying()){
            mPlayView.pause();
            mCenterPlayIv.setVisibility(View.VISIBLE);
        }else{
            mPlayView.start();
            mCenterPlayIv.setVisibility(View.GONE);
        }
        return false;
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
        keyboardPatch.disable();
        unbinder.unbind();
    }
}
