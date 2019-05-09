package com.project.wisdomfirecontrol.firecontrol.ui.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.mvp_0726.common.event.CommonEvent;
import com.mvp_0726.common.utils.Constans;
import com.mvp_0726.common.utils.LogUtils;
import com.mvp_0726.common.view.webview.H5JSInterface;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.firecontrol.base.MyApplication;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2018/2/27.
 */

public class WebViewUtils {
    private static final String TAG = "tag";
    private String url;
    private WebView webView;
    private ProgressBar progressBar;
    private Context context;
    private WebSettings webSettings;
    private H5JSInterface h5JSInterface;

    public WebViewUtils(Context context, String url, WebView webView, ProgressBar progressBar) {
        this.context = context;
        this.url = url;
        this.webView = webView;
        this.progressBar = progressBar;
    }

    public H5JSInterface getH5JsInterface() {
        return h5JSInterface;
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView() {
        if (webSettings == null) {
            webSettings = webView.getSettings();
        }
//        WebChromeClient wvcc = new WebChromeClient();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容


        // 加快HTML网页加载完成速度
        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setLoadsImagesAutomatically(true);
        } else {
            webSettings.setLoadsImagesAutomatically(false);
        }
        h5JSInterface = new H5JSInterface(MyApplication.getContext());
        webView.addJavascriptInterface(h5JSInterface, "javaObject");

        // 加载Web地址
        webView.loadUrl(url);
        webView.setDownloadListener(new MyWebViewDownLoadListener());

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("tag", "shouldOverrideUrlLoading: " + url);
                //这里进行url拦截
                if (url != null && url.contains("androidUpload")) {
//                    Global.showToast("aaaaaaaaaaaaa");
                }

                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView var1, int var2, String var3, String var4) {
                progressBar.setVisibility(View.GONE);
                Global.showToast(context.getString(R.string.update_fail));
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
//                webView.loadUrl("javascript:"+ "window.alert('Js injection success')" );
//                webView.loadUrl("javascript:echoImg(" + "bbbbbbbbbbbbb" + ")");
                super.onPageFinished(webView, s);
            }
        });

//        webView.setWebChromeClient(wvcc);
//        WebViewClient wvc = new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                webView.loadUrl(url);
//                return true;
//            }
//        };
//        webView.setWebViewClient(wvc);

        webView.setWebChromeClient(new WebChromeClient() {
            //进度条
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
            }


            @Override
            public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback callback) {
            }

            @Override
            public void onHideCustomView() {
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView(String SERVICE_URL, final WebView webView, final ProgressBar progressBar) {
        if (webSettings == null) {
            webSettings = webView.getSettings();
        }
        WebChromeClient wvcc = new WebChromeClient();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
        // 加载Web地址
        // 加快HTML网页加载完成速度
        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setLoadsImagesAutomatically(true);
        } else {
            webSettings.setLoadsImagesAutomatically(false);
        }

        h5JSInterface = new H5JSInterface(MyApplication.getContext());
        webView.addJavascriptInterface(h5JSInterface, "javaObject");
        webView.setDownloadListener(new MyWebViewDownLoadListener());
        webView.loadUrl(SERVICE_URL);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && url.contains("androidUpload")) {
                    Global.showToast("aaaaaaaaaaaaa");
                }
                LogUtils.d("tag===" + url);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView var1, int var2, String var3, String var4) {
                progressBar.setVisibility(View.GONE);
                Global.showToast(context.getString(R.string.update_fail));
            }

            @Override
            public void onPageFinished(WebView webView, String s) {

//                webView.loadUrl("javascript:echoImg(" + "aaaaaaaaaaaa" + ")");
                super.onPageFinished(webView, s);
            }
        });

        webView.setWebChromeClient(wvcc);
        WebViewClient wvc = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }
        };
        webView.setWebViewClient(wvc);

        webView.setWebChromeClient(new WebChromeClient() {
            //进度条
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
            }

            @Override
            public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback callback) {
            }

            @Override
            public void onHideCustomView() {
            }
        });
    }

    public void clearCache() {
        if (webView != null) {
            //清空所有Cookie
            CookieSyncManager.createInstance(context);  //Create a singleton CookieSyncManager within a context
            CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
            cookieManager.removeAllCookie();// Removes all cookies.
            CookieSyncManager.getInstance().sync(); // forces sync manager to sync now
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearCache(true);
        }
    }

    //内部类
    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            Log.d(TAG, "onDownloadStart: " + url);
            EventBus.getDefault().post(new CommonEvent(Constans.WEBSUCESS, url));//文件详情
        }
    }

}
