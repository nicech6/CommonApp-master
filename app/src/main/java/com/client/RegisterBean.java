package com.client;

/**
 * @author: cuihai
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2019/6/10 10:29
 * @company:
 * @email: nicech6@163.com
 */
public class RegisterBean {


    /**
     * status : 10000
     * message : 注册成功
     * result : null
     */

    private int status;
    private String message;
    private Object result;

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

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
