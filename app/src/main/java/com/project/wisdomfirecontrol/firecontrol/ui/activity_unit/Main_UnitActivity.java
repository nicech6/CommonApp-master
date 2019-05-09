package com.project.wisdomfirecontrol.firecontrol.ui.activity_unit;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.firecontrol.download.UpdateManager;

import java.util.ArrayList;
import java.util.List;

public class Main_UnitActivity extends BaseActivity {

    /*2018-04-12 修改*/
    private long exitTime;
    List<Fragment> fragments = new ArrayList<>();

    private FrameLayout framelayout;
    private FragmentManager mFragmentManager;
    private Unit_StatementChangeActivity mFragmentOne;
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
//        initDefaultFragment();
//    }
//
//    //初始化默认fragment的加载
//    private void initDefaultFragment() {
//        //实例化FragmentOne
//        mFragmentOne = new Unit_StatementChangeActivity();
//        //获取碎片管理者
//        mFragmentManager = getSupportFragmentManager();
//
//        //开启一个事务
//        fragmentTransaction = mFragmentManager.beginTransaction();
//        //add：往碎片集合中添加一个碎片；
//        //replace：移除之前所有的碎片，替换新的碎片（remove和add的集合体）(很少用，不推荐，因为是重新加载，所以消耗流量)
//        //参数：1.公共父容器的的id  2.fragment的碎片
//        fragmentTransaction.add(R.id.framelayout, new Unit_StatementChangeActivity());
//        fragmentTransaction.addToBackStack(null);
//
//        //提交事务
//        fragmentTransaction.commit();
//        fragmentNow = mFragmentOne;

    }

    //    检查版本更新
    private boolean HasCheckUpdate = false;
    private UpdateManager mUpdateManager;

    private void checkVersion() {
        if (!HasCheckUpdate) {
            mUpdateManager = new UpdateManager(Main_UnitActivity.this, true);
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
                Main_UnitActivity.this.finish();
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
