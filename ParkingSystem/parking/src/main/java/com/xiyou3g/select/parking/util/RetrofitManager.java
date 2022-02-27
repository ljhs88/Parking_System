package com.xiyou3g.select.parking.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static Retrofit retrofit;
    private static String baseUrl;
    private static RetrofitManager retrofitManager;

    private RetrofitManager(String baseUrl) {
        RetrofitManager.baseUrl = baseUrl;
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
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
