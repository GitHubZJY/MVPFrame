package com.zjyang.mvpframe.ui.view;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.utils.DrawUtils;
import com.zjyang.mvpframe.utils.ScreenUtils;

/**
 * <br>类描述: 滑动指示器
 * <br>功能详细描述:
 * 
 * @author  huanglun
 * @date  [2014-1-10]
 */
public class PluginTabStrip extends LinearLayout {
	private int mIndexForSelection;
	private final Paint mSelectedUnderlinePaint;
	private final int mSelectedUnderlineThickness;
	private float mSelectionOffset;
	private int mHightPosition = 0;
	private int mDefaultColor = 0;
	private int mHightColor = 0;
	private int mUnderLineWidth = 0;
	private int mIndicatorPadding = 8;
	

	public PluginTabStrip(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);
		Resources rsc = context.getResources();
		mIndicatorPadding = context.getResources().getDimensionPixelSize(
				R.dimen.goplugin_home_tab_strip_selected_indicator_padding);
		mSelectedUnderlineThickness = DrawUtils.dp2px(2);
		
		mSelectedUnderlinePaint = new Paint();
		mSelectedUnderlinePaint.setColor(rsc.getColor(R.color.goplay_home_tab_strip_selected_indicator));
		mSelectedUnderlinePaint.setStrokeWidth(rsc.getDimensionPixelSize(R.dimen.goplugin_home_tab_strip_selected_indicator_width));

		mDefaultColor = getContext().getResources().getColor(R.color.text_color_gray);
		mHightColor = getContext().getResources().getColor(R.color.primary_text_color);
		
		mUnderLineWidth = ScreenUtils.getsScreenWidth();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int h = getHeight();
		int childNums = getChildCount();
		View child = null;
		if (childNums > 0) {
			child = getChildAt(mIndexForSelection);
			int left = child.getLeft();
			int right = child.getRight();
			if (mSelectionOffset > 0.0f && (mIndexForSelection < childNums - 1)) {
				View nextChild = getChildAt(mIndexForSelection + 1);
				int nextLeft = nextChild.getLeft();
				int nextRight = nextChild.getRight();
				left = (int) (mSelectionOffset * nextLeft + (1.0f - mSelectionOffset) * left);
				right = (int) (mSelectionOffset * nextRight + (1.0f - mSelectionOffset) * right);
			}
			canvas.drawRect(left+mIndicatorPadding, h - mSelectedUnderlineThickness, right-mIndicatorPadding, h, mSelectedUnderlinePaint);
		}

	}

	/**
	 * 功能简述: 设置高亮文字
	 * @param hightPosition
	 */
	public void updateTextViewState(int hightPosition) {
		if (hightPosition != -1) {
			int childCount = getChildCount();
			if (mHightPosition != -1 && mHightPosition < childCount) {
				TextView labelText = (TextView)getChildAt(mHightPosition);
				if (labelText != null) {
					labelText.setTextColor(mDefaultColor);
				}
			}

			if (hightPosition != -1 && hightPosition < childCount) {
				TextView labelText = (TextView)getChildAt(hightPosition);
				if (labelText != null) {
					labelText.setTextColor(mHightColor);
				}
			}
			mHightPosition = hightPosition;
		}
	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {
		mIndexForSelection = arg0;
		mSelectionOffset = arg1;
		invalidate();
	}

	public void onPageSelected(int arg0) {
		mIndexForSelection = arg0;
		mSelectionOffset = 0.0F;
		invalidate();
	}

	public void setSelectedIndicatorColor(int color) {
		mSelectedUnderlinePaint.setColor(color);
		invalidate();
	}
	
	public void setIndicatorColor(int selectedColor, int normalColor) {
		if (mSelectedUnderlinePaint != null) {
			mSelectedUnderlinePaint.setColor(selectedColor);
		}
	}
	
	public void setTitleTextColor(int selectedColor, int normalColor) {
		mHightColor = selectedColor;
		mDefaultColor = normalColor;
	}
	
	public void setUnderlineWidth(int underLineWidth) {
		mUnderLineWidth = underLineWidth;
	}
	
	public int getHightPosition() {
		return mHightPosition;
	}
	
}