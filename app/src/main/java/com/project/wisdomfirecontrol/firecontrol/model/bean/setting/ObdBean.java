package com.project.wisdomfirecontrol.firecontrol.model.bean.setting;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/25.
 */

public class ObdBean implements Serializable {

    /**
     * ae :
     * av :
     * be :
     * bv :
     * ce :
     * cv :
     * frtemp :
     * louele :
     * otemp :
     * thtemp :
     * totemp :
     */

    private String ae;
    private String av;
    private String be;
    private String bv;
    private String ce;
    private String cv;
    private String frtemp;
    private String louele;
    private String otemp;
    private String thtemp;
    private String totemp;

    public String getAe() {
        return ae;
    }

    public void setAe(String ae) {
        this.ae = ae;
    }

    public String getAv() {
        return av;
    }

    public void setAv(String av) {
        this.av = av;
    }

    public String getBe() {
        return be;
    }

    public void setBe(String be) {
        this.be = be;
    }

    public String getBv() {
        return bv;
    }

    public void setBv(String bv) {
        this.bv = bv;
    }

    public String getCe() {
        return ce;
    }

    public void setCe(String ce) {
        this.ce = ce;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getFrtemp() {
        return frtemp;
    }

    public void setFrtemp(String frtemp) {
        this.frtemp = frtemp;
    }

    public String getLouele() {
        return louele;
    }

    public void setLouele(String louele) {
        this.louele = louele;
    }

    public String getOtemp() {
        return otemp;
    }

    public void setOtemp(String otemp) {
        this.otemp = otemp;
    }

    public String getThtemp() {
        return thtemp;
    }

    public void setThtemp(String thtemp) {
        this.thtemp = thtemp;
    }

    public String getTotemp() {
        return totemp;
    }

    public void setTotemp(String totemp) {
        this.totemp = totemp;
    }
}
