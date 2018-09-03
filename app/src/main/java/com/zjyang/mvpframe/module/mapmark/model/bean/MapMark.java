package com.zjyang.mvpframe.module.mapmark.model.bean;

/**
 * Created by zhengjiayang on 2018/9/3.
 */

public class MapMark {

    private int userId;
    private String markLocation;
    private String markTime;
    private double latitude;
    private double longitude;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMarkLocation() {
        return markLocation;
    }

    public void setMarkLocation(String markLocation) {
        this.markLocation = markLocation;
    }

    public String getMarkTime() {
        return markTime;
    }

    public void setMarkTime(String markTime) {
        this.markTime = markTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
