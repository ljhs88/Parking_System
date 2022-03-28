package com.xiyou3g.select.parking.bean;

public class orderbean {

    private int status;
    private String msg;
    private boolean success;
    private Data data;

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public Data getData() {
        return data;
    }

    public class Data {

        private String id;
        private String proId;
        private String posId;
        private String address;
        private int posType;
        private double posPrice;
        private String userId;
        private String car;
        private String startTime;
        private String endTime;
        private String orderTime;
        private String eleNum;
        private String orderPrice;
        private String subPrice;
        private String finePrice;
        private String payPrice;
        private String payType;
        private String payId;
        private String payTime;
        private String ispay;
        private int iscancel;

        public String getId() {
            return id;
        }

        public String getProId() {
            return proId;
        }

        public String getPosId() {
            return posId;
        }

        public String getAddress() {
            return address;
        }

        public int getPosType() {
            return posType;
        }

        public double getPosPrice() {
            return posPrice;
        }

        public String getUserId() {
            return userId;
        }

        public String getCar() {
            return car;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public String getEleNum() {
            return eleNum;
        }

        public String getOrderPrice() {
            return orderPrice;
        }

        public String getSubPrice() {
            return subPrice;
        }

        public String getFinePrice() {
            return finePrice;
        }

        public String getPayPrice() {
            return payPrice;
        }

        public String getPayType() {
            return payType;
        }

        public String getPayId() {
            return payId;
        }

        public String getPayTime() {
            return payTime;
        }

        public String getIspay() {
            return ispay;
        }

        public int getIscancel() {
            return iscancel;
        }
    }

}
