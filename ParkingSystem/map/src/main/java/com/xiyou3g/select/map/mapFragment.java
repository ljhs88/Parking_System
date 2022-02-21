package com.xiyou3g.select.map;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapException;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapPageType;
import com.amap.api.navi.MyNaviListener;
import com.amap.api.navi.NaviSetting;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.enums.PathPlanningStrategy;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviRouteNotifyData;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.xiyou3g.select.map.navigationActivity;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class mapFragment extends Fragment implements AMap.OnMyLocationChangeListener, View.OnClickListener {

    private View view;
    private Button navigationButton1;
    private Button navigationButton2;
    private MapView mMapView;
    private AMap aMap = null;
    private AMapNavi mAMapNavi;
    private double mLongitude;
    private double mLatitude;
    private double longitude;
    private double latitude;
    private List<String> positionList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("123", "onCreate");
        // 在构造MapView之前必须进行合规检查，设置接口之前保证隐私政策合规
        MapsInitializer.updatePrivacyShow(getContext(),true,true);
        MapsInitializer.updatePrivacyAgree(getContext(),true);
        // 导航的合规接口
        NaviSetting.updatePrivacyShow(getContext(), true, true);
        NaviSetting.updatePrivacyAgree(getContext(), true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.map_fragment, container,false);
        Log.d("123", "onCreateView");

        showMap(savedInstanceState);

        showBluePoint();

        getPosition();
        for (int i = 0; i < 2; i++) {
            String[] token = positionList.get(i).split(",");
            String latitude = token[0];
            String longitude = token[1];
            showMarker(Double.valueOf(latitude),Double.valueOf(longitude));
        }
        aMap.setOnMarkerClickListener(markerClickListener);

        navigationButton1 = view.findViewById(R.id.navigation_button1);
        navigationButton2 = view.findViewById(R.id.navigation_button2);
        navigationButton1.setOnClickListener(this);
        navigationButton2.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navigation_button1:
                Intent intent = new Intent(getContext(), navigationActivity.class);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("mLatitude", mLatitude);
                intent.putExtra("mLongitude", mLongitude);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.navigation_button2:
                // 开始导航
                //起点 定位点
                Log.d("123", "起点：" + mLatitude + "," + mLongitude);
                Log.d("123", "终点：" + latitude + "," + longitude);
                Poi start = new Poi("我的位置", new LatLng(mLatitude,mLongitude), null);
                //途经点
                List<Poi> poiList = new ArrayList();
                //poiList.add(new Poi("故宫", new LatLng(39.918058,116.397026), "B000A8UIN8"));
                //终点
                Poi end = new Poi("停车点0号", new LatLng(latitude,longitude), null);
                // 组件参数配置
                AmapNaviParams params = new AmapNaviParams(start, poiList, end, AmapNaviType.DRIVER, AmapPageType.ROUTE);
                // 启动组件
                AmapNaviPage.getInstance().showRouteActivity(getActivity().getApplicationContext(), params, null);
                break;
        }
    }

    // 定义 Marker 点击事件监听
    AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
        // marker 对象被点击时回调的接口
        // 返回 true 则表示接口已响应事件，否则返回false
        @Override
        public boolean onMarkerClick(Marker marker) {
            LatLng latLng = marker.getPosition();
            longitude = latLng.longitude;
            latitude = latLng.latitude;
            Log.d("123","onClick" + longitude+","+latitude);
            return false;
        }
    };

    /**
     * 获取自定义点信息
     */
    private void getPosition() {
        positionList = new ArrayList<>();
        positionList.add("34.480612851789985,109.5239639346525");
        positionList.add("34.580612851789985,109.5231111111111");
    }


    /**
     * 显示地图
     * @param savedInstanceState
     */
    public void showMap(Bundle savedInstanceState) {
        // 获取地图控件引用
        mMapView = view.findViewById(R.id.map);
        // 在activity执行onCreate时执行mMapView.onCreate(saveInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        // 初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
    }

    /**
     * 显示定位蓝点
     */
    public void showBluePoint() {
        // 设置定位蓝点
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();// 初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        // 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(4000); // 设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.showMyLocation(true);// //设置是否显示定位小蓝点，用于满足只想使用定位，不想使用定位小蓝点的场景，设置false以后图面上不再有定位蓝点的概念，但是会持续回调位置信息。
        aMap.setMyLocationStyle(myLocationStyle);// 设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));// 设置显示级别
        aMap.setOnMyLocationChangeListener(this);
    }

    /**
     * 在地图上绘制自定义点
     */
    private void showMarker(double latitude,double longitude) {
        LatLng latLng = new LatLng(latitude,longitude);
        final Marker marker = aMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("停车位0号")
                .snippet("home")
                .draggable(false));
    }

    /**
     * 获取自己位置信息
     * @param location
     */
    @Override
    public void onMyLocationChange(Location location) {
        // 获取经度
        mLongitude = location.getLongitude();
        // 获取纬度
        mLatitude = location.getLatitude();
        //Log.d("123", "经纬度" + mLongitude + "," + mLatitude);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

}
