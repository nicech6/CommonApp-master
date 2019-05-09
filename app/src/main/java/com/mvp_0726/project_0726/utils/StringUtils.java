package com.mvp_0726.project_0726.utils;

import android.content.Context;
import android.text.TextUtils;

import com.mvp_0726.project_0726.constant.Constant;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.base.UserInfo;
import com.project.wisdomfirecontrol.common.base.UserManage;

/**
 * Created by Administrator on 2018/8/10.
 */

public class StringUtils {

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

    /*监管*/
    public static String getItemNameSuper(String name) {
        String itemName = "";
        if (name.equals(Constant.GONGGONGZIYUAN)) {//公共资源
            itemName = Constant.GONGGONGZIYUAN;
        } else if (name.equals(Constant.TONGJIFENXI)) {//统计分析
            itemName = Constant.TONGJIFENXI;
        } else if (name.equals(Constant.XIAOFANGPINGJI)) {//消防评级
            itemName = Constant.XIAOFANGPINGJI;
        } else if (name.equals(Constant.HISTORY_RECOIDING)) {//历史记录
            itemName = Constant.HISTORY_RECOIDING;
        } else if (name.equals(Constant.SAFE_DENGJI)) {//安全等级
            itemName = Constant.SAFE_DENGJI;
        } else if (name.equals(Constant.SAFE_PERSONAL)) {//安全人员
            itemName = Constant.SAFE_PERSONAL;
        } else if (name.equals(Constant.XINGZHENGGONGWEN)) {//行政公文
            itemName = Constant.XINGZHENGGONGWEN;
        } else if (name.equals(Constant.SECURITY_FIL)) {//安全档案
            itemName = Constant.SECURITY_FIL;
        } else if (name.equals(Constant.SECURITY_ZHENGGAI)) {//消防整改
            itemName = Constant.SECURITY_ZHENGGAI;
        } else if (name.equals(Constant.ORGANSMANAGE)) {//机构管理
            itemName = Constant.ORGANSMANAGE;
        } else if (name.equals(Constant.AREAMANAGE)) {//区域管理
            itemName = Constant.AREAMANAGE;
        } else {
            itemName = "正在开发...";
        }

        return itemName;
    }

    /*空字符串返回无*/
    public static String isEntryStrWu(String string) {
        return TextUtils.isEmpty(string) ? "无" : string;
    }

    /*空字符串返回斜杠*/
    public static String isEntryStrXieg(String string) {
        return TextUtils.isEmpty(string) ? "---" : string;
    }

    /*空字符串返回空*/
    public static String isEntryStrNull(String string) {
        return TextUtils.isEmpty(string) ? "" : string;
    }

    /*空字符串返回0*/
    public static String isEntryStrZero(String string) {
        return TextUtils.isEmpty(string) ? "0" : string;
    }

    /*去掉时间末尾 .0*/
    public static String returnStrTime(String string) {
        if (TextUtils.isEmpty(string)) {
            return isEntryStrXieg(string);
        }
        String[] strings = string.split("\\.");
        return strings[0];
    }

//    public static void callPhone(Context context, String phone) {
//        if (PhonePwdCheckUtils.isMobileNumber(phone)) {
//            if (!TextUtils.isEmpty(phone)) {
//                Uri uri = Uri.parse("tel:" + phone);
//                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
//                context.startActivity(intent);
//            }
//        } else {
//            ToastUtils.showToast("该手机号异常");
//        }
//    }
}
