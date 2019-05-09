package com.project.wisdomfirecontrol.firecontrol.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.multidex.MultiDex;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.mvp_0726.common.base.MvpApplication;
import com.mvp_0726.project_0726.file.ExceptionHandler;
import com.project.wisdomfirecontrol.firecontrol.citySelector.db.DBManager;
import com.project.wisdomfirecontrol.firecontrol.ui.Tctwebview.APIWebviewTBS;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * @author LMX
 */
public class MyApplication extends MvpApplication {

    @SuppressLint("StaticFieldLeak")
    public static MyApplication instance;
    APIWebviewTBS mAPIWebviewTBS;
    private DBManager dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //个人封装，针对升级----开始
        if (mAPIWebviewTBS == null) {
            mAPIWebviewTBS = APIWebviewTBS.getAPIWebview();
            mAPIWebviewTBS.initTbs(getApplicationContext());
        }
        ExceptionHandler.getInstance().initConfig(this);
        //个人封装，针对升级----结束

        //导入数据库
        dbHelper = new DBManager(this);
        dbHelper.openDatabase();
        /*科大讯飞*/
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5b72806d");
        ZXingLibrary.initDisplayOpinion(this);

    }

    public static MyApplication getInstance() {
        if (instance == null) {
            synchronized (MyApplication.class) {
                if (instance == null) {
                    instance = new MyApplication();
                }
            }
        }
        return instance;
    }

//        // 短信验证登录
//        SMSSDK.initSDK(this, "1c32a690937b0",
//                "490f625347ea37b1ff872bc25e05aef7");
//
//        // 初始化极光SDK
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 检测网络是否可用
     *
     * @return
     */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }
}
