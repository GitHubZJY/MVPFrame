package com.zjyang.mvpframe.module.login.model.bean;

import com.zjyang.mvpframe.db.bean.DBUser;

/**
 * Created by zhengjiayang on 2018/3/5.
 */

public class User {

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

    public User() {
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public DBUser switchDBUser(User user){
        if(user == null){
            return null;
        }
        DBUser dbUser = new DBUser();
        dbUser.setObjectId(user.getObjectId());
        dbUser.setAccount(user.getAccount());
        dbUser.setPassword(user.getPassword());
        dbUser.setUserName(user.getUserName());
        dbUser.setUserPic(user.getUserPic());
        dbUser.setDescribe(user.getDescribe());
        dbUser.setSex(user.getSex());
        dbUser.setLevelName(user.getLevelName());
        dbUser.setLevel(user.getLevel());
        return dbUser;
    }
}
