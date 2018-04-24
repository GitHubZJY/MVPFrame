package com.zjyang.mvpframe.module.login.model.bean;

/**
 * Created by zhengjiayang on 2018/3/5.
 */

public class User {

    private String id;
    private String account;
    private String password;

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
}
