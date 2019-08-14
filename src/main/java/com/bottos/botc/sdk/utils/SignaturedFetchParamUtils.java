package com.bottos.botc.sdk.utils;

import com.bottos.botc.sdk.entity.RequestDataSign;
import com.bottos.botc.sdk.utils.crypto.CryptTool;
import com.bottos.botc.sdk.utils.crypto.Numeric;
import com.bottos.botc.sdk.utils.msgpack.MsgPack;

/**
 * Created by xionglh on 2019/8/12
 */
public class SignaturedFetchParamUtils {


    public static long[] messageProtoEncode(RequestDataSign sendTransactionRequest, long[] param) {
        long[] pArraySize = MsgPack.packArraySize(9);
        long[] pVersion = MsgPack.packUint32(sendTransactionRequest.getVersion());
        long[] pCursorNum = MsgPack.packUint64(sendTransactionRequest.getCursor_num());
        long[] pCursorLabel = MsgPack.packUint32(sendTransactionRequest.getCursor_label());
        long[] pLifeTime = MsgPack.packUint64(sendTransactionRequest.getLifetime());
        long[] pSender = MsgPack.packStr16(sendTransactionRequest.getSender());
        long[] pContract = MsgPack.packStr16(sendTransactionRequest.getContract());
        long[] pMethod = MsgPack.packStr16(sendTransactionRequest.getMethod());
        long[] pParam = MsgPack.packBin16(param);
        long[] uint8Param = new long[pParam.length];
        for (int i = 0; i < pParam.length; i++) {
            uint8Param[i] = MsgPack.Uint8Array(pParam[i]);
        }
        long[] pSigalg = MsgPack.packUint32(sendTransactionRequest.getSig_alg());
        return ArraysUtils.arrayCopylong(pArraySize, pVersion, pCursorNum, pCursorLabel, pLifeTime, pSender, pContract, pMethod, uint8Param, pSigalg);
    }


    public static String getSignaturedFetchParam(RequestDataSign sendTransactionRequest, long[] params, String privateKeyStr, String chain) {
        long[] encodeBuf1 = messageProtoEncode(sendTransactionRequest, params);
        byte[] ss = Numeric.hexStringToByteArray(chain);
        long[] encodeBuf2 = new long[ss.length];
        for (int i = 0; i < ss.length; i++) {
            encodeBuf2[i] = MsgPack.Uint8Array((long) ss[i]);
        }
        long[] newMsgProto = ArraysUtils.arrayCopylong(encodeBuf1, encodeBuf2);
         return CryptTool.getSignaturedParam(newMsgProto, privateKeyStr);
    }
}
