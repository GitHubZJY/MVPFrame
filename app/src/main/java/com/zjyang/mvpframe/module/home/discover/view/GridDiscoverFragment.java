package com.zjyang.mvpframe.module.home.discover.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zjyang.base.base.SkinManager;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.BaseFragment;
import com.zjyang.mvpframe.module.home.adapter.HomePagerAdapter;
import com.zjyang.mvpframe.module.home.discover.DiscoverTasksContract;
import com.zjyang.mvpframe.module.home.discover.model.bean.Province;
import com.zjyang.mvpframe.module.home.discover.presenter.DiscoverPresenter;
import com.zjyang.mvpframe.ui.view.TabContainer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 74215 on 2018/6/9.
 */

public class GridDiscoverFragment extends BaseFragment implements DiscoverTasksContract.View.BaseDiscoverView, TabContainer.ToggleItemListener{

    private Unbinder unbinder;

    @BindView(R.id.tab_group)
    public RelativeLayout mTabGroup;
    @BindView(R.id.discover_view_pager)
    public ViewPager mViewPager;
    @BindView(R.id.tab_container)
    public TabContainer mTabContainer;

    private HomePagerAdapter mPagerAdapter;

    private List<BaseFragment> mFragmentList;

    private DiscoverTasksContract.Presenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, null);
        unbinder = ButterKnife.bind(this, view);
        mPresenter = new DiscoverPresenter(this);
        initView();
        return view;
    }


    public void initView(){
        mFragmentList =  new ArrayList<>();
        mPagerAdapter = new HomePagerAdapter(getChildFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(8);
        mTabContainer.setToggleItemListener(this);
        mTabContainer.setBackgroundColor(SkinManager.getInstance().getPrimaryColor());
        initData();
    }

    @Override
    public void initProvinceFragment(List<Province> provinceList){
        for(Province province : provinceList){
            DiscoverItemFragment discoverItemFragment = new DiscoverItemFragment();
            discoverItemFragment.setPageTitle(province);
            mFragmentList.add(discoverItemFragment);
        }
        mPagerAdapter.notifyDataSetChanged();
        mTabContainer.setViewPager(mViewPager);
    }

    public void initData(){
        if(mPresenter != null){
            mPresenter.initDataBeforeRequest();
            mPresenter.toggleProvince(1);
            mPresenter.initProvinceTabData();
        }

    }

    @Override
    public void refreshTabData(List<Province> provinceList) {
//        mFragmentList.clear();
//        for(Province province : provinceList){
//            DiscoverItemFragment discoverItemFragment = new DiscoverItemFragment();
//            discoverItemFragment.setPageTitle(province);
//            mFragmentList.add(discoverItemFragment);
//        }
//        mPagerAdapter.notifyDataSetChanged();
//        mTabContainer.setViewPager(mViewPager);
    }

    @Override
    public void toggleItem(int index) {
        if(mFragmentList != null && !mFragmentList.isEmpty() && index < mFragmentList.size()){
            DiscoverItemFragment fragment = (DiscoverItemFragment)mFragmentList.get(index);
            fragment.initVideoList();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
