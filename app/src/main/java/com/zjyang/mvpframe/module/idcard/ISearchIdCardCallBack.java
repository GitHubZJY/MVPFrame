package com.zjyang.mvpframe.module.idcard;

import com.zjyang.mvpframe.module.idcard.model.bean.IdCardInfo;

/**
 * Created by zhengjiayang on 2018/3/2.
 */

public interface ISearchIdCardCallBack {

    void onSearchSuccess(IdCardInfo info);

    void onSearchFail();
}
