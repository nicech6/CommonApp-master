package com.project.wisdomfirecontrol.firecontrol.model.bean.other;

/**
 * Created by Administrator on 2018/4/9.
 */

public class CompanyCheckBean {


    /**
     * early : 0
     * late : 0
     * error : 0
     * reason : 1
     */

    private String early;
    private String late;
    private String error;
    private String reason;

    public String getEarly() {
        return early;
    }

    public void setEarly(String early) {
        this.early = early;
    }

    public String getLate() {
        return late;
    }

    public void setLate(String late) {
        this.late = late;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
