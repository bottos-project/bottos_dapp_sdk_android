package com.bottos.botc.sdk.net.api;

import com.bottos.botc.sdk.config.Configure;
import com.bottos.botc.sdk.config.Constants;
import com.bottos.botc.sdk.entity.BlockHeight;
import com.bottos.botc.sdk.entity.RequestDataSign;
import com.bottos.botc.sdk.net.request.TransactionStatusRequest;
import com.bottos.botc.sdk.net.response.CommonResponse;
import com.bottos.botc.sdk.net.response.SendTransactionResponse;
import com.bottos.botc.sdk.net.response.TransactionStatusResponse;
import com.bottos.botc.sdk.utils.SignaturedFetchParamUtils;
import com.bottos.botc.sdk.utils.crypto.CryptTool;

/**
 * Created by xionglh on 2018/9/4
 */

@SuppressWarnings("unchecked")
public class ApiWrapper extends RetrofitClient {

    public void requestBlockHeight(RequestCallBackImp<BlockHeight> requestCallBackImp) {
       applySchedulers(getService(Configure.getBaseNetUrl(),ApiService.class).requestBlockHeight()).subscribe(newMySubscriber(requestCallBackImp));
    }

    public void transactionStatus(TransactionStatusRequest transactionStatusRequest,RequestCallBackImp<CommonResponse<TransactionStatusResponse>> requestCallBackImp) {
        applySchedulersRestful(getService(Configure.getBaseNetUrl(), ApiService.class).transactionStatus(toRequestBody(transactionStatusRequest))).subscribe(newMySubscriber(requestCallBackImp));
    }

    public void sendTransaction(BlockHeight blockHeight, String send, String method, String privateKey, long[] params, RequestCallBackImp<SendTransactionResponse> requestCallBackImp){
        RequestDataSign requestDataSign = new RequestDataSign();
        requestDataSign.setVersion(blockHeight.getHead_block_version());
        requestDataSign.setCursor_num(blockHeight.getHead_block_num());
        requestDataSign.setCursor_label(blockHeight.getCursor_label());
        requestDataSign.setLifetime(blockHeight.getHead_block_time() + 100);
        requestDataSign.setSender(send);
        requestDataSign.setMethod(method);
        requestDataSign.setContract(Constants.SEND_TRANSACTION_CONTRACT);
        requestDataSign.setSig_alg(1);
        requestDataSign.setParam(CryptTool.getHex16(params));
        requestDataSign.setSignature(SignaturedFetchParamUtils.getSignaturedFetchParam(requestDataSign,params,privateKey, blockHeight.getChain_id()));
//        RequestDataSign.getSignaturedFetchParam(sendTransactionRequest, params, privateKey, blockHeight.getChain_id());
        applySchedulers(getService(Configure.getBaseNetUrl(),ApiService.class).transactionSend(toRequestBody(requestDataSign))).subscribe( newMySubscriber(requestCallBackImp));
    }

}
