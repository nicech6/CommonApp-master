package com.mvp_0726.project_0726.login.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.mvp_0726.common.event.CommonEvent;
import com.mvp_0726.common.network.ApiRetrofit;
import com.mvp_0726.common.network.NetworkUrl;
import com.mvp_0726.common.utils.Constans;
import com.mvp_0726.common.utils.PreferencesUtils;
import com.mvp_0726.project_0726.home.ui.MvpThirdMainActivity;
import com.mvp_0726.project_0726.login.contract.LoginContract;
import com.mvp_0726.project_0726.login.presenter.SplashPresenter;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.base.UserManage;
import com.project.wisdomfirecontrol.common.util.SharedPreUtil;
import com.project.wisdomfirecontrol.firecontrol.base.MyApplication;
import com.project.wisdomfirecontrol.firecontrol.model.bean.login.LoginChangeDataBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.login.PersonelChangeBean;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_login_splash.SelectUnitOrSepuviseActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.view.dialog.SuccessDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by Administrator on 2018/2/8.
 */

public class SplashActivity extends com.mvp_0726.common.base.codereview.BaseActivity implements LoginContract.View {

    private String companyLogoName;
    private SplashPresenter presenter;
    private String userid;
    private String pid;
    private String telNum;
    private String type = "";
    private String imagthpath = "";
    private String userName;
    private String userPwd;


    @Override
    protected int getContentViewResId() {
        return R.layout.activity_splash;
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void initView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        presenter = new SplashPresenter(this, this);
        if (!isTaskRoot()) {
            finish();
            return;
        }
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            isHasLocationPermission();
        } else {
            isFristLogin();
        }
    }

    private void isFristLogin() {
        String string = PreferencesUtils.getString(this, Constans.COOKIE_PREF);
        if (TextUtils.isEmpty(string)) {
            Intent intent = new Intent(SplashActivity.this, SelectUnitOrSepuviseActivity.class);
            startActivity(intent);
            finish();
        } else {
            isGoinMainOrLogin();
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setLisenter() {

    }

    @Override
    protected void widgetClick(View v) {

    }

    private void isHasLocationPermission() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                == PackageManager.PERMISSION_GRANTED) {
//            isFristLogin();
//        } else {//申请权限
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
//        }
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            isFristLogin();
//        } else {//申请权限
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
//        }
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                == PackageManager.PERMISSION_GRANTED) {
//            isFristLogin();
//        } else {//申请权限
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 3);
//        }
        check();

    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    public void check() {
        /**
         * 第 1 步: 检查是否有相应的权限
         */
        boolean isAllGranted = checkPermissionAllGranted(
                new String[]{
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }
        );
        // 如果这3个权限全都拥有, 则直接执行备份代码
        if (isAllGranted) {
            isFristLogin();
            return;
        }

        /**
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                MY_PERMISSION_REQUEST_CODE
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10085) {

        }
    }

    private static final int MY_PERMISSION_REQUEST_CODE = 10000;

    //权限请求结果
    boolean isAllGranted;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        final SuccessDialog successDialog = new SuccessDialog(SplashActivity.this);
        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                // 如果所有的权限都授予了, 则执行备份代码
                isFristLogin();

            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                successDialog.setContent("暂无读写SD卡权限\n是否前往设置？");
                successDialog.setDialogCallback(new SuccessDialog.Dialogcallback() {
                    @Override
                    public void dialogdo(LinearLayout container) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + getPackageName())); // 根据包名打开对应的设置界面
                        startActivityForResult(intent, 10085);
                    }

                    @Override
                    public void dialogcancle() {
                        if (!isFinishing()) {
                            successDialog.dismiss();
                        }
                    }
                });

                if (!isFinishing()) {
                    successDialog.show();
                }
            }
        }

//        switch (requestCode) {
//            case 3:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    isFristLogin();
//                } else {
//                    successDialog.setContent("暂无读写SD卡权限\n是否前往设置？");
//                    successDialog.setDialogCallback(new SuccessDialog.Dialogcallback() {
//                        @Override
//                        public void dialogdo(LinearLayout container) {
//                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                            intent.setData(Uri.parse("package:" + getPackageName())); // 根据包名打开对应的设置界面
//                            startActivityForResult(intent, 10085);
//                        }
//
//                        @Override
//                        public void dialogcancle() {
//                            if (!isFinishing()) {
//                                successDialog.dismiss();
//                            }
//                        }
//                    });
//
//                    if (!isFinishing()) {
//                        successDialog.show();
//                    }
//                }
//                break;
//            case 2:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    isFristLogin();
//                } else {
//                    Global.showToast("请前往权限管理设置");
//                }
//                break;
//        }

    }


    private void isGoinMainOrLogin() {
        if (UserManage.getInstance().hasUserInfo(Global.mContext)) {//自动登录判断，SharePrefences中有数据，则跳转到主页，没数据则跳转到登录页
            String userName = UserManage.getInstance().getUserInfo(Global.mContext).getUserName();
            String userPwd = UserManage.getInstance().getUserInfo(Global.mContext).getPassword();
            if (userName.isEmpty() && userPwd.isEmpty()) {
                gotoLogin();
            }
            boolean networkConnected = MyApplication.getInstance().isNetworkConnected();
            if (!networkConnected) {
                gotoLogin();
            } else {
                if (presenter != null) {
                    ApiRetrofit.changeApiBaseUrl(NetworkUrl.ANDROID_BAIDU_SERVICE);
                    presenter.login(userName, userPwd);
                }
            }
        } else {
            gotoLogin();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void disposeCommonEvent(CommonEvent ecEvent) {
        switch (ecEvent.getWhat()) {
            case Constans.LOGIN:
                LoginChangeDataBean data = (LoginChangeDataBean) ecEvent.getData();
                SharedPreUtil.saveBoolean(Global.mContext, "isLogin", true);
                loginDatas(data);
                break;
            case Constans.RELOGIN:
                gotoLogin();
                break;

        }
    }

    private void gotoLogin() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
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
//        SharedPreUtil.saveString(Global.mContext, "orgShortName", companyLogoName);
        SharedPreUtil.saveString(Global.mContext, "orgShortName", roleName);

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
        Log.d("tag", "loginDatas: " + userName);
//        intent = new Intent(this, MainChangeActivity.class);
//        Intent intent = new Intent(this, MvpMainActivity.class);
        Intent intent = new Intent(this, MvpThirdMainActivity.class);
        intent.putExtra("loginBean", data);
        startActivity(intent);
        finish();
    }

    /*
     * 屏蔽物理返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
