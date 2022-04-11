package com.xiyou3G.parkingsystem.fragment;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.navi.NaviSetting;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.bumptech.glide.Glide;
import com.xiyou3G.parkingsystem.R;
import com.xiyou3G.parkingsystem.ShowSharedActivity;
import com.xiyou3g.select.parking.adapter.RecyclerAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements Inputtips.InputtipsListener, AMapLocationListener {

    private View view;

    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private RecyclerView chargeRecycler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 在构造MapView之前必须进行合规检查，设置接口之前保证隐私政策合规
        MapsInitializer.updatePrivacyShow(getContext(),true,true);
        MapsInitializer.updatePrivacyAgree(getContext(),true);
        // 导航的合规接口
        NaviSetting.updatePrivacyShow(getContext(), true, true);
        NaviSetting.updatePrivacyAgree(getContext(), true);

        view = inflater.inflate(R.layout.layout_home_fragment, container, false);
        initLocation();
        initView();
        return view;
    }

    private void initLocation() {

        try {
            mlocationClient = new AMapLocationClient(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    private void initView() {
        ConstraintLayout constraintLayoutStall = view.findViewById(R.id.shared_stall);
        constraintLayoutStall.setOnClickListener(View -> {
            Intent intent = new Intent(getContext(), ShowSharedActivity.class);
            intent.putExtra("status", "Stall");
            startActivity(intent);
        });
        ConstraintLayout constraintLayoutCharge = view.findViewById(R.id.shared_charge);
        constraintLayoutCharge.setOnClickListener(View -> {
            Intent intent = new Intent(getContext(), ShowSharedActivity.class);
            intent.putExtra("status", "Charge");
            startActivity(intent);
        });
        Banner myBanner = view.findViewById(R.id.home_banner);
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.banner1);
        list.add(R.drawable.banner2);
        myBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        myBanner.setBannerAnimation(Transformer.Default);

        myBanner.setImages(list);
        myBanner.setIndicatorGravity(BannerConfig.CENTER);
        myBanner.isAutoPlay(true);
        myBanner.setDelayTime(3000);
        myBanner.start();
    }


    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        list.remove(0);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(list);
        Log.d("TAG", "onGetInputtips: " + list);
        chargeRecycler.setAdapter(recyclerAdapter);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        chargeRecycler = view.findViewById(R.id.home_charge);
        chargeRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.d("TAG", "onLocationChanged: " + chargeRecycler);
        InputtipsQuery inputtipsQuery = new InputtipsQuery("充电桩", aMapLocation.getCity());
        mlocationClient.stopLocation();
        Inputtips inputtips = new Inputtips(getContext(), inputtipsQuery);
        inputtips.setInputtipsListener(this);
        inputtips.requestInputtipsAsyn();
    }
}
