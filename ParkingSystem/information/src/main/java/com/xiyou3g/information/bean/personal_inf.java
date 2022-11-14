package com.xiyou3g.information.bean;

import android.graphics.Bitmap;

public class personal_inf  {

    private int id;
    private String name;
    private String male;
    private String phone_member;
    private String birthday;
    private String location;
    private String person_sign;
    private String head_bitmap;

    @Override
    public String toString() {
        return "personal_inf{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", male='" + male + '\'' +
                ", phone_member='" + phone_member + '\'' +
                ", location='" + location + '\'' +
                ", person_sign='" + person_sign + '\'' +
                ", head_bitmap='" + head_bitmap + '\'' +
                '}';
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getHead_bitmap() {
        return head_bitmap;
    }

    public void setHead_bitmap(String head_bitmap) {
        this.head_bitmap = head_bitmap;
    }
}
