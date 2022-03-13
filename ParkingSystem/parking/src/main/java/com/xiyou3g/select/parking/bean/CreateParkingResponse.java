package com.xiyou3g.select.parking.bean;

import androidx.annotation.NonNull;

public class CreateParkingResponse {

    private String id;
    private String name;
    private int num;
    private int avaNum;
    private int hourPrice;
    private int dayPrice;
    private int monthPrice;
    private int yearPrice;
    private String province;
    private String city;
    private String district;
    private String address;
    private String longitude;
    private String latitude;
    private String description;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String adminName;
    private String adminMobile;
    public void setId(String id) {
         this.id = id;
     }
     public String getId() {
         return id;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setNum(int num) {
         this.num = num;
     }
     public int getNum() {
         return num;
     }

    public void setAvaNum(int avaNum) {
         this.avaNum = avaNum;
     }
     public int getAvaNum() {
         return avaNum;
     }

    public void setHourPrice(int hourPrice) {
         this.hourPrice = hourPrice;
     }
     public int getHourPrice() {
         return hourPrice;
     }

    public void setDayPrice(int dayPrice) {
         this.dayPrice = dayPrice;
     }
     public int getDayPrice() {
         return dayPrice;
     }

    public void setMonthPrice(int monthPrice) {
         this.monthPrice = monthPrice;
     }
     public int getMonthPrice() {
         return monthPrice;
     }

    public void setYearPrice(int yearPrice) {
         this.yearPrice = yearPrice;
     }
     public int getYearPrice() {
         return yearPrice;
     }

    public void setProvince(String province) {
         this.province = province;
     }
     public String getProvince() {
         return province;
     }

    public void setCity(String city) {
         this.city = city;
     }
     public String getCity() {
         return city;
     }

    public void setDistrict(String district) {
         this.district = district;
     }
     public String getDistrict() {
         return district;
     }

    public void setAddress(String address) {
         this.address = address;
     }
     public String getAddress() {
         return address;
     }

    public void setLongitude(String longitude) {
         this.longitude = longitude;
     }
     public String getLongitude() {
         return longitude;
     }

    public void setLatitude(String latitude) {
         this.latitude = latitude;
     }
     public String getLatitude() {
         return latitude;
     }

    public void setDescription(String description) {
         this.description = description;
     }
     public String getDescription() {
         return description;
     }

    public void setImage1(String image1) {
         this.image1 = image1;
     }
     public String getImage1() {
         return image1;
     }

    public void setImage2(String image2) {
         this.image2 = image2;
     }
     public String getImage2() {
         return image2;
     }

    public void setImage3(String image3) {
         this.image3 = image3;
     }
     public String getImage3() {
         return image3;
     }

    public void setImage4(String image4) {
         this.image4 = image4;
     }
     public String getImage4() {
         return image4;
     }

    public void setImage5(String image5) {
         this.image5 = image5;
     }
     public String getImage5() {
         return image5;
     }

    public void setAdminName(String adminName) {
         this.adminName = adminName;
     }
     public String getAdminName() {
         return adminName;
     }

    public void setAdminMobile(String adminMobile) {
         this.adminMobile = adminMobile;
     }
     public String getAdminMobile() {
         return adminMobile;
     }

    @NonNull
    @Override
    public String toString() {
        return "CreateParkingResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", num=" + num +
                ", avaNum=" + avaNum +
                ", hourPrice=" + hourPrice +
                ", dayPrice=" + dayPrice +
                ", monthPrice=" + monthPrice +
                ", yearPrice=" + yearPrice +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", address='" + address + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", description='" + description + '\'' +
                ", image1='" + image1 + '\'' +
                ", image2='" + image2 + '\'' +
                ", image3='" + image3 + '\'' +
                ", image4='" + image4 + '\'' +
                ", image5='" + image5 + '\'' +
                ", adminName='" + adminName + '\'' +
                ", adminMobile='" + adminMobile + '\'' +
                '}';
    }
}