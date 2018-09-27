package com.zjyang.mvpframe.module.mapmark.view;

import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.Projection;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.zjyang.base.base.BaseActivity;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.mapmark.MapMarkTasksContract;
import com.zjyang.mvpframe.module.mapmark.model.bean.MapMark;
import com.zjyang.mvpframe.module.mapmark.presenter.MapMarkPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zhengjiayang on 2018/9/3.
 */

public class MapMarkActivity extends BaseActivity<MapMarkPresenter> implements MapMarkTasksContract.View{

    private Unbinder unbinder;
    @BindView(R.id.map_view)
    public MapView mMapView;
    @BindView(R.id.toolbar_left_btn)
    Button mBackBtn;

    private AMap aMap;

    @Override
    public MapMarkPresenter createPresenter() {
        return new MapMarkPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_mark);
        unbinder = ButterKnife.bind(this);
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();
        aMap.moveCamera(CameraUpdateFactory.zoomTo(4));
        mPresenter.fillMarkData();
    }

    @OnClick(R.id.toolbar_left_btn)
    void clickBack(){
        finish();
    }

    @Override
    public void setMarkDataInMap(List<MapMark> mapMarks) {
        for (MapMark mapMark : mapMarks){
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(new LatLng(Double.parseDouble(mapMark.getLatitude()), Double.parseDouble(mapMark.getLongitude())));
            markerOption.title(mapMark.getMarkTime()).snippet(mapMark.getCity());

            markerOption.draggable(true);//设置Marker可拖动
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(),R.drawable.ic_mark)));
            Marker marker = aMap.addMarker(markerOption);
            dropInto(marker);
        }
    }

    //掉下来还回弹一次
    private void dropInto(final Marker marker) {

        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final LatLng markerLatlng = marker.getPosition();
        Projection proj = aMap.getProjection();
        Point markerPoint = proj.toScreenLocation(markerLatlng);
        Point startPoint = new Point(markerPoint.x, 0);// 从marker的屏幕上方下落
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;// 动画总时长
        final Interpolator interpolator = new AccelerateInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                if(t > 1.0){
                    t = 1;
                }
                double lng = t * markerLatlng.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * markerLatlng.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        unbinder.unbind();
    }
}
