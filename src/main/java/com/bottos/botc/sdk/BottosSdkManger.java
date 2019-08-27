package com.bottos.botc.sdk;

import com.bottos.botc.sdk.blockchain.BlockChainService;
import com.bottos.botc.sdk.blockchain.BlockChainServiceImp;
import com.bottos.botc.sdk.entity.BlockHeight;
import com.bottos.botc.sdk.net.api.ApiWrapper;
import com.bottos.botc.sdk.net.api.RequestCallBackImp;
import com.bottos.botc.sdk.wallet.WalletService;
import com.bottos.botc.sdk.wallet.WalletServiceImp;

/**
 * Created by xionglh on 2018/9/6
 */
public class BottosSdkManger {

    private static BottosSdkManger botcManger;

    private ApiWrapper mApiWrapper;

    private BottosSdkManger() {
        mApiWrapper = new ApiWrapper();
    }

    public ApiWrapper getApiWrapper() {
        return mApiWrapper;
    }

    public synchronized static BottosSdkManger getInstance() {
        if (botcManger == null) {
            botcManger = new BottosSdkManger();
        }
        return botcManger;
    }

    public BlockChainService getBlickChainService() {
        return new BlockChainServiceImp();
    }

    public WalletService getWalletService() {
        return new WalletServiceImp();
    }


}
