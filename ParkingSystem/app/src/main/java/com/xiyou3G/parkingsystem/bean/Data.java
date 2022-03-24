package com.xiyou3G.parkingsystem.bean;

import java.util.Date;

public class Data {

    private String id;
    private String proId;
    private String posId;
    private String address;
    private int posType;
    private double posPrice;
    private String userId;
    private Date car;
    private Date startTime;
    private Date endTime;
    private String orderTime;
    private int eleNum;
    private double orderPrice;
    private int subPrice;
    private int finePrice;
    private double payPrice;
    private String payType;
    private String payId;
    private String payTime;
    private int ispay;
    private int iscancel;
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }
    public String getProId() {
        return proId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }
    public String getPosId() {
        return posId;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }

    public void setPosType(int posType) {
        this.posType = posType;
    }
    public int getPosType() {
        return posType;
    }

    public void setPosPrice(double posPrice) {
        this.posPrice = posPrice;
    }
    public double getPosPrice() {
        return posPrice;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }

    public void setCar(Date car) {
        this.car = car;
    }
    public Date getCar() {
        return car;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public Date getStartTime() {
        return startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public Date getEndTime() {
        return endTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
    public String getOrderTime() {
        return orderTime;
    }

    public void setEleNum(int eleNum) {
        this.eleNum = eleNum;
    }
    public int getEleNum() {
        return eleNum;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }
    public double getOrderPrice() {
        return orderPrice;
    }

    public void setSubPrice(int subPrice) {
        this.subPrice = subPrice;
    }
    public int getSubPrice() {
        return subPrice;
    }

    public void setFinePrice(int finePrice) {
        this.finePrice = finePrice;
    }
    public int getFinePrice() {
        return finePrice;
    }

    public void setPayPrice(double payPrice) {
        this.payPrice = payPrice;
    }
    public double getPayPrice() {
        return payPrice;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
    public String getPayType() {
        return payType;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }
    public String getPayId() {
        return payId;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }
    public String getPayTime() {
        return payTime;
    }

    public void setIspay(int ispay) {
        this.ispay = ispay;
    }
    public int getIspay() {
        return ispay;
    }

    public void setIscancel(int iscancel) {
        this.iscancel = iscancel;
    }
    public int getIscancel() {
        return iscancel;
    }

}