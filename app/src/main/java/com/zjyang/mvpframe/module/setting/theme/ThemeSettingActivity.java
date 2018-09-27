package com.zjyang.mvpframe.module.setting.theme;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zjyang.base.base.BaseActivity;
import com.zjyang.base.base.BasePresenter;
import com.zjyang.base.base.SkinManager;
import com.zjyang.base.bean.ThemeInfo;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.event.FinishActivityEvent;
import com.zjyang.mvpframe.module.home.view.HomeActivity;
import com.zjyang.base.utils.ShapeUtils;
import com.zjyang.base.utils.ToastUtils;

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
        mThemeAdapter.setCurSelect(SkinManager.getInstance().getCurThemeIndexInList());
        mThemeLv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mThemeLv.setAdapter(mThemeAdapter);
        mThemeAdapter.notifyDataSetChanged();

        mTabGroup.setBackgroundColor(SkinManager.getInstance().getPrimaryColor());
        mTitleTv.setBackgroundColor(SkinManager.getInstance().getPrimaryColor());
        mCameraIv.setBackground(ShapeUtils.getRoundRectDrawable(180, SkinManager.getInstance().getPrimaryColor()));
        mTitleTv.setTextColor(SkinManager.getInstance().getPrimaryTextColor());
        for(int i=0; i<mTabGroup.getChildCount(); i++){
            TextView mTabTv = (TextView)(mTabGroup.getChildAt(i));
            mTabTv.setTextColor(SkinManager.getInstance().getPrimaryTextColor());
        }
    }

    @Override
    public void select(ThemeInfo themeInfo) {
        mTabGroup.setBackgroundColor(Color.parseColor(themeInfo.getThemeColor()));
        mTitleTv.setBackgroundColor(Color.parseColor(themeInfo.getThemeColor()));
        mCameraIv.setBackground(ShapeUtils.getRoundRectDrawable(180, Color.parseColor(themeInfo.getThemeColor())));
        mTitleTv.setTextColor(Color.parseColor(themeInfo.getTextColor()));
        for(int i=0; i<mTabGroup.getChildCount(); i++){
            TextView mTabTv = (TextView)(mTabGroup.getChildAt(i));
            mTabTv.setTextColor(Color.parseColor(themeInfo.getTextColor()));
        }
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder != null){
            unbinder.unbind();
        }
    }
}
