package com.bottos.botc.sdk.wallet;

import com.bottos.botc.sdk.BottosSdkManger;
import com.bottos.botc.sdk.config.ContractConstants;
import com.bottos.botc.sdk.entity.BlockHeight;
import com.bottos.botc.sdk.entity.MsignAuthorInfo;
import com.bottos.botc.sdk.exceptions.BotcError;
import com.bottos.botc.sdk.exceptions.BotcException;
import com.bottos.botc.sdk.net.api.RequestCallBackImp;
import com.bottos.botc.sdk.net.request.ClaimParamsRequest;
import com.bottos.botc.sdk.net.request.CreateAccountParamsRequest;
import com.bottos.botc.sdk.net.request.NewMsignAccountRequest;
import com.bottos.botc.sdk.net.request.StakeParamsRequest;
import com.bottos.botc.sdk.net.request.TransactionStatusRequest;
import com.bottos.botc.sdk.net.request.TransferParamsRequest;
import com.bottos.botc.sdk.net.request.UnStakeParamsRequest;
import com.bottos.botc.sdk.net.response.CommonResponse;
import com.bottos.botc.sdk.net.response.SendTransactionResponse;
import com.bottos.botc.sdk.net.response.TransactionStatusResponse;
import com.bottos.botc.sdk.utils.ArraysUtils;
import com.bottos.botc.sdk.utils.KeystoreKeyCreatTool;
import com.bottos.botc.sdk.utils.Strings;
import com.bottos.botc.sdk.utils.msgpack.MsgPack;

import java.util.List;

/**
 * Created by xionglh on 2018/9/5
 */
public class WalletServiceImp implements WalletService {

    /**
     * Create public-private key
     * @return  Public-private key json data
     */
    @Override
    public String createKeys() {
        return KeystoreKeyCreatTool.createKeys();
    }

    /***
     * To create the Keystore
     * @param accountName Bottos main network account needs to be created
     * @param password KeyStore password
     * @param privateKey The private key
     * @return KeyStore json data
     */
    @Override
    public String createKeystore(String accountName, String password, String privateKey) {
        return KeystoreKeyCreatTool.createBtoKeystore(accountName,password, privateKey);
    }

    /***
     *
     * @param pwd KeyStore password
     * @param keystore  KeyStore
     * @return The private key
     */
    @Override
    public String recoverKeystore(String pwd, String keystore) {
        return KeystoreKeyCreatTool.recoverKeystore(pwd, keystore);
    }

    /**
     *Search for transaction information
     * @param transactionStatusRequest TransactionStatusRequest
     * @param requestCallBackImp Callbacks Object
     */
    @Override
    public void getTransactionStatus(TransactionStatusRequest transactionStatusRequest,RequestCallBackImp<CommonResponse<TransactionStatusResponse>> requestCallBackImp) {
        if (transactionStatusRequest==null) {
            throw new NullPointerException();
        }
        BottosSdkManger.getInstance().getApiWrapper().transactionStatus(transactionStatusRequest,requestCallBackImp);
    }

    /***
     *Create account
     * @param createAccountParamsRequest CreateAccountParamsRequest Object
     * @param send send
     * @param privateKey privateKey
     * @param requestCallBackImp Callbacks Object
     */
    @Override
    public void createAccount(CreateAccountParamsRequest createAccountParamsRequest, String send, String privateKey, RequestCallBackImp<SendTransactionResponse> requestCallBackImp) {
        if (createAccountParamsRequest==null) {
            throw new NullPointerException();
        }
        if (Strings.isEmpty(send)) {
            throw new BotcException(BotcError.ACCOUNT_NAME_ERROR);
        }
        if (Strings.isEmpty(privateKey)) {
            throw new BotcException(BotcError.PRIVATEKEY_EMPTY_ERR);
        }
        BottosSdkManger.getInstance().getBlickChainService().getBlockHeight(new RequestCallBackImp<BlockHeight>() {
            @Override
            public void onNext(BlockHeight blockHeight) {
                long[] arsize = MsgPack.packArraySize(2);
                long[] from = MsgPack.packStr16(createAccountParamsRequest.getName());
                long[] to = MsgPack.packStr16(createAccountParamsRequest.getPubkey());
                long [] param= ArraysUtils.arrayCopylong(arsize, from, to);
                BottosSdkManger.getInstance().getApiWrapper().sendTransaction(blockHeight,send, ContractConstants.CONTRACT_NEW_ACCOUNT,privateKey,param,requestCallBackImp);
            }
        });
    }


    /***
     *Transfer
     * @param transferParamsRequest TransferParamsRequest Object
     * @param send send
     * @param privateKey privateKey
     * @param requestCallBackImp Callbacks Object
     */
    @Override
    public void transfer(TransferParamsRequest transferParamsRequest, String send, String privateKey, RequestCallBackImp<SendTransactionResponse> requestCallBackImp) {
        if (transferParamsRequest==null) {
            throw new NullPointerException();
        }
        if (Strings.isEmpty(send)) {
            throw new BotcException(BotcError.ACCOUNT_NAME_ERROR);
        }
        if (Strings.isEmpty(privateKey)) {
            throw new BotcException(BotcError.PRIVATEKEY_EMPTY_ERR);
        }
        BottosSdkManger.getInstance().getBlickChainService().getBlockHeight(new RequestCallBackImp<BlockHeight>() {
            @Override
            public void onNext(BlockHeight blockHeight) {
                long[] arsize = MsgPack.packArraySize(4);
                long[] from = MsgPack.packStr16(transferParamsRequest.getFrom());
                long[] to = MsgPack.packStr16(transferParamsRequest.getTo());
                long [] value= MsgPack.packUint256(transferParamsRequest.getValue());;
                long []memo=MsgPack.packStr16(transferParamsRequest.getMemo());
                long [] param= ArraysUtils.arrayCopylong(arsize, from, to,value,memo);
                BottosSdkManger.getInstance().getApiWrapper().sendTransaction(blockHeight,send, ContractConstants.CONTRACT_TRABSFER,privateKey,param,requestCallBackImp);
            }
        });
    }


    /****
     *Stake (Including time-space voting)
     * @param stakeParamsRequest StakeParamsRequest
     * @param send  send
     * @param privateKey privateKey
     * @param requestCallBackImp  Callbacks Object
     */
    @Override
    public void stake(StakeParamsRequest stakeParamsRequest, String send, String privateKey, RequestCallBackImp<SendTransactionResponse> requestCallBackImp) {
        if (stakeParamsRequest==null) {
            throw new NullPointerException();
        }
        if (Strings.isEmpty(send)) {
            throw new BotcException(BotcError.ACCOUNT_NAME_ERROR);
        }
        if (Strings.isEmpty(privateKey)) {
            throw new BotcException(BotcError.PRIVATEKEY_EMPTY_ERR);
        }
        BottosSdkManger.getInstance().getBlickChainService().getBlockHeight(new RequestCallBackImp<BlockHeight>() {
            @Override
            public void onNext(BlockHeight blockHeight) {
                long[] arsize = MsgPack.packArraySize(2);
                long[] amount = MsgPack.packUint256(stakeParamsRequest.getAmount());
                long[] target = MsgPack.packStr16(stakeParamsRequest.getTarget());
                long [] param= ArraysUtils.arrayCopylong(arsize, amount, target);
                BottosSdkManger.getInstance().getApiWrapper().sendTransaction(blockHeight,send, ContractConstants.CONTRACT_STAKE,privateKey,param,requestCallBackImp);
            }

        });
    }

    /***
     *UnStake
     * @param unStakeParamsRequest UnStakeParamsRequest
     * @param send send
     * @param privateKey privateKey
     * @param requestCallBackImp Callbacks Object
     */
    @Override
    public void unStake(UnStakeParamsRequest unStakeParamsRequest, String send, String privateKey, RequestCallBackImp<SendTransactionResponse> requestCallBackImp) {
        if (unStakeParamsRequest==null) {
            throw new NullPointerException();
        }
        if (Strings.isEmpty(send)) {
            throw new BotcException(BotcError.ACCOUNT_NAME_ERROR);
        }
        if (Strings.isEmpty(privateKey)) {
            throw new BotcException(BotcError.PRIVATEKEY_EMPTY_ERR);
        }
        BottosSdkManger.getInstance().getBlickChainService().getBlockHeight(new RequestCallBackImp<BlockHeight>() {
            @Override
            public void onNext(BlockHeight blockHeight) {
                long[] arsize = MsgPack.packArraySize(2);
                long[] amount = MsgPack.packUint256(unStakeParamsRequest.getAmount());
                long[] source = MsgPack.packStr16(unStakeParamsRequest.getSource());
                long [] param= ArraysUtils.arrayCopylong(arsize, amount, source);
                BottosSdkManger.getInstance().getApiWrapper().sendTransaction(blockHeight,send, ContractConstants.CONTRACT_UN_STAKE,privateKey,param,requestCallBackImp);
            }

        });
    }

    /**
     * Cash withdrawal after discharge of pledge
     * @param claimParamsRequest ClaimParamsRequest
     * @param send send
     * @param privateKey privateKey
     * @param requestCallBackImp  Callbacks Object
     */
    @Override
    public void claim(ClaimParamsRequest claimParamsRequest, String send, String privateKey, RequestCallBackImp<SendTransactionResponse> requestCallBackImp) {

        if (claimParamsRequest==null) {
            throw new NullPointerException();
        }
        if (Strings.isEmpty(send)) {
            throw new BotcException(BotcError.ACCOUNT_NAME_ERROR);
        }
        if (Strings.isEmpty(privateKey)) {
            throw new BotcException(BotcError.PRIVATEKEY_EMPTY_ERR);
        }
        BottosSdkManger.getInstance().getBlickChainService().getBlockHeight(new RequestCallBackImp<BlockHeight>() {
            @Override
            public void onNext(BlockHeight blockHeight) {
                long[] arsize = MsgPack.packArraySize(1);
                long[] amount = MsgPack.packUint256(claimParamsRequest.getAmount());
                long [] param= ArraysUtils.arrayCopylong(arsize, amount);
                BottosSdkManger.getInstance().getApiWrapper().sendTransaction(blockHeight,send, ContractConstants.CONTRACT_CLAIM,privateKey,param,requestCallBackImp);
            }
        });
    }

    /**
     *Register multi-signing account
     * @param newMsignAccountRequests NewMsignAccountRequest
     * @param send send
     * @param privateKey privateKey
     * @param requestCallBackImp  Callbacks Object
     */
    @Override
    public void newMsignAccount(NewMsignAccountRequest newMsignAccountRequests, String send, String privateKey, RequestCallBackImp<SendTransactionResponse> requestCallBackImp) {
        if (newMsignAccountRequests==null) {
            throw new NullPointerException();
        }
        if (Strings.isEmpty(send)) {
            throw new BotcException(BotcError.ACCOUNT_NAME_ERROR);
        }
        if (Strings.isEmpty(privateKey)) {
            throw new BotcException(BotcError.PRIVATEKEY_EMPTY_ERR);
        }
        BottosSdkManger.getInstance().getBlickChainService().getBlockHeight(new RequestCallBackImp<BlockHeight>() {
            @Override
            public void onNext(BlockHeight blockHeight) {
                List<MsignAuthorInfo> authorList ;
                authorList = newMsignAccountRequests.getAuthority();
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                if (null != authorList && authorList.size() > 0) {
                    for (MsignAuthorInfo authorInfo : authorList) {
                        if (null != authorInfo) {
                            String authorJson = "{\"author_account\":\"" + authorInfo.getAuthor_account() + "\",\"weight\":" + authorInfo.getWeight() + "}";
                            sb.append(authorJson).append(",");
                        }
                    }
                }
                String authorListJson = sb.substring(0, sb.length() - 1) + "]";
                long[] arsize = MsgPack.packArraySize(3);
                long[] authority = MsgPack.packStr16(authorListJson);
                long[] account = MsgPack.packStr16(newMsignAccountRequests.getAccount());
                long[] threshold = MsgPack.packUint32(Long.valueOf(newMsignAccountRequests.getThreshold()));
                long[] param = ArraysUtils.arrayCopylong(arsize, account, authority, threshold);
                BottosSdkManger.getInstance().getApiWrapper().sendTransaction(blockHeight,send, ContractConstants.CONTRACT_NEW_MSIGIN_ACCOUNT,privateKey,param,requestCallBackImp);
            }
        });
    }
}
