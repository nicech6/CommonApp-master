package com.client;

public class HomeNumberBean {

    /**
     * status : 10000
     * message : 成功
     * result : {"onlineCount":7,"alarmCount":12,"faultCount":8}
     */

    private int status;
    private String message;
    private ResultBean result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * onlineCount : 7
         * alarmCount : 12
         * faultCount : 8
         */

        private int onlineCount;
        private int alarmCount;
        private int faultCount;

        public int getOnlineCount() {
            return onlineCount;
        }

        public void setOnlineCount(int onlineCount) {
            this.onlineCount = onlineCount;
        }

        public int getAlarmCount() {
            return alarmCount;
        }

        public void setAlarmCount(int alarmCount) {
            this.alarmCount = alarmCount;
        }

        public int getFaultCount() {
            return faultCount;
        }

        public void setFaultCount(int faultCount) {
            this.faultCount = faultCount;
        }
    }
}
