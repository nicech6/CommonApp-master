package com.mvp_0726.project_0726.online_unit.contract;


import com.mvp_0726.common.base.codereview.BaseView;

public interface SettingOnlinePoliceContract {
    interface View extends BaseView<Object> {

    }

    interface Presenter {
        void getEquipmentCount(String pid);

        void getSenorcount(String pid, String state);

        void getSensor(String pid, String type, String state, String ishave,int what,int errorWhat);

        void getsensorObd(String terminalNo);
    }
}
