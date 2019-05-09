package com.mvp_0726.project_0726.online_unit.presenter;


import android.util.Log;

import com.mvp_0726.common.base.codereview.BasePresenterImpl;
import com.mvp_0726.common.event.CommonEvent;
import com.mvp_0726.common.network.HttpObservable;
import com.mvp_0726.common.network.HttpResultObserver;
import com.mvp_0726.common.utils.Constans;
import com.mvp_0726.project_0726.bean.settingpolice.GetsensorObdSuccessBean;
import com.mvp_0726.project_0726.online_unit.contract.SettingOnlinePoliceContract;
import com.mvp_0726.project_0726.online_unit.event.SettingFragmentEvent;
import com.mvp_0726.project_0726.online_unit.event.SettingPoliceEvent;
import com.mvp_0726.project_0726.online_unit.ui.activity.SettingPoliceOnlineActivity;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.EquipmentCount;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.GetSenorcountBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.setting.SettingManagerBean;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.disposables.Disposable;

public class SettingOnlinePolicePresenter extends BasePresenterImpl<SettingOnlinePoliceContract.View,
        SettingPoliceOnlineActivity> implements SettingOnlinePoliceContract.Presenter {
    public SettingOnlinePolicePresenter(SettingOnlinePoliceContract.View view, SettingPoliceOnlineActivity activity) {
        super(view, activity);
    }

    //设备备件数量
    @Override
    public void getEquipmentCount(String pid) {
        HttpObservable.getObservable(apiRetrofit.getequipmentcount(pid))
                .subscribe(new HttpResultObserver<EquipmentCount>() {
                    @Override
                    protected void onLoading(Disposable d) {
                        showLoadingDialog("加载中...");
                    }

                    @Override
                    protected void onSuccess(EquipmentCount o) {
                        dismissLoadingDialog();
                        if (getView() != null) {
                            if (o.isSuccess()) {
                                EventBus.getDefault().post(new CommonEvent(Constans.EQUIPMENTCOUNTSUCESS, o.getData()));
                            } else {
                                EventBus.getDefault().post(new CommonEvent(Constans.ERROR, o.getMsg()));
                            }
                        }
                    }

                    @Override
                    protected void onFail(Exception e) {
                        dismissLoadingDialog();
                        EventBus.getDefault().post(new CommonEvent(Constans.ERROR, e.toString()));
                    }
                });
    }

    @Override
    public void getSenorcount(String pid, String state) {
        Log.d("tag", "getSenorcount: " + pid + " == " + state);
        HttpObservable.getObservable(apiRetrofit.getSenorcount(pid, state))
                .subscribe(new HttpResultObserver<GetSenorcountBean>() {
                    @Override
                    protected void onLoading(Disposable d) {
//                        showLoadingDialog("加载中...");
                    }

                    @Override
                    protected void onSuccess(GetSenorcountBean o) {
//                        dismissLoadingDialog();
                        if (getView() != null) {
                            if (o.isSuccess()) {
                                EventBus.getDefault().post(new SettingPoliceEvent(Constans.SETTINGSUCESS, o.getData()));
                            } else {
                                EventBus.getDefault().post(new SettingPoliceEvent(Constans.ERROR, o.getMsg()));
                            }
                        }
                    }

                    @Override
                    protected void onFail(Exception e) {
//                        dismissLoadingDialog();
                        EventBus.getDefault().post(new SettingPoliceEvent(Constans.ERROR, e.toString()));
                    }
                });

    }

    @Override
    public void getSensor(String pid, String type, String state, String ishave, final int successWhat,final int errorWhat) {
        HttpObservable.getObservable(apiRetrofit.getSensor(pid, type, state, ishave))
                .subscribe(new HttpResultObserver<SettingManagerBean>() {
                    @Override
                    protected void onLoading(Disposable d) {
                        showLoadingDialog("加载中...");
                    }

                    @Override
                    protected void onSuccess(SettingManagerBean o) {
                        dismissLoadingDialog();
                        if (getView() != null) {
                            if (o.isSuccess()) {
                                EventBus.getDefault().post(new SettingFragmentEvent(successWhat, o.getData()));
                            } else {
                                EventBus.getDefault().post(new SettingFragmentEvent(errorWhat, o.getMsg()));
                            }
                        }
                    }

                    @Override
                    protected void onFail(Exception e) {
                        dismissLoadingDialog();
                        EventBus.getDefault().post(new SettingFragmentEvent(errorWhat, e.toString()));
                    }
                });
    }


    @Override
    public void getsensorObd(String terminalNo) {
        HttpObservable.getObservable(apiRetrofit.getsensorObd(terminalNo))
                .subscribe(new HttpResultObserver<GetsensorObdSuccessBean>() {
                    @Override
                    protected void onLoading(Disposable d) {
//                        showLoadingDialog("加载中...");
                    }

                    @Override
                    protected void onSuccess(GetsensorObdSuccessBean o) {
//                        dismissLoadingDialog();
                        if (getView() != null) {
                            if (o.isSuccess()) {
                                EventBus.getDefault().post(new SettingPoliceEvent(Constans.OBD_SUCCESS, o.getData()));
                            } else {
                                EventBus.getDefault().post(new SettingPoliceEvent(Constans.OBD_ERROR, o.getMsg()));
                            }
                        }
                    }

                    @Override
                    protected void onFail(Exception e) {
//                        dismissLoadingDialog();
                        EventBus.getDefault().post(new SettingPoliceEvent(Constans.OBD_ERROR, e.toString()));
                    }
                });

    }
}
