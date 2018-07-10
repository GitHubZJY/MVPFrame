package com.zjyang.mvpframe.module.home.model;

import com.zjyang.mvpframe.event.GetHomeTabInfoEvent;
import com.zjyang.mvpframe.module.base.BaseFragment;
import com.zjyang.mvpframe.module.home.HomeTasksContract;
import com.zjyang.mvpframe.module.home.discover.view.DiscoverFragment;
import com.zjyang.mvpframe.module.home.discover.view.GridDiscoverFragment;
import com.zjyang.mvpframe.module.home.focus.FocusFragment;
import com.zjyang.mvpframe.module.home.me.MeFragment;
import com.zjyang.mvpframe.module.home.message.MessageFragment;
import com.zjyang.mvpframe.module.home.model.bean.TabInfo;
import com.zjyang.mvpframe.utils.LogUtil;
import com.zjyang.mvpframe.utils.TabComparator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 74215 on 2018/4/1.
 */

public class HomeModel implements HomeTasksContract.Model{

    private static final String TAG = "HomeModel";
    private List<BaseFragment> fragments = new ArrayList<>();
    GridDiscoverFragment mDiscoverFragment;
    FocusFragment mFocusFragment;
    MessageFragment mMessageFragment;
    MeFragment mMeFragment;

    private List<TabInfo> mTabInfoList = new ArrayList<>();
    //首页4个分页的顺序，可改动
    public static final int DISCOVER_TAB_SORT = 1;
    public static final int FOCUS_TAB_SORT = 2;
    public static final int MESSAGE_TAB_SORT = 3;
    public static final int USER_TAB_SORT = 4;
    private static final String DISCOVER_TAB_NAME = "discover";
    private static final String FOCUS_TAB_NAME = "focus";
    private static final String MESSAGE_TAB_NAME = "message";
    private static final String USER_TAB_NAME = "usercenter";

    public HomeModel() {
        initFragments();
        mTabInfoList.clear();
        mTabInfoList.add(new TabInfo(DISCOVER_TAB_NAME, DISCOVER_TAB_SORT, true));
        mTabInfoList.add(new TabInfo(FOCUS_TAB_NAME, FOCUS_TAB_SORT, true));
        mTabInfoList.add(new TabInfo(MESSAGE_TAB_NAME, MESSAGE_TAB_SORT, true));
        mTabInfoList.add(new TabInfo(USER_TAB_NAME, USER_TAB_SORT, true));
        sortTabFragments();
        //动态请求后台下发tab配置
        //requestTabInfo();
    }

    public void initFragments(){
        if(mDiscoverFragment == null){
            mDiscoverFragment = new GridDiscoverFragment();
            fragments.add(mDiscoverFragment);
        }
        if(mFocusFragment == null){
            mFocusFragment = new FocusFragment();
            fragments.add(mFocusFragment);
        }
        if(mMessageFragment == null){
            mMessageFragment = new MessageFragment();
            fragments.add(mMessageFragment);
        }
        if(mMeFragment == null){
            mMeFragment = new MeFragment();
            fragments.add(mMeFragment);
        }
    }

    public void sortTabFragments(){
        initFragments();
        for(TabInfo tabInfo: mTabInfoList){
            if(tabInfo.getTabName().equals(DISCOVER_TAB_NAME)){
                mDiscoverFragment.setSort(tabInfo.getSort());
            }
            if(tabInfo.getTabName().equals(FOCUS_TAB_NAME)){
                mFocusFragment.setSort(tabInfo.getSort());
            }
            if(tabInfo.getTabName().equals(MESSAGE_TAB_NAME)){
                mMessageFragment.setSort(tabInfo.getSort());
            }
            if(tabInfo.getTabName().equals(USER_TAB_NAME)){
                mMeFragment.setSort(tabInfo.getSort());
            }
        }
        Collections.sort(fragments, new TabComparator());
    }

    @Override
    public void requestTabInfo() {
        BmobQuery<TabInfo> bmobQuery = new BmobQuery<TabInfo>();
        bmobQuery.findObjects(new FindListener<TabInfo>(){
            @Override
            public void done(List<TabInfo> list, BmobException e) {
                if(e == null && list.size() > 0){
                    LogUtil.d(TAG, "requestTabInfo size: " + list.size());
                    mTabInfoList.clear();
                    mTabInfoList.addAll(list);
                    sortTabFragments();
                    EventBus.getDefault().post(new GetHomeTabInfoEvent());
                }else{
                    LogUtil.d(TAG, "requestTabInfo size: " + list.size());
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public List<BaseFragment> getFragments() {
        return fragments;
    }
}
