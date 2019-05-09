package com.project.wisdomfirecontrol.firecontrol.ui.holder_Rv;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.ui.BaseAdapterRV;
import com.project.wisdomfirecontrol.common.ui.BaseHolderRV;

/**
 * Created by Administrator on 2018/6/26.
 */

public class StringDatasRvHolder extends BaseHolderRV<String> {

    private LinearLayout ll_rv;
    private TextView tv_item_type_name;


    public StringDatasRvHolder(Context context, ViewGroup parent, BaseAdapterRV<String> adapter, int itemType) {
        super(context, parent, adapter, itemType, R.layout.rv_item_string);
    }

    @Override
    public void onFindViews(View itemView) {
        ll_rv = itemView.findViewById(R.id.ll_rv);
        ViewGroup.LayoutParams layoutParams = ll_rv.getLayoutParams();
        layoutParams.width = (int) Global.mScreenWidth / 4;
        ll_rv.setLayoutParams(layoutParams);
        tv_item_type_name = itemView.findViewById(R.id.tv_item_type_name);

    }

    @Override
    protected void onRefreshView(String bean, int position) {
        tv_item_type_name.setText(bean);
    }
}
