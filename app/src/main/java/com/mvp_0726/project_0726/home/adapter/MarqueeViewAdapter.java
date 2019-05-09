package com.mvp_0726.project_0726.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mvp_0726.common.view.xMarqueeView.XMarqueeView;
import com.mvp_0726.common.view.xMarqueeView.XMarqueeViewAdapter;
import com.mvp_0726.project_0726.home.model.MarqueeDataBean;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Const;
import com.mvp_0726.project_0726.online_unit.ui.activity.SettingPoliceOnlineActivity;

import java.util.List;

/**
 * Created by fxd on 01/01/2017.
 */

public class MarqueeViewAdapter extends XMarqueeViewAdapter<MarqueeDataBean> {

    private Context mContext;
    private TextView tv_type, tv_time, tv_address, tv_area;

    public MarqueeViewAdapter(List<MarqueeDataBean> datas, Context context) {
        super(datas);
        mContext = context;
    }

    @Override
    public View onCreateView(XMarqueeView parent) {
        //跑马灯单个显示条目布局，自定义
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_marqueeview_rv, null);
    }

    @Override
    public void onBindView(View parent, View view, final int position) {
        //布局内容填充
        tv_type = (TextView) view.findViewById(R.id.tv_type);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_address = (TextView) view.findViewById(R.id.tv_address);
        tv_area = (TextView) view.findViewById(R.id.tv_area);

        tv_type.setText(mDatas.get(position).getType());
        tv_time.setText(mDatas.get(position).getCreateTime());
        tv_address.setText(mDatas.get(position).getSensorPosition());
        String monitoringArea = mDatas.get(position).getMonitoringArea();
        if (TextUtils.isEmpty(monitoringArea)) {
            monitoringArea = "未分配监控区";
        }
        tv_area.setText(monitoringArea);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_call = new Intent(mContext, SettingPoliceOnlineActivity.class);
                intent_call.putExtra("INTENT_KEY", Const.GO_SETTINGONLINE_SECOND);
                mContext.startActivity(intent_call);
            }
        });

    }

}
