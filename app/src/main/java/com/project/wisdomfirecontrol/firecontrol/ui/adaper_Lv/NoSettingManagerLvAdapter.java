package com.project.wisdomfirecontrol.firecontrol.ui.adaper_Lv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Global;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/3.
 */

public class NoSettingManagerLvAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private List<String> listDatas;
    private NoSettingManagerViewHolder holder;

    private NoInnerItemOnclickListener mListener;

    private List<NoSettingManagerViewHolder> listHolder = new ArrayList<>();

    public NoSettingManagerLvAdapter(Context context, List<String> listDatas) {
        this.context = context;
        this.listDatas = listDatas;
    }

    @Override
    public int getCount() {
        return listDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return listDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = Global.inflate(R.layout.lv_item_settring_no, parent); // 实例化convertView
            holder = new NoSettingManagerViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (NoSettingManagerViewHolder) convertView.getTag();
        }
        listHolder.add(holder);

        ViewGroup.LayoutParams layoutParams = holder.tv_item_type_name.getLayoutParams();
        layoutParams.width = (int) Global.mScreenWidth / 4;
        holder.tv_item_type_name.setLayoutParams(layoutParams);

        ViewGroup.LayoutParams layoutParams1 = holder.tv_item_type.getLayoutParams();
        layoutParams1.width = (int) Global.mScreenWidth * 3 / 4;
        holder.tv_item_type.setLayoutParams(layoutParams1);

        holder.tv_item_name.setText("删除" + "\n" + "监控区");
        holder.tv_item_type.setText("未绑定设备");
        holder.tv_item_type_name.setText("66栋1楼");

        holder.tv_item_name.setOnClickListener(this);
        holder.tv_item_name.setTag(position);

        return convertView;

    }

    public void showCheck(View view, boolean isShow) {

        for (NoSettingManagerViewHolder holder : listHolder) {
            if (!isShow) {
                holder.tv_item_name.setVisibility(View.VISIBLE);
            } else {
                holder.tv_item_name.setVisibility(View.GONE);
            }
        }
    }

    public interface NoInnerItemOnclickListener {
        void noItemClick(View v);
    }

    public void setOnInnerItemOnClickListener(NoInnerItemOnclickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        mListener.noItemClick(v);
    }


    class NoSettingManagerViewHolder {

        TextView tv_item_type_name, tv_item_name, tv_item_type;

        NoSettingManagerViewHolder(View itemView) {
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_item_type = itemView.findViewById(R.id.tv_item_type);
            tv_item_type_name = itemView.findViewById(R.id.tv_item_type_name);
        }
    }


}
