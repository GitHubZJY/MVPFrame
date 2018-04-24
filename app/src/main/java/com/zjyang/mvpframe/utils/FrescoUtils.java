package com.zjyang.mvpframe.utils;

import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zjyang.mvpframe.application.AppApplication;

/**
 * Created by 74215 on 2018/4/10.
 */

public class FrescoUtils {

    public static void showImgByUrl(String url, SimpleDraweeView simpleDraweeView){
        simpleDraweeView.setImageURI(Uri.parse(url));
//        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(AppApplication.getContext().getResources());
//        /**
//         * 设置淡入淡出效果的显示时间
//         * */
//        GenericDraweeHierarchy hierarchy = builder.setFadeDuration(200).build();
//        DraweeController placeHolderDraweeController = Fresco.newDraweeControllerBuilder()
//                .setUri(Uri.parse(url)) //为图片设置url
//                .build();
//        simpleDraweeView.setController(placeHolderDraweeController);
//        simpleDraweeView.setHierarchy(hierarchy);
    }
}
