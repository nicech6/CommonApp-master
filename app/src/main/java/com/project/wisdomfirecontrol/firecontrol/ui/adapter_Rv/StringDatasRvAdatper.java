package com.project.wisdomfirecontrol.firecontrol.ui.adapter_Rv;

import android.content.Context;
import android.view.ViewGroup;

import com.project.wisdomfirecontrol.common.ui.BaseAdapterRV;
import com.project.wisdomfirecontrol.common.ui.BaseHolderRV;
import com.project.wisdomfirecontrol.firecontrol.ui.holder_Rv.StringDatasRvHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/6/26.
 */

public class StringDatasRvAdatper extends BaseAdapterRV<String> {

    public StringDatasRvAdatper(Context context, List<String> listData) {
        super(context, listData);
    }

    @Override
    public BaseHolderRV<String> createViewHolder(Context context, ViewGroup parent, int viewType) {
        return new StringDatasRvHolder(context,parent,this,viewType);
    }
}
