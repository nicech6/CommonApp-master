package com.mvp_0726.project_0726.login.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mvp_0726.common.event.CommonEvent;
import com.mvp_0726.common.utils.Constans;
import com.mvp_0726.common.utils.StatusBarUtil;
import com.mvp_0726.project_0726.home.ui.MvpThirdMainActivity;
import com.mvp_0726.project_0726.login.contract.LoginContract;
import com.mvp_0726.project_0726.login.presenter.LoginPresenter;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.base.UserManage;
import com.project.wisdomfirecontrol.common.util.SharedPreUtil;
import com.project.wisdomfirecontrol.firecontrol.base.MyApplication;
import com.project.wisdomfirecontrol.firecontrol.download.UpdateManager;
import com.project.wisdomfirecontrol.firecontrol.model.bean.login.LoginChangeDataBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.login.PersonelChangeBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by Administrator on 2017/11/9.
 */

public class LoginActivity extends com.mvp_0726.common.base.codereview.BaseActivity implements LoginContract.View {


    private static final String TAG = "tag";
    private TextView tv_app_name;
    private EditText etUserName, etUserPwd;
    private View view;
    private Button btn_eye_type;
    private LinearLayout ll_login_bg;
    private String userName;
    private String userPwd;
    private String userid;
    private String pid;
    private String telNum;
    private String type = "";
    private String imagthpath = "";
    private String companyLogoName;
    private LoginPresenter loginPresenter;
    private Button btn_login;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_login;
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void initView(Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        loginPresenter = new LoginPresenter(this, this);
        ll_login_bg = findViewById(R.id.ll_login_bg);
        btn_login = findViewById(R.id.btn_login);
        view = findViewById(R.id.space);

        view.setBackground(mActivity.getResources().getDrawable(R.drawable.title_toolbar_bg_blue));
        StatusBarUtil.setFadeStatusBarHeight(mActivity, view);
        iv_back.setVisibility(View.INVISIBLE);
        tv_title.setText(getResources().getString(R.string.btn_login));
        tv_app_name = findViewById(R.id.tv_app_name);
        ViewGroup.LayoutParams layoutParams = tv_app_name.getLayoutParams();
        layoutParams.height = (int) Global.mScreenHeight / 4;
        tv_app_name.setLayoutParams(layoutParams);

        tv_app_name.setText(R.string.app_login_name);
        etUserName = findViewById(R.id.et_user_name);
        etUserPwd = findViewById(R.id.et_user_pwd);
        btn_eye_type = findViewById(R.id.btn_eye_type);

        ll_login_bg.setBackgroundResource(R.drawable.bg_loging1242x2208);
        etUserName.setSelection(etUserName.getText().length());
        etUserName.extendSelection(etUserName.getText().length());
        etUserPwd.setSelection(etUserPwd.getText().length());
        etUserPwd.extendSelection(etUserPwd.getText().length());

        SharedPreUtil.saveString(Global.mContext, "imagthpath", imagthpath);
        checkVersion();
    }

    //    检查版本更新
    private boolean HasCheckUpdate = false;
    private UpdateManager mUpdateManager;

    private void checkVersion() {
        if (!HasCheckUpdate) {
            mUpdateManager = new UpdateManager(LoginActivity.this, true);
            mUpdateManager.checkVersion();
            HasCheckUpdate = true;
        }
    }


    @Override
    public void initData() {
        SharedPreUtil.saveString(Global.mContext, "userinfos", "");
        btnEyeCloseAndOpen();
        boolean hasUserInfo = UserManage.getInstance().hasUserInfo(Global.mContext);
        if (hasUserInfo) {
            userName = UserManage.getInstance().getUserInfo(Global.mContext).getUserName();
            userPwd = UserManage.getInstance().getUserInfo(Global.mContext).getPassword();
            etUserName.setText(userName);
            etUserPwd.setText(userPwd);
        }
    }

    private boolean canSee = false;

    private void btnEyeCloseAndOpen() {
        btn_eye_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //通过全局的一个变量的设置，这个就是判断控件里面的内容是不是能被看到
                if (!canSee) {
                    //如果是不能看到密码的情况下，
                    etUserPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    btn_eye_type.setBackgroundResource(R.drawable.eye_open);
                    canSee = true;
                } else {
                    //如果是能看到密码的状态下
                    etUserPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    btn_eye_type.setBackgroundResource(R.drawable.eye_close);
                    canSee = false;
                }
            }
        });
    }

    @Override
    protected void setLisenter() {
        btn_login.setOnClickListener(this);
    }

    @Override
    protected void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
        }
    }

    //    登录
    private void login() {
        boolean networkConnected = MyApplication.getInstance().isNetworkConnected();
        if (!networkConnected) {
            showErrorToast(getResources().getString(R.string.no_net));
            return;
        }
        userName = etUserName.getText().toString().trim();
//        boolean isPhoneNum1 = StringUtils.isMobileNO(userName);
        if (TextUtils.isEmpty(userName)) {
            showSuccessToast("请输入账号或手机号！");
            return;
        }
        if (TextUtils.isEmpty(userName)) {
            showSuccessToast(getString(R.string.empty_username));
            return;
        }

        userPwd = etUserPwd.getText().toString().trim();
        if (TextUtils.isEmpty(userPwd)) {
            showSuccessToast(getString(R.string.empty_userpwd));
            return;
        }
        if (loginPresenter != null) {
//            ApiRetrofit.changeApiBaseUrl(NetworkUrl.ANDROID_BAIDU_SERVICE);
            loginPresenter.login(userName, userPwd);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void disposeCommonEvent(CommonEvent ecEvent) {
        switch (ecEvent.getWhat()) {
            case Constans.LOGIN:
                LoginChangeDataBean data = (LoginChangeDataBean) ecEvent.getData();
                Log.d(TAG, "disposeCommonEvent: " + data.getMenuDatas().get(0).getName());
                SharedPreUtil.saveBoolean(Global.mContext, "isLogin", true);
                loginDatas(data);

                break;
            case Constans.ERROR:
                String errorMsg = (String) ecEvent.getData();
                showErrorToast(errorMsg);
                break;

        }
    }

    private void loginDatas(LoginChangeDataBean data) {
        PersonelChangeBean personel = data.getPersonel();
        userName = personel.getName();
        userPwd = personel.getPassword();
        userid = personel.getId();
        pid = personel.getPid();
        telNum = personel.getTelNum();
        type = personel.getIsadmin();
        imagthpath = personel.getImagthpath();
        String orgShortName = personel.getOrgShortName();

        String roleName = personel.getRole().get(0).getRoleName();
        String companyType = personel.getCompanyType();
        if (!TextUtils.isEmpty(orgShortName) && !TextUtils.isEmpty(roleName)) {
            companyLogoName = orgShortName + "-" + roleName;
        } else if (!TextUtils.isEmpty(orgShortName) && TextUtils.isEmpty(roleName)) {
            companyLogoName = orgShortName;
        } else if (TextUtils.isEmpty(orgShortName) && !TextUtils.isEmpty(roleName)) {
            companyLogoName = roleName;
        }
        String companyName = personel.getOrgName();
        SharedPreUtil.saveString(Global.mContext, "companyName", companyName);
        SharedPreUtil.saveString(Global.mContext, "companyType", companyType);
        SharedPreUtil.saveString(Global.mContext, "orgShortName", roleName);
//        SharedPreUtil.saveString(Global.mContext, "orgShortName", companyLogoName);

        if (!TextUtils.isEmpty(imagthpath)) {
            SharedPreUtil.saveString(Global.mContext, "imagthpath", imagthpath);
        }

        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPwd)
                && !TextUtils.isEmpty(userid) && !TextUtils.isEmpty(pid)
                && !TextUtils.isEmpty(telNum) && !TextUtils.isEmpty(type)) {
            UserManage.getInstance().saveUserInfo(Global.mContext, telNum, userPwd);
            UserManage.getInstance().saveUserIdInfo(Global.mContext, userid, pid);
            UserManage.getInstance().saveUserPhoneInfo(Global.mContext, telNum);
            UserManage.getInstance().saveUserType(Global.mContext, type);
        }
        Intent intent;
//        intent = new Intent(this, MainChangeActivity.class);
//        intent = new Intent(this, MvpMainActivity.class);
//        intent = new Intent(this, TestActivity.class);
        intent = new Intent(this, MvpThirdMainActivity.class);
        intent.putExtra("loginBean", data);
        super.startActivity(intent);
        super.finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10086) {
            if (mUpdateManager != null) {
                mUpdateManager.isAndoird8();
            }
        }
    }

    private long clickTime = 0; //记录第一次点击的时间

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            showSuccessToast(getString(R.string.btn_again_out));
            clickTime = System.currentTimeMillis();
        } else {
            System.exit(0);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}

