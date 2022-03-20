package com.xiyou3g.select.parking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.xiyou3g.select.parking.UI.ShowChargeUI;
import com.xiyou3g.select.parking.UI.ShowStallUI;
import com.xiyou3g.select.parking.UI.ShowUI;
import com.xiyou3g.select.parking.api.ChargeAndStallService;
import com.xiyou3g.select.parking.bean.ShowInformation;
import com.xiyou3g.select.parking.bean.chargebean;
import com.xiyou3g.select.parking.bean.resbean;
import com.xiyou3g.select.parking.bean.stallbean;
import com.xiyou3g.select.parking.util.RetrofitManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class showInformationFragment extends Fragment implements View.OnClickListener {

    private View view;
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
    private com.xiyou3g.select.parking.bean.chargebean chargebean = null;
    private com.xiyou3g.select.parking.bean.stallbean stallbean = null;

    private String userId;
    private String token;
    private String mobile;
    private String posId;
    private Button navigation_button;
    private Button reserve_button;
    private Button delete_button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show_information, container,false);

        SharedPreferences pref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        userId = pref.getString("userId", "");
        token = pref.getString("userToken", "");
        mobile = pref.getString("mobile", "");

        getStatus();
        buttonClick();
        show();

        return view;
    }

    /**
     * 获取充电桩信息
     * @param activity
     */
    private void getCharge(Activity activity) {
        Retrofit retrofit = retrofitManager.getRetrofit();
        ChargeAndStallService api = retrofit.create(ChargeAndStallService.class);
        Call<chargebean> call = api.postCharge(String.valueOf(thisLatLng.longitude),
                String.valueOf(thisLatLng.latitude));
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
        Call<stallbean> call = api.postStall(String.valueOf(thisLatLng.longitude),
                String.valueOf(thisLatLng.latitude));
        call.enqueue(new Callback<stallbean>() {
            @Override
            public void onResponse(Call<stallbean> call, Response<stallbean> response) {
                stallbean = response.body();
                if (stallbean != null) {
                    Log.d("123", stallbean.getData().toString());
                    showInformation(new ShowStallUI(activity, stallbean));
                } else {
                    Toast.makeText(getActivity(), "获取停车位信息失败!请重新获取!", Toast.LENGTH_SHORT).show();
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
            getCharge(getActivity());
        } else if (STATUS == PARKING) {
            //showInformation(new ShowParkingUI(this, ));
        } else if (STATUS == STALL) {
            getStall(getActivity());
        }
    }

    private void buttonClick() {
        navigation_button = view.findViewById(R.id.navigation_button);
        reserve_button = view.findViewById(R.id.reserve_button);
        delete_button = view.findViewById(R.id.delete_button);
        navigation_button.setOnClickListener(this);
        reserve_button.setOnClickListener(this);
        delete_button.setOnClickListener(this);
    }

    private void getStatus() {
        Intent intent = getActivity().getIntent();
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
    public void onClick(View view) {
        if (view.getId() == R.id.navigation_button) {
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
            createRes();
        } else if (view.getId() == R.id.delete_button) {
            bottomSheetDialog = new BottomSheetDialog(getActivity());
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

    private void createRes() {
        Retrofit retrofit = retrofitManager.getRetrofit();
        ChargeAndStallService api = retrofit.create(ChargeAndStallService.class);
        posId = stallbean.getData().get(0).getId();
        api.postCreate(userId, posId).enqueue(new Callback<resbean>() {
            @Override
            public void onResponse(Call<resbean> call, Response<resbean> response) {
                resbean resbean = response.body();
                if (resbean != null && resbean.isSuccess() == true) {
                    reserve_button.setText(R.string.reservecancel);
                    Toast.makeText(getActivity(), "预约成功!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "预约失败!请重新预约!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<resbean> call, Throwable t) {

            }
        });
    }

    private void cancelRes() {
        Retrofit retrofit = retrofitManager.getRetrofit();
        retrofit.create(ChargeAndStallService.class).
                postCancel(posId).enqueue(new Callback<resbean>() {
            @Override
            public void onResponse(Call<resbean> call, Response<resbean> response) {
                resbean resbean = response.body();
                if (resbean != null && resbean.isSuccess() == true) {
                    reserve_button.setText(R.string.reserve);
                    Toast.makeText(getActivity(), "取消预约成功!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "取消失败!请重新取消!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<resbean> call, Throwable t) {

            }
        });
    }
}
