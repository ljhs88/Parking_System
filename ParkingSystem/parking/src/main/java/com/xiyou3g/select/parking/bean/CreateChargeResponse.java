package com.xiyou3g.select.parking.bean;

public class CreateChargeResponse {

    private int status;
    private String msg;
    private boolean success;
    private Data data;
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

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

}