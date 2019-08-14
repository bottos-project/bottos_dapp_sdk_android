package com.bottos.botc.sdk.utils;

import android.util.Base64;

/**
 * Create by xionglh on 2019/6/11
 */
public class Base64Utils {


    public static String encodeToString(String str) {

        return Base64.encodeToString(str.getBytes(), Base64.DEFAULT);

    }

    public static String unEncodeToString(String base64Str) {
        return new String(Base64.decode(base64Str, Base64.DEFAULT));
    }

}
