package com.mvp_0726.project_0726.home.model;

import java.io.Serializable;

public class SettingCountBean implements Serializable {


    private String name;
    private String count;

    public SettingCountBean(String name,String count){
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
