package com.xiyou3g.select.customer.register.bean;

public class IdentifyingCodeResponse {

    private int status;
    private String msg;
    private boolean success;
    private String data;
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

    public void setData(String data) {
         this.data = data;
     }
     public String getData() {
         return data;
     }

    @Override
    public String toString() {
        return "IdentifyingCodeResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                ", data='" + data + '\'' +
                '}';
    }
}