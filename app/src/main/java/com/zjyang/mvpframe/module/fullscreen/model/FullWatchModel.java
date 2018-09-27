package com.zjyang.mvpframe.module.fullscreen.model;

import com.zjyang.base.base.BaseModel;
import com.zjyang.mvpframe.event.FocusResultEvent;
import com.zjyang.mvpframe.module.fullscreen.FullWatchTasksContract;
import com.zjyang.mvpframe.module.fullscreen.model.bean.RelationShip;
import com.zjyang.base.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 74215 on 2018/9/4.
 */

public class FullWatchModel extends BaseModel implements FullWatchTasksContract.Model{

    public static final String TAG = "FullWatchModel";
    private FullWatchModelCallback mCallback;

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

    @Override
    public void isFocusCurUser(String curUserId, final String authorUserId) {
        BmobQuery<RelationShip> bmobQuery = new BmobQuery<RelationShip>();
        bmobQuery.addWhereEqualTo("masterUserId", curUserId);
        bmobQuery.findObjects(new FindListener<RelationShip>(){
            @Override
            public void done(List<RelationShip> list, BmobException e) {
                if(e == null && list.size() > 0){
                    LogUtil.d(TAG, "共查到" + list.size() +"条好友ID");
                    boolean mHasFocus = false;
                    for(RelationShip relation : list){
                        if(relation.getFriendId() == authorUserId){
                            mHasFocus = true;
                            break;
                        }
                    }
                    if(mCallback != null){
                        mCallback.isFocus(mHasFocus);
                    }
                }else{
                    if(mCallback != null){
                        mCallback.isFocus(false);
                    }
                    e.printStackTrace();
                }
            }
        });
    }

    public void setFocusCallBack(FullWatchModelCallback callback){
        mCallback = callback;
    }
}
