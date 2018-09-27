package com.zjyang.mvpframe.utils;

import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;

import com.example.zjy.player.utils.VideoUtils;
import com.zjyang.base.utils.LogUtil;
import com.zjyang.mvpframe.ui.view.SelectTipSeekBar;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 74215 on 2018/7/31.
 * Bitmap缓存管理类
 */

public class LruCacheManager {

    public static final String TAG = "LruCacheManager";

    private static LruCacheManager mInstance;

    private LruCache<String, Bitmap> mLruCache;

    private Bitmap mFinallyBitmap;


    private LruCacheManager(){
        int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
    }

    public static LruCacheManager getInstance(){
        synchronized (LruCacheManager.class){
            if(mInstance == null){
                mInstance = new LruCacheManager();
            }
            return mInstance;
        }
    }

    /**
     * 将Bitmap添加到缓存
     * @param key
     * @param bitmap
     */
    public void addBitmapToCache(String key, final Bitmap bitmap) {
        if (getBitmapFromCache(key) == null && bitmap != null) {
            LogUtil.d(TAG, "addBitmapToCache");
            mLruCache.put(key, bitmap);
        }
    }

    /**
     * 从缓存中获取Bitmap
     * @param key
     * @return
     */
    public Bitmap getBitmapFromCache(String key) {
        return mLruCache.get(key);
    }

    /**
     * 加载缓存Bitmap
     * @param imageView 要用来展示bitmap的控件
     * @param path 视频路径
     * @param progress 视频进度某一刻
     */
    public void loadBitmap(final ImageView imageView, final String path, final long progress){
        loadBitmap(null, imageView, path, progress);
    }

    public void loadBitmap(final SelectTipSeekBar seekBarTipView, final ImageView imageView, final String path, final long progress){
        final String key = path + progress;
        Bitmap bitmap = getBitmapFromCache(key);
        if(bitmap != null){
            imageView.setImageBitmap(bitmap);
            if(seekBarTipView != null){
                seekBarTipView.setBitmap(bitmap);
            }
            mFinallyBitmap = bitmap;
            return;
        }
        Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap thumbBitmap = VideoUtils.getThumbFromVideo(path, progress);
                LogUtil.d(TAG, "create thumb bitmap---->" + key);
                addBitmapToCache(key, thumbBitmap);
                subscriber.onNext(thumbBitmap);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                LogUtil.d(TAG, "show bitmap---->" + key);
                imageView.setImageBitmap(bitmap);
                if(seekBarTipView != null){
                    seekBarTipView.setBitmap(bitmap);
                }
                mFinallyBitmap = bitmap;
            }
        });

    }

    /**
     * 获取当前生成的最后一张Bitmap
     * @return
     */
    public Bitmap getFinallyBitmap(){
        return mFinallyBitmap;
    }

    /**
     * 释放内存
     */
    public void release(){
        if(mLruCache != null){
            Map<String, Bitmap> map = mLruCache.snapshot();
            if(map != null){
                map.clear();
            }
        }
        if(mFinallyBitmap != null){
            mFinallyBitmap.recycle();
        }
    }

}
