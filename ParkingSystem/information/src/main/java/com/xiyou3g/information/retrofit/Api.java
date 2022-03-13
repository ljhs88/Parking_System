package com.xiyou3g.information.retrofit;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

import com.xiyou3g.information.bean.informationBean;

public interface Api {

    //post请求，如果有参数需要添加 @FormUrlEncoded注解，即和@Field配合使用
    @FormUrlEncoded
    @POST("userInfo/modifyUserInfo/")
    Call<ResponseBody> postMap(@FieldMap Map<String, String> map);

    @POST("userInfo/modifyUserInfo/")
    Call<informationBean> postBody(@Body RequestBody body);

    @POST("passport/login")
    Call<informationBean> postBodyLogin(@Body RequestBody body);

    @FormUrlEncoded
    @POST("userInfo/modifyImage")
    Call<ResponseBody> post(@Field("userId") String id, @Field("type") String type, @Field("file") String file);

    @Multipart
    @POST("userInfo/modifyImage")
    Call<informationBean> setHttpPortrait(@PartMap Map<String, RequestBody> map,
                                          @Part MultipartBody.Part image);

    @Streaming
    @GET
    Call<ResponseBody> getPicture(@Url String url);

}
