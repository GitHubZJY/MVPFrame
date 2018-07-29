package com.zjyang.mvpframe.module.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.zjyang.mvpframe.module.base.BaseFragment;

import java.util.List;

/**
 * Created by 74215 on 2018/4/9.
 */

public class HomePagerAdapter extends FragmentPagerAdapter{

    private List<BaseFragment> mViewList;


    public HomePagerAdapter(FragmentManager fm, List<BaseFragment> mViewList) {
        super(fm);
        this.mViewList = mViewList;
    }

    @Override
    public Fragment getItem(int position) {
        return mViewList.get(position);
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        // 最简单解决 notifyDataSetChanged() 页面不刷新问题的方法
        return POSITION_NONE;
    }

    public String getPagerTitle(int position) {
        if(mViewList == null || mViewList.isEmpty()){
            return "";
        }
        return mViewList.get(position).getPageTitle().getProvinceName();
    }
}
