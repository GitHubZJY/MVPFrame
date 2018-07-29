package com.zjyang.mvpframe.module.home.discover;

import com.zjyang.mvpframe.module.home.discover.model.bean.Province;
import com.zjyang.mvpframe.module.home.model.bean.VideoInfo;

import java.util.List;

/**
 * Created by 74215 on 2018/4/21.
 */

public interface DiscoverTasksContract {

    interface View {
        void fillDataToList(List<VideoInfo> data);
        void refreshTabData(List<Province> data);
        void initProvinceFragment(List<Province> data);
        void showEmptyTip();
    }

    interface Presenter {
        //在请求网络数据之前先展示本地缓存数据
        void initDataBeforeRequest();
        //切换区域
        void toggleProvince(int index);
        //刷新当前正在展示的列表数据
        void refreshList();
        //初始化区域tab数据
        void initProvinceTabData();
        void destroy();
    }


    interface Model {
        //获取视频缓存数据
        List<VideoInfo> getDefaultVideoData();
        List<Province> getDefaultProvinceData();
        //通过区域ID获取区域视频数据
        void getVideoDataByProvinceId(int provinceId);
        //查询后台配置的区域
        void queryAllProvince();
        //设置&获取当前正在浏览的tab分页下标
        void setCurSelectTabId(int index);
        int getCurSelectTabId();
    }

}
