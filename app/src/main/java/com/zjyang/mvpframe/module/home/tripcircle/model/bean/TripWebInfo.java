package com.zjyang.mvpframe.module.home.tripcircle.model.bean;

/**
 * Created by zhengjiayang on 2018/9/30.
 */

public class TripWebInfo {

    private String name;
    private String icon;
    private String describe;
    private String url;

    public TripWebInfo() {
    }

    public TripWebInfo(String name, String icon, String describe, String url) {
        this.name = name;
        this.icon = icon;
        this.describe = describe;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
