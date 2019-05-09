package com.project.wisdomfirecontrol.firecontrol.model.bean.document;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/22.
 */

public class DocumentsBean implements Serializable {
    /**
     * content :  55555
     * <p>
     * id : 4bcd90940a0000633776f889c46ea4b6
     * filepath : http://10.0.0.99:8080/zgbd_fireControl/notice/新建文本文档25303.txt
     * title : 5555
     * role : 0
     * gwid : 4bcd911e0a0000633776f889f14f9ccf
     * sendtime : 2018-03-22 11:42:52.0
     * type : 2
     */

    private String content;
    private String id;
    private String filepath;
    private String title;
    private String role;
    private String gwid;
    private String sendtime;
    private String type;

    public DocumentsBean(){

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getGwid() {
        return gwid;
    }

    public void setGwid(String gwid) {
        this.gwid = gwid;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
