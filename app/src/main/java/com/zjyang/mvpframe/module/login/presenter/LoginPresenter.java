package com.zjyang.mvpframe.module.login.presenter;

import android.text.TextUtils;

import com.zjyang.mvpframe.module.base.UserDataManager;
import com.zjyang.mvpframe.module.login.ILoginCallBack;
import com.zjyang.mvpframe.module.login.LoginErrorCode;
import com.zjyang.mvpframe.module.login.LoginTasksContract;
import com.zjyang.mvpframe.module.login.model.LoginModel;
import com.zjyang.mvpframe.utils.HandlerUtils;

/**
 * Created by zhengjiayang on 2018/3/1.
 */

public class LoginPresenter implements LoginTasksContract.Presenter{

    private LoginTasksContract.View mLoginView;
    private LoginTasksContract.Model mLoginModel;

    public LoginPresenter(LoginTasksContract.View loginView) {
        mLoginView = loginView;
        mLoginModel = new LoginModel();
    }

    @Override
    public void checkLogin(String account, String password) {
        if(TextUtils.isEmpty(account)){
            mLoginView.showAccountNotNullTip();
            return;
        }
        if(TextUtils.isEmpty(password)){
            mLoginView.showPasswordNotNullTip();
            return;
        }
        mLoginView.showLoginAnim();
        mLoginModel.login(account, password, new ILoginCallBack() {
            @Override
            public void loginSuccess() {
                HandlerUtils.postDelay(new Runnable() {
                    @Override
                    public void run() {
                        mLoginView.jumpToHomeActivity();
                    }
                }, 1000);

            }

            @Override
            public void loginFail(final int errorStatus) {
                HandlerUtils.postDelay(new Runnable() {
                    @Override
                    public void run() {
                        if(errorStatus == LoginErrorCode.PASSWORD_ERROR){
                            mLoginView.showPwErrorToast();
                            mLoginView.resetInput();
                        }else if(errorStatus == LoginErrorCode.ACCOUNT_NOT_EXIST){
                            mLoginView.showAccountNotExist();
                            mLoginView.resetInput();
                        }
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void checkUserCache() {
        if(UserDataManager.getInstance().getCurUser() != null && mLoginView != null){
            mLoginView.jumpToHomeActivity();
        }
    }
}
