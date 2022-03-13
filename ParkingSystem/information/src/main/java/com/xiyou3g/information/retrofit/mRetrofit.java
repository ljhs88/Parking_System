package com.xiyou3g.information.retrofit;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;
import com.xiyou3g.information.bean.informationBean;
import com.google.android.gms.common.util.HttpUtils;
import com.xiyou3g.information.bean.requestInformationBean;
import com.xiyou3g.information.retrofit.Api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.PartMap;

public class mRetrofit {

    Retrofit retrofit;
    public Api api;

    public mRetrofit() {
        //步骤4:创建Retrofit对象
        retrofit = new Retrofit.Builder()
                .baseUrl("http://101.201.78.192:8888/") // 设置网络请求baseUrl
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析
                .build();
        // 步骤5:创建网络请求接口的实例
        api = retrofit.create(Api.class);
    }

    public static mRetrofit getInstance() {
        mRetrofit retrofit = new mRetrofit();
        return retrofit;
    }

    public void setHttpPortrait(String strPath, String userid, String type) {
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
        //Log.d("123", "retrofit2");
        call.enqueue(new Callback<informationBean>() {
            @Override
            public void onResponse(Call<informationBean> call, Response<informationBean> response) {
                try {
                    Log.d("123", response.body().toString());
                } catch (Exception e) {
                    Log.d("123", e.toString());
                    //返回数据异常
                }
            }

            @Override
            public void onFailure(Call<informationBean> call, Throwable t) {
                //请求异常
                Log.d("123", "onFailure");
            }
        });
    }

    public void setContent(RequestBody requestBody) {
        retrofit2.Call<informationBean> call = mRetrofit.getInstance().api.postBody(requestBody);
        //步骤7:发送网络请求(异步)
        call.enqueue(new Callback<informationBean>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<informationBean> call, Response<informationBean> response) {
                Log.d("123", "onResponse"+response.body().toString());
            }

            @Override
            public void onFailure(Call<informationBean> call, Throwable t) {
                Log.d("123", t.toString());
            }
        });
    }

}
