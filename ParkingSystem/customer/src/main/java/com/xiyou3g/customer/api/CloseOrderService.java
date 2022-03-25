package com.xiyou3g.customer.api;

import com.xiyou3g.customer.bean.CloseOrderResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.POST;

public interface CloseOrderService {

    @POST("orders/closeOrders")
    Call<CloseOrderResponse> closeOrder(@Field("orderId")String order);

    @POST("orders/updateOrders")
    Call<CloseOrderResponse> updateOrder(@FieldMap Map<String, String> map);

}
