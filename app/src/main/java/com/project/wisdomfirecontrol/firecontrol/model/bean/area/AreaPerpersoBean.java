package com.project.wisdomfirecontrol.firecontrol.model.bean.area;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/21.
 */

public class AreaPerpersoBean implements Serializable {


    /**
     * success : true
     * msgTitle : 成功提示
     * msg : 操作成功!
     * data : [{"id":"6a3edb800a0000637c4391346fdef973","name":"YY","telNum":"yuefeng","position":"","pid":"yun","createTime":"2018-06-15 17:07:56.0","orderNumber":"0","password":"123456","isadmin":"0"},{"id":"0","name":"新增","telNum":""}]
     */

    private boolean success;
    private String msgTitle;
    private String msg;
    private List<AreaPerpersoDataBean> data;

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

    public List<AreaPerpersoDataBean> getData() {
        return data;
    }

    public void setData(List<AreaPerpersoDataBean> data) {
        this.data = data;
    }

}
