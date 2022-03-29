package com.xiyou3g.select.parking.bean;

import java.util.List;

public class chargebean {

        private int status;
        private String msg;
        private boolean success;
        private List<data> data;

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

        public void setData(List<data> data) {
            this.data = data;
        }
        public List<data> getData() {
            return data;
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
            private double price;
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

            public double getPrice() {
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
                return "Data{" +
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

    @Override
    public String toString() {
        return "chargebean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}

