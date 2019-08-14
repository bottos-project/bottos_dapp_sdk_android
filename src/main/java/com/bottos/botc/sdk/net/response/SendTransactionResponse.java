package com.bottos.botc.sdk.net.response;

import com.bottos.botc.sdk.entity.TradeInfo;

/**
 * Created by xionglh on 2019/8/13
 */
public class SendTransactionResponse {

    private TradeInfo tradeInfo;

    private String trx_hash;

    public TradeInfo getTradeInfo() {
        return tradeInfo;
    }

    public void setTradeInfo(TradeInfo tradeInfo) {
        this.tradeInfo = tradeInfo;
    }

    public String getTrx_hash() {
        return trx_hash;
    }

    public void setTrx_hash(String trx_hash) {
        this.trx_hash = trx_hash;
    }

}
