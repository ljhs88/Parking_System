package com.xiyou3g.select.parking.api;

import com.xiyou3g.select.parking.bean.chargebean;
import com.xiyou3g.select.parking.bean.stallbean;
import com.xiyou3g.select.parking.getbean.GetChargeOrStallResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetNearbyService {

    @POST("sharedParking/geoRadiusPoint")
    @FormUrlEncoded
    Call<GetChargeOrStallResponse> getStall(@Field("longitude")String longitude, @Field("latitude")String latitude);

    @POST("sharedCharging/geoRadiusPoint")
    @FormUrlEncoded
    Call<GetChargeOrStallResponse> getCharge(@Field("longitude")String longitude, @Field("latitude")String latitude);

    @FormUrlEncoded
    @POST("sharedCharging/getSC/")
    Call<chargebean> postCharge(@Field("scId") String scId);

    @FormUrlEncoded
    @POST("sharedParking/getSP")
    Call<stallbean> postStall(@Field("spId") String spId);

}
