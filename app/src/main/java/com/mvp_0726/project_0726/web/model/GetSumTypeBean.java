package com.mvp_0726.project_0726.web.model;

import java.io.Serializable;
import java.util.List;

public class GetSumTypeBean implements Serializable {


    /**
     * status : 10000
     * message : null
     * result : [{"sum":1,"type":"REFORM_NOTICE"},{"sum":5,"type":"MTANCE_PUSH_NOTICE"},{"sum":1,"type":"APTITUDE_MINE"},{"sum":1,"type":"APTITUDE_VERIFY"}]
     */

    private int status;
    private Object message;
    private List<GetSumTypeResultBean> result;

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

    public List<GetSumTypeResultBean> getResult() {
        return result;
    }

    public void setResult(List<GetSumTypeResultBean> result) {
        this.result = result;
    }

}
