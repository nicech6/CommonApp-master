package com.mvp_0726.project_0726.home.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvp_0726.project_0726.constant.Constant;
import com.mvp_0726.project_0726.online_unit.ui.activity.SettingPoliceOnlineActivity;
import com.mvp_0726.project_0726.web.ui.WebH5Activity;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Const;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.firecontrol.model.bean.login.MenuDatasBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.login.MenuDatasBeanX;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_common.OnlineUnitActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_common.SystemSettingActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_setting.SettingManagerActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_video.VideoSytemListActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/7/30.
 */

public class HomeAdapter extends BaseItemDraggableAdapter<MenuDatasBeanX, BaseViewHolder> {

    private RecyclerView recyclerview;
    private TextView tv_item_type;
    private Intent intent;
    private String INTENT_VALUE = "";

    public HomeAdapter(int layoutResId, @Nullable List<MenuDatasBeanX> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuDatasBeanX item) {
        tv_item_type = helper.getView(R.id.tv_item_type);
        tv_item_type.setTextSize(13f);
        if (item != null) {
            helper.setText(R.id.tv_item_type, item.getName())
                    .setTextColor(R.id.tv_item_type, mContext.getResources().getColor(R.color.mvp_home_item_txt))
                    .addOnClickListener(R.id.layout_item_home);
        }
        recyclerview = helper.getView(R.id.item_recyclerview);
        recyclerview.setLayoutManager(new GridLayoutManager(mContext, Const.COUNT));

        final HomeItemAdapter homeItemAdapter = new HomeItemAdapter(R.layout.recycler_item_home_item, item.getMenuDatas());
        recyclerview.setAdapter(homeItemAdapter);

        homeItemAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
               MenuDatasBean menuDatasBean = homeItemAdapter.getItem(position);
                String name = menuDatasBean.getName();
                switch (name) {
                    case Constant.XIAQU_UNIT://辖区单位
                        intent = new Intent(mContext, OnlineUnitActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case Constant.SYSTEMMAIN://系统维护
                        intent = new Intent(mContext, SystemSettingActivity.class);
//                        intent = new Intent(mContext, TestActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case Constant.SETTINGMANAGE://设备管理
                        intent = new Intent(mContext, SettingManagerActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case Constant.VEDIO_LOOKING://视频
                        intent = new Intent(mContext, VideoSytemListActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case Constant.ONLINE_UNIT:////联网单位
                        INTENT_VALUE = Const.GO_SETTINGONLINE_FIRST;
                        intent = new Intent(mContext, SettingPoliceOnlineActivity.class);
                        intent.putExtra(Constant.INTENT_KEY, INTENT_VALUE);
                        mContext.startActivity(intent);
                        break;
                    case Constant.XIAOFANGBAOJING:////联网单位
                        INTENT_VALUE = Const.GO_SETTINGONLINE_SECOND;
                        intent = new Intent(mContext, SettingPoliceOnlineActivity.class);
                        intent.putExtra(Constant.INTENT_KEY, INTENT_VALUE);
                        mContext.startActivity(intent);
                        break;
                    case Constant.SHEBEIGUZHANG:////联网单位
                        INTENT_VALUE = Const.GO_SETTINGONLINE_THIRD;
                        intent = new Intent(mContext, SettingPoliceOnlineActivity.class);
                        intent.putExtra(Constant.INTENT_KEY, INTENT_VALUE);
                        mContext.startActivity(intent);
                        break;
                    default:
//                        Global.showToast("正在开发...");
                        INTENT_VALUE = getItemNameSuper(name);
                        if (INTENT_VALUE.equals("正在开发...")) {
                            Global.showToast(INTENT_VALUE);
                            return;
                        } else if (INTENT_VALUE.equals(Constant.XINGZHENGGONGWEN)) {
//                            Global.showToast("正在开发...");
                            List<?> menuDatas = menuDatasBean.getMenuDatas();
                            if (menuDatas.size() > 0) {//两个
                                INTENT_VALUE = Constant.XINGZHENGGONGWEN_NEWADD;
                            }
                        }
                        intent = new Intent(new Intent(mContext, WebH5Activity.class));
                        intent.putExtra(Constant.INTENT_KEY, INTENT_VALUE);
                        mContext.startActivity(intent);
//
                        break;
                }

            }
        });

    }

    /*监管*/
    private String getItemNameSuper(String name) {
        String itemName = "";
        if (name.equals(Constant.GONGGONGZIYUAN)) {//公共资源
            itemName = Constant.GONGGONGZIYUAN;
        } else if (name.equals(Constant.TONGJIFENXI)) {//统计分析
            itemName = Constant.TONGJIFENXI;
        } else if (name.equals(Constant.XIAOFANGPINGJI)) {//消防评级
            itemName = Constant.XIAOFANGPINGJI;
        } else if (name.equals(Constant.HISTORY_RECOIDING)) {//历史记录
            itemName = Constant.HISTORY_RECOIDING;
        } else if (name.equals(Constant.SAFE_DENGJI)) {//安全等级
            itemName = Constant.SAFE_DENGJI;
        } else if (name.equals(Constant.SAFE_PERSONAL)) {//安全人员
            itemName = Constant.SAFE_PERSONAL;
        } else if (name.equals(Constant.XINGZHENGGONGWEN)) {//行政公文
            itemName = Constant.XINGZHENGGONGWEN;
        } else {
            itemName = "正在开发...";
        }

        return itemName;
    }

}
