package com.zjyang.recorder.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.aliyun.common.utils.StorageUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by 74215 on 2018/8/12.
 */

public class FilterModel {

    public static final String TAG = "FilterModel";

    public static String SD_DIR;
    public static String QU_DIR;
    public static String QU_NAME = "tripvideo";
    public final static String QU_COLOR_FILTER = "filter";
    private static Object object = new Object();


    public static List<String> getColorFilterList() {
        List<String> list = new ArrayList<>();
        File file = new File(QU_DIR, QU_COLOR_FILTER);
        if(file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for(File fileTemp : files) {
                if(fileTemp.exists()) {
                    list.add(fileTemp.getAbsolutePath());
                }
            }
        }
        return list;
    }

    public static void copyAll(Context cxt) {
        SD_DIR = StorageUtils.getCacheDirectory(cxt).getAbsolutePath() + File.separator;
        QU_DIR = SD_DIR + QU_NAME + File.separator;
        File dir = new File(QU_DIR);
        Log.d(TAG, "开始拷贝--->");
        copySelf(cxt,QU_NAME);
        Log.d(TAG, "开始创建--->");
        dir.mkdirs();
        Log.d(TAG, "开始解压--->");
        unZip();
        Log.d(TAG, "结束--->");
    }

    public static void copySelf(Context cxt, String root) {
        try {
            String[] files = cxt.getAssets().list(root);
            if(files.length > 0) {
                File subdir = new File(SD_DIR + root);
                if (!subdir.exists()) {
                    subdir.mkdirs();
                }

                for (String fileName : files) {
                    File fileTemp = new File(SD_DIR + root + File.separator + fileName);
                    if (fileTemp.exists()){
                        continue;
                    }
                    copySelf(cxt,root + File.separator + fileName);
                }
            }else{
                //Logger.getDefaultLogger().d("copy...."+Common.SD_DIR+root);
                OutputStream myOutput = new FileOutputStream(SD_DIR+root);
                InputStream myInput = cxt.getAssets().open(root);
                byte[] buffer = new byte[1024 * 8];
                int length = myInput.read(buffer);
                while(length > 0)
                {
                    myOutput.write(buffer, 0, length);
                    length = myInput.read(buffer);
                }

                myOutput.flush();
                myInput.close();
                myOutput.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static int length;
    public static void unZip() {
        File[] files = new File(SD_DIR + QU_NAME).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name != null && name.endsWith(".zip")) {
                    return true;
                }
                return false;
            }
        });
        length = files.length;
        if(length == 0) {
            //mView.setVisibility(View.GONE);
            return;
        }
        for(final File file : files) {
            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    try {
                        int len = file.getAbsolutePath().length();
                        //判断解压后的文件是否存在,截取.zip之前的字符串
                        if (!new File(file.getAbsolutePath().substring(0, len - 4)).exists()) {
                            unZipFolder(file.getAbsolutePath(), SD_DIR + QU_NAME);
                            //insertDB(file.getAbsolutePath().substring(0, len - 4));
                        }
                        synchronized (object) {
                            length--;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Object o) {
                    synchronized (object) {
                        if (length == 0) {
                            //mView.setVisibility(View.GONE);
                        }
                    }
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    public static void unZipFolder(String zipFileString, String outPathString) throws Exception {
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFileString));
        ZipEntry zipEntry;
        String szName = "";
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                // get the folder name of the widget
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(outPathString + File.separator + szName);
                folder.mkdirs();
            } else {

                File file = new File(outPathString + File.separator + szName);
                file.createNewFile();
                FileOutputStream out = new FileOutputStream(file);
                int len;
                byte[] buffer = new byte[1024];
                while ((len = inZip.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
            }
        }
        inZip.close();
    }
}
