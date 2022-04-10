package com.xiyou3g.select.pay.api;

import com.xiyou3g.select.pay.bean.PayResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PayService {

    @POST("payment/queryPayment")
    @FormUrlEncoded
    Call<PayResponse> getPay(@Field("userId")String userId);

    @POST("payment/queryIncome")
    @FormUrlEncoded
    Call<PayResponse> getIncome(@Field("proId")String proId);

}
