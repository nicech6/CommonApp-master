package com.mvp_0726.project_0726.login.modle;

/**
 * Created by Administrator on 2018/8/14.
 */

public class ChangePwdBean {

    /**
     * success : false
     * msgTitle : 失败提示
     * msg : 操作失败!
     * data : 账号不存在！
     */

    private boolean success;
    private String msgTitle;
    private String msg;
    private String data;

    private ChangePwdBean(){}

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
