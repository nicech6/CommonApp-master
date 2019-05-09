package com.project.wisdomfirecontrol.firecontrol.model.bean.other;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/26.
 */

public class GetSenorcountDataBean implements Serializable {
    /**
     * typename : 全部
     * num : 0
     */

    private String typename;
    private Integer num;

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
