package com.zjyang.mvpframe.module.login.presenter;

import android.text.TextUtils;

import com.zjyang.base.base.BasePresenter;
import com.zjyang.mvpframe.module.UserDataManager;
import com.zjyang.mvpframe.module.login.ILoginCallBack;
import com.zjyang.mvpframe.module.login.LoginErrorCode;
import com.zjyang.mvpframe.module.login.LoginTasksContract;
import com.zjyang.mvpframe.module.login.model.LoginModel;
import com.zjyang.base.utils.HandlerUtils;

/**
 * Created by zhengjiayang on 2018/3/1.
 */

public class LoginPresenter extends BasePresenter<LoginTasksContract.View, LoginModel> implements LoginTasksContract.Presenter{


    @Override
    public LoginModel createModel() {
        return new LoginModel();
    }

    @Override
    public void checkLogin(String account, String password) {
        if(TextUtils.isEmpty(account)){
            mView.showAccountNotNullTip();
            return;
        }
        if(TextUtils.isEmpty(password)){
            mView.showPasswordNotNullTip();
            return;
        }
        mView.showLoginAnim();
        mModel.login(account, password, new ILoginCallBack() {
            @Override
            public void loginSuccess() {
                HandlerUtils.postDelay(new Runnable() {
                    @Override
                    public void run() {
                        mView.jumpToHomeActivity();
                    }
                }, 1000);

            }

            @Override
            public void loginFail(final int errorStatus) {
                HandlerUtils.postDelay(new Runnable() {
                    @Override
                    public void run() {
                        if(errorStatus == LoginErrorCode.PASSWORD_ERROR){
                            mView.showPwErrorToast();
                            mView.resetInput();
                        }else if(errorStatus == LoginErrorCode.ACCOUNT_NOT_EXIST){
                            mView.showAccountNotExist();
                            mView.resetInput();
                        }
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void checkUserCache() {
        if(UserDataManager.getInstance().getCurUser() != null && mView != null){
            mView.jumpToHomeActivity();
        }
    }
}
