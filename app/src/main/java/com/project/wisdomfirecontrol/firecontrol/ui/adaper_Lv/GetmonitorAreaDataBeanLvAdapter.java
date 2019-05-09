package com.project.wisdomfirecontrol.firecontrol.ui.adaper_Lv;

import android.content.Context;
import android.view.ViewGroup;

import com.project.wisdomfirecontrol.common.ui.BaseAdapterLV;
import com.project.wisdomfirecontrol.common.ui.BaseHolderLV;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.GetmonitorAreaDataBean;
import com.project.wisdomfirecontrol.firecontrol.ui.holder_Lv.GetmonitorAreaDataBeanLvHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/4/3.
 */

public class GetmonitorAreaDataBeanLvAdapter extends BaseAdapterLV<GetmonitorAreaDataBean>{

    public GetmonitorAreaDataBeanLvAdapter(Context context, List<GetmonitorAreaDataBean> listData) {
        super(context, listData);
    }

    @Override
    public BaseHolderLV<GetmonitorAreaDataBean> createViewHolder(Context context, ViewGroup parent, GetmonitorAreaDataBean bean, int position) {
        return new GetmonitorAreaDataBeanLvHolder(context,parent,this,position,bean);
    }
}
