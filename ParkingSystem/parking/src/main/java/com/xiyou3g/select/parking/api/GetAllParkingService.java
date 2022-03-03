package com.xiyou3g.select.parking.api;

import com.xiyou3g.select.parking.bean.AllParkingResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetAllParkingService {

    @GET("admin/getAllParking")
    Call<ArrayList<AllParkingResponse>> get(@Query("parksId")String parksId);

}
