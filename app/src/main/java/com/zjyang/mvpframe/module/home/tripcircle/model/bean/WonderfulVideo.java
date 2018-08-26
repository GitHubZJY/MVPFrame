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

    public String getPreivewPic() {
        return preivewPic;
    }

    public void setPreivewPic(String preivewPic) {
        this.preivewPic = preivewPic;
    }
}
