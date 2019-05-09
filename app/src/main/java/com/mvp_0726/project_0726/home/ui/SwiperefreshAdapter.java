package com.mvp_0726.project_0726.home.ui;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.wisdomfirecontrol.R;

import java.util.List;

/**
 * Created by Administrator on 2018/7/30.
 */

public class SwiperefreshAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SwiperefreshAdapter(int layoutResId) {
        super(layoutResId);
    }

    public SwiperefreshAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String txt) {

        helper.setText(R.id.tv_item_type_name, txt)
                .addOnClickListener(R.id.ll_item_des);
    }
}
