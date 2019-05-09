package com.project.wisdomfirecontrol.common.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * 适配器基类封装(RecyclerView)
 *
 * @author LMX
 */
public abstract class BaseAdapterRV<T> extends RecyclerView.Adapter {

    private Context context;
    
    /** 列表显示的数据集合 */
    public List<T> listData;

    public BaseAdapterRV(Context context, List<T> listData) {
        this.context = context;
        this.listData = listData;
    }

    // RV.Adapter重写方法1:
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 创建holder对象
        return createViewHolder(context, parent, viewType);
    }

    // RV.Adapter重写方法2:
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseHolderRV<T> baseHolder = (BaseHolderRV<T>) holder;
        T bean = getItem(position);
        baseHolder.refreshView(bean, position);
    }

    // RV.Adapter重写方法3:
    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }

    /**
     * 创建holder对象
     *
     * @param context
     * @param parent
     * @return
     */
    public abstract BaseHolderRV<T> createViewHolder(
            Context context, ViewGroup parent, int viewType);

    /**
     * 获取列表项对应的实体对象
     */
    public T getItem(int i) {
        return listData.get(i);
    }

    /**
     * 刷新数据显示
     *
     * @param newData 要显示的新数据
     */
    public void setDatas(List<T> newData) {
        this.listData = newData;
        notifyDataSetChanged();
    }

    /**
     * 删除一个对象, 并刷新界面
     * @param bean
     */
    public void remove(T bean) {
        listData.remove(bean);
        notifyDataSetChanged();
    }

    public void refreshData(T data, int position) {

    }
}
















