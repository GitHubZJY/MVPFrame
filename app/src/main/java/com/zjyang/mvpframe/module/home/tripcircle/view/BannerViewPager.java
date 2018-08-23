package com.zjyang.mvpframe.module.home.tripcircle.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.utils.DrawUtils;
import com.zjyang.mvpframe.utils.FrescoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengjiayang on 2018/8/20.
 */

public class BannerViewPager extends ViewPager{

    private InnerPagerAdapter mAdapter;
    private int mCurrentIndex = Integer.MAX_VALUE / 2;
    //播放标志
    private boolean isPlay = false;
    private final int PLAY = 0x123;
    //默认页面之间的切换时间
    private long mDelayTime = 2000;
    private boolean mIsAutoScroll = true;
    private boolean mGestureScroll = false;
    private List<String> mBannerUrlList;

    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            if (msg.what == PLAY && mIsAutoScroll){
                setCurrentItem(mCurrentIndex);
                if (isPlay){
                    play();
                }
            }
        }
    };

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        addOnPageChangeListener(mPageListener);
        mBannerUrlList = new ArrayList<>();
        mAdapter = new InnerPagerAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

            }
        });
        setAdapter(mAdapter);
        setCurrentItem(Integer.MAX_VALUE / 2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int height = 0;
//        for (int i = 0; i < getChildCount(); i++) {
//            View child = getChildAt(i);
//            child.measure(widthMeasureSpec,
//                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//            int h = child.getMeasuredHeight();
//            if (h > height)
//                height = h;
//        }
//
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
//                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public void setAutoScrollEnable(boolean isAuto){
        mIsAutoScroll = isAuto;
    }

    /**
     * 开始播放
     * @param delayMillis
     */
    public void startPlay( long delayMillis){
        isPlay = true;
        mDelayTime = delayMillis;
        play();
    }

    private void play(){
        mCurrentIndex ++;
        mHandler.sendEmptyMessageDelayed(PLAY, mDelayTime);
    }

    /**
     * 停止播放
     */
    public void stopPlay(){
        isPlay = false;
    }


    public void setBannerData(List<String> bannerData){
        mBannerUrlList.clear();
        mBannerUrlList.addAll(bannerData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setOffscreenPageLimit(int limit) {
        super.setOffscreenPageLimit(1);
    }

    /**
     * 手指滑动时暂停自动轮播，手指松开时重新启动自动轮播
     * @param state
     */
    public void onPageScrollStateChange(int state){
        if(!mIsAutoScroll){
            return;
        }
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE :
                if(!mGestureScroll){
                    return;
                }
                mGestureScroll = false;
                mHandler.removeMessages(PLAY);
                mHandler.sendEmptyMessageDelayed(PLAY, 100);
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
                // 手指滑动时，清除播放下一张，防止滑动过程中自动播放下一张
                mGestureScroll = true;
                mHandler.removeMessages(PLAY);
                break;
            default:
                break;
        }
    }

    /**
     * 滑动的时候更新下标
     * @param position
     */
    public void setScrollPosition(int position){
        mCurrentIndex = position;
    }

    ScrollPageListener mScrollPageListener;

    public void setScrollPageListener(ScrollPageListener mScrollPageListener) {
        this.mScrollPageListener = mScrollPageListener;
    }

    public interface ScrollPageListener {
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
        void onPageSelected(int position);
        void onPageScrollStateChanged(int state);
    }

    OnPageChangeListener mPageListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if(mScrollPageListener != null){
                mScrollPageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if(position == 0){
                return;
            }
            setScrollPosition(position);
            if(mScrollPageListener != null){
                mScrollPageListener.onPageSelected(position % 3);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            onPageScrollStateChange(state);
            if(mScrollPageListener != null){
                mScrollPageListener.onPageScrollStateChanged(state);
            }
        }
    };

    /**
     * 内部Adapter，包装setAdapter传进来的adapter，设置getCount返回Integer.MAX_VALUE
     */
    private class InnerPagerAdapter extends PagerAdapter {

        private PagerAdapter adapter;

        public InnerPagerAdapter(PagerAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return adapter.isViewFromObject(arg0, arg1);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= mBannerUrlList.size();
            if (position<0){
                position = mBannerUrlList.size()+position;
            }
            SimpleDraweeView draweeView = new SimpleDraweeView(getContext());
            FrescoUtils.showImgByUrl(mBannerUrlList.get(position), draweeView);
            container.addView(draweeView);
            //draweeView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return draweeView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            adapter.destroyItem(container, position, object);
        }

        @Override
        public void notifyDataSetChanged() {
            adapter.notifyDataSetChanged();
        }
    }
}
