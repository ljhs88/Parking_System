package com.xiyou3g.select.parking.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import com.xiyou3g.select.parking.bean.chargebean;
import com.xiyou3g.select.parking.bean.stallbean;
import com.xiyou3g.select.parking.bean.resbean;

public interface ChargeAndStallService {

    @FormUrlEncoded
    @POST("sharedCharging/querySCbylola")
    Call<chargebean> postCharge(@Field("longitude") String longitude, @Field("latitude") String latitude);

    @FormUrlEncoded
    @POST("sharedParking/querySPbylola")
    Call<stallbean> postStall(@Field("longitude") String longitude, @Field("latitude") String latitude);

    /*-----------------------------*/
    @FormUrlEncoded
    @POST("reserver/createRes")
    Call<resbean> postCreate(@Field("userId") String userid, @Field("posId") String posId);

    @FormUrlEncoded
    @POST("reserver/cancelRes")
    Call<resbean> postCancel(@Field("Id") String podId);

}
