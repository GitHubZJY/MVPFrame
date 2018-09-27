package com.zjyang.base;

import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.zjyang.base.base.SkinManager;

/**
 * Created by zhengjiayang on 2018/9/26.
 */

public class BaseApp {

    public static Context sContext;

    public static void init(Context context){
        sContext =context;
        SkinManager.getInstance().init();
        Fresco.initialize(context);
    }
}
