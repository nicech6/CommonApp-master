package com.project.wisdomfirecontrol.firecontrol.model.bean.other;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/22.
 */

public class GetmonitorAreaBean implements Serializable {


    /**
     * success : true
     * msgTitle : 成功提示
     * msg : 操作成功!
     * data : [{"id":"21257b350a00004100384a0d457143bd","areaname":"测2","personal":"1","tel":"1","address":"广东省佛山市南海区大沥镇国昌·新城市广场","pid":"yun","isc":"true","detailaddress":"11","lng":"113.201675","lat":"23.139999","province":"广东省","city":"广州市","district":"东山区","street":"","personalid":"0"},{"id":"2145bb4effffffc96086df45bdcdbc11","areaname":"55","personal":"5222","tel":"122","address":"广东省佛山市南海区大沥镇国昌·新城市广场","pid":"yun","isc":"true","detailaddress":"","lng":"113.201675","lat":"23.139999","province":"请选择省份","city":"请选择城市","district":"请选择区县","street":"","personalid":"0"},{"id":"2575c828ffffffc95d16b0844019204a","areaname":"444","personal":"4564","tel":"4","address":"广东省佛山市南海区大沥镇国昌·新城市广场","pid":"yun","isc":"true","detailaddress":"","lng":"113.201675","lat":"23.139999","province":"请选择省份","city":"请选择城市","district":"请选择区县","street":"","personalid":"0"},{"id":"262a7d14ffffffc959c068317e2cab04","areaname":"5","personal":"4","tel":"4","address":"广东省佛山市南海区大沥镇国昌·新城市广场","pid":"yun","isc":"true","detailaddress":"","lng":"113.201675","lat":"23.139999","province":"请选择省份","city":"请选择城市","district":"请选择区县","street":"","personalid":"0"}]
     */

    private boolean success;
    private String msgTitle;
    private String msg;
    private List<GetmonitorAreaDataBean> data;

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

    public List<GetmonitorAreaDataBean> getData() {
        return data;
    }

    public void setData(List<GetmonitorAreaDataBean> data) {
        this.data = data;
    }

}
