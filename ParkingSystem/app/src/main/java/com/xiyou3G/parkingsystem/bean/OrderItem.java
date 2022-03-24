package com.xiyou3G.parkingsystem.bean;

import androidx.annotation.NonNull;

public class OrderItem {

    private String address;
    private String startTime;
    private String endTime;
    private Double price;
    private int status;
    private String ordersId;
    private int type;

    public OrderItem(String address, String startTime, String endTime, Double price, int status, String ordersId, int type) {
        this.address = address;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.status = status;
        this.ordersId = ordersId;
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(String ordersId) {
        this.ordersId = ordersId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @NonNull
    @Override
    public String toString() {
        return "OrderItem{" +
                "address='" + address + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", price=" + price +
                ", status=" + status +
                ", ordersId='" + ordersId + '\'' +
                ", type=" + type +
                '}';
    }
}
