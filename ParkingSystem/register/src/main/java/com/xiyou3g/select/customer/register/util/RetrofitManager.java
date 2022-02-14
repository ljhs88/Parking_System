package com.xiyou3g.select.customer.register.util;

import com.xiyou3g.select.customer.register.Api.Api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private final Retrofit retrofit;

    public RetrofitManager(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private Retrofit getRetrofit() {
        return retrofit;
    }

    public Api getApi() {
        return getRetrofit().create(Api.class);
    }

    public Call<ResponseBody> returnGet(String account, String password) {
        return getApi().get(account, password);
    }

    public Call<ResponseBody> returnPost(String account, String password) {
        return getApi().post(account, password);
    }


}
