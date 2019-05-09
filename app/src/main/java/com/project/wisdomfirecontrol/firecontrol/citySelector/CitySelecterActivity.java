package com.project.wisdomfirecontrol.firecontrol.citySelector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.firecontrol.citySelector.adapter.MyBaseAdapter;
import com.project.wisdomfirecontrol.firecontrol.citySelector.adapter.SortAdapter;
import com.project.wisdomfirecontrol.firecontrol.citySelector.bean.RegionInfo;
import com.project.wisdomfirecontrol.firecontrol.citySelector.bean.SortModel;
import com.project.wisdomfirecontrol.firecontrol.citySelector.db.RegionDAO;
import com.project.wisdomfirecontrol.firecontrol.citySelector.utils.CharacterParser;
import com.project.wisdomfirecontrol.firecontrol.citySelector.utils.ClearEditText;
import com.project.wisdomfirecontrol.firecontrol.citySelector.utils.PinyinComparator;
import com.project.wisdomfirecontrol.firecontrol.citySelector.widget.SideBar;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_login_splash.SreachActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/6/5.
 */

public class CitySelecterActivity extends BaseActivity {

    private List<RegionInfo> provinceList;
    private List<RegionInfo> citysList;
    private List<String> provinces;
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;
    private List<RegionInfo> mReMenCitys;//热门城市列表
    private MyGridViewAdapter gvAdapter;
    private GridView mGridView;
    private RelativeLayout iv_left;

    private LinearLayout ll_title;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_city_selecter;
    }

    @Override
    public void initView() {
        ll_title = findView(R.id.ll_title);
        ll_title.setVisibility(View.GONE);
        initDatas();
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


    private void initDatas() {
        provinceList = RegionDAO.getProvencesOrCity(1);
        provinceList.addAll(RegionDAO.getProvencesOrCity(2));
        citysList = new ArrayList<RegionInfo>();
        mReMenCitys = new ArrayList<RegionInfo>();


        provinces = new ArrayList<String>();
        for (RegionInfo info : provinceList) {
            provinces.add(info.getName().trim());
        }
        mReMenCitys.add(new RegionInfo(2, 1, "北京"));
        mReMenCitys.add(new RegionInfo(25, 1, "上海"));
        mReMenCitys.add(new RegionInfo(77, 6, "深圳"));
        mReMenCitys.add(new RegionInfo(76, 6, "广州"));
        mReMenCitys.add(new RegionInfo(197, 14, "长沙"));
        mReMenCitys.add(new RegionInfo(343, 1, "天津"));

        initViews();
    }

    private void initViews() {
        iv_left = (RelativeLayout) findViewById(R.id.iv_left);
        View view = View.inflate(this, R.layout.head_city_list, null);
        mGridView = (GridView) view.findViewById(R.id.id_gv_remen);
        gvAdapter = new MyGridViewAdapter(this, mReMenCitys);
        mGridView.setAdapter(gvAdapter);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                if (s.equals("热门")) {
                    sortListView.setSelection(0);
                }
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        sortListView.addHeaderView(view);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                hideSoftInput(mClearEditText.getWindowToken());
                String name = ((SortModel) adapter.getItem(position - 1)).getName();
                Intent intent = new Intent(CitySelecterActivity.this, SreachActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("city", name);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);//返回页面1
                finish();
            }
        });

        SourceDateList = filledData(provinceList);

        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(this, SourceDateList);
        sortListView.setAdapter(adapter);


        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftInput(mClearEditText.getWindowToken());
                CitySelecterActivity.this.finish();
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(CitySelecterActivity.this, SreachActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("city", mReMenCitys.get(position).getName());
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);//返回页面1
                finish();
            }
        });
    }


    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(List<RegionInfo> date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();
        if (date.size() > 0) {
            for (int i = 0; i < date.size(); i++) {
                SortModel sortModel = new SortModel();
                sortModel.setName(date.get(i).getName());
                //汉字转换成拼音
                String pinyin = characterParser.getSelling(date.get(i).getName());
                String sortString = pinyin.substring(0, 1).toUpperCase();

                // 正则表达式，判断首字母是否是英文字母
                if (sortString.matches("[A-Z]")) {
                    sortModel.setSortLetters(sortString.toUpperCase());
                } else {
                    sortModel.setSortLetters("#");
                }

                mSortList.add(sortModel);
            }
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            if (!provinces.contains(filterStr)) {
                filterDateList.clear();
                for (SortModel sortModel : SourceDateList) {
                    String name = sortModel.getName();
                    if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                        filterDateList.add(sortModel);
                    }
                }
            } else {
                filterDateList.clear();
                for (int i = 0; i < provinceList.size(); i++) {
                    String name = provinceList.get(i).getName();
                    if (name.equals(filterStr)) {
                        filterDateList.addAll(filledData(RegionDAO.getProvencesOrCityOnId(provinceList.get(i).getId())));
//                        filterDateList.addAll(filledData(RegionDAO.getProvencesOrCityOnParent(provinceList.get(i).getId())));
//                        Log.d("tag", "filterData444: " + filterDateList.get(0).getName());
                    }
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }

    private class MyGridViewAdapter extends MyBaseAdapter<RegionInfo, GridView> {
        private LayoutInflater inflater;

        public MyGridViewAdapter(Context ct, List<RegionInfo> list) {
            super(ct, list);
            inflater = LayoutInflater.from(ct);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_remen_city, null);
                holder.id_tv_cityname = (TextView) convertView.findViewById(R.id.id_tv_cityname);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            RegionInfo info = mReMenCitys.get(position);
            holder.id_tv_cityname.setText(info.getName());
            return convertView;
        }

        class ViewHolder {
            TextView id_tv_cityname;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    protected void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
