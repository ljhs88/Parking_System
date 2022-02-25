package com.xiyou3g.select.customer.register.bean;

public class LoginData {

    private String mobile;
    private String smsCode;

    public LoginData(String mobile, String smsCode) {
        this.mobile = mobile;
        this.smsCode = smsCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\t\"mobile\":\""+ mobile + "\",\n" +
                "\t\"smsCode\":\"" + smsCode + "\"\n" +
                "}";
    }
}
