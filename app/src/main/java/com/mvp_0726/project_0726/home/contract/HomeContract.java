package com.mvp_0726.project_0726.home.contract;


import com.mvp_0726.common.base.codereview.BaseView;

public interface HomeContract {
    interface View extends BaseView<Object> {

    }

    interface Presenter {
        void getMarqueeDatas(String pid);

        void getOrgandetailDatas(String pid);

        void getEquipmentCount(String pid);
        /*获取角标数量*/
        void getAppNum(String pid,String id);
    }
}
