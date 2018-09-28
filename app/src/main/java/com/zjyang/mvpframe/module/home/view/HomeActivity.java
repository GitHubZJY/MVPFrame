package com.zjyang.mvpframe.module.home.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zjyang.base.base.BaseActivity;
import com.zjyang.base.base.SkinManager;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.event.FinishActivityEvent;
import com.zjyang.mvpframe.module.BaseFragment;
import com.zjyang.mvpframe.module.UserDataManager;
import com.zjyang.mvpframe.module.home.HomeTasksContract;
import com.zjyang.mvpframe.module.home.adapter.HomePagerAdapter;
import com.zjyang.mvpframe.module.home.discover.model.VideoFramesModel;
import com.zjyang.mvpframe.module.home.presenter.HomePresenter;
import com.zjyang.base.utils.ShapeUtils;
import com.zjyang.mvpframe.ui.dialog.DialogHelper;
import com.zjyang.mvpframe.ui.view.CustomViewPager;
import com.zjyang.base.utils.LogUtil;
import com.zjyang.base.utils.PermissionUtils;
import com.zjyang.base.utils.ToastUtils;
import com.zjyang.recorder.activity.RecorderActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 74215 on 2018/3/13.
 */

public class HomeActivity extends BaseActivity<HomePresenter> implements HomeTasksContract.View, HomeBottomBar.TabClickListener{

    private static final String TAG = "HomeActivity";
    private Unbinder unbinder;

    @BindView(R.id.view_pager)
    public CustomViewPager mViewPager;
    @BindView(R.id.bottom_tab)
    public HomeBottomBar mHomeBottombar;
    @BindView(R.id.bottom_camera_llyt)
    public RelativeLayout mCameraRlyt;
    @BindView(R.id.bottom_camera_bg)
    public ImageView mCameraBg;
    @BindView(R.id.bottom_camera_iv)
    public ImageView mCameraIv;

    private HomePagerAdapter mPagerAdapter;

    private List<BaseFragment> mFragmentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);// 允许使用transitions
        setContentView(R.layout.activity_home);
        unbinder = ButterKnife.bind(this);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        mCameraIv.setBackground(ShapeUtils.getRoundRectDrawable(180, SkinManager.getInstance().getPrimaryColor()));
        mCameraBg.setBackground(ShapeUtils.getRoundRectDrawable(180, Color.parseColor("#ffffff")));

        mFragmentList = mPresenter.getChildPages();
        mPagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);

        mHomeBottombar.setTabClickListener(this);
    }

    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void resetFragments(){
        LogUtil.d(TAG, "resetFragments");
        mFragmentList.clear();
        mFragmentList.addAll(mPresenter.getChildPages());
        mPagerAdapter.notifyDataSetChanged();
    }


    @OnClick(R.id.bottom_camera_iv)
    void clickToCamera(){
        //6.0以上若未有相机权限，需先申请
        boolean hasGranted = PermissionUtils.newInstance().grantPermission(Manifest.permission.CAMERA);
        if(!hasGranted){
            //假如还未有权限，则先不跳转录制界面
            DialogHelper.Companion.showPermissionDialog(getFragmentManager(),  "开启录制需要先授予相机权限");
            return;
        }
        jumpToCameraPage();
    }

    @Override
    public boolean clickTab(int index) {
        if(index == 4 && !UserDataManager.getInstance().checkLogin(this)){
            return false;
        }
        mViewPager.setCurrentItem(index - 1);
        return true;
    }

    /*************************Android6.0以上申请相机权限的回调**************************/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PermissionUtils.MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                // 如果所有的权限都授予了, 则跳转录制界面
                jumpToCameraPage();
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                ToastUtils.showToast(this, "暂无权限");
            }
        }
    }

    public void jumpToCameraPage(){
        Intent intent = new Intent(this, RecorderActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.bottom_activity_open_enter, 0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFinishActivityEvent(FinishActivityEvent event){
        finish();
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
        EventBus.getDefault().unregister(this);
        if(mPresenter != null){
            mPresenter.destroy();
        }
    }
}
