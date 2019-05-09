package com.mvp_0726.common.view.dialog;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.project.wisdomfirecontrol.R;

/**
 * on 2018/6/2 提示框
 */

public class SucessCacheSureDialog extends Dialog {

    private TextView cancle, sure,tv_title;

    public SucessCacheSureDialog(@NonNull Context context) {
        super(context, R.style.DialogStyle_white);
        setContentView(R.layout.dialog_delete);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setCancelable(true);
        initView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        this.getWindow().setDimAmount(0f);
    }

    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        cancle = findViewById(R.id.tv_cancel);
        sure = findViewById(R.id.tv_sure);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sucessCacheSureListener != null) {
                    sucessCacheSureListener.cancle();
                }
                dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sucessCacheSureListener != null) {
                    sucessCacheSureListener.sure();
                }
                dismiss();
            }
        });
    }

    public void setTextContent(String txt){
        this.tv_title.setText(txt);
    }

    private SucessCacheSureListener sucessCacheSureListener;

    public void setSucessCacheSureListener(SucessCacheSureListener listener) {
        this.sucessCacheSureListener = listener;
    }

    public interface SucessCacheSureListener {
        void sure();

        void cancle();
    }
}
