package com.project.wisdomfirecontrol.firecontrol.model.protocol;

/**
 * @author LMX
 */

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Retrofit接口
 */
public interface IHttpService {

    String VIDEO_IP = "120.78.217.251";
    String HOST_IP = "120.78.217.251/zgbd_fireControl/";
    //        String HOST_IP = "www.zgjiuan.cn/";//邓超
    //        String HOST_IP = "10.0.0.65:8080";//小古
    String BASE_URL = "http://" + HOST_IP;
    String BASE_URL_IP = "http://www.zgjiuan.cn/zgbd_fireControl/";
    String HOST_URL = BASE_URL + "h5/";
    String HOST_URL_Z = "http://www.zgjiuan.cn/h5/";
    String HOST_URL_CHANGE = BASE_URL_IP + "h5/";
    /*企业端*/
    String HOME_URL = BASE_URL_IP + "H5/fire/";
    //1.2联网设备
    String NETDEVICE_URL = HOME_URL + "fire1.html";
    // 1.3消防报警
    String FIREALARM_URL = HOME_URL + "fire2.html";
    // 1.4设备故障
    String DEVICEORDER_URL = HOME_URL + "fire3.html";

    //2、 统计分析  2.1消防监控
    String ATOUMATION_ANALYZE_URL = HOME_URL + "fire4.html";
    // 2.2消防报警
    String FIREALARM_ANALYZE_URL = HOME_URL + "fire6.html";
    // 2.3设备故障
    String DEVICEORDER_ANALYZE_URL = HOME_URL + "fire5.html";

    // 3、安全等级 3.1联网单位年度
    String NET_YEAR_SAFETY_URL = HOME_URL + "fire7.html";
    // 3.2联网单位月度
    String NET_MOUTH_SAFETY_URL = HOME_URL + "fire8.html";
    // 3.3消防资质文件管理
    String FIRE_DECUMENT_MANAGE_URL = HOME_URL + "fire9.html";

    //4、消防巡检  4.1值班人员
    String WATCH_KEEPER_URL = HOME_URL + "fire10.html";
    // 4.2消防巡检
    String FIRE_INSPECTION_URL = HOME_URL + "fire11.html";
    //    // 4.2.1巡检打卡
    String CHECKING_CLOCK_URL = HOME_URL + "fire12.html";

    //    // 4.2.2隐患上报
//    String HIDDENTROUBLE_REPORT_URL = HOME_URL + "fire13.html";
    // 4.2.3巡检记录
    String INSPECTION_RECORD_URL = HOME_URL + "fire14.html";
    // 4.2.4隐患记录
    String HIDDENTROUBLE_RECORD_URL = HOME_URL + "fire15.html";

    // 5、行政公文  5.1消防整改公文
    String RECTIFICATION_DOCUMENT_URL = HOME_URL + "fire16.html";
    // 5.1.1整改公文正文
//    String RECTIFICATION_OFFICIAL_TEXT_URL = HOME_URL + "fire17.html";
    // 5.2消防通知公文
    String NOTIFICATION_DOCUMENT_URL = HOME_URL + "fire18.html";


    /*监管端* */

    String HOME_URL_UNIT = BASE_URL_IP + "H5/fireJG/";
    String HOME_URL_JSPS_SS = BASE_URL_IP + "jsps/satingStandard/";
    String HOME_URL_JSPS_NOTICE = BASE_URL_IP + "jsps/notice/";

    //1.1联网单位界面
    String NETDEVICE_URL_UNIT = HOME_URL_UNIT + "fireJG1.html";
    String NETDEVICE_POLICE_URL_UNIT = HOME_URL_UNIT + "fireJG1Police.html";
    String NETDEVICE_BAD_URL_UNIT = HOME_URL_UNIT + "fireJG1Bad.html";
    // 2.2联网公共消防资源
    String FIREALARM_URL_UNIT = HOME_URL_UNIT + "fireJG2.html";
    String FIREALARM_BAD_URL_UNIT = HOME_URL_UNIT + "fireJG2Bad.html";

    // 统计分析
    // 1联网单位页面
    String DEVICEORDER_URL_UNIT = HOME_URL_UNIT + "fireJG3.html";

    // 2消防报警页面
    String ATOUMATION_ANALYZE_URL_UNIT = HOME_URL_UNIT + "fireJG4.html";
    // 3设备故障页面
    String FIREALARM_ANALYZE_URL_UNIT = HOME_URL_UNIT + "fireJG5.html";

    //    4、消防等级
    // 1年度消防等级
    String DEVICEORDER_ANALYZE_URL_UNIT = HOME_URL_JSPS_SS + "kp2.jsp";
    // 2、月度消防等级
    String NET_YEAR_SAFETY_URL_UNIT = HOME_URL_JSPS_SS + "kp4.jsp";
    // 3月度消防等级趋势
    String NET_MOUTH_SAFETY_URL_UNIT = HOME_URL_JSPS_SS + "kp3.jsp";

    //5、行政公文
    // 1通知公文
//    String FIRE_DECUMENT_MANAGE_URL_UNIT = HOME_URL_UNIT + "fireJG6.html";
    String BASEH5URL = "http://www.zgjiuan.cn/zgbd_fireControl/jsps/application/notice/";

    String FIRE_DECUMENT_MANAGE_URL_UNIT = BASEH5URL + "notice-grid.jsp";
    //2发送整改公文
//    String WATCH_KEEPER_URL_UNIT = HOME_URL_UNIT + "fireJG7.html";
    String WATCH_KEEPER_URL_UNIT = BASEH5URL + "notice-grid.jsp";
    // 3通知发布
//    String FIRE_INSPECTION_URL_UNIT = HOME_URL_JSPS_NOTICE + "notice1H5.jsp";
    String FIRE_INSPECTION_URL_UNIT = BASEH5URL + "notice-push-grid.jsp";
    // 4整改发布
//    String INSPECTION_RECORD_URL_UNIT = HOME_URL_JSPS_NOTICE + "notice2H5.jsp";
    String INSPECTION_RECORD_URL_UNIT = BASEH5URL + "notice-push-grid.jsp";

    String INSPECTION_VIDEO_URL_UNIT = HOME_URL_UNIT + "fireJG8.html";

    /*公文整改*/
    //    工单发布
    String URL_H5_PUBLISH_ORDER = BASE_URL_IP + "jsps/application/maintenance/maintenance-grid.jsp";
    //    我的工单
    String URL_H5_MY_ORDER = BASE_URL_IP + "jsps/application/maintenance/maintenance-mine.jsp";

    /*安全档案*/
    /*我的档案*/
    String URL_H5_MY_GRID = BASE_URL_IP + "jsps/application/aptitude/aptitude-grid.jsp";
    //审核档案
    String URL_H5_MY_AUDIT = BASE_URL_IP + "jsps/application/aptitude/aptitude-audit.jsp";

    /*机构管理*/
    String URL_H5_ORGANSMANAGE = "http://www.zgjiuan.cn/jsps/application/system/organ/organ-list.jsp";
    /*区域管理*/
    String URL_H5_AREAMANAGE = "http://www.zgjiuan.cn/jsps/system/areaList.jsp";


    //    0: 账号密码登录
//    1：手机号码登录
//    2: 第三方应用登录
    int TYPE_LOGIN_NORMAL = 0;
    int TYPE_LOGIN_PHONE = 1;
    int TYPE_LOGIN_THIRD_PART = 2;

    int TYPE_HOME = 0;
    int TYPE_SHOP_LIST = 1;
    int TYPE_SHOP_CATEGORY = 2;
    int TYPE_ORDER_BY = 3;
    int TYPE_SHOP_DETAIL = 4;
    int TYPE_LOGIN = 4;

    int TYPE_DAKA = 1;
    int TYPE_TROUBLE = 2;

    int TYPE_GETDOCUMENT = 3;
    int TYPE_IMG_NUM = 4;

    int TYPE_IMG_SUBMIT_NUM = 5;

    int TYPE_TROUBLE_TYPE_NUM = 6;
    int TYPE_SETTINGMANAGER = 7;//设备信息
    int TYPE_DELETESENSOR = 8;//删除设备
    int TYPE_GETAREAPERSON = 9;//获取消防负责人
    int TYPE_GETORGANDETAILBYID = 10;//获取公司信息
    int TYPE_GETOBD = 11;//获取obd信息

    String XIAQU_UNIT = "辖区单位";
    String ONLINE_UNIT = "联网单位";
    String TONGJIFENXI = "统计分析";
    String GONGGONGZIYUAN = "公共资源";
    String XIAOFANGPINGJI = "消防评级";
    String FIRSTPAGE_UNIT = "单位首页";
    String SAFE_PERSONAL = "安全人员";
    String HISTORY_RULE = "历史记录";
    String SAFE_DENGJI = "安全等级";
    String XINGZHENGGONGWEN = "行政公文";

    String VEDIO_LOOKING = "视频监控";
    String SETTINGMANAGE = "设备管理";
    String SYSTEMMAIN = "系统维护";


    /*1.2 版本更改 修改  统计分析 ，历史记录*/
    String TONGJIFENFX = BASE_URL_IP + "jsps/analysis/organ-history.jsp";
    String HOSTORY_RECORD = BASE_URL_IP + "jsps/analysis/sensor-history.jsp";


    @GET("baobiao.html")
    Call<JsonObject> getHomeData();

    /*登录*/
    @POST("login.action")
    Call<JsonObject> login(
            @Query("username") String phone,
            @Query("password") String pwd);


    /*打卡*/
    @FormUrlEncoded
    @POST("daka.action")
    Call<JsonObject> daka(
            @Field("userid") String userid,
            @Field("latitude") String Latitude,
            @Field("longitude") String Longitude,
            @Field("address") String address,
            @Field("memo") String memo,
            @Field("pid") String pid,
            @Field("imageurl") String imageurl);


    /*隐患上报*/
    @FormUrlEncoded
    @POST("yinhuan.action")
    Call<JsonObject> troubleReport(
            @Field("name") String name,
            @Field("img") String img,
            @Field("video") String video,
            @Field("pid") String pid,
            @Field("memo") String memo,
            @Field("terminalNO") String terminalNO,
            @Field("voice") String voice);

    /*
     * 获取正文
     * */
    @POST("zhenggai.action")
    Call<JsonObject> getDocument(
            @Query("pid") String pid,
            @Query("limit") String name,
            @Query("page") String memo);

    /*整改公文*/
    @POST("savegw.action")
    Call<JsonObject> rectificationTitle(
            @Query("id") String gwid,
            @Query("aduituser") String userid,
            @Query("replyContent") String title_des,
            @Query("img") String imageurl);

    /*获取图片数量*/
    @POST("getImgNum.action")
    Call<JsonObject> getImgNum(
            @Query("pid") String pid);

    /*获取历史文本*/
    @POST("getOrganImg.action")
    Call<JsonObject> getOrganImg(
            @Query("pid") String pid);

    /*提交资质文件*/
    @FormUrlEncoded
    @POST("saveOrganImg.action")
    Call<JsonObject> saveOrganImg(
            @Field("id") String userid,
            @Field("pid") String pid,
            @Field("newimg") String imageurl);

    /*删除图片*/
    @POST("deleteOrganImg.action")
    Call<JsonObject> deleteOrganImg(
            @Query("pid") String pid,
            @Query("img") String imageurl,
            @Query("isAnd") String isAnd);

    /*获取隐患类型*/
    @FormUrlEncoded
    @POST("getSensorList.action")
    Call<JsonObject> getSensorList(
            @Query("pid") String pid);

    /*获取首页item 条数*/
    @FormUrlEncoded
    @POST("getEquipmentCount.action")
    Call<JsonObject> getequipmentcount(
            @Query("pid") String pid);

    /*getvideoequipment 视频列表*/
    @FormUrlEncoded
    @POST("getvideoequipment.action")
    Call<JsonObject> getvideoequipment(
            @Field("pid") String pid,
            @Field("type") String type);

    /*getvideoequipment 条数*/
    @FormUrlEncoded
    @POST("userregister.action")
    Call<JsonObject> userregister(
            @Field("province") String province,
            @Field("city") String city,
            @Field("district") String district,
            @Field("township") String township,
            @Field("address") String address,
            @Field("orgName") String orgName,
            @Field("orgShortName") String orgShortName,
            @Field("legalperson") String legalperson,
            @Field("principal") String principal,
            @Field("principalTel") String principalTel,
            @Field("name1") String name1,
            @Field("phone1") String phone1,
            @Field("name2") String name2,
            @Field("phone2") String phone2,
            @Field("name3") String name3,
            @Field("phone3") String phone3,
            @Field("name4") String name4,
            @Field("phone4") String phone4,
            @Field("area") String area,
            @Field("renshu") String renshu,
            @Field("imageArrays") String imageArrays,
            @Field("tel") String tel,
            @Field("password") String password,
            @Field("lat") String lat,
            @Field("lng") String lng);

    /*设备管理*/
    @POST("getsensor.action")
    Call<JsonObject> getsensor(
            @Query("pid") String pid);

    /*用户端设备管理*/
    @POST("sensorQY/listByPerson.action")
    Call<JsonObject> getClientsensor(
            @Query("personId") String personId
            , @Query("type") String type
    );

    /*删除设备管理*/
    @POST("deletesensor.action")
    Call<JsonObject> deletesensor(
            @Query("id") String id);

    /*删除区域*/
    @POST("deletearea.action")
    Call<JsonObject> deletearea(
            @Query("id") String id);

    /*获取消防负责人*/
    @POST("getareaperson.action")
    Call<JsonObject> getareaperson(
            @Query("pid") String pid);

    /*保存修改设备信息接口*/
    @POST("savesensor.action")
    Call<JsonObject> savesensor(
            @Query("id") String id,
            @Query("pid") String pid,
            @Query("type") String type,
            @Query("createTime") String createTime,
            @Query("terminalNO") String terminalNO,
            @Query("sensorPosition") String sensorPosition,
            @Query("monitorAreaid") String monitorAreaid,
            @Query("personId") String personId,
            @Query("manager") String manager,
            @Query("managerPhone") String managerPhone);


    /*获取监控区域服务接口*/
    @POST("getmonitorarea.action")
    Call<JsonObject> getmonitorarea(
            @Query("pid") String pid);

    /*获取公司注册信息接口*/
    @POST("getOrgandetailbyid.action")
    Call<JsonObject> getorgandetailbyid(
            @Query("id") String pid);

    /*新增修改监控区服务接口*/
    @POST("savemonitorarea.action")
    Call<JsonObject> savemonitorarea(
            @Query("id") String id,
            @Query("areaname") String areaname,
            @Query("personal") String personal,
            @Query("tel") String tel,
            @Query("address") String address,
            @Query("pid") String pid,
            @Query("isc") String isc,
            @Query("detailaddress") String detailaddress,
            @Query("lng") String lng,
            @Query("lat") String lat,
            @Query("province") String province,
            @Query("city") String city,
            @Query("district") String district,
            @Query("street") String street,
            @Query("personalid") String personalid);

    /*获取机构下的设备信息和监控区域信息服务接口*/
    @POST("getsensor.action")
    Call<JsonObject> getsensor(
            @Query("pid") String pid,
            @Query("type") String type,
            @Query("state") String state,
            @Query("ishave") String ishave);

    /*获取机构下的设备信息和监控区域信息服务接口*/
    @POST("getsenorcount.action")
    Call<JsonObject> getsenorcount(
            @Query("pid") String pid,
            @Query("state") String state);

    /*获取机构下的设备信息和监控区域信息服务接口*/
    @POST("UpdateSensor.action")
    Call<JsonObject> UpdateSensor(
            @Query("id") String id);

    /*获取OBD*/
    @POST("getsensorobd.action")
    Call<JsonObject> getsensorObd(
            @Query("terminalNo") String terminalNo);
}
