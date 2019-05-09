package com.project.wisdomfirecontrol.firecontrol.ui.adaper_Lv;

import android.content.Context;
import android.view.ViewGroup;

import com.project.wisdomfirecontrol.common.ui.BaseAdapterLV;
import com.project.wisdomfirecontrol.common.ui.BaseHolderLV;
import com.project.wisdomfirecontrol.firecontrol.model.bean.area.AreaPerpersoDataBean;
import com.project.wisdomfirecontrol.firecontrol.ui.holder_Lv.AreaPerpersoDataBeanLvHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/4/3.
 */

public class AreaPerpersoDataBeanLvAdapter extends BaseAdapterLV<AreaPerpersoDataBean>{

    public AreaPerpersoDataBeanLvAdapter(Context context, List<AreaPerpersoDataBean> listData) {
        super(context, listData);
    }

    @Override
    public BaseHolderLV<AreaPerpersoDataBean> createViewHolder(Context context, ViewGroup parent, AreaPerpersoDataBean bean, int position) {
        return new AreaPerpersoDataBeanLvHolder(context,parent,this,position,bean);
    }
}
