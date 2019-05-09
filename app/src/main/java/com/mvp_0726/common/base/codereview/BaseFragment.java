package com.mvp_0726.common.base.codereview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvp_0726.common.utils.ToastUtils;

/**
 * Created  on 2018-01-05.
 * author:seven
 * email:seven2016s@163.com
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    public View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
        }

        initView();
        initData();
        setLisenter();
        return rootView;
    }



//    protected abstract void initView(View rootView, Bundle savedInstanceState);

    /*
   * 带图片的toast
   * */
    public void showSuccessToast(String msg) {
        ToastUtils.success(msg);
    }

    /*
    * error的toast
    * */
    public void showErrorToast(String msg) {
        ToastUtils.error(msg);
    }

    protected abstract int getLayoutId();

    protected void initView() {

    }


    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public abstract boolean onKeyDown(int keyCode, KeyEvent event);

    protected abstract void initData();

    protected abstract void setLisenter();

    protected abstract void widgetClick(View v);

    @Override
    public void onClick(View v) {
        widgetClick(v);
    }
}
