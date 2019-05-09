package com.project.wisdomfirecontrol.firecontrol.ui.fragment_unit;

import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseFragment;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.base.UserInfo;
import com.project.wisdomfirecontrol.common.base.UserManage;
import com.project.wisdomfirecontrol.firecontrol.base.MyApplication;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_unit.Unit_CommonConnectUnitActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.WebViewUtils;

/**
 * Created by Administrator on 2018/1/30.
 */

public class First_Web_Fragment extends BaseFragment {

    private String TAG = "tag";

    private WebViewUtils webViewUtil;
    private String userid;
    private String pid;
    private WebSettings webSettings;

    private WebView webView;
    private ProgressBar mWebProgressbar;


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_second_web;
    }

    @Override
    public void initView() {
        webView = findView(R.id.webview);
        mWebProgressbar = findView(R.id.web_progressbar);
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    public void onStart() {
        super.onStart();
        loadWebH5(Unit_CommonConnectUnitActivity.string);
    }

    //监控第一个Fragment
    @Override
    public void setUserVisibleHint(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (hidden) {
            Log.d(TAG, "onHiddenChanged: " + "不可见");
        } else {
            Log.d(TAG, "onHiddenChanged: " + "当前可见");
        }
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
            webViewUtil = new WebViewUtils(getActivity(), SERVICE_URL, webView, mWebProgressbar);
            Log.d(TAG, "initView: webViewUtil" + SERVICE_URL);
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
        getActivity().finish();//结束退出程序
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
    public void initTitleHeight(LinearLayout linearLayout) {
    }

    @Override
    public void onClick(View v, int id) {

    }

    @Override
    public void onHttpSuccess(int reqType, Message obj) {
        super.onHttpSuccess(reqType, obj);
    }


    @Override
    public void onHttpError(int reqType, String error) {
    }

}
