package com.project.wisdomfirecontrol.firecontrol.ui.utils;

import android.content.Context;
import android.text.TextUtils;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Const;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.base.UserInfo;
import com.project.wisdomfirecontrol.common.base.UserManage;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.IHttpService;
import com.project.wisdomfirecontrol.firecontrol.ui.fragment_supevise.Supevise_StatementFragment;

import java.util.List;


/**
 * Created by Administrator on 2018/3/8.
 */

public class Supevise_StringUtils {

    public static int[] imagesCommon = new int[]{
            R.drawable.icon_lwdw_unit,
            R.drawable.icon_xfbj,
            R.drawable.icon_sbgz,

            R.drawable.icon_ggxf,
            R.drawable.icon_tjfx,
            R.drawable.icon_xfdj,
            R.drawable.icon_xzwj,
            R.drawable.icon_spjg,
            R.drawable.icon_xfzg,

            R.drawable.icon_lwzy,
            R.drawable.icon_gzzy,

            R.drawable.icon_lwdw_unit,
            R.drawable.icon_xfbj,
            R.drawable.icon_sbgz,

            R.drawable.icon_nddj,
            R.drawable.icon_yddj,
            R.drawable.icon_ydqs,

            R.drawable.icon_tzgw,
            R.drawable.icon_zggw,
            R.drawable.icon_tzlb,
            R.drawable.icon_zglb,
    };

    public static int returnStateBgStr(String string) {
        int count = 0;
        if (string.contains(Supevise_StatementFragment.listData.get(0))) {
            count = imagesCommon[0];
        } else if (string.contains(Supevise_StatementFragment.listData.get(1))) {
            count = imagesCommon[1];
        } else if (string.contains(Supevise_StatementFragment.listData.get(2))) {
            count = imagesCommon[2];

        } else if (string.contains(Supevise_StatementFragment.listData.get(3))) {
            count = imagesCommon[3];
        } else if (string.contains(Supevise_StatementFragment.listData.get(4))) {
            count = imagesCommon[4];
        } else if (string.contains(Supevise_StatementFragment.listData.get(5))) {
            count = imagesCommon[5];

        } else if (string.contains(Supevise_StatementFragment.listData.get(6))) {
            count = imagesCommon[6];
        } else if (string.contains(Supevise_StatementFragment.listData.get(7))) {
            count = imagesCommon[7];
        } else if (string.contains(Supevise_StatementFragment.listData.get(8))) {
            count = imagesCommon[8];
        }else {
            count = imagesCommon[8];
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
        } else if (string.contains(IHttpService.XIAOFANGPINGJI)) {
            count = Const.COUNT_EIGHTH;
        }else {
            count = Const.COUNT_THIRD;
        }

        return count;

//        int count = 0;
//        if (string.contains(Supevise_StatementFragment.listData.get(0))) {
//            count = Const.COUNT_FIRST;
//        } else if (string.contains(Supevise_StatementFragment.listData.get(1))) {
//            count = Const.COUNT_SECOND;
//        } else if (string.contains(Supevise_StatementFragment.listData.get(2))) {
//            count = Const.COUNT_THIRD;
//        } else if (string.contains(Supevise_StatementFragment.listData.get(3))) {
//            count = Const.COUNT_FOURTH;
//        } else if (string.contains(Supevise_StatementFragment.listData.get(4))) {
//            count = Const.COUNT_FIFTH;
//        } else if (string.contains(Supevise_StatementFragment.listData.get(5))) {
//            count = Const.COUNT_SIXTH;
//
//        } else if (string.contains(Supevise_StatementFragment.listData.get(6))) {
//            count = Const.COUNT_SEVENTH;
//        } else if (string.contains(Supevise_StatementFragment.listData.get(7))) {
//            count = Const.COUNT_EIGHTH;
//        } else if (string.contains(Supevise_StatementFragment.listData.get(8))) {
//            count = Const.COUNT_NINTH;
//        }

//        return count;
    }

    public static String returnActivityH5Url(int itemCount, int countFirstOne) {
        String url = "";
        if (itemCount == Const.COUNT_FIRST || itemCount == Const.COUNT_SECOND || itemCount == Const.COUNT_THIRD) {
            if (countFirstOne == Const.COUNT_FIRST_ONE) {
                url = IHttpService.NETDEVICE_URL_UNIT;
            } else if (countFirstOne == Const.COUNT_FIRST_TWO) {
                url = IHttpService.NETDEVICE_POLICE_URL_UNIT;
            } else {
                url = IHttpService.NETDEVICE_BAD_URL_UNIT;
            }

        } else if (itemCount == Const.COUNT_FOURTH) {
            if (countFirstOne == Const.COUNT_FIRST_ONE) {
                url = IHttpService.FIREALARM_URL_UNIT;//公共消防
            } else {
                url = IHttpService.FIREALARM_BAD_URL_UNIT;
            }

        } else if (itemCount == Const.COUNT_FIFTH) {//统计分析
            if (countFirstOne == Const.COUNT_FIRST_ONE) {
                url = IHttpService.DEVICEORDER_URL_UNIT;
            } else if (countFirstOne == Const.COUNT_FIRST_TWO) {
                url = IHttpService.ATOUMATION_ANALYZE_URL_UNIT;
            } else {
                url = IHttpService.FIREALARM_ANALYZE_URL_UNIT;
            }

        } else if (itemCount == Const.COUNT_SIXTH) {//消防等级
            if (countFirstOne == Const.COUNT_FIRST_ONE) {
                url = IHttpService.DEVICEORDER_ANALYZE_URL_UNIT;
            } else if (countFirstOne == Const.COUNT_FIRST_TWO) {
                url = IHttpService.NET_YEAR_SAFETY_URL_UNIT;
            } else {
                url = IHttpService.NET_MOUTH_SAFETY_URL_UNIT;
            }

        } else if (itemCount == Const.COUNT_SEVENTH) {//行政公文
            if (countFirstOne == Const.COUNT_FIRST_ONE) {
                url = IHttpService.FIRE_DECUMENT_MANAGE_URL_UNIT;
            } else if (countFirstOne == Const.COUNT_FIRST_TWO) {
                url = IHttpService.WATCH_KEEPER_URL_UNIT;
            } else if (countFirstOne == Const.COUNT_FIRST_THREE) {
                url = IHttpService.FIRE_INSPECTION_URL_UNIT;
            } else {
                url = IHttpService.INSPECTION_RECORD_URL_UNIT;
            }
        } else if (itemCount == Const.COUNT_EIGHTH) {//消防评级
            if (countFirstOne == Const.COUNT_FIRST_ONE) {
                url = IHttpService.DEVICEORDER_ANALYZE_URL_UNIT;
            } else if (countFirstOne == Const.COUNT_FIRST_TWO) {
                url = IHttpService.NET_YEAR_SAFETY_URL_UNIT;
            } else if (countFirstOne == Const.COUNT_FIRST_THREE) {
                url = IHttpService.NET_MOUTH_SAFETY_URL_UNIT;
            } else {
                url = IHttpService.INSPECTION_RECORD_URL_UNIT;
            }
        }
        return url;
    }

    //    监管端url
    public static String returnStateNetUrlStr(String string) {
        String count = "";
        if (string.contains(Supevise_StatementFragment.listData.get(0))) {
//            count = Supevise_StatementFragment.listData.get(0);
            count = Const.GO_SETTINGONLINE_FIRST;
        } else if (string.contains(Supevise_StatementFragment.listData.get(1))) {
//            count = Supevise_StatementFragment.listData.get(1);
            count = Const.GO_SETTINGONLINE_SECOND;
        } else if (string.contains(Supevise_StatementFragment.listData.get(2))) {
//            count = Supevise_StatementFragment.listData.get(2);
            count = Const.GO_SETTINGONLINE_THIRD;

        } else if (string.contains(Supevise_StatementFragment.listData.get(3))) {
            count = Supevise_StatementFragment.listData.get(3);
        } else if (string.contains(Supevise_StatementFragment.listData.get(4))) {
            count = Supevise_StatementFragment.listData.get(4);
        } else if (string.contains(Supevise_StatementFragment.listData.get(5))) {
            count = Supevise_StatementFragment.listData.get(5);

        } else if (string.contains(Supevise_StatementFragment.listData.get(6))) {
            count = Supevise_StatementFragment.listData.get(6);
        } else if (string.contains(Supevise_StatementFragment.listData.get(7))) {
            count = Const.GO_NETDEVICE;
        } else if (string.contains(Supevise_StatementFragment.listData.get(9))) {
            count = Const.GO_MYSYSTEM;
        } else {
            count = Const.GO_SETTINGMANAGER;
        }
        return count;
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

    /*返回title name*/
    public static String returnActivityTitle(int itemCount, int countFirstOne, List<String> item_first_Name,
                                             List<String> item_fourth_Name, List<String> item_fifth_Name,
                                             List<String> item_sixth_Name, List<String> item_seventh_Name) {
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
            }

        } else if (itemCount == Const.COUNT_FIFTH) {
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
            } else if (countFirstOne == Const.COUNT_FIRST_THREE) {
                url = item_seventh_Name.get(1);
            } else {
                url = item_seventh_Name.get(1);
            }
        } else if (itemCount == Const.COUNT_EIGHTH) {
            if (countFirstOne == Const.COUNT_FIRST_ONE) {
                url = item_sixth_Name.get(0);
            } else if (countFirstOne == Const.COUNT_FIRST_TWO) {
                url = item_sixth_Name.get(1);
            } else if (countFirstOne == Const.COUNT_FIRST_THREE) {
                url = item_sixth_Name.get(1);
            } else {
                url = item_sixth_Name.get(1);
            }
        }
        return url;
    }
}
