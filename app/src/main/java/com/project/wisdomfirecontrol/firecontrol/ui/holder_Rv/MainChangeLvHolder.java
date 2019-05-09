package com.project.wisdomfirecontrol.firecontrol.ui.holder_Rv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mvp_0726.project_0726.online_unit.ui.activity.SettingPoliceOnlineActivity;
import com.project.wisdomfirecontrol.GlideApp;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Const;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.base.UserInfo;
import com.project.wisdomfirecontrol.common.base.UserManage;
import com.project.wisdomfirecontrol.common.ui.BaseAdapterRV;
import com.project.wisdomfirecontrol.common.ui.BaseHolderRV;
import com.project.wisdomfirecontrol.firecontrol.model.bean.login.MenuDatasBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.login.MenuDatasBeanX;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.IHttpService;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_common.OnlineUnitActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_common.SystemSettingActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_setting.SettingManagerActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_supevise.Supevise_CommonConnectUnitActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_unit.Unit_CommonConnectUnitActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_video.VideoSytemListActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/3/30.
 */

public class MainChangeLvHolder extends BaseHolderRV<MenuDatasBeanX> {

    private static final String TAG = "tag";
    private TextView itemName;
    private TextView tv_rv_item_num;
    private ImageView iv_sudoku_logo;
    private LinearLayout ll_item;
    private String INTENT_KEY = "";
    private String INTENT_VALUE = "";
    private int imageId;
    private Intent intent;
    private String itemNameTitle;
    private int color;

    public MainChangeLvHolder(Context context, ViewGroup parent, BaseAdapterRV<MenuDatasBeanX> adapter, int viewType) {
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
        params.width = (int) Global.mScreenWidth / Const.COUNT_THREE;
        params.height = (int) Global.mScreenWidth / Const.COUNT;
        ll_item.setLayoutParams(params);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onRefreshView(MenuDatasBeanX bean, int position) {
        String imagePath = bean.getImagePath();
        if (!TextUtils.isEmpty(imagePath)) {
            GlideApp.with(context).load(imagePath).placeholder(R.drawable.icon_common)
                    .error(R.drawable.icon_common).into(iv_sudoku_logo);
        } else {
            iv_sudoku_logo.setImageResource(R.drawable.icon_common);
        }
        itemName.setText(bean.getName());
    }

    @Override
    protected void onItemClick(View itemView, int position, MenuDatasBeanX bean) {
        String name = bean.getName();
        INTENT_KEY = "INTENT_KEY";
        if (name.equals(IHttpService.XIAQU_UNIT)) {
            List<MenuDatasBean> menuDatas = bean.getMenuDatas();
            if (menuDatas.size() > 0) {
                intent = new Intent(context, OnlineUnitActivity.class);
            } else {
                Global.showToast("该角色尚未授权子功能");
                return;
            }
        } else if (name.equals(IHttpService.ONLINE_UNIT)) {//联网单位
            INTENT_VALUE = Const.GO_SETTINGONLINE_FIRST;
            intent = new Intent(context, SettingPoliceOnlineActivity.class);
            intent.putExtra(INTENT_KEY, INTENT_VALUE);

        } else if (name.equals(IHttpService.SYSTEMMAIN)) {
            //系统维护
            intent = new Intent(context, SystemSettingActivity.class);
        } else if (name.equals(IHttpService.SETTINGMANAGE)) {
            //设备管理

            intent = new Intent(context, SettingManagerActivity.class);
        } else if (name.equals(IHttpService.VEDIO_LOOKING)) {
            //视频
            intent = new Intent(context, VideoSytemListActivity.class);
        } else if (name.equals(IHttpService.FIRSTPAGE_UNIT)) {
            Global.showToast("正在开发");
            return;
//        } else if (name.equals(IHttpService.XIAOFANGPINGJI)) {
//            Global.showToast("正在开发");
//            return;
        } else if (name.equals(IHttpService.HISTORY_RULE)) {
            Global.showToast("正在开发");
            return;
        } else {
            INTENT_VALUE = getItemNameSuper(name);
            if (INTENT_VALUE.equals("正在开发")) {
                Global.showToast("正在开发");
                return;
            }
            UserInfo userType = UserManage.getInstance().getUserType(Global.mContext);
            if (userType.getType().equals("1")) {
                Log.d(TAG, "onItemClick: 监管端  " + userType.getType() + "  +++  " + INTENT_VALUE);
                intent = new Intent(context, Supevise_CommonConnectUnitActivity.class);
            } else {//单位
                Log.d(TAG, "onItemClick:企业端  " + userType.getType() + "  +++  " + INTENT_VALUE);
                intent = new Intent(context, Unit_CommonConnectUnitActivity.class);
            }
            intent.putExtra(INTENT_KEY, INTENT_VALUE);
        }
        super.context.startActivity(intent);
    }

    /*监管*/
    private String getItemNameSuper(String name) {
        String itemName = "";
        if (name.equals(IHttpService.TONGJIFENXI)) {
            //统计分析
            itemName = IHttpService.TONGJIFENXI;
        } else if (name.equals(IHttpService.GONGGONGZIYUAN)) {
            //公共资源、公共消防
            itemName = IHttpService.GONGGONGZIYUAN;
        } else if (name.equals(IHttpService.XIAOFANGPINGJI)) {
//            消防评级即等级
            itemName = IHttpService.XIAOFANGPINGJI;
        } else if (name.equals(IHttpService.SAFE_PERSONAL)) {
            //安全人员
            itemName = IHttpService.SAFE_PERSONAL;
        } else if (name.equals(IHttpService.SAFE_DENGJI)) {
            //安全等级
            itemName = IHttpService.SAFE_DENGJI;
        } else if (name.equals(IHttpService.XINGZHENGGONGWEN)) {
            //行政公文
            itemName = IHttpService.XINGZHENGGONGWEN;
        } else {
            itemName = "正在开发";
        }

        return itemName;
    }
}
