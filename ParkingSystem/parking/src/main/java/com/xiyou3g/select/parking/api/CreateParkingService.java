package com.xiyou3g.select.parking.api;

import com.xiyou3g.select.parking.bean.CreateParkingResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CreateParkingService {

    @POST("admin/createParks")
    Call<CreateParkingResponse> createParking(@Body RequestBody requestBody);

}
