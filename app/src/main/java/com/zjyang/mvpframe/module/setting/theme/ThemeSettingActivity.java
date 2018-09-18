package com.zjyang.mvpframe.module.setting.theme;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zjyang.mvpframe.MainActivity;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.event.FinishActivityEvent;
import com.zjyang.mvpframe.module.base.BaseActivity;
import com.zjyang.mvpframe.module.base.BasePresenter;
import com.zjyang.mvpframe.module.base.SkinManager;
import com.zjyang.mvpframe.module.home.view.HomeActivity;
import com.zjyang.mvpframe.ui.ShapeUtils;
import com.zjyang.mvpframe.utils.DrawUtils;
import com.zjyang.mvpframe.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 74215 on 2018/9/16.
 */

public class ThemeSettingActivity extends BaseActivity implements ThemeSettingAdapter.SelectThemeListener{

    private Unbinder unbinder;

    @BindView(R.id.bottom_camera_iv)
    ImageView mCameraIv;
    @BindView(R.id.bottom_camera_bg)
    ImageView mCameraBg;
    @BindView(R.id.tab_group)
    LinearLayout mTabGroup;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.toolbar_right_tv)
    TextView mSaveTv;

    @BindView(R.id.theme_lv)
    RecyclerView mThemeLv;

    ThemeSettingAdapter mThemeAdapter;


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

        mThemeAdapter = new ThemeSettingAdapter(this, SkinManager.getInstance().getThemeList());
        mThemeAdapter.setSelectThemeListener(this);
        mThemeLv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mThemeLv.setAdapter(mThemeAdapter);
    }

    @Override
    public void select(ThemeInfo themeInfo) {
        mTabGroup.setBackgroundColor(Color.parseColor(themeInfo.getThemeColor()));
        mTitleTv.setBackgroundColor(Color.parseColor(themeInfo.getThemeColor()));
        mCameraIv.setBackground(ShapeUtils.getRoundRectDrawable(180, Color.parseColor(themeInfo.getThemeColor())));
    }

    @OnClick(R.id.toolbar_right_tv)
    void clickSave(){
        if(mThemeAdapter.getCurApplyTheme() == null){
            ToastUtils.showToast(this, "应用失败");
            return;
        }
        SkinManager.getInstance().toggleTheme(mThemeAdapter.getCurApplyTheme().getThemeId());
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
