package com.zjyang.mvpframe.module.idcard.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.base.BaseActivity;
import com.zjyang.mvpframe.module.idcard.IdCardTasksContract;
import com.zjyang.mvpframe.module.idcard.model.bean.IdCardInfo;
import com.zjyang.mvpframe.module.idcard.presenter.IdCardPresenter;
import com.zjyang.mvpframe.ui.ShapeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zhengjiayang on 2018/3/2.
 */

public class IdCardSrcActivity extends BaseActivity implements IdCardTasksContract.View{

    private Unbinder unbinder;

    @BindView(R.id.root_view)
    public LinearLayout mRootView;
    @BindView(R.id.card_ed)
    public EditText mCardEd;
    @BindView(R.id.search_btn)
    public Button mSearchBtn;
    @BindView(R.id.area_tag_tv)
    public TextView mAreaTagTv;
    @BindView(R.id.area_tv)
    public TextView mAreaTv;
    @BindView(R.id.sex_tag_tv)
    public TextView mSexTagTv;
    @BindView(R.id.sex_tv)
    public TextView mSexTv;
    @BindView(R.id.birthday_tag_tv)
    public TextView mBirthdayTagTv;
    @BindView(R.id.birthday_tv)
    public TextView mBirthdayTv;
    @BindView(R.id.progress)
    public ProgressBar mProgress;

    private IdCardTasksContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);// 允许使用transitions
        setContentView(R.layout.activity_idcardsrc);

        unbinder = ButterKnife.bind(this);
        mPresenter = new IdCardPresenter(this);
        initView();
    }

    public void initView(){
        mProgress.setTransitionName("progress");

        mSearchBtn.setBackground(ShapeUtils.getRoundRectDrawable());
        mAreaTagTv.setBackground(ShapeUtils.getRoundRectDrawable(0));
        mSexTagTv.setBackground(ShapeUtils.getRoundRectDrawable(0));
        mBirthdayTagTv.setBackground(ShapeUtils.getRoundRectDrawable(0));
    }

    @Override
    public void showIdCardNotNullTip() {
        Toast.makeText(this, "身份证不能为空",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSearchResult(IdCardInfo idCardInfo) {
        mAreaTv.setText(idCardInfo.getArea());
        mSexTv.setText(idCardInfo.getSex());
        mBirthdayTv.setText(idCardInfo.getBirthday());
    }

    @Override
    public void searchFail() {
        Toast.makeText(this, "查询不到结果",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.search_btn)
    void clickSearch(){
        mPresenter.checkAndSearch(mCardEd.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
