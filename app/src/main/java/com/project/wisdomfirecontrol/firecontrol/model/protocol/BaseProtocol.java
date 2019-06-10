package com.project.wisdomfirecontrol.firecontrol.model.protocol;

import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.firecontrol.base.MyApplication;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 网络请求基类
 *
 * @author LMX
 */
public class BaseProtocol {

    private String message;

    IHttpService getHttpService() {
        return RetrofitManager.getInstance().getHttpService();
    }

    /**
     * 执行网络请求
     */
    private <T> void execute(Call<JsonObject> call, final OnHttpCallback callback,
                             final int reqType, final Class<T> clazz, final int what) {
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    String json = response.body().toString();
                    Log.d("tag", " onResponse:" + json);
                    // 手动解析json字符串
                    JSONObject obj = new JSONObject(json);
                    // 获取状态码
                    int status = obj.getInt("status");
                    if (10000 == status) {      // 请求成功
                        Gson gson = new Gson();
                        T bean = gson.fromJson(json, clazz);

                        if (callback != null) {
                            Message msg = new Message();
                            msg.what = what;    // 用来作请求标识的变量（通常情况下变量用不到）
                            msg.obj = bean;     // 实体数据
                            callback.onHttpSuccess(reqType, msg);
                        }
                    } else {    // 请求失败
                        String error = obj.getString("data");
                        if (what == 7) {
                            error = "暂无数据";
                        }
                        if (callback != null) {
                            callback.onHttpError(reqType, error);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callback != null) {
                        callback.onHttpError(reqType, e.getMessage());

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                message = t.getMessage();
                if (callback != null) {
                    boolean networkConnected = MyApplication.getInstance().isNetworkConnected();
                    if (networkConnected) {
                        if (TextUtils.isEmpty(message)) {
                            message = Global.mContext.getResources().getString(R.string.service_error);
                        } else if (message.contains("timeout")) {
                            message = "请重试";
                        } else if (message.contains("a")) {
                            message = Global.mContext.getResources().getString(R.string.service_error);
                        }
                        callback.onHttpError(reqType, message);
                    } else {
                        callback.onHttpError(reqType, Global.mContext.getResources().getString(R.string.no_net));
                    }
                }
            }
        });
    }

    /**
     * 执行网络请求
     */
    public <T> void execute(Call<JsonObject> call, final OnHttpCallback callback,
                            final int reqType, final Class<T> clazz) {
        execute(call, callback, reqType, clazz, 0);  // what: 指定默认值0
    }

    /**
     * 执行网络请求
     */
    private <T> void execute1(Call<JsonObject> call, final OnHttpCallback callback,
                              final int reqType, final Class<T> clazz, final int what) {
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    String json = response.body().toString();
                    Log.d("tag", " onResponse:" + json);
                    // 手动解析json字符串
                    JSONObject obj = new JSONObject(json);
                    // 获取状态码
                    int status = obj.getInt("status");
                    if (status == 10000) {      // 请求成功
                        Gson gson = new Gson();
                        T bean = gson.fromJson(json, clazz);

                        if (callback != null) {
                            Message msg = new Message();
                            msg.what = what;    // 用来作请求标识的变量（通常情况下变量用不到）
                            msg.obj = bean;     // 实体数据
                            callback.onHttpSuccess(reqType, msg);
                        }
                    } else {    // 请求失败
//                        String error = obj.getString("data");
                        if (callback != null) {
                            callback.onHttpError(reqType, "加载失败");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callback != null) {
                        callback.onHttpError(reqType, e.getMessage());

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                message = t.getMessage();
                if (callback != null) {
                    boolean networkConnected = MyApplication.getInstance().isNetworkConnected();
                    if (networkConnected) {
                        if (TextUtils.isEmpty(message)) {
                            message = Global.mContext.getResources().getString(R.string.service_error);
                        } else if (message.contains("timeout")) {
                            message = "请重试";
                        } else if (message.contains("a")) {
                            message = Global.mContext.getResources().getString(R.string.service_error);
                        }
                        callback.onHttpError(reqType, message);
                    } else {
                        callback.onHttpError(reqType, Global.mContext.getResources().getString(R.string.no_net));
                    }
                }
            }
        });
    }

    /**
     * 执行网络请求
     */
    public <T> void execute1(Call<JsonObject> call, final OnHttpCallback callback,
                             final int reqType, final Class<T> clazz) {
        execute1(call, callback, reqType, clazz, 0);  // what: 指定默认值0
    }


    /**
     * 网络请求回调接口
     */
    public interface OnHttpCallback {
        /**
         * 请求成功
         *
         * @param reqType 请求类型标识
         * @param obj     返回的实体对象
         */
        public void onHttpSuccess(int reqType, Message obj);

        /**
         * 请求失败
         *
         * @param reqType 请求类型标识
         * @param error   失败原因
         */
        public void onHttpError(int reqType, String error);
    }

}
