package com.zjyang.mvpframe.db.bean;

import com.zjyang.mvpframe.module.login.model.bean.User;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 74215 on 2018/6/2.
 */

@Entity
public class DBUser {

    private String objectId;
    private String id;
    private String account;
    private String password;
    private String userPic;
    private String userName;
    private String describe;
    private String sex;
    private String levelName;
    private int level;

    @Generated(hash = 455852979)
    public DBUser(String objectId, String id, String account, String password,
            String userPic, String userName, String describe, String sex,
            String levelName, int level) {
        this.objectId = objectId;
        this.id = id;
        this.account = account;
        this.password = password;
        this.userPic = userPic;
        this.userName = userName;
        this.describe = describe;
        this.sex = sex;
        this.levelName = levelName;
        this.level = level;
    }
    @Generated(hash = 138933025)
    public DBUser() {
    }


    
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPic() {
        return this.userPic;
    }
    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getAccount() {
        return this.account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getObjectId() {
        return this.objectId;
    }
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }


    public User switchUser(DBUser dbUser){
        if(dbUser == null){
            return null;
        }
        User user = new User();
        user.setObjectId(dbUser.getObjectId());
        user.setAccount(dbUser.getAccount());
        user.setPassword(dbUser.getPassword());
        user.setUserName(dbUser.getUserName());
        user.setUserPic(dbUser.getUserPic());
        user.setDescribe(dbUser.getDescribe());
        user.setSex(dbUser.getSex());
        user.setLevelName(dbUser.getLevelName());
        user.setLevel(dbUser.getLevel());
        return user;
    }

}
