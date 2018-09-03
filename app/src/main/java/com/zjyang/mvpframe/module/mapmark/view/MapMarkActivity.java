package com.zjyang.mvpframe.module.mapmark.view;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.zjyang.mvpframe.R;
import com.zjyang.mvpframe.module.base.BaseActivity;
import com.zjyang.mvpframe.module.base.BasePresenter;
import com.zjyang.mvpframe.module.mapmark.MapMarkTasksContract;
import com.zjyang.mvpframe.module.mapmark.model.bean.MapMark;
import com.zjyang.mvpframe.module.mapmark.presenter.MapMarkPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhengjiayang on 2018/9/3.
 */

public class MapMarkActivity extends BaseActivity<MapMarkPresenter> implements MapMarkTasksContract.View{

    private Unbinder unbinder;
    @BindView(R.id.map_view)
    public MapView mMapView;

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

        mPresenter.fillMarkData();
    }

    @Override
    public void setMarkDataInMap(List<MapMark> mapMarks) {
        for (MapMark mapMark : mapMarks){
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(new LatLng(34.341568, 108.940174));
            markerOption.title("2017.04.22").snippet("西安市");

            markerOption.draggable(true);//设置Marker可拖动
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(),R.drawable.ic_position)));
            aMap.addMarker(markerOption);
        }
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
