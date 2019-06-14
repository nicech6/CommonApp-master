package com.project.wisdomfirecontrol.common.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mvp_0726.common.utils.AppManager;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.util.StatusBarUtil;
import com.project.wisdomfirecontrol.common.util.Utils;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.BaseProtocol;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.IHttpService;
import com.project.wisdomfirecontrol.firecontrol.ui.view.DialogHelper;
import com.project.wisdomfirecontrol.firecontrol.ui.view.dialog.SuccessDialog;
import com.project.wisdomfirecontrol.firecontrol.ui.view.dialog.WaitDialog;
import com.project.wisdomfirecontrol.firecontrol.ui.view.toast.T;

import java.util.HashMap;
import java.util.Map;


/**
 * Activity基类，所有的Activity都需要继承此类。
 * 封装： 查看子控件，设置监听器，初始化数据，
 * toast, showDialog, showProgressDialog等方法
 *
 * @author LMX
 */
public abstract class BaseActivity extends AppCompatActivity
        implements IUIOperation, BaseProtocol.OnHttpCallback {

    public TextView title;
    private LinearLayout llTitle;
    private static boolean _isVisible = false;
    @SuppressLint("StaticFieldLeak")
    private static WaitDialog _waitDialog;

    // 权限管理
    private Map<Integer, Runnable> allowablePermissionRunnables = new HashMap<>();
    private Map<Integer, Runnable> disallowablePermissionRunnables = new HashMap<>();
    private SuccessDialog successDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        AppManager.getAppManager().addActivity(this);
        setContentView(getLayoutRes());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // 系统的一个根布局，可以查找到activity布局的所有的子控件
        View root = findViewById(android.R.id.content);
        title = findView(R.id.tv_title);
        llTitle = findView(R.id.ll_title);
        llTitle.setBackgroundColor(getResources().getColor(R.color.mvp_title_blue));
        if (title != null) {
            title.setBackgroundColor(getResources().getColor(R.color.mvp_title_blue));
            title.setTextColor(getResources().getColor(R.color.white));
        }

        // 查找activity布局中所有的Button（ImageButton），并设置点击事件
        Utils.findButtonAndSetOnClickListener(root, this);

        initView();
        initListener();
        initData();
        StatusBarUtil.immersive(this, getResources().getColor(R.color.mvp_title_blue));
    }

    @Override
    public void initTitleHeight(LinearLayout linearLayout) {
        int statusBarHeight = Global.getStatusBarHeight(this);
        ViewGroup.LayoutParams params = linearLayout.getLayoutParams();

        params.height = (statusBarHeight + 45);

        linearLayout.setLayoutParams(params);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 查找子控件，可以省略强转
     */
    public <T> T findView(int id) {
        @SuppressWarnings("unchecked")
        T view = (T) findViewById(id);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            default:
                onClick(v, v.getId());
                break;
        }
    }

    public void showToast(String text) {
        Global.showToast(text);
    }
    public void showLongToast(String text) {
        Global.showLongToast(text);
    }

    public void showAnimToast(String text) {
        T.showAnimToast(this, text);
    }

    private ProgressDialog mPDialog;

    /**
     * 显示加载提示框(不能在子线程调用)
     */
    public void showProgressDialog(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPDialog = new ProgressDialog(BaseActivity.this);
                mPDialog.setMessage(message);
                // 点击外部时不销毁
                mPDialog.setCanceledOnTouchOutside(false);

                // activity如果正在销毁或已销毁，不能show Dialog，否则出错。
                if (!isFinishing())
                    mPDialog.show();
            }
        });
    }

    public SuccessDialog showSuccessDialog(Activity activity, String txt) {
        if (successDialog == null) {
            successDialog = new SuccessDialog(activity);
        }
        successDialog.setContent(txt);
        successDialog.hideCancle();
        successDialog.setDialogCallback(new SuccessDialog.Dialogcallback() {
            @Override
            public void dialogdo(LinearLayout container) {
                finish();
            }

            @Override
            public void dialogcancle() {
                successDialog.dismiss();
            }
        });
        if (!isFinishing()) {
            successDialog.show();
        }
        return null;
    }

    public WaitDialog showWaitDialog(Activity activity, String message) {
        if (!_isVisible) {
            if (_waitDialog == null) {
                _waitDialog = DialogHelper.getWaitDialog(activity, message);
            }
            if (_waitDialog != null) {
                _waitDialog.setMessage(message);
                if (!isFinishing()) {
                    _waitDialog.show();
                }
                _isVisible = true;
            }
            return _waitDialog;
        }
        return null;
    }

    public void dismissWaitDialog() {
        if (_isVisible && _waitDialog != null) {
            try {
                if (!isFinishing())
                    _waitDialog.dismiss();
                _waitDialog = null;
                _isVisible = false;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    protected void setPageTitle(String title) {
        TextView tvTitle = findView(R.id.tv_title);
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    /**
     * 销毁加载提示框
     */
    public void dismissProgressDialog() {
        if (mPDialog != null) {
            mPDialog.dismiss();
            mPDialog = null;
        }
    }

    /**
     * 显示对话框
     *
     * @param title    标题
     * @param message  内容
     * @param listener 回调监听器
     */
    public void showDialog(String title, String message,
                           final OnDialogClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onConfirm(dialog);
                        }
                    }
                });
        builder.setNegativeButton("取消", null);
        if (!isFinishing())
            builder.create().show();
    }

    /**
     * 提示对话框
     */
    public void showDialog(String title, String message) {
        showDialog(title, message, null);
    }

    /**
     * 提示对话框
     */
    public void showDialog(String message) {
        showDialog("", message, null);
    }

    /**
     * 对话框点击回调
     */
    public interface OnDialogClickListener {

        /**
         * 确定
         */
        public void onConfirm(DialogInterface dialog);

        /**
         * 取消
         */
        public void onCancel(DialogInterface dialog);
    }

    // 网络请求成功
    @Override
    public void onHttpSuccess(int reqType, Message obj) {
    }

    // 网络请求失败
    @Override
    public void onHttpError(int reqType, String error) {
        // 提示出错对话框
        if (reqType != IHttpService.TYPE_TROUBLE_TYPE_NUM) {
            showDialog(error);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        overridePendingTransitionExit();
        super.startActivity(intent);
    }

    @Override
    public void finish() {
        overridePendingTransitionExit();
        super.finish();
    }

    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.modal_in, R.anim.modal_out);
    }

    /**
     * 请求权限
     *
     * @param id                   请求授权的id 唯一标识即可
     * @param permission           请求的权限
     * @param allowableRunnable    同意授权后的操作
     * @param disallowableRunnable 禁止权限后的操作
     */
    public void requestPermission(int id, String permission, Runnable allowableRunnable, Runnable disallowableRunnable) {
        if (allowableRunnable == null) {
            throw new IllegalArgumentException("allowableRunnable == null");
        }

        allowablePermissionRunnables.put(id, allowableRunnable);
        if (disallowableRunnable != null) {
            disallowablePermissionRunnables.put(id, disallowableRunnable);
        }

        //版本判断
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //减少是否拥有权限
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getApplicationContext(), permission);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                //弹出对话框接收权限
                ActivityCompat.requestPermissions(BaseActivity.this, new String[]{permission}, id);
            } else {
                allowableRunnable.run();
            }
        } else {
            allowableRunnable.run();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Runnable allowRun = allowablePermissionRunnables.get(requestCode);
            allowRun.run();
        } else {
            Runnable disallowRun = disallowablePermissionRunnables.get(requestCode);
            disallowRun.run();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}



















