package com.mvp_0726.project_0726.bean.settingpolice;

import java.io.Serializable;
import java.util.List;

public class GetsensorObdSuccessBean implements Serializable {


    /**
     * success : true
     * msgTitle : 成功提示
     * msg : 操作成功!
     * data : [{"dataType":"loc","obd":"{'Ae':'56.2A','Be':'33.3A','Ce':'44.6A','Av':'230.5V','Bv':'230.0V','Cv':'231.4V','Av2':'0.0V','Bv2':'0.0V','Cv2':'0.0V','louele':'0.0ma','otemp':'28.0℃','totemp':'27.9℃','thtemp':'28.1℃','frtemp':'28.1℃'}","obdParam":{"Ce":"44.6A","Be":"33.3A","Ae":"56.2A","thtemp":"28.1℃","totemp":"27.9℃","frtemp":"28.1℃","otemp":"28.0℃","louele":"0.0mA","Cv":"231.4V","Bv":"230.0V","Av":"230.5V","Av2":"0.0V","Cv2":"0.0V","Bv2":"0.0V"},"procTime":"无","procUserId":"无","procUserName":"无","saveTime":"2019-04-15 16:36:13.0","terminalNO":"1440022680301","type":""}]
     */

    private boolean success;
    private String msgTitle;
    private String msg;
    private List<GetsensorObdDataBean> data;

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

    public List<GetsensorObdDataBean> getData() {
        return data;
    }

    public void setData(List<GetsensorObdDataBean> data) {
        this.data = data;
    }
}
