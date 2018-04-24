package com.zjyang.mvpframe.module.home.discover.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.event.FullScreenExitEvent;
import com.zjyang.mvpframe.event.RequestVideoListEvent;
import com.zjyang.mvpframe.module.home.discover.DiscoverTasksContract;
import com.zjyang.mvpframe.module.home.discover.model.VideoFramesModel;
import com.zjyang.mvpframe.module.home.discover.presenter.DiscoverPresenter;
import com.zjyang.mvpframe.module.home.model.bean.VideoInfo;
import com.zjyang.mvpframe.module.home.view.VideoListAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 74215 on 2018/4/9.
 */

public class DiscoverFragment extends Fragment implements DiscoverTasksContract.View{

    private Unbinder unbinder;

    @BindView(R.id.video_recycle_view)
    public RecyclerView mVideoListView;
    @BindView(R.id.top_tab_1)
    public TextView mTopTab1;
    @BindView(R.id.top_tab_2)
    public TextView mTopTab2;
    @BindView(R.id.top_tab_3)
    public TextView mTopTab3;
    @BindView(R.id.top_tab_4)
    public TextView mTopTab4;
    @BindView(R.id.top_tab_5)
    public TextView mTopTab5;


    private VideoListAdapter mVideoAdapter;
    private List<VideoInfo> mVideoList;
    private LinearLayoutManager mLayoutManager;

    private DiscoverTasksContract.Presenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, null);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        mPresenter = new DiscoverPresenter(this);
        initView();
        return view;
    }


    public void initView(){

        mLayoutManager = new LinearLayoutManager(getContext());
        mVideoListView.setLayoutManager(mLayoutManager);
        mVideoList = new ArrayList<>();
        String[] picUrl = new String[]{
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523797863813&di=c1a82078d1672426d666cf4c8bd284d1&imgtype=0&src=http%3A%2F%2Fwww.rui2.net%2Fuploadfile%2Fdata%2F2015%2F0623%2F20150623114232290.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523824983803&di=49ac1e588634c0405b8b43ace1170a29&imgtype=0&src=http%3A%2F%2Fpic.qiantucdn.com%2F58pic%2F16%2F96%2F59%2F33t58PICARc_1024.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523825017835&di=90359e5215580dbbea56efaa3f90ed7c&imgtype=0&src=http%3A%2F%2Fpic2.ooopic.com%2F11%2F25%2F28%2F31b1OOOPIC3e.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523825078043&di=2ac80a304706fbcc370ea5a6a2d751a3&imgtype=0&src=http%3A%2F%2Fwww.rui2.net%2Fuploadfile%2Foutput%2F2%2F2013%2F1004%2F5ba491db21616fe8.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523825117163&di=e65211e7015596cb127c25f539974fcf&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fgames%2Ftransform%2F20160614%2F9Jah-fxszmaa1989666.jpg",
                "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2288195174,495473604&fm=27&gp=0.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523825201873&di=99bdc703cda55dcc8765bf391595725f&imgtype=0&src=http%3A%2F%2Fi3.tietuku.com%2F2b33ceb94af6609d.png"
        };
        for(int i=0; i<7; i++){
            VideoInfo videoInfo = new VideoInfo();
            videoInfo.setVideoUrl("http://221.228.226.23/11/t/j/v/b/tjvbwspwhqdmgouolposcsfafpedmb/sh.yinyuetai.com/691201536EE4912BF7E4F1E2C67B8119.mp4");
            videoInfo.setStatus(1);
            videoInfo.setVideoThumbUrl(picUrl[i]);
            videoInfo.setUserPicUrl(picUrl[i]);
            videoInfo.setUserName("HELLO");
            mVideoList.add(videoInfo);
        }
        mVideoAdapter = new VideoListAdapter(getContext(), mVideoList);
        mVideoListView.setAdapter(mVideoAdapter);
        mVideoListView.setItemAnimator(new DefaultItemAnimator());

        mVideoListView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int curPlayIndex = VideoFramesModel.getInstance().getCurPlayItemIndex();
                if(curPlayIndex == -1){
                    return;
                }
                int firstVisibleIndex = mLayoutManager.findFirstVisibleItemPosition();
                int lastVisibleIndex = mLayoutManager.findLastVisibleItemPosition();
                if(curPlayIndex < firstVisibleIndex || curPlayIndex > lastVisibleIndex){
                    VideoFramesModel.getInstance().getCurPlayView().stopPlayback();
                    mVideoAdapter.notifyItemChanged(curPlayIndex);
                    VideoFramesModel.getInstance().setCurPlayItemIndex(-1);
                    mVideoList.get(curPlayIndex).setStatus(VideoListAdapter.STOP_STATUS);
                }
            }
        });
    }


    @OnClick(R.id.top_tab_1)
    void clickTab1(){
        mPresenter.toggleProvince(1);
    }

    @OnClick(R.id.top_tab_2)
    void clickTab2(){
        mPresenter.toggleProvince(2);
    }

    @OnClick(R.id.top_tab_3)
    void clickTab3(){
        mPresenter.toggleProvince(3);
    }

    @OnClick(R.id.top_tab_4)
    void clickTab4(){
        mPresenter.toggleProvince(4);
    }

    @OnClick(R.id.top_tab_5)
    void clickTab5(){
        mPresenter.toggleProvince(5);
    }


    @Override
    public void toggleTopTab(int index) {
        switch (index){
            case 1:
                resetAllTopTabColor();
                mTopTab1.setTextColor(getResources().getColor(R.color.primary_text_color));
                break;
            case 2:
                resetAllTopTabColor();
                mTopTab2.setTextColor(getResources().getColor(R.color.primary_text_color));
                break;
            case 3:
                resetAllTopTabColor();
                mTopTab3.setTextColor(getResources().getColor(R.color.primary_text_color));
                break;
            case 4:
                resetAllTopTabColor();
                mTopTab4.setTextColor(getResources().getColor(R.color.primary_text_color));
                break;
            case 5:
                resetAllTopTabColor();
                mTopTab5.setTextColor(getResources().getColor(R.color.primary_text_color));
                break;
        }
    }

    private void resetAllTopTabColor(){
        mTopTab1.setTextColor(getResources().getColor(R.color.text_color_gray));
        mTopTab2.setTextColor(getResources().getColor(R.color.text_color_gray));
        mTopTab3.setTextColor(getResources().getColor(R.color.text_color_gray));
        mTopTab4.setTextColor(getResources().getColor(R.color.text_color_gray));
        mTopTab5.setTextColor(getResources().getColor(R.color.text_color_gray));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFullScreenExitEvent(FullScreenExitEvent exitEvent){
        mVideoAdapter.exitFullScreenNotify();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRequstVideoListSuccessEvent(RequestVideoListEvent event){
        if(event.ismIsSuccess()){
            mVideoList.clear();
            mVideoList.addAll(event.getVideoInfoList());
            mVideoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }
}
