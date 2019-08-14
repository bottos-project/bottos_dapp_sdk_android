package com.bottos.botc.sdk.net.request;

/**
 * Created by xionglh on 2019/8/13
 */
public class TransferParamsRequest {
    
    private String 	from;
    private String  to;
    private String  value;
    private String  memo;

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
