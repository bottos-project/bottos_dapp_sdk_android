package com.bottos.botc.sdk.entity;

import java.io.Serializable;

/**
 * Create by xionglh on 2019/6/11
 */
public class WalletResponseInfo<T> implements Serializable {

    private int code;

    private String function;

    private String message;

    private String memo="";

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
