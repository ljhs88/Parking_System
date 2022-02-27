package com.xiyou3g.select.parking;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.xiyou3g.select.parking.bean.CreateInformation;
import com.xiyou3g.select.parking.util.RetrofitManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

@Route(path = "/parking/ParkingFragment")
public class ParkingFragment extends Fragment implements AMap.OnMapClickListener, AMap.OnMarkerClickListener, View.OnClickListener {
    private View mapLayout;
    private MapView mapView;
    private AMap aMap;
    private LatLng thisLatLng;
    private LatLng nowLatLng;
    private BottomSheetDialog bottomSheetDialog;
    private static int STATUS = 0;
    private static final int CHARGE = 1;
    private static final int PARKING = 2;
    private static final int STALL = 3;
    private RetrofitManager retrofitManager = RetrofitManager.createRetrofitManager("http://101.201.78.192:8888/");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mapLayout = inflater.inflate(R.layout.fragment_parking, container, false);
        return mapLayout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapView mapView = mapLayout.findViewById(R.id.parking_map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        aMap.setOnMapClickListener(this);
        aMap.setOnMarkerClickListener(this);

        AMapLocationClient.updatePrivacyShow(getContext(), true, true);
        AMapLocationClient.updatePrivacyAgree(getContext(), true);
        AMapLocationClient.setApiKey("8b3f86bb7f0a09c36d1d1a0edf8e87ba");
        EventBus.getDefault().register(getContext());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        EventBus.getDefault().unregister(getContext());
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
    public void onMapClick(LatLng latLng) {
        thisLatLng = latLng;
        showBottomSheetDialog();
    }

    private void showBottomSheetDialog() {
    bottomSheetDialog = new BottomSheetDialog(Objects.requireNonNull(getContext()));
    bottomSheetDialog.setCancelable(true);
    bottomSheetDialog.setContentView(R.layout.layout_bottomsheetdialog_marker);
    bottomSheetDialog.show();
    Button create_parking_button = bottomSheetDialog.getWindow().findViewById(R.id.create_parking_button);
    Button create_stall_button = bottomSheetDialog.getWindow().findViewById(R.id.create_stall_button);
    Button cancel_button = bottomSheetDialog.getWindow().findViewById(R.id.cancel_button);
    create_parking_button.setOnClickListener(this);
    create_stall_button.setOnClickListener(this);
    cancel_button.setOnClickListener(this);

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

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
        }
    }

    private void start(String title) {
        Intent intent = new Intent(getContext(), Cus_ParkingActivity.class);
        intent.putExtra("title", title);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void receiveInformation(CreateInformation createInformation) {
        if (createInformation.getStatus() == CHARGE) {
            aMap.addMarker(new MarkerOptions().title(createInformation.getName()).position(thisLatLng).snippet(createInformation.getBriefIntroduction()).icon(BitmapDescriptorFactory.fromResource(R.drawable.charge)));
        } else {
            aMap.addMarker(new MarkerOptions().title(createInformation.getName()).position(thisLatLng).snippet(createInformation.getBriefIntroduction()).icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_image)));
        }
    }
}