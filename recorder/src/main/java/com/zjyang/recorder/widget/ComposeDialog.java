package com.zjyang.recorder.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.widget.ProgressBar;

import com.zjyang.recorder.R;

/**
 * Created by 74215 on 2018/8/20.
 */

public class ComposeDialog extends AppCompatDialog{

    private CircleProgressBar mProgressBar;


    public ComposeDialog(Context context) {
        this(context, 0);
    }

    public ComposeDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_compose_dialog);

        mProgressBar = (CircleProgressBar) findViewById(R.id.progress_bar);
        if(mProgressBar != null){
            mProgressBar.setProgress(0);
        }

    }

    public void setCurProgress(int progress){
        mProgressBar.setProgress(progress);
    }
}
