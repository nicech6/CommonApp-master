package com.mvp_0726.project_0726.web.presenter;


import com.mvp_0726.common.base.codereview.BasePresenterImpl;
import com.mvp_0726.common.event.CommonEvent;
import com.mvp_0726.common.network.HttpObservable;
import com.mvp_0726.common.network.HttpResultObserver;
import com.mvp_0726.common.utils.Constans;
import com.mvp_0726.project_0726.web.contract.WebH5Contract;
import com.mvp_0726.project_0726.web.model.GetSumTypeBean;
import com.mvp_0726.project_0726.web.ui.WebH5Activity;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.disposables.Disposable;

/*公司信息*/
public class WebH5Presenter extends BasePresenterImpl<WebH5Contract.View, WebH5Activity> implements WebH5Contract.Presenter {
    public WebH5Presenter(WebH5Contract.View view, WebH5Activity activity) {
        super(view, activity);
    }


    //获取角标数量
    @Override
    public void getSumType(String pid, String id) {
        HttpObservable.getObservable(apiRetrofit.getSumType(pid, id))
                .subscribe(new HttpResultObserver<GetSumTypeBean>() {
                    @Override
                    protected void onLoading(Disposable d) {
//                        showLoadingDialog("加载数据中.");
                    }

                    @Override
                    protected void onSuccess(GetSumTypeBean o) {
//                        dismissLoadingDialog();
                        if (getView() != null) {
                            if (o.getStatus() == 10000) {
                                EventBus.getDefault().post(new CommonEvent(Constans.GETSUMTYPENUMSUCESS, o.getResult()));
                            } else {
                                EventBus.getDefault().post(new CommonEvent(Constans.GETSUMTYPENUMERROR, ""));
                            }
                        }
                    }

                    @Override
                    protected void onFail(Exception e) {
//                        dismissLoadingDialog();
                        EventBus.getDefault().post(new CommonEvent(Constans.GETSUMTYPENUMERROR, e.toString()));
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
//                        dismissLoadingDialog();
                    }
                });
    }
}
