package com.zjyang.mvpframe.module.home.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.base.SkinManager;
import com.zjyang.mvpframe.module.home.model.HomeModel;
import com.zjyang.mvpframe.ui.ShapeUtils;
import com.zjyang.mvpframe.utils.DrawUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by 74215 on 2018/5/13.
 * 封裝首页底部tab,以便于动态更改顺序位置（更改位置在HomeModel类中进行设置）
 */

public class HomeBottomBar extends LinearLayout{

    private int mScreenWidth;
    private List<LinearLayout> mTabItemList = new ArrayList<>();
    private LinearLayout mDiscoverTab;
    private LinearLayout mFocusTab;
    private LinearLayout mMessageTab;
    private LinearLayout mMeTab;
    private LinearLayout mCenterSpace; //中间腾出间隔
    private ImageView mDiscoverIv;
    private ImageView mFocusIv;
    private ImageView mMessageIv;
    private ImageView mMeIv;
    private TextView mDiscoverTv;
    private TextView mFocusTv;
    private TextView mMessageTv;
    private TextView mMeTv;

    private Drawable mDiscoverNormal = getResources().getDrawable(R.drawable.bottom_bar_video_nomarl);
    private Drawable mFocusNormal = getResources().getDrawable(R.drawable.bottom_bar_focus_normal);
    private Drawable mCircleNormal = getResources().getDrawable(R.drawable.bottom_bar_circle_normal);
    private Drawable mMeNormal = getResources().getDrawable(R.drawable.bottom_bar_me_normal);


    private Drawable mDiscoverActive = ShapeUtils.drawColor(getResources().getDrawable(R.drawable.bottom_bar_video_nomarl), SkinManager.getInstance().getPrimaryColor());
    private Drawable mFocusActive = ShapeUtils.drawColor(getResources().getDrawable(R.drawable.bottom_bar_focus_normal), SkinManager.getInstance().getPrimaryColor());
    private Drawable mCircleActive = ShapeUtils.drawColor(getResources().getDrawable(R.drawable.bottom_bar_circle_normal), SkinManager.getInstance().getPrimaryColor());
    private Drawable mMeNorActive = ShapeUtils.drawColor(getResources().getDrawable(R.drawable.bottom_bar_me_normal), SkinManager.getInstance().getPrimaryColor());

    public HomeBottomBar(Context context) {
        this(context, null);
    }

    public HomeBottomBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeBottomBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScreenWidth = getScreenWidth(context);
        initView(context);
    }

    private void initView(Context context){
        setOrientation(HORIZONTAL);
        setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        mDiscoverTab = new LinearLayout(context);
        mFocusTab = new LinearLayout(context);
        mMessageTab = new LinearLayout(context);
        mMeTab = new LinearLayout(context);
        mDiscoverIv = new ImageView(context);
        mFocusIv = new ImageView(context);
        mMessageIv = new ImageView(context);
        mMeIv = new ImageView(context);
        mDiscoverTv = new TextView(context);
        mFocusTv = new TextView(context);
        mMessageTv = new TextView(context);
        mMeTv = new TextView(context);
        mCenterSpace = new LinearLayout(context);

        mTabItemList.add(mDiscoverTab);
        mTabItemList.add(mFocusTab);
        mTabItemList.add(mMessageTab);
        mTabItemList.add(mMeTab);


        LinearLayout.LayoutParams mItemIvParams = new LinearLayout.LayoutParams(DrawUtils.dp2px(24), DrawUtils.dp2px(24));
        mItemIvParams.setMargins(0,0,0, DrawUtils.dp2px(3));

        mDiscoverIv.setImageDrawable(mDiscoverNormal);
        //mDiscoverIv.setBackgroundResource(R.drawable.bottom_bar_video_nomarl);
        mDiscoverIv.setLayoutParams(mItemIvParams);
        mFocusIv.setImageDrawable(mFocusNormal);
        //mFocusIv.setBackgroundResource(R.drawable.bottom_bar_focus_normal);
        mFocusIv.setLayoutParams(mItemIvParams);
        mMessageIv.setImageDrawable(mCircleNormal);
        //mMessageIv.setBackgroundResource(R.drawable.bottom_bar_circle_normal);
        mMessageIv.setLayoutParams(mItemIvParams);
        mMeIv.setImageDrawable(mMeNormal);
        //mMeIv.setBackgroundResource(R.drawable.bottom_bar_me_normal);
        mMeIv.setLayoutParams(mItemIvParams);
        mDiscoverTab.addView(mDiscoverIv);
        mFocusTab.addView(mFocusIv);
        mMessageTab.addView(mMessageIv);
        mMeTab.addView(mMeIv);

        initTabItemTv(mDiscoverTv);
        initTabItemTv(mFocusTv);
        initTabItemTv(mMessageTv);
        initTabItemTv(mMeTv);
        mDiscoverTv.setText(getResources().getString(R.string.discover_tab_name));
        mFocusTv.setText(getResources().getString(R.string.focus_tab_name));
        mMessageTv.setText(getResources().getString(R.string.message_tab_name));
        mMeTv.setText(getResources().getString(R.string.me_tab_name));
        mDiscoverTab.addView(mDiscoverTv);
        mFocusTab.addView(mFocusTv);
        mMessageTab.addView(mMessageTv);
        mMeTab.addView(mMeTv);

        initTabItem(mDiscoverTab);
        initTabItem(mFocusTab);
        initTabItem(mCenterSpace);
        initTabItem(mMessageTab);
        initTabItem(mMeTab);

        sortTab();

        initOnClick();
    }

    private void initTabItemTv(TextView itemTv){
        itemTv.setTextColor(getResources().getColor(R.color.primary_text_color));
        itemTv.setTextSize(DrawUtils.dp2px(5));
        LinearLayout.LayoutParams mItemTvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemTv.setLayoutParams(mItemTvParams);
    }


    private void initTabItem(LinearLayout tabItem){
        tabItem.setOrientation(VERTICAL);
        tabItem.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams mDiscoverParams = new LinearLayout.LayoutParams(mScreenWidth / 5, ViewGroup.LayoutParams.MATCH_PARENT);
        tabItem.setLayoutParams(mDiscoverParams);
    }

    public void sortTab(){
        removeAllViews();
        mDiscoverTab.setTag(HomeModel.DISCOVER_TAB_SORT);
        mFocusTab.setTag(HomeModel.FOCUS_TAB_SORT);
        mMessageTab.setTag(HomeModel.MESSAGE_TAB_SORT);
        mMeTab.setTag(HomeModel.USER_TAB_SORT);
        Collections.sort(mTabItemList, new TabItemComparator());
        for(int i=0; i < mTabItemList.size(); i++){
            if(i == 0){
                Drawable mFirstActiveDrawable;
                if(mTabItemList.get(i) == mDiscoverTab){
                    mFirstActiveDrawable = mDiscoverActive;
                }else if(mTabItemList.get(i) == mFocusTab){
                    mFirstActiveDrawable = mFocusActive;
                }else if(mTabItemList.get(i) == mMessageTab){
                    mFirstActiveDrawable = mCircleActive;
                }else{
                    mFirstActiveDrawable = mMeNorActive;
                }
                ((ImageView)mTabItemList.get(i).getChildAt(0)).setImageDrawable(mFirstActiveDrawable);
            }
            if(i == 2){
                addView(mCenterSpace);
            }
            addView(mTabItemList.get(i));
        }
    }

    private void initOnClick(){
        mDiscoverTab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mDiscoverIv.setImageDrawable(mDiscoverActive);
                mFocusIv.setImageDrawable(mFocusNormal);
                mMessageIv.setImageDrawable(mCircleNormal);
                mMeIv.setImageDrawable(mMeNormal);
                mDiscoverTv.setTextColor(SkinManager.getInstance().getPrimaryColor());
                mFocusTv.setTextColor(Color.BLACK);
                mMessageTv.setTextColor(Color.BLACK);
                mMeTv.setTextColor(Color.BLACK);
                if(mTabClickListener != null){
                    mTabClickListener.clickTab((Integer) mDiscoverTab.getTag());
                }
            }
        });
        mFocusTab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mDiscoverIv.setImageDrawable(mDiscoverNormal);
                mFocusIv.setImageDrawable(mFocusActive);
                mMessageIv.setImageDrawable(mCircleNormal);
                mMeIv.setImageDrawable(mMeNormal);
                mDiscoverTv.setTextColor(Color.BLACK);
                mFocusTv.setTextColor(SkinManager.getInstance().getPrimaryColor());
                mMessageTv.setTextColor(Color.BLACK);
                mMeTv.setTextColor(Color.BLACK);
                if(mTabClickListener != null){
                    mTabClickListener.clickTab((Integer) mFocusTab.getTag());
                }
            }
        });
        mMessageTab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mDiscoverIv.setImageDrawable(mDiscoverNormal);
                mFocusIv.setImageDrawable(mFocusNormal);
                mMessageIv.setImageDrawable(mCircleActive);
                mMeIv.setImageDrawable(mMeNormal);
                mDiscoverTv.setTextColor(Color.BLACK);
                mFocusTv.setTextColor(Color.BLACK);
                mMessageTv.setTextColor(SkinManager.getInstance().getPrimaryColor());
                mMeTv.setTextColor(Color.BLACK);
                if(mTabClickListener != null){
                    mTabClickListener.clickTab((Integer) mMessageTab.getTag());
                }
            }
        });
        mMeTab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mDiscoverIv.setImageDrawable(mDiscoverNormal);
                mFocusIv.setImageDrawable(mFocusNormal);
                mMessageIv.setImageDrawable(mCircleNormal);
                mMeIv.setImageDrawable(mMeNorActive);
                mDiscoverTv.setTextColor(Color.BLACK);
                mFocusTv.setTextColor(Color.BLACK);
                mMessageTv.setTextColor(Color.BLACK);
                mMeTv.setTextColor(SkinManager.getInstance().getPrimaryColor());
                if(mTabClickListener != null){
                    mTabClickListener.clickTab((Integer) mMeTab.getTag());
                }
            }
        });
    }

    class TabItemComparator implements Comparator<LinearLayout> {


        @Override
        public int compare(LinearLayout tab1, LinearLayout tab2) {
            if((Integer)tab1.getTag() > (Integer)tab2.getTag()){
                return 1;
            }else if((Integer)tab1.getTag() < (Integer)tab2.getTag()){
                return -1;
            }
            return 0;
        }


    }

    private TabClickListener mTabClickListener;

    public void setTabClickListener(TabClickListener mTabClickListener) {
        this.mTabClickListener = mTabClickListener;
    }

    public interface TabClickListener {
        void clickTab(int index);
    }


    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return width of the screen.
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
