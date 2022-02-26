package com.xiyou3g.select.parking.bean;

import android.graphics.Bitmap;

public class CreateInformation {

    private String name;
    private int number;
    private int price;
    private String briefIntroduction;
    private Bitmap bitmap;

    public CreateInformation(String name, int number, int price, String briefIntroduction, Bitmap bitmap) {
        this.name = name;
        this.number = number;
        this.price = price;
        this.briefIntroduction = briefIntroduction;
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBriefIntroduction() {
        return briefIntroduction;
    }

    public void setBriefIntroduction(String briefIntroduction) {
        this.briefIntroduction = briefIntroduction;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}