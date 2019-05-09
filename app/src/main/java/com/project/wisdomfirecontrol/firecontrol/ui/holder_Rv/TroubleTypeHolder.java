package com.project.wisdomfirecontrol.firecontrol.ui.holder_Rv;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.ui.BaseAdapterLV;
import com.project.wisdomfirecontrol.common.ui.BaseHolderLV;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.TroubleTypesBean;

/**
 * Created by Administrator on 2018/3/27.
 */

public class TroubleTypeHolder extends BaseHolderLV<TroubleTypesBean> {

    private TextView tv_type_name;

    public TroubleTypeHolder(Context context, ViewGroup parent, BaseAdapterLV<TroubleTypesBean> adapter, int position, TroubleTypesBean bean) {
        super(context, parent, adapter, position, bean);
    }

    @Override
    public View onCreateView(Context context, ViewGroup parent) {
        View view = Global.inflate(R.layout.lv_item_trouble_type, parent);
        tv_type_name = view.findViewById(R.id.tv_type_name);
        return view;
    }

    @Override
    protected void onRefreshView(TroubleTypesBean bean, int position) {
        if (bean == null) {
            return;
        }
        if (!TextUtils.isEmpty(bean.getName())) {
            tv_type_name.setText(bean.getName());
        }
    }

}
