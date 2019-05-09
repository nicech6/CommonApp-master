package com.project.wisdomfirecontrol.firecontrol.model.bean.other;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/25.
 */

public class GetCompanyRegisterInfosBean implements Serializable {


    /**
     * success : true
     * msgTitle : 成功提示
     * msg : 操作成功!
     * data : {"id":"02b63a4dffffffc93af00c85116aa6c5","pid":"zhuce","orgName":"粤峰","principal":"后","principalTel":"13636363636","createTime":"2018-06-15 17:10:54.0","address":"中国广东省广州市天河区新塘大街28祺禾商贸元","lng":"113.414084","lat":"23.158944","legalperson":"秦","area":"68","name1":"周","name2":"密码","name3":"哦哦","name4":"","phone1":"13636363636","phone2":"13535353535","phone3":"解决","phone4":"","isexamine":"1","imgUrl":"http://120.78.217.251:80/webfiles/zgbd_fireControl/images/organ/2018061517105470840.png","renshu":"79","province":"广东省","city":"广州市","district":"天河区","township":"新塘街道","companytype":"0"}
     */

    private boolean success;
    private String msgTitle;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 02b63a4dffffffc93af00c85116aa6c5
         * pid : zhuce
         * orgName : 粤峰
         * principal : 后
         * principalTel : 13636363636
         * createTime : 2018-06-15 17:10:54.0
         * address : 中国广东省广州市天河区新塘大街28祺禾商贸元
         * lng : 113.414084
         * lat : 23.158944
         * legalperson : 秦
         * area : 68
         * name1 : 周
         * name2 : 密码
         * name3 : 哦哦
         * name4 :
         * phone1 : 13636363636
         * phone2 : 13535353535
         * phone3 : 解决
         * phone4 :
         * isexamine : 1
         * imgUrl : http://120.78.217.251:80/webfiles/zgbd_fireControl/images/organ/2018061517105470840.png
         * renshu : 79
         * province : 广东省
         * city : 广州市
         * district : 天河区
         * township : 新塘街道
         * companytype : 0
         */

        private String id;
        private String pid;
        private String orgName;
        private String principal;
        private String principalTel;
        private String createTime;
        private String address;
        private String lng;
        private String lat;
        private String legalperson;
        private String area;
        private String name1;
        private String name2;
        private String name3;
        private String name4;
        private String phone1;
        private String phone2;
        private String phone3;
        private String phone4;
        private String isexamine;
        private String imgUrl;
        private String renshu;
        private String province;
        private String city;
        private String district;
        private String township;
        private String companytype;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getPrincipal() {
            return principal;
        }

        public void setPrincipal(String principal) {
            this.principal = principal;
        }

        public String getPrincipalTel() {
            return principalTel;
        }

        public void setPrincipalTel(String principalTel) {
            this.principalTel = principalTel;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

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

        public String getLegalperson() {
            return legalperson;
        }

        public void setLegalperson(String legalperson) {
            this.legalperson = legalperson;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getName1() {
            return name1;
        }

        public void setName1(String name1) {
            this.name1 = name1;
        }

        public String getName2() {
            return name2;
        }

        public void setName2(String name2) {
            this.name2 = name2;
        }

        public String getName3() {
            return name3;
        }

        public void setName3(String name3) {
            this.name3 = name3;
        }

        public String getName4() {
            return name4;
        }

        public void setName4(String name4) {
            this.name4 = name4;
        }

        public String getPhone1() {
            return phone1;
        }

        public void setPhone1(String phone1) {
            this.phone1 = phone1;
        }

        public String getPhone2() {
            return phone2;
        }

        public void setPhone2(String phone2) {
            this.phone2 = phone2;
        }

        public String getPhone3() {
            return phone3;
        }

        public void setPhone3(String phone3) {
            this.phone3 = phone3;
        }

        public String getPhone4() {
            return phone4;
        }

        public void setPhone4(String phone4) {
            this.phone4 = phone4;
        }

        public String getIsexamine() {
            return isexamine;
        }

        public void setIsexamine(String isexamine) {
            this.isexamine = isexamine;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getRenshu() {
            return renshu;
        }

        public void setRenshu(String renshu) {
            this.renshu = renshu;
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

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getTownship() {
            return township;
        }

        public void setTownship(String township) {
            this.township = township;
        }

        public String getCompanytype() {
            return companytype;
        }

        public void setCompanytype(String companytype) {
            this.companytype = companytype;
        }
    }
}
