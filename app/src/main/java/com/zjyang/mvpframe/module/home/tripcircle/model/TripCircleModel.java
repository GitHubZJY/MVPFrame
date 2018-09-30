package com.zjyang.mvpframe.module.home.tripcircle.model;

import com.zjyang.mvpframe.event.GetWonderfulVideoEvent;
import com.zjyang.mvpframe.module.home.tripcircle.TripCircleTasksContract;
import com.zjyang.mvpframe.module.home.tripcircle.model.bean.TripWebInfo;
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
        urlList.add("http://pic41.photophoto.cn/20161217/0017030086344808_b.jpg");
        urlList.add("http://photocdn.sohu.com/20150114/Img407794285.jpg");
        urlList.add("http://img.zcool.cn/community/015372554281b00000019ae9803e5c.jpg");
        return urlList;
    }

    @Override
    public List<TripWebInfo> getTripWebData() {
        List<TripWebInfo> mTripWebInfoList = new ArrayList<>();
        mTripWebInfoList.add(new TripWebInfo("马蜂窝", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1471108867,2346946684&fm=26&gp=0.jpg", "马蜂窝!靠谱的旅游攻略,自由行,自助游分享社区,海量旅游景点图片、游记、交通、美食、购物等自由行旅游攻略信息,马蜂窝旅游网获取自由行,自助游攻略信息更全面", "https://m.mafengwo.cn"));
        mTripWebInfoList.add(new TripWebInfo("携程旅行", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3914154535,3854686130&fm=27&gp=0.jpg", "携程旅行网是中国领先的在线旅行服务公司,向超过9000万会员提供酒店预订、酒店点评及特价酒店查询、机票预订、飞机票查询、时刻表、票价查询、航班查询、度假预订、商旅...", "https://www.ctrip.com/"));
        mTripWebInfoList.add(new TripWebInfo("去哪儿网", "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1782844311,2805295425&fm=27&gp=0.jpg", "去哪儿Qunar.com提供机票,飞机票,特价机票,打折机票的查询预订;99元春秋航空特惠折扣机票,百元南航、海航惊喜特价机票任您挑选,国航、深航1折特价机票和折扣机票一", "https://www.qunar.com/"));
        mTripWebInfoList.add(new TripWebInfo("Airbnb", "https://ss0.bdstatic.com/-0U0bnSm1A5BphGlnYG/tam-ogel/ad7df003bb1284bd541161cf4bcad1df_121_121.jpg", "爱彼迎预订立享优惠，解锁别样十一假期！全球500万房源，房东实名认证，24小时中文客服，支付宝安全支付，放心入住！无论是泰国、美国、台湾、上海，还是厦门，找干净安全的性价比住宿、适合家庭出游的公寓别墅，或是海景房、树屋等特色住宿，爱彼迎都不会让你失望！", "https://zh.airbnb.com/"));
        return mTripWebInfoList;
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
