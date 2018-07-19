package com.zjyang.mvpframe.module.home.model.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by 74215 on 2018/4/1.
 */

public class VideoInfo extends BmobObject{

    private String videoUrl;
    private String videoThumbUrl;
    private int status;
    private String userId;
    private String userPicUrl;
    private String userName;
    private int provinceId;
    private String watchNum;
    private String duration;
    private List<Comment> commentList;
    private int likeNum;

    public VideoInfo() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getWatchNum() {
        return watchNum;
    }

    public void setWatchNum(String watchNum) {
        this.watchNum = watchNum;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPicUrl() {
        return userPicUrl;
    }

    public void setUserPicUrl(String userPicUrl) {
        this.userPicUrl = userPicUrl;
    }

    public VideoInfo(String videoUrl, int status) {
        this.videoUrl = videoUrl;
        this.status = status;
    }

    public String getVideoThumbUrl() {
        return videoThumbUrl;
    }

    public void setVideoThumbUrl(String videoThumbUrl) {
        this.videoThumbUrl = videoThumbUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
