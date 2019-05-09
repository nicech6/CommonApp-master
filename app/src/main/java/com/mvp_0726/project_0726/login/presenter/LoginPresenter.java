package com.mvp_0726.project_0726.login.presenter;


import com.mvp_0726.common.base.codereview.BasePresenterImpl;
import com.mvp_0726.common.event.CommonEvent;
import com.mvp_0726.common.network.HttpObservable;
import com.mvp_0726.common.network.HttpResultObserver;
import com.mvp_0726.common.utils.Constans;
import com.mvp_0726.project_0726.login.contract.LoginContract;
import com.mvp_0726.project_0726.login.ui.LoginActivity;
import com.project.wisdomfirecontrol.firecontrol.model.bean.login.LoginChangeBean;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.disposables.Disposable;

/*公司信息*/
public class LoginPresenter extends BasePresenterImpl<LoginContract.View, LoginActivity> implements LoginContract.Presenter {
    public LoginPresenter(LoginContract.View view, LoginActivity activity) {
        super(view, activity);
    }

    @Override
    public void login(String userName, String pwd) {

        HttpObservable.getObservable(apiRetrofit.login(userName, pwd))
                .subscribe(new HttpResultObserver<LoginChangeBean>() {

                    @Override
                    protected void onLoading(Disposable d) {
                        showLoadingDialog("登录中...");
                    }

                    @Override
                    protected void onSuccess(LoginChangeBean o) {
                        dismissLoadingDialog();
                        if (getView() != null) {
                            if (o.isSuccess()) {
                                EventBus.getDefault().post(new CommonEvent(Constans.LOGIN, o.getData()));
                            } else {
                                EventBus.getDefault().post(new CommonEvent(Constans.ERROR, o.getData()));
                            }
                        }
                    }

                    @Override
                    protected void onFail(Exception e) {
                        dismissLoadingDialog();
                        EventBus.getDefault().post(new CommonEvent(Constans.ERROR, "请检查账号、密码或网络!"));
                    }
                });
    }

}
