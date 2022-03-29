package com.xiyou3g.information.bean;

public class reChargeBean {

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
        private String walletId;
        private String userId;
        private int amount;
        private int rechargeType;
        private String rechargeTime;
        private int ispay;
        private int iscancel;
        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setWalletId(String walletId) {
            this.walletId = walletId;
        }
        public String getWalletId() {
            return walletId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
        public String getUserId() {
            return userId;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
        public int getAmount() {
            return amount;
        }

        public void setRechargeType(int rechargeType) {
            this.rechargeType = rechargeType;
        }
        public int getRechargeType() {
            return rechargeType;
        }

        public void setRechargeTime(String rechargeTime) {
            this.rechargeTime = rechargeTime;
        }
        public String getRechargeTime() {
            return rechargeTime;
        }

        public void setIspay(int ispay) {
            this.ispay = ispay;
        }
        public int getIspay() {
            return ispay;
        }

        public void setIscancel(int iscancel) {
            this.iscancel = iscancel;
        }
        public int getIscancel() {
            return iscancel;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id='" + id + '\'' +
                    ", walletId='" + walletId + '\'' +
                    ", userId='" + userId + '\'' +
                    ", amount=" + amount +
                    ", rechargeType=" + rechargeType +
                    ", rechargeTime='" + rechargeTime + '\'' +
                    ", ispay=" + ispay +
                    ", iscancel=" + iscancel +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "reChargeBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}
