package com.mvp_0726.common.network;


import com.mvp_0726.common.base.MvpApplication;

/**
 * Created  on 2018-03-20.
 */

public class NetworkUrl {

    public static final String ANDROID_TEST_SERVICE = "http://111.231.243.103:8090/fireControl/";
    public static final String ANDROID_DENG_SERVICE = "http://10.0.0.31:8080/";//邓超";
    public static final String ANDROID_TIAN_SERVICE = "http://10.0.0.50:8080/";//邓超";
    public static final String ANDROID_BAIDU_SERVICE = "http://111.231.243.103:8090/fireControl/";
    private static boolean sIsDebug;

    static String getNetWorkName() {
        sIsDebug = MvpApplication.getIsDebug();
        if (sIsDebug) {
            return ANDROID_BAIDU_SERVICE;
        } else {
            return ANDROID_TEST_SERVICE;
        }
    }

    public static String getNetWorkUrl() {
        sIsDebug = MvpApplication.getIsDebug();
        if (sIsDebug) {
            return ANDROID_TEST_SERVICE;
        } else {
            return ANDROID_BAIDU_SERVICE;
        }
    }
}
