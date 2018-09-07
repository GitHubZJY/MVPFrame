package com.zjyang.recorder.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.aliyun.struct.effect.EffectBean;
import com.aliyun.struct.effect.EffectFilter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zjyang.recorder.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 74215 on 2018/8/18.
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder>{

    private List<EffectFilter> filterList;
    private Context mContext;
    private int mCurSelectIndex;

    public FilterAdapter(Context context) {
        mContext = context;
        filterList = new ArrayList<>();
        mCurSelectIndex = 0;
    }

    @Override
    public int getItemCount() {
        return filterList == null ? 0 : filterList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final EffectFilter filter = filterList.get(position);

        if(filter == null){
            return;
        }
        String filterName = filter.getName();
        holder.nameTv.setText(TextUtils.isEmpty(filterName) ? "原片" : filterName);
        holder.mDefaultTv.setVisibility(TextUtils.isEmpty(filterName) ? View.VISIBLE : View.GONE);
        Log.d("filter", filter.getPath() + "/icon.png");
        holder.previewIv.setImageURI(Uri.parse("file://"+filter.getPath() + "/icon.png"));
        if(mCurSelectIndex == position){
            holder.previewBgIv.setVisibility(View.VISIBLE);
        }else{
            holder.previewBgIv.setVisibility(View.GONE);
        }

        holder.previewIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifySelectIndex(position);
                EffectBean effectInfo = new EffectBean();
                effectInfo.setPath(filter.getPath());
                effectInfo.setId(position);
                if(mListener != null){
                    mListener.changeFilter(effectInfo);
                }
            }
        });
    }


    public void setFilterList(List<EffectFilter> data){
        if(filterList == null){
            return;
        }
        filterList.clear();
        filterList.addAll(data);
    }

    public void notifySelectIndex(int position){
        mCurSelectIndex = position;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private SimpleDraweeView previewIv;
        private ImageView previewBgIv;
        private TextView nameTv;
        private TextView mDefaultTv;


        public MyViewHolder(View itemView) {
            super(itemView);
            previewIv = itemView.findViewById(R.id.filter_preview_pic);
            previewBgIv = itemView.findViewById(R.id.bg_filter_pic);
            nameTv = itemView.findViewById(R.id.filter_name_tv);
            mDefaultTv = itemView.findViewById(R.id.default_tv);
        }
    }

    OnFilterChangeListener mListener;

    public void setFilterChangeListener(OnFilterChangeListener mListener) {
        this.mListener = mListener;
    }

    public interface OnFilterChangeListener {
        void changeFilter(EffectBean effectBean);
    }
}
