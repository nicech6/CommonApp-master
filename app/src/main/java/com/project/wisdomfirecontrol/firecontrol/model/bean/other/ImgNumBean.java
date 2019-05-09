package com.project.wisdomfirecontrol.firecontrol.model.bean.other;

/**
 * Created by Administrator on 2018/3/23.
 */

public class ImgNumBean {

    /**
     * success : true
     * msgTitle : 成功提示
     * msg : 操作成功!
     * data : 0
     */

    private boolean success;
    private String msgTitle;
    private String msg;
    private int data;

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

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
