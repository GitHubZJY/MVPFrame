package com.zjyang.mvpframe.module.base;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.zjyang.mvpframe.module.home.discover.model.bean.Province;

/**
 * Created by 74215 on 2018/5/12.
 */

public class BaseFragment extends Fragment {

    public Province pageTitle;
    private int sort;

    public Province getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(Province pageTitle) {
        this.pageTitle = pageTitle;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
