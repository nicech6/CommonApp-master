package com.mvp_0726.project_0726.bean.settingpolice;

import java.io.Serializable;

public class ObdParamBean implements Serializable {
    /**
     * Ce : 44.6A
     * Be : 33.3A
     * Ae : 56.2A
     * thtemp : 28.1℃
     * totemp : 27.9℃
     * frtemp : 28.1℃
     * otemp : 28.0℃
     * louele : 0.0mA
     * Cv : 231.4V
     * Bv : 230.0V
     * Av : 230.5V
     * Av2 : 0.0V
     * Cv2 : 0.0V
     * Bv2 : 0.0V
     */

    private String Ce;
    private String Be;
    private String Ae;
    private String thtemp;
    private String totemp;
    private String frtemp;
    private String otemp;
    private String louele;
    private String Cv;
    private String Bv;
    private String Av;
    private String Av2;
    private String Cv2;
    private String Bv2;

    public String getCe() {
        return Ce;
    }

    public void setCe(String Ce) {
        this.Ce = Ce;
    }

    public String getBe() {
        return Be;
    }

    public void setBe(String Be) {
        this.Be = Be;
    }

    public String getAe() {
        return Ae;
    }

    public void setAe(String Ae) {
        this.Ae = Ae;
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

    public String getFrtemp() {
        return frtemp;
    }

    public void setFrtemp(String frtemp) {
        this.frtemp = frtemp;
    }

    public String getOtemp() {
        return otemp;
    }

    public void setOtemp(String otemp) {
        this.otemp = otemp;
    }

    public String getLouele() {
        return louele;
    }

    public void setLouele(String louele) {
        this.louele = louele;
    }

    public String getCv() {
        return Cv;
    }

    public void setCv(String Cv) {
        this.Cv = Cv;
    }

    public String getBv() {
        return Bv;
    }

    public void setBv(String Bv) {
        this.Bv = Bv;
    }

    public String getAv() {
        return Av;
    }

    public void setAv(String Av) {
        this.Av = Av;
    }

    public String getAv2() {
        return Av2;
    }

    public void setAv2(String Av2) {
        this.Av2 = Av2;
    }

    public String getCv2() {
        return Cv2;
    }

    public void setCv2(String Cv2) {
        this.Cv2 = Cv2;
    }

    public String getBv2() {
        return Bv2;
    }

    public void setBv2(String Bv2) {
        this.Bv2 = Bv2;
    }
}
