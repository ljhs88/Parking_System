package com.xiyou3g.select.parking.util;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static Retrofit retrofit;
    private static String baseUrl;
    private static RetrofitManager retrofitManager;

    private RetrofitManager(String baseUrl/*, String userToken*/) {
        RetrofitManager.baseUrl = baseUrl;
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        /*.addHeader("userToken", userToken)*/
                        .build();
                return chain.proceed(request);
            }
        }).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                /*.client(okHttpClient)*/
                .build();
    }

    public static RetrofitManager createRetrofitManager(String baseUrl) {
        if (retrofitManager == null) {
            retrofitManager = new RetrofitManager(baseUrl);
        }
        return retrofitManager;
    }

    public void changeBaseUrl(String baseUrl) {
        if (baseUrl.equals(RetrofitManager.baseUrl)) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        RetrofitManager.baseUrl = baseUrl;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
