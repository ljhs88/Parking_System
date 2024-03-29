package com.xiyou3g.select.parking.bean;

public class PhotoResponse {

    private int status;
    private String msg;
    private boolean success;
    private Data data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PhotoResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                ", data='" + data + '\'' +
                '}';
    }
}
