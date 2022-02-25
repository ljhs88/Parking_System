package com.xiyou3g.select.customer.register.Api;

import com.xiyou3g.select.customer.register.bean.IdentifyingCodeResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IdentifyingCodeService {

    @POST("passport/getSMSCode/")
    @FormUrlEncoded
    Call<IdentifyingCodeResponse> getIdentifyingCode (@Field("mobile")String mobile);

}
