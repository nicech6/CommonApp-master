package com.project.wisdomfirecontrol.firecontrol.ui.adaper_Lv;

import android.content.Context;
import android.view.ViewGroup;

import com.project.wisdomfirecontrol.common.ui.BaseAdapterLV;
import com.project.wisdomfirecontrol.common.ui.BaseHolderLV;
import com.project.wisdomfirecontrol.firecontrol.ui.holder_Lv.StringDatasLvHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/4/3.
 */

public class StringDatasLvAdapter extends BaseAdapterLV<String>{

    public StringDatasLvAdapter(Context context, List<String> listData) {
        super(context, listData);
    }

    @Override
    public BaseHolderLV<String> createViewHolder(Context context, ViewGroup parent, String bean, int position) {
        return new StringDatasLvHolder(context,parent,this,position,bean);
    }
}
