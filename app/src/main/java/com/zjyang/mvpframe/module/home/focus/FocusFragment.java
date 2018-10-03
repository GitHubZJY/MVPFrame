package com.zjyang.mvpframe.module.home.focus;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zjyang.base.base.SkinManager;
import com.zjyang.base.utils.DrawUtils;
import com.zjyang.base.utils.ScreenUtils;
import com.zjyang.base.utils.ShapeUtils;
import com.zjyang.base.widget.SpaceItemDecoration;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.BaseFragment;
import com.zjyang.mvpframe.module.home.focus.adapter.TripInfoListAdapter;
import com.zjyang.mvpframe.module.home.focus.bean.FindTripInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 74215 on 2018/5/12.
 */

public class FocusFragment extends BaseFragment {

    private Unbinder unbinder;

    @BindView(R.id.trip_info_list)
    RecyclerView mTripInfoLv;
    @BindView(R.id.tool_bar)
    RelativeLayout mToolbar;
    @BindView(R.id.tool_bar_bg)
    View mToolbarBg;
    @BindView(R.id.search_entrance)
    RelativeLayout mSearchView;

    private TripInfoListAdapter mAdapter;
    private List<FindTripInfo> mTripInfoList;
    private LinearLayoutManager mTripInfoLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_focus, null);
        unbinder = ButterKnife.bind(this, view);

        mToolbarBg.getLayoutParams().height = DrawUtils.dp2px(48) + ScreenUtils.getStatusBarHeight();
        mToolbarBg.setBackgroundColor(SkinManager.getInstance().getPrimaryColor());
        RelativeLayout.LayoutParams toolbarParams = (RelativeLayout.LayoutParams) mToolbar.getLayoutParams();
        toolbarParams.setMargins(0, ScreenUtils.getStatusBarHeight(), 0, 0);
        mSearchView.setBackground(ShapeUtils.getRoundRectDrawable(DrawUtils.dp2px(25), Color.parseColor("#ffffff")));


        mTripInfoList = new ArrayList<>();
        mTripInfoList.add(new FindTripInfo("随手起个名", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1471108867,2346946684&fm=26&gp=0.jpg", "10月是去南疆（新疆）最佳时间，寻找能从天津去南疆（喀什、胡杨林）的游伴。时间10月中旬8或11天（视飞机还是火车），现有正规旅行社出团，只是不接受单人参团，故相约游伴（也可多人），团费（3900或4", "https://m.mafengwo.cn"));
        mTripInfoList.add(new FindTripInfo("携程旅行", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3914154535,3854686130&fm=27&gp=0.jpg", "国庆在国内都是人挤人，看人头比较多，还是去国外吧，越南离我们最近，从广州飞去2个钟就到了，越南消费也很低，比较国内还便宜，行程是（2号到7号）：胡志明--美奈--芽庄--河内，有四个小伙伴了，两个女生", "https://www.ctrip.com/"));
        mTripInfoList.add(new FindTripInfo("去哪儿网", "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1782844311,2805295425&fm=27&gp=0.jpg", "北京人退休，职业司机。8月24-9月24自驾游新疆。穷游东，西，南北疆，帕米尔高原，伊宁昭苏，走独库公路，沙漠公路。行程大约30天，张掖租车，环疆游返回张掖结束。人均费用8500-9500元。 现一车", "https://www.qunar.com/"));
        mTripInfoList.add(new FindTripInfo("Airbnb", "https://ss0.bdstatic.com/-0U0bnSm1A5BphGlnYG/tam-ogel/ad7df003bb1284bd541161cf4bcad1df_121_121.jpg", "爱彼迎预订立享优惠，解锁别样十一假期！全球500万房源，房东实名认证，24小时中文客服，支付宝安全支付，放心入住！无论是泰国、美国、台湾、上海，还是厦门，找干净安全的性价比住宿、适合家庭出游的公寓别墅，或是海景房、树屋等特色住宿，爱彼迎都不会让你失望！", "https://zh.airbnb.com/"));
        mTripInfoList.add(new FindTripInfo("Airbnb", "https://ss0.bdstatic.com/-0U0bnSm1A5BphGlnYG/tam-ogel/ad7df003bb1284bd541161cf4bcad1df_121_121.jpg", "爱彼迎预订立享优惠，解锁别样十一假期！全球500万房源，房东实名认证，24小时中文客服，支付宝安全支付，放心入住！无论是泰国、美国、台湾、上海，还是厦门，找干净安全的性价比住宿、适合家庭出游的公寓别墅，或是海景房、树屋等特色住宿，爱彼迎都不会让你失望！", "https://zh.airbnb.com/"));
        mTripInfoList.add(new FindTripInfo("Airbnb", "https://ss0.bdstatic.com/-0U0bnSm1A5BphGlnYG/tam-ogel/ad7df003bb1284bd541161cf4bcad1df_121_121.jpg", "爱彼迎预订立享优惠，解锁别样十一假期！全球500万房源，房东实名认证，24小时中文客服，支付宝安全支付，放心入住！无论是泰国、美国、台湾、上海，还是厦门，找干净安全的性价比住宿、适合家庭出游的公寓别墅，或是海景房、树屋等特色住宿，爱彼迎都不会让你失望！", "https://zh.airbnb.com/"));

        mAdapter = new TripInfoListAdapter(getContext(), mTripInfoList);
        mTripInfoLv.addItemDecoration(new SpaceItemDecoration(DrawUtils.dp2px(8), 1, LinearLayoutManager.VERTICAL));

        mTripInfoLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mTripInfoLv.setLayoutManager(mTripInfoLayoutManager);
        mTripInfoLv.setAdapter(mAdapter);
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder != null){
            unbinder.unbind();
        }
    }
}
