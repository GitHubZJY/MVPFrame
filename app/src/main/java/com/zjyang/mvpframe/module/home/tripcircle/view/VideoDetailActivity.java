package com.zjyang.mvpframe.module.home.tripcircle.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zjy.player.ui.PlayerListener;
import com.example.zjy.player.ui.YPlayerView;
import com.zjyang.base.base.BaseActivity;
import com.zjyang.base.base.BasePresenter;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.event.FullScreenExitEvent;
import com.zjyang.mvpframe.module.enlarge.view.BundleViewController;
import com.zjyang.mvpframe.module.enlarge.view.EnlargeWatchActivity;
import com.zjyang.mvpframe.module.home.tripcircle.adapter.CommentListAdapter;
import com.zjyang.mvpframe.module.home.tripcircle.model.bean.CommentInfo;
import com.zjyang.mvpframe.module.home.tripcircle.model.bean.WonderfulVideo;
import com.zjyang.mvpframe.module.home.tripcircle.presenter.VideoDetailPresenter;
import com.zjyang.base.utils.DrawUtils;
import com.zjyang.mvpframe.utils.KeyboardPatch;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhengjiayang on 2018/8/24.
 */

public class VideoDetailActivity extends BaseActivity implements PlayerListener{

    private Unbinder unbinder;

    @BindView(R.id.root_view)
    RelativeLayout mRootView;
    @BindView(R.id.video_frame)
    YPlayerView mVideoFrame;
    @BindView(R.id.comment_lv)
    RecyclerView mCommentLv;
    @BindView(R.id.video_title)
    TextView mTitleTv;
    @BindView(R.id.video_describe)
    TextView mDescribeTv;

    private CommentListAdapter mCommentAdapter;
    private static final String INTENT_DATA = "INTENT_DATA";
    private WonderfulVideo mDataInfo;

    private KeyboardPatch keyboardPatch;

    public static void go(Context context, WonderfulVideo wonderfulVideo){
        Intent intent = new Intent(context, VideoDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(INTENT_DATA, wonderfulVideo);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    @Override
    public BasePresenter createPresenter() {
        return new VideoDetailPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);// 允许使用transitions
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setSoftInputMode
                (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_video_detail);
        unbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        keyboardPatch = new KeyboardPatch(this, mRootView);
        keyboardPatch.enable();
        mVideoFrame.setVideoUrl("http://bmob-cdn-18798.b0.upaiyun.com/2018/08/26/c3e45865408a5b10807865469083b339.mp4");
        mVideoFrame.start();

        List<CommentInfo> commentInfoList = new ArrayList<>();

        for(int i=0; i<5; i++){
            CommentInfo commentInfo = new CommentInfo();
            commentInfoList.add(commentInfo);
        }

        mCommentAdapter = new CommentListAdapter(commentInfoList, this);
        mCommentLv.setLayoutManager(new LinearLayoutManager(this));
        mCommentLv.setAdapter(mCommentAdapter);

        getIntentData();
    }

    public void getIntentData(){
        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                mDataInfo = bundle.getParcelable(INTENT_DATA);
                initData();
            }
        }
    }

    public void initData(){
        String title = mDataInfo.getTitle();
        String describe = mDataInfo.getDescribe();
        mTitleTv.setText(TextUtils.isEmpty(title) ? "" : title);
        mDescribeTv.setText(TextUtils.isEmpty(describe) ? "" : describe);
    }

    @Override
    public void clickNarrow() {
        BundleViewController.getInstance().setPlayerView(mVideoFrame);
        BundleViewController.getInstance().setIsPlaying(mVideoFrame.isPlaying());
        Intent intent = new Intent(this, EnlargeWatchActivity.class);
        startActivity(intent);
    }

    @Override
    public void clickBack() {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onExitFullScreenEvent(FullScreenExitEvent event){
        YPlayerView yPlayerView = BundleViewController.getInstance().getPlayerView();
        if(yPlayerView != null){
            ViewGroup viewParent = (ViewGroup)yPlayerView.getParent();
            if(viewParent != null){
                viewParent.removeView(yPlayerView);
            }
            mRootView.addView(yPlayerView, 1);
            yPlayerView.getLayoutParams().height = DrawUtils.dp2px(220);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mVideoFrame != null){
            mVideoFrame.setPlayerListener(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoFrame.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoFrame.stopPlayback();
        keyboardPatch.disable();
        if(unbinder != null){
            unbinder.unbind();
        }
        EventBus.getDefault().unregister(this);
    }
}
