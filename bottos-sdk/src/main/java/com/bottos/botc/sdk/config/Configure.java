package com.bottos.botc.sdk.config;

/**
 * Created by xionglh on 2018/9/4
 */
public class Configure {

    public static String BASE_NET_URL = "http://servicenode1.chainbottos.com:8689/";

    public static boolean DEBUG_MODE = true;

    public static void  init(String baseUrl){
        BASE_NET_URL=baseUrl;
    }

    public static String getBaseNetUrl() {
        return BASE_NET_URL;
    }

    public static void setBaseNetUrl(String baseNetUrl) {
        BASE_NET_URL = baseNetUrl;
    }

    public static boolean isDebugMode() {
        return DEBUG_MODE;
    }

    public static void setDebugMode(boolean debugMode) {
        DEBUG_MODE = debugMode;
    }
}
