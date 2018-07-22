package com.zjyang.mvpframe.net;

import com.zjyang.mvpframe.module.login.model.bean.User;

/**
 * Created by 74215 on 2018/7/21.
 */

public class ResponseBean<T> {

    private String status;
    private String message;

    private T data;

    public ResponseBean() {
    }

    public ResponseBean(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
