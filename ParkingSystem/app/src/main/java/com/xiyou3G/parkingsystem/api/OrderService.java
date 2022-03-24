package com.xiyou3G.parkingsystem.api;

import com.xiyou3G.parkingsystem.bean.AllOrderResponse;
import com.xiyou3G.parkingsystem.bean.OrderResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OrderService {

    @POST("orders/getAllOrders")
    @FormUrlEncoded
    Call<AllOrderResponse> getAllOrder(@Field("userId")String userId);


    @POST("orders/getOrders")
    @FormUrlEncoded
    Call<OrderResponse> getOrder(@Field("ordersId")String ordersId);
}
