package com.mvp_0726.project_0726.home.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fxd on 01/01/2017.
 */

public class MarqueeBean implements Serializable{

    /**
     * success : true
     * msgTitle : 成功提示
     * msg : 操作成功!
     * data : [{"address":"广东省深圳市龙岗区横岗街道龙岗大道大运软件小镇","areaBoss":"-","createTime":"2018-08-09 15:08:08.0","id":"1d83999c0a0a002328bd01a03b00da9a","lat":"22.685202","lng":"114.228163","monitorAreaid":"18d35fa50a0a002326f0ab607bcca683","monitoringArea":"11","monitoringAreaAddress":"","name":"","pid":"","sensorPosition":"测试设备-4","simNO":"","state":"2","terminalNO":"ceshi4","type":"电气火灾"},{"address":"广东省深圳市龙华区银星科技大厦银星科技工业园","areaBoss":"管理员-admin","createTime":"2018-08-09 15:07:53.0","id":"1d835fff0a0a002328bd01a0bb804ba2","lat":"22.729280","lng":"114.047476","monitorAreaid":"d9d966950a0a002340500969df9e35c0","monitoringArea":"B","monitoringAreaAddress":"","name":"","pid":"","sensorPosition":"测试设备-3","simNO":"","state":"2","terminalNO":"ceshi3","type":"电气火灾"},{"address":"广东省深圳市龙华区银星科技大厦银星科技工业园","areaBoss":"管理员-admin","createTime":"2018-08-09 15:07:34.0","id":"1d8313750a0a002328bd01a05cab9d6a","lat":"22.729280","lng":"114.047476","monitorAreaid":"d9d966950a0a002340500969df9e35c0","monitoringArea":"B","monitoringAreaAddress":"","name":"","pid":"","sensorPosition":"测试设备-2","simNO":"","state":"2","terminalNO":"ceshi2","type":"电气火灾"},{"address":"广东省深圳市龙岗区横岗街道龙岗大道大运软件小镇","areaBoss":"33-13302437937","createTime":"2018-08-09 15:07:16.0","id":"1d82ce8e0a0a002328bd01a062735f85","lat":"22.685202","lng":"114.228163","monitorAreaid":"d9d93c5d0a0a002340500969d13d7a16","monitoringArea":"A","monitoringAreaAddress":"","name":"","pid":"","sensorPosition":"测试设备-1","simNO":"","state":"2","terminalNO":"ceshi1","type":"电气火灾"},{"address":"广东省深圳市龙岗区横岗街道龙岗大道大运软件小镇","areaBoss":"-","createTime":"2018-07-23 12:27:06.0","id":"c5640ffcffffffc92285bbaf495cc255","lat":"22.685202","lng":"114.228163","monitorAreaid":"18d35fa50a0a002326f0ab607bcca683","monitoringArea":"11","monitoringAreaAddress":"","name":"","pid":"","sensorPosition":"93939393","simNO":"","state":"2","terminalNO":"93939393","type":"烟感器"}]
     */

    private boolean success;
    private String msgTitle;
    private String msg;
    private List<MarqueeDataBean> data;

    private MarqueeBean(){}

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<MarqueeDataBean> getData() {
        return data;
    }

    public void setData(List<MarqueeDataBean> data) {
        this.data = data;
    }
}
