package com.zjyang.mvpframe.module.home.tripcircle.model;

import com.zjyang.mvpframe.event.GetWonderfulVideoEvent;
import com.zjyang.mvpframe.module.home.tripcircle.TripCircleTasksContract;
import com.zjyang.mvpframe.module.home.tripcircle.model.bean.WonderfulVideo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by zhengjiayang on 2018/8/23.
 */

public class TripCircleModel implements TripCircleTasksContract.Model{

    @Override
    public List<String> getBannerData() {
        List<String> urlList = new ArrayList<>();
        urlList.add("http://pic41.photophoto.cn/20161217/0017030086344808_b.jpg");
        urlList.add("http://photocdn.sohu.com/20150114/Img407794285.jpg");
        urlList.add("http://img.zcool.cn/community/015372554281b00000019ae9803e5c.jpg");
        return urlList;
    }

    @Override
    public void getAllWonderfulVideo() {
        BmobQuery<WonderfulVideo> bmobQuery = new BmobQuery<WonderfulVideo>();
        bmobQuery.findObjects(new FindListener<WonderfulVideo>(){
            @Override
            public void done(List<WonderfulVideo> list, BmobException e) {
                if(e == null && list.size() > 0){
                    EventBus.getDefault().post(new GetWonderfulVideoEvent(true, list));
                }else{
                    e.printStackTrace();
                    EventBus.getDefault().post(new GetWonderfulVideoEvent(false, null));
                }
            }
        });
    }
}
