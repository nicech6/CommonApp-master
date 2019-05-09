package com.project.wisdomfirecontrol.firecontrol.ui.activity_common;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.util.SharedPreUtil;
import com.project.wisdomfirecontrol.common.util.StringUtils;
import com.project.wisdomfirecontrol.common.util.VersionCodeAndNameUtils;
import com.project.wisdomfirecontrol.firecontrol.download.UpdateManager;

/**
 * Created by Administrator on 2017/7/17.版本号
 */

public class AboutVersionCodeActivity extends BaseActivity {
    private TextView mTvTitle, mTvPhontVersioncode, mTvAppVersioncode, tv_app_versioncode_des;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_aboutversioncode;
    }

    @Override
    public void initView() {
        mTvTitle = findView(R.id.tv_title);
        mTvPhontVersioncode = findView(R.id.tv_phone_versioncode);
        mTvAppVersioncode = findView(R.id.tv_app_versioncode);
        tv_app_versioncode_des = findView(R.id.tv_app_versioncode_des);
        mTvTitle.setText(R.string.version_about);
        getAppVersionCode();
        checkVersion();
    }

    //    检查版本更新
    private boolean HasCheckUpdate = false;
    private UpdateManager mUpdateManager;

    private void checkVersion() {
        if (!HasCheckUpdate) {
            mUpdateManager = new UpdateManager(AboutVersionCodeActivity.this, true);
            mUpdateManager.checkVersion();
            HasCheckUpdate = true;
        }
    }

    //    显示版本信息
    @SuppressLint("SetTextI18n")
    private void getAppVersionCode() {
        String des = "";
        String description = SharedPreUtil.getString(Global.mContext, "description", "");
        if (!StringUtils.isEmpty(description)) {
            if (description.contains(";")) {
                String[] split = description.split(";");
                for (String aSplit : split) {
                    if (!StringUtils.isEmpty(aSplit)) {
                        des = des + "\r\n" + aSplit;
                    }
                }

            } else {
                des = "\r\n" + "1." + description;
            }
        }
        String versionName = VersionCodeAndNameUtils.getVersionName(this);
        mTvAppVersioncode.setText(getString(R.string.about_app_version) + versionName);
        if (StringUtils.isEmpty(des)) {
            des = "\r\n" + getString(R.string.no_update_des);
        }
        tv_app_versioncode_des.setText(getString(R.string.update_des) + "\r\n" + des);

    }


    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v, int id) {

    }

    //Global.showToast("aaaaaaaaaaaa");

}
