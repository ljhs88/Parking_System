package com.xiyou3g.information.retrofit;

import android.annotation.SuppressLint;
import android.util.Log;

import com.xiyou3g.information.bean.informationBean;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class mRetrofit {

    public static Retrofit retrofit;
    public static Api api;

    public mRetrofit() {
    }

    public static Retrofit getInstance() {
        if (retrofit == null) {
            //步骤4:创建Retrofit对象
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://101.201.78.192:8888/") // 设置网络请求baseUrl
                    .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析
                    .build();
            // 步骤5:创建网络请求接口的实例
            api = retrofit.create(Api.class);
        }
        return retrofit;
    }

    public static void setHttpPortrait(String strPath, String userid, String type) {
        File file = new File(strPath);
        MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("file", file.getName(),
                RequestBody.create(MediaType.parse("multipart/form-data"), file));
        //Log.d("123","file:" + imageBodyPart.toString());
        /*MultipartBody.Part idBodyPart = MultipartBody.Part.createFormData("userId", userid,
                RequestBody.create(MediaType.parse("multipart/form-data"), userid));
        MultipartBody.Part typeBodyPart = MultipartBody.Part.createFormData("type", type,
                RequestBody.create(MediaType.parse("multipart/form-data"), type));*/
        Map<String, RequestBody> map = new HashMap<>();
        map.put("userId", RequestBody.create(MediaType.parse("multipart/form-data"), userid));
        map.put("type", RequestBody.create(MediaType.parse("multipart/form-data"), type));
        Call<informationBean> call = api.setHttpPortrait(map, imageBodyPart);
        call.enqueue(new Callback<informationBean>() {
            @Override
            public void onResponse(Call<informationBean> call, Response<informationBean> response) {
                try {
                    Log.d("123", "SET:"+response.body().toString());
                } catch (Exception e) {
                    Log.d("123", e.toString());
                }
            }

            @Override
            public void onFailure(Call<informationBean> call, Throwable t) {
                //请求异常
                Log.d("123", "onFailure");
            }
        });
    }

    public static void setContent(RequestBody requestBody) {
        retrofit2.Call<informationBean> call = api.postBody(requestBody);
        //步骤7:发送网络请求(异步)
        call.enqueue(new Callback<informationBean>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<informationBean> call, Response<informationBean> response) {
                if (response.body() != null){
                    Log.d("123", "SET:"+response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<informationBean> call, Throwable t) {
                Log.d("123", t.toString());
            }
        });
    }

}
