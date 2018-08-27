package com.zjyang.mvpframe.module.home.tripcircle.model.bean;

import java.util.List;

/**
 * Created by zhengjiayang on 2018/8/23.
 */

public class WonderfulVideo {

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
}
