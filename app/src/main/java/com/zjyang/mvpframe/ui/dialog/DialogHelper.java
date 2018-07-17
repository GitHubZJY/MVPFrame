package com.zjyang.mvpframe.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.ui.ShapeUtils;
import com.zjyang.mvpframe.utils.DrawUtils;
import com.zjyang.mvpframe.utils.PermissionUtils;

/**
 * Created by zhengjiayang on 2018/7/16.
 */

public class DialogHelper {

    private static final String PERMISSION_DIALOG = "permission_dialog";


    public static void showPermissionDialog(FragmentManager manager, final String msg){
        final BaseDialogFragment dialogFragment = BaseDialogFragment.create(true, 0.7);
        dialogFragment.setDialogCallBack( new BaseDialogFragment.DialogCallBack() {
            @Override
            public Dialog getDialog(Context context) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Base_AlertDialog);
                builder.setView(getPermissionDialogView(context, msg, dialogFragment));
                return builder.create();
            }
        });
        dialogFragment.setCancelListener(new BaseDialogFragment.OnDialogCancelListener() {
            @Override
            public void onCancel() {

            }
        });
        dialogFragment.show(manager, PERMISSION_DIALOG);
    }

    private static View getPermissionDialogView(Context context, String msg, final DialogFragment dialogFragment){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_permission, null);
        TextView msgTv = view.findViewById(R.id.dialog_detail);
        TextView cancelTv = view.findViewById(R.id.cancel_tv);
        TextView agreeTv = view.findViewById(R.id.agree_tv);
        LinearLayout mBgView = view.findViewById(R.id.dialog_bg);
        msgTv.setText(msg);
        mBgView.setBackground(ShapeUtils.getRoundRectDrawable(DrawUtils.dp2px(4), Color.parseColor("#ffffff")));
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dialogFragment != null){
                    dialogFragment.dismiss();
                }
            }
        });

        agreeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dialogFragment != null){
                    dialogFragment.dismiss();
                }
                PermissionUtils.newInstance().jumpPermissionPage();
            }
        });
        return view;
    }

}
