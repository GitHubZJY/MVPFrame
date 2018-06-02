package com.zjyang.mvpframe.module.login.model.bean;

import com.zjyang.mvpframe.db.bean.DBUser;

/**
 * Created by zhengjiayang on 2018/3/5.
 */

public class User {

    private String id;
    private String account;
    private String password;
    private String userPic;
    private String userName;

    public User() {
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

    public DBUser switchDBUser(User user){
        if(user == null){
            return null;
        }
        DBUser dbUser = new DBUser();
        dbUser.setId(user.getId());
        dbUser.setAccount(user.getAccount());
        dbUser.setPassword(user.getPassword());
        dbUser.setUserName(user.getUserName());
        dbUser.setUserPic(user.getUserPic());
        return dbUser;
    }
}
