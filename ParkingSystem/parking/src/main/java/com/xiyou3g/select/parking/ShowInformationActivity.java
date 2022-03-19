package com.xiyou3g.select.parking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.xiyou3g.select.parking.UI.ShowChargeUI;
import com.xiyou3g.select.parking.UI.ShowStallUI;
import com.xiyou3g.select.parking.UI.ShowUI;
import com.xiyou3g.select.parking.api.ChargeAndStallService;
import com.xiyou3g.select.parking.bean.ShowInformation;
import com.xiyou3g.select.parking.bean.chargebean;
import com.xiyou3g.select.parking.bean.stallbean;
import com.xiyou3g.select.parking.util.RetrofitManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShowInformationActivity extends AppCompatActivity implements View.OnClickListener {

    private static int STATUS = 3;
    private static final int CHARGE = 1;
    private static final int PARKING = 2;
    private static final int STALL = 3;
    private String scId;
    private String spId;
    private ShowInformation showInformation;
    private BottomSheetDialog bottomSheetDialog;
    private LatLng thisLatLng;
    private RetrofitManager retrofitManager = RetrofitManager.createRetrofitManager("http://101.201.78.192:8888/");
    private chargebean chargebean = null;
    private stallbean stallbean = null;

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

        //getStatus();
        show();
        /**
         * 获取这两个id
         */
        scId = "946762136657330176";
        spId = "946762136657330176";
    }

    /**
     * 获取充电桩信息
     * @param activity
     */
    private void getCharge(Activity activity) {
        Retrofit retrofit = retrofitManager.getRetrofit();
        ChargeAndStallService api = retrofit.create(ChargeAndStallService.class);
        Call<chargebean> call = api.postCharge(scId);
        call.enqueue(new Callback<com.xiyou3g.select.parking.bean.chargebean>() {
            @Override
            public void onResponse(Call<chargebean> call, Response<chargebean> response) {
                chargebean = response.body();
                Log.d("123", "response");
                if (chargebean != null) {
                    Log.d("123", response.body().toString());
                    showInformation(new ShowChargeUI(activity, chargebean));
                }
            }

            @Override
            public void onFailure(Call<chargebean> call, Throwable t) {
                Log.d("123", t.toString());
            }
        });
    }

    private void getStall(Activity activity) {
        Retrofit retrofit = retrofitManager.getRetrofit();
        ChargeAndStallService api = retrofit.create(ChargeAndStallService.class);
        Call<stallbean> call = api.postStall(spId);
        call.enqueue(new Callback<stallbean>() {
            @Override
            public void onResponse(Call<stallbean> call, Response<stallbean> response) {
                stallbean = response.body();
                if (stallbean != null) {
                    Log.d("123", stallbean.getData().toString());
                    showInformation(new ShowStallUI(activity, stallbean));
                }
            }

            @Override
            public void onFailure(Call<stallbean> call, Throwable t) {
                Log.d("123", "onFailure:"+t.toString());
            }
        });
    }

    private void show() {
        if (STATUS == CHARGE) {
            getCharge(this);
        } else if (STATUS == PARKING) {
            //showInformation(new ShowParkingUI(this, ));
        } else if (STATUS == STALL) {
            getStall(this);
        }
    }

    private void buttonClick() {
        Button navigation_button = findViewById(R.id.navigation_button);
        Button reserve_button = findViewById(R.id.reserve_button);
        Button delete_button = findViewById(R.id.delete_button);
        navigation_button.setOnClickListener(this);
        reserve_button.setOnClickListener(this);
        delete_button.setOnClickListener(this);
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
            String name;
            double latitude;
            double longitude;
            if (stallbean != null) {
                name = stallbean.getData().getOwnerNum();
                latitude = Double.parseDouble(stallbean.getData().getLatitude());
                longitude = Double.parseDouble(stallbean.getData().getLongitude());
            } else {
                name = chargebean.getData().getOwnerNum();
                latitude = Double.parseDouble(chargebean.getData().getLatitude());
                longitude = Double.parseDouble(chargebean.getData().getLongitude());
            }
            /*ARouter.getInstance().build("/map/MapActivity")
                    .withDouble("latitude", thisLatLng.latitude)
                    .withDouble("longitude", thisLatLng.longitude)
                    .withString("destination", showInformation.getName())
                    .navigation();*/
            ARouter.getInstance().build("/map/MapActivity")
                    .withDouble("latitude", latitude)
                    .withDouble("longitude", longitude)
                    .withString("destination", name)
                    .navigation();
        } else if (view.getId() == R.id.reserve_button) {

        } else if (view.getId() == R.id.delete_button) {
            bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(R.layout.layout_bottomsheetdialog_delete);
            Button sure_button = bottomSheetDialog.getWindow().findViewById(R.id.sure_button);
            Button cancel_sure_button = bottomSheetDialog.getWindow().findViewById(R.id.cancel_sure_button);
            sure_button.setOnClickListener(this);
            cancel_sure_button.setOnClickListener(this);
            bottomSheetDialog.show();
        } else if (view.getId() == R.id.sure_button) {

            bottomSheetDialog.cancel();
        } else if (view.getId() == R.id.cancel_sure_button) {
            bottomSheetDialog.cancel();
        }
    }
}