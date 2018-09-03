package com.zjyang.mvpframe.module.mapmark.model;

import com.zjyang.mvpframe.event.GetMapMarkEvent;
import com.zjyang.mvpframe.module.base.BaseModel;
import com.zjyang.mvpframe.module.mapmark.MapMarkTasksContract;
import com.zjyang.mvpframe.module.mapmark.model.bean.MapMark;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengjiayang on 2018/9/3.
 */

public class MapMarkModel extends BaseModel implements MapMarkTasksContract.Model{

    @Override
    public void getMarkDataByUserId(int userId) {
        List<MapMark> markList = new ArrayList<>();
        markList.add(new MapMark());
        EventBus.getDefault().post(new GetMapMarkEvent(true, markList));
    }
}
