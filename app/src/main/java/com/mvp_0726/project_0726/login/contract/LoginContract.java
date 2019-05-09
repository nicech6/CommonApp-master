package com.mvp_0726.project_0726.login.contract;


import com.mvp_0726.common.base.codereview.BaseView;

public interface LoginContract {
    interface View extends BaseView<Object> {

    }

    interface Presenter {
        void login(String userName, String pwd);
    }
}
