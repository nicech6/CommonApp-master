package com.mvp_0726.common.network;

import java.io.Serializable;

/**
 * Created  on 2018-03-20.
 * author:seven
 * email:seven2016s@163.com
 */

public class ResponseCustom<T> implements Serializable {


    private boolean success;
    private String msgTitle;
    private String msg;
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
