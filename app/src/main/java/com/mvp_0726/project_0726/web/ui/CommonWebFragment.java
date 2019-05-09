package com.mvp_0726.project_0726.web.ui;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.mvp_0726.common.base.codereview.BaseFragment;
import com.mvp_0726.common.event.CommonEvent;
import com.mvp_0726.common.utils.Constans;
import com.mvp_0726.common.utils.Global;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.UserInfo;
import com.project.wisdomfirecontrol.common.base.UserManage;
import com.project.wisdomfirecontrol.firecontrol.base.MyApplication;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.WebViewUtils;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2018/1/30.
 */

public class CommonWebFragment extends BaseFragment {

    private String TAG = "tag";

    private WebViewUtils webViewUtil;
    private String userid;
    private String pid;
    private WebSettings webSettings;

    private WebView webView;
    private ProgressBar mWebProgressbar;
    private String url;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_second_web;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        webView = rootView.findViewById(R.id.webview);
        mWebProgressbar = rootView.findViewById(R.id.web_progressbar);
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    public void onStart() {
        super.onStart();

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void disposeCommonEvent(CommonEvent ecEvent) {
        switch (ecEvent.getWhat()) {
            case Constans.WEBH5SUCESS:
                url = (String) ecEvent.getData();
                loadWebH5(url);
                break;
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    //监控第一个Fragment
    @Override
    public void setUserVisibleHint(boolean hidden) {
        super.onHiddenChanged(hidden);
    }


    private void loadWebH5(String SERVICE_URL) {
        boolean networkConnected = MyApplication.getInstance().isNetworkConnected();
        if (!networkConnected) {
            showErrorToast("网络不可用，请检查!");
            return;
        }
        boolean hasUserInfo = UserManage.getInstance().hasUserInfo(Global.mContext);
        if (hasUserInfo) {
            UserInfo userIdInfo = UserManage.getInstance().getUserIdInfo(Global.mContext);
            userid = userIdInfo.getUserid();
            pid = userIdInfo.getPid();
            if (TextUtils.isEmpty(userid) && TextUtils.isEmpty(pid)) {
                showErrorToast(getString(R.string.login_again));
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
    public void initData() {
    }

    @Override
    protected void setLisenter() {

    }

    @Override
    protected void widgetClick(View v) {

    }


}
