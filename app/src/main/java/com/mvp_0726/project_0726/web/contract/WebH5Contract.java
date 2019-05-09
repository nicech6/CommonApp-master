package com.mvp_0726.project_0726.web.contract;


import com.mvp_0726.common.base.codereview.BaseView;

public interface WebH5Contract {
    interface View extends BaseView<Object> {

    }

    interface Presenter {
        /*获取角标数量*/
        void getSumType(String pid, String id);
    }
}
