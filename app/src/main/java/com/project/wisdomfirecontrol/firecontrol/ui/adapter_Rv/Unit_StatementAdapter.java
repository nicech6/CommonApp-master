package com.project.wisdomfirecontrol.firecontrol.ui.adapter_Rv;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

import com.project.wisdomfirecontrol.common.ui.BaseAdapterRV;
import com.project.wisdomfirecontrol.common.ui.BaseHolderRV;
import com.project.wisdomfirecontrol.firecontrol.ui.holder_Rv.Unit_StatementHolder;

/**
 * Created by Administrator on 2018/3/30.
 */

public class Unit_StatementAdapter extends BaseAdapterRV<String>{

    public Unit_StatementAdapter(Context context, List<String> listData) {
        super(context, listData);
    }

    @Override
    public BaseHolderRV<String> createViewHolder(Context context, ViewGroup parent, int viewType) {
        return new Unit_StatementHolder(context,parent,this,viewType);
    }
}
