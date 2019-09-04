package com.bottos.botc.sdk.net.api;

import com.bottos.botc.sdk.entity.BlockHeight;
import com.bottos.botc.sdk.net.response.CommonResponse;
import com.bottos.botc.sdk.net.response.SendTransactionResponse;
import com.bottos.botc.sdk.net.response.TransactionStatusResponse;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xionglh on 2018/9/4
 */
public interface ApiService {

    @GET("/v1/block/height")
    Observable<CommonResponse<BlockHeight>> requestBlockHeight();

    @POST("/v1/transaction/send")
    Observable<CommonResponse<SendTransactionResponse>> transactionSend(@Body RequestBody requestBody);

    @POST("/v1/transaction/status")
    Observable<CommonResponse<TransactionStatusResponse>> transactionStatus(@Body RequestBody requestBody);


}
