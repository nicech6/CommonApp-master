package com.mvp_0726.project_0726.online_unit.ui.activity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.client.HomeNumberBean;
import com.mvp_0726.common.base.codereview.BaseActivity;
import com.mvp_0726.common.event.CommonEvent;
import com.mvp_0726.common.utils.Constans;
import com.mvp_0726.common.utils.StatusBarUtil;
import com.mvp_0726.common.utils.ViewUtils;
import com.mvp_0726.common.view.DrawableCenterTextView;
import com.mvp_0726.project_0726.online_unit.contract.SettingOnlinePoliceContract;
import com.mvp_0726.project_0726.online_unit.presenter.SettingOnlinePolicePresenter;
import com.mvp_0726.project_0726.online_unit.ui.fragment_online.SettingPoliceOnlineFragment;
import com.mvp_0726.project_0726.utils.StringUtils;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Const;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.util.SharedPreUtil;
import com.project.wisdomfirecontrol.firecontrol.base.MyApplication;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.EquipmentCount;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2018/6/26.
 * 联网，设备，报警故障
 */

public class SettingPoliceOnlineActivity extends BaseActivity implements SettingOnlinePoliceContract.View {

    private DrawableCenterTextView tv_item_name_1, tv_item_name_2, tv_item_name_3;
    private TextView tv_rv_item_num_1, tv_rv_item_num_2, tv_rv_item_num_3, tv_company_name;
    private GradientDrawable myGrad1, myGrad2, myGrad3;
    private static final String TAG = "tag";
    private LinearLayout ll_item_tab_1, ll_item_tab_2, ll_item_tab_3;
    private FrameLayout framelayout;
    private FragmentManager mFragmentManager;
    private FragmentTransaction fragmentTransaction;

    public static String state = "";
    private String pid;
    private SettingOnlinePolicePresenter presenter;
    private boolean mIsDanwei;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_settingpoliceonline;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        presenter = new SettingOnlinePolicePresenter(this, this);

        View view = findViewById(R.id.space);

        view.setBackground(mActivity.getResources().getDrawable(R.drawable.title_toolbar_bg_blue));
        StatusBarUtil.setFadeStatusBarHeight(mActivity, view);

        ll_item_tab_1 = findViewById(R.id.ll_item_tab_1);
        ll_item_tab_2 = findViewById(R.id.ll_item_tab_2);
        ll_item_tab_3 = findViewById(R.id.ll_item_tab_3);

        framelayout = findViewById(R.id.framelayout);

        tv_rv_item_num_1 = findViewById(R.id.tv_rv_item_num_1);
        tv_rv_item_num_2 = findViewById(R.id.tv_rv_item_num_2);
        tv_rv_item_num_3 = findViewById(R.id.tv_rv_item_num_3);
        myGrad1 = (GradientDrawable) tv_rv_item_num_1.getBackground();
        myGrad2 = (GradientDrawable) tv_rv_item_num_2.getBackground();
        myGrad3 = (GradientDrawable) tv_rv_item_num_3.getBackground();

        myGrad1.setColor(getResources().getColor(R.color.mvp_title_blue));
        myGrad2.setColor(getResources().getColor(R.color.mvp_home_red));
        myGrad3.setColor(getResources().getColor(R.color.yellow));
        tv_rv_item_num_1.setText("0");
        tv_rv_item_num_2.setText("0");
        tv_rv_item_num_3.setText("0");
        if (com.mvp_0726.common.utils.Global.isPad()) {
            ViewUtils.setTextViewRl(tv_rv_item_num_1, (int) com.mvp_0726.common.utils.Global.mScreenWidth / 8,
                    0, (int) com.mvp_0726.common.utils.Global.mScreenWidth / 8, 0);
            ViewUtils.setTextViewRl(tv_rv_item_num_2, (int) com.mvp_0726.common.utils.Global.mScreenWidth / 8,
                    0, (int) com.mvp_0726.common.utils.Global.mScreenWidth / 8, 0);
            ViewUtils.setTextViewRl(tv_rv_item_num_3, (int) com.mvp_0726.common.utils.Global.mScreenWidth / 8,
                    0, (int) com.mvp_0726.common.utils.Global.mScreenWidth / 8, 0);
        }
        tv_item_name_1 = findViewById(R.id.tv_item_name_1);
        tv_item_name_2 = findViewById(R.id.tv_item_name_2);
        tv_item_name_3 = findViewById(R.id.tv_item_name_3);

        tv_company_name = findViewById(R.id.tv_company_name);

        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra("INTENT_KEY");
//        mIsDanwei = intent.getBooleanExtra("isDanwei", false);
        showTxt();
        if (TextUtils.isEmpty(stringExtra)) {
            return;
        }
        initDefaultFragment(stringExtra);
    }


    //初始化默认fragment的加载
    private void initDefaultFragment(String stringExtra) {
        //获取碎片管理者
        mFragmentManager = getSupportFragmentManager();
        //开启一个事务
        fragmentTransaction = mFragmentManager.beginTransaction();
        //add：往碎片集合中添加一个碎片；
        //replace：移除之前所有的碎片，替换新的碎片（remove和add的集合体）(很少用，不推荐，因为是重新加载，所以消耗流量)
        //参数：1.公共父容器的的id  2.fragment的碎片
        if (stringExtra.equals(Const.GO_SETTINGONLINE_FIRST)) {
            if (mIsDanwei) {
                tv_title.setText("联网单位");
            } else {
                tv_title.setText("联网设备");
            }
//            fragmentTransaction.add(R.id.framelayout, new OnlineSettingFragment());
            ll_item_tab_1.setBackgroundColor(getResources().getColor(R.color.list_divider));
            ll_item_tab_2.setBackgroundColor(getResources().getColor(R.color.white));
            ll_item_tab_3.setBackgroundColor(getResources().getColor(R.color.white));
            state = "";
        } else if (stringExtra.equals(Const.GO_SETTINGONLINE_SECOND)) {
            if (mIsDanwei) {
                tv_title.setText("报警单位");
            } else {
                tv_title.setText("报警设备");
            }
//            fragmentTransaction.add(R.id.framelayout, new PoliceSettingFragment());
            ll_item_tab_2.setBackgroundColor(getResources().getColor(R.color.list_divider));
            ll_item_tab_1.setBackgroundColor(getResources().getColor(R.color.white));
            ll_item_tab_3.setBackgroundColor(getResources().getColor(R.color.white));
            state = "2";
        } else {
            if (mIsDanwei) {
                tv_title.setText("故障单位");
            } else {
                tv_title.setText("故障设备");
            }
            ll_item_tab_3.setBackgroundColor(getResources().getColor(R.color.list_divider));
            ll_item_tab_2.setBackgroundColor(getResources().getColor(R.color.white));
            ll_item_tab_1.setBackgroundColor(getResources().getColor(R.color.white));
            state = "1";
        }
        fragmentTransaction.add(R.id.framelayout, new SettingPoliceOnlineFragment());

        fragmentTransaction.addToBackStack(null);

        //提交事务
        fragmentTransaction.commit();
    }

    private void showTxt() {
        if (mIsDanwei){
            tv_item_name_1.setText("联网单位");
            tv_item_name_2.setText("报警单位");
            tv_item_name_3.setText("故障单位");
        }else {
            tv_item_name_1.setText(getResources().getString(R.string.netdevice_url));
            tv_item_name_2.setText(getResources().getString(R.string.firealarm_url));
            tv_item_name_3.setText(getResources().getString(R.string.deviceorder_url));
        }
        tv_item_name_1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.icon_lwsb1), null, null, null);
        tv_item_name_2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.icon_bj), null, null, null);
        tv_item_name_3.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.icon_gzsb), null, null, null);

        ll_item_tab_1.setBackgroundColor(getResources().getColor(R.color.list_divider));
        ll_item_tab_2.setBackgroundColor(getResources().getColor(R.color.white));
        ll_item_tab_3.setBackgroundColor(getResources().getColor(R.color.white));
    }


    @Override
    protected void setLisenter() {
        ll_item_tab_1.setOnClickListener(this);
        ll_item_tab_2.setOnClickListener(this);
        ll_item_tab_3.setOnClickListener(this);

    }

    @Override
    public void initData() {
        getEquipmentCount();
        String companyName = SharedPreUtil.getString(Global.mContext, "companyName", "");
        tv_company_name.setText(companyName);
    }

    //    获取条数
    private void getEquipmentCount() {

        boolean networkConnected = MyApplication.getInstance().isNetworkConnected();
        if (!networkConnected) {
            showErrorToast("请检查网络是否正常");
        }
        pid = StringUtils.getUserPid(Global.mContext);
        if (presenter != null) {
            presenter.getEquipmentCount(pid);
        }
    }

    @Override
    protected void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.ll_item_tab_1:
                tv_title.setText(getResources().getString(R.string.netdevice_url));
                state = "";
                ll_item_tab_1.setBackgroundColor(getResources().getColor(R.color.list_divider));
                ll_item_tab_2.setBackgroundColor(getResources().getColor(R.color.white));
                ll_item_tab_3.setBackgroundColor(getResources().getColor(R.color.white));
                EventBus.getDefault().postSticky(new CommonEvent(Constans.SETTINGFIRSTSUCESS, state));
                break;
            case R.id.ll_item_tab_2:
                tv_title.setText(getResources().getString(R.string.firealarm_url));
                ll_item_tab_1.setBackgroundColor(getResources().getColor(R.color.white));
                ll_item_tab_2.setBackgroundColor(getResources().getColor(R.color.list_divider));
                ll_item_tab_3.setBackgroundColor(getResources().getColor(R.color.white));
                EventBus.getDefault().postSticky(new CommonEvent(Constans.SETTINGSECONDSUCESS, "2"));
                break;
            case R.id.ll_item_tab_3:
                tv_title.setText(getResources().getString(R.string.deviceorder_url));
                ll_item_tab_1.setBackgroundColor(getResources().getColor(R.color.white));
                ll_item_tab_2.setBackgroundColor(getResources().getColor(R.color.white));
                ll_item_tab_3.setBackgroundColor(getResources().getColor(R.color.list_divider));
                EventBus.getDefault().postSticky(new CommonEvent(Constans.SETTINGTHIRDSUCESS, "1"));
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void disposeCommonEvent(CommonEvent ecEvent) {
        switch (ecEvent.getWhat()) {
            case Constans.EQUIPMENTCOUNTSUCESS:
                EquipmentCount.DataBean data = (EquipmentCount.DataBean) ecEvent.getData();
                showEquipmentCount(data);
                break;
            case Constans.CLIENTNUMBER:
                HomeNumberBean.ResultBean resultBean = (HomeNumberBean.ResultBean) ecEvent.getData();
                tv_rv_item_num_1.setText(String.valueOf(resultBean.getOnlineCount()));
                tv_rv_item_num_2.setText(String.valueOf(resultBean.getAlarmCount()));
                tv_rv_item_num_3.setText(String.valueOf(resultBean.getFaultCount()));
                break;
        }
    }

    @Override
    protected void onStop() {
        state = "";
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void showEquipmentCount(EquipmentCount.DataBean data) {
        tv_rv_item_num_1.setText(String.valueOf(data.getSensorcount()));
        tv_rv_item_num_2.setText(String.valueOf(data.getBaojingcount()));
        tv_rv_item_num_3.setText(String.valueOf(data.getGuzhangcount()));
    }


}
