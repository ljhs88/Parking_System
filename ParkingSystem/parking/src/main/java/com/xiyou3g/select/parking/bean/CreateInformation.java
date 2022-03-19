package com.xiyou3g.select.parking.bean;

import android.graphics.Bitmap;

public class CreateInformation {

    private int status;
    private String name;
    private String ownerMobile;
    private String ownerNum;
    private int price = -1;
    private String adminName;
    private String adminMobile;
    private int finePrice = -1;
    private String address;

    private Bitmap bitmap;

    public CreateInformation(int status, String name, String ownerMobile, String ownerNum, int price, String adminName, String adminMobile, int finePrice, String address, Bitmap bitmap) {
        this.status = status;
        this.name = name;
        this.ownerMobile = ownerMobile;
        this.ownerNum = ownerNum;
        this.price = price;
        this.adminName = adminName;
        this.adminMobile = adminMobile;
        this.finePrice = finePrice;
        this.address = address;
        this.bitmap = bitmap;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerMobile() {
        return ownerMobile;
    }

    public void setOwnerMobile(String ownerMobile) {
        this.ownerMobile = ownerMobile;
    }

    public String getOwnerNum() {
        return ownerNum;
    }

    public void setOwnerNum(String ownerNum) {
        this.ownerNum = ownerNum;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminMobile() {
        return adminMobile;
    }

    public void setAdminMobile(String adminMobile) {
        this.adminMobile = adminMobile;
    }

    public int getFinePrice() {
        return finePrice;
    }

    public void setFinePrice(int finePrice) {
        this.finePrice = finePrice;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "CreateInformation{" +
                "status=" + status +
                ", name='" + name + '\'' +
                ", ownerMobile='" + ownerMobile + '\'' +
                ", ownerNum='" + ownerNum + '\'' +
                ", price=" + price +
                ", adminName='" + adminName + '\'' +
                ", adminMobile='" + adminMobile + '\'' +
                ", finePrice=" + finePrice +
                ", bitmap=" + bitmap +
                '}';
    }
}