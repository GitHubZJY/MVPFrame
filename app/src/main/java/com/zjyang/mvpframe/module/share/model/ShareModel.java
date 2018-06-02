package com.zjyang.mvpframe.module.share.model;

import com.zjyang.mvpframe.event.ShareResultEvent;
import com.zjyang.mvpframe.module.base.UserDataManager;
import com.zjyang.mvpframe.module.home.model.bean.VideoInfo;
import com.zjyang.mvpframe.module.login.model.bean.User;
import com.zjyang.mvpframe.module.share.ShareTaskContracts;
import com.zjyang.mvpframe.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by 74215 on 2018/5/26.
 */

public class ShareModel implements ShareTaskContracts.Model{

    private static final String TAG = "ShareModel";


    @Override
    public void uploadVideoFile(String videoPath) {
        final BmobFile video = new BmobFile(new File(videoPath));
        final User user = UserDataManager.getInstance().getCurUser();
        if(user == null){
            return;
        }
        video.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    LogUtil.d(TAG, "上传视频成功!");
                    String videoStringUrl = video.getFileUrl();
                    VideoInfo videoInfo = new VideoInfo();
                    videoInfo.setVideoUrl(videoStringUrl);
                    videoInfo.setProvinceId(1);
                    videoInfo.setUserPicUrl(user.getUserPic());
                    videoInfo.setUserName(user.getUserName());
                    videoInfo.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                EventBus.getDefault().post(new ShareResultEvent(true));
                            }else{
                                EventBus.getDefault().post(new ShareResultEvent(false));
                            }
                        }
                    });
                }else {
                    EventBus.getDefault().post(new ShareResultEvent(false));
                    LogUtil.d(TAG, "上传视频失败-->"+e.getErrorCode());
                }
            }
        });
    }
}
