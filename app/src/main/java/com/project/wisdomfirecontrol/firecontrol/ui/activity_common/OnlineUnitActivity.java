package com.project.wisdomfirecontrol.firecontrol.ui.activity_common;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.base.UserInfo;
import com.project.wisdomfirecontrol.common.base.UserManage;
import com.project.wisdomfirecontrol.firecontrol.base.MyApplication;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.IHttpService;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.WebViewUtils;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by Administrator on 2018/7/10.
 */

public class OnlineUnitActivity extends BaseActivity {

    private WebViewUtils webViewUtil;
    private String userid;
    private String pid;
    private WebSettings webSettings;

    private WebView webView;
    private ProgressBar mWebProgressbar;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_onlineunit;
    }

    @Override
    public void initView() {
        title.setText("辖区地图");
        webView = findView(R.id.webview);
        mWebProgressbar = findView(R.id.web_progressbar);

        loadWebH5(IHttpService.NETDEVICE_URL_UNIT);
    }

    private void loadWebH5(String SERVICE_URL) {
        boolean networkConnected = MyApplication.getInstance().isNetworkConnected();
        if (!networkConnected) {
            Global.showToast("网络不可用，请检查!");
            return;
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

        SERVICE_URL = SERVICE_URL + "?id=" + userid + "&organid=" + pid;
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

        if (webViewUtil == null) {
            webViewUtil = new WebViewUtils(this, SERVICE_URL, webView, mWebProgressbar);
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
}
