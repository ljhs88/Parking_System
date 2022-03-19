package com.xiyou3g.select.parking.api;

import com.xiyou3g.select.parking.bean.CreateChargeResponse;
import com.xiyou3g.select.parking.bean.PhotoResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface CreateChargeService {

    @POST("sharedCharging/createSC")
    Call<CreateChargeResponse> createCharge(@Query("userId") String userId, @Body RequestBody requestBody);


    @POST("sharedCharging/modifySC")
    Call<CreateChargeResponse> changeCharge(@Query("scId") String scId, @Body RequestBody requestBody);

    @POST("sharedCharging/modifyImage")
    @Multipart
    Call<PhotoResponse> upLoadPhoto(@PartMap Map<String,RequestBody> map, @Part MultipartBody.Part file);


}
