package com.xiyou3g.select.parking;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.xiyou3g.select.parking.UI.ShowChargeUI;
import com.xiyou3g.select.parking.UI.ShowStallUI;
import com.xiyou3g.select.parking.UI.ShowUI;
import com.xiyou3g.select.parking.api.ChargeAndStallService;
import com.xiyou3g.select.parking.bean.ShowInformation;
import com.xiyou3g.select.parking.bean.chargebean;
import com.xiyou3g.select.parking.bean.orderbean;
import com.xiyou3g.select.parking.bean.stallbean;
import com.xiyou3g.select.parking.util.RetrofitManager;
import com.xiyou3g.select.parking.util.ToastUtil;
import com.xiyou3g.select.parking.util.toolBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShowInformationActivity extends AppCompatActivity implements View.OnClickListener {

    private static int STATUS = 2;
    private static final int CHARGE = 1;
    private static final int STALL = 2;
    private ShowInformation showInformation;
    private BottomSheetDialog bottomSheetDialog;
    private LatLng thisLatLng;
    private RetrofitManager retrofitManager = RetrofitManager.createRetrofitManager("http://101.201.78.192:8888/");
    private chargebean chargebean = null;
    private stallbean stallbean = null;

    private String userId;
    private String token;
    private String mobile;
    private String posId;
    private Button navigation_button;
    private int NAVIGATION_BUTTON_STATUS = 0;
    private final int NAVIGATION_CREATE = 0;
    private final int NAVIGATION_CANCEL = 0;

    private TextView StallAndChargeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_information);
        EventBus.getDefault().register(this);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            decorView.setSystemUiVisibility(systemUiVisibility | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        // 设置充电桩和停车位
        StallAndChargeText = findViewById(R.id.text7);

        SharedPreferences pref = getSharedPreferences("data", Context.MODE_PRIVATE);
        userId = pref.getString("userId", "");
        token = pref.getString("userToken", "");
        mobile = pref.getString("mobile", "");

        //thisLatLng = new LatLng(34.15512142931449, 108.90559129372537);
        //userId = "946762136657330176";

        buttonClick();
        getStatus();
        Log.d("123", String.valueOf(thisLatLng.latitude)+""+String.valueOf(thisLatLng.longitude));
        show();
    }

    @SuppressLint("ResourceAsColor")
    private void setToolBar(String name) {
        Toolbar toolbar = findViewById(R.id.show_toolbar);
        toolbar.setTitle(name);
        toolbar.setTitleTextColor(R.color.black);
        toolbar.setTitleMarginStart(toolBar.getAndroidScreenProperty(this));
        setSupportActionBar(toolbar);
    }

    /**
     * 获取充电桩信息
     * @param activity
     */
    private void getCharge(Activity activity) {
        Log.d("123", "getCharge");
        Retrofit retrofit = retrofitManager.getRetrofit();
        ChargeAndStallService api = retrofit.create(ChargeAndStallService.class);
        Call<chargebean> call = api.postCharge(String.valueOf(thisLatLng.longitude),
                String.valueOf(thisLatLng.latitude));
        call.enqueue(new Callback<com.xiyou3g.select.parking.bean.chargebean>() {
            @Override
            public void onResponse(Call<chargebean> call, Response<chargebean> response) {
                StallAndChargeText.setText("充电桩信息");
                chargebean = response.body();
                Log.d("123", "response");
                if (chargebean != null && chargebean.getSuccess()) {
                    Log.d("123", response.body().toString());
                    setToolBar(chargebean.getData().get(0).getOwnerNum());
                    showInformation(new ShowChargeUI(activity, chargebean));
                }
            }

            @Override
            public void onFailure(Call<chargebean> call, Throwable t) {
                ToastUtil.getToast(ShowInformationActivity.this, "获取充电桩信息失败！请重新尝试！");
            }
        });
    }

    /**
     * 获取停车场信息
     * @param activity
     */
    private void getStall(Activity activity) {
        Log.d("123", "getStall");
        Retrofit retrofit = retrofitManager.getRetrofit();
        ChargeAndStallService api = retrofit.create(ChargeAndStallService.class);
        Call<stallbean> call = api.postStall(String.valueOf(thisLatLng.longitude),
                String.valueOf(thisLatLng.latitude));
        call.enqueue(new Callback<stallbean>() {
            @Override
            public void onResponse(Call<stallbean> call, Response<stallbean> response) {
                StallAndChargeText.setText("停车位信息");
                stallbean = response.body();
                if (stallbean != null && stallbean.isSuccess()) {
                    Log.d("123", stallbean.toString());
                    setToolBar(stallbean.getData().get(0).getOwnerNum());
                    showInformation(new ShowStallUI(activity, stallbean));
                } else {
                    ToastUtil.getToast(ShowInformationActivity.this, "获取停车位信息失败！请重新尝试！");
                }
            }

            @Override
            public void onFailure(Call<stallbean> call, Throwable t) {
                Log.d("123", t.toString());
                ToastUtil.getToast(ShowInformationActivity.this, "获取停车位信息失败！请重新尝试！");
            }
        });
    }

    private void show() {
        Log.d("123", String.valueOf(STATUS));
        if (STATUS == CHARGE) {
            Log.d("123", "show");
            getCharge(this);
        } else if (STATUS == STALL) {
            getStall(this);
        }
    }

    private void buttonClick() {
        navigation_button = findViewById(R.id.navigation_button);
        navigation_button.setOnClickListener(this);
    }

    private void getStatus() {
        Intent intent = getIntent();
        STATUS = intent.getIntExtra("STATUS", 0);
        thisLatLng = new LatLng(intent.getDoubleExtra("latitude",0),
                intent.getDoubleExtra("longitude", 0));
    }

    private void showInformation (ShowUI showUI) {
        showUI.showImage();
        showUI.showText();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void receiveShowInformation(ShowInformation showInformation) {
        this.showInformation = showInformation;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.navigation_button) {
            // 创建订单
            createRes();
            String name;
            double latitude;
            double longitude;
            if (stallbean != null) {
                name = stallbean.getData().get(0).getOwnerNum();
                latitude = Double.parseDouble(stallbean.getData().get(0).getLatitude());
                longitude = Double.parseDouble(stallbean.getData().get(0).getLongitude());
            } else {
                name = chargebean.getData().get(0).getOwnerNum();
                latitude = Double.parseDouble(chargebean.getData().get(0).getLatitude());
                longitude = Double.parseDouble(chargebean.getData().get(0).getLongitude());
            }
            ARouter.getInstance().build("/map/MapActivity")
                    .withDouble("latitude", latitude)
                    .withDouble("longitude", longitude)
                    .withString("destination", name)
                    .navigation();
        }/* else if (view.getId() == R.id.delete_button) {
            bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(R.layout.layout_bottomsheetdialog_delete);
            Button sure_button = bottomSheetDialog.getWindow().findViewById(R.id.sure_button);
            Button cancel_sure_button = bottomSheetDialog.getWindow().findViewById(R.id.cancel_sure_button);
            sure_button.setOnClickListener(this);
            cancel_sure_button.setOnClickListener(this);
            bottomSheetDialog.show();
        }*/ else if (view.getId() == R.id.sure_button) {
            bottomSheetDialog.cancel();
        } else if (view.getId() == R.id.cancel_sure_button) {
            bottomSheetDialog.cancel();
        }
    }

    /**
     * 创建订单
     */
    private void createRes() {
        Retrofit retrofit = retrofitManager.getRetrofit();
        ChargeAndStallService api = retrofit.create(ChargeAndStallService.class);
        if (STATUS == CHARGE) {
            posId = chargebean.getData().get(0).getId();
        } else if (STATUS == STALL) {
            posId = stallbean.getData().get(0).getId();
        }
        Log.d("TAG123", posId+","+userId);
        api.postCreate(posId, userId).enqueue(new Callback<orderbean>() {
            @Override
            public void onResponse(Call<orderbean> call, Response<orderbean> response) {
                orderbean bean = response.body();
                Log.d("123", response.toString());
                Log.d("TAG123", "onResponse: " + bean);
                if (bean != null && bean.isSuccess()) {
                    Log.d("123", bean.toString());
                    /**
                     * 导航按钮逻辑
                     */
                } else {
                    ToastUtil.getToast(ShowInformationActivity.this, "导航失败!请重新尝试!");
                }
            }

            @Override
            public void onFailure(Call<orderbean> call, Throwable t) {
                ToastUtil.getToast(ShowInformationActivity.this, "导航失败!请重新尝试!");
            }
        });
    }

}