package com.xiyou3g.select.parking.bean;

import java.util.List;

public class stallbean {

    private int status;
    private String msg;
    private boolean success;
    private List<Data> data;

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<Data> getData() {
        return data;
    }

    public class Data {
        private String id;
        private String userId;
        private int auditState;
        private int ispublish;
        private int status;
        private String ownerName;
        private String ownerMobile;
        private String ownerImage;
        private String province;
        private String city;
        private String district;
        private String address;
        private String longitude;
        private String latitude;
        private String ownerNum;
        private double hourPrice;
        private String image1;
        private String image2;
        private String image3;
        private String adminName;
        private String adminMobile;

        public String getId() {
            return id;
        }

        public String getUserId() {
            return userId;
        }

        public int getAuditState() {
            return auditState;
        }

        public int getIspublish() {
            return ispublish;
        }

        public int getStatus() {
            return status;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public String getOwnerMobile() {
            return ownerMobile;
        }

        public String getOwnerImage() {
            return ownerImage;
        }

        public String getProvince() {
            return province;
        }

        public String getCity() {
            return city;
        }

        public String getDistrict() {
            return district;
        }

        public String getAddress() {
            return address;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getOwnerNum() {
            return ownerNum;
        }

        public double getHourPrice() {
            return hourPrice;
        }

        public String getImage1() {
            return image1;
        }

        public String getImage2() {
            return image2;
        }

        public String getImage3() {
            return image3;
        }

        public String getAdminName() {
            return adminName;
        }

        public String getAdminMobile() {
            return adminMobile;
        }
    }

}
