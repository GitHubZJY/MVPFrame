package com.zjyang.mvpframe.module.home.discover.model.bean;

/**
 * Created by 74215 on 2018/7/28.
 */

public class Province {

    private int provinceId;
    private String provinceName;

    public Province() {
    }

    public Province(int provinceId, String provinceName) {
        this.provinceId = provinceId;
        this.provinceName = provinceName;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}
