package com.zjyang.mvpframe.module.home.model.bean;

/**
 * Created by 74215 on 2018/5/12.
 */

public class TabInfo {

    private String tabName;
    private int sort;
    private boolean isVisible;

    public TabInfo(String tabName, int sort, boolean isVisible) {
        this.tabName = tabName;
        this.sort = sort;
        this.isVisible = isVisible;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
