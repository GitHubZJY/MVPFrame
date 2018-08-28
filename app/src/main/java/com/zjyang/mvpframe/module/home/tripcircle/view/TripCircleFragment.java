package com.zjyang.mvpframe.module.home.tripcircle.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.base.BaseFragment;
import com.zjyang.mvpframe.module.home.tripcircle.TripCircleTasksContract;
import com.zjyang.mvpframe.module.home.tripcircle.adapter.WonderfulVideoAdapter;
import com.zjyang.mvpframe.module.home.tripcircle.model.bean.WonderfulVideo;
import com.zjyang.mvpframe.module.home.tripcircle.presenter.TripCirclePresenter;
import com.zjyang.mvpframe.module.home.tripcircle.widget.BannerViewPager;
import com.zjyang.mvpframe.ui.ShapeUtils;
import com.zjyang.mvpframe.ui.view.CustomScrollView;
import com.zjyang.mvpframe.ui.view.SpaceItemDecoration;
import com.zjyang.mvpframe.utils.DrawUtils;
import com.zjyang.mvpframe.utils.FrescoUtils;
import com.zjyang.mvpframe.utils.LogUtil;
import com.zjyang.mvpframe.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 74215 on 2018/5/12.
 */

public class TripCircleFragment extends BaseFragment implements TripCircleTasksContract.View{
    private Unbinder unbinder;

    @BindView(R.id.root_view)
    RelativeLayout mRootView;
    @BindView(R.id.view_pager)
    BannerViewPager mViewPager;
    @BindView(R.id.wonderful_video_list)
    RecyclerView mWonderfulVideoLv;
    @BindView(R.id.top_scene_iv)
    SimpleDraweeView mTopSceneIv;
    @BindView(R.id.second_scene_iv)
    SimpleDraweeView mSecondSceneIv;
    @BindView(R.id.third_scene_iv)
    SimpleDraweeView mThirdSceneIv;
    @BindView(R.id.search_entrance_tv)
    TextView mSearchTv;
    @BindView(R.id.scroll_view)
    CustomScrollView mScrollView;
    @BindView(R.id.tool_bar)
    RelativeLayout mToolbar;
    @BindView(R.id.tool_bar_bg)
    View mToolbarBg;


    private WonderfulVideoAdapter mWonderfulAdapter;
    private LinearLayoutManager mWonderfulLayoutManager;
    private TripCircleTasksContract.Presenter mPresenter;

    List<WonderfulVideo> mWonderfulVideoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tripcircle, null);
        unbinder = ButterKnife.bind(this, view);

        mToolbarBg.getLayoutParams().height = DrawUtils.dp2px(48) + ScreenUtils.getStatusBarHeight();
        mToolbar.setPadding(0, ScreenUtils.getStatusBarHeight(), 0, 0);

        mPresenter = new TripCirclePresenter(this);
        mPresenter.initTripCircleData();

        mWonderfulVideoList = new ArrayList<>();
        //mWonderfulVideoList.add(new WonderfulVideo("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523797863813&di=c1a82078d1672426d666cf4c8bd284d1&imgtype=0&src=http%3A%2F%2Fwww.rui2.net%2Fuploadfile%2Fdata%2F2015%2F0623%2F20150623114232290.jpg"));
        //mWonderfulVideoList.add(new WonderfulVideo("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523797863813&di=c1a82078d1672426d666cf4c8bd284d1&imgtype=0&src=http%3A%2F%2Fwww.rui2.net%2Fuploadfile%2Fdata%2F2015%2F0623%2F20150623114232290.jpg"));
        //mWonderfulVideoList.add(new WonderfulVideo("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523797863813&di=c1a82078d1672426d666cf4c8bd284d1&imgtype=0&src=http%3A%2F%2Fwww.rui2.net%2Fuploadfile%2Fdata%2F2015%2F0623%2F20150623114232290.jpg"));
        //mWonderfulVideoList.add(new WonderfulVideo("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523797863813&di=c1a82078d1672426d666cf4c8bd284d1&imgtype=0&src=http%3A%2F%2Fwww.rui2.net%2Fuploadfile%2Fdata%2F2015%2F0623%2F20150623114232290.jpg"));
        //mWonderfulVideoList.add(new WonderfulVideo("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523797863813&di=c1a82078d1672426d666cf4c8bd284d1&imgtype=0&src=http%3A%2F%2Fwww.rui2.net%2Fuploadfile%2Fdata%2F2015%2F0623%2F20150623114232290.jpg"));
        //mWonderfulVideoList.add(new WonderfulVideo("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523797863813&di=c1a82078d1672426d666cf4c8bd284d1&imgtype=0&src=http%3A%2F%2Fwww.rui2.net%2Fuploadfile%2Fdata%2F2015%2F0623%2F20150623114232290.jpg"));
        mWonderfulAdapter = new WonderfulVideoAdapter(getContext(), mWonderfulVideoList);
        mWonderfulLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mWonderfulVideoLv.addItemDecoration(new SpaceItemDecoration(6, 1, LinearLayoutManager.HORIZONTAL));
        mWonderfulVideoLv.setLayoutManager(mWonderfulLayoutManager);
        mWonderfulVideoLv.setAdapter(mWonderfulAdapter);

        FrescoUtils.showImgByUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535397882896&di=09c6277a19464f5a7c83b2450927da96&imgtype=0&src=http%3A%2F%2Fimg06.tooopen.com%2Fimages%2F20180116%2Ftooopen_sy_232320877961.jpg", mTopSceneIv);
        FrescoUtils.showUrlBlur(mSecondSceneIv, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536055305&di=9c893f74135893a9b4e13283d9d0c5ad&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.juimg.com%2Ftuku%2Fyulantu%2F120410%2F8881-12041016294579.jpg", 15);
        FrescoUtils.showImgByUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523797863813&di=c1a82078d1672426d666cf4c8bd284d1&imgtype=0&src=http%3A%2F%2Fwww.rui2.net%2Fuploadfile%2Fdata%2F2015%2F0623%2F20150623114232290.jpg", mThirdSceneIv);

        mSearchTv.setBackground(ShapeUtils.getRoundRectDrawable(DrawUtils.dp2px(18), Color.parseColor("#EDEDED")));

        mScrollView.setOnScrollListener(new CustomScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                float alpha = (float) scrollY / 300f;
                mToolbarBg.setAlpha(alpha);
                LogUtil.d("scrollview", "y: " + scrollY);
            }
        });
        return view;
    }

    @Override
    public void initBannerView(List<String> urlList){
        mViewPager.setBannerData(urlList);
        mViewPager.startPlay(2000);
    }

    @Override
    public void notifyWonderfulLv(List<WonderfulVideo> wonderfulVideos) {
        if(wonderfulVideos != null){
            mWonderfulVideoList.clear();
            mWonderfulVideoList.addAll(wonderfulVideos);
            mWonderfulAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
