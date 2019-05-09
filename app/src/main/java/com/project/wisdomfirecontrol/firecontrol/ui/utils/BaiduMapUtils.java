package com.project.wisdomfirecontrol.firecontrol.ui.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Global;

/**
 * Created by Administrator on 2017/12/13.
 */

public class BaiduMapUtils {

    private static String address;

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     * @return true 表示开启
     */
    public static boolean isOPenGPS(Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        if (locationManager != null) {
            boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
            boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (gps) {
                return true;
            } else {
                // 转到手机设置界面，用户设置GPS
                Global.showToast(context.getResources().getString(R.string.open_gps));
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                ((AppCompatActivity) context).startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
            }
            if (network) {
                return true;
            }
        }
        return false;
    }


    public static LatLng ConverCommonToBaidu(LatLng sourceLatLng) {
        // 将COMMON坐标转换成百度坐标
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.COMMON);
        // sourceLatLng待转换坐标
        converter.coord(sourceLatLng);
        LatLng desLatLng = converter.convert();
        return desLatLng;
    }

    public static LatLng ConverGpsToBaidu(LatLng sourceLatLng) {
        // 将Gps设备获取坐标转换成百度坐标
        CoordinateConverter converter = new CoordinateConverter();

        converter.from(CoordinateConverter.CoordType.GPS);
        // sourceLatLng待转换坐标
        converter.coord(sourceLatLng);
        LatLng desLatLng = converter.convert();
        return desLatLng;
    }

    /*
     * 让地图在中间
     *@param bm
      @param ll
     */

    public static void MoveMapToCenter(BaiduMap bm, LatLng ll, int zoom) {
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(zoom);
        bm.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }


    /*坐标转地址*/
    public static String returnAddress(GeoCoder geoCoder, LatLng latLng,String city,String district) {

        if (TextUtils.isEmpty(city) || TextUtils.isEmpty(district)) {
            // ReverseGeo搜索
            geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
        }else {
            // Geo搜索
            geoCoder.geocode(new GeoCodeOption().city(city).address(district));
        }

        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

            @SuppressLint("DefaultLocale")
            @Override
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有检索到结果
                    address = "未匹配到相应地址信息";
                } else {
                    address = String.format("%f,%f", result.getLocation().longitude, result.getLocation().latitude);
                }
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    address = "未匹配到相应地址";
                } else {
                    //获取反向地理编码结果
                    address = result.getAddress();
                }
            }
        });

        return address;
    }
}
