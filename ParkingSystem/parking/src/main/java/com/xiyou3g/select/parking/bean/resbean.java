package com.xiyou3g.select.parking.bean;

public class resbean {

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
        private String posId;
        private String posName;
        private String userId;
        private String car;
        private String proId;
        private String resTime;

        public String getId() {
            return id;
        }

        public String getPosId() {
            return posId;
        }

        public String getPosName() {
            return posName;
        }

        public String getUserId() {
            return userId;
        }

        public String getCar() {
            return car;
        }

        public String getProId() {
            return proId;
        }

        public String getResTime() {
            return resTime;
        }
    }

}
