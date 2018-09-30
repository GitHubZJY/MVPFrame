package com.zjyang.mvpframe.ui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zjyang.base.base.SkinManager;
import com.zjyang.mvpframe.module.home.adapter.HomePagerAdapter;
import com.zjyang.base.utils.DrawUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengjiayang on 2018/7/27.
 */

public class TabContainer extends HorizontalScrollView implements
        ViewPager.OnPageChangeListener {
    private int mLastScrollTo;
    private int mScrollState = ViewPager.SCROLL_STATE_SETTLING;
    private PluginTabStrip mTabStrip;
    private final int mTitleOffset;
    private ViewPager mViewPager;
    private Context mContext;

    private List<TextView> mTitleList;
    private int mScreenWidth = 0;

    public static final int SCROLL_RIGHT = 1;
    public static final int SCROLL_LEFT = 2;

    private int mTabViewWidth = DrawUtils.dp2px(20);
    private int mTabViewTextNormalColor = SkinManager.getInstance().getPrimaryTextColor();
    private int mTabViewTextSize = 15;


    public TabContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        }

        this.setClipChildren(false);
        setHorizontalScrollBarEnabled(false);
        mTitleOffset = DrawUtils.dp2px(52);
        mTitleList = new ArrayList<>();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTabStrip = new PluginTabStrip(getContext(), null);
    }

    private void scrollToChild(int childIdx, int dis) {
        int nums = mTabStrip.getChildCount();
        if (nums == 0 || childIdx < 0 || childIdx > nums) {
            return;
        }
        View child = mTabStrip.getChildAt(childIdx);
        if (child == null) {
            return;
        }
        int left = dis + child.getLeft();
        if (childIdx > 0 || dis > 0) {
            left -= mTitleOffset;
        }
        if (left != mLastScrollTo) {
            mLastScrollTo = left;
            scrollTo(left, 0);
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        mScrollState = arg0;
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        int nums = mTabStrip.getChildCount();
        if (nums == 0 || arg0 < 0 || arg0 >= nums) {
            return;
        }
        mTabStrip.onPageScrolled(arg0, arg1, arg2);
        View child = mTabStrip.getChildAt(arg0);
        int dis = 0;
        if (child != null) {
            dis = (int) (arg1 * child.getWidth());
        }
        scrollToChild(arg0, dis);
    }

    @Override
    public void onPageSelected(int arg0) {
        if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
            mTabStrip.onPageSelected(arg0);
            scrollToChild(arg0, 0);
        } else if (mScrollState == ViewPager.SCROLL_STATE_SETTLING) {
            mTabStrip.updateTextViewState(arg0);
            if(mListener != null){
                mListener.toggleItem(arg0);
            }
        }
    }

    public void setSelectedIndicatorColor(int color) {
        mTabStrip.setSelectedIndicatorColor(color);
    }

    public void setViewPager(ViewPager viewpager) {
        mViewPager = viewpager;
        mViewPager.setOnPageChangeListener(this);
        initChildView();
    }

    private void initChildView() {
        mTabStrip.removeAllViews();
        HomePagerAdapter adapter = (HomePagerAdapter)mViewPager.getAdapter();
        View view;
        int nums = adapter.getCount();
        for (int i = 0; i < nums; i++) {
            final int index = i;
            String title = adapter.getPagerTitle(index);
            TextView textView = new TextView(mContext);
            textView.setText(title);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(SkinManager.getInstance().getPrimaryTextColor());
            textView.setPadding(DrawUtils.dp2px(8), 0, DrawUtils.dp2px(8), 0);
            if (mTabViewTextNormalColor != -1) {
                textView.setTextColor(mTabViewTextNormalColor);
            }
            if (mTabViewTextSize != 0) {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTabViewTextSize);
            }
            if(index == 0){
                textView.setTypeface(Typeface.DEFAULT_BOLD);
                textView.setTextColor(SkinManager.getInstance().getPrimaryTextColor());
            }
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(index);
                }
            });
            mTabStrip.addView(textView);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)textView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            textView.setLayoutParams(layoutParams);
        }
        addView(mTabStrip);
    }

    ToggleItemListener mListener;

    public void setToggleItemListener(ToggleItemListener mListener) {
        this.mListener = mListener;
    }

    public interface ToggleItemListener {
        void toggleItem(int index);
    }

    public void setTabViewWidth(int width) {
        mTabViewWidth = width;
    }

    public void setTabViewTextNormalColor(int color) {
        mTabViewTextNormalColor = color;
    }

    public void setTabViewTextSize(int textSize) {
        mTabViewTextSize = textSize;
    }
}
