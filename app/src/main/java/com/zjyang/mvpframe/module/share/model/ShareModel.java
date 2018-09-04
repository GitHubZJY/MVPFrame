package com.zjyang.mvpframe.module.share.model;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.example.zjy.player.utils.VideoUtils;
import com.zjyang.mvpframe.event.ShareResultEvent;
import com.zjyang.mvpframe.module.base.UserDataManager;
import com.zjyang.mvpframe.module.home.model.bean.VideoInfo;
import com.zjyang.mvpframe.module.login.model.bean.User;
import com.zjyang.mvpframe.module.mapmark.model.bean.MapMark;
import com.zjyang.mvpframe.module.share.ShareTaskContracts;
import com.zjyang.mvpframe.utils.FileUtils;
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

    private MapMark mMapMark;

    @Override
    public void setLocationData(MapMark mapMark) {
        mMapMark = mapMark;
    }

    @Override
    public void uploadVideoFile(final String videoPath, Bitmap thumbBm) {
        final BmobFile thumb;
        final String thumbPath = FileUtils.saveBitmapToFile(thumbBm, FileUtils.getFileNameByPath(videoPath));
        if(!TextUtils.isEmpty(thumbPath)){
            LogUtil.d(TAG, "本地截图路径: " + thumbPath);
            thumb = new BmobFile(new File(thumbPath));
            thumb.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    String thumbUrl = "";
                    if (e == null){
                        LogUtil.d(TAG, "上传截图成功!");
                        thumbUrl = thumb.getFileUrl();
                        FileUtils.deleteFile(thumbPath);
                        LogUtil.d(TAG, "删除本地截图缓存!");
                    }
                    LogUtil.d(TAG, "开始上传视频!");
                    uploadVideo(videoPath, thumbUrl);
                }
            });
        }



    }

    private void uploadVideo(String videoPath, final String thumbUrl){
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
                    videoInfo.setProvinceId(Integer.parseInt(mMapMark.getCityCode()));
                    videoInfo.setLocation(mMapMark.getMarkLocation());
                    videoInfo.setUserId(user.getObjectId());
                    videoInfo.setUserPicUrl(user.getUserPic());
                    videoInfo.setUserName(user.getUserName());
                    videoInfo.setVideoThumbUrl(thumbUrl);
                    videoInfo.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                uploadAddressData(mMapMark);
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

    private void uploadAddressData(MapMark mapMark){
        mapMark.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    LogUtil.d(TAG, "上传位置信息成功");
                }else{
                    LogUtil.d(TAG, "上传位置信息失败-->"+e.getErrorCode());
                }
            }
        });
    }
}
