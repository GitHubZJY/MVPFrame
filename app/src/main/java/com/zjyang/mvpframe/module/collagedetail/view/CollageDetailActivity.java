package com.zjyang.mvpframe.module.collagedetail.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zjyang.base.base.BaseActivity;
import com.zjyang.base.utils.FrescoUtils;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.collagedetail.CollageDetailTasksContract;
import com.zjyang.mvpframe.module.collagedetail.presenter.CollageDetailPresenter;
import com.zjyang.mvpframe.module.home.focus.bean.FindTripInfo;
import com.zjyang.mvpframe.module.home.tripcircle.adapter.CommentListAdapter;
import com.zjyang.mvpframe.module.home.tripcircle.model.bean.CommentInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zhengjiayang on 2018/9/3.
 */

public class CollageDetailActivity extends BaseActivity<CollageDetailPresenter> implements CollageDetailTasksContract.View{

    private Unbinder unbinder;
    @BindView(R.id.toolbar_left_btn)
    Button mBackBtn;
    @BindView(R.id.comment_lv)
    RecyclerView mCommentLv;
    @BindView(R.id.user_name_tv)
    TextView mAuthorTv;
    @BindView(R.id.user_describe)
    TextView mDescribeTv;
    @BindView(R.id.user_pic_iv)
    SimpleDraweeView mAuthorPicIv;

    private CommentListAdapter mCommentAdapter;
    private static final String INTENT_DATA = "INTENT_DATA";
    private FindTripInfo mDataInfo;


    public static void go(Context context, FindTripInfo findTripInfo){
        Intent intent = new Intent(context, CollageDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(INTENT_DATA, findTripInfo);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public CollageDetailPresenter createPresenter() {
        return new CollageDetailPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage_detail);
        unbinder = ButterKnife.bind(this);
        //mPresenter.fillMarkData();

        List<CommentInfo> commentInfoList = new ArrayList<>();

        for(int i=0; i<5; i++){
            CommentInfo commentInfo = new CommentInfo();
            commentInfoList.add(commentInfo);
        }

        mCommentAdapter = new CommentListAdapter(commentInfoList, this);
        mCommentLv.setLayoutManager(new LinearLayoutManager(this));
        mCommentLv.setAdapter(mCommentAdapter);

        getIntentData();
    }

    public void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mDataInfo = bundle.getParcelable(INTENT_DATA);
                initData();
            }
        }
    }

    public void initData(){
        String title = mDataInfo.getName();
        String describe = mDataInfo.getDescribe();
        mAuthorTv.setText(TextUtils.isEmpty(title) ? "" : title);
        mDescribeTv.setText(TextUtils.isEmpty(describe) ? "" : describe);
        FrescoUtils.showImgByUrl(mDataInfo.getUrl(), mAuthorPicIv);
    }


    @OnClick(R.id.toolbar_left_btn)
    void clickBack(){
        finish();
    }

    @Override
    public void fillDataToCommentLv(List<CommentInfo> collageCommentList) {

    }



    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
