package com.xiyou3g.select.pay.bean;
import java.util.Date;

public class Data {

    private String id;
    private String orderId;
    private String userId;
    private String userWalletId;
    private String proId;
    private String proWalletId;
    private double payPrice;
    private int payType;
    private Date payTime;
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

    public void setUserWalletId(String userWalletId) {
        this.userWalletId = userWalletId;
    }
    public String getUserWalletId() {
        return userWalletId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }
    public String getProId() {
        return proId;
    }

    public void setProWalletId(String proWalletId) {
        this.proWalletId = proWalletId;
    }
    public String getProWalletId() {
        return proWalletId;
    }

    public void setPayPrice(double payPrice) {
        this.payPrice = payPrice;
    }
    public double getPayPrice() {
        return payPrice;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }
    public int getPayType() {
        return payType;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
    public Date getPayTime() {
        return payTime;
    }

    public void setIscancel(int iscancel) {
        this.iscancel = iscancel;
    }
    public int getIscancel() {
        return iscancel;
    }

}