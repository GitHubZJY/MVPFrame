package com.zjyang.mvpframe.module.login.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zjyang.mvpframe.MainActivity;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.base.BaseActivity;
import com.zjyang.mvpframe.module.base.BasePresenter;
import com.zjyang.mvpframe.module.home.view.HomeActivity;
import com.zjyang.mvpframe.module.login.LoginTasksContract;
import com.zjyang.mvpframe.module.login.presenter.LoginPresenter;
import com.zjyang.mvpframe.ui.ShapeUtils;
import com.zjyang.mvpframe.ui.view.JellyInterpolator;
import com.zjyang.mvpframe.utils.DrawUtils;
import com.zjyang.mvpframe.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zhengjiayang on 2018/3/1.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginTasksContract.View{

    private Unbinder unbinder;

    @BindView(R.id.account_ed)
    public EditText mAccountEd;
    @BindView(R.id.password_ed)
    public EditText mPasswordEd;
    @BindView(R.id.login_btn)
    public CardView mLoginBtn;
    @BindView(R.id.layout_progress)
    public View mProgress;
    @BindView(R.id.input_layout)
    public View mInputLayout;
    @BindView(R.id.input_layout_name)
    public LinearLayout mAccountLlyt;
    @BindView(R.id.input_layout_psw)
    public LinearLayout mPwLlyt;
    @BindView(R.id.toolbar_left_btn)
    Button mBackBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);// 允许使用transitions
        setContentView(R.layout.activity_login);

        unbinder = ButterKnife.bind(this);
        initView();
    }

    public void initView(){
        mPresenter.checkUserCache();
    }

    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void showAccountNotNullTip() {
        Toast.makeText(this, "账户不为空", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPasswordNotNullTip() {
        Toast.makeText(this, "密码不为空", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPwErrorToast() {
        Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAccountNotExist() {
        ToastUtils.showToast(this, "该账号不存在");
    }

    @Override
    public void jumpToHomeActivity() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    @OnClick(R.id.login_btn)
    void loginClick(){
        clickLoginBtnAnim();
        mPresenter.checkLogin(mAccountEd.getText().toString(), mPasswordEd.getText().toString());
    }

    @Override
    public void showLoginAnim() {
        // 计算出控件的高与宽
        float mWidth = mLoginBtn.getMeasuredWidth();
        float mHeight = mLoginBtn.getMeasuredHeight();
        // 隐藏输入框
        mAccountLlyt.setVisibility(View.INVISIBLE);
        mPwLlyt.setVisibility(View.INVISIBLE);

        inputAnimator(mInputLayout, mWidth, mHeight);
    }

    /**
     * 输入框的动画效果
     *
     * @param view
     *            控件
     * @param w
     *            宽
     * @param h
     *            高
     */
    private void inputAnimator(final View view, float w, float h) {

        AnimatorSet set = new AnimatorSet();

        ValueAnimator animator = ValueAnimator.ofFloat(0, w/2);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 1f, 0.5f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(mLoginBtn,
                "alpha", 1f, 0.0f);
        set.setDuration(500);
        set.setInterpolator(new LinearInterpolator());

        set.playTogether(animator, animator2, animator3);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /**
                 * 动画结束后，先显示加载的动画，然后再隐藏输入框
                 */
                mProgress.setVisibility(View.VISIBLE);
                progressAnimator(mProgress);
                mInputLayout.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });

    }


    /**
     * 出现进度动画
     *
     * @param view
     */
    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();

    }

    /**
     * 恢复初始状态
     */
    @Override
    public void resetInput() {
        mProgress.setVisibility(View.GONE);
        mInputLayout.setVisibility(View.VISIBLE);
        mAccountLlyt.setVisibility(View.VISIBLE);
        mPwLlyt.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mInputLayout.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        mInputLayout.setLayoutParams(params);

        AnimatorSet set = new AnimatorSet();
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 0.5f,1f );

        ObjectAnimator loginBtnAnimator = ObjectAnimator.ofFloat(mLoginBtn, "alpha", 0.0f,1f );

        set.setDuration(500);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(loginBtnAnimator, animator2);
        set.start();
    }

    public void clickLoginBtnAnim(){
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mLoginBtn,
                "scaleX", 1f, 1.0f, 1f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mLoginBtn,
                "scaleY", 1f, 1.0f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorX, animatorY);
        set.setDuration(300);
        set.start();
    }

    @OnClick(R.id.toolbar_left_btn)
    void clickClose(){
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
