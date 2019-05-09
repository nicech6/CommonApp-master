package com.project.wisdomfirecontrol.common.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.util.Utils;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.BaseProtocol;
import com.project.wisdomfirecontrol.firecontrol.ui.view.DialogHelper;
import com.project.wisdomfirecontrol.firecontrol.ui.view.dialog.SuccessDialog;
import com.project.wisdomfirecontrol.firecontrol.ui.view.dialog.WaitDialog;
import com.project.wisdomfirecontrol.firecontrol.ui.view.toast.T;

/**
 * Fragment基类，所有的Fragment都需要继承此类。
 * 封装查看子控件，设置监听器，初始化数据
 *
 * @author LMX
 */
public abstract class BaseFragment extends Fragment
        implements IUIOperation, BaseProtocol.OnHttpCallback {

    /**
     * 管理Fragment的Activity
     */
    public BaseActivity mActivity;
    private SuccessDialog successDialog;
    private static boolean _isVisible = false;
    private static WaitDialog _waitDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化Activity对象
        mActivity = (BaseActivity) getActivity();
    }

    /**
     * Fragment显示的布局
     */
    public View mRoot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRoot == null) {
            mRoot = inflater.inflate(getLayoutRes(), null);

            // 查找布局中的所有的button(ImageButton),并设置点击事件
            Utils.findButtonAndSetOnClickListener(mRoot, this);

            initView();
            initListener();
            initData();

        } else {
            // 解除mRoot与其父控件的关系
            unbindWidthParent(mRoot);
        }

        return mRoot;
    }

    /**
     * 设置界面标题
     */
    protected void setPageTitle(String title) {
        TextView tvTitle = findView(R.id.tv_title);
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    /**
     * 解除父子控件关系
     *
     * @param view
     */
    public void unbindWidthParent(View view) {
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
    }

    /**
     * 查找子控件，可以省略强转
     */
    public <T> T findView(int id) {
        @SuppressWarnings("unchecked")
        T view = (T) mRoot.findViewById(id);
        return view;
    }


    @Override
    public void onClick(View v) {
        onClick(v, v.getId());
    }

    public void showToast(String text) {
        Global.showToast(text);
    }

    public void showAnimToast(String text) {
        T.showAnimToast(mActivity, text);
    }

    // 网络请求成功
    @Override
    public void onHttpSuccess(int reqType, Message obj) {
    }

    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public abstract boolean onKeyDown(int keyCode, KeyEvent event);

    // 网络请求失败
    @Override
    public void onHttpError(int reqType, String error) {
        mActivity.dismissProgressDialog();
        // 提示出错对话框
//        mActivity.showDialog("提示", error);
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
                mActivity.finish();
            }

            @Override
            public void dialogcancle() {
                successDialog.dismiss();
            }
        });
        if (!mActivity.isFinishing()){
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
                if (!mActivity.isFinishing()) {
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
                if (!mActivity.isFinishing())
                    _waitDialog.dismiss();
                _waitDialog = null;
                _isVisible = false;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

