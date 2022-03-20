package com.xiyou3g.select.parking.api;

import com.xiyou3g.select.parking.bean.CreateStallResponse;
import com.xiyou3g.select.parking.bean.PhotoResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface CreateStallService {

    @POST("sharedParking/createSP")
    Call<CreateStallResponse> createStall(@Query("userId") String userId, @Body RequestBody requestBody);


    @POST("sharedParking/modifySP")
    @FormUrlEncoded
    Call<CreateStallResponse> changeStall(@Field("spId") String spId, @Body RequestBody requestBody);

    @POST("sharedParking/modifyImage")
    @Multipart
    Call<PhotoResponse> upLoadPhoto(@PartMap Map<String,RequestBody> map, @Part MultipartBody.Part file);

}
