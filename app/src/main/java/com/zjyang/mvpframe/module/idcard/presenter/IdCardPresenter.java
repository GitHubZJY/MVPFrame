package com.zjyang.mvpframe.module.idcard.presenter;

import android.text.TextUtils;

import com.zjyang.mvpframe.module.idcard.ISearchIdCardCallBack;
import com.zjyang.mvpframe.module.idcard.IdCardTasksContract;
import com.zjyang.mvpframe.module.idcard.model.IdCardModel;
import com.zjyang.mvpframe.module.idcard.model.bean.IdCardInfo;
import com.zjyang.mvpframe.utils.LogUtil;

/**
 * Created by zhengjiayang on 2018/3/2.
 */

public class IdCardPresenter implements IdCardTasksContract.Presenter{

    private IdCardTasksContract.View mView;
    private IdCardTasksContract.Model mModel;

    public IdCardPresenter(IdCardTasksContract.View mView) {
        this.mView = mView;
        mModel = new IdCardModel();
    }

    @Override
    public void checkAndSearch(String idcard) {
        if(TextUtils.isEmpty(idcard)){
            mView.showIdCardNotNullTip();
            return;
        }

        mModel.getInfoByIdCard(idcard, new ISearchIdCardCallBack() {
            @Override
            public void onSearchSuccess(IdCardInfo info) {
                mView.showSearchResult(info);
            }

            @Override
            public void onSearchFail() {
                mView.searchFail();
            }
        });

    }
}
