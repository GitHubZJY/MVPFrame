package com.zjyang.mvpframe.module.idcard;

import com.zjyang.mvpframe.module.idcard.model.bean.IdCardInfo;

/**
 * Created by zhengjiayang on 2018/3/2.
 */

public interface IdCardTasksContract {

    interface View {
        void showIdCardNotNullTip();
        void showSearchResult(IdCardInfo idCardInfo);
        void searchFail();
    }

    interface Presenter {
        void checkAndSearch(String idcard);
    }

    interface Model {
        void getInfoByIdCard(String idcard, ISearchIdCardCallBack callBack);
    }
}
