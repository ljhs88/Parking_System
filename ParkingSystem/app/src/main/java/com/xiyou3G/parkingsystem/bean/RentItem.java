package com.xiyou3G.parkingsystem.bean;

import androidx.annotation.NonNull;

import java.util.Date;

public class RentItem {

    private double price;
    private Date startTime;
    private Date endTime;
    private int status;
    private String orderId;

    public RentItem(double price, Date startTime, Date endTime, int status, String orderId) {
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.orderId = orderId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @NonNull

    @Override
    public String toString() {
        return "RentItem{" +
                "price=" + price +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", status=" + status +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
