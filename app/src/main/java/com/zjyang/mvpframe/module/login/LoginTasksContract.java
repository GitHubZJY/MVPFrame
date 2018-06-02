package com.zjyang.mvpframe.module.login;

/**
 * Created by zhengjiayang on 2018/3/1.
 * 登陆页面接口协议
 */

public interface LoginTasksContract {

    interface View {
        void showPwErrorToast();
        void showAccountNotNullTip();
        void showPasswordNotNullTip();
        void jumpToHomeActivity();
        void showLoginAnim();
        void showAccountNotExist();
        void resetInput();
    }

    interface Presenter {
        void checkLogin(String account, String password);
        void checkUserCache();
    }

    interface Model {
        void login(String account, String password, ILoginCallBack callBack);
    }

}
