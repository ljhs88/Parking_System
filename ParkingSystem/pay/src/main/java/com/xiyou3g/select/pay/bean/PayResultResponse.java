package com.xiyou3g.select.pay.bean;

import com.google.gson.annotations.SerializedName;

public class PayResultResponse {

    private int status;
    private String msg;
    private boolean success;

    @SerializedName("Data")
    private PayData data;
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

    public void setData(PayData data) {
        this.data = data;
    }
    public PayData getData() {
        return data;
    }

}