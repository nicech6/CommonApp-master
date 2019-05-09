package com.project.wisdomfirecontrol.firecontrol.ui.adapter_Rv;

import android.content.Context;
import android.view.ViewGroup;

import com.project.wisdomfirecontrol.common.ui.BaseAdapterRV;
import com.project.wisdomfirecontrol.common.ui.BaseHolderRV;
import com.project.wisdomfirecontrol.firecontrol.model.bean.login.MenuDatasBeanX;
import com.project.wisdomfirecontrol.firecontrol.ui.holder_Rv.MainChangeLvHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/3/30.
 */

public class MainChangeLvAdapter extends BaseAdapterRV<MenuDatasBeanX>{

    public MainChangeLvAdapter(Context context, List<MenuDatasBeanX> listData) {
        super(context, listData);
    }

    @Override
    public BaseHolderRV<MenuDatasBeanX> createViewHolder(Context context, ViewGroup parent, int viewType) {
        return new MainChangeLvHolder(context,parent,this,viewType);
    }
}
