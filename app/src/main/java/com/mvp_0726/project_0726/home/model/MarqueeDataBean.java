package com.mvp_0726.project_0726.home.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/10.
 */

public class MarqueeDataBean implements Serializable{
    /**
     * address : 广东省深圳市龙岗区横岗街道龙岗大道大运软件小镇
     * areaBoss : -
     * createTime : 2018-08-09 15:08:08.0
     * id : 1d83999c0a0a002328bd01a03b00da9a
     * lat : 22.685202
     * lng : 114.228163
     * monitorAreaid : 18d35fa50a0a002326f0ab607bcca683
     * monitoringArea : 11
     * monitoringAreaAddress :
     * name :
     * pid :
     * sensorPosition : 测试设备-4
     * simNO :
     * state : 2
     * terminalNO : ceshi4
     * type : 电气火灾
     */

    private String address;
    private String areaBoss;
    private String createTime;
    private String id;
    private String lat;
    private String lng;
    private String monitorAreaid;
    private String monitoringArea;
    private String monitoringAreaAddress;
    private String name;
    private String pid;
    private String sensorPosition;
    private String simNO;
    private String state;
    private String terminalNO;
    private String type;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAreaBoss() {
        return areaBoss;
    }

    public void setAreaBoss(String areaBoss) {
        this.areaBoss = areaBoss;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getMonitorAreaid() {
        return monitorAreaid;
    }

    public void setMonitorAreaid(String monitorAreaid) {
        this.monitorAreaid = monitorAreaid;
    }

    public String getMonitoringArea() {
        return monitoringArea;
    }

    public void setMonitoringArea(String monitoringArea) {
        this.monitoringArea = monitoringArea;
    }

    public String getMonitoringAreaAddress() {
        return monitoringAreaAddress;
    }

    public void setMonitoringAreaAddress(String monitoringAreaAddress) {
        this.monitoringAreaAddress = monitoringAreaAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSensorPosition() {
        return sensorPosition;
    }

    public void setSensorPosition(String sensorPosition) {
        this.sensorPosition = sensorPosition;
    }

    public String getSimNO() {
        return simNO;
    }

    public void setSimNO(String simNO) {
        this.simNO = simNO;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTerminalNO() {
        return terminalNO;
    }

    public void setTerminalNO(String terminalNO) {
        this.terminalNO = terminalNO;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
