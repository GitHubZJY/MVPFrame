package com.zjyang.mvpframe.db.bean;

import com.zjyang.mvpframe.module.login.model.bean.User;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 74215 on 2018/6/2.
 */

@Entity
public class DBUser {

    private String id;
    private String account;
    private String password;
    private String userPic;
    private String userName;
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
    @Generated(hash = 900494697)
    public DBUser(String id, String account, String password, String userPic,
            String userName) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.userPic = userPic;
        this.userName = userName;
    }
    @Generated(hash = 138933025)
    public DBUser() {
    }


    public User switchUser(DBUser dbUser){
        if(dbUser == null){
            return null;
        }
        User user = new User();
        user.setId(dbUser.getId());
        user.setAccount(dbUser.getAccount());
        user.setPassword(dbUser.getPassword());
        user.setUserName(dbUser.getUserName());
        user.setUserPic(dbUser.getUserPic());
        return user;
    }

}
