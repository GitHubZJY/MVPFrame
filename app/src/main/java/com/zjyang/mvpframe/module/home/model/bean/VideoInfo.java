package com.zjyang.mvpframe.module.home.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by 74215 on 2018/4/1.
 */

public class VideoInfo extends BmobObject implements Parcelable {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.videoUrl);
        dest.writeString(this.videoThumbUrl);
        dest.writeInt(this.status);
        dest.writeString(this.userId);
        dest.writeString(this.userPicUrl);
        dest.writeString(this.userName);
        dest.writeInt(this.provinceId);
        dest.writeString(this.watchNum);
        dest.writeString(this.duration);
        dest.writeList(this.commentList);
        dest.writeInt(this.likeNum);
    }

    protected VideoInfo(Parcel in) {
        this.videoUrl = in.readString();
        this.videoThumbUrl = in.readString();
        this.status = in.readInt();
        this.userId = in.readString();
        this.userPicUrl = in.readString();
        this.userName = in.readString();
        this.provinceId = in.readInt();
        this.watchNum = in.readString();
        this.duration = in.readString();
        this.commentList = new ArrayList<Comment>();
        in.readList(this.commentList, Comment.class.getClassLoader());
        this.likeNum = in.readInt();
    }

    public static final Parcelable.Creator<VideoInfo> CREATOR = new Parcelable.Creator<VideoInfo>() {
        @Override
        public VideoInfo createFromParcel(Parcel source) {
            return new VideoInfo(source);
        }

        @Override
        public VideoInfo[] newArray(int size) {
            return new VideoInfo[size];
        }
    };
}
