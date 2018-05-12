package com.zjyang.mvpframe.module.home.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.zjy.player.ui.YPlayerView;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.base.BaseActivity;
import com.zjyang.mvpframe.module.camera.view.CameraActivity;
import com.zjyang.mvpframe.module.home.adapter.HomePagerAdapter;
import com.zjyang.mvpframe.module.home.discover.model.VideoFramesModel;
import com.zjyang.mvpframe.module.home.discover.view.DiscoverFragment;
import com.zjyang.mvpframe.module.home.model.bean.VideoInfo;
import com.zjyang.mvpframe.ui.ShapeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 74215 on 2018/3/13.
 */

public class HomeActivity extends BaseActivity{

    private Unbinder unbinder;

    @BindView(R.id.view_pager)
    public ViewPager mViewPager;
    @BindView(R.id.bottom_camera_llyt)
    public LinearLayout mCameraLlyt;
    @BindView(R.id.bottom_camera_iv)
    public ImageView mCameraIv;

    private HomePagerAdapter mPagerAdapter;

    private List<Fragment> mFragmentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);// 允许使用transitions
        setContentView(R.layout.activity_home);
        unbinder = ButterKnife.bind(this);

        mCameraIv.setBackground(ShapeUtils.getRoundRectDrawable(15, Color.parseColor("#ffd600")));

        mFragmentList = new ArrayList<>();
        DiscoverFragment discoverFragment = new DiscoverFragment();
        mFragmentList.add(discoverFragment);

        mPagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), mFragmentList);

        mViewPager.setAdapter(mPagerAdapter);
    }


    @OnClick(R.id.bottom_camera_iv)
    void clickToCamera(){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int curPlayIndex = VideoFramesModel.getInstance().getCurPlayItemIndex();
        if(curPlayIndex != -1){
            //不等于-1，说明已有视频正在播放状态
            VideoFramesModel.getInstance().getCurPlayView().stopPlayback();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
