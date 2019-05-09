package com.mvp_0726.common.utils;

/**
 * Created by seven
 * on 2018/5/17
 * email:seven2016s@163.com
 * eventbus的what记录以及一些常量的配置
 */

public class Constans {
    /*
     * 网络监听
     * */
    public static final int NET_WORK_AVAILABLE = 0;//有网络
    public static final int NET_WORK_DISABLED = 1;//没网络


    public static final int COMMON = 5;//公共
    public static final int COUNT_THREE = 3;

    public static final String COUNT_ZERO = "0";
    /*
     * home模块 100~200
     * */
    public static final int HOMEDATA = 101;//获取首页数据成功
    public static final int HOMEBANNER = 102;//获取首页banner
    public static final int HOMEERROR = 100;//首页请求数据失败
    public static final int HOMEDASUCCESS = 103;//banner,homedata请求数据成功，取消loading

    public static final String COOKIE_PREF = "cookie_pref";//cookie保存
    /*
     * 登录注册用户相关10-20
     * */
    public static final int REGISTER = 10;//注册成功
    public static final int LOGIN = 11;//登录成功
    public static final int RELOGIN = 13;//重新登录
    public static final int USERERROR = 12;//用户相关错误
    public static final int USERERRORS = 14;//注册相关错误
    public static final int CHANPWD = 15;//注册相关错误
    public static final String USERNAME = "user_name";//存储用户名
    public static final String USERPASSWORD = "user_pwd";//存储用户名密码
    public static final int ERROR = 40;//失败
    public static final int WEBSUCESS = 30;//

    /*
     * 首页 40-50
     * */
    public static final int EQUIPMENTCOUNTSUCESS = 41;//获取报警数量成功
    public static final int MARQUEESUCESS = 42;//获取报警数量失败
    public static final int MARQUEEERROR = 45;//获取报警数量失败
    public static final int COMPANYINFOSSUCESS = 43;//获取公司信息成功
    public static final int WEBH5SUCESS = 44;//h5成功/*

    public static final int GETAPPNUMSUCESS = 446;//角标数据成功/*
    public static final int GETAPPNUMERROR = 447;//角标数据/

    /*安全档案界面角标*/
    public static final int GETSUMTYPENUMSUCESS = 448;//角标数据成功/*
    public static final int GETSUMTYPENUMERROR = 449;//角标数据/*

    /* 联网 报警 故障 50-70
     * */
    public static final int SETTINGSUCESS = 50;//获取成功
    public static final int SETTINGFIRSTSUCESS = 51;//获取联网成功
    public static final int SETTINGSECONDSUCESS = 52;//获取报警成功
    public static final int SETTINGTHIRDSUCESS = 53;//获取故障成功

    public static final int ALLSUCESS = 54;//获取全部成功
    public static final int DIANQISUCESS = 55;//获取电气火灾成功
    public static final int YANGANSUCESS = 56;//获取烟感成功
    public static final int QIGANSUCESS = 57;//获取气感成功
    public static final int XIAOFANGSUCESS = 58;//获取消防栓成功
    public static final int MIEHUOSUCESS = 59;//获取灭火器成功

    public static final int ALLERROR = 60;//失败
    public static final int DIANQIERROR = 61;//失败
    public static final int YANGANERROR = 62;//失败
    public static final int QIGANERROR = 63;//失败
    public static final int XIAOFANGERROR = 64;//失败
    public static final int MIEHUOERROR = 65;//失败

    /*巡查打卡*/
    public static final int PATROLOCKSUCCESS = 66;//成功
    public static final int PATROLOCKERROR = 67;

    //获取OBD数据
    public static final int OBD_SUCCESS = 68;//成功
    public static final int OBD_ERROR = 68;//失败

}
