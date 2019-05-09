package com.mvp_0726.project_0726.web.model;

import java.io.Serializable;

public class GetSumTypeResultBean implements Serializable {
    /**
     * sum : 1
     * type : REFORM_NOTICE
     */

    private int sum;
    private String type;

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
