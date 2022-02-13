package com.xiyou3g.information.bean;

import org.litepal.crud.LitePalSupport;

public class personal_inf extends LitePalSupport {

    private int id;
    private String male;
    private String phone_member;
    private String car_member;
    private String car_age;
    private String location;
    private String person_sign;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMale() {
        return male;
    }

    public void setMale(String male) {
        this.male = male;
    }

    public String getPhone_member() {
        return phone_member;
    }

    public void setPhone_member(String phone_member) {
        this.phone_member = phone_member;
    }

    public String getCar_member() {
        return car_member;
    }

    public void setCar_member(String car_member) {
        this.car_member = car_member;
    }

    public String getCar_age() {
        return car_age;
    }

    public void setCar_age(String car_age) {
        this.car_age = car_age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPerson_sign() {
        return person_sign;
    }

    public void setPerson_sign(String person_sign) {
        this.person_sign = person_sign;
    }

    @Override
    public String toString() {
        return "personal_inf{" +
                "id=" + id +
                ", male='" + male + '\'' +
                ", phone_member='" + phone_member + '\'' +
                ", car_member='" + car_member + '\'' +
                ", car_age='" + car_age + '\'' +
                ", location='" + location + '\'' +
                ", person_sign='" + person_sign + '\'' +
                '}';
    }
}
