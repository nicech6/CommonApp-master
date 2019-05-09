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
import com.project.wisdomfirecontrol.firecontrol.ui.activity_unit.PatrolclockActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_unit.ConnectUnitActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_unit.HiddenTroubleReportActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.Unit_StringUtils;

/**
 * Created by Administrator on 2018/3/30.
 */

public class FireInspectionHolder extends BaseHolderRV<String> {

    private TextView itemName;
    private TextView tv_rv_item_num;
    private ImageView iv_sudoku_logo;
    private LinearLayout ll_item;
    private String INTENT_KEY = "";
    private String INTENT_VALUE = "";
    private int imageId;
    private Intent intent;
    private String itemNameTitle;

    public FireInspectionHolder(Context context, ViewGroup parent, BaseAdapterRV<String> adapter, int viewType) {
        super(context, parent, adapter, viewType, R.layout.statement_rv_sudoku_item);
    }


    @Override
    public void onFindViews(View itemView) {
        ll_item = itemView.findViewById(R.id.ll_item);
        iv_sudoku_logo = itemView.findViewById(R.id.iv_sudoku_logo);
        itemName = itemView.findViewById(R.id.tv_item_name);
        tv_rv_item_num = itemView.findViewById(R.id.tv_rv_item_num);
        tv_rv_item_num.setVisibility(View.INVISIBLE);
        ViewGroup.LayoutParams params = ll_item.getLayoutParams();
        params.width = (int) Global.mScreenWidth / Const.COUNT_HIGHT;
        params.height = (int) Global.mScreenWidth / Const.COUNT;
        ll_item.setLayoutParams(params);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onRefreshView(String bean, int position) {
        if (TextUtils.isEmpty(bean)) {
            return;
        }
        imageId = Unit_StringUtils.returnStateFireBgStr(bean);
        iv_sudoku_logo.setImageResource(imageId);
        itemName.setText(bean);
    }

    @Override
    protected void onItemClick(View itemView, int position, String bean) {
        INTENT_VALUE = Unit_StringUtils.returnWebNetUrlAndJoin(bean);
        itemNameTitle = Unit_StringUtils.returnStattFireItemName(bean);
        if (TextUtils.isEmpty(itemNameTitle)) {
            return;
        }
        if (INTENT_VALUE.contains(Const.GO_NETDEVICE)) {
            intent = new Intent(context, PatrolclockActivity.class);
        } else if (INTENT_VALUE.contains(Const.GO_HIDDENTROUBLE)) {
            intent = new Intent(context, HiddenTroubleReportActivity.class);
        } else {
            intent = new Intent(context, ConnectUnitActivity.class);
        }
        INTENT_KEY = "INTENT_KEY";
        intent.putExtra(INTENT_KEY, INTENT_VALUE);
        intent.putExtra("itemNameTitle", itemNameTitle);
        super.context.startActivity(intent);

    }
}
