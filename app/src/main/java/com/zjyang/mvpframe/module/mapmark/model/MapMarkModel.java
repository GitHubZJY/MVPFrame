package com.zjyang.mvpframe.module.mapmark.model;

import com.zjyang.mvpframe.event.GetMapMarkEvent;
import com.zjyang.mvpframe.module.base.BaseModel;
import com.zjyang.mvpframe.module.mapmark.MapMarkTasksContract;
import com.zjyang.mvpframe.module.mapmark.model.bean.MapMark;
import com.zjyang.mvpframe.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by zhengjiayang on 2018/9/3.
 */

public class MapMarkModel extends BaseModel implements MapMarkTasksContract.Model{

    public static final String TAG = "MapMarkModel";

    @Override
    public void getMarkDataByUserId(String userId) {
        BmobQuery<MapMark> bmobQuery = new BmobQuery<MapMark>();
        bmobQuery.addWhereEqualTo("userId", userId);
        bmobQuery.findObjects(new FindListener<MapMark>(){
            @Override
            public void done(List<MapMark> list, BmobException e) {
                if(e == null && list.size() > 0){
                    LogUtil.d(TAG, "共查到" + list.size() +"条位置数据");
                    EventBus.getDefault().post(new GetMapMarkEvent(true, list));
                }else{
                    e.printStackTrace();
                    EventBus.getDefault().post(new GetMapMarkEvent(false, null));
                }
            }
        });
    }
}
