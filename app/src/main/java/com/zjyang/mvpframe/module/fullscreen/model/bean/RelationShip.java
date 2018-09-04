package com.zjyang.mvpframe.module.fullscreen.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by 74215 on 2018/9/5.
 */

public class RelationShip extends BmobObject{

    private String masterUserId;
    private String friendId;

    public String getMasterUserId() {
        return masterUserId;
    }

    public void setMasterUserId(String masterUserId) {
        this.masterUserId = masterUserId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }
}
