package com.bottos.botc.sdk.utils.crypto;

/**
 * Created by xionglh on 2018/9/12
 */
public class CryptTool {

    public static String getSignaturedParam(long[] buf, String privateKeyStr) {
        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hexStr = Long.toHexString(buf[i]);
            hexStr = hexStr.length() == 1 ? "0" + hexStr : hexStr;
            hex.append(hexStr);
        }
        byte[] hass = Sha256Hash.hash(hex.toString().getBytes());
        ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(privateKeyStr));
        ECDSASignature signature = ecKeyPair.sign(hass);
        StringBuilder r = new StringBuilder(signature.r.toString(16));
        StringBuilder s = new StringBuilder(signature.s.toString(16));

        for (int i = 0; i < 64 - r.length(); i++) {
            r.insert(0, "0");
        }
        for (int i = 0; i < 64 - s.length(); i++) {
            s.insert(0, "0");
        }
        return r.toString() + s.toString();
    }

    public static String getHex16(long[] buf) {
        String hex = "";
        for (int i = 0; i < buf.length; i++) {
            String hexStr = Long.toHexString(buf[i]);
            hexStr = hexStr.length() == 1 ? "0" + hexStr : hexStr;
            hex += hexStr;
        }
        return hex;
    }

}
