package com.bottos.botc.sdk.net.request;

/**
 * Created by xionglh on 2019/8/13
 */
public class StakeParamsRequest {

    private String amount;

    /***
     *space   or  time   or  vote
     */
    private String target;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
