package com.zjyang.mvpframe.utils;

import com.zjyang.mvpframe.module.base.BaseFragment;

import java.util.Comparator;

/**
 * Created by 74215 on 2018/5/12.
 */

public class TabComparator implements Comparator<BaseFragment>{


    @Override
    public int compare(BaseFragment tab1, BaseFragment tab2) {
        if(tab1.getSort() > tab2.getSort()){
            return 1;
        }else if(tab1.getSort() < tab2.getSort()){
            return -1;
        }
        return 0;
    }


}
