package com.project.wisdomfirecontrol.firecontrol.ui.activity_login_splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
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
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.common.base.Const;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.RegisterFirstCompanyBean;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.BaiduMapUtils;

/**
 * Created by Administrator on 2018/6/12.
 */

public class RegisterFirstActivity extends BaseActivity {

    private static final String TAG = "tag";
    private EditText tv_address_detail_room, tv_company_name, tv_company_name_six, tv_address_detail;
    private ImageView iv_location;
    private RelativeLayout btn_find;

    private TextView tv_lng, tv_lat, tv_province, tv_city, tv_area, tv_next;

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

    @Override
    public int getLayoutRes() {
        return R.layout.activity_register;
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

        tv_company_name = findView(R.id.tv_company_name);
        tv_company_name_six = findView(R.id.tv_company_name_six);

        tv_next = findView(R.id.tv_next);

    }

    @Override
    public void initListener() {
        btn_find.setOnClickListener(this);

        iv_location.setOnClickListener(this);

        tv_province.setOnClickListener(this);
        tv_city.setOnClickListener(this);
        tv_area.setOnClickListener(this);

        tv_next.setOnClickListener(this);
    }

    @Override
    public void initData() {
        title.setText("联网单位注册");
        latLng = null;
        /**
         * 预先加载仿iOS滚轮实现的全部数据
         */
        mCityPickerView.init(this);

        geoCoder = GeoCoder.newInstance();

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
        initMap();
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
        }

    }

    /*下一步*/
    private void tv_next() {

        String lng = tv_lng.getText().toString().trim();
        String lat = tv_lat.getText().toString().trim();
        String address_detail = tv_address_detail.getText().toString().trim();

        if (TextUtils.isEmpty(lng) && TextUtils.isEmpty(lat) && TextUtils.isEmpty(address_detail)) {
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

        String address_detail_room = tv_address_detail_room.getText().toString().trim();
//        if (TextUtils.isEmpty(address_detail_room)) {
//            showToast("请填写详细地址");
//            return;
//        }
        String company_name = tv_company_name.getText().toString().trim();
        if (TextUtils.isEmpty(company_name)) {
            showToast("请填写公司全称");
            return;
        }
        String company_name_six = tv_company_name_six.getText().toString().trim();
        if (TextUtils.isEmpty(company_name_six)) {
            showToast("请填写公司简称");
            return;
        }
        RegisterFirstCompanyBean bean = new RegisterFirstCompanyBean();
        bean.setLng(lng);
        bean.setLat(lat);
        bean.setAddress_detail(address_detail);
        bean.setProvince(province);
        bean.setCity(city);
        bean.setArea(area);
        bean.setAddress_detail_room(address_detail_room);
        bean.setCompany_name(company_name);
        bean.setCompany_name_six(company_name_six);

        intoSecondRegister(bean);
    }

    private void intoSecondRegister(RegisterFirstCompanyBean bean) {

        Intent intent = new Intent();
        intent.setClass(this, RegisterSecondActivity.class);
        intent.putExtra("bean", bean);
        super.startActivity(intent);
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
