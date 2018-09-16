package com.zjyang.mvpframe.module.home.tripcircle.view;

import android.content.Intent;
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
import com.zjyang.mvpframe.module.base.SkinManager;
import com.zjyang.mvpframe.module.home.tripcircle.TripCircleTasksContract;
import com.zjyang.mvpframe.module.home.tripcircle.adapter.WonderfulVideoAdapter;
import com.zjyang.mvpframe.module.home.tripcircle.model.bean.WonderfulVideo;
import com.zjyang.mvpframe.module.home.tripcircle.presenter.TripCirclePresenter;
import com.zjyang.mvpframe.module.home.tripcircle.widget.BannerIndicator;
import com.zjyang.mvpframe.module.home.tripcircle.widget.BannerViewPager;
import com.zjyang.mvpframe.module.rank.view.RankActivity;
import com.zjyang.mvpframe.ui.ShapeUtils;
import com.zjyang.mvpframe.ui.view.BannerView;
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
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 74215 on 2018/5/12.
 */

public class TripCircleFragment extends BaseFragment implements TripCircleTasksContract.View, BannerViewPager.ScrollPageListener{
    private Unbinder unbinder;

    @BindView(R.id.root_view)
    RelativeLayout mRootView;
    @BindView(R.id.view_pager_tripcircle)
    BannerView mViewPager;
    @BindView(R.id.banner_indicator)
    BannerIndicator mIndicator;
    @BindView(R.id.wonderful_video_list)
    RecyclerView mWonderfulVideoLv;
    @BindView(R.id.top_scene_iv)
    SimpleDraweeView mTopSceneIv;
    @BindView(R.id.second_scene_iv)
    SimpleDraweeView mSecondSceneIv;
    @BindView(R.id.third_scene_iv)
    SimpleDraweeView mThirdSceneIv;
    @BindView(R.id.search_entrance)
    RelativeLayout mSearchView;
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
        mToolbarBg.setBackgroundColor(SkinManager.getInstance().getPrimaryColor());
        RelativeLayout.LayoutParams toolbarParams = (RelativeLayout.LayoutParams) mToolbar.getLayoutParams();
        toolbarParams.setMargins(0, ScreenUtils.getStatusBarHeight(), 0, 0);

        mPresenter = new TripCirclePresenter(this);
        mPresenter.initTripCircleData();

        mWonderfulVideoList = new ArrayList<>();
        mWonderfulAdapter = new WonderfulVideoAdapter(getContext(), mWonderfulVideoList);
        mWonderfulLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mWonderfulVideoLv.addItemDecoration(new SpaceItemDecoration(10, 1, LinearLayoutManager.HORIZONTAL));
        mWonderfulVideoLv.setLayoutManager(mWonderfulLayoutManager);
        mWonderfulVideoLv.setAdapter(mWonderfulAdapter);

        FrescoUtils.showImgByUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535397882896&di=09c6277a19464f5a7c83b2450927da96&imgtype=0&src=http%3A%2F%2Fimg06.tooopen.com%2Fimages%2F20180116%2Ftooopen_sy_232320877961.jpg", mTopSceneIv);
        FrescoUtils.showUrlBlur(mSecondSceneIv, "http://thumbs.dreamstime.com/t/摄影师剪影-7849367.jpg", 5);
        FrescoUtils.showUrlBlur(mThirdSceneIv, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535480340747&di=b92fa9e60888de9454e34a8cdf6ce09a&imgtype=0&src=http%3A%2F%2Fwww.bjsyqw.com%2Fqiye%2Fupload%2F123%2Fadmin%2F20180606%2F5f3e438e17856714d8f69895a41341d8.jpg", 5);

        mSearchView.setBackground(ShapeUtils.getRoundRectDrawable(DrawUtils.dp2px(25), Color.parseColor("#ffffff")));

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
    }

    @Override
    public void onPageSelected(int position) {
        if(mIndicator != null){
            mIndicator.setCurrentPosition(position);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void notifyWonderfulLv(List<WonderfulVideo> wonderfulVideos) {
        if(wonderfulVideos != null){
            mWonderfulVideoList.clear();
            mWonderfulVideoList.addAll(wonderfulVideos);
            mWonderfulAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.top_scene_iv)
    void clickRankEntrance(){
        Intent intent = new Intent(getContext(), RankActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
