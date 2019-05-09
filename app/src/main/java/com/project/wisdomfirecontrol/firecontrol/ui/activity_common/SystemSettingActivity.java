package com.project.wisdomfirecontrol.firecontrol.ui.activity_common;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mvp_0726.common.base.codereview.BaseActivity;
import com.mvp_0726.common.utils.AppManager;
import com.mvp_0726.common.utils.Constans;
import com.mvp_0726.common.utils.DataCleanManager;
import com.mvp_0726.common.utils.PreferencesUtils;
import com.mvp_0726.common.utils.StatusBarUtil;
import com.mvp_0726.project_0726.login.ui.ChangePasswordActivity;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.base.UserInfo;
import com.project.wisdomfirecontrol.common.util.SharedPreUtil;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_login_splash.SelectUnitOrSepuviseActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.Unit_StringUtils;
import com.project.wisdomfirecontrol.firecontrol.ui.view.dialog.SuccessDialog;

/**
 * Created by Administrator on 2018/4/12.
 * 系统设置
 */

public class SystemSettingActivity extends BaseActivity {
    private TextView tv_unit_name, tv_unit_personla_nametel, tv_first_personla_nametel, tv_unit_personla_tel, tv_first_personla_tel;
    private TextView tv_login_name, tv_change_pwd, tv_app_dail, tv_user_back;
    private UserInfo userInfos;
    private String userName;
    private String principalTel;
    private String firstPhone;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_systemsetting;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        View view = findViewById(R.id.space);

        view.setBackground(mActivity.getResources().getDrawable(R.drawable.title_toolbar_bg_blue));
        StatusBarUtil.setFadeStatusBarHeight(mActivity, view);
        tv_title.setText("系统设置");
        tv_unit_name = findViewById(R.id.tv_unit_name);
        tv_unit_personla_nametel = findViewById(R.id.tv_unit_personla_nametel);
        tv_unit_personla_tel = findViewById(R.id.tv_unit_personla_tel);
        tv_first_personla_nametel = findViewById(R.id.tv_first_personla_nametel);
        tv_first_personla_tel = findViewById(R.id.tv_first_personla_tel);

        tv_login_name = findViewById(R.id.tv_login_name);
        tv_change_pwd = findViewById(R.id.tv_change_pwd);

        tv_app_dail = findViewById(R.id.tv_app_dail);
        tv_user_back = findViewById(R.id.tv_user_back);
    }

    @Override
    protected void setLisenter() {
        tv_unit_personla_tel.setOnClickListener(this);
        tv_change_pwd.setOnClickListener(this);
        tv_app_dail.setOnClickListener(this);
        tv_user_back.setOnClickListener(this);
        tv_first_personla_tel.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData() {
        userInfos = Unit_StringUtils.getUserInfos(Global.mContext);
        if (userInfos != null) {
            userName = userInfos.getUserName();
            if (!TextUtils.isEmpty(userName)) {
                tv_login_name.setText(userName);
            }
        }
        String companyName = SharedPreUtil.getString(Global.mContext, "companyName", "");
        String principal = SharedPreUtil.getString(Global.mContext, "principal", "");
        principalTel = SharedPreUtil.getString(Global.mContext, "principalTel", "");
        String firstName = SharedPreUtil.getString(Global.mContext, "firstName", "");
        firstPhone = SharedPreUtil.getString(Global.mContext, "firstPhone", "");
        tv_unit_name.setText(companyName);
        tv_unit_personla_nametel.setText(principal);
        tv_unit_personla_tel.setText(principalTel);
        tv_first_personla_nametel.setText(firstName);
        tv_first_personla_tel.setText(firstPhone);
    }


    @Override
    protected void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.tv_unit_personla_tel:
                tv_unit_personla_tel();
                break;
            case R.id.tv_first_personla_tel:
                tv_first_personla_tel();
                break;
            case R.id.tv_change_pwd:
                changePwd();
                break;
            case R.id.tv_app_dail:
                tv_app_dail();
                break;
            case R.id.tv_user_back:
                tv_user_back();
                break;
        }
    }

    /*第一联系人电话*/
    private void tv_first_personla_tel() {
        boolean pad = com.mvp_0726.common.utils.Global.isPad();
        if (!pad) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL);   //android.intent.action.DIAL
            intent.setData(Uri.parse("tel:" + firstPhone));
            startActivity(intent);
        }
    }

    /*单位联系人电话*/
    private void tv_unit_personla_tel() {
        boolean pad = com.mvp_0726.common.utils.Global.isPad();
        if (!pad) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL);   //android.intent.action.DIAL
            intent.setData(Uri.parse("tel:" + principalTel));
            startActivity(intent);
        }
    }


    private void changePwd() {
        Intent intent = new Intent(SystemSettingActivity.this, ChangePasswordActivity.class);
        super.startActivity(intent);
    }

    private void tv_app_dail() {
        Intent intent = new Intent(SystemSettingActivity.this, AboutVersionCodeActivity.class);
        super.startActivity(intent);
    }

    private boolean isRestart = true;

    private void tv_user_back() {
        final SuccessDialog successDialog = new SuccessDialog(SystemSettingActivity.this);
        successDialog.setContent(getString(R.string.iscome_back_login));

        successDialog.setDialogCallback(new SuccessDialog.Dialogcallback() {
            @Override
            public void dialogdo(LinearLayout container) {
                if (!isFinishing()) {
                    successDialog.dismiss();
                }
                SharedPreUtil.saveBoolean(Global.mContext, "isLogin", false);
                SharedPreUtil.saveString(Global.mContext, "userinfos", "");
                DataCleanManager.clearAllCache(SystemSettingActivity.this);
                if (isRestart) {
                    Intent intent = new Intent(SystemSettingActivity.this, SelectUnitOrSepuviseActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    PreferencesUtils.putString(SystemSettingActivity.this, Constans.COOKIE_PREF, "");
                    AppManager.getAppManager().removedAlllActivity(SystemSettingActivity.this);
                    isRestart = false;
                    finish();
                }
            }

            @Override
            public void dialogcancle() {
                if (!isFinishing()) {
                    successDialog.dismiss();
                }
            }
        });
        successDialog.isCanceledOnTouchOutside(false);
        if (!isFinishing()) {
            successDialog.show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }


}
