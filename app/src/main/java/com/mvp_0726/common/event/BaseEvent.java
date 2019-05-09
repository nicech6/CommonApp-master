package com.mvp_0726.common.event;

/**
 * Created  on 2018-03-26.
 */

public class BaseEvent<T> {
    private int what;
    private T data;

    public BaseEvent(){}

    public BaseEvent(int what) {
        this(what, null);
    }

    public BaseEvent(int what, T data) {
        this.what = what;
        this.data = data;
    }

    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
