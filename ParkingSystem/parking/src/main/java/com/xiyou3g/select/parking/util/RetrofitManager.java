package com.xiyou3g.select.parking.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    Retrofit retrofit;
    String baseUrl;
    RetrofitManager retrofitManager;

    private RetrofitManager(String baseUrl) {
        this.baseUrl = baseUrl;
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public RetrofitManager createRetrofitManager(String baseUrl) {
        if (retrofitManager == null) {
            retrofitManager = new RetrofitManager(baseUrl);
        }
        return retrofitManager;
    }

    public void changeBaseUrl(String baseUrl) {
        if (baseUrl.equals(this.baseUrl)) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        this.baseUrl = baseUrl;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
