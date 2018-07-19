package com.zjyang.mvpframe.module.home.me.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.base.BaseFragment;
import com.zjyang.mvpframe.module.home.me.MeTasksContract;
import com.zjyang.mvpframe.module.home.me.presenter.MePresenter;
import com.zjyang.mvpframe.module.login.model.bean.User;
import com.zjyang.mvpframe.module.myvideo.view.MyVideoActivity;
import com.zjyang.mvpframe.ui.ShapeUtils;
import com.zjyang.mvpframe.utils.ColorUtils;
import com.zjyang.mvpframe.utils.FrescoUtils;
import com.zjyang.mvpframe.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 74215 on 2018/5/12.
 */

public class MeFragment extends BaseFragment implements AppBarLayout.OnOffsetChangedListener, MeTasksContract.View{

    public static final String TAG = "MeFragment";

    private Unbinder unbinder;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.big_user_pic)
    SimpleDraweeView mBigUserPicIv;
    @BindView(R.id.small_user_pic)
    SimpleDraweeView mSmallUserPicIv;
    @BindView(R.id.padding_view)
    View mPaddingView;
    @BindView(R.id.user_name_toolbar_tv)
    TextView mUserNameTitleTv;
    @BindView(R.id.toolbar_user_pic)
    SimpleDraweeView mToolBarUserPicIv;
    @BindView(R.id.user_name_tv)
    TextView mUserNameTv;
    @BindView(R.id.videos_tab)
    LinearLayout mVideosTab;
    @BindView(R.id.focus_tab)
    LinearLayout mFocusTab;
    @BindView(R.id.fans_tab)
    LinearLayout mFansTab;
    @BindView(R.id.user_level_name)
    TextView mUserLevelNameTv;
    @BindView(R.id.describe_tv)
    TextView mDescribeTv;
    @BindView(R.id.my_zuji_item)
    BaseSettingItem mFootMarkItem;
    @BindView(R.id.like_video_history_item)
    BaseSettingItem mLikeHistoryItem;


    MeTasksContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, null);
        unbinder = ButterKnife.bind(this, view);
//        if(!EventBus.getDefault().isRegistered(this)){
//            EventBus.getDefault().register(this);
//        }
        mPresenter = new MePresenter(this);
        initView();
        return view;
    }

    private void initView(){
        mPaddingView.getLayoutParams().height = ScreenUtils.getStatusBarHeight();
        mAppBarLayout.addOnOffsetChangedListener(this);
        mPresenter.fillUserDataToView();
    }

    @Override
    public void initUserDataView(User curUser) {
        if(curUser != null){
            String userPicUrl = curUser.getUserPic();
            String userName = curUser.getUserName();
            String userLevelStr = curUser.getLevelName();
            String userDescribe = curUser.getDescribe();
            int userLevel = curUser.getLevel();
            FrescoUtils.showUrlBlur(mBigUserPicIv, userPicUrl, 3);
            FrescoUtils.showImgByUrl(userPicUrl, mSmallUserPicIv);
            FrescoUtils.showImgByUrl(userPicUrl, mToolBarUserPicIv);
            mUserNameTitleTv.setText(userName);
            mUserNameTv.setText(userName);
            mDescribeTv.setText(userDescribe);
            mUserLevelNameTv.setText("Lv"+userLevel +" "+ userLevelStr);
            mUserLevelNameTv.setBackground(ShapeUtils.getRoundRectDrawable(30, getResources().getColor(R.color.yellow)));
        }
    }

    @OnClick(R.id.videos_tab)
    void clickVideosTab(){
        MyVideoActivity.go(getActivity());
    }

    @OnClick(R.id.focus_tab)
    void clickFocusTab(){

    }

    @OnClick(R.id.fans_tab)
    void clickFansTab(){

    }

    @OnClick(R.id.my_zuji_item)
    void clickFootMark(){

    }

    @OnClick(R.id.like_video_history_item)
    void clickLikeHistory(){

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //滑到顶部scale为1
        float scale = Math.abs(verticalOffset*1.0f)/appBarLayout.getTotalScrollRange();
        mToolBar.setAlpha(scale);
        mToolBar.setBackgroundColor(ColorUtils.changeAlpha(getResources().getColor(R.color.colorPrimary),Math.abs(verticalOffset*1.0f)/appBarLayout.getTotalScrollRange()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }
}
