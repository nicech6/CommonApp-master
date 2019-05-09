package com.project.wisdomfirecontrol.firecontrol.model.bean.setting;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/21.
 */

public class SettingManagerBean implements Serializable {


    /**
     * success : true
     * msgTitle : 成功提示
     * msg : 操作成功!
     * data : [{"sensorid":"2145f443ffffffc96086df45eea9e658","areaid":"2145bb4effffffc96086df45bdcdbc11","areaname":"55","setposition":"55","type":"气感器","terminalno":"2222","address":"广东省佛山市南海区大沥镇国昌·新城市广场","detailaddress":"","personal":"5222","tel":"122","lat":"23.139999","lng":"113.201675"},{"sensorid":"210d12010a00004100384a0d8489555e","areaid":"21257b350a00004100384a0d457143bd","areaname":"测2","setposition":"一楼","type":"消防栓","terminalno":"12345","address":"广东省佛山市南海区大沥镇国昌·新城市广场","detailaddress":"11","personal":"1","tel":"1","lat":"23.139999","lng":"113.201675"},{"sensorid":"","areaid":"215205ad0a00004100384a0d4d10c9ee","areaname":"www","setposition":"","type":"","address":"广东省佛山市南海区大沥镇国昌·新城市广场","detailaddress":"123","creattime":"","personal":"我去","tel":"www","lat":"23.139999","lng":"113.201675"}]
     */

    private boolean success;
    private String msgTitle;
    private String msg;
    private List<SettingManagerDataBean> data;

    private SettingManagerBean(){}

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

    public List<SettingManagerDataBean> getData() {
        return data;
    }

    public void setData(List<SettingManagerDataBean> data) {
        this.data = data;
    }

}
