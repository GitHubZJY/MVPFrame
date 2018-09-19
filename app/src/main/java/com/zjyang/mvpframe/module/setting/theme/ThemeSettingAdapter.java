package com.zjyang.mvpframe.module.setting.theme;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.base.SkinManager;
import com.zjyang.mvpframe.ui.ShapeUtils;
import com.zjyang.mvpframe.utils.DrawUtils;

import java.util.List;

/**
 * Created by zhengjiayang on 2018/9/18.
 */

public class ThemeSettingAdapter extends RecyclerView.Adapter<ThemeSettingAdapter.ThemeSettingViewHolder>{

    private Context mContext;
    private List<ThemeInfo> mThemeList;
    private int curSelect;

    public ThemeSettingAdapter(Context mContext, List<ThemeInfo> mThemeList) {
        this.mContext = mContext;
        this.mThemeList = mThemeList;
    }

    @Override
    public ThemeSettingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_theme_setting, parent, false);
        ThemeSettingViewHolder viewHolder = new ThemeSettingViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ThemeSettingViewHolder holder, final int position) {
        holder.mColorIv.setBackground(ShapeUtils.getRoundRectDrawable(DrawUtils.dp2px(10), Color.parseColor(mThemeList.get(position).getThemeColor())));
        holder.mSelectIv.setVisibility(curSelect == position ? View.VISIBLE : View.GONE);
        holder.mColorIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mSelectIv.setVisibility(View.VISIBLE);
                curSelect = position;
                notifyDataSetChanged();
                if(mListener != null){
                    mListener.select(mThemeList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mThemeList == null ? 0 : mThemeList.size();
    }


    public class ThemeSettingViewHolder extends RecyclerView.ViewHolder{

        private ImageView mColorIv;
        private ImageView mSelectIv;

        public ThemeSettingViewHolder(View itemView) {
            super(itemView);
            mColorIv = (ImageView) itemView.findViewById(R.id.color_iv);
            mSelectIv = (ImageView) itemView.findViewById(R.id.select_iv);
        }
    }

    public ThemeInfo getCurApplyTheme(){
        if(mThemeList == null || mThemeList.isEmpty()){
            return null;
        }
        return mThemeList.get(curSelect);
    }

    public void setCurSelect(int curSelect) {
        this.curSelect = curSelect;
    }

    SelectThemeListener mListener;

    public void setSelectThemeListener(SelectThemeListener mListener) {
        this.mListener = mListener;
    }

    public interface SelectThemeListener {
        void select(ThemeInfo themeInfo);
    }


}
