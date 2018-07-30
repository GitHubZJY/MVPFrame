package com.zjyang.mvpframe.utils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.zjyang.mvpframe.application.AppApplication;

/**
 * Created by zhengjiayang on 2018/7/30.
 */

public class LocationUtils {

    public static final String TAG = "LocationUtils";

    AMapLocationClient mLocationClient;

    private static LocationUtils mInstance = null;
    private LocationUtils(){
        //初始化定位
        mLocationClient = new AMapLocationClient(AppApplication.getContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
    }
    public static LocationUtils getInstance(){
        if(mInstance == null){
            mInstance = new LocationUtils();
        }
        return mInstance;
    }

    /**
     * 启动定位
     */
    public void startLocation(LocationCallback locationCallback){
        this.mLocationCallback = locationCallback;
        mLocationClient.startLocation();
    }

    /**
     * 停止定位
     */
    public void stopLocation(){
        mLocationCallback = null;
        mLocationClient.stopLocation();
    }

    /**
     * 定位回调监听器
     */
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            // 从这里开始就会持续回调
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    double currentLat = amapLocation.getLatitude();//获取纬度
                    double currentLon = amapLocation.getLongitude();//获取经度
                    String address = amapLocation.getAddress();
                    amapLocation.getAccuracy();//获取精度信息
                    if(mLocationCallback != null){
                        mLocationCallback.getAddress(address);
                    }
                    LogUtil.i(TAG, "currentLat : " + currentLat + " currentLon : " + currentLon + " address: " + address);
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    LogUtil.e(TAG, "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };

    LocationCallback mLocationCallback;

    public void setLocationCallback(LocationCallback mLocationCallback){
        this.mLocationCallback = mLocationCallback;
    }

    public interface LocationCallback{
        void getAddress(String address);
    }
}
