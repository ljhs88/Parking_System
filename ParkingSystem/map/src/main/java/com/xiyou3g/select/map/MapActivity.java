package com.xiyou3g.select.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapPageType;
import com.amap.api.navi.NaviSetting;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/map/MapActivity")
public class MapActivity extends AppCompatActivity {
    // 定位纬度
    private double mLatitude;
    // 定位经度
    private double mLongitude;
    // 目的地纬度
    @Autowired(name = "latitude")
    double latitude;
    // 目的地经度
    @Autowired(name = "longitude")
    double longitude;
    // 停车场名
    @Autowired(name = "destination")
    String destination;

    @Override
    protected void onRestart() {
        super.onRestart();
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // 导航的合规接口
        NaviSetting.updatePrivacyShow(this, true, true);
        NaviSetting.updatePrivacyAgree(this, true);
        //设置状态栏透明
        makeStatusBarTransparent(this);
        //状态栏文字自适应
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        // 设置碎片
        //replaceFragment(new mapFragment());
        ARouter.getInstance().inject(this);
        // 直接导航
        GoToNavigation();
    }

    private void GoToNavigation() {
        // 开始导航
        // 起点 定位点
        Poi start = new Poi("我的位置", new LatLng(mLatitude, mLongitude), null);
        // 途经点
        List<Poi> poiList = new ArrayList();
        //poiList.add(new Poi("故宫", new LatLng(39.918058,116.397026), "B000A8UIN8"));
        // 终点
        Poi end = new Poi(destination, new LatLng(latitude, longitude), null);
        // 组件参数配置
        AmapNaviParams params = new AmapNaviParams(start, poiList, end, AmapNaviType.DRIVER, AmapPageType.ROUTE);
        // 启动组件
        AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), params, null);
    }

    /*private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();//获取FragmentManager
        FragmentTransaction transaction = manager.beginTransaction();//调用beginTransaction()开启一个事务
        transaction.replace(R.id.fragment, fragment);//使用replace()方法向容器中添加碎片
        transaction.commit();//提交事务
    }*/

    public static void makeStatusBarTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int option = window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}