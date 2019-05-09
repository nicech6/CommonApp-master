package com.mvp_0726.project_0726.login.presenter;


import com.mvp_0726.common.base.codereview.BasePresenterImpl;
import com.mvp_0726.common.event.CommonEvent;
import com.mvp_0726.common.network.HttpObservable;
import com.mvp_0726.common.network.HttpResultObserver;
import com.mvp_0726.common.utils.Constans;
import com.mvp_0726.project_0726.login.contract.LoginContract;
import com.mvp_0726.project_0726.login.ui.SplashActivity;
import com.project.wisdomfirecontrol.firecontrol.model.bean.login.LoginChangeBean;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.disposables.Disposable;

/*公司信息*/
public class SplashPresenter extends BasePresenterImpl<LoginContract.View, SplashActivity> implements LoginContract.Presenter {
    public SplashPresenter(LoginContract.View view, SplashActivity activity) {
        super(view, activity);
    }

    @Override
    public void login(String userName, String pwd) {

        HttpObservable.getObservable(apiRetrofit.login(userName, pwd))
                .subscribe(new HttpResultObserver<LoginChangeBean>() {
                    @Override
                    protected void onLoading(Disposable d) {
//                        showLoadingDialog("加载中...");
                    }

                    @Override
                    protected void onSuccess(LoginChangeBean o) {
//                        dismissLoadingDialog();
                        if (getView() != null) {
                            if (o.isSuccess()) {
                                EventBus.getDefault().post(new CommonEvent(Constans.LOGIN, o.getData()));
                            } else {
                                EventBus.getDefault().post(new CommonEvent(Constans.RELOGIN, o.getData()));
                            }
                        }
                    }

                    @Override
                    protected void onFail(Exception e) {
//                        dismissLoadingDialog();
                        EventBus.getDefault().post(new CommonEvent(Constans.RELOGIN, e.toString()));
                    }
                });
    }

}
