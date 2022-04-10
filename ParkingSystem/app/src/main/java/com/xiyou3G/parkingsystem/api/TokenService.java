package com.xiyou3G.parkingsystem.api;

import com.xiyou3G.parkingsystem.bean.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TokenService {

    @POST("passport/verifyToken")
    @FormUrlEncoded
    Call<TokenResponse> getToken(@Field("userId")String userId, @Field("token")String token);
}
