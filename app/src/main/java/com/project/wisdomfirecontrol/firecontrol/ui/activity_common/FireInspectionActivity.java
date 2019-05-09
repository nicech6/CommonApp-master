package com.project.wisdomfirecontrol.firecontrol.ui.activity_common;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.common.base.Const;
import com.project.wisdomfirecontrol.firecontrol.ui.adapter_Rv.FireInspectionAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/3/30.
 */

public class FireInspectionActivity extends BaseActivity {

    private RecyclerView recyclerview;
    public static List<String> listData;
    private FireInspectionAdapter adapter;
    private TextView tv_title;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_fireinspection;
    }

    @Override
    public void initView() {
        tv_title = findView(R.id.tv_title);
        tv_title.setText(getResources().getString(R.string.common_activity_fire));
        recyclerview = findView(R.id.recyclerview);

        GridLayoutManager manager = new GridLayoutManager(this, Const.COUNT_HIGHT);
        recyclerview.setLayoutManager(manager);
        String[] sudoku_h5 = getResources().getStringArray(R.array.home_firecorol_fire);

        listData = Arrays.asList(sudoku_h5);

        if (adapter == null) {
            adapter = new FireInspectionAdapter(this, listData);
        }
        recyclerview.setAdapter(adapter);
    }


    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v, int id) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        showToast(getString(R.string.onclick_again_back));
        return super.onKeyDown(keyCode, event);
    }
}
