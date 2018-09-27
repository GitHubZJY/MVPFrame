package com.zjyang.mvpframe.module.login.model;

import com.zjyang.base.base.BaseModel;
import com.zjyang.mvpframe.module.UserDataManager;
import com.zjyang.mvpframe.module.login.ILoginCallBack;
import com.zjyang.mvpframe.module.login.LoginErrorCode;
import com.zjyang.mvpframe.module.login.LoginTasksContract;
import com.zjyang.mvpframe.module.login.model.bean.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by zhengjiayang on 2018/3/1.
 */

public class LoginModel extends BaseModel implements LoginTasksContract.Model{


    @Override
    public void login(String account, final String password, final ILoginCallBack callBack) {

        BmobQuery<User> bmobQuery = new BmobQuery<User>();
        bmobQuery.addWhereEqualTo("account", account);
        bmobQuery.findObjects(new FindListener<User>(){
            @Override
            public void done(List<User> list, BmobException e) {
                if(e == null && list.size() > 0){
                    User user = list.get(0);
                    if(user.getPassword().equals(password)){
                        UserDataManager.getInstance().setCurUser(user);
                        callBack.loginSuccess();
                    }else{
                        callBack.loginFail(LoginErrorCode.PASSWORD_ERROR);
                    }
                }else{
                    e.printStackTrace();
                    callBack.loginFail(LoginErrorCode.ACCOUNT_NOT_EXIST);
                }
            }
        });


//        AppApplication.getRequestApi().getUserInfo().
//                subscribeOn(Schedulers.io()).
//                observeOn(AndroidSchedulers.mainThread()).
//                subscribe(new Observer<ResponseBean<User>>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//
//            }
//
//            @Override
//            public void onNext(ResponseBean<User> responseBean) {
//                User user = responseBean.getData();
//                if(user != null){
//                    UserDataManager.getInstance().setCurUser(user);
//                    callBack.loginSuccess();
//                }else{
//                    callBack.loginFail(LoginErrorCode.PASSWORD_ERROR);
//                }
//            }
//        });
    }


}
