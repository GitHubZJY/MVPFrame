package com.zjyang.mvpframe.net;

import com.zjyang.base.utils.HandlerUtils;
import com.zjyang.base.utils.LogUtil;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zhengjiayang on 2018/3/2.
 */

public class HttpRequestManager {

    private static final String TAG = "HttpRequestManager";

    /**
     * 异步的Post请求
     *
     * @param url url
     */
    public static void postAsyn(String url, Map<String, String> params, final IRequestCallBack callBack) {
        // 创建OKHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        /**
         * 在这对添加的参数进行遍历
         */
        for (Map.Entry<String, String> map : params.entrySet()) {
            String key = map.getKey();
            String value;
            /**
             * 判断值是否是空的
             */
            if (map.getValue() == null) {
                value = "";
            } else {
                value = map.getValue();
            }
            /**
             * 把key和value添加到formBody中
             */
            builder.add(key, value);
        }
        RequestBody requestBody = builder.build();


        // 创建一个Request
        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        // 请求加入调度
        call.enqueue(new Callback() {
            // 请求失败的回调
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                LogUtil.e(TAG, "onFailure");
                HandlerUtils.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFail();
                    }
                });
            }

            // 请求成功的回调
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 将response转化成String
                final String responseStr = response.body().string();
                LogUtil.e(TAG, "onResponse: " + responseStr);
                HandlerUtils.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(responseStr);
                    }
                });


            }
        });
    }

}
