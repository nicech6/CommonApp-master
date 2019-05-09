package com.mvp_0726.project_0726.ui.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mvp_0726.project_0726.utils.StringUtils;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.firecontrol.model.bean.setting.SettingManagerDataBean;

import java.util.HashMap;
import java.util.List;

public class MultiNewManageAdapter extends RecyclerView.Adapter {

    private List<SettingManagerDataBean> datas;
    public static HashMap<Integer, Boolean> isSelected;

    public boolean isShow = false;
    public boolean isCheckBoxPress = false;

    public MultiNewManageAdapter(List<SettingManagerDataBean> datas) {
        this.datas = datas;
        init();
    }

    // 初始化 设置所有item都为未选择
    public void init() {
        isSelected = new HashMap<Integer, Boolean>();
        for (int i = 0; i < datas.size(); i++) {
            isSelected.put(i, false);
        }
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onAreaNameClick(int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_settingmanager_item, parent, false);

        return new MultiViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MultiViewHolder) {
            final MultiViewHolder viewHolder = (MultiViewHolder) holder;

            String setposition = StringUtils.isEntryStrXieg(datas.get(position).getSetposition());
            String areaname = StringUtils.isEntryStrXieg(datas.get(position).getAreaname());
            String creattime = datas.get(position).getCreattime();
            if (!TextUtils.isEmpty(creattime)) {
                creattime = StringUtils.returnStrTime(creattime);
            }
            String type = StringUtils.isEntryStrXieg(datas.get(position).getType());

            viewHolder.mTvName.setText(setposition);
            viewHolder.mTvType.setText(type);
            viewHolder.mTvAreaname.setText(areaname);
            viewHolder.mTvCreattime.setText(creattime);
            viewHolder.mCheckBox.setChecked(isSelected.get(position));
            viewHolder.itemView.setSelected(isSelected.get(position));

            if (isShow) {
                viewHolder.mCheckBox.setVisibility(View.VISIBLE);
                viewHolder.mCheckBox.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        viewHolder.mCheckBox.getParent().requestDisallowInterceptTouchEvent(true);
                        if (viewHolder.mCheckBox.isChecked()) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                System.out.println("I'm CheckBox~");
                                isSelected.put(position, false);
                                isCheckBoxPress = true;

                            }
                        } else {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                System.out.println("I'm CheckBox~");
                                isSelected.put(position, true);
                                isCheckBoxPress = true;

                            }
                        }
                        return false;
                    }
                });
                if (mOnItemClickLitener != null) {
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickLitener.onItemClick(viewHolder.itemView, viewHolder.getAdapterPosition());
                        }
                    });
                }
            } else {
                viewHolder.mCheckBox.setVisibility(View.GONE);
                if (mOnItemClickLitener != null) {
                    viewHolder.mTvAreaname.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickLitener.onAreaNameClick(viewHolder.getAdapterPosition());
                        }
                    });

                    viewHolder.mTvName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickLitener.onItemClick(viewHolder.mTvName, viewHolder.getAdapterPosition());
                        }
                    });
                    viewHolder.mTvType.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickLitener.onItemClick(viewHolder.mTvType, viewHolder.getAdapterPosition());
                        }
                    });
                    viewHolder.mTvCreattime.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickLitener.onItemClick(viewHolder.mTvCreattime, viewHolder.getAdapterPosition());
                        }
                    });
                }
            }


        }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MultiViewHolder extends RecyclerView.ViewHolder {
        TextView mTvName, mTvType, mTvAreaname, mTvCreattime;
        CheckBox mCheckBox;

        public MultiViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tv_item_name);
            mTvType = (TextView) itemView.findViewById(R.id.tv_item_type);
            mTvAreaname = (TextView) itemView.findViewById(R.id.tv_item_type_name);
            mTvCreattime = (TextView) itemView.findViewById(R.id.tv_item_time);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.cb_item_check);
        }
    }

}
