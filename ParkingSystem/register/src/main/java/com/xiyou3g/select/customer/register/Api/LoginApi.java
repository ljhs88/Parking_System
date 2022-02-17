package com.xiyou3g.select.customer.register.Api;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginApi {

    @POST("post")
    @FormUrlEncoded
    Call<ResponseBody> post(@Field("useName") String useName, @Field("passWord") String passWord);

    @GET("get")
    Call<ResponseBody> get(@Query("key") String useName, @Query("passWord") String passWord);

    @POST("postBody")
    Call<ResponseBody> postBody(@Body RequestBody body);

}
