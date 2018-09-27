package com.zjyang.mvpframe.module.enlarge.view;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.zjy.player.ui.PlayerListener;
import com.example.zjy.player.ui.YPlayerView;
import com.zjyang.base.base.BaseActivity;
import com.zjyang.base.base.BasePresenter;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.event.FullScreenExitEvent;
import com.zjyang.mvpframe.module.enlarge.EnlargeTasksContract;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 74215 on 2018/9/9.
 */

public class EnlargeWatchActivity extends BaseActivity implements EnlargeTasksContract.View, PlayerListener{

    public Unbinder unbinder;

    @BindView(R.id.root_view)
    FrameLayout mRootView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);// 允许使用transitions
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_enlarge_watch);
        unbinder = ButterKnife.bind(this);
        YPlayerView yPlayerView = BundleViewController.getInstance().getPlayerView();
        if(yPlayerView != null){
            ViewGroup viewParent = (ViewGroup)yPlayerView.getParent();
            if(viewParent != null){
                viewParent.removeView(yPlayerView);
            }
            mRootView.addView(yPlayerView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            yPlayerView.setPlayerListener(this);
            if(BundleViewController.getInstance().isPlaying()){
                yPlayerView.start();
            }
        }
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void clickNarrow() {
        finish();
    }

    @Override
    public void clickBack() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder != null){
            unbinder.unbind();
        }
        EventBus.getDefault().post(new FullScreenExitEvent());
    }
}
