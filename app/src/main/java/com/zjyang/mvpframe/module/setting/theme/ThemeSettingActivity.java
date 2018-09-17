package com.zjyang.mvpframe.module.setting.theme;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.zjyang.mvpframe.MainActivity;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.event.FinishActivityEvent;
import com.zjyang.mvpframe.module.base.BaseActivity;
import com.zjyang.mvpframe.module.base.BasePresenter;
import com.zjyang.mvpframe.module.base.SkinManager;
import com.zjyang.mvpframe.module.home.view.HomeActivity;
import com.zjyang.mvpframe.ui.ShapeUtils;
import com.zjyang.mvpframe.utils.DrawUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 74215 on 2018/9/16.
 */

public class ThemeSettingActivity extends BaseActivity {

    private Unbinder unbinder;

    @BindView(R.id.yellow_iv)
    ImageView mYellowIv;
    @BindView(R.id.blue_iv)
    ImageView mBlueIv;
    @BindView(R.id.red_iv)
    ImageView mRedIv;
    @BindView(R.id.orange_iv)
    ImageView mOrangeIv;

    @BindView(R.id.bottom_camera_iv)
    ImageView mCameraIv;
    @BindView(R.id.bottom_camera_bg)
    public ImageView mCameraBg;


    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_theme);
        unbinder = ButterKnife.bind(this);

        mCameraIv.setBackground(ShapeUtils.getRoundRectDrawable(180, SkinManager.getInstance().getPrimaryColor()));
        mCameraBg.setBackground(ShapeUtils.getRoundRectDrawable(180, Color.parseColor("#ffffff")));

        mYellowIv.setBackground(ShapeUtils.getRoundRectDrawable(DrawUtils.dp2px(10), Color.parseColor(SkinManager.YELLOW)));
        mBlueIv.setBackground(ShapeUtils.getRoundRectDrawable(DrawUtils.dp2px(10), Color.parseColor(SkinManager.BLUE)));
        mRedIv.setBackground(ShapeUtils.getRoundRectDrawable(DrawUtils.dp2px(10), Color.parseColor(SkinManager.RED)));
        mOrangeIv.setBackground(ShapeUtils.getRoundRectDrawable(DrawUtils.dp2px(10), Color.parseColor(SkinManager.ORANGE)));
    }

    @OnClick(R.id.yellow_iv)
    void clickYellow(){
        SkinManager.getInstance().toggleTheme(SkinManager.YELLOW_THEME);
        restart();
    }

    @OnClick(R.id.blue_iv)
    void clickBlue(){
        SkinManager.getInstance().toggleTheme(SkinManager.BLUE_THEME);
        restart();
    }

    @OnClick(R.id.red_iv)
    void clickRed(){
        SkinManager.getInstance().toggleTheme(SkinManager.RED_THEME);
        restart();
    }

    @OnClick(R.id.orange_iv)
    void clickOrange(){
        SkinManager.getInstance().toggleTheme(SkinManager.ORANGE_THEME);
        restart();
    }

    public void restart(){
        EventBus.getDefault().post(new FinishActivityEvent());
        Intent mStartActivity = new Intent(this, HomeActivity.class);
        startActivity(mStartActivity);
        finish();
        //int mPendingIntentId = 123456;
        //PendingIntent mPendingIntent = PendingIntent.getActivity(this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        //AlarmManager mgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        //mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 10, mPendingIntent);
        //System.exit(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder != null){
            unbinder.unbind();
        }
    }
}
