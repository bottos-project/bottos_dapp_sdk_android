package com.bottos.botc.sdk.wallet;

import com.bottos.botc.sdk.net.api.RequestCallBackImp;
import com.bottos.botc.sdk.net.request.AccountInfoRequest;
import com.bottos.botc.sdk.net.request.ClaimParamsRequest;
import com.bottos.botc.sdk.net.request.CreateAccountParamsRequest;
import com.bottos.botc.sdk.net.request.NewMsignAccountRequest;
import com.bottos.botc.sdk.net.request.StakeParamsRequest;
import com.bottos.botc.sdk.net.request.TransactionStatusRequest;
import com.bottos.botc.sdk.net.request.TransferParamsRequest;
import com.bottos.botc.sdk.net.request.UnStakeParamsRequest;
import com.bottos.botc.sdk.net.response.AccountInfoResponse;
import com.bottos.botc.sdk.net.response.CommonResponse;
import com.bottos.botc.sdk.net.response.SendTransactionResponse;
import com.bottos.botc.sdk.net.response.TransactionStatusResponse;

import io.github.novacrypto.bip39.Validation.InvalidChecksumException;
import io.github.novacrypto.bip39.Validation.InvalidWordCountException;
import io.github.novacrypto.bip39.Validation.UnexpectedWhiteSpaceException;
import io.github.novacrypto.bip39.Validation.WordNotFoundException;

/**
 * Created by xionglh on 2018/9/4
 */
public interface WalletService {


    String createKeys();

    String createKeystore(String accountName, String password, String privateKey);

    String recoverKeystore(String pwd, String keystore);

    String createMnemonic();

    void validateMnemonic(String mnemonicStr) throws WordNotFoundException, UnexpectedWhiteSpaceException, InvalidChecksumException, InvalidWordCountException, InvalidWordCountException;

     String hroughMnemonicsCreateKeystore(String account,String mnemonicStr,String pwd);

    void getTransactionStatus(TransactionStatusRequest transactionStatusRequest, RequestCallBackImp<CommonResponse<TransactionStatusResponse>> requestCallBackImp);

    void createAccount(CreateAccountParamsRequest createAccountParamsRequest, String send, String privateKey, final RequestCallBackImp<SendTransactionResponse> requestCallBackImp);

    void getAccountInfo(AccountInfoRequest accountInfoRequest, final RequestCallBackImp<AccountInfoResponse> requestCallBackImp);

    void transfer(TransferParamsRequest transferParamsRequest, String send, String privateKey, final RequestCallBackImp<SendTransactionResponse> requestCallBackImp);

    void stake(StakeParamsRequest stakeParamsRequest, String send, String privateKey, final RequestCallBackImp<SendTransactionResponse> requestCallBackImp);

    void unStake(UnStakeParamsRequest unStakeParamsRequest, String send, String privateKey, final RequestCallBackImp<SendTransactionResponse> requestCallBackImp);

    void claim(ClaimParamsRequest claimParamsRequest, String send, String privateKey, final RequestCallBackImp<SendTransactionResponse> requestCallBackImp);

    void newMsignAccount(NewMsignAccountRequest newMsignAccountRequest, String send, String privateKey, final RequestCallBackImp<SendTransactionResponse> requestCallBackImp);


}
