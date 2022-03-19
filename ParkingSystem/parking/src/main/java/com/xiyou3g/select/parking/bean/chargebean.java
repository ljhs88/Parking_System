package com.xiyou3g.select.parking.bean;

public class chargebean {

    private String status;
    private String msg;
    private String success;
    private data data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public data getData() {
        return data;
    }

    public void setData(data data) {
        this.data = data;
    }

    public class data {
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
        private int price;
        private String image1;
        private String image2;
        private String image3;
        private String adminName;
        private String adminMobile;
        private int isfine;
        private int finePrice;

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

        public int getPrice() {
            return price;
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

        public int getIsfine() {
            return isfine;
        }

        public int getFinePrice() {
            return finePrice;
        }

        @Override
        public String toString() {
            return "data{" +
                    "id='" + id + '\'' +
                    ", userId='" + userId + '\'' +
                    ", auditState=" + auditState +
                    ", ispublish=" + ispublish +
                    ", status=" + status +
                    ", ownerName='" + ownerName + '\'' +
                    ", ownerMobile='" + ownerMobile + '\'' +
                    ", ownerImage='" + ownerImage + '\'' +
                    ", province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    ", district='" + district + '\'' +
                    ", address='" + address + '\'' +
                    ", longitude='" + longitude + '\'' +
                    ", latitude='" + latitude + '\'' +
                    ", ownerNum='" + ownerNum + '\'' +
                    ", price=" + price +
                    ", image1='" + image1 + '\'' +
                    ", image2='" + image2 + '\'' +
                    ", image3='" + image3 + '\'' +
                    ", adminName='" + adminName + '\'' +
                    ", adminMobile='" + adminMobile + '\'' +
                    ", isfine=" + isfine +
                    ", finePrice=" + finePrice +
                    '}';
        }
    }



}
