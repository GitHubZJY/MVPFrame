package com.zjyang.mvpframe.module.login;

/**
 * Created by zhengjiayang on 2018/3/1.
 * 登陆回调接口
 */

public interface ILoginCallBack {

    void loginSuccess();

    void loginFail(int errorStatus);

}
