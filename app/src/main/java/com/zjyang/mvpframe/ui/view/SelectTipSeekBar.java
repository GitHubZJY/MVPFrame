package com.zjyang.mvpframe.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.utils.DrawUtils;

import java.lang.reflect.Field;

/**
 * Created by zhengjiayang on 2018/8/1.
 */

public class SelectTipSeekBar extends FrameLayout implements View.OnTouchListener, SeekBar.OnSeekBarChangeListener {

    public static final String TAG = "SelectTipSeekBar";

    private SeekBar mSeekBar;
    private SeekBarTipView mTipView;

    private int[] mSeekBarLoc = new int[2];
    private int[] mRootLoc = new int[2];
    private int mSeekBarX;
    private int mRootX;
    private int mSeekBarPaddingStart;
    private int mSeekBarPaddingEnd;

    public SelectTipSeekBar(Context context) {
        this(context, null);
    }

    public SelectTipSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectTipSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context){
        mTipView = new SeekBarTipView(context);
        FrameLayout.LayoutParams tipParams = new LayoutParams(DrawUtils.dp2px(60), DrawUtils.dp2px(72));
        addView(mTipView, tipParams);
        mTipView.setVisibility(GONE);

        mSeekBar = new SeekBar(context);
        mSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.bg_video_seekbar));
        mSeekBar.setThumb(getResources().getDrawable(R.drawable.seekbar_thumb_normal));
        FrameLayout.LayoutParams seekBarParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(mSeekBar, seekBarParams);
        seekBarParams.setMargins(0, DrawUtils.dp2px(100), 0, 0);

        try {
            Class<?> superclass = mSeekBar.getClass().getSuperclass().getSuperclass();
            Field mMaxHeight = superclass.getDeclaredField("mMaxHeight");
            mMaxHeight.setAccessible(true);
            mMaxHeight.set(mSeekBar,DrawUtils.dp2px(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mSeekBar.setMinimumHeight(DrawUtils.dp2px(2));

        mSeekBar.setOnTouchListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取进度条在屏幕中的起点横坐标
        mSeekBar.getLocationOnScreen(mSeekBarLoc);
        mSeekBarX = mSeekBarLoc[0];
        //获取该自定义View在屏幕中的起点横坐标
        getLocationOnScreen(mRootLoc);
        mRootX = mRootLoc[0];
        mSeekBarPaddingStart = mSeekBar.getPaddingStart();
        mSeekBarPaddingEnd = mSeekBar.getPaddingEnd();
    }

    /**
     * 设置进度条最大值
     * @param max
     */
    public void setMax(int max){
        mSeekBar.setMax(max);
    }

    /**
     * 设置指示器显示的图片
     * @param bitmap
     */
    public void setBitmap(Bitmap bitmap){
        mTipView.setBitmap(bitmap);
    }



    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = motionEvent.getRawX();

        float y = mSeekBar.getTop();
        if(x < mSeekBarX + mSeekBarPaddingStart || x > mSeekBarX + mSeekBar.getWidth() - mSeekBarPaddingEnd){
            return true;
        }
        float curProgress = mSeekBar.getMax()*(x - mSeekBarX - mSeekBarPaddingStart)/(mSeekBar.getWidth() - mSeekBarPaddingStart - mSeekBarPaddingEnd);
        mSeekBar.setProgress((int)curProgress);
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                mTipView.setVisibility(View.VISIBLE);
                //这里setX setY都是相对父布局左上顶点来作为原点计算
                mTipView.setX(x - mTipView.getWidth()/2 - mRootX);
                mTipView.setY(y -  mTipView.getHeight());
                break;
            case MotionEvent.ACTION_MOVE:
                mTipView.setX(x - mTipView.getWidth()/2 - mRootX);
                mTipView.setY(y - mTipView.getHeight());
                break;
            case MotionEvent.ACTION_UP:
                mTipView.setVisibility(View.GONE);
                break;
        }
        return true;
    }

    /******************************************设置滑动监听接口start****************************************/
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener listener){
        this.mListener = listener;
    }
    OnSeekBarChangeListener mListener;
    public interface OnSeekBarChangeListener {
        void onProgressChanged(SeekBar seekBar, int i, boolean b);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if(mListener != null){
            mListener.onProgressChanged(seekBar, i, b);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
/******************************************设置滑动监听接口end****************************************/
}
