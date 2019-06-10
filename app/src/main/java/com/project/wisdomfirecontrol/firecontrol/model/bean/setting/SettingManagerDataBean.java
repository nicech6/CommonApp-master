package com.project.wisdomfirecontrol.firecontrol.model.bean.setting;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/21.
 */

public class SettingManagerDataBean implements Serializable {

    /**
     * address : 广东省广州市天河区新塘街道新塘南约村口大街30号
     * areaid : 35129549ffffffc925a4ad12da4ad1e7
     * areaname : 测试点
     * city : 广州市
     * creattime : 2018-07-11 17:53:45.0
     * detailaddress :
     * district : 天河区
     * isc : true
     * lat : 23.158798
     * lng : 113.414168
     * obd : {"ae":"","av":"","be":"","bv":"","ce":"","cv":"","frtemp":"","louele":"","otemp":"","thtemp":"","totemp":""}
     * personal : 新增
     * personid : 0
     * province : 广东省
     * sensorid : 88c2cf490a00001f254d88ebd2bfa999
     * setposition : 测试设备
     * state :
     * street :
     * tel : 599
     * terminalno : 123
     * type : 消防栓
     */

    private String sensorid;//设备id
    private String areaid;
    private String areaname;//监控区名
    private String setposition;//安装位置
    private String type;
    private String terminalno;
    private String address;//监控地址
    private String detailaddress;
    private String personal;
    private String personid;
    private String tel;
    private String lat;
    private String lng;
    private String creattime;//安装时间
    private String province;//省
    private String city;//市
    private String district;//区
    private String street;//街道
    private String isc;
    private String state;

    private String personId;
    private String manager;
    private String managerPhone;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    private ObdBean obd;

    public ObdBean getObd() {
        return obd;
    }

    public void setObd(ObdBean obd) {
        this.obd = obd;
    }

    public String getSensorid() {
        return sensorid;
    }

    public void setSensorid(String sensorid) {
        this.sensorid = sensorid;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getSetposition() {
        return setposition;
    }

    public void setSetposition(String setposition) {
        this.setposition = setposition;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTerminalno() {
        return terminalno;
    }

    public void setTerminalno(String terminalno) {
        this.terminalno = terminalno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailaddress() {
        return detailaddress;
    }

    public void setDetailaddress(String detailaddress) {
        this.detailaddress = detailaddress;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getCreattime() {
        return creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public String getIsc() {
        return isc;
    }

    public void setIsc(String isc) {
        this.isc = isc;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
