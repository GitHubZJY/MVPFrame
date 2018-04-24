package com.zjyang.mvpframe.module.idcard.model.bean;

/**
 * Created by zhengjiayang on 2018/3/2.
 */

public class IdCardInfo {

    private String area;
    private String sex;
    private String birthday;
    private String verify;

    public IdCardInfo() {
    }

    public IdCardInfo(String mArea, String mSex, String mBirthday, String verify) {
        this.area = mArea;
        this.sex = mSex;
        this.birthday = mBirthday;
        this.verify = verify;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String mArea) {
        this.area = mArea;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String mSex) {
        this.sex = mSex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String mBirthday) {
        this.birthday = mBirthday;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

}
