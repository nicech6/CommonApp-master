package com.mvp_0726.project_0726.home.ui;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mvp_0726.common.base.codereview.BaseActivity;
import com.mvp_0726.common.event.CommonEvent;
import com.mvp_0726.common.event.NetWorkChangeEvent;
import com.mvp_0726.common.utils.Constans;
import com.mvp_0726.common.utils.Global;
import com.mvp_0726.common.utils.ImageUtils;
import com.mvp_0726.common.utils.StatusBarUtil;
import com.mvp_0726.common.view.popupwindow.PopupWindowHelper;
import com.mvp_0726.common.view.xMarqueeView.XMarqueeView;
import com.mvp_0726.project_0726.constant.Constant;
import com.mvp_0726.project_0726.home.adapter.HomeDatasChangeAdapter;
import com.mvp_0726.project_0726.home.adapter.MarqueeViewAdapter;
import com.mvp_0726.project_0726.home.contract.HomeContract;
import com.mvp_0726.project_0726.home.model.MarqueeDataBean;
import com.mvp_0726.project_0726.home.model.OrgandetailDataBean;
import com.mvp_0726.project_0726.home.presenter.HomePresenter;
import com.mvp_0726.project_0726.login.modle.DanWeiBean;
import com.mvp_0726.project_0726.online_unit.ui.activity.SettingPoliceOnlineActivity;
import com.mvp_0726.project_0726.utils.StringUtils;
import com.mvp_0726.project_0726.utils.XunFeiUtils;
import com.mvp_0726.project_0726.web.ui.WebH5Activity;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Const;
import com.project.wisdomfirecontrol.common.util.SharedPreUtil;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.EquipmentCount;
import com.project.wisdomfirecontrol.firecontrol.model.bean.login.LoginChangeDataBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.login.MenuDatasBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.login.MenuDatasBeanX;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_common.OnlineUnitActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_common.SystemSettingActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_setting.SettingManagerActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_video.VideoSytemListActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;


/**
 * Created by Administrator on 2018/7/30.
 * 第二期主 ——首页
 */

public class MvpMainActivity extends BaseActivity implements HomeContract.View {
    private static final String TAG = "TAG";
    private RecyclerView recyclerView;
    private Banner banner;
    private List<?> images;
    private TextView tv_app_company, tv_app_company_type;
    private TextView tv_item_equipment_count, tv_item_equipment_txt, tv_item_callpolice_count,
            tv_item_callpolice_txt, tv_item_malfunction_count, tv_item_malfunction_txt;

    private LoginChangeDataBean loginBean;
    List<String> list = new ArrayList<>();
    private LinearLayout ll_equipment_police_malfunction, ll_marqueeview, ll_juese;
    private LinearLayout ll_home_equipment, ll_home_callpolice, ll_home_malfunction;
    private ImageView iv_safe;
    private XMarqueeView marqueeview;

    //    private List<MenuDatasBeanX> menuDatas = new ArrayList<>();
    private List<MenuDatasBean> menuDatasList = new ArrayList<>();
    //    private HomeAdapter homeAdapter;
    private HomeDatasChangeAdapter homeAdapter;

    private MarqueeViewAdapter marqueeAdapter;
    private PopupWindowHelper popupWindowHelper;
    private String pid;

    private OrgandetailDataBean dataBean = null;
    private String companyName;
    private String principal;
    private String principalTel;
    private String personalCount;
    private String companyArea;
    private String nameFirst;
    private String firstPhone;
    private List<MarqueeDataBean> marqueeList;
    private HomePresenter presenter;

    private boolean isUpdateInfo = true;
    private Handler handlerdHandler;
    private static final int MSG_UPDATE_INFO = 0x110;
    private HandlerThread thread;
    private int count;
    private XunFeiUtils mXunFeiUtils;
    private Intent intent;
    private String INTENT_VALUE = "";


    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main_mvp;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        presenter = new HomePresenter(this, this);
        tv_title.setText(R.string.jiuan_xiaofng);
        iv_back.setVisibility(View.INVISIBLE);

        ll_juese = findViewById(R.id.ll_juese);
        tv_app_company = findViewById(R.id.tv_app_company);
        tv_app_company_type = findViewById(R.id.tv_app_company_type);

        ll_home_equipment = findViewById(R.id.ll_home_equipment);
        ll_home_callpolice = findViewById(R.id.ll_home_callpolice);
        ll_home_malfunction = findViewById(R.id.ll_home_malfunction);

        tv_app_company.setBackground(getResources().getDrawable(R.drawable.title_toolbar_bg_blue));
        tv_app_company_type.setBackground(getResources().getDrawable(R.drawable.home_toolbar_bg_blue));

        tv_app_company.setTextSize(15f);
        tv_app_company_type.setTextSize(15f);

//        StatusBarUtil.setFadeStatusBarHeight(mActivity, view);
        StatusBarUtil.setTranslateByColor(MvpMainActivity.this, Color.TRANSPARENT);//透明

        banner = findViewById(R.id.mybanner);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(MvpMainActivity.this, Const.COUNT));

        initViewById();
        initMyBanner();
    }


    private void initViewById() {
        ll_equipment_police_malfunction = findViewById(R.id.ll_equipment_police_malfunction);
        ll_marqueeview = findViewById(R.id.ll_marqueeview);
        ll_equipment_police_malfunction.setVisibility(View.VISIBLE);
        ll_marqueeview.setVisibility(View.GONE);
        marqueeview = findViewById(R.id.marqueeview);

        iv_safe = findViewById(R.id.iv_safe);
        iv_safe.setImageResource(R.drawable.img_anquan);

        tv_item_equipment_count = findViewById(R.id.tv_item_equipment_count);
        tv_item_equipment_txt = findViewById(R.id.tv_item_equipment_txt);

        tv_item_callpolice_count = findViewById(R.id.tv_item_callpolice_count);
        tv_item_callpolice_txt = findViewById(R.id.tv_item_callpolice_txt);

        tv_item_malfunction_count = findViewById(R.id.tv_item_malfunction_count);
        tv_item_malfunction_txt = findViewById(R.id.tv_item_malfunction_txt);

        tv_item_equipment_count.setTextSize(20f);
        tv_item_callpolice_count.setTextSize(20f);
        tv_item_malfunction_count.setTextSize(20f);

        tv_item_equipment_txt.setTextSize(13f);
        tv_item_callpolice_txt.setTextSize(13f);
        tv_item_malfunction_txt.setTextSize(13f);

        tv_item_equipment_count.setText("0");
        tv_item_callpolice_count.setText("无报警");
        tv_item_malfunction_count.setText("无故障");
        tv_item_equipment_count.setTextColor(getResources().getColor(R.color.home_item_count_equipment));
        tv_item_callpolice_count.setTextColor(getResources().getColor(R.color.home_item_count_callpolice_normal));
        tv_item_malfunction_count.setTextColor(getResources().getColor(R.color.home_item_count_malfunction_normal));

        tv_item_equipment_txt.setText(R.string.homt_equipment);
        tv_item_callpolice_txt.setText(R.string.homt_callpolice);
        tv_item_malfunction_txt.setText(R.string.homt_malfunction);
        tv_item_equipment_txt.setTextColor(getResources().getColor(R.color.mvp_home_item_txt));
        tv_item_callpolice_txt.setTextColor(getResources().getColor(R.color.mvp_home_item_txt));
        tv_item_malfunction_txt.setTextColor(getResources().getColor(R.color.mvp_home_item_txt));

        initViewHeightWidth(ll_equipment_police_malfunction, ll_marqueeview);

    }

    private void initViewHeightWidth(LinearLayout ll_equipment_police_malfunction, LinearLayout ll_marqueeview) {
        ViewGroup.LayoutParams params = ll_equipment_police_malfunction.getLayoutParams();
        ViewGroup.LayoutParams params_marqueeView = ll_marqueeview.getLayoutParams();
        if (Global.isPad()) {
            params.height = (int) Global.mScreenWidth / 5;
            params_marqueeView.height = (int) Global.mScreenHeight / 5;
        } else {
            params.height = (int) Global.mScreenWidth / 4;
            params_marqueeView.height = (int) Global.mScreenHeight / 4;
        }

        ll_equipment_police_malfunction.setLayoutParams(params);


        ll_marqueeview.setLayoutParams(params);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        recyclerView.getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    /*轮播图*/
    private void initMyBanner() {
        ViewGroup.LayoutParams params = banner.getLayoutParams();
        params.height = (int) Global.mScreenHeight / 4;
        banner.setLayoutParams(params);
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            loginBean = (LoginChangeDataBean) bundle.get("loginBean");
            if (loginBean != null) {
                initRecycler(loginBean);
            }
        }
        String orgShortName = SharedPreUtil.getString(this, "orgShortName", "");
        String companyType = SharedPreUtil.getString(this, "companyType", "");
        if (!TextUtils.isEmpty(orgShortName)) {
            tv_app_company.setText(orgShortName);
        }
        tv_app_company_type.setText(companyType);

        String imagthpath = SharedPreUtil.getString(this, "imagthpath", "");
        if (TextUtils.isEmpty(imagthpath)) {
            list.add(ImageUtils.useStytemResourceImages(this, R.drawable.baner_image_first));
            list.add(ImageUtils.useStytemResourceImages(this, R.drawable.banner_image_second));
            list.add(ImageUtils.useStytemResourceImages(this, R.drawable.banner_image_third));
        } else {
            String imageUrl = imagthpath.replace(" ", "");//去掉所用空格
            list.clear();
            String[] split = imageUrl.split(",");
            list.addAll(Arrays.asList(split));
        }
        bannerAnimotion(list);
        pid = loginBean.getPersonel().getPid();
        if (TextUtils.isEmpty(pid)) {
            pid = StringUtils.getUserPid(MvpMainActivity.this);
        }
        if (presenter != null) {
//            ApiRetrofit.changeApiBaseUrl(NetworkUrl.ANDROID_BAIDU_SERVICE);
            presenter.getEquipmentCount(pid);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
//                        ApiRetrofit.changeApiBaseUrl(NetworkUrl.ANDROID_TEST_SERVICE);
                        presenter.getOrgandetailDatas(pid);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        startOnlineTime();
    }

    private void startOnlineTime() {
        count = 0;
        isUpdateInfo = true;
        thread = new HandlerThread("MyHandlerThread");
        thread.start();//创建一个HandlerThread并启动它
        handlerdHandler = new Handler(thread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                checkForUpdate();
                if (isUpdateInfo) {
                    handlerdHandler.sendEmptyMessageDelayed(MSG_UPDATE_INFO, 2000);
                }
            }
        };
        handlerdHandler.sendEmptyMessage(MSG_UPDATE_INFO);
    }


    private void checkForUpdate() {
        try {
            //模拟耗时
            handlerdHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (count % 30 == 0) {
                        getMarqueeDatas(pid);
                        Log.d(TAG, "run:1111111 " + count);
                    }
                    count = count + 2;

                    if (count >= 7800) {
                        count = 0;
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*获取报警数据*/
    private void getMarqueeDatas(String pid) {
        if (TextUtils.isEmpty(pid)) {
            pid = StringUtils.getUserPid(MvpMainActivity.this);
        }
        if (presenter != null) {
//            ApiRetrofit.changeApiBaseUrl(NetworkUrl.ANDROID_BAIDU_SERVICE);
            presenter.getMarqueeDatas(pid);
        }
    }

    private void bannerAnimotion(List<String> list) {
        images = new ArrayList<>(list);
        //设置banner样式
//        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
//        //设置标题集合（当banner样式有显示title时）

//        banner.setBannerTitles(titles);
//        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(5000);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    protected void setLisenter() {
        tv_app_company.setOnClickListener(this);
        tv_app_company_type.setOnClickListener(this);

        ll_home_equipment.setOnClickListener(this);
        ll_home_callpolice.setOnClickListener(this);
        ll_home_malfunction.setOnClickListener(this);
    }

    private boolean isPopupWindow = true;

    @Override
    protected void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.tv_app_company_type:
                showCompanyInfos(true, dataBean);
                break;
            case R.id.ll_home_equipment:
                Intent intent = new Intent(MvpMainActivity.this, SettingPoliceOnlineActivity.class);
                intent.putExtra("INTENT_KEY", Const.GO_SETTINGONLINE_FIRST);
                super.startActivity(intent);
                break;
            case R.id.ll_home_callpolice:
                Intent intent_call = new Intent(MvpMainActivity.this, SettingPoliceOnlineActivity.class);
                intent_call.putExtra("INTENT_KEY", Const.GO_SETTINGONLINE_SECOND);
                super.startActivity(intent_call);
                break;
            case R.id.ll_home_malfunction:
                Intent intent_mal = new Intent(MvpMainActivity.this, SettingPoliceOnlineActivity.class);
                intent_mal.putExtra("INTENT_KEY", Const.GO_SETTINGONLINE_THIRD);
                super.startActivity(intent_mal);
                break;
        }

    }

    private void initMarqueeView(List<MarqueeDataBean> marqueeList) {
        int size = marqueeList.size();
        if (size > 0) {
            tv_app_company.setBackground(getResources().getDrawable(R.drawable.title_toolbar_bg_red));
            tv_app_company_type.setBackground(getResources().getDrawable(R.drawable.home_toolbar_bg_red));
            ll_equipment_police_malfunction.setVisibility(View.GONE);
            ll_marqueeview.setVisibility(View.VISIBLE);

            if (marqueeAdapter == null) {
                marqueeAdapter = new MarqueeViewAdapter(marqueeList, this);
                marqueeview.setAdapter(marqueeAdapter);
            } else {
                marqueeAdapter.notifyDataChanged();
            }

            MarqueeDataBean marqueeDataBean = marqueeList.get(0);

            isNotification(marqueeDataBean);

        } else {
            tv_app_company.setBackground(getResources().getDrawable(R.drawable.title_toolbar_bg_blue));
            tv_app_company_type.setBackground(getResources().getDrawable(R.drawable.home_toolbar_bg_blue));
            ll_equipment_police_malfunction.setVisibility(View.VISIBLE);
            ll_marqueeview.setVisibility(View.GONE);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void disposeCommonEvent(CommonEvent ecEvent) {
        switch (ecEvent.getWhat()) {
            case Constans.EQUIPMENTCOUNTSUCESS:
                EquipmentCount.DataBean data = (EquipmentCount.DataBean) ecEvent.getData();
                showEquipmentCount(data);

                break;
            case Constans.ERROR:
//                showErrorToast("加载失败");
                break;
            case Constans.MARQUEEERROR:
                if (presenter != null) {
                    presenter.getEquipmentCount(pid);
                }
                break;
            case Constans.COMPANYINFOSSUCESS://公司信息
                dataBean = (OrgandetailDataBean) ecEvent.getData();
                showCompanyInfos(false, dataBean);
                break;
            case Constans.MARQUEESUCESS://报警轮播信息
                marqueeList = (List<MarqueeDataBean>) ecEvent.getData();
                initMarqueeView(marqueeList);
                break;

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 0, sticky = true)
    public void disposeNetWorkChange(NetWorkChangeEvent netWorkChangeEvent) {
        switch (netWorkChangeEvent.getWhat()) {
            case Constans.NET_WORK_AVAILABLE://有网
                break;
            case Constans.NET_WORK_DISABLED://没网
                showErrorToast("当前无网络");
                break;
        }
    }

    private void showEquipmentCount(EquipmentCount.DataBean data) {
        String baojingcount = "";
        String sensorcount = String.valueOf(data.getSensorcount());
        int dataBaojingcount = data.getBaojingcount();
        String guzhangcount = String.valueOf(data.getGuzhangcount());
        if (guzhangcount.equals("0")) {
            guzhangcount = "无故障";
        }
        tv_item_equipment_count.setText(sensorcount);
        tv_item_malfunction_count.setText(guzhangcount);
        Log.d(TAG, "showEquipmentCount: " + sensorcount + " ==" + dataBaojingcount + " == " + guzhangcount);
        if (dataBaojingcount > 0) {
            baojingcount = String.valueOf(dataBaojingcount);
//            tv_app_company.setBackground(mActivity.getResources().getDrawable(R.drawable.title_toolbar_bg_red));
//            tv_app_company_type.setBackground(mActivity.getResources().getDrawable(R.drawable.home_toolbar_bg_red));
        } else {
            baojingcount = "无报警";
//            tv_app_company.setBackground(mActivity.getResources().getDrawable(R.drawable.title_toolbar_bg_blue));
//            tv_app_company_type.setBackground(mActivity.getResources().getDrawable(R.drawable.home_toolbar_bg_blue));
        }
        tv_item_callpolice_count.setText(baojingcount);
    }

    @SuppressLint("SetTextI18n")
    private void showCompanyInfos(boolean isPopupWindow, OrgandetailDataBean dataBean) {

        View view = Global.inflate(R.layout.moudle_home_popup_companyinfos, null);
        ImageView iv_popup_company_logo = view.findViewById(R.id.iv_popup_company_logo);
        iv_popup_company_logo.setImageResource(R.drawable.img_mr);
        if (dataBean != null) {
            companyName = dataBean.getOrgName();
            principal = dataBean.getPrincipal();
            principalTel = dataBean.getPrincipalTel();
            personalCount = dataBean.getRenshu();
            companyArea = dataBean.getArea() + "平方";
            nameFirst = dataBean.getName1();
            firstPhone = dataBean.getPhone1();
        } else {
            companyName = "无";
            principal = "无";
            principalTel = "无";
            personalCount = "无";
            companyArea = "无";
            nameFirst = "无";
            firstPhone = "无";
        }

        TextView company_name = view.findViewById(R.id.tv_popup_company_name);
        TextView company_pricipal = view.findViewById(R.id.tv_popup_company_pricipal);//负责人
        TextView company_phone = view.findViewById(R.id.tv_popup_company_phone);//电话
        TextView company_count = view.findViewById(R.id.tv_popup_company_count);//人数
        TextView company_area = view.findViewById(R.id.tv_popup_company_area);//面积

        SharedPreUtil.saveString(com.project.wisdomfirecontrol.common.base.Global.mContext, "companyName", companyName);
        SharedPreUtil.saveString(com.project.wisdomfirecontrol.common.base.Global.mContext, "principal", principal);
        SharedPreUtil.saveString(com.project.wisdomfirecontrol.common.base.Global.mContext, "principalTel", principalTel);
        SharedPreUtil.saveString(com.project.wisdomfirecontrol.common.base.Global.mContext, "firstName", nameFirst);
        SharedPreUtil.saveString(com.project.wisdomfirecontrol.common.base.Global.mContext, "firstPhone", firstPhone);

        company_name.setText(companyName);
        company_pricipal.setText("第一负责人：" + principal);
        company_phone.setText("电话：" + principalTel);
        company_count.setText("人数：" + personalCount);
        company_area.setText("面积：" + companyArea);

        if (isPopupWindow) {
            int height = 0;
            boolean pad = Global.isPad();
            if (pad) {
                height = (int) Global.mScreenWidth / 5;
            } else {
                height = (int) Global.mScreenHeight / 3;
            }
            final PopupWindowHelper.Builder builder = new PopupWindowHelper.Builder();
            builder.context(this)
                    .view(view)
                    .anchor(ll_juese)
                    .alpha(1f)
                    .width((int) Global.mScreenWidth)
                    .height(height)
                    .backgroundDrawable(new ColorDrawable())
                    .focusable(true)
                    .position(PopupWindowHelper.TOP);
            popupWindowHelper = builder.build();
            popupWindowHelper.show();
        } else {
            if (popupWindowHelper != null) {
                popupWindowHelper.dismiss();
            }
        }
    }

    private void initRecycler(LoginChangeDataBean data) {
        List<MenuDatasBeanX> menuDatas = data.getMenuDatas();
        for (MenuDatasBeanX menuData : menuDatas) {
            List<MenuDatasBean> menuDatas1 = menuData.getMenuDatas();
            menuDatasList.addAll(menuDatas1);
        }

//        homeAdapter = new HomeAdapter(R.layout.recycler_item_home, menuDatas);//分组
        homeAdapter = new HomeDatasChangeAdapter(R.layout.recycler_item_home_item, menuDatasList);
        homeAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(homeAdapter);
        homeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String name = menuDatasList.get(position).getName();
                Log.d(TAG, "onClick: " + name);
                switch (name) {
                    case Constant.XIAQU_UNIT://辖区单位
                        intent = new Intent(MvpMainActivity.this, OnlineUnitActivity.class);
                        MvpMainActivity.this.startActivity(intent);
                        break;
                    case Constant.SYSTEMMAIN://系统维护
                        intent = new Intent(MvpMainActivity.this, SystemSettingActivity.class);
//                        intent = new Intent(mContext, TestActivity.class);
                        MvpMainActivity.this.startActivity(intent);
                        break;
                    case Constant.SETTINGMANAGE://设备管理
                        intent = new Intent(MvpMainActivity.this, SettingManagerActivity.class);
                        MvpMainActivity.this.startActivity(intent);
                        break;
                    case Constant.VEDIO_LOOKING://视频
                        intent = new Intent(MvpMainActivity.this, VideoSytemListActivity.class);
                        MvpMainActivity.this.startActivity(intent);
                        break;
                    case Constant.ONLINE_UNIT:////联网单位
                        INTENT_VALUE = Const.GO_SETTINGONLINE_FIRST;
                        intent = new Intent(MvpMainActivity.this, SettingPoliceOnlineActivity.class);
                        intent.putExtra(Constant.INTENT_KEY, INTENT_VALUE);
                        MvpMainActivity.this.startActivity(intent);
                        break;
                    case Constant.XIAOFANGBAOJING:////联网单位
                        INTENT_VALUE = Const.GO_SETTINGONLINE_SECOND;
                        intent = new Intent(MvpMainActivity.this, SettingPoliceOnlineActivity.class);
                        intent.putExtra(Constant.INTENT_KEY, INTENT_VALUE);
                        MvpMainActivity.this.startActivity(intent);
                        break;
                    case Constant.SHEBEIGUZHANG:////联网单位
                        INTENT_VALUE = Const.GO_SETTINGONLINE_THIRD;
                        intent = new Intent(MvpMainActivity.this, SettingPoliceOnlineActivity.class);
                        intent.putExtra(Constant.INTENT_KEY, INTENT_VALUE);
                        MvpMainActivity.this.startActivity(intent);
                        break;
                    default:
//                        Global.showToast("正在开发...");
                        INTENT_VALUE = StringUtils.getItemNameSuper(name);
                        if (INTENT_VALUE.equals("正在开发...")) {
                            com.project.wisdomfirecontrol.common.base.Global.showToast(INTENT_VALUE);
                            return;
                        } else if (INTENT_VALUE.equals(Constant.XINGZHENGGONGWEN)) {
//                            Global.showToast("正在开发...");
                            List<?> menuDatas = menuDatasList.get(position).getMenuDatas();
                            if (menuDatas.size() > 0) {//两个
                                INTENT_VALUE = Constant.XINGZHENGGONGWEN_NEWADD;
                            }
                        }
                        intent = new Intent(new Intent(MvpMainActivity.this, WebH5Activity.class));
                        intent.putExtra(Constant.INTENT_KEY, INTENT_VALUE);
                        MvpMainActivity.this.startActivity(intent);
//
                        break;
                }
            }
        });
    }

    /*notification*/
    private void isNotification(MarqueeDataBean marqueeDataBean) {

        String createTime = marqueeDataBean.getCreateTime();
        if (!TextUtils.isEmpty(createTime)) {
            String waring = SharedPreUtil.getString(MvpMainActivity.this, "createTime", "");
            if (!waring.equals(createTime)) {
                SharedPreUtil.saveString(Global.mContext, "createTime", createTime);
                initNotification(marqueeDataBean);
            }
        }
    }


    private void initNotification(MarqueeDataBean marqueeDataBean) {

        //第一步：实例化通知栏构造器Notification.Builder：
        Notification.Builder builder = new Notification.Builder(Global.mContext);
        //实例化通知栏构造器Notification.Builder，参数必填（Context类型），为创建实例的上下文
        //第二步：获取状态通知栏管理：
        NotificationManager mNotifyMgr = (NotificationManager) Global.mContext.getSystemService(NOTIFICATION_SERVICE);
        //获取状态栏通知的管理类（负责发通知、清除通知等操作）
        //第三步：设置通知栏PendingIntent（点击动作事件等都包含在这里）:
//        Intent push = new Intent(Global.mContext, MainActivity.class);
        Intent push = new Intent(Global.mContext, SettingPoliceOnlineActivity.class);
        push.putExtra("INTENT_KEY", Const.GO_SETTINGONLINE_SECOND);
        //新建一个显式意图，第一个参数 Context 的解释是用于获得 package name，以便找到第二个参数 Class 的位置
        //PendingIntent可以看做是对Intent的包装，通过名称可以看出PendingIntent用于处理即将发生的意图，而Intent用来用来处理马上发生的意图
        //本程序用来响应点击通知的打开应用，第二个参数非常重要，点击notification 进入到activity, 使用到pendingIntent类方法，
        // PengdingIntent.getActivity()的第二个参数，即请求参数，实际上是通过该参数来区别不同的Intent的，如果id相同，就会覆盖掉之前的Intent了
        PendingIntent contentIntent = PendingIntent.getActivity(Global.mContext, 0, push, FLAG_CANCEL_CURRENT);
        //第四步：对Builder进行配置：
        String address = marqueeDataBean.getSensorPosition();
        String type = marqueeDataBean.getType();
        builder.setTicker("请尽快处理~");        //收到信息后状态栏显示的文字信息
        builder.setContentTitle("报警信息,请尽快处理~");
        String txt = "安装位置:" + address + "发生" + type + "警报";//标题
        builder.setContentText(txt);   //内容
        if (mXunFeiUtils == null) {
            mXunFeiUtils = new XunFeiUtils(Global.mContext);
        }
        mXunFeiUtils.speak("安装位置" + address + "发生" + type + "警报请尽快处理");
//        builder.setSubText(carInfosBeans.get(0).getGpsTime());   //内容下面的一小段文字
        builder.setWhen(System.currentTimeMillis());       //设置通知时间
        builder.setSmallIcon(R.mipmap.icon_app);            //设置小图标
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_app));
        builder.setDefaults(Notification.DEFAULT_ALL);   //打开呼吸灯，声音，震动，触发系统默认行为
        builder.setAutoCancel(true);                        //设置点击后取消Notification
        //比较手机sdk版本与Android 5.0 Lollipop的sdk
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            /*android5.0加入了一种新的模式Notification的显示等级，共有三种：
            VISIBILITY_PUBLIC只有在没有锁屏时会显示通知
            VISIBILITY_PRIVATE任何情况都会显示通知
            VISIBILITY_SECRET在安全锁和没有锁屏的情况下显示通知*/
            builder.setVisibility(Notification.VISIBILITY_PUBLIC);
            builder.setPriority(Notification.PRIORITY_DEFAULT);//设置该通知优先级
            builder.setCategory(Notification.CATEGORY_MESSAGE);//设置通知类别
            //setFullScreenIntent(contentIntent, true)//将Notification变为悬挂式Notification
        }
        builder.setContentIntent(contentIntent);

        //第五步：发送通知请求：
        Notification notify = builder.build();//得到一个Notification对象
        mNotifyMgr.notify(1, notify);//发送通知请求
    }


    //如果你需要考虑更好的体验，可以这么操作
    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        if (banner != null) {
            banner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        if (banner != null) {
            banner.stopAutoPlay();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (thread != null) {
            thread.quit();
            handlerdHandler.removeMessages(MSG_UPDATE_INFO);
        }

        if (mXunFeiUtils != mXunFeiUtils) {
            mXunFeiUtils.stopSpeak();
        }
    }

}
