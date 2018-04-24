package com.zjyang.mvpframe.module.idcard.model;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.zjyang.mvpframe.module.idcard.ISearchIdCardCallBack;
import com.zjyang.mvpframe.module.idcard.IdCardTasksContract;
import com.zjyang.mvpframe.module.idcard.model.bean.IdCardInfo;
import com.zjyang.mvpframe.net.HttpRequestManager;
import com.zjyang.mvpframe.net.HttpResultInfo;
import com.zjyang.mvpframe.net.IRequestCallBack;
import com.zjyang.mvpframe.utils.Constants;
import com.zjyang.mvpframe.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengjiayang on 2018/3/2.
 */

public class IdCardModel implements IdCardTasksContract.Model{

    private final static String SEARCH_ID_CARD_URL = "http://apis.juhe.cn/idcard/index";


    @Override
    public void getInfoByIdCard(String idcard, final ISearchIdCardCallBack callBack) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("cardno", idcard);
        paramsMap.put("dtype", "json");
        paramsMap.put("key", Constants.JU_HE_APP_KEY);
        HttpRequestManager.postAsyn(SEARCH_ID_CARD_URL, paramsMap, new IRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                if(!TextUtils.isEmpty(result)){
                    IdCardInfo idCardInfo = parseResult(result);
                    callBack.onSearchSuccess(idCardInfo);
                }else{
                    callBack.onSearchFail();
                }
            }

            @Override
            public void onFail() {
                callBack.onSearchFail();
            }
        });
    }

    private IdCardInfo parseResult(String result){
        IdCardInfo idCardInfo = new IdCardInfo();
        try {
            HttpResultInfo httpResultInfo = JSON.parseObject(result, HttpResultInfo.class);
            idCardInfo = JSON.parseObject(httpResultInfo.getResult(), IdCardInfo.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return idCardInfo;
    }
}
