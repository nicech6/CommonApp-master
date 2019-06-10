package com.client;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.bean.BaojingDialogBean;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.mvp_0726.common.network.ApiRetrofit;
import com.mvp_0726.common.network.HttpObservable;
import com.mvp_0726.common.network.HttpResultObserver;
import com.mvp_0726.project_0726.login.ui.LoginActivity;
import com.mvp_0726.project_0726.login.ui.SplashActivity;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.common.base.Const;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.util.PwdCheckUtil;
import com.project.wisdomfirecontrol.common.util.StringUtils;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.SubmitBean;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_login_splash.RegisterFirstActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_login_splash.SreachActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.BaiduMapUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;
import io.reactivex.disposables.Disposable;

public class RegisterClientActivity extends BaseActivity {
    private TextureMapView mapview;
    private RelativeLayout btn_find;
    private boolean isFirstLoc = true; // 是否首次定位
    BitmapDescriptor map_location = BitmapDescriptorFactory.fromResource(R.drawable.person_location);
    public MarkerOptions ooA;
    private Marker mMarker;
    LocationClient mLocClient;
    public RegisterClientActivity.MyLocationListenner myListener = new RegisterClientActivity.MyLocationListenner();
    private double mCurrentLat;
    private double mCurrentLon;
    private String mAddrStr;
    private LatLng latLng;
    private BaiduMap baiduMap;
    private String city;
    private TextView tv_lng, tv_lat, tv_province, tv_city, tv_area;
    CityPickerView mCityPickerView = new CityPickerView();
    private GeoCoder geoCoder;
    private ImageView iv_location;
    private EditText tv_address_detail_room, tv_company_name, tv_company_name_six, tv_address_detail, tv_user_pwd_again;
    private TextView tv_send_msg_pwd;
    private EditText tv_user_phone, edt_msg_pwd, edt_user_pwd, tv_unit_personal_count;
    private String phone;
    private boolean cheche_pwd_xieye = false;
    private CheckBox cb_agree;
    private boolean isRemberPwd_xieye;
    private TextView tv_success;
    private String user_pwd;
    private String user_pwd_again;
    private EditText tv_user_name, tv_user_area;


    @Override
    public int getLayoutRes() {
        return R.layout.activity_client_register;
    }

    /**
     * 弹出省市区选择器
     */
    private void wheel() {

        CityConfig cityConfig = new CityConfig.Builder().title("选择城市")//标题
                .build();

        mCityPickerView.setConfig(cityConfig);
        mCityPickerView.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
//                tv_lng.setText("");
//                tv_lat.setText("");
//                tv_address_detail.setText("");
                if (province != null) {
                    tv_province.setText(province.getName());
                }

                if (city != null) {
                    tv_city.setText(city.getName());
                }

                if (district != null) {
                    tv_area.setText(district.getName());
                }
            }

            @Override
            public void onCancel() {
            }
        });
        mCityPickerView.showCityPicker();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LatLng latLng = null;
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();

            String province = bundle.getString("province");
            city = bundle.getString("city");
            String area = bundle.getString("area");
            String addressCity = bundle.getString("address");
            double lat = bundle.getDouble("latitude");
            double lon = bundle.getDouble("longitude");

            tv_lng.setText(String.valueOf(lon));
            tv_lat.setText(String.valueOf(lat));

            tv_province.setText(province);
            tv_city.setText(city);
            tv_area.setText(area);
//            tv_address_detail.setText(addressCity);

            if (lat > 0 || lon > 0) {
                latLng = new LatLng(lat, lon);
            }
            MapStatus ms = new MapStatus.Builder().target(latLng)
                    .overlook(-20).zoom(Const.BAIDU_ZOOM).build();
            ooA = new MarkerOptions().icon(map_location).zIndex(10);
            ooA.position(latLng);
            mMarker = null;
            if (baiduMap != null) {
                mMarker = (Marker) (baiduMap.addOverlay(ooA));
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));

                baiduMap.clear();
                baiduMap.addOverlay(ooA);
                BaiduMapUtils.MoveMapToCenter(baiduMap, latLng, Const.BAIDU_ZOOM);
            }
        }
    }

    /*搜索*/
    private void btn_find() {
        Intent intent = new Intent(this, SreachActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("addressCity", city);
        intent.putExtras(bundle);//将Bundle添加到Intent,也可以在Bundle中添加相应数据传递给下个页面,例如：bundle.putString("abc", "bbb");
        super.startActivityForResult(intent, 0);// 跳转并要求返回值，0代表请求值(可以随便写)
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mapview == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mAddrStr = location.getAddrStr();
            city = location.getCity();
            latLng = new LatLng(mCurrentLat, mCurrentLon);
            latLng = BaiduMapUtils.ConverGpsToBaidu(latLng);
            if (isFirstLoc) {

                tv_lng.setText(String.valueOf(mCurrentLon));
                tv_lat.setText(String.valueOf(mCurrentLat));

                tv_province.setText(location.getProvince());
                tv_city.setText(city);
                tv_area.setText(location.getDistrict());
                tv_address_detail.setText(location.getAddrStr());
                isFirstLoc = false;
                MapStatus ms = new MapStatus.Builder().target(latLng)
                        .overlook(-20).zoom(Const.BAIDU_ZOOM_14).build();
                ooA = new MarkerOptions().icon(map_location).zIndex(10);
                ooA.position(latLng);
                mMarker = null;
                mMarker = (Marker) (baiduMap.addOverlay(ooA));
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
            }
        }
    }

    @Override
    public void initView() {
        mapview = findView(R.id.mapview);
        iv_location = findView(R.id.iv_location);
        btn_find = findView(R.id.btn_find);
        tv_lng = findView(R.id.tv_lng);
        tv_lat = findView(R.id.tv_lat);

        tv_address_detail_room = findView(R.id.tv_address_detail_room);
        tv_province = findView(R.id.tv_province);
        tv_city = findView(R.id.tv_city);
        tv_area = findView(R.id.tv_area);
        tv_address_detail = findView(R.id.tv_address_detail);
        tv_company_name = findView(R.id.tv_company_name);
        /**
         * 预先加载仿iOS滚轮实现的全部数据
         */
        mCityPickerView.init(this);

        geoCoder = GeoCoder.newInstance();
        tv_send_msg_pwd = findView(R.id.tv_send_msg_pwd);
        cb_agree = findView(R.id.cb_agree);

        tv_user_phone = findView(R.id.tv_user_phone);
        edt_msg_pwd = findView(R.id.edt_msg_pwd);
        tv_send_msg_pwd = findView(R.id.tv_send_msg_pwd);
        tv_send_msg_pwd.setText("发送动态验证码");
        edt_user_pwd = findView(R.id.edt_user_pwd);
        tv_user_pwd_again = findView(R.id.tv_user_pwd_again);
        tv_success = findView(R.id.tv_success);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_user_area = findViewById(R.id.tv_user_area);


        inintSMSSDK();

        initEditText();


    }

    private void initEditText() {

        edt_msg_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() < 4) {
                    return;
                }
                try {
                    String verifity = edt_msg_pwd.getText().toString().trim();
                    String thePhone = tv_user_phone.getText().toString().trim();
                    SMSSDK.submitVerificationCode(Const.COUNTRY, thePhone, verifity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        edt_user_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() < 8) {
                    return;
                }
                try {
                    String user_pwd = edt_user_pwd.getText().toString().trim();
                    boolean letterDigit = PwdCheckUtil.isLetterDigit(user_pwd);
//                    Log.d(TAG, "onTextChanged: " + letterDigit);
                    if (!letterDigit) {
                        showToast("密码需包含母及数字");
                        return;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /*同意并遵守*/
    private void cb_agree() {
        if (!cheche_pwd_xieye) {
            cheche_pwd_xieye = true;
        } else {
            cheche_pwd_xieye = false;
        }
        cb_agree.setChecked(cheche_pwd_xieye);
    }

    @Override
    protected void onPause() {
        mapview.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mapview.onResume();
        super.onResume();
    }

    @Override
    public void initListener() {
        btn_find.setOnClickListener(this);
        tv_send_msg_pwd.setOnClickListener(this);

        iv_location.setOnClickListener(this);
        tv_success.setOnClickListener(this);
        cb_agree.setOnClickListener(this);

        tv_province.setOnClickListener(this);
        tv_city.setOnClickListener(this);
        tv_area.setOnClickListener(this);
        tv_send_msg_pwd.setOnClickListener(this);
        tv_user_phone = findView(R.id.tv_user_phone);
        edt_msg_pwd = findView(R.id.edt_msg_pwd);
        tv_send_msg_pwd = findView(R.id.tv_send_msg_pwd);
        tv_send_msg_pwd.setText("发送动态验证码");
        edt_user_pwd = findView(R.id.edt_user_pwd);
        tv_user_pwd_again = findView(R.id.tv_user_pwd_again);

    }

    /*获取动态码*/
    private void tv_send_msg_pwd() {
        phone = tv_user_phone.getText().toString().trim();
//        phone = "13726870018";
        if (TextUtils.isEmpty(phone)) {
            showToast("手机号码不能为空");
            return;
        }
        boolean isPhoneNum = StringUtils.isMobileNO(phone);

        if (!isPhoneNum) {
            showToast("请输入有效的手机号码！");
        } else {
            phone = tv_user_phone.getText().toString().trim();
//            phone = "13726870018";
            getCode(Const.COUNTRY, phone);
        }
    }

    @SuppressLint("SetTextI18n")
    private void getCode(String country, String phone) {
        // 2.通过sdk发送短信验证
        // 请求获取短信验证码
        SMSSDK.getVerificationCode(country, phone);
        // 3.把按钮变成不可点击，并且显示倒计时
        try {
            handler.sendEmptyMessage(YANZHENGMA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inintSMSSDK() {
        SMSSDK.initSDK(this, Const.MOB_APPKEY, Const.MOB_APP_SECRET, false);
        try {
            EventHandler eventHandler = new EventHandler() {
                public void afterEvent(int event, int result, Object data) {
                    Message msg = new Message();
                    msg.arg1 = event;
                    msg.arg2 = result;
                    msg.obj = data;
                    handler.sendMessage(msg);
                }
            };
            SMSSDK.registerEventHandler(eventHandler); // 注册短信回调
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int count = 60;
    private final int YANZHENGMA = 10;
    private final int YANZHENGIN = 12;
    private final int YANZHENGSUCCESS = 15;
    /*** 处理UI线程更新Handle **/
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressLint("SetTextI18n")
        public void handleMessage(android.os.Message msg) {
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    tv_send_msg_pwd.setText("验证成功");

                    count = 0;
                    handler.sendEmptyMessageDelayed(YANZHENGSUCCESS, 2500);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    showToast("验证码已经发送");

                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                } else if (event == SMSSDK.RESULT_ERROR) {
                    showToast("请检查验证码是否正确");
                }
            } else {
                int status = 0;
                try {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;

                    JSONObject object = new JSONObject(throwable.getMessage());
                    String des = object.optString("detail");
                    status = object.optInt("status");
                    if (!TextUtils.isEmpty(des)) {
                        Global.showToast(des);
                    }
                } catch (Exception e) {
                    SMSLog.getInstance().w(e);
                }
            }

            switch (msg.what) {
                case YANZHENGMA://等待获取验证码
                    if (count > 0) {
                        count--;
                        handler.sendEmptyMessageDelayed(YANZHENGMA, 1000);
                    } else {
                        handler.sendEmptyMessageDelayed(YANZHENGIN, 1000);
                    }
                    if (count == 0) {
                        count = 0;
                    }

                    tv_send_msg_pwd.setText("" + count + " S");

                    break;
                case YANZHENGIN:
                    tv_send_msg_pwd.setText("重新获取验证码");
                    count = 60;
                    break;
                case YANZHENGSUCCESS:
                    tv_send_msg_pwd.setText("验证成功");
                    break;

                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onStart() {
        super.onStart();
        initMap();
    }

    @Override
    public void onClick(View v, int id) {
        switch (v.getId()) {

            case R.id.btn_find:
                btn_find();
                break;
            case R.id.iv_location:
                iv_location();
                break;
            case R.id.tv_province:
                wheel();
                break;
            case R.id.tv_city:
                wheel();
                break;
            case R.id.tv_area:
                wheel();
                break;
            case R.id.tv_send_msg_pwd:
                tv_send_msg_pwd();
                break;
            case R.id.tv_success:
                tv_success();
                break;
            case R.id.cb_agree:
                cb_agree();
                break;
        }

    }

    private void tv_success() {
        if (TextUtils.isEmpty(tv_address_detail_room.getText().toString())) {
            showToast("请先填写详细地址");
            return;
        }
        if (TextUtils.isEmpty(tv_company_name.getText().toString())) {
            showToast("请先填写公司名称！");
            return;
        }
        if (TextUtils.isEmpty(tv_user_name.getText().toString())) {
            showToast("请先填写姓名！");
            return;
        }
        if (TextUtils.isEmpty(tv_user_area.getText().toString())) {
            showToast("请先填写住址！");
            return;
        }
        String unit_personal_frst_phone = tv_user_phone.getText().toString().trim();
        boolean isPhoneNum1 = StringUtils.isMobileNO(unit_personal_frst_phone);
        if (!isPhoneNum1) {
            showToast("请输入有效的手机号码！");
            return;
        }
        user_pwd = edt_user_pwd.getText().toString().trim();
        user_pwd_again = tv_user_pwd_again.getText().toString().trim();
        if (TextUtils.isEmpty(user_pwd) && TextUtils.isEmpty(user_pwd_again)) {
            showToast("请填写密码");
            return;
        }
        if (!user_pwd.equals(user_pwd_again)) {
            showToast("两次密码不一致");
            return;
        }
        String msg = tv_send_msg_pwd.getText().toString().trim();
//        验证成功
        if (!msg.equals("验证成功")) {
            showToast("请先验证手机号码");
            return;
        }
        if (!cheche_pwd_xieye) {
            showToast("请先勾选协议");
            return;

        }
        String lng = tv_lng.getText().toString().trim();
        String lat = tv_lat.getText().toString().trim();
        RegisterClientBean bean = new RegisterClientBean();
        bean.setLat(lat);
        bean.setLng(lng);
        bean.setOrgName(tv_company_name.getText().toString());
        bean.setAddress(tv_user_area.getText().toString());
//        bean.setCode(edt_msg_pwd.getText().toString());
        bean.setName(tv_user_name.getText().toString());
        bean.setPassword(user_pwd);
        bean.setTelNum(tv_user_phone.getText().toString());
        bean.setPosition(tv_address_detail_room.getText().toString());

        HttpObservable.getObservable(ApiRetrofit.getApiRetrofit().getApiServis().loginClient(bean.getPosition(), bean.getOrgName(), bean.getAddress(), bean.getTelNum(), bean.getPassword(), bean.getName(), bean.getLng(), bean.getLat()))
                .subscribe(new HttpResultObserver<RegisterBean>() {

                    @Override
                    protected void onLoading(Disposable d) {
                        showToast("注册中...");
                    }

                    @Override
                    protected void onSuccess(RegisterBean o) {
                        if ("注册成功".equals(o.getMessage())) {
                            showToast("注册成功");
                        }
                        Intent intent = new Intent(RegisterClientActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    protected void onFail(Exception e) {
                        showToast("注册失败");
                    }
                });
    }

    /*地图居中*/
    private void iv_location() {
        if (baiduMap != null) {
            baiduMap.clear();
        }
        if (mLocClient != null) {
            mLocClient.requestLocation();
        }
        isFirstLoc = true;
    }

    private void initMap() {
        if (mapview == null) {
            return;
        }
        boolean oPenGPS = BaiduMapUtils.isOPenGPS(this);
        if (oPenGPS) {
            isHasLocationPermission(mapview);
        }
    }

    @Override
    public void initData() {

    }

    private void isHasLocationPermission(final TextureMapView bmapView) {
        initLocation(bmapView);
    }


    private void initLocation(TextureMapView bmapView) {

        baiduMap = bmapView.getMap();
        // 地图初始化
        bmapView.showZoomControls(false);// 缩放控件是否显示

        // 开启定位图层
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        baiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
//        option.setAddrType("all");
        mLocClient.setLocOption(option);
        mLocClient.start();
        baiduMap.showMapPoi(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyMap();
    }

    private void destroyMap() {
        isFirstLoc = true;
        if (baiduMap != null) {
            baiduMap.clear();
        }
        if (mLocClient != null) {
            mLocClient.stop();
        }
    }
}
