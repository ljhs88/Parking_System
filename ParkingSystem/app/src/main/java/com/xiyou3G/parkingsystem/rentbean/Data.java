package com.xiyou3G.parkingsystem.rentbean;

import java.util.Date;


public class Data {

    private String id;
    private String orderId;
    private String userId;
    private String proId;
    private Date startTime;
    private Date endTime;
    private double payPrice;
    private int ispay;
    private int iscancel;
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getOrderId() {
        return orderId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }
    public String getProId() {
        return proId;
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

    public void setPayPrice(double payPrice) {
        this.payPrice = payPrice;
    }
    public double getPayPrice() {
        return payPrice;
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

    @Override
    public String toString() {
        return "Data{" +
                "id='" + id + '\'' +
                ", orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", proId='" + proId + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", payPrice=" + payPrice +
                ", ispay=" + ispay +
                ", iscancel=" + iscancel +
                '}';
    }
}
