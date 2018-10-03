package com.zjyang.mvpframe.module.home.focus.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zjyang.base.utils.FrescoUtils;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.collagedetail.view.CollageDetailActivity;
import com.zjyang.mvpframe.module.home.focus.bean.FindTripInfo;
import com.zjyang.mvpframe.module.home.tripcircle.model.bean.TripWebInfo;

import java.util.List;

/**
 * Created by 74215 on 2018/10/1.
 */

public class TripInfoListAdapter extends RecyclerView.Adapter<TripInfoListAdapter.TripInfoListViewHolder>{

    private List<FindTripInfo> mVideoList;
    private Context mContext;

    public TripInfoListAdapter(Context mContext, List<FindTripInfo> videoList) {
        this.mContext = mContext;
        this.mVideoList = videoList;
    }

    @Override
    public TripInfoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip_info_list, parent, false);
        TripInfoListViewHolder viewHolder = new TripInfoListViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TripInfoListViewHolder holder, int position) {
        final FindTripInfo tripWebInfo = mVideoList.get(position);
        String icon = tripWebInfo.getIcon();
        String name = tripWebInfo.getName();
        String describe = tripWebInfo.getDescribe();
        final String url = tripWebInfo.getUrl();
        if(!TextUtils.isEmpty(icon)){
            FrescoUtils.showImgByUrl(icon, holder.mPreviewIv);
        }
        holder.mWebNameTv.setText(TextUtils.isEmpty(name) ? "" : name);
        holder.mDescribeTv.setText(TextUtils.isEmpty(describe) ? "" : describe);

        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CollageDetailActivity.go(mContext, tripWebInfo);
                //WebViewActivity.go(mContext, url);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    public class TripInfoListViewHolder extends RecyclerView.ViewHolder {
        CardView mRootView;
        SimpleDraweeView mPreviewIv;
        TextView mWebNameTv;
        TextView mDescribeTv;
        public TripInfoListViewHolder(View itemView) {
            super(itemView);
            mRootView = (CardView) itemView.findViewById(R.id.item_trip_info_root_view);
            mPreviewIv = (SimpleDraweeView) itemView.findViewById(R.id.user_pic_iv);
            mWebNameTv = (TextView) itemView.findViewById(R.id.user_name_tv);
            mDescribeTv = (TextView) itemView.findViewById(R.id.trip_info_describe_tv);
        }
    }
}
