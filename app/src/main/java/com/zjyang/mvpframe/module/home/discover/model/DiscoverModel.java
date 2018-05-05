package com.zjyang.mvpframe.module.home.discover.model;

import com.zjyang.mvpframe.event.RequestVideoListEvent;
import com.zjyang.mvpframe.module.home.discover.DiscoverTasksContract;
import com.zjyang.mvpframe.module.home.model.bean.VideoInfo;
import com.zjyang.mvpframe.module.login.model.bean.User;
import com.zjyang.mvpframe.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 74215 on 2018/4/21.
 */

public class DiscoverModel implements DiscoverTasksContract.Model{

    private static final String TAG = "DiscoverModel";

    private int mCurSelectTabIndex;

    @Override
    public int getCurSelectTabIndex() {
        return mCurSelectTabIndex;
    }

    @Override
    public void setCurSelectTabIndex(int mCurSelectTabIndex) {
        this.mCurSelectTabIndex = mCurSelectTabIndex;
    }

    @Override
    public List<VideoInfo> getDefaultProvinceData() {
        List<VideoInfo> mVideoList = new ArrayList<>();
        String[] picUrl = new String[]{
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523797863813&di=c1a82078d1672426d666cf4c8bd284d1&imgtype=0&src=http%3A%2F%2Fwww.rui2.net%2Fuploadfile%2Fdata%2F2015%2F0623%2F20150623114232290.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523824983803&di=49ac1e588634c0405b8b43ace1170a29&imgtype=0&src=http%3A%2F%2Fpic.qiantucdn.com%2F58pic%2F16%2F96%2F59%2F33t58PICARc_1024.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523825017835&di=90359e5215580dbbea56efaa3f90ed7c&imgtype=0&src=http%3A%2F%2Fpic2.ooopic.com%2F11%2F25%2F28%2F31b1OOOPIC3e.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523825078043&di=2ac80a304706fbcc370ea5a6a2d751a3&imgtype=0&src=http%3A%2F%2Fwww.rui2.net%2Fuploadfile%2Foutput%2F2%2F2013%2F1004%2F5ba491db21616fe8.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523825117163&di=e65211e7015596cb127c25f539974fcf&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fgames%2Ftransform%2F20160614%2F9Jah-fxszmaa1989666.jpg",
                "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2288195174,495473604&fm=27&gp=0.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523825201873&di=99bdc703cda55dcc8765bf391595725f&imgtype=0&src=http%3A%2F%2Fi3.tietuku.com%2F2b33ceb94af6609d.png"
        };
        for(int i=0; i<7; i++){
            VideoInfo videoInfo = new VideoInfo();
            videoInfo.setVideoUrl("http://221.228.226.23/11/t/j/v/b/tjvbwspwhqdmgouolposcsfafpedmb/sh.yinyuetai.com/691201536EE4912BF7E4F1E2C67B8119.mp4");
            videoInfo.setStatus(1);
            videoInfo.setVideoThumbUrl(picUrl[i]);
            videoInfo.setUserPicUrl(picUrl[i]);
            videoInfo.setUserName("HELLO");
            mVideoList.add(videoInfo);
        }
        return mVideoList;
    }

    @Override
    public void getVideoDataByProvinceId(int provinceId) {
        BmobQuery<VideoInfo> bmobQuery = new BmobQuery<VideoInfo>();
        bmobQuery.addWhereEqualTo("provinceId", provinceId);
        bmobQuery.findObjects(new FindListener<VideoInfo>(){
            @Override
            public void done(List<VideoInfo> list, BmobException e) {
                if(e == null && list.size() > 0){
                    EventBus.getDefault().post(new RequestVideoListEvent(true, list));
                }else{
                    e.printStackTrace();
                    EventBus.getDefault().post(new RequestVideoListEvent(false, null));
                }
            }
        });
    }
}
