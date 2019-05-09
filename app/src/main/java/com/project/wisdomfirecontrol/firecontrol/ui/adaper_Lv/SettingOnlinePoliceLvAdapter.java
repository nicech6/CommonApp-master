package com.project.wisdomfirecontrol.firecontrol.ui.adaper_Lv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mvp_0726.common.view.DrawableCenterTextView;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.util.LogUtil;
import com.project.wisdomfirecontrol.firecontrol.model.bean.setting.SettingManagerDataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/3.
 */

public class SettingOnlinePoliceLvAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private List<SettingManagerDataBean> listDatas;
    private SettingManagerViewHolder holder;
    private NoSettingManagerViewHolder noHolder;

    private static final int TYPE_COUNT = 2;//item类型的总数
    private static final int TYPE_COMSUM = 0;//绑定设备类型
    private static final int TYPE_CHARGE = 1;//未绑定类型
    private int currentType;//当前item类型

    private List<String> checkBoxIDList;            //存储checkBox的值
    private List<String> textTiewIDList;            //存储checkBox的值

    private boolean isOnclick = true;
    private String state;

    //get set
    public List<String> getCheckBoxIDList() {
        return checkBoxIDList;
    }

    public void setCheckBoxIDList(List<String> checkBoxIDList) {
        this.checkBoxIDList = checkBoxIDList;
    }

    private InnerItemOnclickListener mListener;
//    private NoInnerItemOnclickListener mnoListener;

    private List<SettingManagerViewHolder> listHolder = new ArrayList<>();
    private List<NoSettingManagerViewHolder> listNoHolder = new ArrayList<>();

    public SettingOnlinePoliceLvAdapter(Context context, List<SettingManagerDataBean> listDatas) {
        this.context = context;
        this.listDatas = listDatas;

        checkBoxIDList = new ArrayList<>();
        textTiewIDList = new ArrayList<>();
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

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        if (TextUtils.isEmpty(listDatas.get(position).getSensorid())) {
            return TYPE_CHARGE;//未绑定类型
        } else {
            return TYPE_COMSUM;//绑定设备类型
        }
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        currentType = getItemViewType(position);
        if (currentType == TYPE_COMSUM) {
            if (convertView == null) {
                convertView = Global.inflate(R.layout.lv_item_settring_add_state, parent); // 实例化convertView
                holder = new SettingManagerViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (SettingManagerViewHolder) convertView.getTag();
            }

            ViewGroup.LayoutParams layoutParams0 = holder.ll_check.getLayoutParams();
            layoutParams0.width = (int) Global.mScreenWidth / 4;
            holder.ll_check.setLayoutParams(layoutParams0);

            ViewGroup.LayoutParams layoutParams1 = holder.tv_item_name.getLayoutParams();
            layoutParams1.width = (int) Global.mScreenWidth / 4;
            holder.tv_item_name.setLayoutParams(layoutParams1);

            ViewGroup.LayoutParams layoutParams2 = holder.tv_item_type.getLayoutParams();
            layoutParams2.width = (int) Global.mScreenWidth / 4;
            holder.tv_item_type.setLayoutParams(layoutParams2);

            ViewGroup.LayoutParams layoutParams3 = holder.rv_item_time.getLayoutParams();
            layoutParams3.width = (int) Global.mScreenWidth / 4;
            holder.rv_item_time.setLayoutParams(layoutParams3);

            ViewGroup.LayoutParams layoutParams = holder.tv_item_type_name.getLayoutParams();
            layoutParams.width = (int) Global.mScreenWidth / 4;
            holder.tv_item_type_name.setLayoutParams(layoutParams);

            listHolder.add(holder);

            state = listDatas.get(position).getState();
            if (TextUtils.isEmpty(state)) {
                state = "离线";
            } else if (state.equals("3")) {
                state = "已处理";
            } else if (state.equals("1")) {
                state = "故障";
            } else if (state.equals("2")) {
                state = "报警";
            } else if (state.equals("0")) {
                state = "在线";
            }
            if (state.equals("报警") || state.equals("已处理")) {
                holder.tv_item_time.setTextColor(context.getResources().getColor(R.color.red));
                holder.tv_item_name.setTextColor(context.getResources().getColor(R.color.red));
                holder.tv_item_type.setTextColor(context.getResources().getColor(R.color.red));
                holder.tv_item_type_name.setTextColor(context.getResources().getColor(R.color.red));
            } else {
                holder.tv_item_time.setTextColor(context.getResources().getColor(R.color.black));
                holder.tv_item_name.setTextColor(context.getResources().getColor(R.color.black));
                holder.tv_item_type.setTextColor(context.getResources().getColor(R.color.black));
                holder.tv_item_type_name.setTextColor(context.getResources().getColor(R.color.black));
            }

            if (state.equals("报警")) {
                holder.tv_item_time.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        context.getResources().getDrawable(R.drawable.alarm), null);
            } else if (state.equals("故障")) {
                holder.tv_item_time.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        context.getResources().getDrawable(R.drawable.offline), null);
            } else if (state.equals("在线")) {
                holder.tv_item_time.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        context.getResources().getDrawable(R.drawable.online), null);
            } else if (state.equals("已处理")) {
                holder.tv_item_time.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                state = "已处理";
            } else {
                holder.tv_item_time.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        context.getResources().getDrawable(R.drawable.offline), null);
            }
            holder.iv_item_time.setVisibility(View.GONE);
            holder.tv_item_time.setVisibility(View.VISIBLE);

            holder.tv_item_time.setText(state);
            String setposition = listDatas.get(position).getSetposition();
            LogUtil.d("==============" + setposition);
            if (TextUtils.isEmpty(setposition)) {
                setposition = "---";
            }
            holder.tv_item_name.setText(setposition);
            String type = listDatas.get(position).getType();
            if (TextUtils.isEmpty(type)) {
                type = "---";
            }
            holder.tv_item_type.setText(type);
            String areaname = listDatas.get(position).getAreaname();
            if (TextUtils.isEmpty(areaname)) {
                areaname = "---";
            }
            holder.tv_item_type_name.setText(areaname);

            holder.tv_item_type_name.setBackgroundResource(R.color.white);

            holder.tv_item_name.setOnClickListener(this);
            holder.tv_item_type.setOnClickListener(this);
            holder.rv_item_time.setOnClickListener(this);
            holder.iv_item_time.setOnClickListener(this);
            holder.tv_item_time.setOnClickListener(this);
            holder.tv_item_type_name.setOnClickListener(this);

            holder.tv_item_time.setTag(position);
            holder.tv_item_name.setTag(position);
            holder.tv_item_type.setTag(position);
            holder.rv_item_time.setTag(position);
            holder.iv_item_time.setTag(position);
            holder.tv_item_type_name.setTag(position);


            //获取复选框选中状态改变事件进行增删改
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    /*
                     * b=选中状态
                     * if b = true 将值添加至checkBoxIDList
                     * if b = false 将值从checkBoxIDList移除
                     * */
                    if (b) {
                        checkBoxIDList.add(listDatas.get(position).getSensorid());
                        Log.d("tag", "onCheckedChanged:111 " + checkBoxIDList.size() + " ++ " + listDatas.get(position).getSensorid());
                    } else {
                        checkBoxIDList.remove(listDatas.get(position).getSensorid());
//                        Log.d("tag", "onCheckedChanged: " + checkBoxIDList.size() + " ++ " + listDatas.get(position).getSensorid());

                    }
                }
            });


        } else if (currentType == TYPE_CHARGE) {
            if (convertView == null) {
                convertView = Global.inflate(R.layout.lv_item_settring_no, parent); // 实例化convertView
                noHolder = new NoSettingManagerViewHolder(convertView);
                convertView.setTag(noHolder);
            } else {
                noHolder = (NoSettingManagerViewHolder) convertView.getTag();
            }
            listNoHolder.add(noHolder);

            ViewGroup.LayoutParams layoutParams1 = noHolder.tv_item_type.getLayoutParams();
            layoutParams1.width = (int) Global.mScreenWidth * 3 / 4;
            noHolder.tv_item_type.setLayoutParams(layoutParams1);

            ViewGroup.LayoutParams layoutParams = noHolder.tv_item_type_name1.getLayoutParams();
            layoutParams.width = (int) Global.mScreenWidth / 4;
            noHolder.tv_item_type_name1.setLayoutParams(layoutParams);

            ViewGroup.LayoutParams layoutParams0 = noHolder.tv_item_name1.getLayoutParams();
            layoutParams0.width = (int) Global.mScreenWidth / 4;
            noHolder.tv_item_name1.setLayoutParams(layoutParams0);

            ViewGroup.LayoutParams layoutParams3 = noHolder.tv_item_time1.getLayoutParams();
            layoutParams3.width = (int) Global.mScreenWidth * 2 / 4;
            noHolder.tv_item_time1.setLayoutParams(layoutParams3);

            noHolder.tv_item_name1.setText("删除" + "\n" + "监控区");
            noHolder.tv_item_type.setText("未绑定设备");
            noHolder.tv_item_time1.setText("未绑定设备");
            noHolder.tv_item_type_name1.setText(listDatas.get(position).getAreaname());
            noHolder.tv_item_type_name1.setBackgroundResource(R.color.red_hand);
            noHolder.tv_item_name1.setOnClickListener(this);
            noHolder.tv_item_name1.setOnClickListener(this);
            noHolder.tv_item_type_name1.setOnClickListener(this);
            noHolder.tv_item_name1.setTag(position);
            noHolder.tv_item_time1.setTag(position);
            noHolder.tv_item_type_name1.setTag(position);
        }

        notifyDataSetChanged();
        return convertView;

    }

    /*绑定设备*/
    public void showCheck(View view, boolean isShow) {
        for (SettingManagerViewHolder holder : listHolder) {
            if (!isShow) {
                holder.rv_item_time.setVisibility(View.GONE);
                holder.ll_check.setVisibility(View.VISIBLE);
                holder.checkBox.setChecked(false);
                notifyDataSetChanged();
            } else {
                holder.ll_check.setVisibility(View.GONE);
                holder.rv_item_time.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
            }
        }
    }

    /*绑定设备*/
    public void showPositionHolder(View view, boolean isShow, int position) {

        for (SettingManagerViewHolder holder : listHolder) {
            if (!isShow) {
                listHolder.get(position).rv_item_time.setVisibility(View.GONE);
                listHolder.get(position).ll_check.setVisibility(View.VISIBLE);
                listHolder.get(position).checkBox.setChecked(false);
            } else {
                listHolder.get(position).ll_check.setVisibility(View.GONE);
                listHolder.get(position).rv_item_time.setVisibility(View.VISIBLE);
            }
        }
    } /*未绑定设备*/

    public void showNoPositionHolder(View view, boolean isShow, int position) {
        if (!isShow) {
            listNoHolder.get(position).tv_item_type.setVisibility(View.GONE);
            listNoHolder.get(position).tv_item_name1.setVisibility(View.VISIBLE);
            listNoHolder.get(position).tv_item_time1.setVisibility(View.VISIBLE);

        } else {
            listNoHolder.get(position).tv_item_name1.setVisibility(View.GONE);
            listNoHolder.get(position).tv_item_time1.setVisibility(View.GONE);
            listNoHolder.get(position).tv_item_type.setVisibility(View.VISIBLE);
        }
    }

    /*未绑定设备*/
    public void showNosettingCheck(View view, boolean isShow) {
        Log.d("tag", "showNosettingCheck111: " + isShow);
        for (NoSettingManagerViewHolder holder : listNoHolder) {
            if (!isShow) {
                holder.tv_item_type.setVisibility(View.GONE);
                holder.tv_item_name1.setVisibility(View.VISIBLE);
                holder.tv_item_time1.setVisibility(View.VISIBLE);
                notifyDataSetChanged();

            } else {
                holder.tv_item_name1.setVisibility(View.GONE);
                holder.tv_item_time1.setVisibility(View.GONE);
                holder.tv_item_type.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
            }
        }
    }

    public List<String> getTextTiewIDList() {
        return textTiewIDList;
    }

    public void setTextTiewIDList(List<String> textTiewIDList) {
        this.textTiewIDList = textTiewIDList;
    }

    public interface InnerItemOnclickListener {
        void itemClick(View v);
    }

    public void setOnInnerItemOnClickListener(InnerItemOnclickListener listener) {
        this.mListener = listener;
    }

//    public interface NoInnerItemOnclickListener {
//        void noItemClick(View v);
//    }
//
//    public void setOnNOInnerItemOnClickListener(NoInnerItemOnclickListener listener) {
//        this.mnoListener = listener;
//    }

    @Override
    public void onClick(View v) {
        mListener.itemClick(v);
//        mnoListener.noItemClick(v);
    }


    class SettingManagerViewHolder {

        TextView tv_item_type_name, tv_item_name, tv_item_type;
        DrawableCenterTextView tv_item_time;
        ImageView iv_item_time;
        RelativeLayout rv_item_time;
        CheckBox checkBox;
        RelativeLayout ll_check;

        SettingManagerViewHolder(View itemView) {
            ll_check = itemView.findViewById(R.id.ll_check);
            checkBox = itemView.findViewById(R.id.view_time);
            tv_item_time = itemView.findViewById(R.id.tv_item_time);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_item_type = itemView.findViewById(R.id.tv_item_type);
            iv_item_time = itemView.findViewById(R.id.iv_item_time);
            rv_item_time = itemView.findViewById(R.id.rv_item_time);
            tv_item_type_name = itemView.findViewById(R.id.tv_item_type_name);
        }
    }

    class NoSettingManagerViewHolder {

        TextView tv_item_type_name1, tv_item_name1, tv_item_type, tv_item_time1;

        NoSettingManagerViewHolder(View itemView) {
            tv_item_name1 = itemView.findViewById(R.id.tv_item_name1);
            tv_item_type = itemView.findViewById(R.id.tv_item_type);
            tv_item_time1 = itemView.findViewById(R.id.tv_item_time1);
            tv_item_type_name1 = itemView.findViewById(R.id.tv_item_type_name1);
        }
    }


}
