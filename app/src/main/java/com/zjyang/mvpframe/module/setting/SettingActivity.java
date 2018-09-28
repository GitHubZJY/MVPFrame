package com.zjyang.mvpframe.module.setting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.zjyang.base.base.BaseActivity;
import com.zjyang.base.base.BasePresenter;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.event.FinishActivityEvent;
import com.zjyang.base.widget.BaseSettingItem;
import com.zjyang.mvpframe.module.setting.theme.ThemeSettingActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 74215 on 2018/9/16.
 */

public class SettingActivity extends BaseActivity {

    private Unbinder unbinder;

    @BindView(R.id.setting_theme_entrance)
    BaseSettingItem mThemeEntrance;
    @BindView(R.id.toolbar_left_btn)
    Button mBackBtn;

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        unbinder = ButterKnife.bind(this);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @OnClick(R.id.setting_theme_entrance)
    void clickToSettingTheme(){
        Intent intent = new Intent(this, ThemeSettingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.toolbar_left_btn)
    void clickBack(){
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFinishActivityEvent(FinishActivityEvent event){
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder != null){
            unbinder.unbind();
        }
        EventBus.getDefault().unregister(this);
    }
}
