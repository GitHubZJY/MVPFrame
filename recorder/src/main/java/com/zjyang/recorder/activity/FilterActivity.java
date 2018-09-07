package com.zjyang.recorder.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aliyun.common.utils.ToastUtil;
import com.aliyun.qupai.editor.AliyunICompose;
import com.aliyun.qupai.editor.AliyunIEditor;
import com.aliyun.qupai.editor.AliyunIPlayer;
import com.aliyun.qupai.editor.OnPlayCallback;
import com.aliyun.qupai.editor.OnPreparedListener;
import com.aliyun.qupai.editor.impl.AliyunEditorFactory;
import com.aliyun.struct.effect.EffectBean;
import com.aliyun.struct.effect.EffectFilter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.zjyang.recorder.Constant;
import com.zjyang.recorder.R;
import com.zjyang.recorder.adapter.FilterAdapter;
import com.zjyang.recorder.utils.ComposeFactory;
import com.zjyang.recorder.utils.FilterModel;
import com.zjyang.recorder.widget.ComposeDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 74215 on 2018/8/5.
 */

public class FilterActivity extends Activity implements View.OnClickListener{

    private FrameLayout mGlSurfaceContainer;
    private SurfaceView mSurfaceView;
    private RecyclerView mFilterLv;
    private FilterAdapter mFilterAdapter;
    private ImageView mBackIv;
    private ImageView mFinishIv;
    private ComposeDialog mComposeDialog;

    private AliyunIEditor mAliyunIEditor;
    private AliyunIPlayer mAliyunIPlayer;
    private AliyunICompose mCompose;
    private Uri mUri;
    private String mVideoPath;

    private String mConfig;
    private String mOutputPath = Constant.FINALLY_VIDEO_OUTPUT_PATH;
    //后台解析视频滤镜压缩包
    private PasteFilterDataTask mPasteTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.layout_filter);
        Fresco.initialize(this);
        mGlSurfaceContainer = (FrameLayout) findViewById(R.id.glsurface_view);
        mSurfaceView = (SurfaceView) findViewById(R.id.play_view);
        mFilterLv = (RecyclerView) findViewById(R.id.filter_lv);
        mBackIv = (ImageView) findViewById(R.id.filter_back_iv);
        mFinishIv = (ImageView) findViewById(R.id.finish_filter_iv);

        mBackIv.setOnClickListener(this);
        mFinishIv.setOnClickListener(this);
        mVideoPath = getIntent().getStringExtra(Constant.INTENT_VIDEO_PATH);
        mUri = Uri.fromFile(new File(mVideoPath));
        mConfig = mVideoPath;

        initPlayer();
        initGlSurfaceView();
        initFilterListView();


        copyAssets();


        mCompose = ComposeFactory.INSTANCE.getInstance();
        mCompose.init(this);
        mComposeDialog = new ComposeDialog(this);
    }

    private void initGlSurfaceView() {
        if (mAliyunIPlayer != null) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mGlSurfaceContainer.getLayoutParams();
            layoutParams.height = RelativeLayout.LayoutParams.MATCH_PARENT;

            mGlSurfaceContainer.setLayoutParams(layoutParams);
        }
    }

    private void initPlayer(){
        mAliyunIEditor = AliyunEditorFactory.creatAliyunEditor(mUri);
        mAliyunIEditor.init(mSurfaceView);
        mAliyunIPlayer = mAliyunIEditor.createAliyunPlayer();
        if (mAliyunIPlayer == null) {
            ToastUtil.showToast(this, "Create player failed");
            finish();
            return;
        }
        mAliyunIPlayer.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared() {
                mAliyunIPlayer.start();
            }
        });

        mAliyunIPlayer.setOnPlayCallbackListener(new OnPlayCallback() {
            @Override
            public void onPlayStarted() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onSeekDone() {

            }

            @Override
            public void onPlayCompleted() {
                mAliyunIPlayer.start();
            }

            @Override
            public int onTextureIDCallback(int i, int i1, int i2) {
                return 0;
            }
        });
    }

    private void initFilterListView(){
        mFilterAdapter = new FilterAdapter(this);
        mFilterLv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mFilterLv.setAdapter(mFilterAdapter);

        mFilterAdapter.setFilterChangeListener(new FilterAdapter.OnFilterChangeListener() {
            @Override
            public void changeFilter(EffectBean effectBean) {
                if(TextUtils.isEmpty(effectBean.getPath())){
                    mAliyunIEditor.applyFilter(effectBean);
                    return;
                }
                mAliyunIEditor.applyFilter(effectBean);
            }
        });
    }

    private final AliyunICompose.AliyunIComposeCallBack mCallback = new AliyunICompose.AliyunIComposeCallBack() {
        @Override

        public void onComposeError(int errorCode) {
            Log.d("filter", "onComposeError");
            Toast.makeText(FilterActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComposeProgress(final int progress) {
            if(mComposeDialog != null){
                mComposeDialog.setCurProgress(progress);
            }
            Log.d("filter", "onComposeProgress: " + progress);

        }

        @Override
        public void onComposeCompleted() {
                Toast.makeText(FilterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                if(mComposeDialog != null){
                    mComposeDialog.dismiss();
                }
                Log.d("filter", "onComposeCompleted" + mOutputPath);
                Class shareClass = null;
                try {
                    shareClass = Class.forName("com.zjyang.mvpframe.module.share.view.ShareActivity");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(FilterActivity.this,shareClass);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.INTENT_VIDEO_PATH, mOutputPath);
                intent.putExtras(bundle);
                startActivity(intent);

        }
    };

    @Override
    public void onClick(View view) {
        if(view == mBackIv){
            finish();
        }else if(view == mFinishIv){
            //不重新resume一下会导致滤镜合成无效
            mAliyunIPlayer.pause();
            mAliyunIEditor.onPause();
            mAliyunIPlayer.resume();
            mAliyunIEditor.onResume();
            mComposeDialog.show();
            mCompose.compose(mConfig, mOutputPath, mCallback);
        }
    }

    private void copyAssets() {
        mPasteTask = new PasteFilterDataTask();
        mPasteTask.execute();
    }

    public class PasteFilterDataTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            FilterModel.copyAll(FilterActivity.this);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            List<EffectFilter> effectList = new ArrayList<>();
            List<String> filterList = FilterModel.getColorFilterList();
            EffectFilter noFilterEffect = new EffectFilter("");
            noFilterEffect.setDuration(-1);
            noFilterEffect.setStartTime(-1);
            effectList.add(noFilterEffect);
            for(String str : filterList){
                EffectFilter filter = new EffectFilter(str);
                effectList.add(filter);
            }
            mFilterAdapter.setFilterList(effectList);
            mFilterAdapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mAliyunIPlayer.resume();
        mAliyunIEditor.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAliyunIPlayer.pause();
        mAliyunIEditor.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            if(mCompose != null){
                mCompose.release();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(mPasteTask != null){
            mPasteTask.cancel(true);
        }
    }
}
