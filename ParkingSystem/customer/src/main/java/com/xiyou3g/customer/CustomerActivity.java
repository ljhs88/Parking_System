package com.xiyou3g.customer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
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
    private static int STATUS = 1;
    private static final int STALL = 1;
    private static final int CHARGE = 2;

    private final RetrofitManager retrofitManager = RetrofitManager.createRetrofitManager("http://101.201.78.192:8888/");

    CloseOrderService closeOrderService = retrofitManager.getRetrofit().create(CloseOrderService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        initView();
        request();
    }

    private void request() {


        closeOrderService.findOrder(orderId).enqueue(new Callback<CloseOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<CloseOrderResponse> call, @NonNull Response<CloseOrderResponse> response) {
                CloseOrderResponse closeOrderResponse = response.body();
                if (closeOrderResponse != null && closeOrderResponse.getData() != null) {
                    Data data = closeOrderResponse.getData();
                    Log.d("TAG", "onResponse: " + data);
                    runOnUiThread(()->{
                        address.setText(data.getAddress());
                        if (data.getPosType() == 0){
                            type.setText("停车位");
                            finePrice.setVisibility(View.GONE);
                            STATUS = STALL;
                            findViewById(R.id.complete_finePrice_text1).setVisibility(View.GONE);
                            findViewById(R.id.complete_eleNum_text1).setVisibility(View.GONE);
                            eleNum.setVisibility(View.GONE);
                        } else {
                            type.setText("充电桩");
                            finePrice.setText(String.valueOf(data.getFinePrice()));
                            STATUS = CHARGE;
                            eleNum.setText(String.valueOf(data.getEleNum()));
                        }
                        car.setText(data.getCar());
                        start.setText(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.CHINA).format(data.getStartTime()));
                        if (data.getEndTime() != null) {
                            end.setText(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.CHINA).format(data.getEndTime()));
                        } else {
                            end.setVisibility(View.GONE);
                            findViewById(R.id.complete_endTime_text2).setVisibility(View.GONE);
                        }

                        orderPrice.setText(String.valueOf(data.getOrderPrice()));
                        subPrice.setText(String.valueOf(data.getSubPrice()));
                        payPrice.setText(String.valueOf(data.getPayPrice()));
                        if (data.getIscancel() == 1) {
                            completeButton.setVisibility(View.GONE);
                        }



                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<CloseOrderResponse> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure: ", t);
            }

        });

    }

    @SuppressLint("SetTextI18n")
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
        completeButton.setClickable(true);
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        Log.d("TAG", "initView: " + 1);
        completeButton.setOnClickListener(view->{
            Log.d("TAG", "initView: ");


            Map<String, String> map = new HashMap<>();
            String eleNumString = getEleNum();
            String subPriceString = "3";
            map.put("eleNum", eleNumString);
            map.put("ordersId", orderId);
            map.put("subPrice",subPriceString);
            Log.d("TAG123", "initView: " + orderId);
            closeOrderService.updateOrder(map).enqueue(new Callback<CloseOrderResponse>() {
                @Override
                public void onResponse(@NonNull Call<CloseOrderResponse> call, @NonNull Response<CloseOrderResponse> response) {
                    CloseOrderResponse closeOrderResponse = response.body();
                    Log.d("TAG123", "onResponse: " + "data");
                    Log.d("TAG123", "onResponse: " + closeOrderResponse);
                    if (closeOrderResponse != null) {

                        closeOrderService.closeOrder(orderId).enqueue(new Callback<CloseOrderResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<CloseOrderResponse> call, @NonNull Response<CloseOrderResponse> response) {
                                CloseOrderResponse closeOrderResponse1 = response.body();
                                if (closeOrderResponse1 != null) {
                                    Data data = closeOrderResponse1.getData();
                                    if (data.getIspay() == 0) {
                                        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(CustomerActivity.this, R.style.BottomSheetDialog);
                                        bottomSheetDialog.setContentView(R.layout.layout_bottom_pay);
                                        bottomSheetDialog.show();
                                        bottomSheetDialog.getWindow().findViewById(R.id.wallet).setOnClickListener(v->{
                                            PopupWindow popupWindow = new PopupWindow(CustomerActivity.this);
                                            View view = View.inflate(CustomerActivity.this, R.layout.pay_layout, null);
                                            popupWindow.setContentView(view);
                                            popupWindow.setBackgroundDrawable(null);
                                            popOutShadow(popupWindow);
                                            bottomSheetDialog.show();
                                            popupWindow.showAtLocation(CustomerActivity.this.getWindow().getDecorView().getRootView(), Gravity.CENTER, 0 ,0);
                                            Button button = view.findViewById(R.id.pay_button);
                                            TextView textView1 = view.findViewById(R.id.pay_text1);
                                            TextView textView2 = view.findViewById(R.id.pay_text2);
                                            textView1.setText("向商家付款");
                                            textView2.setText("￥" + payPrice.getText());
                                            button.setOnClickListener(v1 -> {
                                                Map<String, String> map = new HashMap<>();
                                                map.put("orderId", orderId);
                                                map.put("payType", "0");
                                                closeOrderService.payOrder(map).enqueue(new Callback<PayResultResponse>() {
                                                    @Override
                                                    public void onResponse(@NonNull Call<PayResultResponse> call, @NonNull Response<PayResultResponse> response) {
                                                        PayResultResponse payResultResponse = response.body();
                                                        Log.d("TAG", "onResponse: pay" + orderId);
                                                        if (payResultResponse != null) {
                                                            finish();
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(@NonNull Call<PayResultResponse> call, @NonNull Throwable t) {

                                                    }
                                                });
                                            });
                                            bottomSheetDialog.cancel();
                                        });
                                    } else {
                                        finish();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<CloseOrderResponse> call, @NonNull Throwable t) {

                            }
                        });
                    }

                }

                @Override
                public void onFailure(@NonNull Call<CloseOrderResponse> call, @NonNull Throwable t) {
                    Log.e("TAG123", "onFailure: ", t);
                }
            });
        });


    }

    private void popOutShadow(PopupWindow popupWindow) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;//设置阴影透明度
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getWindow().getAttributes();
            lp1.alpha = 1f;
           getWindow().setAttributes(lp1);
        });
    }

    private String getEleNum() {
        if (STATUS == STALL) {
            return "0";
        } else {
            return "4";
        }
    }
}