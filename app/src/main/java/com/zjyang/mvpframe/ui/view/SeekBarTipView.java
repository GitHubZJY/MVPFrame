package com.zjyang.mvpframe.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.utils.DrawUtils;

/**
 * Created by zhengjiayang on 2018/8/1.
 */

public class SeekBarTipView extends View{

    Paint mPaint;
    Path mPath;
    int width;  //最终的宽度
    int height;   //最终的高度
    private int DEFAULT_WIDTH = DrawUtils.dp2px(40);
    private int DEFAULT_HEIGHT = DrawUtils.dp2px(52);
    private int mTriangleHeight = DrawUtils.dp2px(10);
    private int mTriangleWidth = DrawUtils.dp2px(12);
    private int mContentPadding = DrawUtils.dp2px(2);
    private Bitmap mBitmap;
    Rect mBmSrcRect, mBmDesRect;

    public SeekBarTipView(Context context) {
        this(context, null);
    }

    public SeekBarTipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SeekBarTipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    void initView(){
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.yellow));

        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if(wSpecMode==MeasureSpec.EXACTLY){
            width = wSpecSize;
        }
        else{
            width = DEFAULT_WIDTH;
        }
        if(hSpecMode==MeasureSpec.EXACTLY){
            height = hSpecSize;
        }
        else{
            height = DEFAULT_HEIGHT;
        }
        setMeasuredDimension(width, height);
    }

    public void setBitmap(Bitmap bitmap){
        mBitmap = bitmap;
        if(mBitmap != null){
            mBmSrcRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
            mBmDesRect = new Rect(mContentPadding, mContentPadding, width - mContentPadding, height - mTriangleHeight - mContentPadding);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, width, height - mTriangleHeight, mPaint);
        mPath.moveTo(width / 2 - mTriangleWidth/2, height - mTriangleHeight);
        mPath.lineTo(width / 2 + mTriangleWidth/2, height - mTriangleHeight);
        mPath.lineTo(width / 2, height);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
        if(mBitmap != null){
            canvas.drawBitmap(mBitmap, mBmSrcRect, mBmDesRect, mPaint);
        }
    }
}
