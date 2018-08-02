package com.zjyang.mvpframe.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;

import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.utils.DrawUtils;

import java.lang.reflect.Field;

/**
 * Created by zhengjiayang on 2018/8/1.
 * 带提示的seekbar
 */

public class SelectTipSeekBar extends FrameLayout implements View.OnTouchListener, SeekBar.OnSeekBarChangeListener {

    public static final String TAG = "SelectTipSeekBar";

    private SeekBar mSeekBar;
    private SeekBarTipView mTipView;
    private PopupWindow mPopupWindow;

    private Rect mSeekBarRect = new Rect();
    private Rect mRootRect = new Rect();
    private int mSeekBarX;
    private int mSeekBarY;
    private int mSeekBarPaddingStart;
    private int mSeekBarPaddingEnd;
    private int TIP_VIEW_WIDTH = 60;
    private int TIP_VIEW_HEIGHT = 72;

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
        mSeekBar = new SeekBar(context);
        mSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.bg_video_seekbar));
        mSeekBar.setThumb(getResources().getDrawable(R.drawable.seekbar_thumb_normal));
        FrameLayout.LayoutParams seekBarParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(mSeekBar, seekBarParams);
        seekBarParams.setMargins(0, 0, 0, 0);
        //反射设置SeekBar的高度
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
        mSeekBar.setOnSeekBarChangeListener(this);
        initHintPopup();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

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
        if(mTipView != null){
            mTipView.setBitmap(bitmap);
        }
    }

    /**
     * 初始化提示弹框
     */
    private void initHintPopup() {
        mTipView = new SeekBarTipView(getContext());
        mPopupWindow = new PopupWindow(mTipView, DrawUtils.dp2px(TIP_VIEW_WIDTH), DrawUtils.dp2px(TIP_VIEW_HEIGHT), true);
    }

    /**
     * 展示提示弹框
     * @param x
     * @param y
     */
    private void showPopup(float x, float y) {
        if(mPopupWindow != null){
            mPopupWindow.showAtLocation(mSeekBar, Gravity.NO_GRAVITY, (int)x, (int)y);
        }

    }

    /**
     * 随着滑动更新弹框位置与seekbar游标一致
     * @param x
     * @param y
     */
    private void updatePopup(float x, float y){
        if(mPopupWindow != null){
            mPopupWindow.update(mSeekBar, (int)x, (int)y, -1, -1);
        }
    }

    /**
     * 隐藏提示弹框
     */
    private void hidePopup() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }




    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //获取进度条在屏幕中的起点横坐标
        mSeekBar.getGlobalVisibleRect(mSeekBarRect);
        mSeekBarX = mSeekBarRect.left;
        mSeekBarY = mSeekBarRect.top;
        //获取该自定义View在屏幕中的起点横坐标
        getGlobalVisibleRect(mRootRect);
        mSeekBarPaddingStart = mSeekBar.getPaddingStart();
        mSeekBarPaddingEnd = mSeekBar.getPaddingEnd();
        float x = motionEvent.getRawX();
        if(x < mSeekBarX + mSeekBarPaddingStart || x > mSeekBarX + mSeekBar.getWidth() - mSeekBarPaddingEnd){
            return true;
        }
        float curProgress = mSeekBar.getMax()*(x - mSeekBarX - mSeekBarPaddingStart)*1.0f/(mSeekBar.getWidth() - mSeekBarPaddingStart - mSeekBarPaddingEnd)*1.0f;
        mSeekBar.setProgress((int)curProgress);
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                showPopup(x - mTipView.getWidth()/2, mSeekBarY -  mTipView.getHeight() - mSeekBar.getHeight());
                break;
            case MotionEvent.ACTION_MOVE:
                updatePopup(x - mTipView.getWidth()/2 - mSeekBarX, - mTipView.getHeight() - mSeekBar.getHeight());
                break;
            case MotionEvent.ACTION_UP:
                hidePopup();
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
