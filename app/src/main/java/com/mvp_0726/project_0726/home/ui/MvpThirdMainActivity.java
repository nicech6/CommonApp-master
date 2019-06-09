package com.mvp_0726.project_0726.home.ui;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bean.BaojingDialogBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mvp_0726.common.base.codereview.BaseActivity;
import com.mvp_0726.common.event.CommonEvent;
import com.mvp_0726.common.event.NetWorkChangeEvent;
import com.mvp_0726.common.network.ApiRetrofit;
import com.mvp_0726.common.network.HttpObservable;
import com.mvp_0726.common.network.HttpResultObserver;
import com.mvp_0726.common.utils.Constans;
import com.mvp_0726.common.utils.Global;
import com.mvp_0726.common.utils.LogUtils;
import com.mvp_0726.common.utils.StatusBarUtil;
import com.mvp_0726.common.view.popupwindow.PopupWindowHelper;
import com.mvp_0726.project_0726.constant.Constant;
import com.mvp_0726.project_0726.home.adapter.HomeDatasChangeAdapter;
import com.mvp_0726.project_0726.home.adapter.HomeSettingCountAdapter;
import com.mvp_0726.project_0726.home.contract.HomeContract;
import com.mvp_0726.project_0726.home.model.GridCountResultBean;
import com.mvp_0726.project_0726.home.model.MarqueeDataBean;
import com.mvp_0726.project_0726.home.model.OrgandetailDataBean;
import com.mvp_0726.project_0726.home.model.SettingCountBean;
import com.mvp_0726.project_0726.home.presenter.HomeThirdPresenter;
import com.mvp_0726.project_0726.login.modle.DanWeiBean;
import com.mvp_0726.project_0726.online_unit.ui.activity.SettingPoliceOnlineActivity;
import com.mvp_0726.project_0726.ui.setting.NewSettingManagerActivity;
import com.mvp_0726.project_0726.utils.StringUtils;
import com.mvp_0726.project_0726.utils.XunFeiUtils;
import com.mvp_0726.project_0726.web.ui.WebH5Activity;
import com.mvp_0726.service.BaojingDialog;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Const;
import com.project.wisdomfirecontrol.common.util.LogUtil;
import com.project.wisdomfirecontrol.common.util.SharedPreUtil;
import com.project.wisdomfirecontrol.firecontrol.download.UpdateManager;
import com.project.wisdomfirecontrol.firecontrol.model.bean.login.LoginChangeDataBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.login.MenuDatasBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.login.MenuDatasBeanX;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.EquipmentCount;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.IHttpService;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_common.OnlineUnitActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_common.SystemSettingActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_unit.ConnectUnitActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_video.VideoSytemListActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;


/**
 * Created by Administrator on 2018/7/30.
 * 第三期主 ——首页
 */

public class MvpThirdMainActivity extends BaseActivity implements HomeContract.View {
    private RecyclerView recyclerViewHead, recyclerView;
    private TextView tv_app_company;
    private LoginChangeDataBean loginBean;
    private List<MenuDatasBean> menuDatasList = new ArrayList<>();
    private List<SettingCountBean> countList = new ArrayList<>();
    private HomeDatasChangeAdapter homeAdapter;

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
    //    private List<MarqueeDataBean> marqueeList;
    private HomeThirdPresenter presenter;

    private boolean isUpdateInfo = true;
    private Handler handlerdHandler;
    private static final int MSG_UPDATE_INFO = 0x110;
    private HandlerThread thread;
    private int count;
    private XunFeiUtils mXunFeiUtils;
    private Intent intent;
    private String INTENT_VALUE = "";
    private HomeSettingCountAdapter mCountAdapter;
    private String mLianStr;
    private String mBaoStr;
    private String mGuStr;
    private String mCompanyType;
    private String mRightNo;
    private String mId;
    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;

    private Handler handler = new Handler();
    private Runnable task = new Runnable() {
        public void run() {
            // TODO Auto-generated method stub
            handler.postDelayed(this, 10 * 1000);//设置循环时间，此处是10秒
            //需要执行的代码
//            Log.d(TAG, "task");

            HttpObservable.getObservable(ApiRetrofit.getApiRetrofit().getApiServis().getBaojingDialog("1", StringUtils.getUserPid(getApplicationContext()), "1", "16"))
                    .subscribe(new HttpResultObserver<BaojingDialogBean>() {

                        @Override
                        protected void onLoading(Disposable d) {

                        }

                        @Override
                        protected void onSuccess(BaojingDialogBean o) {
                            if (o != null && o.getData() != null && o.getData().size() > 0) {
                                for (int i = 0; i < o.getData().size(); i++) {
                                    if ("2".equals(o.getData().get(i).getState())) {
                                        EventBus.getDefault().post(getUrl(o.getData().get(i).getId()));
                                    }
                                }
                            }
                        }

                        @Override
                        protected void onFail(Exception e) {

                        }
                    });
        }
    };

    public String getUrl(String id) {
        String url = "";
        url = "http://www.zgjiuan.cn/sensorQY/showcall110.action?sensorid=" + id + "&title=报警信息";
        return url;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(String url) {
        showSuccessDialog(this, url);

    }

    BaojingDialog mBaojingDialog;

    public void showSuccessDialog(Context context, String url) {
        if (mBaojingDialog == null) {
            mBaojingDialog = new BaojingDialog(context);
        }
        mBaojingDialog.setUil(url);
        mBaojingDialog.setDialogCallback(new BaojingDialog.Dialogcallback() {
            @Override
            public void dialogdo(LinearLayout container) {
                mBaojingDialog.dismiss();
            }

            @Override
            public void dialogcancle() {
                mBaojingDialog.dismiss();
            }
        });

        mBaojingDialog.show();
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main_mvp_third;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        presenter = new HomeThirdPresenter(this, this);
//        tv_title.setText(R.string.jiuan_xiaofng);
        iv_back.setVisibility(View.INVISIBLE);

        mTv1 = (TextView) findViewById(R.id.tv_1);
        mTv2 = (TextView) findViewById(R.id.tv_2);
        mTv3 = (TextView) findViewById(R.id.tv_3);

        tv_app_company = findViewById(R.id.tv_app_company);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "songti.ttf");
        tv_app_company.setTypeface(typeface);
        StatusBarUtil.setTranslateByColor(MvpThirdMainActivity.this, Color.TRANSPARENT);//透明

        recyclerViewHead = findViewById(R.id.recyclerview_head);
        recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new GridLayoutManager(MvpThirdMainActivity.this, Const.COUNT_THIRD));
        mCompanyType = SharedPreUtil.getString(this, "companyType", "");
        if (mCompanyType.equals("企业监管")) {
            mLianStr = "联网设备";
            mBaoStr = "报警设备";
            mGuStr = "故障设备";
            startOnlineTime();
        } else {
            mLianStr = "联网单位";
            mBaoStr = "报警单位";
            mGuStr = "故障单位";
//            initDataBaojin("0", "0", "0");
        }
        initRecycleView();
        checkVersion();
        handler.post(task);//立即调用
    }


    //    检查版本更新
    private boolean HasCheckUpdate = false;
    private UpdateManager mUpdateManager;

    private void checkVersion() {
        if (!HasCheckUpdate) {
            mUpdateManager = new UpdateManager(MvpThirdMainActivity.this, true);
            mUpdateManager.checkVersion();
            HasCheckUpdate = true;
        }
    }

    private void initRecycleView() {
        recyclerViewHead.setLayoutManager(new GridLayoutManager(MvpThirdMainActivity.this, Const.COUNT_THIRD));
        mCountAdapter = new HomeSettingCountAdapter(R.layout.recycler_item_home_count, countList);
        recyclerViewHead.setAdapter(mCountAdapter);
        mCountAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (true) {
                    whatActivity(position);
                } else {
                    showSuccessToast("正在开发...");
                }
            }
        });
    }

    private void whatActivity(int position) {
        String type = "";
        Intent intent = new Intent();
        if (position == 0) {
            type = Const.GO_SETTINGONLINE_FIRST;
        } else if (position == 1) {
            type = Const.GO_SETTINGONLINE_SECOND;
        } else {
            type = Const.GO_SETTINGONLINE_THIRD;

        }
        intent.setClass(MvpThirdMainActivity.this, SettingPoliceOnlineActivity.class);
        intent.putExtra("INTENT_KEY", type);
        super.startActivity(intent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        recyclerView.getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    @SuppressLint("SetTextI18n")
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
//        mCompanyType = SharedPreUtil.getString(this, "companyType", "");
        if (!TextUtils.isEmpty(orgShortName)) {//+ mCompanyType
            tv_app_company.setText(orgShortName);
        }

//        String imagthpath = SharedPreUtil.getString(this, "imagthpath", "");
        pid = loginBean.getPersonel().getPid();
        mId = loginBean.getPersonel().getId();
        if (TextUtils.isEmpty(pid)) {
            pid = StringUtils.getUserPid(MvpThirdMainActivity.this);
        }
        if (presenter != null) {
//            ApiRetrofit.changeApiBaseUrl(NetworkUrl.ANDROID_BAIDU_SERVICE);
            if (mCompanyType.equals("企业监管")) {
                presenter.getEquipmentCount(pid);
            } else {
                presenter.getdanweiNum(pid);
            }
            presenter.getAppNum(pid, mId);
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
    }

    /*数据刷新*/
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
                    if (count % 60 == 0) {
                        getMarqueeDatas(pid);
                    }
                    count = count + 2;

                    if (count >= 10000) {
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
            pid = StringUtils.getUserPid(MvpThirdMainActivity.this);
        }
        if (presenter != null) {
//            ApiRetrofit.changeApiBaseUrl(NetworkUrl.ANDROID_BAIDU_SERVICE);
            presenter.getMarqueeDatas(pid);
        }
    }


    @Override
    protected void setLisenter() {
        tv_app_company.setOnClickListener(this);
        mTv1.setOnClickListener(this);
        mTv2.setOnClickListener(this);
        mTv3.setOnClickListener(this);
    }

    @Override
    protected void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.tv_app_company:
                if (dataBean != null) {
                    showCompanyInfos(true, dataBean);
                } else {
                    showSuccessToast("数据加载中，请稍等片刻");
                }
                break;

            case R.id.tv_1:
                if (mCompanyType.equals("企业监管")) {
                    whatActivity(0);
                } else {
                    INTENT_VALUE = StringUtils.getItemNameSuper(Constant.ORGANSMANAGE);
                    LogUtil.d("========name===" + INTENT_VALUE + "  == " + Constant.ORGANSMANAGE);
                    intent = new Intent(new Intent(MvpThirdMainActivity.this, WebH5Activity.class));
                    intent.putExtra(Constant.INTENT_KEY, INTENT_VALUE);
                    MvpThirdMainActivity.this.startActivity(intent);
                }
                break;
            case R.id.tv_2:
                if (mCompanyType.equals("企业监管")) {
                    whatActivity(2);
                } else {
                    INTENT_VALUE = StringUtils.getItemNameSuper(Constant.ORGANSMANAGE);
                    LogUtil.d("========name===" + INTENT_VALUE + "  == " + Constant.ORGANSMANAGE);
                    intent = new Intent(new Intent(MvpThirdMainActivity.this, WebH5Activity.class));
                    intent.putExtra(Constant.INTENT_KEY, INTENT_VALUE);
                    MvpThirdMainActivity.this.startActivity(intent);
                }
                break;
            case R.id.tv_3:
                if (mCompanyType.equals("企业监管")) {
                    whatActivity(1);
                } else {
                    INTENT_VALUE = StringUtils.getItemNameSuper(Constant.ORGANSMANAGE);
                    LogUtil.d("========name===" + INTENT_VALUE + "  == " + Constant.ORGANSMANAGE);
                    intent = new Intent(new Intent(MvpThirdMainActivity.this, WebH5Activity.class));
                    intent.putExtra(Constant.INTENT_KEY, INTENT_VALUE);
                    MvpThirdMainActivity.this.startActivity(intent);
                }
                break;
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
//                if (presenter != null) {
//                    presenter.getEquipmentCount(pid);
//                }
                break;
            case Constans.COMPANYINFOSSUCESS://公司信息
                dataBean = (OrgandetailDataBean) ecEvent.getData();
                showCompanyInfos(false, dataBean);
                break;
            case Constans.MARQUEESUCESS://报警轮播信息
//                marqueeList = (List<MarqueeDataBean>) ecEvent.getData();
                break;

            case Constans.GETAPPNUMSUCESS://角标数量
                GridCountResultBean gridCountBean = (GridCountResultBean) ecEvent.getData();
                showAppCornerMarkCount(gridCountBean);

                break;
            case Constans.DANWEINUM:

                DanWeiBean.ResultBean bean = (DanWeiBean.ResultBean) ecEvent.getData();
                mTv1.setText(mLianStr + "    " + bean.getLwTotal());
                mTv2.setText(mGuStr + "    " + bean.getGzlxTotal());
                mTv3.setText(mBaoStr + "    " + bean.getBjTotal());
                break;
        }
    }

    /*角标数据*/
    private void showAppCornerMarkCount(GridCountResultBean gridCountBean) {
        int app_aptitude = gridCountBean.getAPP_APTITUDE();
        int app_mtance = gridCountBean.getAPP_MTANCE();
        int app_notice = gridCountBean.getAPP_NOTICE();
        List<MenuDatasBean> menuDatasListNew = new ArrayList<>();
        menuDatasListNew.clear();
        LogUtils.d("==========" + app_aptitude + " ++ " + app_mtance + " ++ " + app_notice);
        for (MenuDatasBean menuDatasBean : menuDatasList) {
            mRightNo = "";
            mRightNo = menuDatasBean.getRightNo();
            LogUtils.d("========" + mRightNo);
            if (mRightNo.equals("APP_APTITUDE")) {
                menuDatasBean.setCount(app_aptitude);
            } else if (mRightNo.equals("APP_MTANCE")) {
                menuDatasBean.setCount(app_mtance);
            } else if (mRightNo.equals("APP_NOTICE")) {
                menuDatasBean.setCount(app_notice);
            }
            menuDatasListNew.add(menuDatasBean);
        }
        menuDatasList.clear();
        menuDatasList.addAll(menuDatasListNew);

        if (homeAdapter != null) {
            homeAdapter.setNewData(menuDatasList);
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
        String sensorcount = String.valueOf(data.getSensorcount());

        String dataBaojingcount = String.valueOf(data.getBaojingcount());
        String guzhangcount = String.valueOf(data.getGuzhangcount());

        initDataBaojin(sensorcount, dataBaojingcount, guzhangcount);

    }

    private void initDataBaojin(String sensorcount, String dataBaojingcount, String guzhangcount) {
//        countList.clear();
//        countList.add(new SettingCountBean(mLianStr, sensorcount));
//        countList.add(new SettingCountBean(mBaoStr, dataBaojingcount));
//        countList.add(new SettingCountBean(mGuStr, guzhangcount));
//
//        if (mCountAdapter != null) {
//            mCountAdapter.setNewData(countList);
//        }

        mTv1.setText(mLianStr + "    " + sensorcount);
        mTv2.setText(mGuStr + "    " + guzhangcount);
        mTv3.setText(mBaoStr + "    " + dataBaojingcount);

    }

    /*公司信息*/
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

        if (TextUtils.isEmpty(personalCount)) {
            personalCount = "";
        }
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
                height = (int) Global.mScreenHeight / 4;
            }
            final PopupWindowHelper.Builder builder = new PopupWindowHelper.Builder();
            builder.context(this)
                    .view(view)
                    .anchor(tv_app_company)
                    .alpha(1f)
                    .width((int) (Global.mScreenWidth / 2))//ActionBar.LayoutParams.WRAP_CONTENT
                    .height(height)
                    .backgroundDrawable(new ColorDrawable())
                    .focusable(true)
                    .position(PopupWindowHelper.BOTTOM);
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
//                showSuccessToast(name);
                switch (name) {
                    case Constant.XIAQU_UNIT://辖区地图
                        intent = new Intent(MvpThirdMainActivity.this, OnlineUnitActivity.class);
                        MvpThirdMainActivity.this.startActivity(intent);
                        break;
                    case Constant.SYSTEMMAIN://系统维护
                        intent = new Intent(MvpThirdMainActivity.this, SystemSettingActivity.class);
//                        intent = new Intent(mContext, TestActivity.class);
                        MvpThirdMainActivity.this.startActivity(intent);
                        break;
                    case Constant.SETTINGMANAGE://设备管理
//                        intent = new Intent(MvpThirdMainActivity.this, SettingManagerActivity.class);
                        intent = new Intent(MvpThirdMainActivity.this, NewSettingManagerActivity.class);
                        MvpThirdMainActivity.this.startActivity(intent);
                        break;
                    case Constant.VEDIO_LOOKING://视频
                        intent = new Intent(MvpThirdMainActivity.this, VideoSytemListActivity.class);
                        MvpThirdMainActivity.this.startActivity(intent);
                        break;
                    case Constant.ONLINE_UNIT:////联网单位
                        INTENT_VALUE = Const.GO_SETTINGONLINE_FIRST;
                        intent = new Intent(MvpThirdMainActivity.this, SettingPoliceOnlineActivity.class);
                        intent.putExtra(Constant.INTENT_KEY, INTENT_VALUE);
                        MvpThirdMainActivity.this.startActivity(intent);
                        break;
                    case Constant.XIAOFANGBAOJING:////联网单位
                        INTENT_VALUE = Const.GO_SETTINGONLINE_SECOND;
                        intent = new Intent(MvpThirdMainActivity.this, SettingPoliceOnlineActivity.class);
                        intent.putExtra(Constant.INTENT_KEY, INTENT_VALUE);
                        MvpThirdMainActivity.this.startActivity(intent);
                        break;
                    case Constant.SHEBEIGUZHANG:////联网单位
                        INTENT_VALUE = Const.GO_SETTINGONLINE_THIRD;
                        intent = new Intent(MvpThirdMainActivity.this, SettingPoliceOnlineActivity.class);
                        intent.putExtra(Constant.INTENT_KEY, INTENT_VALUE);
                        MvpThirdMainActivity.this.startActivity(intent);
                        break;

                    case Constant.TRAINING_CHECK://培训检查
                        INTENT_VALUE = IHttpService.HIDDENTROUBLE_RECORD_URL;
                        intent = new Intent(MvpThirdMainActivity.this, ConnectUnitActivity.class);
                        intent.putExtra("INTENT_KEY", INTENT_VALUE);
                        intent.putExtra("itemNameTitle", "隐患记录");
                        MvpThirdMainActivity.this.startActivity(intent);
                        break;
                    default:
                        INTENT_VALUE = StringUtils.getItemNameSuper(name);
                        if (INTENT_VALUE.equals("正在开发...")) {
                            com.project.wisdomfirecontrol.common.base.Global.showToast(INTENT_VALUE);
                            return;
                        }
                        if (INTENT_VALUE.equals(Constant.XINGZHENGGONGWEN)) {
//                            Global.showToast("正在开发...");
                            List<?> menuDatas = menuDatasList.get(position).getMenuDatas();
                            if (menuDatas.size() > 0) {//两个
                                INTENT_VALUE = Constant.XINGZHENGGONGWEN_NEWADD;
                            }
                        }
                        LogUtil.d("========name===" + INTENT_VALUE + "  == " + name);
                        intent = new Intent(new Intent(MvpThirdMainActivity.this, WebH5Activity.class));
                        intent.putExtra(Constant.INTENT_KEY, INTENT_VALUE);
                        MvpThirdMainActivity.this.startActivity(intent);
                        break;
                }
            }
        });
    }

    /*notification*/
    private void isNotification(MarqueeDataBean marqueeDataBean) {

        String createTime = marqueeDataBean.getCreateTime();
        if (!TextUtils.isEmpty(createTime)) {
            String waring = SharedPreUtil.getString(MvpThirdMainActivity.this, "createTime", "");
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
        if (presenter != null) {
            if (!TextUtils.isEmpty(pid) && !TextUtils.isEmpty(mId)) {
                presenter.getAppNum(pid, mId);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
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
        handler.removeCallbacks(task);
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
}
