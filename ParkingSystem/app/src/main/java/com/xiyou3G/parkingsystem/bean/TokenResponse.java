/**
 * Copyright 2022 bejson.com
 */
package com.xiyou3G.parkingsystem.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Auto-generated: 2022-04-10 20:52:57
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class TokenResponse {

    private int status;
    private String msg;
    private boolean success;

    @SerializedName("Data")
    private TokenData data;
    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    public boolean getSuccess() {
        return success;
    }

    public void setData(TokenData data) {
        this.data = data;
    }
    public TokenData getData() {
        return data;
    }

}