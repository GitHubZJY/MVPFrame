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
        urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523797863813&di=c1a82078d1672426d666cf4c8bd284d1&imgtype=0&src=http%3A%2F%2Fwww.rui2.net%2Fuploadfile%2Fdata%2F2015%2F0623%2F20150623114232290.jpg");
        urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523824983803&di=49ac1e588634c0405b8b43ace1170a29&imgtype=0&src=http%3A%2F%2Fpic.qiantucdn.com%2F58pic%2F16%2F96%2F59%2F33t58PICARc_1024.jpg");
        urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523825017835&di=90359e5215580dbbea56efaa3f90ed7c&imgtype=0&src=http%3A%2F%2Fpic2.ooopic.com%2F11%2F25%2F28%2F31b1OOOPIC3e.jpg");
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
