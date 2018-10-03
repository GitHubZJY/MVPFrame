package com.zjyang.mvpframe.module.collagedetail;

import com.zjyang.mvpframe.module.home.focus.bean.FindTripInfo;
import com.zjyang.mvpframe.module.home.tripcircle.model.bean.CommentInfo;
import com.zjyang.mvpframe.module.mapmark.model.bean.MapMark;

import java.util.List;

/**
 * Created by zhengjiayang on 2018/7/17.
 */

public interface CollageDetailTasksContract {

    interface View {
        void fillDataToCommentLv(List<CommentInfo> collageCommentList);
    }


    interface Model {
        void getCommentByCollageId(String collageId);
    }

    interface Presenter {
        void initCommentList();
    }
}
