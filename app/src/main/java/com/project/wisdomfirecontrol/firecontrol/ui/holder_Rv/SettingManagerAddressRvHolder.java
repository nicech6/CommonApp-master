package com.project.wisdomfirecontrol.firecontrol.ui.holder_Rv;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.ui.BaseAdapterLV;
import com.project.wisdomfirecontrol.common.ui.BaseHolderLV;

/**
 * Created by Administrator on 2018/4/3.
 */

public class SettingManagerAddressRvHolder extends BaseHolderLV<String> {

    private TextView tv_item_type_name, tv_item_name, tv_item_type;
    public TextView tv_item_time;
    public CheckBox view_time;

    public SettingManagerAddressRvHolder(Context context, ViewGroup parent, BaseAdapterLV<String> adapter, int position, String bean) {
        super(context, parent, adapter, position, bean);
    }

    @Override
    public View onCreateView(Context context, ViewGroup parent) {
        View itemView = Global.inflate(R.layout.lv_item_settring_add, parent);
        view_time = itemView.findViewById(R.id.view_time);
        tv_item_name = itemView.findViewById(R.id.tv_item_name);
        tv_item_type = itemView.findViewById(R.id.tv_item_type);
        tv_item_time = itemView.findViewById(R.id.tv_item_time);
        tv_item_type_name = itemView.findViewById(R.id.tv_item_type_name);
        return itemView;
    }

    @Override
    protected void onRefreshView(String bean, int position) {
        if (!TextUtils.isEmpty(bean)) {
            tv_item_name.setText(bean);
            tv_item_type.setText(bean);
            tv_item_time.setText(bean);
            tv_item_type_name.setText(bean);
        }
    }

}