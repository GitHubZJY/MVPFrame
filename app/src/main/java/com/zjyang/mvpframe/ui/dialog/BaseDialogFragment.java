package com.zjyang.mvpframe.ui.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.zjy.player.utils.PlayerUtil;

/**
 * Created by zhengjiayang on 2018/7/16.
 */

public class BaseDialogFragment extends DialogFragment {

    private DialogCallBack mDialogCallBack;
    private OnDialogCancelListener mCancelListener;

    public static BaseDialogFragment create(boolean cancelable){
        return create(null, cancelable, null);
    }

    public static BaseDialogFragment create(DialogCallBack call, boolean cancelable, OnDialogCancelListener cancelListener){
        BaseDialogFragment instance = new BaseDialogFragment();
        instance.setCancelable(cancelable);
        instance.mCancelListener = cancelListener;
        instance.mDialogCallBack = call;
        return instance;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(mDialogCallBack == null){
            return super.onCreateDialog(savedInstanceState);
        }
        return mDialogCallBack.getDialog(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            //在5.0以下的版本会出现白色背景边框，若在5.0以上设置则会造成文字部分的背景也变成透明
            if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                //目前只有这两个dialog会出现边框
                if(dialog instanceof ProgressDialog || dialog instanceof DatePickerDialog) {
                    getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            }
            Window window = getDialog().getWindow();
            window.setGravity(Gravity.CENTER);
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.width = (int) (PlayerUtil.getScreenWidth(getContext()) * 0.7);
            windowParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            windowParams.dimAmount = 0.0f;
            window.setAttributes(windowParams);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mCancelListener != null) {
            mCancelListener.onCancel();
        }
    }

    public void setDialogCallBack(DialogCallBack dialogCallBack) {
        this.mDialogCallBack = dialogCallBack;
    }

    public void setCancelListener(OnDialogCancelListener cancelListener) {
        this.mCancelListener = cancelListener;
    }

    public interface DialogCallBack {
        Dialog getDialog(Context context);
    }

    public interface OnDialogCancelListener {
        void onCancel();
    }
}
