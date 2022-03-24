package com.xiyou3G.parkingsystem.bean;

import androidx.annotation.NonNull;

public class RentItem {

    private double price;
    private String startTime;
    private String endTime;
    private int status;
    private String orderId;

    public RentItem(double price, String startTime, String endTime, int status, String orderId) {
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
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
