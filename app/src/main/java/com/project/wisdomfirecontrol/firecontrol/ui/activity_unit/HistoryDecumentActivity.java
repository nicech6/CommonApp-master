package com.project.wisdomfirecontrol.firecontrol.ui.activity_unit;

import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.luck.picture.lib.entity.LocalMedia;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.common.base.Const;
import com.project.wisdomfirecontrol.firecontrol.base.MyApplication;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.SubmitBean;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.CommonProtocol;
import com.project.wisdomfirecontrol.firecontrol.ui.adapter_Rv.HistoryDecumentAdapter;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.PhotoUtils;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.Unit_StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/23.
 */

public class HistoryDecumentActivity extends BaseActivity {

    private static final String TAG = "tag";
    private TextView tv_title, tv_title_right, tv_title_des, tv_submit;
    private String title, gwid;
    private List<LocalMedia> selectList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PhotoUtils photoUtils;
    private CommonProtocol commonProtocol;
    private String imageurl;
    private StringBuffer stringBuffer;
    private String title_des;
    private String userid;
    private List<String> listData;
    private String pid;
    private HistoryDecumentAdapter adapter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_historydecument;
    }

    @Override
    public void initView() {
        tv_title = findView(R.id.tv_title);
        tv_title.setText(R.string.title_right_txt);
        recyclerView = findView(R.id.recycler);
        GridLayoutManager manager = new GridLayoutManager(this, Const.COUNT);
        recyclerView.setLayoutManager(manager);

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        boolean networkConnected = MyApplication.getInstance().isNetworkConnected();
        if (!networkConnected) {
            showToast(getResources().getString(R.string.no_net));
            return;
        }
        pid = Unit_StringUtils.getUserPid(this);
        commonProtocol = new CommonProtocol();
        showWaitDialog(this, getString(R.string.inupdate));
        commonProtocol.getOrganImg(this, pid);

    }

    @Override
    public void onClick(View v, int id) {
    }


    @Override
    public void onHttpSuccess(int reqType, Message msg) {
        super.onHttpSuccess(reqType, msg);
        dismissWaitDialog();
        SubmitBean bean = (SubmitBean) msg.obj;
        String data = bean.getData();
        listData = new ArrayList<>();
        listData.clear();
        if (data.contains(",")) {
            String[] split = data.split(",");
            if (split.length > 0) {
                for (String aSplit : split) {
                    if (!TextUtils.isEmpty(aSplit)) {
                        listData.add(aSplit);
                    }
                }
            }
        } else {
            if (!TextUtils.isEmpty(data)) {
                listData.add(data);
            } else {
                showToast("暂无历史图片");
                return;
            }
        }
        if (adapter == null) {
            adapter = new HistoryDecumentAdapter(HistoryDecumentActivity.this, listData);
        } else {
            adapter.notifyDataSetChanged();
        }

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onHttpError(int reqType, String error) {
        super.onHttpError(reqType, error);
        dismissWaitDialog();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }
}
