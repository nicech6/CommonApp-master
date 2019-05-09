package com.project.wisdomfirecontrol.firecontrol.model.bean.other;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/19.
 */

public class CountBean implements Serializable{
   private String name;
   private int count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
