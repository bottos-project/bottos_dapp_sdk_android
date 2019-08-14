package com.bottos.botc.sdk.entity;

import java.io.Serializable;

/**
 * Create by xionglh on 2019/6/11
 */
public class DappParamsInfo implements Serializable {


    private DappInfo dapp;

    private String type;

    private DappParamsInfoData data;

    public DappInfo getDapp() {
        return dapp;
    }

    public void setDapp(DappInfo dapp) {
        this.dapp = dapp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DappParamsInfoData getData() {
        return data;
    }

    public void setData(DappParamsInfoData data) {
        this.data = data;
    }

    public static class DappParamsInfoData<T> implements Serializable {

        private String contract;

        private String sender;

        private String method;

        private WalletBTOFile keystore;

        private T param;

        public WalletBTOFile getKeystore() {
            return keystore;
        }

        public void setKeystore(WalletBTOFile keystore) {
            this.keystore = keystore;
        }

        public T getParam() {
            return param;
        }

        public void setParam(T param) {
            this.param = param;
        }

        public String getContract() {
            return contract;
        }

        public void setContract(String contract) {
            this.contract = contract;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }
    }
}
