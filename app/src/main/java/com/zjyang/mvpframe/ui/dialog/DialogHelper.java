package com.zjyang.mvpframe.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.utils.PermissionUtils;

/**
 * Created by zhengjiayang on 2018/7/16.
 */

public class DialogHelper {


    public static void showPermissionDialog(FragmentManager manager, final String title, final String msg){
        final BaseDialogFragment dialogFragment = BaseDialogFragment.create(new BaseDialogFragment.DialogCallBack() {
            @Override
            public Dialog getDialog(Context context) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Base_AlertDialog);
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_permission, null);
                TextView msgTv = view.findViewById(R.id.dialog_detail);
                TextView cancelTv = view.findViewById(R.id.cancel_tv);
                TextView agreeTv = view.findViewById(R.id.agree_tv);
                msgTv.setText(msg);
                builder.setView(view);
                cancelTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                agreeTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PermissionUtils.newInstance().jumpPermissionPage();
                    }
                });
                return builder.create();
            }
        }, true, null);
        dialogFragment.show(manager, "s");
    }

}
