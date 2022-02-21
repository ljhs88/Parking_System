package com.xiyou3g.select.map;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.maps.AMapException;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.AMapNaviViewOptions;
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

import java.util.ArrayList;
import java.util.List;

public class navigationActivity extends AppCompatActivity implements AMapNaviListener, AMapNaviViewListener {

    private AMapNaviView mAMapNaviView;
    private int strategy = 0;
    private AMapNavi mAMapNavi;
    private double longitude;
    private double latitude;
    private double mLongitude;
    private double mLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Intent intent = getIntent();
        mLatitude = intent.getDoubleExtra("mLatitude", 0);
        mLongitude = intent.getDoubleExtra("mLongitude", 0);
        latitude = intent.getDoubleExtra("latitude", 0);
        longitude = intent.getDoubleExtra("longitude", 0);

        mAMapNaviView = findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        // 控制显示模式
        mAMapNaviView.setShowMode(1);
        // 车头朝向
        mAMapNaviView.setNaviMode(1);

        AMapNaviViewOptions aMapNaviViewOptions = mAMapNaviView.getViewOptions();
        aMapNaviViewOptions.setLayoutVisible(true);
        // 显示模式 设置锁车相关配置
        aMapNaviViewOptions.setAutoLockCar(true);
        aMapNaviViewOptions.setAutoDisplayOverview(true);
        aMapNaviViewOptions.setLockMapDelayed(3000);
        // 导航中动态切换
        aMapNaviViewOptions.setAutoChangeZoom(true);
        // 设置菜单按钮是否在导航界面显示
        aMapNaviViewOptions.setSettingMenuEnabled(true);
        // 设置导航界面是否显示路线全览按钮
        aMapNaviViewOptions.setRouteListButtonShow(true);

        mAMapNaviView.setAMapNaviViewListener(this);
        try {
            mAMapNavi = AMapNavi.getInstance(this);
            mAMapNavi.setUseInnerVoice(true);//使用内置语音播报
            mAMapNavi.addAMapNaviListener(this);
        } catch (AMapException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAMapNaviView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAMapNaviView.onPause();
        // 停止导航之后，会触及底层stop，然后就不会再有回调了，但是讯飞当前还是没有说完的半句话还是会说完
        // mAMapNavi.stopNavi();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAMapNaviView.onDestroy();
        //since 1.6.0 不再在naviview destroy的时候自动执行AMapNavi.stopNavi();请自行执行
        mAMapNavi.stopNavi();
        mAMapNavi.destroy();
    }

    //==============================下面是AMapNaviListener实现的方法=========================//
    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onInitNaviSuccess() {
        // 起点信息
        List<NaviLatLng> startList = new ArrayList<NaviLatLng>();
        startList.add(new NaviLatLng(mLatitude,mLongitude));
        // 终点信息
        List<NaviLatLng> endList = new ArrayList<NaviLatLng>();
        endList.add(new NaviLatLng(latitude, longitude));
        strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
        // 经纬度算路
        mAMapNavi.calculateDriveRoute(startList, endList, null, PathPlanningStrategy.DRIVING_MULTIPLE_ROUTES_DEFAULT);
        Log.d("123", "算路成功");
    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        mAMapNavi.startNavi(NaviType.GPS);// 实时导航
        //mAMapNavi.startNavi(NaviType.EMULATOR);// 模拟导航
        Log.d("123", "开始导航");
    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {
    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onGetNavigationText(int i, String s) {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onEndEmulatorNavi() {

    }

    @Override
    public void onArriveDestination() {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onGpsOpenStatus(boolean b) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {

    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {

    }

    @Override
    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {

    }

    @Override
    public void hideModeCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

    }

    @Override
    public void hideLaneInfo() {

    }

    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onPlayRing(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    @Override
    public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    @Override
    public void onNaviRouteNotify(AMapNaviRouteNotifyData aMapNaviRouteNotifyData) {

    }

    @Override
    public void onGpsSignalWeak(boolean b) {

    }

    //============================下面是AMapNaviViewListener实现的方法==================================//
    @Override
    public void onNaviSetting() {
        finish();
    }

    @Override
    public void onNaviViewShowMode(int i) {
        Log.d("123", "显示模式:" + i);
    }

    @Override
    public void onNaviCancel() {
        finish();
        Log.d("123", "finish");
    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }

    @Override
    public void onNaviMapMode(int i) {
        Log.d("123","车头朝向:" + i);
    }

    @Override
    public void onNaviTurnClick() {

    }

    @Override
    public void onNextRoadClick() {

    }

    @Override
    public void onScanViewButtonClick() {

    }

    @Override
    public void onLockMap(boolean b) {

    }

    @Override
    public void onNaviViewLoaded() {
        Log.d("123", "导航页面加载成功");
    }

    @Override
    public void onMapTypeChanged(int i) {

    }


}