package com.project.wisdomfirecontrol.firecontrol.ui.activity_unit;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.base.UserInfo;
import com.project.wisdomfirecontrol.common.base.UserManage;
import com.project.wisdomfirecontrol.firecontrol.base.MyApplication;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.Unit_StringUtils;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.WebViewUtils;

/**
 * Created by Administrator on 2018/3/1.
 * 联网单位
 */

public class ConnectUnitActivity extends BaseActivity {

    private TextView tv_title;
    private WebView webView;
    private ProgressBar mWebProgressbar;
    private String TAG = "tag";
    private String stringExtra, itemNameTitle;
    private String SERVICE_URL;
    private WebViewUtils webViewUtil;
    private String userid;
    private String pid;
    private WebSettings webSettings;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_connectunit;
    }

    @Override
    public void initView() {
        tv_title = findView(R.id.tv_title);
        webView = findView(R.id.webview);
        mWebProgressbar = findView(R.id.web_progressbar);
        boolean networkConnected = MyApplication.getInstance().isNetworkConnected();
        if (!networkConnected) {
            Global.showToast("网络不可用，请检查!");
            return;
        }
        Intent intent = getIntent();
        stringExtra = intent.getStringExtra("INTENT_KEY");
        itemNameTitle = intent.getStringExtra("itemNameTitle");
        if (TextUtils.isEmpty(stringExtra)) {
            return;
        }
        if (!TextUtils.isEmpty(itemNameTitle)) {
            tv_title.setText(itemNameTitle);
        }

        boolean hasUserInfo = UserManage.getInstance().hasUserInfo(Global.mContext);
        if (hasUserInfo) {
            UserInfo userIdInfo = UserManage.getInstance().getUserIdInfo(Global.mContext);
            userid = userIdInfo.getUserid();
            pid = userIdInfo.getPid();
            if (TextUtils.isEmpty(userid) && TextUtils.isEmpty(pid)) {
                showToast(getString(R.string.login_again));
                return;
            }
        }
        SERVICE_URL = Unit_StringUtils.returnWebNetUrl(stringExtra) + "?id=" + userid + "&organid=" + pid;
        Log.d(TAG, "initView: " + SERVICE_URL);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //自适应屏幕
        webSettings = webView.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        if (TextUtils.isEmpty(SERVICE_URL)) {
            return;
        }

        if (webViewUtil == null) {
            webViewUtil = new WebViewUtils(this, SERVICE_URL, webView, mWebProgressbar);
        }
        webViewUtil.initWebView();
    }

    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        finish();//结束退出程序
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

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v, int id) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webViewUtil != null) {
            webViewUtil.clearCache();
        }
    }
}
