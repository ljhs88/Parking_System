package com.xiyou3g.information.bean;

import com.google.gson.annotations.SerializedName;

public class informationBean {

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

    public informationBean.data getData() {
        return data;
    }

    public void setData(informationBean.data data) {
        this.data = data;
    }

    public class data {
        private String id;
        private String mobile;
        private String nickname;
        private String face;
        private int sex;
        private String birthday;
        private String country;
        private String province;
        private String city;
        private String district;
        private String description;
        private String bgImg;
        private String createdTime;
        private String updatedTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getBgImg() {
            return bgImg;
        }

        public void setBgImg(String bgImg) {
            this.bgImg = bgImg;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getUpdatedTime() {
            return updatedTime;
        }

        public void setUpdatedTime(String updatedTime) {
            this.updatedTime = updatedTime;
        }

        @Override
        public String toString() {
            return "data{" +
                    "id='" + id + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", face='" + face + '\'' +
                    ", sex=" + sex +
                    ", birthday='" + birthday + '\'' +
                    ", country='" + country + '\'' +
                    ", province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    ", district='" + district + '\'' +
                    ", description='" + description + '\'' +
                    ", bgImg='" + bgImg + '\'' +
                    ", createdTime='" + createdTime + '\'' +
                    ", updatedTime='" + updatedTime + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "informationBean{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", success='" + success + '\'' +
                ", data=" + data +
                '}';
    }
}
