package com.xiyou3g.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xiyou3g.customer.api.CloseOrderService;
import com.xiyou3g.customer.bean.CloseOrderResponse;
import com.xiyou3g.customer.bean.Data;
import com.xiyou3g.select.parking.util.RetrofitManager;

import java.util.HashMap;
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

        closeOrderService.updateOrder(map).enqueue(new Callback<CloseOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<CloseOrderResponse> call, @NonNull Response<CloseOrderResponse> response) {

            }

            @Override
            public void onFailure(@NonNull Call<CloseOrderResponse> call, @NonNull Throwable t) {

            }
        });

        closeOrderService.closeOrder(orderId).enqueue(new Callback<CloseOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<CloseOrderResponse> call, @NonNull Response<CloseOrderResponse> response) {
                CloseOrderResponse closeOrderResponse = response.body();
                assert closeOrderResponse != null;
                Data data = closeOrderResponse.getData();
                runOnUiThread(()->{
                    address.setText(data.getAddress());
                    if (data.getPosType() == 0){
                        type.setText("停车场");
                        finePrice.setVisibility(View.GONE);
                        findViewById(R.id.complete_finePrice_text1).setVisibility(View.GONE);
                    }
                    else {
                        type.setText("充电桩");
                        finePrice.setText(data.getFinePrice());
                    }
                    car.setText(data.getCar());
                    start.setText(data.getStartTime().toString());
                    end.setText(data.getEndTime().toString());
                    eleNum.setText(data.getEleNum());
                    orderPrice.setText(String.valueOf(data.getOrderPrice()));
                    subPrice.setText(String.valueOf(data.getSubPrice()));


                    payPrice.setText(String.valueOf(data.getPayPrice()));
                });
            }

            @Override
            public void onFailure(@NonNull Call<CloseOrderResponse> call, @NonNull Throwable t) {

            }
        });
    }

    private void initView() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();

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
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");

    }
}