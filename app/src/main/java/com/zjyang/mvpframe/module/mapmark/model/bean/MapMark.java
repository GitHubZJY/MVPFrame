package com.zjyang.mvpframe.module.mapmark.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobObject;

/**
 * Created by zhengjiayang on 2018/9/3.
 */

public class MapMark extends BmobObject implements Parcelable {

    private String userId;
    private String markLocation;
    private String markTime;
    private String city;
    private String cityCode;
    private String latitude;
    private String longitude;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.markLocation);
        dest.writeString(this.markTime);
        dest.writeString(this.city);
        dest.writeString(this.cityCode);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
    }

    public MapMark() {
    }

    protected MapMark(Parcel in) {
        this.userId = in.readString();
        this.markLocation = in.readString();
        this.markTime = in.readString();
        this.city = in.readString();
        this.cityCode = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
    }

    public static final Parcelable.Creator<MapMark> CREATOR = new Parcelable.Creator<MapMark>() {
        @Override
        public MapMark createFromParcel(Parcel source) {
            return new MapMark(source);
        }

        @Override
        public MapMark[] newArray(int size) {
            return new MapMark[size];
        }
    };
}
