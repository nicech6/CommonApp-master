package com.project.wisdomfirecontrol.firecontrol.model.bean.other;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/19.
 */

public class EquipmentCount implements Serializable{

    /**
     * success : true
     * msgTitle : 成功提示
     * msg : 操作成功!
     * data : {"sensorcount":0,"guzhangcount":0,"baojingcount":0}
     */

    private boolean success;
    private String msgTitle;
    private String msg;
    private DataBean data;

    private EquipmentCount(){}

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * sensorcount : 0
         * guzhangcount : 0
         * baojingcount : 0
         */

        private int sensorcount;
        private int guzhangcount;
        private int baojingcount;

        public int getSensorcount() {
            return sensorcount;
        }

        public void setSensorcount(int sensorcount) {
            this.sensorcount = sensorcount;
        }

        public int getGuzhangcount() {
            return guzhangcount;
        }

        public void setGuzhangcount(int guzhangcount) {
            this.guzhangcount = guzhangcount;
        }

        public int getBaojingcount() {
            return baojingcount;
        }

        public void setBaojingcount(int baojingcount) {
            this.baojingcount = baojingcount;
        }
    }
}
