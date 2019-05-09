package com.project.wisdomfirecontrol.firecontrol.model.bean.other;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/27.
 */

public class TroubleTypesBean implements Serializable {

    /**
     * id : 61330c51ffffffc919212109d2fea82c
     * pid : c5073eab0a00000518c6c9b9c959a415
     * type : 1
     * createTime : 2018-03-26 15:25:24.0
     * lng : 113.29854889648459
     * lat : 23.236745132251194
     * address : 广东省广州市白云区永平街道荟贤路
     * name : 测试
     * terminalNO : 110000
     * state : 2
     */

    private String id;
    private String pid;
    private String type;
    private String createTime;
    private String lng;
    private String lat;
    private String address;
    private String name;
    private String terminalNO;
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTerminalNO() {
        return terminalNO;
    }

    public void setTerminalNO(String terminalNO) {
        this.terminalNO = terminalNO;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
