package com.zjyang.mvpframe.utils;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.zjyang.base.utils.LogUtil;
import com.zjyang.mvpframe.module.camera.model.CameraModel;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by 74215 on 2018/6/3.
 */

public class FileUtils {

    private static final String TAG = "FileUtils";

    /**
     * 保存文件
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static String saveBitmapToFile(Bitmap bm, String fileName){
        String filePath = "";
        try{
            String path = CameraModel.LOCAL_VIDEO_THUMB_CACHE_PATH;
            File dirFile = new File(path);
            if(!dirFile.exists()){
                dirFile.mkdir();
            }
            File myCaptureFile = new File(path + "/" + fileName + ".jpg");
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
            filePath = myCaptureFile.getPath();
        }catch (Exception e){
            e.printStackTrace();
        }
        return filePath;
    }

    public static String getFileNameByPath(String path){
        if(TextUtils.isEmpty(path)){
            return null;
        }
        String[] arr = path.split("/");
        if(arr.length == 0){
            return null;
        }
        String[] arr2 = arr[arr.length - 1].split("\\.");
        if(arr2.length == 0){
            return null;
        }
        return arr2[0];
    }

    /**
     * 删除单个文件
     *
     * @param filePath
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                LogUtil.d(TAG, "删除单个文件" + filePath + "成功！");
                return true;
            } else {
                LogUtil.d(TAG, "删除单个文件" + filePath + "失败！");
                return false;
            }
        } else {
            LogUtil.d(TAG, "删除单个文件失败：" + filePath + "不存在！");
            return false;
        }
    }
}
