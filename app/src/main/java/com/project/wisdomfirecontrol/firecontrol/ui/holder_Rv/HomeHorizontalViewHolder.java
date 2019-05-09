package com.project.wisdomfirecontrol.firecontrol.ui.holder_Rv;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Const;
import com.project.wisdomfirecontrol.common.ui.BaseAdapterRV;
import com.project.wisdomfirecontrol.common.ui.BaseHolderRV;
import com.project.wisdomfirecontrol.firecontrol.ui.adapter_Rv.HomeItemHorizontalAdapter;
import com.project.wisdomfirecontrol.firecontrol.ui.fragment_common.HomeFragment;


/**
 * Created by Administrator on 2018/3/19.
 */

public class HomeHorizontalViewHolder extends BaseHolderRV<String> {

    private TextView tv_top_title;
    private RecyclerView item_recyclerview;
    private List<String> list;
    private HomeItemHorizontalAdapter adapter;

    public HomeHorizontalViewHolder(Context context, ViewGroup parent,
                                    BaseAdapterRV<String> adapter,
                                    int itemType) {
        super(context, parent, adapter, itemType, R.layout.home_horizontal_rv_item);
    }

    @Override
    public void onFindViews(View itemView) {
        tv_top_title = itemView.findViewById(R.id.tv_top_title);
        item_recyclerview = itemView.findViewById(R.id.item_recyclerview);

    }

    @Override
    protected void onRefreshView(String bean, int position) {

        GridLayoutManager manager = new GridLayoutManager(context, Const.COUNT);
        item_recyclerview.setLayoutManager(manager);
        if (!TextUtils.isEmpty(bean)) {
            tv_top_title.setText(bean);
        }
        if (position == 0) {
            list = HomeFragment.onlineList;
        } else if (position == 1) {
            list = HomeFragment.analyzeList;
        } else if (position == 2) {
            list = HomeFragment.safeList;
        } else if (position == 3) {
            list = HomeFragment.firecorolList;
        } else if (position == 4) {
            list = HomeFragment.decumentList;
        }
        adapter = new HomeItemHorizontalAdapter(context, list);
        item_recyclerview.setAdapter(adapter);
    }
}
