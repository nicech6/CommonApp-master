package com.mvp_0726.common.base;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.mvp_0726.common.base.codereview.BaseFragment;
import com.mvp_0726.common.base.codereview.BasePresenter;


public abstract class BaseMvpFragment<P extends BasePresenter, M extends IBaseModel> extends BaseFragment implements IBaseView {
    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    public void startNewActivityForResult(@NonNull Class<?> clz, Bundle bundle, int requestCode) {

    }

    @Override
    public void startNewActivity(@NonNull Class<?> clz) {

    }

    @Override
    public void startNewActivity(@NonNull Class<?> clz, Bundle bundle) {

    }

   /* @Override
    public void showToast(String msg) {

    }

    @Override
    public void back() {

    }*/
}
