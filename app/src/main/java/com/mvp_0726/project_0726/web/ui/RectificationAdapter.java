package com.mvp_0726.project_0726.web.ui;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.firecontrol.model.bean.document.DocumentsBean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/30.
 */

public class RectificationAdapter extends BaseQuickAdapter<DocumentsBean, BaseViewHolder> {

    public RectificationAdapter(int layoutResId) {
        super(layoutResId);
    }

    public RectificationAdapter(int layoutResId, @Nullable List<DocumentsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DocumentsBean item) {

        helper.setText(R.id.tv_item_des, item.getTitle())
                .setText(R.id.tv_item_time, item.getSendtime())
                .addOnClickListener(R.id.ll_item_document);
    }
}
