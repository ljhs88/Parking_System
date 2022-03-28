package com.xiyou3g.information.bean;

public class getWalletBean {

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

    public class Data {

        private String id;
        private String userId;
        private double balance;
        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
        public String getUserId() {
            return userId;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }
        public double getBalance() {
            return balance;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id='" + id + '\'' +
                    ", userId='" + userId + '\'' +
                    ", balance=" + balance +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "getWalletBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}
