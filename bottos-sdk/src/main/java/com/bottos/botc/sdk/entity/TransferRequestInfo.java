package com.bottos.botc.sdk.entity;


import java.io.Serializable;

/**
 * Create by xionglh on 2019/6/11
 */
public class TransferRequestInfo implements Serializable {

    private String from;

    private String to;

    private String value;

    private String memo;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
