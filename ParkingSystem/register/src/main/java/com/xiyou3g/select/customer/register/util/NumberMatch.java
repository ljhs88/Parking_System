package com.xiyou3g.select.customer.register.util;

public class NumberMatch {

    public static boolean number (String phone_number) {
        return phone_number.matches("^1(3[0-9]|5[0-3,5-9]|7[1-3,5-8]|8[0-9])\\d{8}$");
    }

}