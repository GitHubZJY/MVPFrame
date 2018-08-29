package com.zjyang.mvpframe.module.home.tripcircle.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.utils.DrawUtils;

/**
 * Created by zhengjiayang on 2018/8/29.
 */

public class BannerIndicator extends View{

    private Context mContext;
    private int mCellCount;
    private int currentPosition;
    private int mCellRadius = DrawUtils.dp2px(3);
    private int mCellMargin = DrawUtils.dp2px(4);
    private Paint mPaint;

    public BannerIndicator(Context context) {
        this(context, null);
    }

    public BannerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
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
                mPaint.setColor(getResources().getColor(R.color.yellow));
            } else {
                mPaint.setColor(Color.WHITE);
            }
            int left = getPaddingLeft() + i * mCellRadius * 2 + mCellMargin * i;

            canvas.drawCircle(left + mCellRadius, getHeight() / 2, mCellRadius, mPaint);
        }
    }
}
