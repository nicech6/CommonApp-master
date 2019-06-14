package com.mvp_0726.service;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bean.BaojingDialogBean;
import com.client.BaojingEvent;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;
import com.mvp_0726.common.Model.FileBean;
import com.mvp_0726.common.event.CommonEvent;
import com.mvp_0726.common.network.ApiRetrofit;
import com.mvp_0726.common.network.ApiService;
import com.mvp_0726.common.network.HttpObservable;
import com.mvp_0726.common.network.HttpResultObserver;
import com.mvp_0726.common.utils.Constans;
import com.mvp_0726.common.utils.GsonTools;
import com.mvp_0726.project_0726.home.model.MarqueeBean;
import com.mvp_0726.project_0726.home.model.OrgandetailBean;
import com.mvp_0726.project_0726.home.ui.MvpThirdMainActivity;
import com.mvp_0726.project_0726.utils.StringUtils;
import com.mvp_0726.project_0726.web.ui.WebH5Activity;
import com.project.wisdomfirecontrol.firecontrol.model.bean.login.LoginChangeBean;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_setting.ChangerSettingActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.view.dialog.SuccessDialog;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.disposables.Disposable;
import okhttp3.Response;

public class BaojingService extends Service {
    public static final String TAG = "BaojingService";
    private Handler handler = new Handler();
    private Runnable task = new Runnable() {
        public void run() {
            // TODO Auto-generated method stub
            handler.postDelayed(this, 10 * 1000);//设置循环时间，此处是10秒
            //需要执行的代码
            Log.d(TAG, "task");

            HttpObservable.getObservable(ApiRetrofit.getApiRetrofit().getApiServis().getBaojingDialog("yun"))
                    .subscribe(new HttpResultObserver<BaojingDialogBean>() {

                        @Override
                        protected void onLoading(Disposable d) {

                        }

                        @Override
                        protected void onSuccess(BaojingDialogBean o) {
                            if (o != null && o.getData() != null && o.getData().size() > 0) {
                                for (int i = 0; i < o.getData().size(); i++) {
                                    if ("2".equals(o.getData().get(i).getState())) {
                                        EventBus.getDefault().post(new BaojingEvent(getUrl(o.getData().get(i).getId())));
                                    }
                                }
                            }
                        }

                        @Override
                        protected void onFail(Exception e) {

                        }
                    });
        }
    };

    public String getUrl(String id) {
        String url = "";
        url = "http://www.zgjiuan.cn/sensorQY/showcall110.action?sensorid=" + id + "&title=报警信息";
        return url;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        handler.post(task);//立即调用
        return super.onStartCommand(intent, flags, startId);
    }
}
