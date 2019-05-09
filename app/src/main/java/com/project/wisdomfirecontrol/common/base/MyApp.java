package com.project.wisdomfirecontrol.common.base;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * 应用程序上下文对象，常作一些初始化操作
 *
 * @author LMX
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Global.init(this);
        com.mvp_0726.common.utils.Global.init(this);
//      百度地图
        SDKInitializer.initialize(this);
        // new DBTest().test();
    }
}
