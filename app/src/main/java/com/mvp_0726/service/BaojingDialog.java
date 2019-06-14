package com.mvp_0726.service;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.wisdomfirecontrol.R;

public class BaojingDialog {
    Context context;
    Dialogcallback dialogcallback;
    Dialog dialog;
    TextView sure;
    public TextView content;
    EditText editText;
    private float width = 162;
    private float height = 120;
    private LinearLayout ll_container;
    private TextView cancle;
    private WebView mWv;
    private String url;

    public void setUil(String url) {
        this.url = url;
        initWebView();
    }

    public BaojingDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context, R.style.dialog);
        // 透明
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.alpha = 1.0f;
        // 设置宽度、高度、密度、对齐方式
        float density = getDensity(context);
        params.width = (int) (width * density);
        params.height = (int) (height * density);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        dialog.setContentView(R.layout.dialog_web);
        mWv = (WebView) dialog.findViewById(R.id.webview);
        dialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
//        ll_container = (LinearLayout) dialog.findViewById(R.id.ll_container);
//        content = (TextView) dialog.findViewById(R.id.content);
//
//        sure = (TextView) dialog.findViewById(R.id.ok);
//        sure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogcallback.dialogdo(ll_container);
//            }
//        });
//
//        cancle = (TextView) dialog.findViewById(R.id.cancle);
//        cancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogcallback.dialogcancle();
//            }
//        });
        initWebView();
    }

    /**
     * 获取显示密度
     *
     * @param context
     * @return
     */
    public float getDensity(Context context) {
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        return dm.density;
    }

    /**
     * 设定一个interfack接口,使mydialog可以處理activity定義的事情
     *
     * @author
     */
    public interface Dialogcallback {
        public void dialogdo(LinearLayout container);

        public void dialogcancle();
    }

    public void setDialogCallback(Dialogcallback dialogcallback) {
        this.dialogcallback = dialogcallback;
    }
    public Dialog getDialog(){
        return  dialog;
    }

    /**
     * @category Set The Content of the TextView
     */
    public void setContent(String ctm) {
//        content.setText(ctm);
    }

    /**
     * Get the Text of the EditText
     */
    public String getText() {
        return editText.getText().toString();
    }

    public void isCanceledOnTouchOutside(boolean flag) {
        dialog.setCanceledOnTouchOutside(flag);
    }

    public void show() {
        if (dialog != null) {
            dialog.show();
        }
    }

    public void hideCancle() {
//        cancle.setVisibility(View.GONE);
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 初始化webview
     */
    private void initWebView() {
        WebSettings settings = mWv.getSettings();

        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptEnabled(true);
        //不开启，公众号网页 下方 阅读原文点击无效
        settings.setDomStorageEnabled(true);
        settings.setSavePassword(false);
        //关闭缓存
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAllowFileAccess(false);
        settings.setTextSize(WebSettings.TextSize.NORMAL);
        //mWv.addJavascriptInterface(new WebViewActivity.JsCallNative(), "JsCallNative");
        //加载网页
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setBlockNetworkImage(false);
        //扩大比例的缩放
        settings.setUseWideViewPort(true);
        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        // 设置可以支持缩放
        settings.setSupportZoom(true);
//        settings.setBuiltInZoomControls(true);
        //不显示webview缩放按钮
        settings.setDisplayZoomControls(false);
        settings.setSaveFormData(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(settings.MIXED_CONTENT_ALWAYS_ALLOW);  //注意安卓5.0以上的权限
        }
        mWv.setVerticalScrollbarOverlay(true);
        mWv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
//                if (mProgressbar != null) {
//                    if (newProgress == 100) {
//                        mProgressbar.setVisibility(View.GONE);
//                    } else {
//                        if (mProgressbar.getVisibility() == View.GONE) {
//                            mProgressbar.setVisibility(View.VISIBLE);
//                        }
//                    }
//                    mProgressbar.setProgress(newProgress);
//                }
            }
        });
        mWv.setWebViewClient(new WebViewClient() {
            @Override

            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

                handler.proceed();

//            handler.cancel();

//            handler.handleMessage(null);

            }
        });


        //加载url链接内容
        showWebView();
    }

    /**
     * 显示链接内容
     */
    private void showWebView() {
        if (!TextUtils.isEmpty(url)) {
            //加载html地址
            mWv.loadUrl(url);
        }
    }
}
