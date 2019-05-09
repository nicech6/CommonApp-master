package com.mvp_0726.common.view.webview;

import android.os.Bundle;

/**
 * Created by seven
 * on 2018/5/28
 * h5控制android跳转之类的事件
 */

public interface H5Control {
    void H5ControlAndroidEvent(String url, Bundle bundle);
    void H5ControlAndroidEvent();
    void H5ControlAndroidEvent(String el);
    void H5ControlAndroidDownloadFile(String url);
}
