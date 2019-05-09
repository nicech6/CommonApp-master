package com.project.wisdomfirecontrol.firecontrol.ui.holder_Lv;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mvp_0726.common.utils.Global;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.ui.BaseAdapterLV;
import com.project.wisdomfirecontrol.common.ui.BaseHolderLV;
import com.project.wisdomfirecontrol.firecontrol.model.bean.document.DocumentsBean;

/**
 * Created by Administrator on 2018/3/22.
 */

public class RectificationDocumentHolder extends BaseHolderLV<DocumentsBean> {

    private TextView tv_item_des, tv_item_time;
    private View itemView;

    public RectificationDocumentHolder(Context context, ViewGroup parent,
                                       BaseAdapterLV<DocumentsBean> adapter,
                                       int position, DocumentsBean bean) {
        super(context, parent, adapter, position, bean);
    }

    @Override
    public View onCreateView(Context context, ViewGroup parent) {
        itemView = Global.inflate(R.layout.lv_item_document, parent);
        tv_item_des = itemView.findViewById(R.id.tv_item_des);
        tv_item_time = itemView.findViewById(R.id.tv_item_time);
        return itemView;
    }

    @Override
    protected void onRefreshView(DocumentsBean bean, int position) {
        Log.d("tag", "onRefreshView: " + bean.getSendtime());
        String title = bean.getTitle();
        String sendtime = bean.getSendtime();
        tv_item_des.setText(title);
        tv_item_time.setText(sendtime);
    }

}
