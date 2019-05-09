package com.mvp_0726.project_0726.login.contract;


import com.mvp_0726.common.base.codereview.BaseView;

public interface ChangePasswordContract {
    interface View extends BaseView<Object> {

    }

    interface Presenter {
        void updatePwd(String username, String oldPwd,String newPwd);
    }
}
