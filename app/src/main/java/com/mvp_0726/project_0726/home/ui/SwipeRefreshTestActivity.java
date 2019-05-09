package com.mvp_0726.project_0726.home.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mvp_0726.common.base.codereview.BaseActivity;
import com.mvp_0726.common.utils.StatusBarUtil;
import com.project.wisdomfirecontrol.R;

import java.util.ArrayList;
import java.util.List;

public class SwipeRefreshTestActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout swiperefresh;
    private RecyclerView recyclerView;
    private SwiperefreshAdapter adapter;

    private List<String> listDatas = new ArrayList<>();

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_swiperefresh;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        View view = findViewById(R.id.space);
        view.setBackground(mActivity.getResources().getDrawable(R.drawable.title_toolbar_bg_blue));
        StatusBarUtil.setFadeStatusBarHeight(mActivity, view);
        tv_title.setText("刷新");

        swiperefresh = findViewById(R.id.swiperefresh);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initRecycler();
    }

    private void initRecycler() {
        adapter = new SwiperefreshAdapter(R.layout.lv_item_string);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setLisenter() {

    }

    @Override
    protected void widgetClick(View v) {

    }

    /*刷新*/
    @Override
    public void onRefresh() {

    }
}
