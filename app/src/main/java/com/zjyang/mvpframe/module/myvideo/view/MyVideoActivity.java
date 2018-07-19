package com.zjyang.mvpframe.module.myvideo.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;

import com.andview.refreshview.XRefreshView;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.event.RequestMyVideoListEvent;
import com.zjyang.mvpframe.module.base.BaseActivity;
import com.zjyang.mvpframe.module.home.model.bean.VideoInfo;
import com.zjyang.mvpframe.module.myvideo.MyVideoTasksContract;
import com.zjyang.mvpframe.module.myvideo.adapter.MyVideoListAdapter;
import com.zjyang.mvpframe.module.myvideo.presenter.MyVideoPresenter;
import com.zjyang.mvpframe.ui.view.CustomToolBar;
import com.zjyang.mvpframe.ui.view.RefreshLoadRecyclerView;
import com.zjyang.mvpframe.ui.view.SpaceItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 74215 on 2018/7/18.
 */

public class MyVideoActivity extends BaseActivity implements MyVideoTasksContract.View, CustomToolBar.OnClickListener{

    public static final String TAG = "MyVideoActivity";
    private Unbinder unbinder;

    @BindView(R.id.tool_bar)
    CustomToolBar mToolBar;
    @BindView(R.id.refresh_view)
    public RefreshLoadRecyclerView mRefreshRecyclerView;

    public ViewStub mEmptyView;

    private MyVideoListAdapter mVideoAdapter;
    private List<VideoInfo> mVideoList;
    private GridLayoutManager mLayoutManager;

    MyVideoTasksContract.Presenter mPresenter;

    public static void go(Context context){
        Intent intent = new Intent(context, MyVideoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);// 允许使用transitions
        setContentView(R.layout.activity_my_video);
        unbinder = ButterKnife.bind(this);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        mPresenter = new MyVideoPresenter(this);
        initView();
        initListener();
    }

    private void initView(){
        mLayoutManager = new GridLayoutManager(this, 3);
        mRefreshRecyclerView.setLayoutManager(mLayoutManager);
        mVideoList = new ArrayList<>();
        mVideoAdapter = new MyVideoListAdapter(this, mVideoList);
        mRefreshRecyclerView.setAdapter(mVideoAdapter);
        mRefreshRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRefreshRecyclerView.addItemDecoration(new SpaceItemDecoration(3, 3));

        //优先检查当前是否有缓存数据
        mPresenter.checkCacheDataAndNotify();
        //同时发起网络请求查询当前用户视频数据
        mRefreshRecyclerView.startRefresh();
        mPresenter.getMyVideoList();
    }

    public void initListener(){
        mRefreshRecyclerView.setXRefreshViewListener(new XRefreshView.XRefreshViewListener() {
            @Override
            public void onRefresh() {
                mPresenter.getMyVideoList();
            }

            @Override
            public void onRefresh(boolean isPullDown) {

            }

            @Override
            public void onLoadMore(boolean isSilence) {

            }

            @Override
            public void onRelease(float direction) {

            }

            @Override
            public void onHeaderMove(double headerMovePercent, int offsetY) {

            }
        });

        mToolBar.setClickListener(this);
    }

    @Override
    public void clickLeftBtn() {

    }

    @Override
    public void clickRightBtn() {

    }

    @Override
    public void fillDataToListView(List<VideoInfo> videoList){
        if(videoList != null){
            mVideoList.clear();
            mVideoList.addAll(videoList);
            mVideoAdapter.notifyDataSetChanged();
        }else{
            showEmptyView();
        }
    }

    public void showEmptyView(){
        mRefreshRecyclerView.setVisibility(View.GONE);
        if(mEmptyView == null){
            mEmptyView = findViewById(R.id.empty_tip_view);
            mEmptyView.inflate();
        }else{
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    public void hideEmptyView(){
        mRefreshRecyclerView.setVisibility(View.VISIBLE);
        if(mEmptyView != null){
            mEmptyView.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRequstVideoListSuccessEvent(RequestMyVideoListEvent event){
        mRefreshRecyclerView.stopRefresh();//刷新停止
        if(event.isSuccess()){
            hideEmptyView();
            mVideoList.clear();
            mVideoList.addAll(event.getVideoList());
            mVideoAdapter.notifyDataSetChanged();
        }else{
            if(mVideoList == null || mVideoList.isEmpty()){
                showEmptyView();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}
