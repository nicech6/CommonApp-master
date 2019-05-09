package com.mvp_0726.project_0726.home.presenter;


import com.mvp_0726.common.base.codereview.BasePresenterImpl;
import com.mvp_0726.common.event.CommonEvent;
import com.mvp_0726.common.network.HttpObservable;
import com.mvp_0726.common.network.HttpResultObserver;
import com.mvp_0726.common.utils.Constans;
import com.mvp_0726.project_0726.home.contract.PatrolclockContract;
import com.mvp_0726.project_0726.home.ui.MvpMainActivity;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.SubmitBean;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.disposables.Disposable;

public class PatrolockPresenter extends BasePresenterImpl<PatrolclockContract.View, MvpMainActivity> implements PatrolclockContract.Presenter {
    public PatrolockPresenter(PatrolclockContract.View view, MvpMainActivity activity) {
        super(view, activity);
    }

    @Override
    public void daka(String userid, String Latitude, String Longitude, String address,
                     String memo, String imageurl, String pid) {
        HttpObservable.getObservable(apiRetrofit.daka(userid,Latitude,Longitude,address,memo,pid,imageurl))
                .subscribe(new HttpResultObserver<SubmitBean>() {
                    @Override
                    protected void onLoading(Disposable d) {
                        showLoadingDialog("打卡中...");
                    }

                    @Override
                    protected void onSuccess(SubmitBean o) {
                        dismissLoadingDialog();
                        if (getView() != null) {
                            if (o.isSuccess()) {
                                EventBus.getDefault().post(new CommonEvent(Constans.PATROLOCKSUCCESS, o.getData()));
                            } else {
                                EventBus.getDefault().post(new CommonEvent(Constans.PATROLOCKERROR, o.getMsg()));
                            }
                        }
                    }

                    @Override
                    protected void onFail(Exception e) {
                        dismissLoadingDialog();
                        EventBus.getDefault().post(new CommonEvent(Constans.PATROLOCKERROR, e.toString()));
                    }
                });
    }
}
