package com.xiyou3G.parkingsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.xiyou3G.parkingsystem.api.TokenService;
import com.xiyou3G.parkingsystem.bean.TokenResponse;
import com.xiyou3g.select.customer.register.Cus_LoginActivity;
import com.xiyou3g.select.parking.util.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private final RetrofitManager retrofitManager = RetrofitManager.createRetrofitManager("http://101.201.78.192:8888/");

    private boolean isLogin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            decorView.setSystemUiVisibility(systemUiVisibility | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getToken();
        Handler handler = new Handler();
        handler.postDelayed(() -> {

            Intent intent;
            if (isLogin) {
                intent = new Intent(SplashActivity.this, MainActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, Cus_LoginActivity.class);
            }
            startActivity(intent);
            finish();
        }, 3000);

    }

    private void getToken() {
        TokenService tokenService = retrofitManager.getRetrofit().create(TokenService.class);
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");
        String mobile = sharedPreferences.getString("mobile", "");
        String userToken = sharedPreferences.getString("userToken", "");
        tokenService.getToken(userId, userToken).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(@NonNull Call<TokenResponse> call, @NonNull Response<TokenResponse> response) {
                TokenResponse tokenResponse = response.body();
                Log.d("TAG123", "onResponse: " + response);
                if (tokenResponse == null) {
                    isLogin = false;
                } else {
                    isLogin = tokenResponse.getSuccess();
                }

            }

            @Override
            public void onFailure(@NonNull Call<TokenResponse> call, @NonNull Throwable t) {

            }
        });
    }


}