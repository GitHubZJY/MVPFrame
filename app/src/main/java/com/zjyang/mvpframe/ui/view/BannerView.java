package com.zjyang.mvpframe.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zjyang.base.utils.FrescoUtils;

import java.util.ArrayList;
import java.util.List;

import static android.view.Gravity.BOTTOM;
import static android.view.Gravity.CENTER_HORIZONTAL;

/**
 * Created by zhengjiayang on 2018/8/31.
 */

public class BannerView extends FrameLayout{

    /**
     * 轮播图ViewPager
     */
    private ViewPager mBannerViewPager;
    /**
     * 轮播指示器
     */
    private BannerIndicator mIndicator;
    /**
     * 指示器小圆点半径
     */
    private int mCellRadius = dp2px(3);
    /**
     * 指示器小圆点间距
     */
    private int mCellMargin = dp2px(4);
    /**
     * 指示器小圆点激活状态的颜色
     */
    private int mIndicatorColor = Color.parseColor("#000000");
    /**
     * 是否自动滑动
     */
    private boolean mIsAutoScroll = true;
    /**
     * 默认页面之间自动切换的时间间隔
     */
    private long mDelayTime = 2000;
    /**
     * 轮播图地址集合
     */
    private List<String> mBannerUrlList;
    /**
     * 轮播内部Adapter,实现无限轮播
     */
    private InnerPagerAdapter mAdapter;
    /**
     * 当前真正的下标，初始值为MAX的一半，以解决初始无法左滑的问题
     */
    private int mCurrentIndex = Integer.MAX_VALUE / 2;
    //播放标志
    private boolean isPlay = false;
    //触发轮播的消息标志位
    private final int PLAY = 0x123;
    //标志是否是手势操作造成的滑动
    private boolean mGestureScroll = false;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == PLAY && mIsAutoScroll) {
                mBannerViewPager.setCurrentItem(mCurrentIndex);
                if (isPlay) {
                    play();
                }
            }
        }
    };

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs){
        initBannerViewPager(context, attrs);
        initIndicatorView(context);
    }

    /**
     * 初始化ViewPager
     * @param context
     * @param attrs
     */
    private void initBannerViewPager(Context context, AttributeSet attrs){
        mBannerViewPager = new ViewPager(context, attrs);
        FrameLayout.LayoutParams bannerParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mBannerViewPager, bannerParams);
        mBannerViewPager.addOnPageChangeListener(mPageListener);
        mBannerUrlList = new ArrayList<>();
        mAdapter = new InnerPagerAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mBannerUrlList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

            }
        });
        mBannerViewPager.setAdapter(mAdapter);
        mBannerViewPager.setCurrentItem(Integer.MAX_VALUE / 2);
    }

    /**
     * 初始化指示器
     * @param context
     */
    private void initIndicatorView(Context context){
        mIndicator = new BannerIndicator(context);
        FrameLayout.LayoutParams indicatorParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dp2px(40));
        indicatorParams.gravity = BOTTOM | CENTER_HORIZONTAL;
        addView(mIndicator, indicatorParams);
    }

    ViewPager.OnPageChangeListener mPageListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            //此处返回的position是大数
            if (position == 0 || mBannerUrlList.isEmpty()) {
                return;
            }
            setScrollPosition(position);
            int smallPos = position % mBannerUrlList.size();
            mIndicator.setCurrentPosition(smallPos);
            if (mScrollPageListener != null) {
                mScrollPageListener.onPageSelected(smallPos);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            onPageScrollStateChange(state);
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        if(hMode ==  MeasureSpec.UNSPECIFIED){
            height = dp2px(200);
        }else {
            height = hSize;
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setBannerData(List<String> bannerData) {
        mBannerUrlList.clear();
        mBannerUrlList.addAll(bannerData);
        mAdapter.notifyDataSetChanged();
        startPlay(mDelayTime);
        mIndicator.setCellCount(bannerData.size());
    }

    /**
     * 开始播放
     *
     * @param delayMillis
     */
    public void startPlay(long delayMillis) {
        isPlay = true;
        mDelayTime = delayMillis;
        play();
    }

    private void play() {
        mCurrentIndex++;
        mHandler.sendEmptyMessageDelayed(PLAY, mDelayTime);
    }

    /**
     * 停止播放
     */
    public void stopPlay() {
        isPlay = false;
    }

    /**
     * 手指滑动时暂停自动轮播，手指松开时重新启动自动轮播
     *
     * @param state
     */
    private void onPageScrollStateChange(int state) {
        if (!mIsAutoScroll) {
            return;
        }
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE:
                if (!mGestureScroll) {
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
     *
     * @param position
     */
    public void setScrollPosition(int position) {
        mCurrentIndex = position;
    }

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
            if (position < 0) {
                position = mBannerUrlList.size() + position;
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

    /**
     * 设置是否自动轮播
     * @param isAuto
     */
    public void setAutoScrollEnable(boolean isAuto) {
        mIsAutoScroll = isAuto;
    }

    /**
     * 设置是否显示指示器
     * @param flag
     */
    public void setHasIndicator(boolean flag){
        mIndicator.setVisibility(flag ? VISIBLE : GONE);
    }

    /**
     * 是否显示切换动画
     * @param isShow
     */
    public void setIsShowAnim(boolean isShow){
        if(!isShow){
            return;
        }
        mBannerViewPager.setPageTransformer(true, new ZoomOutPagerTransformer());
    }

    public class ZoomOutPagerTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        @SuppressLint("NewApi")
        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
            { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
                        / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }


    /**
     * 指示器View
     */
    public class BannerIndicator extends View{

        private int mCellCount;
        private int currentPosition;
        private Paint mPaint;

        public BannerIndicator(Context context) {
            super(context);
            init();
        }

        public void init(){
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
        }

        public void setCellCount(int cellCount) {
            mCellCount = cellCount;
            invalidate();
        }

        public void setCurrentPosition(int currentPosition) {
            this.currentPosition = currentPosition;
            invalidate();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            // 重新测量当前界面的宽度
            int width = getPaddingLeft() + getPaddingRight() + mCellRadius * 2 * mCellCount + mCellMargin * (mCellCount - 1);
            int height = getPaddingTop() + getPaddingBottom() + mCellRadius * 2;
            width = resolveSize(width, widthMeasureSpec);
            height = resolveSize(height, heightMeasureSpec);
            setMeasuredDimension(width, height);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            for (int i = 0; i < mCellCount; i++) {
                if (i == currentPosition) {
                    mPaint.setColor(mIndicatorColor);
                } else {
                    mPaint.setColor(Color.WHITE);
                }
                int left = getPaddingLeft() + i * mCellRadius * 2 + mCellMargin * i;

                canvas.drawCircle(left + mCellRadius, getHeight() / 2, mCellRadius, mPaint);
            }
        }
    }

    /**
     * dp转px
     * @param dpVal   dp value
     * @return px value
     */
    public int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                getContext().getResources().getDisplayMetrics());
    }

    /**
     * 滚动监听回调接口
     */

    ScrollPageListener mScrollPageListener;

    public void setScrollPageListener(ScrollPageListener mScrollPageListener) {
        this.mScrollPageListener = mScrollPageListener;
    }

    public interface ScrollPageListener {
        void onPageSelected(int position);
    }
}
