package com.zjyang.mvpframe.module.home.focus.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhengjiayang on 2018/9/30.
 */

public class FindTripInfo implements Parcelable {

    private String name;
    private String icon;
    private String describe;
    private String url;

    public FindTripInfo() {
    }

    public FindTripInfo(String name, String icon, String describe, String url) {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.icon);
        dest.writeString(this.describe);
        dest.writeString(this.url);
    }

    protected FindTripInfo(Parcel in) {
        this.name = in.readString();
        this.icon = in.readString();
        this.describe = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<FindTripInfo> CREATOR = new Parcelable.Creator<FindTripInfo>() {
        @Override
        public FindTripInfo createFromParcel(Parcel source) {
            return new FindTripInfo(source);
        }

        @Override
        public FindTripInfo[] newArray(int size) {
            return new FindTripInfo[size];
        }
    };
}
