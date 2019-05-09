package com.project.wisdomfirecontrol.firecontrol.ui.holder_Rv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Const;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.ui.BaseAdapterRV;
import com.project.wisdomfirecontrol.common.ui.BaseHolderRV;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_common.SystemSettingActivity;
import com.mvp_0726.project_0726.online_unit.ui.activity.SettingPoliceOnlineActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_setting.SettingManagerActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_supevise.Supevise_CommonConnectUnitActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_video.VideoSytemListActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.Supevise_StringUtils;


/**
 * Created by Administrator on 2018/2/28.
 */

public class Supevise_StatementHolder extends BaseHolderRV<String> {

    private TextView itemName;
    private TextView tv_rv_item_num;
    private ImageView iv_sudoku_logo;
    private LinearLayout ll_item;
    private String INTENT_KEY = "";
    private String TYPE_KEY = "";
    private String INTENT_VALUE = "";
    private int imageId;
    private Intent intent;
    private String itemNameTitle;

    public Supevise_StatementHolder(Context context, ViewGroup parent,
                                    BaseAdapterRV<String> adapter,
                                    int itemType) {
        super(context, parent, adapter, itemType, R.layout.statement_rv_sudoku_item);
    }

    @Override
    public void onFindViews(View itemView) {
        ll_item = itemView.findViewById(R.id.ll_item);
        iv_sudoku_logo = itemView.findViewById(R.id.iv_sudoku_logo);
        itemName = itemView.findViewById(R.id.tv_item_name);
        tv_rv_item_num = itemView.findViewById(R.id.tv_rv_item_num);
        ViewGroup.LayoutParams params = ll_item.getLayoutParams();
        params.width = (int) Global.mScreenWidth / Const.COUNT_THREE;
        params.height = (int) Global.mScreenWidth / Const.COUNT;
        ll_item.setLayoutParams(params);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onRefreshView(String bean, int position) {
        int color = 0;
        if (TextUtils.isEmpty(bean)) {
            return;
        }
        tv_rv_item_num.setVisibility(View.INVISIBLE);
        imageId = Supevise_StringUtils.returnStateBgStr(bean);
        iv_sudoku_logo.setImageResource(imageId);
        itemName.setText(bean);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onItemClick(View itemView, int position, String bean) {

        INTENT_VALUE = Supevise_StringUtils.returnStateNetUrlStr(bean);

        if (INTENT_VALUE.contains(Const.GO_NETDEVICE)) {
            intent = new Intent(context, VideoSytemListActivity.class);
            super.context.startActivity(intent);
        } else if (INTENT_VALUE.contains(Const.GO_MYSYSTEM)) {
            intent = new Intent(context, SystemSettingActivity.class);
            super.context.startActivity(intent);
        } else if (INTENT_VALUE.contains(Const.GO_SETTINGMANAGER)) {
            intent = new Intent(context, SettingManagerActivity.class);
        } else if (INTENT_VALUE.contains(Const.GO_SETTINGONLINE_FIRST)) {

            intent = new Intent(context, SettingPoliceOnlineActivity.class);
            INTENT_KEY = "INTENT_KEY";
            intent.putExtra(INTENT_KEY, INTENT_VALUE);
        } else if (INTENT_VALUE.contains(Const.GO_SETTINGONLINE_SECOND)) {
            intent = new Intent(context, SettingPoliceOnlineActivity.class);
            INTENT_KEY = "INTENT_KEY";
            intent.putExtra(INTENT_KEY, INTENT_VALUE);
        } else if (INTENT_VALUE.contains(Const.GO_SETTINGONLINE_THIRD)) {
            intent = new Intent(context, SettingPoliceOnlineActivity.class);
            INTENT_KEY = "INTENT_KEY";
            intent.putExtra(INTENT_KEY, INTENT_VALUE);
        } else {
            if (!TextUtils.isEmpty(INTENT_VALUE)) {
                intent = new Intent(context, Supevise_CommonConnectUnitActivity.class);
                INTENT_KEY = "INTENT_KEY";
                intent.putExtra(INTENT_KEY, INTENT_VALUE);
                super.context.startActivity(intent);
            }
        }

    }
}
