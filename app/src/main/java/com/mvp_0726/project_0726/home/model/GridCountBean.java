package com.mvp_0726.project_0726.home.model;

import java.io.Serializable;

public class GridCountBean implements Serializable {

    /**首页角标数量
     * status : 10000
     * message : null
     * result : {"APP_NOTICE":1,"APP_APTITUDE":2,"APP_MTANCE":1}
     */

    private int status;
    private Object message;
    private GridCountResultBean result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public GridCountResultBean getResult() {
        return result;
    }

    public void setResult(GridCountResultBean result) {
        this.result = result;
    }
}
