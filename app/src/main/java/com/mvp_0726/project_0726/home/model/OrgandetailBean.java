package com.mvp_0726.project_0726.home.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/10.
 */

public class OrgandetailBean implements Serializable {

    /**
     * success : true
     * msgTitle : 成功提示
     * msg : 操作成功!
     * data : {"id":"yun","orgName":"乆安消防平台","principal":"曹总","principalTel":"18080792134","createTime":"2018-06-14 16:47:44.0","address":"广东省-广州市-市辖区-大运软件小镇58栋-大运软件小镇58栋","lng":"114.228163","lat":"22.685202","legalperson":"曹总","area":"2000","name1":"曹总","name2":"时宇","name3":"李工","phone1":"18080792134","phone2":"18045012612","phone3":"13428906789","isexamine":"1","imgUrl":"http://172.18.68.201:8080/zgbd_fireControl/notice/004773460.jpg,http://10.0.0.31:8080/zgbd_fireControl/notice/banner_image_third42299.jpg","renshu":"40","orgShortName":"乆安消防","province":"广东省","city":"广州市","district":"市辖区","township":"大运软件小镇58栋","companytype":"1"}
     */

    private OrgandetailBean(){}

    private boolean success;
    private String msgTitle;
    private String msg;
    private OrgandetailDataBean data;

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

    public OrgandetailDataBean getData() {
        return data;
    }

    public void setData(OrgandetailDataBean data) {
        this.data = data;
    }
}
