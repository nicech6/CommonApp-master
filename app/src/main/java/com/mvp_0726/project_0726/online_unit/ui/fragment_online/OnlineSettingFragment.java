package com.mvp_0726.project_0726.online_unit.ui.fragment_online;

import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseFragment;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.GetSenorcountBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.GetSenorcountDataBean;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.CommonProtocol;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.IHttpService;
import com.project.wisdomfirecontrol.firecontrol.ui.adaper_Lv.IndicatorFragmentAdapter;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.DatasUtils;
import com.project.wisdomfirecontrol.firecontrol.ui.view.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/26.
 */

public class OnlineSettingFragment extends BaseFragment {

    private TabPageIndicator indicator;
    private ViewPager viewPager;
    private String[] titles;
    private CommonProtocol commonProtocol;
    private List<String> list;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_onlinesetting;
    }

    @Override
    public void initView() {
        indicator = findView(R.id.indicator1);
        viewPager = findView(R.id.viewPager);

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

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new AllSettingPoliceTrubleFragment());
        fragments.add(new ElectricCableFragment());
        fragments.add(new SmokeDetectorFragment());
        fragments.add(new GasSensorFragment());
        fragments.add(new FireHydrantFragment());
        fragments.add(new OffFireSensorFragment());

        viewPager.setAdapter(new IndicatorFragmentAdapter(
                getChildFragmentManager(), fragments, titles));

        indicator.setViewPager(viewPager);
    }

    private void setTabPagerIndicator() {
        indicator.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_NOWEIGHT_EXPAND_NOSAME);// 设置模式，一定要先设置模式
        indicator.setDividerColor(getResources().getColor(R.color.blue));// 设置分割线的颜色
        indicator.setDividerPadding(Global.dp2px(10));
//        indicator.setIndicatorColor(getResources().getColor(R.color.blue));// 设置底部导航线的颜色
        indicator.setIndicatorColorResource(R.color.blue);
        indicator.setTextColorSelected(getResources().getColor(R.color.blue));// 设置tab标题选中的颜色
        indicator.setTextColor(getResources().getColor(R.color.gray));// 设置tab标题未被选中的颜色
        indicator.setTextSize(Global.sp2px(14));// 设置字体大小
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onStart() {
        super.onStart();

        getNetDatas();
    }

    private void getNetDatas() {
        String pid = DatasUtils.getUserPid(getContext());
//        showWaitDialog(getActivity(), getString(R.string.inupdate));
        commonProtocol = new CommonProtocol();
        String state = "";
//        RetrofitManager.changeApiBaseUrl(NetworkUrl.ANDROID_TEST_SERVICE);
        commonProtocol.getsenorcount(this, pid, state);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onHttpSuccess(int reqType, Message obj) {
        super.onHttpSuccess(reqType, obj);
        dismissWaitDialog();

        if (reqType == IHttpService.TYPE_SETTINGMANAGER) {
            GetSenorcountBean bean = (GetSenorcountBean) obj.obj;
            if (bean != null) {
                List<GetSenorcountDataBean> data = bean.getData();
                if (data.size() > 0) {
                    showNetDatas(data);
                } else {
                    showToast("暂无信息");
                }
            }
        }
    }

    private void showNetDatas(List<GetSenorcountDataBean> data) {
        for (GetSenorcountDataBean datum : data) {
            String typename = datum.getTypename();
            int datumNum = datum.getNum();
            if (typename.equals("全部")) {
                list.set(0, "全部（" + String.valueOf(datumNum) + "）");
            } else if (typename.equals("烟感器")) {
                list.set(2, "烟感器（" + String.valueOf(datumNum) + "）");
            } else if (typename.equals("气感")) {
                list.set(3, "气感（" + String.valueOf(datumNum) + "）");
            } else if (typename.equals("消防栓")) {
                list.set(4, "消防栓（" + String.valueOf(datumNum) + "）");
            } else if (typename.equals("灭火器")) {
                list.set(5, "灭火器（" + String.valueOf(datumNum) + "）");
            } else {
                list.set(1, "电气火灾（" + String.valueOf(datumNum) + "）");
//            } else if (typename.equals("电气火灾")) {
//                list.set(1, "烟感（" + String.valueOf(datumNum) + "）");
            }
        }

        titles = list.toArray(new String[list.size()]);
        initViewPager(titles);
        setTabPagerIndicator();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        list.clear();
    }

    @Override
    public void onHttpError(int reqType, String error) {
        super.onHttpError(reqType, error);
        dismissWaitDialog();
    }

    @Override
    public void initTitleHeight(LinearLayout linearLayout) {

    }

    @Override
    public void onClick(View v, int id) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
