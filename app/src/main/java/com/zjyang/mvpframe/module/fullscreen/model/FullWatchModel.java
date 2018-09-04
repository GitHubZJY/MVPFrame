package com.zjyang.mvpframe.module.fullscreen.model;

import com.zjyang.mvpframe.event.FocusResultEvent;
import com.zjyang.mvpframe.module.base.BaseModel;
import com.zjyang.mvpframe.module.fullscreen.FullWatchTasksContract;
import com.zjyang.mvpframe.module.fullscreen.model.bean.RelationShip;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 74215 on 2018/9/4.
 */

public class FullWatchModel extends BaseModel implements FullWatchTasksContract.Model{

    @Override
    public void insertFocusData(String curUserId, String authorUserId) {
        RelationShip relationShip = new RelationShip();
        relationShip.setMasterUserId(curUserId);
        relationShip.setFriendId(authorUserId);
        relationShip.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    EventBus.getDefault().post(new FocusResultEvent(true));
                }else{
                    EventBus.getDefault().post(new FocusResultEvent(false));
                }
            }
        });
    }
}
