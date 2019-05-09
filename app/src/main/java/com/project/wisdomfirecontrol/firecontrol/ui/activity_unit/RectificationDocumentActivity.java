package com.project.wisdomfirecontrol.firecontrol.ui.activity_unit;

import android.content.Intent;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mvp_0726.project_0726.web.ui.RectificationAdapter;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.common.base.Const;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.base.UserInfo;
import com.project.wisdomfirecontrol.common.base.UserManage;
import com.project.wisdomfirecontrol.firecontrol.base.MyApplication;
import com.project.wisdomfirecontrol.firecontrol.model.bean.document.DocumentBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.document.DocumentsBean;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.CommonProtocol;
import com.project.wisdomfirecontrol.firecontrol.ui.adapter_Rv.RectificationDocumentAdaper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 * 整改公文正文
 */

public class RectificationDocumentActivity extends BaseActivity {
    private static final String TAG = "tag";
    private TextView tv_title;
    private RecyclerView recyclerView;
    private List<DocumentsBean> listData;
    private RectificationDocumentAdaper documentAdaper;
    private CommonProtocol commonProtocol;
    private String pid;
    private String title;
    private String gwid;
//    private SmartRefreshLayout refreshLayout;
    private boolean isSuccess = false;
    private boolean isError = false;
    private int count = 0;
    private RectificationAdapter rectificationAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_rectificationdocument;
    }

    @Override
    public void initView() {
        tv_title = findView(R.id.tv_title);
        tv_title.setText(R.string.rectificationdocument_title);

//        refreshLayout = findView(R.id.refreshLayout);
        recyclerView = findViewById(R.id.rv_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initRecycler();
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//                count++;
//                getNetDatas(String.valueOf(count));
//                refreshlayout.finishRefresh(isError);//传入false表示刷新失败
//            }
//        });
//
//        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                count++;
//                getNetDatas(String.valueOf(count));
//                refreshlayout.finishLoadmore(isSuccess);//传入false表示加载失败
//            }
//        });
//        refreshLayout.setRefreshHeader(new MaterialHeader(this).setShowBezierWave(true));
//        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));

    }

    private void initRecycler() {
        rectificationAdapter = new RectificationAdapter(R.layout.lv_item_document);
        recyclerView.setAdapter(rectificationAdapter);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        count++;
        getNetDatas(String.valueOf(count));
    }

    private void getNetDatas(String page) {
        boolean hasUserInfo = UserManage.getInstance().hasUserInfo(Global.mContext);
        if (hasUserInfo) {
            UserInfo userIdInfo = UserManage.getInstance().getUserIdInfo(Global.mContext);
            pid = userIdInfo.getPid();
            if (TextUtils.isEmpty(pid)) {
                showToast(getString(R.string.login_again));
                return;
            }
        }
        boolean networkConnected = MyApplication.getInstance().isNetworkConnected();
        if (!networkConnected) {
            showToast(getResources().getString(R.string.no_net));
            return;
        }
        showWaitDialog(this, getString(R.string.inupdate));
        commonProtocol = new CommonProtocol();
        commonProtocol.getDocument(this, pid, Const.PAGE_LIMIT, page);
        Log.d(TAG, "getNetDatas: " + page + " +++ " + pid + " ++ " + Const.PAGE_LIMIT);
    }

    @Override
    public void onClick(View v, int id) {

    }

    @Override
    public void onHttpSuccess(int reqType, Message msg) {
        super.onHttpSuccess(reqType, msg);
        if (listData == null) {
            listData = new ArrayList<>();
        }
        if (!isError) {
            isSuccess = true;
            //回调成功后的相关操作
        }
        isError = false;
        dismissWaitDialog();
        DocumentBean documentBean = (DocumentBean) msg.obj;
        int count = documentBean.getCount();
        if (count == 0) {
            showToast(getString(R.string.empty_title));
        }
        List<DocumentsBean> data = documentBean.getData();
        if (data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                DocumentsBean documentsBean = new DocumentsBean();
                documentsBean.setContent(data.get(i).getContent());
                documentsBean.setId(data.get(i).getId());
                documentsBean.setFilepath(data.get(i).getFilepath());
                documentsBean.setTitle(data.get(i).getTitle());
                documentsBean.setRole(data.get(i).getRole());
                documentsBean.setGwid(data.get(i).getGwid());
                documentsBean.setSendtime(data.get(i).getSendtime());
                documentsBean.setType(data.get(i).getType());
                listData.add(documentsBean);
            }
        }
        rectificationAdapter = new RectificationAdapter(R.layout.lv_item_document, listData);
        rectificationAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(rectificationAdapter);

        rectificationAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                title = listData.get(position).getTitle();
                gwid = listData.get(position).getGwid();

                Intent intent = new Intent(RectificationDocumentActivity.this, RectificationTitleActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("gwid", gwid);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onHttpError(int reqType, String error) {
        super.onHttpError(reqType, error);
        dismissWaitDialog();
        if (count > 1) {
            count--;
        }
        isError = true;
        isSuccess = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (listData == null) {
            listData = new ArrayList<>();
        }
        if (listData.size() > 0) {
            listData.clear();
        }
        count = 0;
    }
}
