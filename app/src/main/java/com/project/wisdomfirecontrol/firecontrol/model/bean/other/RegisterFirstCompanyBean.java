package com.project.wisdomfirecontrol.firecontrol.model.bean.other;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/13.
 */

public class RegisterFirstCompanyBean implements Serializable {

    private String lng;
    private String lat;
    private String address_detail;
    private String province;
    private String city;
    private String area;
    private String address_detail_room;
    private String company_name;
    private String company_name_six;

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getAddress_detail() {
        return address_detail;
    }

    public void setAddress_detail(String address_detail) {
        this.address_detail = address_detail;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress_detail_room() {
        return address_detail_room;
    }

    public void setAddress_detail_room(String address_detail_room) {
        this.address_detail_room = address_detail_room;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_name_six() {
        return company_name_six;
    }

    public void setCompany_name_six(String company_name_six) {
        this.company_name_six = company_name_six;
    }
}
