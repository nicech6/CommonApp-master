package com.project.wisdomfirecontrol.firecontrol.ui.activity_setting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * Created by Administrator on 2018/6/20.
 * 二维码
 */

public class QRcodeActivity extends BaseActivity {

    private CaptureFragment captureFragment;

    public static boolean isOpen = false;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_qrcode;
    }

    @Override
    public void initView() {
        title.setText("二维码");
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();

        LinearLayout linearLayout = findView(R.id.linear1);
        TextView tv_setting_numbyhand = findView(R.id.tv_setting_numbyhand);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpen) {
                    CodeUtils.isLightEnable(true);
                    isOpen = true;
                } else {
                    CodeUtils.isLightEnable(false);
                    isOpen = false;
                }

            }
        });

        tv_setting_numbyhand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.setClass(QRcodeActivity.this, AddNewSettingActivity.class);
                startActivityForResult(resultIntent, REQUEST_CODE);
            }
        });
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            resultIntent.setClass(QRcodeActivity.this, AddNewSettingActivity.class);
            resultIntent.putExtra("result", result);
            startActivityForResult(resultIntent, REQUEST_CODE);

        }

        @Override
        public void onAnalyzeFailed() {
//            Intent resultIntent = new Intent();
//            Bundle bundle = new Bundle();
//            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
//            bundle.putString(CodeUtils.RESULT_STRING, "");
//            resultIntent.putExtras(bundle);
//            QRcodeActivity.this.setResult(RESULT_OK, resultIntent);
            QRcodeActivity.this.finish();
            showToast("请检查二维码是否正确");
        }
    };

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v, int id) {

    }

    public static final int REQUEST_CODE = 111;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       /* *
         * 处理二维码扫描结果*/

        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                finish();
            }
        }
    }
}
