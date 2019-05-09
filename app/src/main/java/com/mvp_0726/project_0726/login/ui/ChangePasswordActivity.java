package com.mvp_0726.project_0726.login.ui;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.mvp_0726.common.event.CommonEvent;
import com.mvp_0726.common.utils.Constans;
import com.mvp_0726.common.utils.StatusBarUtil;
import com.mvp_0726.project_0726.login.contract.ChangePasswordContract;
import com.mvp_0726.project_0726.login.presenter.ChangePasswordPresenter;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.base.UserManage;
import com.project.wisdomfirecontrol.firecontrol.base.MyApplication;
import com.project.wisdomfirecontrol.firecontrol.ui.view.dialog.SuccessDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2017/6/29.
 * 修改密码
 */

public class ChangePasswordActivity extends com.mvp_0726.common.base.codereview.BaseActivity implements ChangePasswordContract.View {

    private EditText et_oldPwd, et_newPwd;
    private Button btn_change;
    private CheckBox chk_showPwd;
    private SuccessDialog myDialog;
    private static String TAG = "tag";
    private ChangePasswordPresenter presenter;
    private String data;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        presenter = new ChangePasswordPresenter(this, this);

        View view = findViewById(R.id.space);

        view.setBackground(mActivity.getResources().getDrawable(R.drawable.title_toolbar_bg_blue));
        StatusBarUtil.setFadeStatusBarHeight(mActivity, view);

        et_oldPwd = findViewById(R.id.et_oldPwd);
        et_newPwd = findViewById(R.id.et_newPwd);
        btn_change = findViewById(R.id.btn_change);
        btn_change.setOnClickListener(this);
        chk_showPwd = findViewById(R.id.chk_showPwd);
        chk_showPwd.setOnClickListener(this);
        tv_title.setText(R.string.change_pwd);
        doBusiness();
    }

    private void doBusiness() {
        chk_showPwd.setChecked(false);
        chk_showPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (chk_showPwd.isChecked()) {
                    chk_showPwd.setBackgroundResource(R.drawable.bg_button_select_pressed);
                    et_oldPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_newPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    chk_showPwd.setBackgroundResource(R.drawable.bg_button_select_default);
                    et_oldPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_newPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }

            ;

        });

    }

    // 修改密码
    public void changePwd() {
        boolean networkConnected = MyApplication.getInstance().isNetworkConnected();
        if (!networkConnected) {
            showErrorToast(getResources().getString(R.string.no_net));
            return;
        }
        String oldPwd = et_oldPwd.getText().toString().trim();
        String newPwd = et_newPwd.getText().toString().trim();

//        if (oldPwd.length() < 6 || oldPwd.length() > 16) {
//            Global.showToast(getString(R.string.old_pwd_six));
//            return;
//        }

//        if (newPwd.length() < 6 || newPwd.length() > 16) {
//            Global.showToast(getString(R.string.new_pwd_six));
//            return;
//        }
        String userName = UserManage.getInstance().getUserInfo(Global.mContext).getUserName();
        if (presenter != null) {
            presenter.updatePwd(userName, oldPwd, newPwd);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void disposeCommonEvent(CommonEvent ecEvent) {
        switch (ecEvent.getWhat()) {
            case Constans.CHANPWD:
                data = (String) ecEvent.getData();
                showSuccessToast(data);
//                Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
//                startActivity(intent);
//                AppManager.getAppManager().removedAlllActivity(ChangePasswordActivity.this);
//                finish();
                break;
            case Constans.ERROR:
                data = (String) ecEvent.getData();
                showErrorToast(data);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void setLisenter() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change:
                changePwd();
                break;
        }
    }

}
