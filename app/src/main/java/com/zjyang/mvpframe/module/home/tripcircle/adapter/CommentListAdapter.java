package com.zjyang.mvpframe.module.home.tripcircle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.base.UserDataManager;
import com.zjyang.mvpframe.module.home.tripcircle.model.bean.CommentInfo;
import com.zjyang.mvpframe.utils.FrescoUtils;

import java.util.List;

/**
 * Created by 74215 on 2018/8/26.
 */

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentViewHolder>{

    private List<CommentInfo> mCommentList;
    private Context mContext;

    public CommentListAdapter(List<CommentInfo> mCommentList, Context mContext) {
        this.mCommentList = mCommentList;
        this.mContext = mContext;
    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_comment_list, parent, false);
        CommentViewHolder viewHolder = new CommentViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        FrescoUtils.showImgByUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523797863813&di=c1a82078d1672426d666cf4c8bd284d1&imgtype=0&src=http%3A%2F%2Fwww.rui2.net%2Fuploadfile%2Fdata%2F2015%2F0623%2F20150623114232290.jpg", holder.mUserPic);
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{


        private SimpleDraweeView mUserPic;

        public CommentViewHolder(View itemView) {
            super(itemView);
            mUserPic = (SimpleDraweeView) itemView.findViewById(R.id.user_pic);
        }
    }
}
