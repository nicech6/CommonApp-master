package com.project.wisdomfirecontrol.firecontrol.ui.adapter_Rv;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

import com.project.wisdomfirecontrol.common.ui.BaseAdapterLV;
import com.project.wisdomfirecontrol.common.ui.BaseHolderLV;
import com.project.wisdomfirecontrol.firecontrol.model.bean.document.DocumentsBean;
import com.project.wisdomfirecontrol.firecontrol.ui.holder_Lv.RectificationDocumentHolder;

/**
 * Created by Administrator on 2018/3/22.
 */

public class RectificationDocumentAdaper extends BaseAdapterLV<DocumentsBean> {

    public RectificationDocumentAdaper(Context context, List<DocumentsBean> listData) {
        super(context, listData);
    }

    @Override
    public BaseHolderLV<DocumentsBean> createViewHolder(Context context, ViewGroup parent,DocumentsBean bean, int position) {
        return new RectificationDocumentHolder(context, parent, this, position, bean);
    }
}
