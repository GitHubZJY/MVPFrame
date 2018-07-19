package com.zjyang.mvpframe.ui.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhengjiayang on 2018/7/17.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int columnNum;

    public SpaceItemDecoration(int space, int columnNum) {
        this.space = space;
        this.columnNum = columnNum;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        outRect.left = space;
        outRect.bottom = space;
        outRect.right = space;
        int index = parent.getChildLayoutPosition(view);
        if(index == 0 || index == 1){
            //outRect.top = space;
        }
        if (index % columnNum != 0) {
            //outRect.left = 0;
            outRect.left = 0;
        }
    }

}
