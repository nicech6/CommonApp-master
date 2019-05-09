package com.mvp_0726.project_0726.home.contract;


import com.mvp_0726.common.base.codereview.BaseView;

public interface PatrolclockContract {
    interface View extends BaseView<Object> {

    }

    interface Presenter {

        void daka(String userid, String Latitude, String Longitude, String address,
                  String memo, String imageurl, String pid);
    }
}
