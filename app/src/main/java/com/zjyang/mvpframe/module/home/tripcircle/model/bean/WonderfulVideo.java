package com.zjyang.mvpframe.module.home.tripcircle.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengjiayang on 2018/8/23.
 */

public class WonderfulVideo implements Parcelable{

    private String preivewPic;
    private String videoUrl;
    private List<String> tag;
    private String title;
    private String describe;
    private List<CommentInfo> commentList;

    public WonderfulVideo(String preivewPic) {
        this.preivewPic = preivewPic;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<CommentInfo> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentInfo> commentList) {
        this.commentList = commentList;
    }

    public String getPreivewPic() {
        return preivewPic;
    }

    public void setPreivewPic(String preivewPic) {
        this.preivewPic = preivewPic;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.preivewPic);
        dest.writeString(this.videoUrl);
        dest.writeStringList(this.tag);
        dest.writeString(this.title);
        dest.writeString(this.describe);
        dest.writeList(this.commentList);
    }

    protected WonderfulVideo(Parcel in) {
        this.preivewPic = in.readString();
        this.videoUrl = in.readString();
        this.tag = in.createStringArrayList();
        this.title = in.readString();
        this.describe = in.readString();
        this.commentList = new ArrayList<CommentInfo>();
        in.readList(this.commentList, CommentInfo.class.getClassLoader());
    }

    public static final Creator<WonderfulVideo> CREATOR = new Creator<WonderfulVideo>() {
        @Override
        public WonderfulVideo createFromParcel(Parcel source) {
            return new WonderfulVideo(source);
        }

        @Override
        public WonderfulVideo[] newArray(int size) {
            return new WonderfulVideo[size];
        }
    };
}
