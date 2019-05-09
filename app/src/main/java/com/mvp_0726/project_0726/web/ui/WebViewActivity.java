package com.mvp_0726.project_0726.web.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.mvp_0726.common.base.codereview.BaseActivity;
import com.project.wisdomfirecontrol.R;
import com.tencent.smtt.sdk.WebView;

public class WebViewActivity extends BaseActivity {


    private WebView webView;
    private ProgressBar mWebProgressbar;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        webView = findViewById(R.id.webview);
        webView.requestFocusFromTouch();
        mWebProgressbar = findViewById(R.id.web_progressbar);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setLisenter() {

    }

    @Override
    protected void widgetClick(View v) {

    }
}
