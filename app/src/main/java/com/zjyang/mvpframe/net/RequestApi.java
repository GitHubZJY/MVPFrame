package com.zjyang.mvpframe.net;

import com.zjyang.mvpframe.module.login.model.bean.User;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by 74215 on 2018/7/21.
 */

public interface RequestApi {

    String BASE_URL = "http://rap2api.taobao.org/app/mock/19214/api/";

    @GET("login")
    Observable<ResponseBean<User>> getUserInfo();
}
