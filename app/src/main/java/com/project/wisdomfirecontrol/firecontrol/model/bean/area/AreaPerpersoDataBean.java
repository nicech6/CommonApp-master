package com.project.wisdomfirecontrol.firecontrol.model.bean.area;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/22.
 */

public class AreaPerpersoDataBean implements Serializable {


    /**
     * id : 6a3edb800a0000637c4391346fdef973
     * name : YY
     * telNum : yuefeng
     * position :
     * pid : yun
     * createTime : 2018-06-15 17:07:56.0
     * orderNumber : 0
     * password : 123456
     * isadmin : 0
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
}
