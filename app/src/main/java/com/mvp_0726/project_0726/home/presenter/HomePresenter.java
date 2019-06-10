package com.mvp_0726.project_0726.home.presenter;


import com.mvp_0726.common.base.codereview.BasePresenterImpl;
import com.mvp_0726.common.event.CommonEvent;
import com.mvp_0726.common.network.HttpObservable;
import com.mvp_0726.common.network.HttpResultObserver;
import com.mvp_0726.common.utils.Constans;
import com.mvp_0726.project_0726.home.contract.HomeContract;
import com.mvp_0726.project_0726.home.model.GridCountBean;
import com.mvp_0726.project_0726.home.model.MarqueeBean;
import com.mvp_0726.project_0726.home.model.OrgandetailBean;
import com.mvp_0726.project_0726.home.ui.MvpMainActivity;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.EquipmentCount;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.disposables.Disposable;

/*公司信息*/
public class HomePresenter extends BasePresenterImpl<HomeContract.View, MvpMainActivity> implements HomeContract.Presenter {
    public HomePresenter(HomeContract.View view, MvpMainActivity activity) {
        super(view, activity);
    }

    //*轮播*//*
    @Override
    public void getMarqueeDatas(String pid) {
        HttpObservable.getObservable(apiRetrofit.getMarqueeDatas(pid))
                .subscribe(new HttpResultObserver<MarqueeBean>() {
                    @Override
                    protected void onLoading(Disposable d) {
                    }

                    @Override
                    protected void onSuccess(MarqueeBean o) {
                        if (getView() != null) {
                            if (o.isSuccess()) {
                                EventBus.getDefault().postSticky(new CommonEvent(Constans.MARQUEESUCESS, o.getData()));
                            } else {
                                EventBus.getDefault().postSticky(new CommonEvent(Constans.MARQUEEERROR, o.getMsg()));
                            }
                        }
                    }

                    @Override
                    protected void onFail(Exception e) {
                        EventBus.getDefault().postSticky(new CommonEvent(Constans.MARQUEEERROR, e.toString()));
                    }
                });
    }

    @Override
    public void getOrgandetailDatas(String pid) {
        HttpObservable.getObservable(apiRetrofit.getorgandetailbyid(pid))
                .subscribe(new HttpResultObserver<OrgandetailBean>() {
                    @Override
                    protected void onLoading(Disposable d) {
                    }

                    @Override
                    protected void onSuccess(OrgandetailBean o) {
                        if (getView() != null) {
                            if (o.isSuccess()) {
                                EventBus.getDefault().postSticky(new CommonEvent(Constans.COMPANYINFOSSUCESS, o.getData()));
                            } else {
                                EventBus.getDefault().postSticky(new CommonEvent(Constans.ERROR, o.getMsg()));
                            }
                        }
                    }

                    @Override
                    protected void onFail(Exception e) {
                        EventBus.getDefault().postSticky(new CommonEvent(Constans.ERROR, e.toString()));
                    }
                });
    }


    //设备备件数量
    @Override
    public void getEquipmentCount(String pid) {
        HttpObservable.getObservable(apiRetrofit.getequipmentcount(pid))
                .subscribe(new HttpResultObserver<EquipmentCount>() {
                    @Override
                    protected void onLoading(Disposable d) {
//                        showLoadingDialog("登录中...");
                    }

                    @Override
                    protected void onSuccess(EquipmentCount o) {
//                        dismissLoadingDialog();
                        if (getView() != null) {
                            if (o.isSuccess()) {
                                EventBus.getDefault().postSticky(new CommonEvent(Constans.EQUIPMENTCOUNTSUCESS, o.getData()));
                            } else {
                                EventBus.getDefault().postSticky(new CommonEvent(Constans.ERROR, o.getMsg()));
                            }
                        }
                    }

                    @Override
                    protected void onFail(Exception e) {
//                        dismissLoadingDialog();
                        EventBus.getDefault().postSticky(new CommonEvent(Constans.ERROR, e.toString()));
                    }
                });
    }


    //获取角标数量
    @Override
    public void getAppNum(String pid, String id) {
        HttpObservable.getObservable(apiRetrofit.getAppNum(pid, id))
                .subscribe(new HttpResultObserver<GridCountBean>() {
                    @Override
                    protected void onLoading(Disposable d) {
                        showLoadingDialog("加载数据中.");
                    }

                    @Override
                    protected void onSuccess(GridCountBean o) {
                        dismissLoadingDialog();
                        if (getView() != null) {
                            if (o.getStatus() == 10000) {
                                EventBus.getDefault().postSticky(new CommonEvent(Constans.GETAPPNUMSUCESS, o.getResult()));
                            } else {
                                EventBus.getDefault().postSticky(new CommonEvent(Constans.GETAPPNUMERROR, ""));
                            }
                        }
                    }

                    @Override
                    protected void onFail(Exception e) {
                        dismissLoadingDialog();
                        EventBus.getDefault().postSticky(new CommonEvent(Constans.GETAPPNUMERROR, e.toString()));
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        dismissLoadingDialog();
                    }
                });
    }

    @Override
    public void getdanweiNum(String pid) {

    }

    @Override
    public void getClientEquipmentCount(String personId) {

    }
}
