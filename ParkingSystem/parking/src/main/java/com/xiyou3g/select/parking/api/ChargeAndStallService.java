package com.xiyou3g.select.parking.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import com.xiyou3g.select.parking.bean.chargebean;
import com.xiyou3g.select.parking.bean.stallbean;

public interface ChargeAndStallService {

    @FormUrlEncoded
    @POST("sharedCharging/getSC/")
    Call<chargebean> postCharge(@Field("scId") String scId);

    @FormUrlEncoded
    @POST("sharedParking/getSP")
    Call<stallbean> postStall(@Field("spId") String spId);

}
