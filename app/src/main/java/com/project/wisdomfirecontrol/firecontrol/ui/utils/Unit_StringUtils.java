package com.project.wisdomfirecontrol.firecontrol.ui.utils;

import android.content.Context;
import android.text.TextUtils;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Const;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.base.UserInfo;
import com.project.wisdomfirecontrol.common.base.UserManage;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.IHttpService;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_common.FireInspectionActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_unit.Unit_StatementChangeActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.fragment_common.HomeFragment;

import java.util.List;

/**
 * Created by Administrator on 2018/3/8.
 */

public class Unit_StringUtils {

    public static int[] imagesCommon = new int[]{
            R.drawable.icon_lwdw,
            R.drawable.icon_xfbj,
            R.drawable.icon_sbgz,

            R.drawable.icon_tjfx,
            R.drawable.icon_aqdj,
            R.drawable.icon_aqry,
            R.drawable.icon_xzwj,
            R.drawable.icon_spjg,
            R.drawable.icon_xfzg,

            R.drawable.icon_xfjk,
            R.drawable.icon_xfbj,
            R.drawable.icon_sbgz,

            R.drawable.icon_dwnd,
            R.drawable.icon_dwyd,
            R.drawable.icon_zzgl,

            R.drawable.icon_zbry,
            R.drawable.icon_xfxj,

            R.drawable.icon_xjdk,
            R.drawable.icon_yhsb,
            R.drawable.icon_xjjl,
            R.drawable.icon_yhjl,

            R.drawable.icon_xftz,
            R.drawable.icon_xfzg,

            R.drawable.icon_lwsb,

            R.drawable.icon_lwzy,
            R.drawable.icon_gzzy,

    };

    /*消防巡查 icon image*/
    public static int returnStateFireBgStr(String string) {
        int count = 0;
        if (string.contains(FireInspectionActivity.listData.get(0))) {
            count = imagesCommon[17];

        } else if (string.contains(FireInspectionActivity.listData.get(1))) {
            count = imagesCommon[18];

        } else if (string.contains(FireInspectionActivity.listData.get(2))) {
            count = imagesCommon[19];

        } else if (string.contains(FireInspectionActivity.listData.get(3))) {
            count = imagesCommon[20];

        }
        return count;
    }

    public static int returnStateBgStr(String string) {
        int count = 0;
        if (string.contains(Unit_StatementChangeActivity.listData.get(0))) {
            count = imagesCommon[0];
        } else if (string.contains(Unit_StatementChangeActivity.listData.get(1))) {
            count = imagesCommon[1];
        } else if (string.contains(Unit_StatementChangeActivity.listData.get(2))) {
            count = imagesCommon[2];

        } else if (string.contains(Unit_StatementChangeActivity.listData.get(3))) {
            count = imagesCommon[3];
        } else if (string.contains(Unit_StatementChangeActivity.listData.get(4))) {
            count = imagesCommon[4];
        } else if (string.contains(Unit_StatementChangeActivity.listData.get(5))) {
            count = imagesCommon[5];

        } else if (string.contains(Unit_StatementChangeActivity.listData.get(6))) {
            count = imagesCommon[6];
        } else if (string.contains(Unit_StatementChangeActivity.listData.get(7))) {
            count = imagesCommon[7];
        } else if (string.contains(Unit_StatementChangeActivity.listData.get(8))) {
            count = imagesCommon[8];
        } else {
            count = imagesCommon[8];
        }

        return count;
    }

    //    企业端返回URL
    public static String returnStateNetUrlStr(String string) {
        String count = "";
        if (string.contains(Unit_StatementChangeActivity.listData.get(0))) {
//            count = Unit_StatementChangeActivity.listData.get(0);
            count = Const.GO_SETTINGONLINE_FIRST;
        } else if (string.contains(Unit_StatementChangeActivity.listData.get(1))) {
//            count = Unit_StatementChangeActivity.listData.get(1);
            count = Const.GO_SETTINGONLINE_SECOND;
        } else if (string.contains(Unit_StatementChangeActivity.listData.get(2))) {
//            count = Unit_StatementChangeActivity.listData.get(2);
            count = Const.GO_SETTINGONLINE_THIRD;

        } else if (string.contains(Unit_StatementChangeActivity.listData.get(3))) {
            count = Unit_StatementChangeActivity.listData.get(3);
        } else if (string.contains(Unit_StatementChangeActivity.listData.get(4))) {
            count = Unit_StatementChangeActivity.listData.get(4);
        } else if (string.contains(Unit_StatementChangeActivity.listData.get(5))) {
            count = Unit_StatementChangeActivity.listData.get(5);

        } else if (string.contains(Unit_StatementChangeActivity.listData.get(6))) {
            count = Unit_StatementChangeActivity.listData.get(6);
        } else if (string.contains(Unit_StatementChangeActivity.listData.get(7))) {
            count = Const.GO_NETDEVICE;
        } else if (string.contains(Unit_StatementChangeActivity.listData.get(9))) {
            count = Const.GO_MYSYSTEM;
        } else {
            count = Const.GO_SETTINGMANAGER;
        }
        return count;
    }

    public static int returnActivityItemBgStr(String string) {

        int count = 0;
        if (string.contains(IHttpService.TONGJIFENXI)) {
            count = Const.COUNT_FOURTH;
        } else if (string.contains(IHttpService.SAFE_DENGJI)) {
            count = Const.COUNT_FIFTH;
        } else if (string.contains(IHttpService.SAFE_PERSONAL)) {
            count = Const.COUNT_SIXTH;

        } else if (string.contains(IHttpService.XINGZHENGGONGWEN)) {
            count = Const.COUNT_SEVENTH;
        } else if (string.contains(IHttpService.GONGGONGZIYUAN)) {
            count = Const.COUNT_NINTH;
        } else if (string.contains(IHttpService.XIAOFANGPINGJI)) {
            count = Const.COUNT_EIGHTH_TEN;
        } else {
            count = Const.COUNT_THIRD;
        }

        return count;
    }


    public static String returnStattFireItemName(String string) {
        String url = "";
        if (string.contains(FireInspectionActivity.listData.get(0))) {
            url = FireInspectionActivity.listData.get(0);

        } else if (string.contains(FireInspectionActivity.listData.get(1))) {
            url = FireInspectionActivity.listData.get(1);

        } else if (string.contains(FireInspectionActivity.listData.get(2))) {
            url = FireInspectionActivity.listData.get(2);

        } else if (string.contains(FireInspectionActivity.listData.get(3))) {
            url = FireInspectionActivity.listData.get(3);

        }
        return url;
    }


    public static String returnItemName(String string) {
        String url = "";
        if (string.contains(HomeFragment.onlineList.get(0))) {
            url = HomeFragment.sudoku_h5_List.get(0);
        } else if (string.contains(HomeFragment.onlineList.get(1))) {
            url = HomeFragment.sudoku_h5_List.get(1);
        } else if (string.contains(HomeFragment.onlineList.get(2))) {
            url = HomeFragment.sudoku_h5_List.get(2);

        } else if (string.contains(HomeFragment.analyzeList.get(0))) {
            url = HomeFragment.sudoku_h5_List.get(3);
        } else if (string.contains(HomeFragment.analyzeList.get(1))) {
            url = HomeFragment.sudoku_h5_List.get(4);
        } else if (string.contains(HomeFragment.analyzeList.get(2))) {
            url = HomeFragment.sudoku_h5_List.get(5);

        } else if (string.contains(HomeFragment.safeList.get(0))) {
            url = HomeFragment.sudoku_h5_List.get(6);
        } else if (string.contains(HomeFragment.safeList.get(1))) {
            url = HomeFragment.sudoku_h5_List.get(7);
        } else if (string.contains(HomeFragment.safeList.get(2))) {
            url = Const.GO_DECUMENT_MANAGE;

        } else if (string.contains(HomeFragment.firecorolList.get(0))) {
            url = HomeFragment.sudoku_h5_List.get(8);
//        } else if (string.contains(HomeFragment.firecorolList.get(1))) {

//            url = IHttpService.FIRE_INSPECTION_URL;
        } else if (string.contains(HomeFragment.firecorolList.get(1))) {
            url = Const.GO_NETDEVICE;
        } else if (string.contains(HomeFragment.firecorolList.get(2))) {
            url = Const.GO_HIDDENTROUBLE;
        } else if (string.contains(HomeFragment.firecorolList.get(3))) {
            url = HomeFragment.sudoku_h5_List.get(9);
        } else if (string.contains(HomeFragment.firecorolList.get(4))) {
            url = HomeFragment.sudoku_h5_List.get(10);

//        } else if (string.contains(HomeFragment.decumentList.get(0))) {
//            url = IHttpService.RECTIFICATION_DOCUMENT_URL;
        } else if (string.contains(HomeFragment.decumentList.get(0))) {
            url = Const.GO_RECTIFICATION;
        } else if (string.contains(HomeFragment.decumentList.get(1))) {
            url = HomeFragment.sudoku_h5_List.get(11);


        }

        return url;
    }

    public static String returnItemNetUrl(String string) {
        String url = "";
        if (string.contains(HomeFragment.onlineList.get(0))) {
            url = IHttpService.NETDEVICE_URL;
        } else if (string.contains(HomeFragment.onlineList.get(1))) {
            url = IHttpService.FIREALARM_URL;
        } else if (string.contains(HomeFragment.onlineList.get(2))) {
            url = IHttpService.DEVICEORDER_URL;

        } else if (string.contains(HomeFragment.analyzeList.get(0))) {
            url = IHttpService.ATOUMATION_ANALYZE_URL;
        } else if (string.contains(HomeFragment.analyzeList.get(1))) {
            url = IHttpService.FIREALARM_ANALYZE_URL;
        } else if (string.contains(HomeFragment.analyzeList.get(2))) {
            url = IHttpService.DEVICEORDER_ANALYZE_URL;

        } else if (string.contains(HomeFragment.safeList.get(0))) {
            url = IHttpService.NET_YEAR_SAFETY_URL;
        } else if (string.contains(HomeFragment.safeList.get(1))) {
            url = IHttpService.NET_MOUTH_SAFETY_URL;
        } else if (string.contains(HomeFragment.safeList.get(2))) {
            url = Const.GO_DECUMENT_MANAGE;

        } else if (string.contains(HomeFragment.firecorolList.get(0))) {
            url = IHttpService.WATCH_KEEPER_URL;
//        } else if (string.contains(HomeFragment.firecorolList.get(1))) {
//            url = IHttpService.FIRE_INSPECTION_URL;
        } else if (string.contains(HomeFragment.firecorolList.get(1))) {
            url = Const.GO_NETDEVICE;
        } else if (string.contains(HomeFragment.firecorolList.get(2))) {
            url = Const.GO_HIDDENTROUBLE;
        } else if (string.contains(HomeFragment.firecorolList.get(3))) {
            url = IHttpService.INSPECTION_RECORD_URL;
        } else if (string.contains(HomeFragment.firecorolList.get(4))) {
            url = IHttpService.HIDDENTROUBLE_RECORD_URL;

//        } else if (string.contains(HomeFragment.decumentList.get(0))) {
//            url = IHttpService.RECTIFICATION_DOCUMENT_URL;
        } else if (string.contains(HomeFragment.decumentList.get(0))) {
            url = Const.GO_RECTIFICATION;
        } else if (string.contains(HomeFragment.decumentList.get(1))) {
            url = IHttpService.NOTIFICATION_DOCUMENT_URL;
        }

        return url;
    }


    public static String returnWebNetUrlAndJoin(String string) {
        String url = "";

        if (string.contains(FireInspectionActivity.listData.get(0))) {
            url = Const.GO_NETDEVICE;

        } else if (string.contains(FireInspectionActivity.listData.get(1))) {
            url = Const.GO_HIDDENTROUBLE;

        } else if (string.contains(FireInspectionActivity.listData.get(2))) {
            url = IHttpService.INSPECTION_RECORD_URL;

        } else if (string.contains(FireInspectionActivity.listData.get(3))) {
            url = IHttpService.HIDDENTROUBLE_RECORD_URL;

        }

        return url;
    }

    public static String returnWebNetUrl(String string) {
        String url = "";
        if (string.contains(IHttpService.NETDEVICE_URL)) {
            url = IHttpService.NETDEVICE_URL;
        } else if (string.contains(IHttpService.FIREALARM_URL)) {
            url = IHttpService.FIREALARM_URL;
        } else if (string.contains(IHttpService.DEVICEORDER_URL)) {
            url = IHttpService.DEVICEORDER_URL;

        } else if (string.contains(IHttpService.ATOUMATION_ANALYZE_URL)) {
            url = IHttpService.ATOUMATION_ANALYZE_URL;
        } else if (string.contains(IHttpService.FIREALARM_ANALYZE_URL)) {
            url = IHttpService.FIREALARM_ANALYZE_URL;
        } else if (string.contains(IHttpService.DEVICEORDER_ANALYZE_URL)) {
            url = IHttpService.DEVICEORDER_ANALYZE_URL;

        } else if (string.contains(IHttpService.NET_YEAR_SAFETY_URL)) {
            url = IHttpService.NET_YEAR_SAFETY_URL;
        } else if (string.contains(IHttpService.NET_MOUTH_SAFETY_URL)) {
            url = IHttpService.NET_MOUTH_SAFETY_URL;
        } else if (string.contains(IHttpService.FIRE_DECUMENT_MANAGE_URL)) {
            url = IHttpService.FIRE_DECUMENT_MANAGE_URL;

        } else if (string.contains(IHttpService.WATCH_KEEPER_URL)) {
            url = IHttpService.WATCH_KEEPER_URL;
        } else if (string.contains(IHttpService.FIRE_INSPECTION_URL)) {
            url = IHttpService.FIRE_INSPECTION_URL;
        } else if (string.contains(IHttpService.INSPECTION_RECORD_URL)) {
            url = IHttpService.INSPECTION_RECORD_URL;
        } else if (string.contains(IHttpService.HIDDENTROUBLE_RECORD_URL)) {
            url = IHttpService.HIDDENTROUBLE_RECORD_URL;

        } else if (string.contains(IHttpService.RECTIFICATION_DOCUMENT_URL)) {
            url = IHttpService.RECTIFICATION_DOCUMENT_URL;
        } else {
            url = IHttpService.NOTIFICATION_DOCUMENT_URL;
        }
        return url;
    }

    public static String getUserId(Context context) {
        String userid = "";
        boolean hasUserInfo = UserManage.getInstance().hasUserInfo(Global.mContext);
        if (hasUserInfo) {
            UserInfo userIdInfo = UserManage.getInstance().getUserIdInfo(Global.mContext);
            userid = userIdInfo.getUserid();
            if (TextUtils.isEmpty(userid)) {
                Global.showToast(context.getResources().getString(R.string.login_again));
            } else {
                return userid;
            }
        }
        return userid;
    }

    public static String getUserPid(Context context) {
        String pid = "";
        boolean hasUserInfo = UserManage.getInstance().hasUserInfo(Global.mContext);
        if (hasUserInfo) {
            UserInfo userIdInfo = UserManage.getInstance().getUserIdInfo(Global.mContext);
            pid = userIdInfo.getPid();
            if (TextUtils.isEmpty(pid)) {
                Global.showToast(context.getResources().getString(R.string.login_again));
            } else {
                return pid;
            }
        }
        return pid;
    }

    public static UserInfo getUserInfos(Context context) {
        UserInfo userIdInfo = null;
        boolean hasUserInfo = UserManage.getInstance().hasUserInfo(Global.mContext);
        if (hasUserInfo) {
            userIdInfo = UserManage.getInstance().getUserInfo(Global.mContext);
            return userIdInfo;
        } else {
            Global.showToast(context.getResources().getString(R.string.login_again));
        }
        return userIdInfo;
    }

    public static String returnActivityH5Url(int itemCount, int countFirstOne) {
        String url = "";
        if (itemCount == Const.COUNT_FIRST || itemCount == Const.COUNT_SECOND || itemCount == Const.COUNT_THIRD) {

            if (countFirstOne == Const.COUNT_FIRST_ONE) {
                url = IHttpService.NETDEVICE_URL;
            } else if (countFirstOne == Const.COUNT_FIRST_TWO) {
                url = IHttpService.FIREALARM_URL;
            } else {
                url = IHttpService.DEVICEORDER_URL;
            }

        } else if (itemCount == Const.COUNT_FOURTH) {

            if (countFirstOne == Const.COUNT_FIRST_ONE) {
                url = IHttpService.ATOUMATION_ANALYZE_URL;
            } else if (countFirstOne == Const.COUNT_FIRST_TWO) {
                url = IHttpService.FIREALARM_ANALYZE_URL;
            } else {
                url = IHttpService.DEVICEORDER_ANALYZE_URL;
            }

        } else if (itemCount == Const.COUNT_FIFTH) {
            if (countFirstOne == Const.COUNT_FIRST_ONE) {
                url = IHttpService.NET_YEAR_SAFETY_URL;
            } else if (countFirstOne == Const.COUNT_FIRST_TWO) {
                url = IHttpService.NET_MOUTH_SAFETY_URL;
            } else {
                url = IHttpService.FIRE_DECUMENT_MANAGE_URL;//跳转资质管理
            }

        } else if (itemCount == Const.COUNT_SIXTH) {
            if (countFirstOne == Const.COUNT_FIRST_ONE) {
                url = IHttpService.WATCH_KEEPER_URL;
            } else {
                url = IHttpService.CHECKING_CLOCK_URL;//跳转消防巡检
            }

        } else if (itemCount == Const.COUNT_SEVENTH) {
            if (countFirstOne == Const.COUNT_FIRST_ONE) {
                url = IHttpService.NOTIFICATION_DOCUMENT_URL;
            } else {
                url = IHttpService.RECTIFICATION_DOCUMENT_URL;

            }
        } else if (itemCount == Const.COUNT_EIGHTH_TEN) {
            if (countFirstOne == Const.COUNT_FIRST_ONE) {
                url = IHttpService.DEVICEORDER_ANALYZE_URL_UNIT;
            } else if (countFirstOne == Const.COUNT_FIRST_TWO) {
                url = IHttpService.NET_YEAR_SAFETY_URL_UNIT;
            } else if (countFirstOne == Const.COUNT_FIRST_THREE) {
                url = IHttpService.NET_MOUTH_SAFETY_URL_UNIT;
            } else {
                url = IHttpService.INSPECTION_RECORD_URL_UNIT;
            }
        } else {
            if (countFirstOne == Const.COUNT_FIRST_ONE) {
                url = IHttpService.FIREALARM_URL_UNIT;
            } else {
                url = IHttpService.FIREALARM_BAD_URL_UNIT;

            }
        }
        return url;
    }

    /*返回title name*/
    public static String returnActivityTitle(int itemCount, int countFirstOne, List<String> item_first_Name,
                                             List<String> item_fourth_Name, List<String> item_fifth_Name,
                                             List<String> item_sixth_Name, List<String> item_seventh_Name, List<String> item_fourth_name1) {
        String url = "";
        if (itemCount == Const.COUNT_FIRST || itemCount == Const.COUNT_SECOND || itemCount == Const.COUNT_THIRD) {

            if (countFirstOne == Const.COUNT_FIRST_ONE) {
                url = item_first_Name.get(0);
            } else if (countFirstOne == Const.COUNT_FIRST_TWO) {
                url = item_first_Name.get(1);
            } else {
                url = item_first_Name.get(2);
            }

        } else if (itemCount == Const.COUNT_FOURTH) {

            if (countFirstOne == Const.COUNT_FIRST_ONE) {
                url = item_fourth_Name.get(0);
            } else if (countFirstOne == Const.COUNT_FIRST_TWO) {
                url = item_fourth_Name.get(1);
            } else {
                url = item_fourth_Name.get(2);
            }

        } else if (itemCount == Const.COUNT_FIFTH) {
            if (countFirstOne == Const.COUNT_FIRST_ONE) {
                url = item_fifth_Name.get(0);
            } else if (countFirstOne == Const.COUNT_FIRST_TWO) {
                url = item_fifth_Name.get(1);
            } else {
                url = item_fifth_Name.get(2);
            }
        } else if (itemCount == Const.COUNT_EIGHTH_TEN) {
            if (countFirstOne == Const.COUNT_FIRST_ONE) {
                url = item_fifth_Name.get(0);
            } else if (countFirstOne == Const.COUNT_FIRST_TWO) {
                url = item_fifth_Name.get(1);
            } else {
                url = item_fifth_Name.get(2);
            }

        } else if (itemCount == Const.COUNT_SIXTH) {
            if (countFirstOne == Const.COUNT_FIRST_ONE) {
                url = item_sixth_Name.get(0);
            } else if (countFirstOne == Const.COUNT_FIRST_TWO) {
                url = item_sixth_Name.get(1);
            } else {
                url = item_sixth_Name.get(1);
            }

        } else if (itemCount == Const.COUNT_SEVENTH) {
            if (countFirstOne == Const.COUNT_FIRST_ONE) {
                url = item_seventh_Name.get(0);
            } else if (countFirstOne == Const.COUNT_FIRST_TWO) {
                url = item_seventh_Name.get(1);
            } else {
                url = item_seventh_Name.get(1);
            }
        } else if (itemCount == Const.COUNT_EIGHTH) {
            url = "视频监管";
        } else {
            if (countFirstOne == Const.COUNT_FIRST_ONE) {
                url = item_fourth_name1.get(0);
            } else {
                url = item_fourth_name1.get(1);
            }
        }
        return url;
    }
}
