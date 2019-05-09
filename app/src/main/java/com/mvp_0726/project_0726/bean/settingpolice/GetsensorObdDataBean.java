package com.mvp_0726.project_0726.bean.settingpolice;

import java.io.Serializable;

public class GetsensorObdDataBean implements Serializable {
    /**
     * dataType : loc
     * obd : {'Ae':'56.2A','Be':'33.3A','Ce':'44.6A','Av':'230.5V','Bv':'230.0V','Cv':'231.4V','Av2':'0.0V','Bv2':'0.0V','Cv2':'0.0V','louele':'0.0ma','otemp':'28.0℃','totemp':'27.9℃','thtemp':'28.1℃','frtemp':'28.1℃'}
     * obdParam : {"Ce":"44.6A","Be":"33.3A","Ae":"56.2A","thtemp":"28.1℃","totemp":"27.9℃","frtemp":"28.1℃","otemp":"28.0℃","louele":"0.0mA","Cv":"231.4V","Bv":"230.0V","Av":"230.5V","Av2":"0.0V","Cv2":"0.0V","Bv2":"0.0V"}
     * procTime : 无
     * procUserId : 无
     * procUserName : 无
     * saveTime : 2019-04-15 16:36:13.0
     * terminalNO : 1440022680301
     * type :
     */

    private String dataType;
    private String obd;
    private ObdParamBean obdParam;
    private String procTime;
    private String procUserId;
    private String procUserName;
    private String saveTime;
    private String terminalNO;
    private String type;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getObd() {
        return obd;
    }

    public void setObd(String obd) {
        this.obd = obd;
    }

    public ObdParamBean getObdParam() {
        return obdParam;
    }

    public void setObdParam(ObdParamBean obdParam) {
        this.obdParam = obdParam;
    }

    public String getProcTime() {
        return procTime;
    }

    public void setProcTime(String procTime) {
        this.procTime = procTime;
    }

    public String getProcUserId() {
        return procUserId;
    }

    public void setProcUserId(String procUserId) {
        this.procUserId = procUserId;
    }

    public String getProcUserName() {
        return procUserName;
    }

    public void setProcUserName(String procUserName) {
        this.procUserName = procUserName;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }

    public String getTerminalNO() {
        return terminalNO;
    }

    public void setTerminalNO(String terminalNO) {
        this.terminalNO = terminalNO;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
