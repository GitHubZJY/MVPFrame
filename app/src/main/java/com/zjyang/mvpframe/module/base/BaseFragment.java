package com.zjyang.mvpframe.module.base;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * Created by 74215 on 2018/5/12.
 */

public class BaseFragment extends Fragment {

    private int sort;

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
