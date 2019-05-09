package com.project.wisdomfirecontrol.firecontrol.ui.activity_setting;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.common.base.Const;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.util.SharedPreUtil;
import com.project.wisdomfirecontrol.firecontrol.model.bean.area.AreaPerpersoBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.area.AreaPerpersoDataBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.setting.SettingManagerDataBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.SubmitBean;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.CommonProtocol;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.IHttpService;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_login_splash.SreachActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.adaper_Lv.AreaPerpersoDataBeanLvAdapter;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.BaiduMapUtils;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.DatasUtils;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.PhotoUtils;
import com.project.wisdomfirecontrol.firecontrol.ui.view.dialog.SuccessDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/12.
 * 新增监控区
 */

public class ChangeMonitoringAreaFirstActivity extends BaseActivity {

    private static final String TAG = "tag";
    private EditText tv_address_detail_room, tv_company_name, tv_address_detail;
    private EditText tv_addpesoanl_name, tv_phone_tel;
    private ImageView iv_location;
    private RelativeLayout btn_find;

    private TextView tv_lng, tv_lat, tv_province, tv_city, tv_area, tv_next, tv_personal_name;

    private TextureMapView mapview;
    private boolean isFirstLoc = true; // 是否首次定位
    BitmapDescriptor map_location = BitmapDescriptorFactory.fromResource(R.drawable.person_location);
    public MarkerOptions ooA;
    private Marker mMarker;
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private double mCurrentLat;
    private double mCurrentLon;
    private String mAddrStr;
    private LatLng latLng;
    private BaiduMap baiduMap;
    private String city;

    private CityConfig.WheelType mWheelType = CityConfig.WheelType.PRO_CITY_DIS;

    CityPickerView mCityPickerView = new CityPickerView();
    private GeoCoder geoCoder;


    private CheckBox cb_pwd;
    private AreaPerpersoDataBeanLvAdapter stringDatasLvAdapter;
    private String pid;
    private CommonProtocol commonProtocol;
    private SettingManagerDataBean settingManagerDataBean;
    private String id;
    private String isc = "";
    private String personalid;
    private List<AreaPerpersoDataBean> data = new ArrayList<>();
    private SuccessDialog successDialog;
    private String tel;
    private String areaname;
    private String detailaddress;
    private String address;
    private String personal;
    private String newPersonal;
    private String lng;
    private String lat;
    private boolean cheche_pwd = false;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_monitoringarea;
    }

    @Override
    public void initView() {
        btn_find = findView(R.id.btn_find);

        mapview = findView(R.id.mapview);
        iv_location = findView(R.id.iv_location);

        tv_lng = findView(R.id.tv_lng);
        tv_lat = findView(R.id.tv_lat);

        tv_province = findView(R.id.tv_province);
        tv_city = findView(R.id.tv_city);
        tv_area = findView(R.id.tv_area);

        tv_address_detail = findView(R.id.tv_address_detail);

        tv_address_detail_room = findView(R.id.tv_address_detail_room);

        tv_next = findView(R.id.tv_next);
        cb_pwd = findView(R.id.cb_pwd);
        tv_company_name = findView(R.id.tv_company_name);//监控区名称
        tv_personal_name = findView(R.id.tv_personal_name);//负责人名称
        tv_addpesoanl_name = findView(R.id.tv_addpesoanl_name);//新增负责人名称
        tv_phone_tel = findView(R.id.tv_phone_tel);//手机号或座机
        title.setText("修改监控区");
        baiduMap = mapview.getMap();
        // 地图初始化
        mapview.showZoomControls(false);// 缩放控件是否显示
        cb_pwd.setChecked(false);
        isc = "false";
    }

    @Override
    public void initListener() {
        btn_find.setOnClickListener(this);

        iv_location.setOnClickListener(this);

        tv_province.setOnClickListener(this);
        tv_city.setOnClickListener(this);
        tv_area.setOnClickListener(this);

        tv_next.setOnClickListener(this);

        cb_pwd.setOnClickListener(this);
        tv_personal_name.setOnClickListener(this);
    }

    @Override
    public void initData() {
        latLng = null;
        /**
         * 预先加载仿iOS滚轮实现的全部数据
         */
        mCityPickerView.init(this);

        geoCoder = GeoCoder.newInstance();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            settingManagerDataBean = (SettingManagerDataBean) bundle.get("settingManagerDataBean");
            if (settingManagerDataBean != null) {
                /*监控区*/
                String areaname = settingManagerDataBean.getAreaname();
                String personal = settingManagerDataBean.getPersonal();
                String tel = settingManagerDataBean.getTel();
                String address = settingManagerDataBean.getAddress();
                String detailaddress = settingManagerDataBean.getDetailaddress();
                String province = settingManagerDataBean.getProvince();
                city = settingManagerDataBean.getCity();
                String district = settingManagerDataBean.getDistrict();
                id = settingManagerDataBean.getAreaid();
                personalid = settingManagerDataBean.getPersonid();
                tv_province.setText(province);
                tv_city.setText(city);
                tv_area.setText(district);

                tv_company_name.setText(areaname);
                tv_personal_name.setText(personal);
                tv_address_detail.setText(address);
                tv_address_detail_room.setText(detailaddress);
//                tv_addpesoanl_name.setText(personal);
                tv_phone_tel.setText(tel);

                String lat = settingManagerDataBean.getLat();
                String lng = settingManagerDataBean.getLng();
                tv_lng.setText(lng);
                tv_lat.setText(lat);
                if (TextUtils.isEmpty(lat) || TextUtils.isEmpty(lng)) {
                    initMap();
                } else {
                    latLng = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
                    if (baiduMap != null) {
                        LatLng latLng = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
                        BaiduMapUtils.MoveMapToCenter(baiduMap, latLng, 16);
                    }
                }
            } else {
                initMap();
            }
        }


        tv_address_detail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                if (!TextUtils.isEmpty(string)) {
                    String latlng = BaiduMapUtils.returnAddress(geoCoder, latLng, tv_city.getText().toString().trim(), string);
                    showLngLat(latlng);
                }
            }
        });
    }

    /*show lng  lat*/
    private void showLngLat(String latlng) {

        if (!TextUtils.isEmpty(latlng)) {
            String[] split = latlng.split(",");
            if (split.length == 2) {
                String lng = split[0];
                String lat = split[1];
                tv_lng.setText(lng);
                tv_lat.setText(lat);
                LatLng latLng = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
                MapStatus ms = new MapStatus.Builder().target(latLng)
                        .overlook(-20).zoom(Const.BAIDU_ZOOM_14).build();
                ooA = new MarkerOptions().icon(map_location).zIndex(10);
                ooA.position(latLng);
                if (baiduMap != null) {
                    mMarker = (Marker) (baiduMap.addOverlay(ooA));
                    baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));

                    baiduMap.clear();
                    baiduMap.addOverlay(ooA);
                    BaiduMapUtils.MoveMapToCenter(baiduMap, latLng, Const.BAIDU_ZOOM_14);
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
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

    private void isHasLocationPermission(final TextureMapView bmapView) {
        initLocation(bmapView);
    }

    private void initLocation(TextureMapView bmapView) {
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
    protected void onPause() {
        mapview.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mapview.onResume();
        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
            case R.id.tv_next:
                tv_next();
                break;
            case R.id.tv_personal_name:
                tv_personal_name();
                break;
            case R.id.cb_pwd:
                cb_pwd();
                break;
        }

    }

    /*地址一样*/
    private void cb_pwd() {
        String address = SharedPreUtil.getString(Global.mContext, "address", "");
        if (!cheche_pwd) {
            cheche_pwd = true;
            isc = "true";
            tv_address_detail.setText(address);

        } else {
            cheche_pwd = false;
            isc = "false";
            tv_address_detail.setText("");
        }
        cb_pwd.setChecked(cheche_pwd);
    }

    /*新增负责人*/
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void tv_personal_name() {
        tv_addpesoanl_name.setText("");
        tv_phone_tel.setText("");
        if (data.size() > 0) {
            showSelectorPerperson(data);
        } else {
            pid = DatasUtils.getUserPid(this);
            showWaitDialog(this, getString(R.string.inupdate));
            commonProtocol = new CommonProtocol();
//            pid = "yun";
            commonProtocol.getareaperson(this, pid);
        }
    }

    /*下一步*/
    private void tv_next() {

        lng = tv_lng.getText().toString().trim();
        lat = tv_lat.getText().toString().trim();
        address = tv_address_detail.getText().toString().trim();

        if (TextUtils.isEmpty(lng) && TextUtils.isEmpty(lat) && TextUtils.isEmpty(address)) {
            showToast("请确定位置信息");
            return;
        }

        String province = tv_province.getText().toString().trim();
        if (TextUtils.isEmpty(province)) {
            showToast("请选择省份");
            return;
        }
        String city = tv_city.getText().toString().trim();
        if (TextUtils.isEmpty(city)) {
            showToast("请选择城市");
            return;
        }
        String area = tv_area.getText().toString().trim();
        if (TextUtils.isEmpty(area)) {
            showToast("请选择地区");
            return;
        }

        detailaddress = tv_address_detail_room.getText().toString().trim();

        areaname = tv_company_name.getText().toString().trim();
        if (TextUtils.isEmpty(areaname)) {
            showToast("请填写监控区名称");
            return;
        }
        String personal = tv_personal_name.getText().toString().trim();
        if (TextUtils.isEmpty(personal)) {
            showToast("请选择区域消防负责人");
            return;
        }

        tel = tv_phone_tel.getText().toString().trim();
        if (TextUtils.isEmpty(tel)) {
            showToast("请填写联系电话");
            return;
        }

        newPersonal = tv_addpesoanl_name.getText().toString().trim();

        if (!personal.equals("新增")) {
            newPersonal = personal;
        }

        intoSecondRegister(areaname, area, newPersonal, tel, address, isc, detailaddress,
                lng, lat, province, city, detailaddress);
    }

    private void intoSecondRegister(String areaname, String area, String newPersonal,
                                    String tel, String address, String isc, String detailaddress,
                                    String lng, String lat, String province, String city, String district) {
        pid = DatasUtils.getUserPid(this);
        showWaitDialog(this, getString(R.string.submit_in));
        commonProtocol = new CommonProtocol();
        Log.d(TAG, "intoSecondRegister: " + isc + " +++ " + personalid);
        commonProtocol.savemonitorarea(this, id, areaname, newPersonal, tel, address, pid,
                isc, detailaddress, lng, lat, province, city, area, "", personalid);
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
                tv_lng.setText("");
                tv_lat.setText("");
                tv_address_detail.setText("");
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

    /*搜索*/
    private void btn_find() {
        Intent intent = new Intent(this, SreachActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("addressCity", city);
        intent.putExtras(bundle);//将Bundle添加到Intent,也可以在Bundle中添加相应数据传递给下个页面,例如：bundle.putString("abc", "bbb");
        super.startActivityForResult(intent, 0);// 跳转并要求返回值，0代表请求值(可以随便写)
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
            tv_address_detail.setText(addressCity);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyMap();
        data.clear();
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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onHttpSuccess(int reqType, Message obj) {
        super.onHttpSuccess(reqType, obj);
        dismissWaitDialog();

        if (reqType == IHttpService.TYPE_GETAREAPERSON) {
            AreaPerpersoBean bean = (AreaPerpersoBean) obj.obj;
            if (bean != null) {
                data = bean.getData();
                if (data.size() > 0) {
                    showSelectorPerperson(data);
                }
            }
        } else if (reqType == IHttpService.TYPE_SETTINGMANAGER) {
            SubmitBean bean = (SubmitBean) obj.obj;
            if (bean != null) {
                if (bean.getMsg().equals("操作成功!")) {
//                    showSuccessDialog(ChangeMonitoringAreaFirstActivity.this, "修改监控区成功，是否退出当前界面");
                    showDelectDialog("修改监控区成功，是否退出当前界面");
                } else {
                    showToast("操作失败，请重试");
                }
            }
        }
    }

    /*成功*/
    private void showDelectDialog(String txt) {
        if (successDialog == null) {
            successDialog = new SuccessDialog(this);
        }
        successDialog.setContent(txt);
//        successDialog.hideCancle();
        successDialog.setDialogCallback(new SuccessDialog.Dialogcallback() {
            @Override
            public void dialogdo(LinearLayout container) {
                if (!isFinishing()) {
                    successDialog.dismiss();
                }
                Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();

                bundle.putString("areaName", areaname);
                bundle.putString("newPersonal", newPersonal);
                bundle.putString("tel", tel);
                bundle.putString("address", address + detailaddress);
                bundle.putString("lng", lng);
                bundle.putString("lat", lat);
                resultIntent.putExtras(bundle);
                ChangeMonitoringAreaFirstActivity.this.setResult(RESULT_OK, resultIntent);
                ChangeMonitoringAreaFirstActivity.this.finish();

            }

            @Override
            public void dialogcancle() {
                if (!isFinishing()) {
                    successDialog.dismiss();
                }
            }

        });
        if (!isFinishing()) {
            successDialog.show();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showSelectorPerperson(final List<AreaPerpersoDataBean> data) {

        View typeView = LayoutInflater.from(this).inflate(R.layout.lv_item_type_login, null);
        final PhotoUtils photoUtils = new PhotoUtils(this, typeView, (int) Global.mScreenWidth / 2,
                ActionBar.LayoutParams.WRAP_CONTENT, tv_personal_name, false,true);
        ListView listView = typeView.findViewById(R.id.lv_item_type);

        if (stringDatasLvAdapter == null) {
            stringDatasLvAdapter = new AreaPerpersoDataBeanLvAdapter(this, data);
        }
        listView.setAdapter(stringDatasLvAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = data.get(position).getName();
                tv_personal_name.setText(name);
                if (!name.equals("新增")) {
                    tv_phone_tel.setText(data.get(position).getTelNum());

                } else {
                    tv_phone_tel.setText("");
                    tv_addpesoanl_name.setText("");
                }
                personalid = data.get(position).getId();
                if (photoUtils != null) {
                    photoUtils.showTypeOrDismiss();
                }
            }
        });


    }

    @Override
    public void onHttpError(int reqType, String error) {
        super.onHttpError(reqType, error);
        dismissWaitDialog();
    }
}
