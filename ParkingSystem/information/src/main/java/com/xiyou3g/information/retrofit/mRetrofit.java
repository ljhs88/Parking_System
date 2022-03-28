package com.xiyou3g.information.retrofit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.xiyou3g.information.bean.informationBean;
import com.xiyou3g.information.bean.reChargeBean;

import java.io.File;
import java.io.IOException;
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
import com.xiyou3g.information.Utility.ToastUtil;

public class mRetrofit {

    public static boolean ok = false;
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

    public static void setHttpPortrait(Activity activity, String strPath, String userid, String type) {
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
        Call<informationBean> call = api.setHeadAndBg(map, imageBodyPart);
        call.enqueue(new Callback<informationBean>() {
            @Override
            public void onResponse(Call<informationBean> call, Response<informationBean> response) {
                if (response.body()!=null) {
                    try {
                        Log.d("123", "SET:"+response.body().toString());
                    } catch (Exception e) {
                        ToastUtil.getToast(activity, "更新照片失败！请重新尝试！");
                    }
                } else {
                    ToastUtil.getToast(activity, "更新照片失败！请重新尝试！");
                }
            }

            @Override
            public void onFailure(Call<informationBean> call, Throwable t) {
                //请求异常
                ToastUtil.getToast(activity, "更新照片失败！请重新尝试！");
            }
        });
    }

    public static void setContent(Activity activity, RequestBody requestBody) {
        retrofit2.Call<informationBean> call = api.postBody(requestBody);
        //步骤7:发送网络请求(异步)
        call.enqueue(new Callback<informationBean>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<informationBean> call, Response<informationBean> response) {
                if (response.body() != null){
                    Log.d("123", "SET:"+response.body().toString());
                } else {
                    ToastUtil.getToast(activity, "更新信息失败！请重新尝试！");
                }
            }

            @Override
            public void onFailure(Call<informationBean> call, Throwable t) {
                ToastUtil.getToast(activity, "更新信息失败！请重新尝试！");
            }
        });
    }

    /*-----------------------------------身份验证------------------------------------------*/

    public static void setCardPhoto(Activity activity, String strPath, String userid, Integer type) {
        File file = new File(strPath);
        MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("file", file.getName(),
                RequestBody.create(MediaType.parse("multipart/form-data"), file));
        Map<String, RequestBody> map = new HashMap<>();
        map.put("userId", RequestBody.create(MediaType.parse("multipart/form-data"), userid));
        map.put("type", RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(type)));
        /*RequestBody useridBody = RequestBody.create(MediaType.parse("multipart/form-data"), userid);
        RequestBody typeBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(type));*/
        Call<ResponseBody> call = api.setCard(map, imageBodyPart);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("123", type+":"+response.body().string());
                    ToastUtil.getToast(activity, "上传成功！");
                } catch (Exception e) {
                    Log.d("123", "onResponse:" +e.toString());
                    ToastUtil.getToast(activity, "上传照片失败，请重新尝试！");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //请求异常
                Log.d("123", t.toString());
                ToastUtil.getToast(activity, "上传照片失败，请重新尝试！");
            }
        });
    }

    public static void setIdentity(Activity activity, String userid, RequestBody requestBody) {
        Call<ResponseBody> call = api.identityPost(userid, requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body()!=null){
                    try {
                        Log.d("123", response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                        ToastUtil.getToast(activity, "上传信息失败，请重新尝试！");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("123", "onFailure:"+t.toString());
                ToastUtil.getToast(activity, "上传信息失败，请重新尝试！");
            }
        });
    }

    public static void cancelIdentity(Activity activity, String userid) {
        Call<ResponseBody> call = api.identityCancel(userid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body()!=null) {
                    try {
                        Log.d("123", "cancel:"+response.body().string());
                        ToastUtil.getToast(activity, "取消成功！");
                    } catch (IOException e) {
                        e.printStackTrace();
                        ToastUtil.getToast(activity, "取消实名失败，请重新尝试！");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("123", t.toString());
                ToastUtil.getToast(activity, "取消实名失败，请重新尝试！");
            }
        });
    }

    public static void setUpDataIdentity(Activity activity, RequestBody body) {
        Call<ResponseBody> call = api.identityUpDataPost(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body()!=null){
                    try {
                        Log.d("123", response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                        ToastUtil.getToast(activity, "更新实名信息失败，请重新尝试！");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("123", t.toString());
                ToastUtil.getToast(activity, "更新实名信息失败，请重新尝试！");
            }
        });
    }

    /*-----------------------钱包---------------------*/

    public static void reCharge(Activity activity,Map<String, String> map) {
        api.reCharge(map).enqueue(new Callback<reChargeBean>() {
            @Override
            public void onResponse(Call<reChargeBean> call, Response<reChargeBean> response) {
                reChargeBean bean = response.body();
                if (bean != null && bean.getSuccess()==true) {
                    ToastUtil.getToast(activity, "充值成功！");
                } else {
                    ToastUtil.getToast(activity, "充值失败！请重新尝试！");
                }
            }

            @Override
            public void onFailure(Call<reChargeBean> call, Throwable t) {
                ToastUtil.getToast(activity, "充值失败！请重新尝试！");
            }
        });
    }



}
