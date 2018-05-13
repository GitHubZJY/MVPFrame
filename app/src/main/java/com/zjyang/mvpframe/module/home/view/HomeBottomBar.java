package com.zjyang.mvpframe.module.home.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.home.model.HomeModel;
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


        LinearLayout.LayoutParams mItemIvParams = new LinearLayout.LayoutParams(DrawUtils.dp2px(26), DrawUtils.dp2px(26));


        mDiscoverIv.setBackgroundResource(R.drawable.ic_bottom_tab_hot);
        mDiscoverIv.setLayoutParams(mItemIvParams);
        mFocusIv.setBackgroundResource(R.drawable.ic_bottom_tab_like);
        mFocusIv.setLayoutParams(mItemIvParams);
        mMessageIv.setBackgroundResource(R.drawable.ic_bottom_tab_msg);
        mMessageIv.setLayoutParams(mItemIvParams);
        mMeIv.setBackgroundResource(R.drawable.ic_bottom_tab_me);
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
                if(mTabClickListener != null){
                    mTabClickListener.clickTab((Integer) mDiscoverTab.getTag());
                }
            }
        });
        mFocusTab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTabClickListener != null){
                    mTabClickListener.clickTab((Integer) mFocusTab.getTag());
                }
            }
        });
        mMessageTab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTabClickListener != null){
                    mTabClickListener.clickTab((Integer) mMessageTab.getTag());
                }
            }
        });
        mMeTab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
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
