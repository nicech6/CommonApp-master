package com.project.wisdomfirecontrol.firecontrol.model.bean.other;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/3/27.
 */

public class TroubleTypeBean implements Serializable{


    /*
     * success : true
     * msgTitle : 成功提示
     * msg : 操作成功!
     */

    private boolean success;
    private String msgTitle;
    private String msg;
    private List<TroubleTypesBean> data;

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

    public List<TroubleTypesBean> getData() {
        return data;
    }

    public void setData(List<TroubleTypesBean> data) {
        this.data = data;
    }

}
