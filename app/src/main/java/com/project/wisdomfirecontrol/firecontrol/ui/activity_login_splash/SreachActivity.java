package com.project.wisdomfirecontrol.firecontrol.ui.activity_login_splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.firecontrol.citySelector.CitySelecterActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.adaper_Lv.SreachAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/5.
 */

public class SreachActivity extends BaseActivity {

    private static final String TAG = "tag";
    private EditText editSreach;
    private Button btnSreach;
    private ListView sreachResult;
    private LinearLayout ll_title;
    private TextView edit_city;

    private PoiSearch mPoiSearch;
    private SreachAdapter mSreachAdapter;
    private String city;
    private List<PoiInfo> allAddr = new ArrayList<>();

    @Override
    public int getLayoutRes() {
        return R.layout.sreach_activity;
    }

    @Override
    public void initView() {
        ll_title = findView(R.id.ll_title);
        editSreach = findView(R.id.edit_sreach);
        btnSreach = findView(R.id.btn_sreach);
        edit_city = findView(R.id.edit_city);
        sreachResult = findView(R.id.sreach_result);


        ll_title.setVisibility(View.GONE);
        mPoiSearch = PoiSearch.newInstance();   //初始化检索功能
        mPoiSearch.setOnGetPoiSearchResultListener(poiLisener);
        btnSreach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SreachActivity.super.finish();
            }
        });
        Bundle bundle = getIntent().getExtras();
        city = (String) bundle.get("addressCity");
        edit_city.setText(city);
        editSreach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    mPoiSearch.searchInCity((new PoiCitySearchOption())
                            .keyword(s.toString())
                            .city(edit_city.getText().toString().trim())
                            .pageNum(10));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    //搜索监听
    public OnGetPoiSearchResultListener poiLisener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {       //检索结果
            if (poiResult == null || poiResult.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(SreachActivity.this, "很抱歉，暂无该位置信息", Toast.LENGTH_SHORT).show();
                return;
            }
            allAddr.clear();
            allAddr = poiResult.getAllPoi();
            if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                mSreachAdapter = new SreachAdapter(SreachActivity.this, allAddr);
                sreachResult.setAdapter(mSreachAdapter);
                sreachResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    private double latitude = 0;
                    private double longitude = 0;

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        LatLng location = allAddr.get(position).location;
                        if (location == null) {
                            showToast("你选的范围过于广阔");
                            return;
                        }
                        latitude = location.latitude;
                        longitude = location.longitude;
                        if (latitude > 0 && longitude > 0) {
                            Intent intent = new Intent(SreachActivity.this, RegisterFirstActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("province", allAddr.get(position).province);
                            bundle.putString("city", allAddr.get(position).city);
                            bundle.putString("area", allAddr.get(position).area);
                            bundle.putString("address", allAddr.get(position).address);
                            bundle.putDouble("latitude", latitude);
                            bundle.putDouble("longitude", longitude);
                            intent.putExtras(bundle);
                            setResult(Activity.RESULT_OK, intent);//返回页面1
                            finish();
                        } else {
                            showToast("你选的范围过于广阔");
                        }
                    }
                });
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {     // 获取Place详情页检索结果

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };


    @Override
    public void initListener() {
        edit_city.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v, int id) {
        switch (v.getId()) {
            case R.id.edit_city:
                edit_city();
                break;
        }
    }

    private void edit_city() {
        allAddr.clear();
        editSreach.setText("");
        Intent intent = new Intent(this, CitySelecterActivity.class);
//        Bundle bundle = new Bundle();
//        intent.putExtras(bundle);//将Bundle添加到Intent,也可以在Bundle中添加相应数据传递给下个页面,例如：bundle.putString("abc", "bbb");
        super.startActivityForResult(intent, 0);// 跳转并要求返回值，0代表请求值(可以随便写)
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            edit_city.setText("");
            Bundle bundle = data.getExtras();
            city = bundle.getString("city");
            edit_city.setText(city);
        }
    }
}
