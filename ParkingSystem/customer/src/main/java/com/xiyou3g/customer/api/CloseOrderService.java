package com.xiyou3g.customer.api;

import com.xiyou3g.customer.bean.CloseOrderResponse;
import com.xiyou3g.select.pay.bean.PayResultResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CloseOrderService {

    @POST("orders/closeOrders")
    @FormUrlEncoded
    Call<CloseOrderResponse> closeOrder(@Field("orderId")String order);

    @POST("orders/updateOrders")
    @FormUrlEncoded
    Call<CloseOrderResponse> updateOrder(@FieldMap Map<String, String> map);

    @POST("orders/getOrders")
    @FormUrlEncoded
    Call<CloseOrderResponse> findOrder(@Field("ordersId")String order);

    @POST("payment/createPayment")
    @FormUrlEncoded
    Call<PayResultResponse> payOrder(@FieldMap Map<String, String> map);
}
