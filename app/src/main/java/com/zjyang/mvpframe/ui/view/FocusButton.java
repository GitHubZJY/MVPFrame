package com.zjyang.mvpframe.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.ui.ShapeUtils;
import com.zjyang.mvpframe.utils.DrawUtils;

import static android.view.Gravity.CENTER;

/**
 * Created by zhengjiayang on 2018/9/4.
 */

public class FocusButton extends TextView{

    private boolean mIsChecked;
    Drawable mFocuedDrawable;
    Drawable mUnFocusDrawable;

    public FocusButton(Context context) {
        this(context, null);
    }

    public FocusButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FocusButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setBackground(ShapeUtils.getRoundRectDrawable(32, getResources().getColor(R.color.yellow)));
        setTextColor(getResources().getColor(R.color.primary_text_color));
        mUnFocusDrawable = getResources().getDrawable(R.drawable.ic_add_black);
        mFocuedDrawable = getResources().getDrawable(R.drawable.ic_ok);
        mUnFocusDrawable.setBounds(0, 0, DrawUtils.dp2px(10),  DrawUtils.dp2px(10));
        setCompoundDrawables(mUnFocusDrawable, null, null, null);
        setGravity(CENTER);
        setPadding(DrawUtils.dp2px(4), DrawUtils.dp2px(2), DrawUtils.dp2px(4), DrawUtils.dp2px(2));
        setTextSize(DrawUtils.dp2px(5));
        setText("关注");
        setOnClickListener(mClickListener);
    }

    public void setIsFocus(boolean isFocus){
        if(isFocus){
            setCompoundDrawables(mFocuedDrawable, null, null, null);
            setText("已关注");
        }else{
            setCompoundDrawables(mUnFocusDrawable, null, null, null);
            setText("关注");
        }
    }

    private OnClickListener mClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!mIsChecked){
                setIsFocus(true);
            }
        }
    };
}
