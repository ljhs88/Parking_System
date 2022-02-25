/**
  * Copyright 2022 bejson.com 
  */
package com.xiyou3g.select.customer.register.bean;
import java.util.Date;

/**
 * Auto-generated: 2022-02-24 10:21:40
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Data {

    private String id;
    private String mobile;
    private String nickname;
    private String face;
    private int sex;
    private Date birthday;
    private String country;
    private String province;
    private String city;
    private String district;
    private String description;
    private String bgImg;
    private Date createdTime;
    private Date updatedTime;
    private String userToken;
    public void setId(String id) {
         this.id = id;
     }
     public String getId() {
         return id;
     }

    public void setMobile(String mobile) {
         this.mobile = mobile;
     }
     public String getMobile() {
         return mobile;
     }

    public void setNickname(String nickname) {
         this.nickname = nickname;
     }
     public String getNickname() {
         return nickname;
     }

    public void setFace(String face) {
         this.face = face;
     }
     public String getFace() {
         return face;
     }

    public void setSex(int sex) {
         this.sex = sex;
     }
     public int getSex() {
         return sex;
     }

    public void setBirthday(Date birthday) {
         this.birthday = birthday;
     }
     public Date getBirthday() {
         return birthday;
     }

    public void setCountry(String country) {
         this.country = country;
     }
     public String getCountry() {
         return country;
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

    public void setDescription(String description) {
         this.description = description;
     }
     public String getDescription() {
         return description;
     }

    public void setBgImg(String bgImg) {
         this.bgImg = bgImg;
     }
     public String getBgImg() {
         return bgImg;
     }

    public void setCreatedTime(Date createdTime) {
         this.createdTime = createdTime;
     }
     public Date getCreatedTime() {
         return createdTime;
     }

    public void setUpdatedTime(Date updatedTime) {
         this.updatedTime = updatedTime;
     }
     public Date getUpdatedTime() {
         return updatedTime;
     }

    public void setUserToken(String userToken) {
         this.userToken = userToken;
     }
     public String getUserToken() {
         return userToken;
     }

    @Override
    public String toString() {
        return "Data{" +
                "id='" + id + '\'' +
                ", mobile='" + mobile + '\'' +
                ", nickname='" + nickname + '\'' +
                ", face='" + face + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", description='" + description + '\'' +
                ", bgImg='" + bgImg + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", userToken='" + userToken + '\'' +
                '}';
    }
}