package com.mvp_0726.project_0726.home.model;

import java.io.Serializable;

public class GridCountResultBean implements Serializable {
    /**
     * APP_NOTICE : 1
     * APP_APTITUDE : 2
     * APP_MTANCE : 1
     */

    private int APP_NOTICE;
    private int APP_APTITUDE;
    private int APP_MTANCE;

    public int getAPP_NOTICE() {
        return APP_NOTICE;
    }

    public void setAPP_NOTICE(int APP_NOTICE) {
        this.APP_NOTICE = APP_NOTICE;
    }

    public int getAPP_APTITUDE() {
        return APP_APTITUDE;
    }

    public void setAPP_APTITUDE(int APP_APTITUDE) {
        this.APP_APTITUDE = APP_APTITUDE;
    }

    public int getAPP_MTANCE() {
        return APP_MTANCE;
    }

    public void setAPP_MTANCE(int APP_MTANCE) {
        this.APP_MTANCE = APP_MTANCE;
    }
}
