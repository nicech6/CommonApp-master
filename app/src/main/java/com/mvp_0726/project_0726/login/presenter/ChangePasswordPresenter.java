package com.mvp_0726.project_0726.login.presenter;


import com.mvp_0726.common.base.codereview.BasePresenterImpl;
import com.mvp_0726.common.event.CommonEvent;
import com.mvp_0726.common.network.HttpObservable;
import com.mvp_0726.common.network.HttpResultObserver;
import com.mvp_0726.common.utils.Constans;
import com.mvp_0726.project_0726.login.contract.ChangePasswordContract;
import com.mvp_0726.project_0726.login.modle.ChangePwdBean;
import com.mvp_0726.project_0726.login.ui.ChangePasswordActivity;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.disposables.Disposable;

/*公司信息*/
public class ChangePasswordPresenter extends BasePresenterImpl<ChangePasswordContract.View, ChangePasswordActivity> implements ChangePasswordContract.Presenter {
    public ChangePasswordPresenter(ChangePasswordContract.View view, ChangePasswordActivity activity) {
        super(view, activity);
    }

    @Override
    public void updatePwd(String username, String oldPwd, String newPwd) {
        HttpObservable.getObservable(apiRetrofit.updatePwd(username, oldPwd,newPwd))
                .subscribe(new HttpResultObserver<ChangePwdBean>() {
                    @Override
                    protected void onLoading(Disposable d) {
                        showLoadingDialog("修改中...");
                    }

                    @Override
                    protected void onSuccess(ChangePwdBean o) {
                        dismissLoadingDialog();
                        if (getView() != null) {
                            if (o.isSuccess()) {
                                EventBus.getDefault().post(new CommonEvent(Constans.CHANPWD, o.getData()));
                            } else {
                                EventBus.getDefault().post(new CommonEvent(Constans.ERROR, o.getData()));
                            }
                        }
                    }

                    @Override
                    protected void onFail(Exception e) {
                        dismissLoadingDialog();
                        EventBus.getDefault().post(new CommonEvent(Constans.ERROR, "访问失败!"));
                    }
                });
    }
}
