package com.zjyang.mvpframe.ui.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;

/**
 * Created by zhengjiayang on 2018/7/19.
 */

public class RefreshLoadRecyclerView extends XRefreshView{

    private RecyclerView mRecyclerView;

    private boolean mIsRefreshEnable;
    private boolean mIsLoadMoreEnable;

    public RefreshLoadRecyclerView(Context context) {
        this(context, null);
    }

    public RefreshLoadRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context){
        mRecyclerView = new RecyclerView(context);
        addView(mRecyclerView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //刷新动画，需要自定义CustomGifHeader，不需要修改动画的会默认头布局
        RefreshViewHeader header = new RefreshViewHeader(getContext());
        setCustomHeaderView(header);

        mIsRefreshEnable = true;
        mIsLoadMoreEnable = false;

        //默认只有下拉刷新，需要上拉加载的添加下面第二行代码
        setPullRefreshEnable(mIsRefreshEnable);//设置允许下拉刷新
        setPullLoadEnable(mIsLoadMoreEnable);//设置允许上拉加载
    }

    public void setIsRefreshEnable(boolean isRefreshEnable) {
        this.mIsRefreshEnable = isRefreshEnable;
    }

    public void setIsLoadMoreEnable(boolean isLoadMoreEnable) {
        this.mIsLoadMoreEnable = isLoadMoreEnable;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager){
        if(mRecyclerView != null){
            mRecyclerView.setLayoutManager(layoutManager);
        }
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        if(mRecyclerView != null){
            mRecyclerView.setAdapter(adapter);
        }
    }

    public void setItemAnimator(RecyclerView.ItemAnimator itemAnimator){
        if(mRecyclerView != null){
            mRecyclerView.setItemAnimator(itemAnimator);
        }
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration){
        if(mRecyclerView != null){
            mRecyclerView.addItemDecoration(itemDecoration);
        }
    }

    public void setOnScrollListener(RecyclerView.OnScrollListener listener){
        if(mRecyclerView != null){
            mRecyclerView.setOnScrollListener(listener);
        }
    }
}
