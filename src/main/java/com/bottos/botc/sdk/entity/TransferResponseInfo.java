package com.bottos.botc.sdk.entity;

/**
 * Create by xionglh on 2019/6/12
 */
public class TransferResponseInfo {

    private String account;

    private String value;

    private String from;

    private String to;

    private  String memo;

    private String hax;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getHax() {
        return hax;
    }

    public void setHax(String hax) {
        this.hax = hax;
    }
}
