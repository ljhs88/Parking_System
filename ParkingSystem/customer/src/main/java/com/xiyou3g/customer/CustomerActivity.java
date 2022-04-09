package com.xiyou3g.customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.xiyou3g.customer.api.CloseOrderService;
import com.xiyou3g.customer.bean.CloseOrderResponse;
import com.xiyou3g.customer.bean.Data;
import com.xiyou3g.select.parking.util.RetrofitManager;
import com.xiyou3g.select.parking.util.ToastUtil;
import com.xiyou3g.select.pay.bean.PayResultResponse;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Route(path = "/selectCustomer/CustomerActivity")
public class CustomerActivity extends AppCompatActivity {

    private TextView address;
    private TextView type;
    private TextView car;
    private TextView start;
    private TextView end;
    private TextView eleNum;
    private TextView orderPrice;
    private TextView subPrice;
    private TextView finePrice;
    private TextView payPrice;
    private String orderId;
    private Button completeButton;
    private final RetrofitManager retrofitManager = RetrofitManager.createRetrofitManager("http://101.201.78.192:8888/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        initView();
        request();
    }

    private void request() {
        CloseOrderService closeOrderService = retrofitManager.getRetrofit().create(CloseOrderService.class);
        Map<String, String> map = new HashMap<>();
        map.put("eleNum", "4");
        map.put("orderId", orderId);
        map.put("subPrice", "0");

        closeOrderService.findOrder(orderId).enqueue(new Callback<CloseOrderResponse>() {
            @Override
            public void onResponse(Call<CloseOrderResponse> call, Response<CloseOrderResponse> response) {
                CloseOrderResponse closeOrderResponse = response.body();
                Log.d("TAG", "onResponse: " + closeOrderResponse);
                Data data = closeOrderResponse.getData();
                runOnUiThread(()->{
                    address.setText(data.getAddress());
                    if (data.getPosType() == 0){
                        type.setText("停车场");
                        finePrice.setVisibility(View.GONE);

                        findViewById(R.id.complete_finePrice_text1).setVisibility(View.GONE);
                    } else {
                        type.setText("充电桩");
                        finePrice.setText(String.valueOf(data.getFinePrice()));
                    }
                    car.setText(data.getCar());
                    start.setText(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.CHINA).format(data.getStartTime()));
                    end.setText(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.CHINA).format(data.getStartTime()));
                    eleNum.setText(String.valueOf(data.getEleNum()));
                    orderPrice.setText(String.valueOf(data.getOrderPrice()));
                    subPrice.setText(String.valueOf(data.getSubPrice()));
                    payPrice.setText(String.valueOf(data.getPayPrice()));
                    if (data.getIscancel() == 1) {
                        completeButton.setVisibility(View.GONE);
                    }
                    completeButton.setOnClickListener(view->{
                        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(CustomerActivity.this, R.style.BottomSheetDialog);
                        bottomSheetDialog.setContentView(R.layout.layout_bottom_pay);
                        Button button = bottomSheetDialog.getWindow().findViewById(R.id.wallet);
                        button.setOnClickListener(v->{

                            closeOrderService.closeOrder(orderId).enqueue(new Callback<CloseOrderResponse>() {
                                @Override
                                public void onResponse(@NonNull Call<CloseOrderResponse> call, @NonNull Response<CloseOrderResponse> response) {

                                }

                                @Override
                                public void onFailure(@NonNull Call<CloseOrderResponse> call, @NonNull Throwable t) {

                                }
                            });

                            Map<String, String> map = new HashMap<>();
                            map.put("orderId", orderId);
                            map.put("payType", "0");

                            closeOrderService.updateOrder(map).enqueue(new Callback<CloseOrderResponse>() {
                                @Override
                                public void onResponse(@NonNull Call<CloseOrderResponse> call, @NonNull Response<CloseOrderResponse> response) {


                                }

                                @Override
                                public void onFailure(@NonNull Call<CloseOrderResponse> call, @NonNull Throwable t) {

                                }
                            });

                            closeOrderService.payOrder(map).enqueue(new Callback<PayResultResponse>() {
                                @Override
                                public void onResponse(@NonNull Call<PayResultResponse> call, @NonNull Response<PayResultResponse> response) {
                                    PayResultResponse payResultResponse = response.body();
                                    assert payResultResponse != null;
                                    ToastUtil.getToast(CustomerActivity.this, payResultResponse.getMsg());
                                    if (payResultResponse.getSuccess()) {
                                        finish();
                                    }

                                }

                                @Override
                                public void onFailure(@NonNull Call<PayResultResponse> call, @NonNull Throwable t) {

                                }
                            });
                        });
                    });
                });
            }

            @Override
            public void onFailure(Call<CloseOrderResponse> call, Throwable t) {

            }
        });



        Log.d("TAG", "request: " + orderId);

    }

    private void initView() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();

        //状态栏字体颜色变为黑色
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            decorView.setSystemUiVisibility(systemUiVisibility | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        address = findViewById(R.id.complete_address_text2);
        type = findViewById(R.id.complete_type_text2);
        car = findViewById(R.id.complete_car_text2);
        start = findViewById(R.id.complete_startTime_text2);
        end = findViewById(R.id.complete_endTime_text2);
        eleNum = findViewById(R.id.complete_eleNum_text2);
        orderPrice = findViewById(R.id.complete_orderPrice_text2);
        subPrice = findViewById(R.id.complete_subPrice_text2);
        finePrice = findViewById(R.id.complete_finePrice_text2);
        payPrice = findViewById(R.id.complete_payPrice_text2);
        completeButton = findViewById(R.id.complete_button);
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");

    }
}