package com.project.wisdomfirecontrol.firecontrol.model.bean.document;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/3/22.
 */

public class DocumentBean implements Serializable {

    /**
     * count : 10
     * data : [{"content":" 55555 \t \r\n \t ","id":"4bcd90940a0000633776f889c46ea4b6","filepath":"http://10.0.0.99:8080/zgbd_fireControl/notice/新建文本文档25303.txt","title":"5555","role":"0","gwid":"4bcd911e0a0000633776f889f14f9ccf","sendtime":"2018-03-22 11:42:52.0","type":"2"},{"content":" 55555 \t \r\n \t ","id":"4bd026930a0000633776f8893d08fcd5","filepath":"http://10.0.0.99:8080/zgbd_fireControl/notice/消防H574823.txt","title":"555555","role":"0","gwid":"4bd027380a0000633776f889c235d0cd","sendtime":"2018-03-22 11:45:41.0","type":"2"},{"content":" 55555 \t \r\n \t ","id":"4bd029750a0000633776f889952960fd","filepath":"http://10.0.0.99:8080/zgbd_fireControl/notice/消防H514653.txt","title":"555555","role":"0","gwid":"4bd029f40a0000633776f889faa4892d","sendtime":"2018-03-22 11:45:41.0","type":"2"},{"content":" 55555 \t \r\n \t ","id":"4bd0308e0a0000633776f889fcfbbd4b","filepath":"http://10.0.0.99:8080/zgbd_fireControl/notice/消防H54110.txt","title":"555555","role":"0","gwid":"4bd031010a0000633776f8890aa1a985","sendtime":"2018-03-22 11:45:41.0","type":"2"},{"content":" 55555 \t \r\n \t ","id":"4bd03e940a0000633776f8899d8b28a2","filepath":"http://10.0.0.99:8080/zgbd_fireControl/notice/消防H514852.txt","title":"555555","role":"0","gwid":"4bd03f0b0a0000633776f88933140007","sendtime":"2018-03-22 11:45:41.0","type":"2"},{"content":" 55555 \t \r\n \t ","id":"4bd044e70a0000633776f8893b2a72ce","filepath":"http://10.0.0.99:8080/zgbd_fireControl/notice/消防H518829.txt","title":"555555","role":"0","gwid":"4bd045610a0000633776f8892d5e511d","sendtime":"2018-03-22 11:45:41.0","type":"2"},{"content":" 55555 \t \r\n \t ","id":"4bd049ce0a0000633776f8891282e988","filepath":"http://10.0.0.99:8080/zgbd_fireControl/notice/消防H537216.txt","title":"555555","role":"0","gwid":"4bd04a630a0000633776f889e0c25cf4","sendtime":"2018-03-22 11:45:41.0","type":"2"},{"content":" 55555 \t \r\n \t ","id":"4bd04eed0a0000633776f88998d668d8","filepath":"http://10.0.0.99:8080/zgbd_fireControl/notice/消防H56180.txt","title":"555555","role":"0","gwid":"4bd04f740a0000633776f88938b80623","sendtime":"2018-03-22 11:45:41.0","type":"2"},{"content":" 55555 \t \r\n \t ","id":"4bd054380a0000633776f8897f36981f","filepath":"http://10.0.0.99:8080/zgbd_fireControl/notice/消防H564349.txt","title":"555555","role":"0","gwid":"4bd054ac0a0000633776f889eecc86ba","sendtime":"2018-03-22 11:45:41.0","type":"2"},{"content":" 55555 \t \r\n \t ","id":"4bd059550a0000633776f889d656a4f1","filepath":"http://10.0.0.99:8080/zgbd_fireControl/notice/消防H544740.txt","title":"555555","role":"0","gwid":"4bd059ce0a0000633776f8898256ed9b","sendtime":"2018-03-22 11:45:41.0","type":"2"}]
     * success : true
     * msg :
     */

    private int count;
    private boolean success;
    private String msg;
    private List<DocumentsBean> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DocumentsBean> getData() {
        return data;
    }

    public void setData(List<DocumentsBean> data) {
        this.data = data;
    }

}
