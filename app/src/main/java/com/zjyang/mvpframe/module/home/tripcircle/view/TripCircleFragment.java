package com.zjyang.mvpframe.module.home.tripcircle.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.base.BaseFragment;
import com.zjyang.mvpframe.module.home.tripcircle.TripCircleTasksContract;
import com.zjyang.mvpframe.module.home.tripcircle.adapter.WonderfulVideoAdapter;
import com.zjyang.mvpframe.module.home.tripcircle.model.bean.WonderfulVideo;
import com.zjyang.mvpframe.module.home.tripcircle.presenter.TripCirclePresenter;
import com.zjyang.mvpframe.module.home.tripcircle.widget.BannerViewPager;
import com.zjyang.mvpframe.ui.view.SpaceItemDecoration;
import com.zjyang.mvpframe.utils.FrescoUtils;

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


    private WonderfulVideoAdapter mWonderfulAdapter;
    private LinearLayoutManager mWonderfulLayoutManager;
    private TripCircleTasksContract.Presenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tripcircle, null);
        unbinder = ButterKnife.bind(this, view);

        mPresenter = new TripCirclePresenter(this);
        mPresenter.initTripCircleData();

        List<WonderfulVideo> videoList = new ArrayList<>();
        videoList.add(new WonderfulVideo("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523797863813&di=c1a82078d1672426d666cf4c8bd284d1&imgtype=0&src=http%3A%2F%2Fwww.rui2.net%2Fuploadfile%2Fdata%2F2015%2F0623%2F20150623114232290.jpg"));
        videoList.add(new WonderfulVideo("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523797863813&di=c1a82078d1672426d666cf4c8bd284d1&imgtype=0&src=http%3A%2F%2Fwww.rui2.net%2Fuploadfile%2Fdata%2F2015%2F0623%2F20150623114232290.jpg"));
        videoList.add(new WonderfulVideo("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523797863813&di=c1a82078d1672426d666cf4c8bd284d1&imgtype=0&src=http%3A%2F%2Fwww.rui2.net%2Fuploadfile%2Fdata%2F2015%2F0623%2F20150623114232290.jpg"));
        videoList.add(new WonderfulVideo("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523797863813&di=c1a82078d1672426d666cf4c8bd284d1&imgtype=0&src=http%3A%2F%2Fwww.rui2.net%2Fuploadfile%2Fdata%2F2015%2F0623%2F20150623114232290.jpg"));
        videoList.add(new WonderfulVideo("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523797863813&di=c1a82078d1672426d666cf4c8bd284d1&imgtype=0&src=http%3A%2F%2Fwww.rui2.net%2Fuploadfile%2Fdata%2F2015%2F0623%2F20150623114232290.jpg"));
        videoList.add(new WonderfulVideo("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523797863813&di=c1a82078d1672426d666cf4c8bd284d1&imgtype=0&src=http%3A%2F%2Fwww.rui2.net%2Fuploadfile%2Fdata%2F2015%2F0623%2F20150623114232290.jpg"));
        mWonderfulAdapter = new WonderfulVideoAdapter(getContext(), videoList);
        mWonderfulLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mWonderfulVideoLv.addItemDecoration(new SpaceItemDecoration(6, 1, LinearLayoutManager.HORIZONTAL));
        mWonderfulVideoLv.setLayoutManager(mWonderfulLayoutManager);
        mWonderfulVideoLv.setAdapter(mWonderfulAdapter);

        FrescoUtils.showImgByUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523797863813&di=c1a82078d1672426d666cf4c8bd284d1&imgtype=0&src=http%3A%2F%2Fwww.rui2.net%2Fuploadfile%2Fdata%2F2015%2F0623%2F20150623114232290.jpg", mTopSceneIv);
        FrescoUtils.showImgByUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523797863813&di=c1a82078d1672426d666cf4c8bd284d1&imgtype=0&src=http%3A%2F%2Fwww.rui2.net%2Fuploadfile%2Fdata%2F2015%2F0623%2F20150623114232290.jpg", mSecondSceneIv);
        FrescoUtils.showImgByUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523797863813&di=c1a82078d1672426d666cf4c8bd284d1&imgtype=0&src=http%3A%2F%2Fwww.rui2.net%2Fuploadfile%2Fdata%2F2015%2F0623%2F20150623114232290.jpg", mThirdSceneIv);

        return view;
    }

    @Override
    public void initBannerView(List<String> urlList){
        mViewPager.setBannerData(urlList);
        mViewPager.startPlay(2000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
