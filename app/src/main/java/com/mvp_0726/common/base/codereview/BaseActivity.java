package com.mvp_0726.common.base.codereview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mvp_0726.common.network.RxLifeManager;
import com.mvp_0726.common.utils.AppManager;
import com.mvp_0726.common.utils.StatusBarUtil;
import com.mvp_0726.common.utils.ToastUtils;
import com.mvp_0726.common.view.dialog.SucessCacheSureDialog;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.firecontrol.ui.view.DialogHelper;
import com.project.wisdomfirecontrol.firecontrol.ui.view.dialog.WaitDialog;


/**
 * Created  on 2018-01-04.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    public BaseActivity mActivity;
    public RelativeLayout iv_back;
    public TextView tv_title;

    private SucessCacheSureDialog sureDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        AppManager.getAppManager().addActivity(this);
        mActivity = this;
        if (isNeedTranslateBar()) {
            StatusBarUtil.setTranslate(mActivity, true);
        }
        init(savedInstanceState);
        initData();
        setLisenter();
    }

    protected boolean isNeedTranslateBar() {
        return true;
    }

    protected void init(Bundle savedInstanceState) {
        setContentView(getContentViewResId());
        iv_back = findViewById(R.id.iv_back);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setTextSize(18f);
        iv_back.setOnClickListener(this);
        initView(savedInstanceState);
    }

    protected abstract int getContentViewResId();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();

    protected abstract void setLisenter();

    protected abstract void widgetClick(View v);

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
        widgetClick(v);
    }

    private static boolean _isVisible = false;
    @SuppressLint("StaticFieldLeak")
    private static WaitDialog _waitDialog;

    public WaitDialog showWaitDialog(Activity activity, String message) {
        if (!_isVisible) {
            if (_waitDialog == null) {
                _waitDialog = DialogHelper.getWaitDialog(activity, message);
            }
            if (_waitDialog != null) {
                _waitDialog.setMessage(message);
                if (!isFinishing()) {
                    _waitDialog.show();
                }
                _isVisible = true;
            }
            return _waitDialog;
        }
        return null;
    }

    public void dismissWaitDialog() {
        if (_isVisible && _waitDialog != null) {
            try {
                if (!isFinishing())
                    _waitDialog.dismiss();
                _waitDialog = null;
                _isVisible = false;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void showSuccessDialog(String txt) {
        try {
            if (sureDialog == null) {
                sureDialog = new SucessCacheSureDialog(this);
            }
            sureDialog.setTextContent(txt);
            sureDialog.setSucessCacheSureListener(new SucessCacheSureDialog.SucessCacheSureListener() {
                @Override
                public void sure() {
                    if (!isFinishing()) {
                        sureDialog.dismiss();
                    }
                    finish();
                }

                @Override
                public void cancle() {
                    if (!isFinishing()) {
                        sureDialog.dismiss();
                    }
                }
            });

            if (!isFinishing()) {
                sureDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        RxLifeManager.getRxLifeManager().onStopDisposable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        RxLifeManager.getRxLifeManager().onDestroy();
    }

    /*
     * 统一处理toolbar的基本设置
     * */
    protected void initTitleBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackground(mActivity.getResources().getDrawable(R.drawable.title_toolbar_bg_blue));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /*
     * 带图片的toast
     * */
    public void showSuccessToast(String msg) {
        ToastUtils.success(msg);
    }

    /*
     * error的toast
     * */
    public void showErrorToast(String msg) {
        ToastUtils.error(msg);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
