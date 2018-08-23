package com.zjyang.mvpframe.module.home.discover.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.event.RequestVideoListEvent;
import com.zjyang.mvpframe.module.base.BaseFragment;
import com.zjyang.mvpframe.module.home.adapter.GridVideoListAdapter;
import com.zjyang.mvpframe.module.home.adapter.HomePagerAdapter;
import com.zjyang.mvpframe.module.home.adapter.VideoListAdapter;
import com.zjyang.mvpframe.module.home.discover.DiscoverTasksContract;
import com.zjyang.mvpframe.module.home.discover.model.VideoFramesModel;
import com.zjyang.mvpframe.module.home.discover.model.bean.Province;
import com.zjyang.mvpframe.module.home.discover.presenter.DiscoverPresenter;
import com.zjyang.mvpframe.module.home.model.bean.VideoInfo;
import com.zjyang.mvpframe.ui.view.RefreshLoadRecyclerView;
import com.zjyang.mvpframe.ui.view.SpaceItemDecoration;
import com.zjyang.mvpframe.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhengjiayang on 2018/7/27.
 */

public class DiscoverItemFragment extends BaseFragment implements DiscoverTasksContract.View.ItemDiscoverView{

    private Unbinder unbinder;

    @BindView(R.id.refresh_view)
    public RefreshLoadRecyclerView mRefreshRecyclerView;

    private ViewStub mEmptyView;

    private GridVideoListAdapter mVideoAdapter;
    private List<VideoInfo> mVideoList;
    private GridLayoutManager mLayoutManager;

    private DiscoverTasksContract.Presenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discover_item_page, null);
        unbinder = ButterKnife.bind(this, view);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        mPresenter = new DiscoverPresenter(this);
        initView();
        return view;
    }

    public void initView(){

        mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRefreshRecyclerView.setLayoutManager(mLayoutManager);
        mVideoList = new ArrayList<>();
        mVideoAdapter = new GridVideoListAdapter(getContext(), mVideoList);
        mRefreshRecyclerView.setAdapter(mVideoAdapter);
        mRefreshRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRefreshRecyclerView.addItemDecoration(new SpaceItemDecoration(3, 2, LinearLayoutManager.VERTICAL));

        mRefreshRecyclerView.setOnRefreshLoadListener(new RefreshLoadRecyclerView.RefreshLoadListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshList();
            }

            @Override
            public void onLoadMore(boolean isSilence) {

            }

            @Override
            public void onRelease(float direction) {

            }
        });
        initData();
    }

    public void initData(){

    }

    @Override
    public void showEmptyTip() {
        if(mEmptyView == null){
            if(null != getView()){
                mEmptyView = getView().findViewById(R.id.empty_tip_view);
                mEmptyView.inflate();
            }
        }else {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    public void initVideoList(){
        if(pageTitle != null && mPresenter != null){
            mPresenter.toggleProvince(pageTitle.getProvinceId());
        }
    }

    @Override
    public void fillDataToList(List<VideoInfo> data) {
        if(mEmptyView != null && mEmptyView.getVisibility() == View.VISIBLE){
            mEmptyView.setVisibility(View.GONE);
        }
        mVideoList.clear();
        mVideoList.addAll(data);
        mVideoAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRequstVideoListEvent(RequestVideoListEvent event){
        if(pageTitle == null){
            return;
        }
        if(event.getProvinceId() != pageTitle.getProvinceId()){
            return;
        }
        mRefreshRecyclerView.stopRefresh();//刷新停止
        List<VideoInfo> videoInfos = event.getVideoInfoList();
        if(videoInfos == null || videoInfos.isEmpty()){
            if(mVideoList == null || mVideoList.isEmpty()){
                //网络请求失败，且当前页面也无缓存数据时
                showEmptyTip();
            }
        }
        if(event.ismIsSuccess()){
            if(mEmptyView != null){
                mEmptyView.setVisibility(View.GONE);
            }
            mVideoList.clear();
            mVideoList.addAll(event.getVideoInfoList());
            mVideoAdapter.notifyDataSetChanged();
        }else{
            if(mVideoList == null || mVideoList.isEmpty()){
                //网络请求失败，且当前页面也无缓存数据时
                showEmptyTip();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if(mPresenter != null){
            mPresenter.destroy();
        }
        EventBus.getDefault().unregister(this);
    }
}
