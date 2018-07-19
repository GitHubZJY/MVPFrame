package com.zjyang.mvpframe.module.myvideo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 74215 on 2018/7/18.
 */

public class MyVideoActivity extends BaseActivity{

    public static final String TAG = "MyVideoActivity";
    private Unbinder unbinder;

    public static void go(Context context){
        Intent intent = new Intent(context, MyVideoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);// 允许使用transitions
        setContentView(R.layout.activity_my_video);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
