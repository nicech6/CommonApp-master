package com.project.wisdomfirecontrol.firecontrol.ui.activity_supevise;

import android.content.Intent;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.util.SharedPreUtil;
import com.project.wisdomfirecontrol.firecontrol.download.UpdateManager;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.GetCompanyRegisterInfosBean;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.CommonProtocol;
import com.project.wisdomfirecontrol.firecontrol.ui.fragment_supevise.Supevise_StatementFragment;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.DatasUtils;

import java.util.ArrayList;
import java.util.List;

public class Main_SupevisetChangeActivity extends BaseActivity {
    /*2018-04-12 修改*/
    private long exitTime;
    List<Fragment> fragments = new ArrayList<>();

    private FrameLayout framelayout;
    private FragmentManager mFragmentManager;
    private Supevise_StatementFragment mFragmentOne;
    private Fragment fragmentNow;
    private FragmentTransaction fragmentTransaction;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main_supevisetchange;
    }

    @Override
    public void initView() {
        framelayout = findView(R.id.framelayout);
        checkVersion();
        initDefaultFragment();
    }

    //初始化默认fragment的加载
    private void initDefaultFragment() {
        //实例化FragmentOne
        mFragmentOne = new Supevise_StatementFragment();
        //获取碎片管理者
        mFragmentManager = getSupportFragmentManager();

        //开启一个事务
        fragmentTransaction = mFragmentManager.beginTransaction();
        //add：往碎片集合中添加一个碎片；
        //replace：移除之前所有的碎片，替换新的碎片（remove和add的集合体）(很少用，不推荐，因为是重新加载，所以消耗流量)
        //参数：1.公共父容器的的id  2.fragment的碎片
        fragmentTransaction.add(R.id.framelayout, new Supevise_StatementFragment());
        fragmentTransaction.addToBackStack(null);

        //提交事务
        fragmentTransaction.commit();
        fragmentNow = mFragmentOne;

    }

    //    检查版本更新
    private boolean HasCheckUpdate = false;
    private UpdateManager mUpdateManager;

    private void checkVersion() {
        if (!HasCheckUpdate) {
            mUpdateManager = new UpdateManager(Main_SupevisetChangeActivity.this, true);
            mUpdateManager.checkVersion();
            mUpdateManager.isAndoird8();
            HasCheckUpdate = true;
        }
    }

    @Override
    public void initTitleHeight(LinearLayout linearLayout) {
        super.initTitleHeight(linearLayout);
    }


    @Override
    public void initListener() {
    }

    @Override
    public void initData() {
        getCompanyInfosDatas();
    }


    private void getCompanyInfosDatas() {
        String pid = DatasUtils.getUserPid(this);
        CommonProtocol commonProtocol = new CommonProtocol();
        commonProtocol.getorgandetailbyid(this, pid);
    }

    @Override
    public void onHttpSuccess(int reqType, Message obj) {
        super.onHttpSuccess(reqType, obj);
        dismissWaitDialog();

        GetCompanyRegisterInfosBean bean = (GetCompanyRegisterInfosBean) obj.obj;
        if (bean != null) {
            String companyName = bean.getData().getOrgName();
            String principal = bean.getData().getPrincipal();
            String principalTel = bean.getData().getPrincipalTel();
            String firstName = bean.getData().getName1();
            String firstPhone = bean.getData().getPhone1();
            String address = bean.getData().getAddress();
            if (!TextUtils.isEmpty(companyName) && !TextUtils.isEmpty(principal) &&
                    !TextUtils.isEmpty(principalTel) && !TextUtils.isEmpty(address)
                    && !TextUtils.isEmpty(firstPhone) && !TextUtils.isEmpty(firstPhone)) {
                SharedPreUtil.saveString(Global.mContext, "address", address);
                SharedPreUtil.saveString(Global.mContext, "companyName", companyName);
                SharedPreUtil.saveString(Global.mContext, "principal", principal);
                SharedPreUtil.saveString(Global.mContext, "principalTel", principalTel);
                SharedPreUtil.saveString(Global.mContext, "firstName", firstName);
                SharedPreUtil.saveString(Global.mContext, "firstPhone", firstPhone);
            }
        }
    }

    @Override
    public void onHttpError(int reqType, String error) {
        super.onHttpError(reqType, error);
        dismissWaitDialog();
    }

    @Override
    public void onClick(View v, int id) {
    }

    /**
     * 重写返回键，实现双击退出效果
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                showToast(this.getResources().getString(R.string.btn_again_out));
                exitTime = System.currentTimeMillis();
            } else {
                Main_SupevisetChangeActivity.this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10086) {
            if (mUpdateManager != null) {
                mUpdateManager.isAndoird8();
            }
        }
//        else if (resultCode == RESULT_OK) {
//            MyFragment myFragment = (MyFragment) fragments.get(1);
//            myFragment.onActivityResult(requestCode, resultCode, data);
//        }
    }

}
