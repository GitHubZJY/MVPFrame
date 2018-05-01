package com.zjyang.mvpframe.module.home.discover.model;

import com.zjyang.mvpframe.event.RequestVideoListEvent;
import com.zjyang.mvpframe.module.home.discover.DiscoverTasksContract;
import com.zjyang.mvpframe.module.home.model.bean.VideoInfo;
import com.zjyang.mvpframe.module.login.model.bean.User;
import com.zjyang.mvpframe.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 74215 on 2018/4/21.
 */

public class DiscoverModel implements DiscoverTasksContract.Model{

    private static final String TAG = "DiscoverModel";

    private int mCurSelectTabIndex;

    @Override
    public int getCurSelectTabIndex() {
        return mCurSelectTabIndex;
    }

    @Override
    public void setCurSelectTabIndex(int mCurSelectTabIndex) {
        this.mCurSelectTabIndex = mCurSelectTabIndex;
    }

    @Override
    public void getVideoDataByProvinceId(int provinceId) {
        BmobQuery<VideoInfo> bmobQuery = new BmobQuery<VideoInfo>();
        bmobQuery.addWhereEqualTo("provinceId", provinceId);
        bmobQuery.findObjects(new FindListener<VideoInfo>(){
            @Override
            public void done(List<VideoInfo> list, BmobException e) {
                if(e == null && list.size() > 0){
                    EventBus.getDefault().post(new RequestVideoListEvent(true, list));
                }else{
                    e.printStackTrace();
                    EventBus.getDefault().post(new RequestVideoListEvent(false, null));
                }
            }
        });
    }
}
