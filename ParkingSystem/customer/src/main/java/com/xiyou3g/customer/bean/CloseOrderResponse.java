/**
 * Copyright 2022 bejson.com
 */
package com.xiyou3g.customer.bean;

/**
 * Auto-generated: 2022-03-25 20:54:49
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class CloseOrderResponse {

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

    @Override
    public String toString() {
        return "CloseOrderResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}