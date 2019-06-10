package com.project.wisdomfirecontrol.firecontrol.model.protocol;

import com.mvp_0726.project_0726.bean.settingpolice.GetsensorObdSuccessBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.area.AreaPerpersoBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.document.DocumentBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.login.LoginChangeBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.EquipmentCount;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.GetCompanyRegisterInfosBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.GetSenorcountBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.GetmonitorAreaBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.ImgNumBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.SubmitBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.TroubleTypeBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.setting.SettingManagerBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.video.VideoEquipmentBean;

/**
 * 网络请求
 *
 * @author LMX
 */
public class CommonProtocol extends BaseProtocol {

    /*
     * 登录：使用电话号码登录
     */
    public void login(final OnHttpCallback callback, String phone, String pwd) {
        super.execute(super.getHttpService().login(phone, pwd),
                callback, IHttpService.TYPE_LOGIN, LoginChangeBean.class);
    }

    /**
     * 巡查打卡
     */
    public void daka(final OnHttpCallback callback, String userid, String Latitude,
                     String Longitude, String address, String memo,
                     String imageurl, String pid) {
        super.execute(super.getHttpService().daka(
                userid, Latitude, Longitude, address, memo, pid, imageurl),
                callback, IHttpService.TYPE_DAKA, SubmitBean.class);
    }

    /*
     * 隐患上报
     * */
    public void troubleReport(final OnHttpCallback callback, String name, String img,
                              String video, String pid, String memo, String terminalNO, String voice) {
        super.execute(super.getHttpService().troubleReport(
                name, img, video, pid, memo, terminalNO, voice),
                callback, IHttpService.TYPE_TROUBLE, SubmitBean.class);
    }

    /*
     * 获取正文
     * */
    public void getDocument(final OnHttpCallback callback, String pid,
                            String limit, String page) {
        super.execute(super.getHttpService().getDocument(pid, limit, page),
                callback, IHttpService.TYPE_GETDOCUMENT, DocumentBean.class);
    }

    /*整改公文*/
    public void rectificationTitle(final OnHttpCallback callback, String gwid,
                                   String userid, String title_des, String imageurl) {
        super.execute(super.getHttpService().rectificationTitle(gwid,
                userid, title_des, imageurl),
                callback, IHttpService.TYPE_GETDOCUMENT, SubmitBean.class);
    }

    /*获取图片数量*/
    public void getImgNum(final OnHttpCallback callback, String pid) {
        super.execute(super.getHttpService().getImgNum(pid),
                callback, IHttpService.TYPE_IMG_NUM, ImgNumBean.class);
    }

    /*获取历史文件*/
    public void getOrganImg(final OnHttpCallback callback, String pid) {
        super.execute(super.getHttpService().getOrganImg(pid),
                callback, IHttpService.TYPE_IMG_NUM, SubmitBean.class);
    }

    /*上传资质文件*/
    public void saveOrganImg(final OnHttpCallback callback, String userid,
                             String pid, String imageurl) {
        super.execute(super.getHttpService().saveOrganImg(userid, pid, imageurl),
                callback, IHttpService.TYPE_IMG_SUBMIT_NUM, SubmitBean.class);
    }

    /*图片删除*/
    public void deleteOrganImg(final OnHttpCallback callback, String pid, String imageurl, String isAnd) {
        super.execute(super.getHttpService().deleteOrganImg(pid, imageurl, isAnd),
                callback, IHttpService.TYPE_IMG_SUBMIT_NUM, SubmitBean.class);
    }

    public void getSensorList(final OnHttpCallback callback, String pid) {
        super.execute(super.getHttpService().getSensorList(pid),
                callback, IHttpService.TYPE_TROUBLE_TYPE_NUM, TroubleTypeBean.class);
    }


    public void getequipmentcount(final OnHttpCallback callback, String pid) {
        super.execute(super.getHttpService().getequipmentcount(pid),
                callback, IHttpService.TYPE_TROUBLE_TYPE_NUM, EquipmentCount.class);
    }

    public void getvideoequipment(final OnHttpCallback callback, String pid, String type) {
        super.execute(super.getHttpService().getvideoequipment(pid, type),
                callback, IHttpService.TYPE_TROUBLE_TYPE_NUM, VideoEquipmentBean.class);
    }

    /*注册*/
    public void userregister(final OnHttpCallback callback, String province, String city, String district,
                             String township, String address, String orgName, String orgShortName,
                             String legalperson, String principal, String principalTel, String name1,
                             String phone1, String name2, String phone2, String name3, String phone3,
                             String name4, String phone4, String area, String renshu, String imageArrays,
                             String tel, String password, String lat, String lng) {
        super.execute(super.getHttpService().userregister(province, city, district, township,
                address, orgName, orgShortName, legalperson, principal, principalTel, name1, phone1,
                name2, phone2, name3, phone3, name4, phone4, area, renshu, imageArrays, tel, password, lat, lng),
                callback, IHttpService.TYPE_TROUBLE_TYPE_NUM, SubmitBean.class);
    }

    /*获取设备信息的接口
     */
    public void getsensor(final OnHttpCallback callback, String pid) {
        super.execute(super.getHttpService().getsensor(pid),
                callback, IHttpService.TYPE_SETTINGMANAGER, SettingManagerBean.class);
    }

    /*获取设备信息的接口
     */
    public void getClientsensor(final OnHttpCallback callback, String perisonId) {
        super.execute(super.getHttpService().getClientsensor(perisonId, null),
                callback, IHttpService.TYPE_SETTINGMANAGER, SettingManagerBean.class);
    }

    /*删除已选设备接口
     */
    public void deletesensor(final OnHttpCallback callback, String id) {
        super.execute(super.getHttpService().deletesensor(id),
                callback, IHttpService.TYPE_DELETESENSOR, SubmitBean.class);
    }

    /*删除监控区域
     */
    public void deletearea(final OnHttpCallback callback, String id) {
        super.execute(super.getHttpService().deletearea(id),
                callback, IHttpService.TYPE_DELETESENSOR, SubmitBean.class);
    }

    /*获取消防负责人的接口
     */
    public void getareaperson(final OnHttpCallback callback, String pid) {
        super.execute(super.getHttpService().getareaperson(pid),
                callback, IHttpService.TYPE_GETAREAPERSON, AreaPerpersoBean.class);
    }

    /*保存修改设备信息接口*/
    public void savesensor(final OnHttpCallback callback, String id, String pid, String type,
                           String createTime, String terminalNO, String sensorPosition, String monitorAreaid, String personId, String manager, String managerPhone) {
        super.execute(super.getHttpService().savesensor(id, pid, type, createTime, terminalNO, sensorPosition, monitorAreaid, personId, manager, managerPhone),
                callback, IHttpService.TYPE_GETAREAPERSON, SubmitBean.class);
    }

    /*获取监控区域服务接口
     */
    public void getmonitorarea(final OnHttpCallback callback, String pid) {
        super.execute(super.getHttpService().getmonitorarea(pid),
                callback, IHttpService.TYPE_SETTINGMANAGER, GetmonitorAreaBean.class);
    }

    /*获取公司信息接口
     */
    public void getorgandetailbyid(final OnHttpCallback callback, String pid) {
        super.execute(super.getHttpService().getorgandetailbyid(pid),
                callback, IHttpService.TYPE_GETORGANDETAILBYID, GetCompanyRegisterInfosBean.class);
    }

    /*保存修改设备信息接口*/
    public void savemonitorarea(final OnHttpCallback callback, String id, String areaname, String personal,
                                String tel, String address, String pid, String isc, String detailaddress, String lng, String lat
            , String province, String city, String district, String street, String personalid) {
        super.execute(super.getHttpService().savemonitorarea(id, areaname, personal, tel, address, pid,
                isc, detailaddress, lng, lat, province, city, district, street, personalid),
                callback, IHttpService.TYPE_SETTINGMANAGER, SubmitBean.class);
    }

    /*获取机构下的设备信息和监控区域信息服务接口
     */
    public void getsensor(final OnHttpCallback callback, String pid, String type, String state, String ishave) {
        super.execute(super.getHttpService().getsensor(pid, type, state, ishave),
                callback, IHttpService.TYPE_SETTINGMANAGER, SettingManagerBean.class);
    }

    /*获取设备类型数量服务接口
     */
    public void getsenorcount(final OnHttpCallback callback, String pid, String state) {
        super.execute(super.getHttpService().getsenorcount(pid, state),
                callback, IHttpService.TYPE_SETTINGMANAGER, GetSenorcountBean.class);
    }

    /*获取设备类型数量服务接口
     */
    public void UpdateSensor(final OnHttpCallback callback, String id) {
        super.execute(super.getHttpService().UpdateSensor(id),
                callback, IHttpService.TYPE_SETTINGMANAGER, SubmitBean.class);
    }

    /*获取OBD接口
     */
    public void getsensorObd(final OnHttpCallback callback, String terminalNo) {
        super.execute(super.getHttpService().getsensorObd(terminalNo),
                callback, IHttpService.TYPE_GETOBD, GetsensorObdSuccessBean.class);
    }

}
