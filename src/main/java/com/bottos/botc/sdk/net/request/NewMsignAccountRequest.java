package com.bottos.botc.sdk.net.request;

import com.bottos.botc.sdk.entity.MsignAuthorInfo;

import java.util.List;

/**
 * Created by xionglh on 2019/8/13
 */
public class NewMsignAccountRequest {

    private List<MsignAuthorInfo> authority;

    private String account;

    private String threshold;

    public List<MsignAuthorInfo> getAuthority() {
        return authority;
    }

    public void setAuthority(List<MsignAuthorInfo> authority) {
        this.authority = authority;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }
}
