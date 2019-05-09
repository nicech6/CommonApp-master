package com.project.wisdomfirecontrol.firecontrol.ui.activity_unit;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.mvp_0726.project_0726.login.ui.SplashActivity;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.common.base.Const;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.base.UserInfo;
import com.project.wisdomfirecontrol.common.base.UserManage;
import com.project.wisdomfirecontrol.common.util.StringUtils;
import com.project.wisdomfirecontrol.firecontrol.base.MyApplication;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.CommonProtocol;
import com.project.wisdomfirecontrol.firecontrol.ui.adapter_Rv.GridImageAdapter;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.BaiduMapUtils;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.PhotoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 * 巡查打卡
 */

public class PatrolclockActivity extends BaseActivity {

    private static final String TAG = "tag";
    private String intent_key;
    private TextView tv_title, tv_signin_address, tv_location, tv_signin_time, tv_upload_signin, tv_signin_count;
    private EditText et_signin_memo;
    private MapView bmapView;
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
    private List<LocalMedia> selectList = new ArrayList<>();
    private TextView tv_upload_infos;
    private RecyclerView recyclerView;
    private PhotoUtils photoUtils;
    private String userid;
    private String pid;
    private String memo;
    private CommonProtocol commonProtocol;
    private String imageurl = "";
    private String lat;
    private String lng;
    private StringBuffer stringBuffer;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_patrolckock;
    }

    @Override
    public void initView() {
        tv_title = findView(R.id.tv_title);
        bmapView = findView(R.id.bmapView);
        tv_location = findView(R.id.tv_location);
        tv_location.setOnClickListener(this);
        tv_signin_address = findView(R.id.tv_signin_address);
        tv_title.setText(getResources().getString(R.string.patrolckock_title));
        tv_upload_infos = findView(R.id.tv_upload_infos);
        tv_signin_time = findView(R.id.tv_signin_time);
        et_signin_memo = findView(R.id.et_signin_memo);
        tv_upload_signin = findView(R.id.tv_upload_signin);
        tv_signin_count = findView(R.id.tv_signin_count);
        tv_upload_signin.setOnClickListener(this);
        recyclerView = findView(R.id.recycler);
        if (photoUtils == null) {
            photoUtils = new PhotoUtils(PatrolclockActivity.this, recyclerView,
                    onAddPicClickListener, selectList, tv_upload_infos,
                    Const.MAXSELECTNUM_SIX, Const.COUNT_FOURTH, true);
        }

        //开启前台定位服务：
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            LocationClient locationClient = new LocationClient(this);
            Notification.Builder builder = new Notification.Builder(PatrolclockActivity.this.getApplicationContext());
//获取一个Notification构造器
            Intent nfIntent = new Intent(PatrolclockActivity.this.getApplicationContext(), SplashActivity.class);
            builder.setContentIntent(PendingIntent.getActivity(PatrolclockActivity.this, 0, nfIntent, 0)) // 设置PendingIntent
                    .setContentTitle("正在进行后台定位") // 设置下拉列表里的标题
                    .setSmallIcon(R.drawable.icon_app) // 设置状态栏内的小图标
                    .setContentText("后台定位通知") // 设置上下文内容
                    .setAutoCancel(true)
                    .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间
            Notification notification = null;
            notification = builder.build();
            notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
            locationClient.enableLocInForeground(1001, notification);// 调起前台定位
        }
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            if (ContextCompat.checkSelfPermission(PatrolclockActivity.this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                if (photoUtils != null) {
                    photoUtils.showSelectVideoOrPhoto("photo", "activity");
                }
            } else {
                Global.showToast(getResources().getString(R.string.no_photo_perssion));
            }
        }

    };

    private void isHasLocationPermission(final MapView bmapView) {
        if (ContextCompat.checkSelfPermission(PatrolclockActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            initMap(bmapView);
        } else {
            showToast(getResources().getString(R.string.open_gps));
        }
    }

    private void initMap(MapView bmapView) {
        baiduMap = bmapView.getMap();
        // 地图初始化
        bmapView.showZoomControls(true);// 缩放控件是否显示
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
        option.setAddrType("all");
        mLocClient.setLocOption(option);
        mLocClient.start();
//        baiduMap.showMapPoi(false);
    }



    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        intent_key = intent.getStringExtra("INTENT_KEY");

        String currentTime = StringUtils.getCurrentTime();
        if (!StringUtils.isEmpty(currentTime)) {
            tv_signin_time.setText(currentTime);
        }

    }

    @Override
    public void onClick(View v, int id) {
        switch (v.getId()) {
            case R.id.tv_location:
                tv_location();
                break;
            case R.id.tv_upload_signin:
                upload_signin();
                break;
        }
    }


    //    打卡签到
    private void upload_signin() {
        boolean networkConnected = MyApplication.getInstance().isNetworkConnected();
        if (!networkConnected) {
            showToast(getResources().getString(R.string.no_net));
            return;
        }
        mAddrStr = tv_signin_address.getText().toString().trim();
        memo = et_signin_memo.getText().toString().trim();
        boolean hasUserInfo = UserManage.getInstance().hasUserInfo(Global.mContext);
        if (hasUserInfo) {
            UserInfo userIdInfo = UserManage.getInstance().getUserIdInfo(Global.mContext);
            userid = userIdInfo.getUserid();
            pid = userIdInfo.getPid();
            if (TextUtils.isEmpty(userid) && TextUtils.isEmpty(pid)) {
                showToast(getString(R.string.login_again));
                return;
            }
            lat = String.valueOf(mCurrentLat);
            lng = String.valueOf(mCurrentLon);
            if (TextUtils.isEmpty(lat)
                    || TextUtils.isEmpty(lng)) {
                showToast(getString(R.string.location_again));
                return;
            }
            commonProtocol = new CommonProtocol();
            showWaitDialog(this, "打卡中...");
//            Log.d(TAG, "upload_signin: " + userid + " +++ " + lat + " +++ " + lng +
//                    " +++ " + mAddrStr + " +++ " + memo + " +++ " + imageurl + "\r\n +++ " + pid + " +++ ");
//            RetrofitManager.changeApiBaseUrl(NetworkUrl.ANDROID_BAIDU_SERVICE);
            commonProtocol.daka(this, userid, lat, lng, mAddrStr, memo, imageurl, pid);
        }

    }

    @Override
    public void onHttpSuccess(int reqType, Message msg) {
        super.onHttpSuccess(reqType, msg);
        showSuccessDialog(this, getString(R.string.success));

    }


    @Override
    public void onHttpError(int reqType, String error) {
        super.onHttpError(reqType, error);
        dismissWaitDialog();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (bmapView == null) {
            return;
        }
        boolean oPenGPS = BaiduMapUtils.isOPenGPS(this);
        if (oPenGPS) {
            isHasLocationPermission(bmapView);
        }
    }

    public void tv_location() {
        if (baiduMap != null && latLng != null) {
            BaiduMapUtils.MoveMapToCenter(baiduMap, latLng, Const.BAIDU_ZOOM);
        } else {
            showToast(getResources().getString(R.string.wait_letter));
        }
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || bmapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mAddrStr = location.getAddrStr();

            latLng = new LatLng(mCurrentLat, mCurrentLon);
            latLng = BaiduMapUtils.ConverGpsToBaidu(latLng);
            if (!StringUtils.isEmpty(mAddrStr)) {
                tv_signin_address.setText(mAddrStr);
            }
            if (isFirstLoc) {
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
    protected void onStop() {
        super.onStop();
        isFirstLoc = true;
        if (baiduMap != null) {
            baiduMap.clear();
        }
        if (mLocClient != null) {
            mLocClient.stop();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList.size() > 0) {
                        showWaitDialog(PatrolclockActivity.this, "图片处理中...");
                        if (photoUtils != null) {
                            imageurl = reducePhoto(selectList);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissWaitDialog();
                            }
                        });
                    }
                    if (photoUtils != null) {
                        photoUtils.setList(selectList);
                        photoUtils.notifyChanged();
                    }
                    break;
            }
        }
    }

    public String reducePhoto(final List<LocalMedia> dataList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (stringBuffer == null) {
                    stringBuffer = new StringBuffer();
                }
                stringBuffer.setLength(0);
                for (int i = 0; i < dataList.size(); i++) {
                    if (i == (dataList.size() - 1)) {
                        stringBuffer.append(com.project.wisdomfirecontrol.common.util.StringUtils.getimage(dataList.get(i).getCompressPath()));
                    } else {
                        stringBuffer.append(com.project.wisdomfirecontrol.common.util.StringUtils.getimage(dataList.get(i).getCompressPath())).append(",");
                    }
                }
                imageurl = "";
                imageurl = stringBuffer.toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissWaitDialog();
                    }
                });
            }
        }).start();

        return imageurl;
    }



}
