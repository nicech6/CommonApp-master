package com.mvp_0726.project_0726.home.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mvp_0726.common.base.codereview.BaseActivity;
import com.mvp_0726.common.utils.StatusBarUtil;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.firecontrol.base.MyApplication;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.WebViewUtils;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by Administrator on 2018/4/12.
 * 系统设置
 */

public class TestActivity extends BaseActivity {
    private TextView tv_ulr;

    private WebViewUtils webViewUtil;
    private ProgressBar mWebProgressbar;
    private WebSettings webSettings;
    private WebView webView;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//竖屏
        View view = findViewById(R.id.space);

        view.setBackground(mActivity.getResources().getDrawable(R.drawable.title_toolbar_bg_blue));
        StatusBarUtil.setFadeStatusBarHeight(mActivity, view);
        tv_title.setText("测试");
        tv_ulr = findViewById(R.id.tv_ulr);
        webView = findViewById(R.id.webview);
        mWebProgressbar = findViewById(R.id.web_progressbar);
    }

    @Override
    protected void setLisenter() {
        tv_ulr.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData() {
        loadWebH5("http://10.0.0.66:8080/zgbd_hw/zhhw.html");
    }

    @Override
    protected void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ulr:
                tv_ulr();
                break;
        }
    }

    private void tv_ulr() {
        loadWebH5("http://10.0.0.66:8080/zgbd_hw/web/videoobj/videomonitor.html");
    }

    private void loadWebH5(String SERVICE_URL) {
        boolean networkConnected = MyApplication.getInstance().isNetworkConnected();
        if (!networkConnected) {
            showErrorToast("网络不可用，请检查!");
            return;
        }
        //自适应屏幕
        if (webSettings == null) {
            webSettings = webView.getSettings();
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setUseWideViewPort(true);
        }
        if (TextUtils.isEmpty(SERVICE_URL)) {
            return;
        }
        Log.d("tag", "loadWebH5: " + SERVICE_URL);

        if (webViewUtil == null) {
            webViewUtil = new WebViewUtils(TestActivity.this, SERVICE_URL, webView, mWebProgressbar);
            webViewUtil.initWebView();
        } else {
            webViewUtil.initWebView(SERVICE_URL, webView, mWebProgressbar);
        }
    }

    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webView == null) {
            return false;
        }
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        return false;
    }

    @Override
    public void onPause() {
        if (webView != null) {
            webView.reload();
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (webView != null) {
            webView.reload();
        }
    }

}
