package com.zjyang.mvpframe.module.login.model;

import com.zjyang.mvpframe.module.login.ILoginCallBack;
import com.zjyang.mvpframe.module.login.LoginTasksContract;
import com.zjyang.mvpframe.module.login.model.bean.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by zhengjiayang on 2018/3/1.
 */

public class LoginModel implements LoginTasksContract.Model{


    @Override
    public void login(String account, String password, final ILoginCallBack callBack) {

        BmobQuery<User> bmobQuery = new BmobQuery<User>();
        bmobQuery.addWhereEqualTo("account", account);
        bmobQuery.findObjects(new FindListener<User>(){
            @Override
            public void done(List<User> list, BmobException e) {
                if(e == null && list.size() > 0){
                    callBack.loginSuccess();
                }else{
                    e.printStackTrace();
                    callBack.loginFail(0);
                }
            }
        });
    }


}
