package com.project.wisdomfirecontrol.firecontrol.ui.activity_setting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.firecontrol.model.bean.setting.SettingManagerDataBean;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.BaiduMapUtils;

/**
 * Created by Administrator on 2018/6/21.
 * 监控器信息
 */

public class MonitoringAreaInfosActivity extends BaseActivity {

    private TextView tv_area_name, tv_area_personal_name, tv_area_address;
    private TextView tv_title_right, tv_title_right_des;
    private TextView tv_lng, tv_lat, tv_next, tv_area_personal_phone;

    private TextureMapView mapview;
    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;
    private MarkerOptions markerOptions;
    private Marker marker;
    private SettingManagerDataBean settingManagerDataBean;
    private LatLng latLng;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_monitoringareainfos;
    }

    @Override
    public void initView() {
        title.setText("监控区信息");
        tv_area_name = findView(R.id.tv_area_name);
        tv_title_right = findView(R.id.tv_title_right);
        tv_title_right_des = findView(R.id.tv_title_right_des);
        tv_title_right.setVisibility(View.GONE);
        tv_title_right_des.setVisibility(View.VISIBLE);
        tv_title_right_des.setText("修改");
        tv_area_name = findView(R.id.tv_area_name);

        tv_area_personal_name = findView(R.id.tv_area_personal_name);
        tv_area_personal_phone = findView(R.id.tv_area_personal_phone);

        tv_area_address = findView(R.id.tv_area_address);
        tv_area_address.setMovementMethod(ScrollingMovementMethod.getInstance());
        mapview = findView(R.id.mapview);
        tv_lng = findView(R.id.tv_lng);
        tv_lat = findView(R.id.tv_lat);
        tv_next = findView(R.id.tv_next);
        baiduMap = mapview.getMap();
        mapview.showZoomControls(false);
        moveTo(mapview);
    }

    @SuppressLint("SetTextI18n")
    private void moveTo(TextureMapView mapview) {
        isFirstLocate = true;
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

                tv_area_name.setText(areaname);
                tv_area_personal_name.setText(personal);
                tv_area_personal_phone.setText("电话：" + tel);
                tv_area_address.setText(address + detailaddress);


                String lat = settingManagerDataBean.getLat();
                String lng = settingManagerDataBean.getLng();
                tv_lng.setText(lng);
                tv_lat.setText(lat);
                if (TextUtils.isEmpty(lat) || TextUtils.isEmpty(lng)) {
                    showToast("无坐标");
                    return;
                } else {
                    latLng = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
                }
                if (isFirstLocate) {
                    LatLng latLng = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
                    BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.person_location);
                    //准备 marker option 添加 marker 使用
                    markerOptions = new MarkerOptions().icon(bitmap).position(latLng);
                    //获取添加的 marker 这样便于后续的操作
                    marker = (Marker) baiduMap.addOverlay(markerOptions);
                    BaiduMapUtils.MoveMapToCenter(baiduMap, latLng, 16);
                }
            }
        }
    }

    @Override
    public void initListener() {
        tv_title_right_des.setOnClickListener(this);
        tv_next.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v, int id) {
        switch (v.getId()) {
            case R.id.tv_next:
                super.finish();
                break;
            case R.id.tv_title_right_des:
                tv_title_right_des();
                break;
        }

    }

    public static final int REQUEST_CODE = 111;

    /*修改*/
    private void tv_title_right_des() {
        if (settingManagerDataBean != null) {
            Intent intent = new Intent();
            intent.setClass(this, ChangeMonitoringAreaFirstActivity.class);
            intent.putExtra("settingManagerDataBean", settingManagerDataBean);
            startActivityForResult(intent, REQUEST_CODE);
//            super.startActivity(intent);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       /* *
         * 处理二维码扫描结果*/

        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                finish();
                String areaName = bundle.getString("areaName");
                String personal = bundle.getString("newPersonal");
                String tel = bundle.getString("tel");
                String address = bundle.getString("address");
                String lng = bundle.getString("lng");
                String lat = bundle.getString("lat");

                tv_area_name.setText(areaName);
                tv_area_personal_name.setText(personal + "\n" + tel);
                tv_area_address.setText(address);
                tv_lng.setText(lng);
                tv_lat.setText(lat);
                if (!TextUtils.isEmpty(lat) || !TextUtils.isEmpty(lng)) {
                    if (baiduMap != null) {
                        baiduMap.clear();
                        LatLng latLng = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
                        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.person_location);
                        //准备 marker option 添加 marker 使用
                        markerOptions = new MarkerOptions().icon(bitmap).position(latLng);
                        //获取添加的 marker 这样便于后续的操作
                        marker = (Marker) baiduMap.addOverlay(markerOptions);
                        BaiduMapUtils.MoveMapToCenter(baiduMap, latLng, 16);
                    }
                }
            }
        }
    }
}
