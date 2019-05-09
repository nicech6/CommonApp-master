package com.project.wisdomfirecontrol.firecontrol.ui.fragment_common;

import android.graphics.Color;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseFragment;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.firecontrol.ui.adapter_Rv.HomeSudokuHorizontalAdapter;

import static com.project.wisdomfirecontrol.common.base.Global.mContext;

/**
 * Created by Administrator on 2018/1/30.
 */

public class HomeFragment extends BaseFragment {

    private String TAG = "tag";

    private List<?> images = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<String>();
    private RecyclerView recyclerView;
    private TextView tv_home_company;
    public static List<String> list;
    public static List<String> onlineList;
    public static List<String> analyzeList;
    public static List<String> safeList;
    public static List<String> firecorolList;
    public static List<String> decumentList;
    public static List<String> sudoku_h5_List;
    public static List<String> sudokuvideoList;
    private HomeSudokuHorizontalAdapter homeSudokuAdapter;

    public static String[] tips = Global.mContext.getResources().getStringArray(R.array.home_sudoku);

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        recyclerView = findView(R.id.recyclerview);
        tv_home_company = findView(R.id.tv_home_company);
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        String[] tips = getResources().getStringArray(R.array.home_title);
        String[] onlineTips = getResources().getStringArray(R.array.home_online);
        String[] analyzeTips = getResources().getStringArray(R.array.home_analyze);
        String[] safeTips = getResources().getStringArray(R.array.home_safe);
        String[] firecorolTips = getResources().getStringArray(R.array.home_firecorol);
        String[] decumentTips = getResources().getStringArray(R.array.home_decument);
        String[] sudoku_h5 = getResources().getStringArray(R.array.home_sudoku_h5);
        String[] sudoku_video = getResources().getStringArray(R.array.home_video);
        list = Arrays.asList(tips);
        onlineList = Arrays.asList(onlineTips);
        analyzeList = Arrays.asList(analyzeTips);
        safeList = Arrays.asList(safeTips);
        firecorolList = Arrays.asList(firecorolTips);
        decumentList = Arrays.asList(decumentTips);
        sudoku_h5_List = Arrays.asList(sudoku_h5);
        sudokuvideoList = Arrays.asList(sudoku_video);
        homeSudokuAdapter = new HomeSudokuHorizontalAdapter(getActivity(), list);
        recyclerView.setAdapter(homeSudokuAdapter);
        tv_home_company.setBackgroundColor(Color.TRANSPARENT);
        tv_home_company.setText(getActivity().getResources().getString(R.string.home_text_company));

    }

    @Override
    public void onPause() {
        super.onPause();
    }


    //如果你需要考虑更好的体验，可以这么操作
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
    }

    @Override
    public void initTitleHeight(LinearLayout linearLayout) {
    }

    @Override
    public void onClick(View v, int id) {

    }

    @Override
    public void onHttpSuccess(int reqType, Message obj) {
        super.onHttpSuccess(reqType, obj);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onHttpError(int reqType, String error) {
    }

}
