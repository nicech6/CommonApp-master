package com.project.wisdomfirecontrol.firecontrol.model.bean.other;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/26.
 */

public class GetSenorcountBean implements Serializable {


    /**
     * success : true
     * msgTitle : 成功提示
     * msg : 操作成功!
     * data : [{"typename":"全部","num":0}]
     */

    private boolean success;
    private String msgTitle;
    private String msg;
    private List<GetSenorcountDataBean> data;

    private GetSenorcountBean(){}

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

    public List<GetSenorcountDataBean> getData() {
        return data;
    }

    public void setData(List<GetSenorcountDataBean> data) {
        this.data = data;
    }

}
