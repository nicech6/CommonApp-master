package com.project.wisdomfirecontrol.firecontrol.model.bean.other;

/**
 * Created by Administrator on 2018/3/21.
 */

public class LoginBean {


    /**
     * success : true
     * msgTitle : 成功提示
     * msg : 操作成功!
     * data : {"id":"c5073eab0a00000518c6c9b9c959a415","name":"李元芳","telNum":"13066615587","position":"总经理","pid":"yun","createTime":"2018-03-21 09:32:19.0","orderNumber":"1","password":"123"}
     */

    private boolean success;
    private String msgTitle;
    private String msg;
    private DataBean data;

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
         * id : c5073eab0a00000518c6c9b9c959a415
         * name : 李元芳
         * telNum : 13066615587
         * position : 总经理
         * pid : yun
         * createTime : 2018-03-21 09:32:19.0
         * orderNumber : 1
         * password : 123
         */

        private String id;
        private String name;
        private String telNum;
        private String position;
        private String pid;
        private String createTime;
        private String orderNumber;
        private String password;
        private String isadmin;
        private String imagthpath;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTelNum() {
            return telNum;
        }

        public void setTelNum(String telNum) {
            this.telNum = telNum;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getIsadmin() {
            return isadmin;
        }

        public void setIsadmin(String isadmin) {
            this.isadmin = isadmin;
        }

        public String getImagthpath() {
            return imagthpath;
        }

        public void setImagthpath(String imagthpath) {
            this.imagthpath = imagthpath;
        }
    }
}
