package com.xiyou3g.select.customer.register.Api;

import com.xiyou3g.select.customer.register.bean.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {

    @POST("passport/login/")
    @FormUrlEncoded
    Call<LoginResponse> login(@Field("mobile")String mobile, @Field("smsCode")String smsCode);

}
