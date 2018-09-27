package com.zjyang.mvpframe.module.rank.presenter;

import com.zjyang.base.base.BasePresenter;
import com.zjyang.mvpframe.module.rank.RankTasksContract;
import com.zjyang.mvpframe.module.rank.model.RankModel;

/**
 * Created by 74215 on 2018/9/1.
 */

public class RankPresenter extends BasePresenter<RankTasksContract.View, RankModel> implements RankTasksContract.Presenter{


    @Override
    public RankModel createModel() {
        return null;
    }
}
