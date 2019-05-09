package com.project.wisdomfirecontrol.firecontrol.ui.holder_Rv;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.ui.BaseAdapterRV;
import com.project.wisdomfirecontrol.common.ui.BaseHolderRV;
import com.project.wisdomfirecontrol.firecontrol.ui.adaper_Lv.SettingManagerAddressLvAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/3/19.
 */

public class SettingMangerHolder extends BaseHolderRV<String> {

    private TextView tv_top_title;
    private RecyclerView item_recyclerview;
    private List<String> list;
    private SettingManagerAddressLvAdapter adapter;

    public SettingMangerHolder(Context context, ViewGroup parent,
                               BaseAdapterRV<String> adapter,
                               int itemType) {
        super(context, parent, adapter, itemType, R.layout.setting_rv_item);
    }

    @Override
    public void onFindViews(View itemView) {
        tv_top_title = itemView.findViewById(R.id.tv_top_title);
        item_recyclerview = itemView.findViewById(R.id.item_recyclerview);

    }

    @Override
    protected void onRefreshView(String bean, int position) {
        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        item_recyclerview.setLayoutManager(manager);
        if (!TextUtils.isEmpty(bean)) {
            tv_top_title.setText(bean);
        }
        for (int i = 0; i < 25; i++) {
            list.add(i + "");
        }
//        list = SettingManagerActivity.listDats;
        Log.d("tag", "initData: " + list.size());
//        adapter = new SettingManagerAddressLvAdapter(context, list);
//        item_recyclerview.setAdapter(adapter);
    }

    @Override
    public void refreshData(String data, int position) {
        super.refreshData(data, position);
    }
}
