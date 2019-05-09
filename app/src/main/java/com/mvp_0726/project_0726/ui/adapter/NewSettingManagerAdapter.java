package com.mvp_0726.project_0726.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvp_0726.project_0726.utils.StringUtils;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.firecontrol.model.bean.setting.SettingManagerDataBean;

import java.util.HashMap;
import java.util.List;

/*设备管理*/
public class NewSettingManagerAdapter extends BaseQuickAdapter<SettingManagerDataBean, BaseViewHolder> {

    private List<SettingManagerDataBean> datas;
    public static HashMap<Integer, Boolean> isSelected;

    public NewSettingManagerAdapter(int layoutResId, @Nullable List<SettingManagerDataBean> data) {
        super(layoutResId, data);
        this.datas = data;
        init();
    }

    // 初始化 设置所有item都为未选择
    public void init() {
        isSelected = new HashMap<Integer, Boolean>();
        for (int i = 0; i < datas.size(); i++) {
            isSelected.put(i, true);
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, SettingManagerDataBean item) {
        if (helper != null && null != item) {
            int position = helper.getAdapterPosition();
            View convertView = helper.getConvertView();
            CheckBox checkBox = helper.getView(R.id.cb_item_check);
            checkBox.setChecked(isSelected.get(position));
            convertView.setSelected(isSelected.get(position));
            String setposition = StringUtils.isEntryStrXieg(item.getSetposition());
            String areaname = StringUtils.isEntryStrXieg(item.getAreaname());
            String creattime = item.getCreattime();
            if (!TextUtils.isEmpty(creattime)) {
                creattime = StringUtils.returnStrTime(creattime);
            }
            String type = StringUtils.isEntryStrXieg(item.getType());
            helper.setText(R.id.tv_item_name, setposition)
                    .setText(R.id.tv_item_type, type)
                    .setText(R.id.tv_item_type_name, areaname)
                    .setText(R.id.tv_item_time, creattime);
        }
    }
}
