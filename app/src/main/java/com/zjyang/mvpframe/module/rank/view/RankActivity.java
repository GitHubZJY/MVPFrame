package com.zjyang.mvpframe.module.rank.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zjyang.base.base.BaseActivity;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.home.discover.model.DiscoverModel;
import com.zjyang.mvpframe.module.rank.RankTasksContract;
import com.zjyang.mvpframe.module.rank.adapter.RankListAdapter;
import com.zjyang.mvpframe.module.rank.presenter.RankPresenter;
import com.zjyang.base.utils.FrescoUtils;
import com.zjyang.base.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 74215 on 2018/9/1.
 */

public class RankActivity extends BaseActivity<RankPresenter> implements RankTasksContract.View{

    private Unbinder unbinder;

    @BindView(R.id.toolbar)
    FrameLayout mToolbar;
    @BindView(R.id.top_1_preview_pic)
    SimpleDraweeView mTop1PreviewIv;
    @BindView(R.id.top_2_preview_pic)
    SimpleDraweeView mTop2PreviewIv;
    @BindView(R.id.top_3_preview_pic)
    SimpleDraweeView mTop3PreviewIv;
    @BindView(R.id.rank_lv)
    RecyclerView mRankLv;

    private RankListAdapter mAdapter;

    @Override
    public RankPresenter createPresenter() {
        return new RankPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        unbinder = ButterKnife.bind(this);

        LinearLayout.LayoutParams toolbarParams = (LinearLayout.LayoutParams) mToolbar.getLayoutParams();
        toolbarParams.setMargins(0, ScreenUtils.getStatusBarHeight(), 0, 0);

        FrescoUtils.showImgByUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523797863813&di=c1a82078d1672426d666cf4c8bd284d1&imgtype=0&src=http%3A%2F%2Fwww.rui2.net%2Fuploadfile%2Fdata%2F2015%2F0623%2F20150623114232290.jpg", mTop1PreviewIv);
        FrescoUtils.showImgByUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523825017835&di=90359e5215580dbbea56efaa3f90ed7c&imgtype=0&src=http%3A%2F%2Fpic2.ooopic.com%2F11%2F25%2F28%2F31b1OOOPIC3e.jpg", mTop2PreviewIv);
        FrescoUtils.showImgByUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523825078043&di=2ac80a304706fbcc370ea5a6a2d751a3&imgtype=0&src=http%3A%2F%2Fwww.rui2.net%2Fuploadfile%2Foutput%2F2%2F2013%2F1004%2F5ba491db21616fe8.jpg", mTop3PreviewIv);
        DiscoverModel discoverModel = new DiscoverModel();
        mAdapter = new RankListAdapter(this, discoverModel.getDefaultVideoData());
        mRankLv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRankLv.setAdapter(mAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
