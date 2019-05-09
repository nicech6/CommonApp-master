package com.mvp_0726.common.base;

import android.annotation.SuppressLint;
import android.content.Context;

import com.mvp_0726.common.network.ApiRetrofit;
import com.mvp_0726.common.utils.RxTool;
import com.project.wisdomfirecontrol.common.base.MyApp;


/**
 * Created  on 2018-01-04.
 */

public class MvpApplication extends MyApp {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static boolean isDebug = true;//true 玩Android flase 百度

    public MvpApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        RxTool.init(this);
        ApiRetrofit.getApiRetrofit();
//        MobSDK.init(this, "262adcdbafad0", "c7ee79a0a5971f6fbd0b20f2af152af2");
    }

    public static Context getContext() {
        return context;
    }

    public static boolean getIsDebug() {
        return isDebug;
    }

    public static void setDebug(boolean debug) {
        isDebug = debug;
    }
}
