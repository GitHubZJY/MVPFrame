package com.zjyang.mvpframe.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by zhengjiayang on 2018/8/28.
 */

public class CustomScrollView extends ScrollView {

    private OnScrollListener listener;

    /**
     * 设置滑动距离监听器
     */
    public void setOnScrollListener(OnScrollListener listener) {
        this.listener = listener;
    }

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 滑动距离监听器
    public interface OnScrollListener{

        /**
         * 在滑动的时候调用，scrollY为已滑动的距离
         */
        void onScroll(int scrollY);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(listener!=null){
            listener.onScroll(getScrollY());
        }
    }
}
