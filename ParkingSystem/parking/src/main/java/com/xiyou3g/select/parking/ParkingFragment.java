package com.xiyou3g.select.parking;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.xiyou3g.select.parking.bean.CreateInformation;
import com.xiyou3g.select.parking.util.RetrofitManager;
import com.xiyou3g.select.parking.util.UpWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

@Route(path = "/parking/ParkingFragment")
public class ParkingFragment extends Fragment implements AMap.OnMapLongClickListener,
        AMap.OnMarkerClickListener,
        View.OnClickListener,
        AMap.OnInfoWindowClickListener,
        AMap.OnMapClickListener,
        AMap.OnMyLocationChangeListener,
        GeocodeSearch.OnGeocodeSearchListener {

    private View mapLayout;
    private MapView mapView;
    private AMap aMap;
    private LatLng thisLatLng;
    private BottomSheetDialog bottomSheetDialog;
    private static int STATUS = 0;
    private static final int CHARGE = 1;
    private static final int PARKING = 2;
    private static final int STALL = 3;
    private RetrofitManager retrofitManager = RetrofitManager.createRetrofitManager("http://101.201.78.192:8888/");
    private UpWindow upWindow;
    private Boolean isShow = false;
    private int change = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mapLayout = inflater.inflate(R.layout.fragment_parking, container, false);

        Log.e("TAG", "onCreateView: ");
        AMapLocationClient.updatePrivacyShow(getContext(), true, true);
        AMapLocationClient.updatePrivacyAgree(getContext(), true);
        AMapLocationClient.setApiKey("8b3f86bb7f0a09c36d1d1a0edf8e87ba");


        mapView = mapLayout.findViewById(R.id.parking_map);

        aMap = mapView.getMap();
        aMap.setOnMapLongClickListener(this);
        aMap.setOnMarkerClickListener(this);
        mapView.onCreate(savedInstanceState);
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.showMyLocation(true);

        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.moveCamera(CameraUpdateFactory.zoomTo(19));
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.showIndoorMap(true);
        aMap.addOnMapLongClickListener(this);
        aMap.addOnMarkerClickListener(this);
        aMap.setOnMapClickListener(this);
        aMap.setOnMyLocationChangeListener(this);
        return mapLayout;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        Log.d("TAG", "onDestroy: " + "fragment");

        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        thisLatLng = latLng;
        showBottomSheetDialog();
    }

    private void showBottomSheetDialog() {
        if (bottomSheetDialog == null) {
            bottomSheetDialog = new BottomSheetDialog(Objects.requireNonNull(getContext()), R.style.BottomSheetDialog);
        }
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.setContentView(R.layout.layout_bottomsheetdialog_marker);

        bottomSheetDialog.show();
        Button create_charge_button = bottomSheetDialog.getWindow().findViewById(R.id.create_charge_button);
        Button create_parking_button = bottomSheetDialog.getWindow().findViewById(R.id.create_parking_button);
        Button create_stall_button = bottomSheetDialog.getWindow().findViewById(R.id.create_stall_button);
        Button cancel_button = bottomSheetDialog.getWindow().findViewById(R.id.cancel_button);
        ImageView arrows = bottomSheetDialog.getWindow().findViewById(R.id.image_arrows);
        arrows.setOnClickListener(this);
        create_parking_button.setOnClickListener(this);
        create_stall_button.setOnClickListener(this);
        cancel_button.setOnClickListener(this);
        create_charge_button.setOnClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.create_charge_button) {
            STATUS = CHARGE;
            start("创建充电桩");
            bottomSheetDialog.cancel();
        } else if (view.getId() == R.id.create_parking_button) {
            STATUS = PARKING;
            start("创建停车场");
            bottomSheetDialog.cancel();
        } else if (view.getId() == R.id.create_stall_button) {
            STATUS = STALL;
            start("创建停车位");
            bottomSheetDialog.cancel();
        } else if (view.getId() == R.id.cancel_button) {
            bottomSheetDialog.cancel();
        } else if (view.getId() == R.id.image_arrows) {
            bottomSheetDialog.cancel();
        }
    }

    private void start(String title) {
        Intent intent = new Intent(getContext(), Cus_ParkingActivity.class);
        intent.putExtra("title", title);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void receiveInformation(CreateInformation createInformation) {
        Log.d("TAG", "receiveInformation: ");
        if (createInformation.getStatus() == CHARGE) {
            aMap.addMarker(new MarkerOptions().title(createInformation.getName()).position(thisLatLng).
                    icon(BitmapDescriptorFactory.fromResource(R.drawable.charge)));
        } else {
            Log.d("TAG", "receiveInformation: ");
            aMap.addMarker(new MarkerOptions().title(createInformation.getName()).position(thisLatLng).
                    icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_image)));
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.d("TAG", "onMapClick: " + isShow);
        if (isShow) {
            upWindow.hint();
            isShow = true;
        } else {
            upWindow.show();
            isShow = false;

        }
    }

    @Override
    public void onMyLocationChange(Location location) {
        if (change == 0) {
            GeocodeSearch geocoderSearch = null;
            try {
                geocoderSearch = new GeocodeSearch(getContext());
                geocoderSearch.setOnGeocodeSearchListener(this);
                RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(location.getLatitude(), location.getLongitude()), 200, GeocodeSearch.AMAP);
                geocoderSearch.getFromLocationAsyn(query);
                Log.d("TAG", "onMyLocationChange: ");
            } catch (AMapException e) {
                e.printStackTrace();
            }
            change++;
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

        RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
        String city = regeocodeAddress.getCity();
        Log.d("TAG", "onRegeocodeSearched: " + city);
        upWindow = new UpWindow(getActivity(), R.layout.search_view, R.style.SearchAnim, R.layout.layout_bottomsheetdialog_recycler, city);
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        Log.d("TAG", "onGeocodeSearched: ");
    }
}