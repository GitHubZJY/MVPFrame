package com.zjyang.mvpframe.module.home.message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.base.BaseFragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 74215 on 2018/5/12.
 */

public class MessageFragment extends BaseFragment{
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, null);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
