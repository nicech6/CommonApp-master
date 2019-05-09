package com.project.wisdomfirecontrol.common.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by Administrator on 2017/12/12.
 */

public class UserManage {

    private static UserManage instance;

    private UserManage() {
    }

    public static UserManage getInstance() {
        if (instance == null) {
            synchronized (UserManage.class) {
                if (instance == null) {
                    instance = new UserManage();
                }
            }
        }
        return instance;
    }

    /**
     * 保存自动登录的用户信息
     */
    public void saveUserInfo(Context context, String username, String password) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("USER_NAME", username);
        editor.putString("PASSWORD", password);
        editor.commit();
    }

    /**
     * 获取用户信息model
     *
     * @param context
     * @param
     * @param
     */
    public UserInfo getUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(sp.getString("USER_NAME", ""));
        userInfo.setPassword(sp.getString("PASSWORD", ""));
        return userInfo;

    }

    public void saveUserIdInfo(Context context, String userid, String pid) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("USER_ID", userid);
        editor.putString("USER_PID", pid);
        editor.commit();
    }

    public UserInfo getUserIdInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserid(sp.getString("USER_ID", ""));
        userInfo.setPid(sp.getString("USER_PID", ""));
        return userInfo;

    }

    public void saveUserPhoneInfo(Context context, String phone) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("USER_PHONE", phone);
        editor.commit();
    }


    public UserInfo getUserPhoneInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        UserInfo userInfo = new UserInfo();
        userInfo.setTelNum(sp.getString("USER_PHONE", ""));
        return userInfo;

    }

    public void saveUserType(Context context, String phone) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("USER_TYPE", phone);
        editor.commit();
    }

    public UserInfo getUserType(Context context) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        UserInfo userInfo = new UserInfo();
        userInfo.setType(sp.getString("USER_TYPE", ""));
        return userInfo;

    }

    /**
     * userInfo中是否有数据
     */
    public boolean hasUserInfo(Context context) {
        UserInfo userInfo = getUserInfo(context);
        if (userInfo != null) {
            if ((!TextUtils.isEmpty(userInfo.getUserName())) && (!TextUtils.isEmpty(userInfo.getPassword()))) {//有数据
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
