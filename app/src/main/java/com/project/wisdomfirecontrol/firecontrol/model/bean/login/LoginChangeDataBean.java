package com.project.wisdomfirecontrol.firecontrol.model.bean.login;

import java.io.Serializable;
import java.util.List;

/*
 * Created by Administrator on 2018/7/9.
 */

public class LoginChangeDataBean implements Serializable{
    /**
     * menuDatas : [{"id":"c9874d9b0a0000410015f2db078224aa","pid":"","name":"辖区单位","rightNo":"1","orderNum":1,"optionNum":"0","menuurl":"","rightid":"c9874d9b0a0000410015f2db078224aa","roleid":"6844eafbffffffc917087d1e58378d48","imagePath":"","menuDatas":[{"id":"070f1cdfffffffc956dd05357dd9b1dc","pid":"c9874d9b0a0000410015f2db078224aa","name":"联网单位","rightNo":"","orderNum":1,"optionNum":"0","menuurl":"/jsps/showpage/fireShowQYSX.jsp","rightid":"070f1cdfffffffc956dd05357dd9b1dc","roleid":"6844eafbffffffc917087d1e58378d48","imagePath":"","menuDatas":[]}]},{"id":"072893dcffffffc956dd053531b282e7","pid":"","name":"视频监控","rightNo":"9","orderNum":9,"optionNum":"0","menuurl":"","rightid":"072893dcffffffc956dd053531b282e7","roleid":"6844eafbffffffc917087d1e58378d48","imagePath":"","menuDatas":[{"id":"07376da9ffffffc956dd0535ba7f6b7f","pid":"072893dcffffffc956dd053531b282e7","name":"实时视频","rightNo":"91","orderNum":1,"optionNum":"0","menuurl":"/jsps/enterprise/vodie1.jsp","rightid":"07376da9ffffffc956dd0535ba7f6b7f","roleid":"6844eafbffffffc917087d1e58378d48","imagePath":"","menuDatas":[]},{"id":"07379d95ffffffc956dd0535eaad0824","pid":"072893dcffffffc956dd053531b282e7","name":"视频管理","rightNo":"92","orderNum":2,"optionNum":"0","menuurl":"/jsps/enterprise/video.jsp","rightid":"07379d95ffffffc956dd0535eaad0824","roleid":"6844eafbffffffc917087d1e58378d48","imagePath":"","menuDatas":[]}]},{"id":"0728b889ffffffc956dd05353bb81bad","pid":"","name":"系统维护","rightNo":"10","orderNum":10,"optionNum":"0","menuurl":"","rightid":"0728b889ffffffc956dd05353bb81bad","roleid":"6844eafbffffffc917087d1e58378d48","imagePath":"","menuDatas":[{"id":"073847b3ffffffc956dd053567fa8f79","pid":"0728b889ffffffc956dd05353bb81bad","name":"设备管理","rightNo":"102","orderNum":2,"optionNum":"0","menuurl":"/jsps/enterprise/sensorList.jsp","rightid":"073847b3ffffffc956dd053567fa8f79","roleid":"6844eafbffffffc917087d1e58378d48","imagePath":"","menuDatas":[]},{"id":"0738897bffffffc956dd05353f9e850d","pid":"0728b889ffffffc956dd05353bb81bad","name":"账号管理","rightNo":"103","orderNum":3,"optionNum":"0","menuurl":"/jsps/system/userList.jsp","rightid":"0738897bffffffc956dd05353f9e850d","roleid":"6844eafbffffffc917087d1e58378d48","imagePath":"","menuDatas":[]},{"id":"0738afa3ffffffc956dd0535943b01f0","pid":"0728b889ffffffc956dd05353bb81bad","name":"机构管理","rightNo":"104","orderNum":4,"optionNum":"0","menuurl":"/jsps/system/organList.jsp","rightid":"0738afa3ffffffc956dd0535943b01f0","roleid":"6844eafbffffffc917087d1e58378d48","imagePath":"","menuDatas":[]},{"id":"0738dc6bffffffc956dd0535bd166442","pid":"0728b889ffffffc956dd05353bb81bad","name":"角色管理","rightNo":"105","orderNum":5,"optionNum":"0","menuurl":"/jsps/system/roleList.jsp","rightid":"0738dc6bffffffc956dd0535bd166442","roleid":"6844eafbffffffc917087d1e58378d48","imagePath":"","menuDatas":[]},{"id":"0739257cffffffc956dd0535935a79ca","pid":"0728b889ffffffc956dd05353bb81bad","name":"权限管理","rightNo":"106","orderNum":6,"optionNum":"0","menuurl":"/jsps/system/rightList.jsp","rightid":"0739257cffffffc956dd0535935a79ca","roleid":"6844eafbffffffc917087d1e58378d48","imagePath":"","menuDatas":[]}]}]
     * personel : {"id":"6a3edb800a0000637c4391346fdef973","name":"赵工","telNum":"yuefeng","position":"","pid":"yun","createTime":"2018-06-26 13:11:15.0","orderNumber":"0","password":"123456","isadmin":"0","imagthpath":"http://www.vocsystem.cn/webfiles/zgbd_fireControl/shouye/lunbo1.png,http://www.vocsystem.cn/webfiles/zgbd_fireControl/shouye/lunbo2.png,http://www.vocsystem.cn/webfiles/zgbd_fireControl/shouye/lunbo3.jpg"}
     */

    private PersonelChangeBean personel;
    private List<MenuDatasBeanX> menuDatas;

    public PersonelChangeBean getPersonel() {
        return personel;
    }

    public void setPersonel(PersonelChangeBean personel) {
        this.personel = personel;
    }

    public List<MenuDatasBeanX> getMenuDatas() {
        return menuDatas;
    }

    public void setMenuDatas(List<MenuDatasBeanX> menuDatas) {
        this.menuDatas = menuDatas;
    }

}
