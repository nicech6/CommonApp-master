package com.mvp_0726.project_0726.login.modle;

public class DanWeiBean {

    /**
     * status : 10000
     * message : 缁熻鎴愬姛
     * result : {"bjTotal":0,"lwTotal":13,"gzlxTotal":11,"zxTotal":2,"pid":null,"lwPids":"06e567e20a0a00235e0856b95d3b9b6e,06ecbee90a0a00235e0856b9640108ad,76b8fba20a0a00235e5370fa2c9e65c0,89a478810a0a00231955c8f17e4089ec,94e1982b0a0a00232f2d5fe49171c792,53670f010a0a0023316bc50c5d260b67,536abb210a0a0023316bc50c5f1ca902,fe15deb20a0a00233ab623251c964edf,7f1470ff0a0a00232b149cb0413808e9,9b61a0980a0a00234cad2edbc4e14235,db8477df0a0a002342d1f0398905373c,d58c002affffffc922f3ccceef215095,zhuce","bjPids":"","gzlxPids":"06ecbee90a0a00235e0856b9640108ad,76b8fba20a0a00235e5370fa2c9e65c0,89a478810a0a00231955c8f17e4089ec,94e1982b0a0a00232f2d5fe49171c792,53670f010a0a0023316bc50c5d260b67,536abb210a0a0023316bc50c5f1ca902,fe15deb20a0a00233ab623251c964edf,7f1470ff0a0a00232b149cb0413808e9,9b61a0980a0a00234cad2edbc4e14235,d58c002affffffc922f3ccceef215095,zhuce","zxPids":"06e567e20a0a00235e0856b95d3b9b6e,db8477df0a0a002342d1f0398905373c","saveVersion":null}
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
         * bjTotal : 0
         * lwTotal : 13
         * gzlxTotal : 11
         * zxTotal : 2
         * pid : null
         * lwPids : 06e567e20a0a00235e0856b95d3b9b6e,06ecbee90a0a00235e0856b9640108ad,76b8fba20a0a00235e5370fa2c9e65c0,89a478810a0a00231955c8f17e4089ec,94e1982b0a0a00232f2d5fe49171c792,53670f010a0a0023316bc50c5d260b67,536abb210a0a0023316bc50c5f1ca902,fe15deb20a0a00233ab623251c964edf,7f1470ff0a0a00232b149cb0413808e9,9b61a0980a0a00234cad2edbc4e14235,db8477df0a0a002342d1f0398905373c,d58c002affffffc922f3ccceef215095,zhuce
         * bjPids :
         * gzlxPids : 06ecbee90a0a00235e0856b9640108ad,76b8fba20a0a00235e5370fa2c9e65c0,89a478810a0a00231955c8f17e4089ec,94e1982b0a0a00232f2d5fe49171c792,53670f010a0a0023316bc50c5d260b67,536abb210a0a0023316bc50c5f1ca902,fe15deb20a0a00233ab623251c964edf,7f1470ff0a0a00232b149cb0413808e9,9b61a0980a0a00234cad2edbc4e14235,d58c002affffffc922f3ccceef215095,zhuce
         * zxPids : 06e567e20a0a00235e0856b95d3b9b6e,db8477df0a0a002342d1f0398905373c
         * saveVersion : null
         */

        private int bjTotal;
        private int lwTotal;
        private int gzlxTotal;
        private int zxTotal;
        private Object pid;
        private String lwPids;
        private String bjPids;
        private String gzlxPids;
        private String zxPids;
        private Object saveVersion;

        public int getBjTotal() {
            return bjTotal;
        }

        public void setBjTotal(int bjTotal) {
            this.bjTotal = bjTotal;
        }

        public int getLwTotal() {
            return lwTotal;
        }

        public void setLwTotal(int lwTotal) {
            this.lwTotal = lwTotal;
        }

        public int getGzlxTotal() {
            return gzlxTotal;
        }

        public void setGzlxTotal(int gzlxTotal) {
            this.gzlxTotal = gzlxTotal;
        }

        public int getZxTotal() {
            return zxTotal;
        }

        public void setZxTotal(int zxTotal) {
            this.zxTotal = zxTotal;
        }

        public Object getPid() {
            return pid;
        }

        public void setPid(Object pid) {
            this.pid = pid;
        }

        public String getLwPids() {
            return lwPids;
        }

        public void setLwPids(String lwPids) {
            this.lwPids = lwPids;
        }

        public String getBjPids() {
            return bjPids;
        }

        public void setBjPids(String bjPids) {
            this.bjPids = bjPids;
        }

        public String getGzlxPids() {
            return gzlxPids;
        }

        public void setGzlxPids(String gzlxPids) {
            this.gzlxPids = gzlxPids;
        }

        public String getZxPids() {
            return zxPids;
        }

        public void setZxPids(String zxPids) {
            this.zxPids = zxPids;
        }

        public Object getSaveVersion() {
            return saveVersion;
        }

        public void setSaveVersion(Object saveVersion) {
            this.saveVersion = saveVersion;
        }
    }
}
