package com.mvp_0726.project_0726.home.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvp_0726.common.utils.LogUtils;
import com.mvp_0726.common.utils.ViewUtils;
import com.mvp_0726.project_0726.home.model.SettingCountBean;
import com.mvp_0726.project_0726.utils.StringUtils;
import com.project.wisdomfirecontrol.R;

import java.util.List;

/**
 * Created by Administrator on 2018/7/30.
 */

public class HomeSettingCountAdapter extends BaseItemDraggableAdapter<SettingCountBean, BaseViewHolder> {

    private TextView tv_item_name, tv_item_count;
    //    private RelativeLayout layout_item_home_item;
    private String name;
    private String mCount;
    private int colorType;

    public HomeSettingCountAdapter(int layoutResId, @Nullable List<SettingCountBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final SettingCountBean item) {
//        layout_item_home_item = helper.getView(R.id.layout_item_home_item);
        tv_item_name = helper.getView(R.id.tv_item_name);
        tv_item_count = helper.getView(R.id.tv_item_count);
        tv_item_name.setTextSize(15f);

        int position = helper.getAdapterPosition();
        if (position == 0) {
            colorType = mContext.getResources().getColor(R.color.color_lian);
        } else if (position == 1) {
            colorType = mContext.getResources().getColor(R.color.color_bao);
        } else {
            colorType = mContext.getResources().getColor(R.color.color_gu);
        }
        LogUtils.d("=====convert==" + position);
        if (item != null) {
            int height = (int) (com.mvp_0726.common.utils.Global.mScreenWidth / 5);
            ViewUtils.setTVHightOrWidth(tv_item_count, height, height);
            name = StringUtils.isEntryStrZero(item.getName());
            mCount = StringUtils.isEntryStrZero(item.getCount());
            helper.setText(R.id.tv_item_count, mCount)
                    .setText(R.id.tv_item_name, name)
                    .setTextColor(R.id.tv_item_count, colorType);
        }
    }

}
