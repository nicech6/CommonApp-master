package com.project.wisdomfirecontrol.firecontrol.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/17.
 */

public class TestBean {


    /**
     * success : true
     * msgTitle : 成功提示
     * msg : 操作成功!
     * data : {role":[{"id":"7d198e820a00001f0e6387d34ed63ed2","roleName":"大boss","description":"神权限。没事别用"}]}}
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

        private PersonelBean personel;
        private List<MenuDatasBeanX> menuDatas;

        public PersonelBean getPersonel() {
            return personel;
        }

        public void setPersonel(PersonelBean personel) {
            this.personel = personel;
        }

        public List<MenuDatasBeanX> getMenuDatas() {
            return menuDatas;
        }

        public void setMenuDatas(List<MenuDatasBeanX> menuDatas) {
            this.menuDatas = menuDatas;
        }

        public static class PersonelBean {
            /**
             * id : 6a3edb800a0000637c4391346fdef973
             * name : 赵工
             * telNum : yuefeng
             * position :
             * pid : yun
             * createTime : 2018-06-26 13:11:15.0
             * orderNumber : 0
             * password : 123456
             * isadmin : 0
             * imagthpath : http://www.vocsystem.cn/webfiles/zgbd_fireControl/shouye/lunbo1.png,http://www.vocsystem.cn/webfiles/zgbd_fireControl/shouye/lunbo2.png,http://www.vocsystem.cn/webfiles/zgbd_fireControl/shouye/lunbo3.jpg
             * orgName : 乆安消防平台
             * orgShortName : 乆安消防
             * role : [{"id":"7d198e820a00001f0e6387d34ed63ed2","roleName":"大boss","description":"神权限。没事别用"}]
             */

            private String id;
            private String name;
            private String telNum;
            private String position;
            private String pid;
            private String createTime;
            private String orderNumber;
            private String password;
            private String isadmin;
            private String imagthpath;
            private String orgName;
            private String orgShortName;
            private List<RoleBean> role;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTelNum() {
                return telNum;
            }

            public void setTelNum(String telNum) {
                this.telNum = telNum;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getOrderNumber() {
                return orderNumber;
            }

            public void setOrderNumber(String orderNumber) {
                this.orderNumber = orderNumber;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getIsadmin() {
                return isadmin;
            }

            public void setIsadmin(String isadmin) {
                this.isadmin = isadmin;
            }

            public String getImagthpath() {
                return imagthpath;
            }

            public void setImagthpath(String imagthpath) {
                this.imagthpath = imagthpath;
            }

            public String getOrgName() {
                return orgName;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }

            public String getOrgShortName() {
                return orgShortName;
            }

            public void setOrgShortName(String orgShortName) {
                this.orgShortName = orgShortName;
            }

            public List<RoleBean> getRole() {
                return role;
            }

            public void setRole(List<RoleBean> role) {
                this.role = role;
            }

            public static class RoleBean {
                /**
                 * id : 7d198e820a00001f0e6387d34ed63ed2
                 * roleName : 大boss
                 * description : 神权限。没事别用
                 */

                private String id;
                private String roleName;
                private String description;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getRoleName() {
                    return roleName;
                }

                public void setRoleName(String roleName) {
                    this.roleName = roleName;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }
            }
        }

        public static class MenuDatasBeanX {
            /**
             * id : 8304f2f50a00001f108419cc38e6d9ba
             * pid :
             * name : 辖区单位
             * rightNo : 01
             * orderNum : 1
             * optionNum : 0
             * menuurl :
             * rightid : 8304f2f50a00001f108419cc38e6d9ba
             * roleid : 7d198e820a00001f0e6387d34ed63ed2
             * imagePath : http://120.78.217.251/zgbd_fireControl/H5/image/home/icon_lwdw.png
             * menuDatas : [{"id":"830543b00a00001f108419cc8eb36e67","pid":"8304f2f50a00001f108419cc38e6d9ba","name":"联网单位","rightNo":"01-01","optionNum":"0","menuurl":"/jsps/showpage/fireShowQYSX.jsp","rightid":"830543b00a00001f108419cc8eb36e67","roleid":"7d198e820a00001f0e6387d34ed63ed2","imagePath":"","menuDatas":[]}]
             */

            private String id;
            private String pid;
            private String name;
            private String rightNo;
            private int orderNum;
            private String optionNum;
            private String menuurl;
            private String rightid;
            private String roleid;
            private String imagePath;
            private List<MenuDatasBean> menuDatas;

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getRightNo() {
                return rightNo;
            }

            public void setRightNo(String rightNo) {
                this.rightNo = rightNo;
            }

            public int getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(int orderNum) {
                this.orderNum = orderNum;
            }

            public String getOptionNum() {
                return optionNum;
            }

            public void setOptionNum(String optionNum) {
                this.optionNum = optionNum;
            }

            public String getMenuurl() {
                return menuurl;
            }

            public void setMenuurl(String menuurl) {
                this.menuurl = menuurl;
            }

            public String getRightid() {
                return rightid;
            }

            public void setRightid(String rightid) {
                this.rightid = rightid;
            }

            public String getRoleid() {
                return roleid;
            }

            public void setRoleid(String roleid) {
                this.roleid = roleid;
            }

            public String getImagePath() {
                return imagePath;
            }

            public void setImagePath(String imagePath) {
                this.imagePath = imagePath;
            }

            public List<MenuDatasBean> getMenuDatas() {
                return menuDatas;
            }

            public void setMenuDatas(List<MenuDatasBean> menuDatas) {
                this.menuDatas = menuDatas;
            }

            public static class MenuDatasBean {
                /**
                 * id : 830543b00a00001f108419cc8eb36e67
                 * pid : 8304f2f50a00001f108419cc38e6d9ba
                 * name : 联网单位
                 * rightNo : 01-01
                 * optionNum : 0
                 * menuurl : /jsps/showpage/fireShowQYSX.jsp
                 * rightid : 830543b00a00001f108419cc8eb36e67
                 * roleid : 7d198e820a00001f0e6387d34ed63ed2
                 * imagePath :
                 * menuDatas : []
                 */

                private String id;
                private String pid;
                private String name;
                private String rightNo;
                private String optionNum;
                private String menuurl;
                private String rightid;
                private String roleid;
                private String imagePath;
                private List<?> menuDatas;

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

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getRightNo() {
                    return rightNo;
                }

                public void setRightNo(String rightNo) {
                    this.rightNo = rightNo;
                }

                public String getOptionNum() {
                    return optionNum;
                }

                public void setOptionNum(String optionNum) {
                    this.optionNum = optionNum;
                }

                public String getMenuurl() {
                    return menuurl;
                }

                public void setMenuurl(String menuurl) {
                    this.menuurl = menuurl;
                }

                public String getRightid() {
                    return rightid;
                }

                public void setRightid(String rightid) {
                    this.rightid = rightid;
                }

                public String getRoleid() {
                    return roleid;
                }

                public void setRoleid(String roleid) {
                    this.roleid = roleid;
                }

                public String getImagePath() {
                    return imagePath;
                }

                public void setImagePath(String imagePath) {
                    this.imagePath = imagePath;
                }

                public List<?> getMenuDatas() {
                    return menuDatas;
                }

                public void setMenuDatas(List<?> menuDatas) {
                    this.menuDatas = menuDatas;
                }
            }
        }
    }
}
