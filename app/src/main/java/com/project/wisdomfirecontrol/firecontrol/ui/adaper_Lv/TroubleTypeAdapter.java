package com.project.wisdomfirecontrol.firecontrol.ui.adaper_Lv;

import android.content.Context;
import android.view.ViewGroup;

import com.project.wisdomfirecontrol.common.ui.BaseAdapterLV;
import com.project.wisdomfirecontrol.common.ui.BaseHolderLV;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.TroubleTypesBean;
import com.project.wisdomfirecontrol.firecontrol.ui.holder_Rv.TroubleTypeHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/3/27.
 */

public class TroubleTypeAdapter extends BaseAdapterLV<TroubleTypesBean> {
    public TroubleTypeAdapter(Context context, List<TroubleTypesBean> listData) {
        super(context, listData);
    }

    @Override
    public BaseHolderLV<TroubleTypesBean> createViewHolder(Context context, ViewGroup parent, TroubleTypesBean bean, int position) {
        return new TroubleTypeHolder(context, parent, this, position, bean);
    }
}
