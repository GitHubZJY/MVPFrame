package com.zjyang.mvpframe.module.collagedetail.model;

import com.zjyang.base.base.BaseModel;
import com.zjyang.base.utils.LogUtil;
import com.zjyang.mvpframe.event.GetMapMarkEvent;
import com.zjyang.mvpframe.module.collagedetail.CollageDetailTasksContract;
import com.zjyang.mvpframe.module.home.focus.bean.FindTripInfo;
import com.zjyang.mvpframe.module.mapmark.MapMarkTasksContract;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by zhengjiayang on 2018/9/3.
 */

public class CollageDetailModel extends BaseModel implements CollageDetailTasksContract.Model{

    public static final String TAG = "MapMarkModel";

    @Override
    public void getCommentByCollageId(String userId) {
        BmobQuery<FindTripInfo> bmobQuery = new BmobQuery<FindTripInfo>();
        bmobQuery.addWhereEqualTo("userId", userId);
//        bmobQuery.findObjects(new FindListener<MapMark>(){
//            @Override
//            public void done(List<MapMark> list, BmobException e) {
//                if(e == null && list.size() > 0){
//                    LogUtil.d(TAG, "共查到" + list.size() +"条位置数据");
//                   // EventBus.getDefault().post(new GetMapMarkEvent(true, list));
//                }else{
//                    e.printStackTrace();
//                    EventBus.getDefault().post(new GetMapMarkEvent(false, null));
//                }
//            }
//        });
    }
}
