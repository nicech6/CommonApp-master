package com.project.wisdomfirecontrol.firecontrol.ui.activity_setting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.mvp_0726.project_0726.bean.settingpolice.GetsensorObdDataBean;
import com.mvp_0726.project_0726.bean.settingpolice.GetsensorObdSuccessBean;
import com.mvp_0726.project_0726.bean.settingpolice.ObdParamBean;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.util.LogUtil;
import com.project.wisdomfirecontrol.firecontrol.model.bean.setting.ObdBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.setting.SettingManagerDataBean;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.CommonProtocol;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.IHttpService;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.BaiduMapUtils;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.DayAxisValueFormatter;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.XYMarkerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/20.
 * 设备信息
 */

public class SettingInfosActivity extends BaseActivity {

    private static final String TAG = "tag";
    private TextView tv_install_address, tv_setting_type, tv_setting_num, tv_install_time;
    private TextView tv_setting_area, tv_area_personal, tv_setting_address, tv_next;
    private TextView tv_title_right_des, tv_title_right;

    private TextureMapView mapview;
    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;
    private MarkerOptions markerOptions;
    private Marker marker;
    private LatLng latLng;
    private SettingManagerDataBean settingManagerDataBean;
    private String personal;
    private String address;
    private String tel;
    private BarChart barchart_e, barchart_v, barchart_temp;
    private String str;

    private RelativeLayout rl_map;
    private CommonProtocol mCommonProtocol;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_settinginfos;
    }

    @Override
    public void initView() {
        title.setText("设备信息");
        tv_title_right = findView(R.id.tv_title_right);
        tv_title_right_des = findView(R.id.tv_title_right_des);
        tv_title_right.setVisibility(View.GONE);
        tv_title_right_des.setText("修改");
        tv_title_right_des.setVisibility(View.VISIBLE);
        tv_install_address = findView(R.id.tv_install_address);
        tv_setting_type = findView(R.id.tv_setting_type);//类型
        tv_setting_num = findView(R.id.tv_setting_num);//设备号
        tv_install_time = findView(R.id.tv_install_time);//安装时间

        tv_setting_area = findView(R.id.tv_setting_area);//监控区域
        tv_area_personal = findView(R.id.tv_area_personal);//负责人
        tv_setting_address = findView(R.id.tv_setting_address);//地址

        mapview = findView(R.id.mapview);//地址
        tv_next = findView(R.id.tv_next);//关闭

        rl_map = findView(R.id.rl_map);//

        ViewGroup.LayoutParams layoutParams = rl_map.getLayoutParams();
        layoutParams.height = (int) Global.mScreenHeight / 3;
        rl_map.setLayoutParams(layoutParams);

        //关闭
        barchart_e = findView(R.id.barchart_e);
        barchart_v = findView(R.id.barchart_v);
        barchart_temp = findView(R.id.barchart_temp);

        moveTo(mapview);
    }

    @SuppressLint("SetTextI18n")
    private void moveTo(TextureMapView mapview) {
        baiduMap = mapview.getMap();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            settingManagerDataBean = (SettingManagerDataBean) bundle.get("settingManagerDataBean");
            if (settingManagerDataBean != null) {

                /*安装位置*/
                String setposition = settingManagerDataBean.getSetposition();
                String type = settingManagerDataBean.getType();
                String terminalno = settingManagerDataBean.getTerminalno();
                tel = settingManagerDataBean.getTel();
                String creattime = settingManagerDataBean.getCreattime();

                ObdBean obd = settingManagerDataBean.getObd();
                if (obd != null) {
//                    showObdDatas(obd);
                }
                LogUtil.d("============size==00===" + terminalno);
                if (!TextUtils.isEmpty(terminalno)) {
                    getOBDDatas(terminalno);
                }

                tv_install_address.setText("安装位置：" + setposition);
                tv_setting_type.setText("设备类型：" + type);
                tv_setting_num.setText("设备号：" + terminalno);
                tv_install_time.setText("安装时间：" + creattime);

                /*监控区*/
                String areaname = settingManagerDataBean.getAreaname();
                personal = settingManagerDataBean.getPersonal();
                String address1 = settingManagerDataBean.getAddress();
                String detailaddress = settingManagerDataBean.getDetailaddress();
                address = address1 + detailaddress;
                tv_setting_area.setText("监控区：" + areaname);
                tv_area_personal.setText("区域负责人：" + personal + "     " + tel);
                tv_setting_address.setText("地址：" + address);

                String lat = settingManagerDataBean.getLat();
                String lng = settingManagerDataBean.getLng();

                if (TextUtils.isEmpty(lat) || TextUtils.isEmpty(lng)) {
                    latLng = new LatLng(39.915267, 116.403406);
                    showToast("无监控区详细坐标");
                } else {
                    latLng = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
                }

                if (isFirstLocate) {
                    //传入经纬度

                    //建立更新工厂
                    MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
                    //执行更新
                    baiduMap.animateMapStatus(update);
                    //建立缩放更新
                    update = MapStatusUpdateFactory.zoomTo(16f);
                    //执行更新
                    baiduMap.animateMapStatus(update);
                    isFirstLocate = false;

                    //检查是否有过标注，如果有，清除
                    if (marker != null) {
                        marker.remove();
                    }
                    //创建latlng对象管理位置
                    latLng = baiduMap.getMapStatus().target;
                    //准备 marker 的图片
                    BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.person_location);
                    //准备 marker option 添加 marker 使用
                    markerOptions = new MarkerOptions().icon(bitmap).position(latLng);
                    //获取添加的 marker 这样便于后续的操作
                    marker = (Marker) baiduMap.addOverlay(markerOptions);
                }
            }
        }
    }

    /*展示obd数据*/
//    private void showObdDatas(ObdBean obd) {
    private void showObdDatas(ObdParamBean obd) {
        List<BarEntry> entries_e = new ArrayList<>();
        List<BarEntry> entries_v = new ArrayList<>();
        List<BarEntry> entries_temp = new ArrayList<>();
        entries_e.clear();
        entries_v.clear();
        entries_temp.clear();
        String ae = obd.getAe();
        String be = obd.getBe();
        String ce = obd.getCe();
        String louele = obd.getLouele();

        entries_e.add(new BarEntry(0, returnFloat(ae)));
        entries_e.add(new BarEntry(1, returnFloat(be)));
        entries_e.add(new BarEntry(2, returnFloat(ce)));
        String substring = louele.substring(0, louele.length() - 2);
        LogUtil.d("=============" + ae + " ++ " + be + " ++ " + ce + " +  " + substring);
        entries_e.add(new BarEntry(3, Float.valueOf(substring)));
//
        String av = obd.getAv();
        String bv = obd.getBv();
        String cv = obd.getCv();

        entries_v.add(new BarEntry(0, returnFloat(av)));
        entries_v.add(new BarEntry(1, returnFloat(bv)));
        entries_v.add(new BarEntry(2, returnFloat(cv)));

        String frtemp = obd.getFrtemp();
        String totemp = obd.getTotemp();
        String thtemp = obd.getThtemp();
        String otemp = obd.getOtemp();
        float v1 = returnFloat(frtemp);
        float v2 = returnFloat(totemp);
        float v3 = returnFloat(thtemp);
        float v4 = returnFloat(otemp);
        entries_temp.add(new BarEntry(0, v1));
        entries_temp.add(new BarEntry(1, v2));
        entries_temp.add(new BarEntry(2, v3));
        entries_temp.add(new BarEntry(3, v4));

        showBarChartDatas(barchart_e, entries_e);
        showBarChartDatas(barchart_v, entries_v);
        showBarChartDatas(barchart_temp, entries_temp);
    }

    private float returnFloat(String str) {
        Log.d(TAG, "returnFloat000 " + str);
        float f = 0;

        if (!TextUtils.isEmpty(str)) {
            if (str.contains("A")) {
                String[] as = str.split("A");
                f = Float.valueOf(as[0]);
            } else if (str.contains("V")) {
                String[] as = str.split("V");
                f = Float.valueOf(as[0]);
            } else if (str.contains("℃")) {
                String[] as = str.split("℃");
                f = Float.valueOf(as[0]);
            } else if (str.contains("mA")) {
//                String[] as = str.split("mA");
                String substring = str.substring(str.length() - 2, str.length() - 1);
//                f = Float.valueOf(as[0]);
                f = Float.valueOf(substring);
            }
        } else {
            f = 0;
        }
        return f;
    }


    private void showBarChartDatas(BarChart barchart, List<BarEntry> entries) {

        //显示网格
        barchart.setDrawGridBackground(false);
        barchart.setBackgroundColor(Color.WHITE);
        //显示边界
        barchart.setDrawBorders(false);
        // 没有数据的时候，显示“暂无数据”
        barchart.setNoDataText("暂无数据");
        barchart.setDragEnabled(false);//禁止拖动
        barchart.setPinchZoom(false);//禁止缩放
        barchart.setDoubleTapToZoomEnabled(false);//禁止双击缩放
        barchart.setScaleEnabled(false);//启用/禁用缩放图表上的两个轴。
        barchart.setScaleXEnabled(false);//启用/禁用缩放在X轴上。
        barchart.setScaleYEnabled(false);//启用/禁用缩放在Y轴上。
        // 不显示y轴右边的值
        barchart.getAxisRight().setEnabled(false);
        //不行事laber
        barchart.setDescription(null);
        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(barchart);

        XAxis xAxis = barchart.getXAxis();
        xAxis.setDrawAxisLine(true); // 是否从X轴发出横向直线
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(2, true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);// 设置x轴数据的位置
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(10);
        xAxis.setAvoidFirstLastClipping(true);//设置x轴起点和终点label不超出屏幕
        xAxis.setEnabled(true);
        xAxis.setCenterAxisLabels(true);//        保证标签在中间
        xAxis.setValueFormatter(xAxisFormatter);

        YAxis yAxis = barchart.getAxisLeft();
        yAxis.setDrawAxisLine(true); // 不显示y轴
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);// 设置y轴数据的位置
        yAxis.setDrawAxisLine(false);// 设置y轴数据的位置
        yAxis.setDrawGridLines(true);
        yAxis.setTextColor(Color.BLACK);
        yAxis.setAxisMinimum(0f);
        yAxis.setTextSize(10);
        yAxis.setCenterAxisLabels(true);
        // 不显示图例
        Legend legend = barchart.getLegend();
        legend.setEnabled(false);
        //创建数据集
        BarDataSet dataSet = new BarDataSet(entries, "电流值"); // add entries to dataset
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setDrawValues(false);

        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(barchart); // For bounds control
        barchart.setMarker(mv); // Set the marker to the chart
        //设置数据显示颜色：柱子颜色

        dataSet.setColors(getResources().getColor(R.color.barchart_red),
                getResources().getColor(R.color.barchart_orange),
                getResources().getColor(R.color.barchart_yellow),
                getResources().getColor(R.color.barchart_green));
//        dataSet.setColor(Color.RED);
        dataSet.setBarBorderColor(Color.BLUE);
        //柱状图数据集
        BarData data = new BarData(dataSet);
//设置柱子宽度
        float barWidth = 0.4f;
        data.setBarWidth(barWidth);
        barchart.setData(data);//装载数据
//        barchart_e.groupBars(0f, groupSpace, barSpace);
        barchart.invalidate();//刷新
    }


    @Override
    public void initListener() {
        tv_next.setOnClickListener(this);
        tv_title_right_des.setOnClickListener(this);
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
            intent.setClass(SettingInfosActivity.this, ChangerSettingActivity.class);
            intent.putExtra("settingManagerDataBean", settingManagerDataBean);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                finish();
                String type = bundle.getString("type");
                String sensorPosition = bundle.getString("sensorPosition");
                String terminalNO = bundle.getString("terminalNO");
                String createTime = bundle.getString("createTime");
                String areaName = bundle.getString("areaName");
                String personal = bundle.getString("personal");
                String tel = bundle.getString("tel");
                String address = bundle.getString("address");
                String lat = bundle.getString("lat");
                String lng = bundle.getString("lng");
                tv_install_address.setText("安装位置：" + sensorPosition);
                tv_setting_type.setText("设备类型：" + type);
                tv_setting_num.setText("设备号：" + terminalNO);
                tv_install_time.setText("安装时间：" + createTime);
                tv_setting_area.setText("监控区：" + areaName);

                tv_area_personal.setText("区域负责人：" + personal + "     " + tel);
                tv_setting_address.setText("地址：" + address);

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


    private void getOBDDatas(String terminalNo) {
        showWaitDialog(SettingInfosActivity.this, getString(R.string.inupdate));
        mCommonProtocol = new CommonProtocol();
        mCommonProtocol.getsensorObd(this, terminalNo);
    }

    @Override
    public void onHttpSuccess(int reqType, Message obj) {
        super.onHttpSuccess(reqType, obj);
        dismissWaitDialog();

        if (reqType == IHttpService.TYPE_GETOBD) {
            GetsensorObdSuccessBean bean = (GetsensorObdSuccessBean) obj.obj;
            GetsensorObdDataBean obdDataBean = bean.getData().get(0);
            LogUtil.d("============size==" + bean.getData().size() + "  +++ " + obdDataBean.getProcUserName());
            ObdParamBean obdBean = obdDataBean.getObdParam();
            showObdDatas(obdBean);
        }
    }

    @Override
    public void onHttpError(int reqType, String error) {
        super.onHttpError(reqType, error);
        dismissWaitDialog();
    }
}
