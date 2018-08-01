package com.zjyang.mvpframe.utils;

import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;

import com.example.zjy.player.utils.VideoUtils;

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

    public void addBitmapToCache(String key, final Bitmap bitmap) {
        if (getBitmapFromCache(key) == null && bitmap != null) {
            LogUtil.d(TAG, "addBitmapToCache");
            mLruCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromCache(String key) {
        return mLruCache.get(key);
    }

    public void loadBitmap(final ImageView imageView, final String path, final long progress){
        final String key = path + progress;
        Bitmap bitmap = getBitmapFromCache(key);
        if(bitmap != null){
            imageView.setImageBitmap(bitmap);
            mFinallyBitmap = bitmap;
            return;
        }
        Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap thumbBitmap = VideoUtils.getThumbFromVideo(path, progress);
                LogUtil.d(TAG, "生成bitmap---->" + key);
                addBitmapToCache(key, thumbBitmap);
                subscriber.onNext(thumbBitmap);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                LogUtil.d(TAG, "显示bitmap");
                imageView.setImageBitmap(bitmap);
                mFinallyBitmap = bitmap;
            }
        });

    }

    public Bitmap getFinallyBitmap(){
        return mFinallyBitmap;
    }

    public void release(){
        if(mLruCache != null){
            mLruCache = null;
        }
    }

}
