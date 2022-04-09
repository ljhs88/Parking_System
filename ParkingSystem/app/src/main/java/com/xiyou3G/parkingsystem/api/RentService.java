package com.xiyou3G.parkingsystem.api;

import com.xiyou3G.parkingsystem.rentbean.RentResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RentService {

    @POST("orders/getAllRent")
    @FormUrlEncoded
    Call<RentResponse> getAllRent(@Field("proId")String proId);

    @POST("orders/getRent")
    @FormUrlEncoded
    Call<RentResponse> getRent(@Field("rentId")String rentId);

}
