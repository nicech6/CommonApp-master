package com.project.wisdomfirecontrol.firecontrol.ui.holder_Lv;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.ui.BaseAdapterLV;
import com.project.wisdomfirecontrol.common.ui.BaseHolderLV;

/**
 * Created by Administrator on 2018/4/3.
 */

public class StringDatasLvHolder extends BaseHolderLV<String> {

    private TextView tv_item_type_name;

    public StringDatasLvHolder(Context context, ViewGroup parent, BaseAdapterLV<String> adapter, int position, String bean) {
        super(context,parent,adapter,position,bean);
    }

    @Override
    public View onCreateView(Context context, ViewGroup parent) {
        View itemView = Global.inflate(R.layout.lv_item_string, parent);
        tv_item_type_name = itemView.findViewById(R.id.tv_item_type_name);
        return itemView;
    }

    @Override
    protected void onRefreshView(String bean, int position) {
        if (!TextUtils.isEmpty(bean)) {
            tv_item_type_name.setText(bean);
        }
    }
}
