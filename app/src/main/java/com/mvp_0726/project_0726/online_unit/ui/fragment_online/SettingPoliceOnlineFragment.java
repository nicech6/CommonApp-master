package com.mvp_0726.project_0726.online_unit.ui.fragment_online;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.mvp_0726.common.base.codereview.BaseFragment;
import com.mvp_0726.common.event.CommonEvent;
import com.mvp_0726.common.utils.Constans;
import com.mvp_0726.project_0726.online_unit.contract.SettingOnlinePoliceContract;
import com.mvp_0726.project_0726.online_unit.event.SettingPoliceEvent;
import com.mvp_0726.project_0726.online_unit.presenter.SettingOnlinePolicePresenter;
import com.mvp_0726.project_0726.online_unit.ui.activity.SettingPoliceOnlineActivity;
import com.mvp_0726.project_0726.utils.StringUtils;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.firecontrol.base.MyApplication;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.GetSenorcountDataBean;
import com.project.wisdomfirecontrol.firecontrol.ui.adaper_Lv.IndicatorFragmentAdapter;
import com.project.wisdomfirecontrol.firecontrol.ui.view.TabPageIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2018/8/10.
 */

public class SettingPoliceOnlineFragment extends BaseFragment implements SettingOnlinePoliceContract.View {

    public static String state = "";
    private TabPageIndicator indicator;
    private ViewPager viewPager;
    private String[] titles;
    private List<String> list;
    private SettingOnlinePolicePresenter presenter;
    private String pid;
    private SlidingTabLayout stl;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_onlinesetting;
    }

    @Override
    protected void initView() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        presenter = new SettingOnlinePolicePresenter(this, (SettingPoliceOnlineActivity) getActivity());

        indicator = rootView.findViewById(R.id.indicator1);
        viewPager = rootView.findViewById(R.id.viewPager);
        stl = rootView.findViewById(R.id.stl);

        list = new ArrayList<>();
        list.add("全部（0）");
        list.add("电气火灾（0）");
        list.add("烟感器（0）");
        list.add("气感（0）");
        list.add("消防栓（0）");
        list.add("灭火器（0）");
//        list.add("电缆（0）");
    }

    private void initViewPager(String[] titles) {

//        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(new AllSettingPoliceTrubleFragment());
//        fragments.add(new ElectricCableFragment());
//        fragments.add(new SmokeDetectorFragment());
//        fragments.add(new GasSensorFragment());
//        fragments.add(new FireHydrantFragment());
//        fragments.add(new OffFireSensorFragment());
//
//        viewPager.setAdapter(new IndicatorFragmentAdapter(
//                getChildFragmentManager(), fragments, titles));
////        indicator.setViewPager(viewPager);
//        stl.setViewPager(viewPager);
    }

    private void setTabPagerIndicator() {
        indicator.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_NOWEIGHT_EXPAND_NOSAME);// 设置模式，一定要先设置模式
        indicator.setDividerColor(getResources().getColor(R.color.white));// 设置分割线的颜色
        indicator.setDividerPadding(Global.dp2px(50));
//        indicator.setIndicatorColor(getResources().getColor(R.color.blue));// 设置底部导航线的颜色
        indicator.setIndicatorColorResource(R.color.blue);
        indicator.setTextColorSelected(getResources().getColor(R.color.blue));// 设置tab标题选中的颜色
        indicator.setTextColor(getResources().getColor(R.color.gray));// 设置tab标题未被选中的颜色
        indicator.setTextSize(Global.sp2px(14));// 设置字体大小
    }

    @Override
    protected void setLisenter() {
    }


    //    获取条数
    private void getSenorcount(String state) {

        boolean networkConnected = MyApplication.getInstance().isNetworkConnected();
        if (!networkConnected) {
            showErrorToast("请检查网络是否正常");
        }
        pid = StringUtils.getUserPid(Global.mContext);
        if (presenter != null) {
//            ApiRetrofit.changeApiBaseUrl(NetworkUrl.ANDROID_TEST_SERVICE);
            presenter.getSenorcount(pid, state);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void disposeSettingPoliceEvent(SettingPoliceEvent event) {
        switch (event.getWhat()) {
            case Constans.SETTINGSUCESS:
                List<GetSenorcountDataBean> data = (List<GetSenorcountDataBean>) event.getData();
                showNetDatas(data);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void disposeCommonEvent(CommonEvent event) {
        switch (event.getWhat()) {
            case Constans.SETTINGFIRSTSUCESS://联网单位
                state = (String) event.getData();
                getSenorcount(state);
                break;
            case Constans.SETTINGSECONDSUCESS://报警
                state = (String) event.getData();
                getSenorcount(state);
                break;
            case Constans.SETTINGTHIRDSUCESS://故障
                state = (String) event.getData();
                getSenorcount(state);
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void initData() {
        state = SettingPoliceOnlineActivity.state;
        getSenorcount(state);
    }

    private void showNetDatas(List<GetSenorcountDataBean> data) {
        List<Fragment> fragments = new ArrayList<>();
        Collections.sort(data, new Comparator<GetSenorcountDataBean>() {
            public int compare(GetSenorcountDataBean arg0, GetSenorcountDataBean arg1) {
                return arg1.getNum().compareTo(arg0.getNum());
            }
        });
        for (int i = 0; i < data.size(); i++) {
            if ("全部".equals(data.get(i).getTypename())){
                list.set(0, data.get(i).getTypename() + "(" + data.get(i).getNum() + ")");
                data.remove(i);
            }
        }
        fragments.add(new AllSettingPoliceTrubleFragment());
//        for (GetSenorcountDataBean datum : data) {
//            String typename = datum.getTypename();
//            int datumNum = datum.getNum();
//            if (typename.equals("全部")) {
//                list.set(0, "全部（" + String.valueOf(datumNum) + "）");
//            } else if (typename.equals("烟感器")) {
//                list.set(2, "烟感器（" + String.valueOf(datumNum) + "）");
//            } else if (typename.equals("气感")) {
//                list.set(3, "气感（" + String.valueOf(datumNum) + "）");
//            } else if (typename.equals("消防栓")) {
//                list.set(4, "消防栓（" + String.valueOf(datumNum) + "）");
//            } else if (typename.equals("灭火器")) {
//                list.set(5, "灭火器（" + String.valueOf(datumNum) + "）");
//            } else {
//                list.set(1, "电气火灾（" + String.valueOf(datumNum) + "）");
////            } else if (typename.equals("电气火灾")) {
////                list.set(1, "烟感（" + String.valueOf(datumNum) + "）");
//            }
//        }
        for (int i = 1; i < 6; i++) {
            list.set(i, data.get(i).getTypename() + "(" + data.get(i).getNum() + ")");
            String typename = data.get(i).getTypename();
            if (typename.equals("全部")) {

//                fragments.add(new AllSettingPoliceTrubleFragment());
            } else if (typename.equals("烟感器")) {
                fragments.add(new SmokeDetectorFragment());
            } else if (typename.equals("气感器")) {
                fragments.add(new GasSensorFragment());
            } else if (typename.equals("消防栓")) {
                fragments.add(new FireHydrantFragment());
            } else if (typename.equals("灭火器")) {
                fragments.add(new OffFireSensorFragment());
            } else if (typename.equals("电气火灾")){
                fragments.add(new ElectricCableFragment());

//            } else if (typename.equals("电气火灾")) {
//                list.set(1, "烟感（" + String.valueOf(datumNum) + "）");
            }
        }

        titles = list.toArray(new String[list.size()]);
        initViewPager(titles);

        viewPager.setAdapter(new IndicatorFragmentAdapter(
                getChildFragmentManager(), fragments, titles));
//        indicator.setViewPager(viewPager);
        stl.setViewPager(viewPager);
//        setTabPagerIndicator();
    }

    @Override
    public void onDestroy() {
        list.clear();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @Override
    protected void widgetClick(View v) {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}

