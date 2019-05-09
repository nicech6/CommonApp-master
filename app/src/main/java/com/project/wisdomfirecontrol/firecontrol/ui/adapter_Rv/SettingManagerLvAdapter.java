package com.project.wisdomfirecontrol.firecontrol.ui.adapter_Rv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.project.wisdomfirecontrol.common.base.Const;
import com.project.wisdomfirecontrol.common.ui.BaseAdapterRV;
import com.project.wisdomfirecontrol.common.ui.BaseHolderRV;
import com.project.wisdomfirecontrol.firecontrol.ui.holder_Rv.SettingMangerHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/2/28.
 */

public class SettingManagerLvAdapter extends BaseAdapterRV<String>{


    public SettingManagerLvAdapter(Context context, List<String> listData) {
        super(context, listData);
    }

    @Override
    public BaseHolderRV createViewHolder(Context context, ViewGroup parent, int viewType) {
        switch (viewType){
            case Const.HORIZONTAL_VIEW:
                return new SettingMangerHolder(context,parent,this,viewType);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return Const.HORIZONTAL_VIEW;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public void refreshData(String data, int position) {
        super.refreshData(data, position);
    }

}

