package com.xiyou3g.baseapplication.eventbus;

public class EventMessage {

    String account;

    public EventMessage(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

}
