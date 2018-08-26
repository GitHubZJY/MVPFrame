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
        FrescoUtils.showImgByUrl(UserDataManager.getInstance().getCurUser().getUserPic(), holder.mUserPic);
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{


        private SimpleDraweeView mUserPic;

        public CommentViewHolder(View itemView) {
            super(itemView);
            mUserPic = (SimpleDraweeView) itemView.findViewById(R.id.user_pic);
        }
    }
}
