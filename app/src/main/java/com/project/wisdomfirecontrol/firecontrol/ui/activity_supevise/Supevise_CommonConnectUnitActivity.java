package com.project.wisdomfirecontrol.firecontrol.ui.activity_supevise;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.common.base.Const;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.IHttpService;
import com.project.wisdomfirecontrol.firecontrol.ui.fragment_supevise.Supevise_First_Web_Fragment;
import com.project.wisdomfirecontrol.firecontrol.ui.fragment_supevise.Supevise_Second_Web_Fragment;
import com.project.wisdomfirecontrol.firecontrol.ui.fragment_supevise.Supevise_Third_Web_Fragment;
import com.project.wisdomfirecontrol.firecontrol.ui.fragment_unit.Fourth_Web_Fragment;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.Supevise_StringUtils;
import com.tencent.smtt.sdk.WebView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/3/1.
 * 联网单位
 */

public class Supevise_CommonConnectUnitActivity extends BaseActivity {

    private TextView tv_title, tv_item_name_1, tv_item_name_2, tv_item_name_3, tv_item_name_4,
            tv_rv_item_num_1, tv_rv_item_num_2, tv_rv_item_num_3;
    private GradientDrawable myGrad1, myGrad2, myGrad3;
    private WebView webView;
    private ProgressBar mWebProgressbar;
    private String TAG = "tag";
    private String stringExtra, itemNameTitle;
    private LinearLayout ll_item, ll_item_tab_1, ll_item_tab_2, ll_item_tab_3, ll_item_tab_4;
    private ImageView iv_sudoku_logo_1, iv_sudoku_logo_2, iv_sudoku_logo_3, iv_sudoku_logo_4;

    private List<String> online = Arrays.asList(IHttpService.NETDEVICE_URL, IHttpService.FIREALARM_URL, IHttpService.DEVICEORDER_URL);

    private FrameLayout framelayout;
    private FragmentManager mFragmentManager;
    private Supevise_First_Web_Fragment mFragmentOne;
    private Supevise_Second_Web_Fragment mFragmentTwo;
    private Supevise_Third_Web_Fragment mFragmentThird;
    private Fourth_Web_Fragment fourth_Web_Fragment;
    private Fragment fragmentNow;
    public static String url_two;
    public static String url_third;
    public static String url_four;
    public static String string;

    private List<String> item_first_Name;
    private List<String> item_fourth_Name;
    private List<String> item_fifth_Name;
    private List<String> item_sixth_Name;
    private List<String> item_seventh_Name;
    private int itemCount;
    private int tempCount;

    private String activityTitle;
    private FragmentTransaction fragmentTransaction;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_common_connectunit;
    }

    @Override
    public void initView() {
        ll_item = findViewById(R.id.ll_item);

        initViewID();


        framelayout = findView(R.id.framelayout);
        mWebProgressbar = findView(R.id.web_progressbar);

        ViewGroup.LayoutParams params = ll_item.getLayoutParams();
        params.height = (int) Global.mScreenWidth / Const.COUNT;
        ll_item.setLayoutParams(params);
        Intent intent = getIntent();
        stringExtra = intent.getStringExtra("INTENT_KEY");
        if (TextUtils.isEmpty(stringExtra)) {
            return;
        }
        tv_title.setText(stringExtra);
        itemCount = Supevise_StringUtils.returnActivityItemBgStr(stringExtra);
        initListDatas(itemCount);
        string = Supevise_StringUtils.returnActivityH5Url(itemCount, Const.COUNT_FIRST_ONE);
        showItemNameAndImages(itemCount, tv_item_name_1, tv_item_name_2, tv_item_name_3,
                iv_sudoku_logo_1, iv_sudoku_logo_2, iv_sudoku_logo_3);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

//        loadWebH5(SERVICE_URL);
        initDefaultFragment(itemCount);
    }

    //初始化默认fragment的加载
    private void initDefaultFragment(int itemCount) {
        //实例化FragmentOne
        mFragmentOne = new Supevise_First_Web_Fragment();
        mFragmentTwo = new Supevise_Second_Web_Fragment();
        mFragmentThird = new Supevise_Third_Web_Fragment();
        fourth_Web_Fragment = new Fourth_Web_Fragment();
        //获取碎片管理者
        mFragmentManager = getSupportFragmentManager();

        //开启一个事务
        fragmentTransaction = mFragmentManager.beginTransaction();
        //add：往碎片集合中添加一个碎片；
        //replace：移除之前所有的碎片，替换新的碎片（remove和add的集合体）(很少用，不推荐，因为是重新加载，所以消耗流量)
        //参数：1.公共父容器的的id  2.fragment的碎片

        if (itemCount == Const.COUNT_FIRST || itemCount == Const.COUNT_FOURTH
                || itemCount == Const.COUNT_FIFTH || itemCount == Const.COUNT_SIXTH
                || itemCount == Const.COUNT_SEVENTH|| itemCount == Const.COUNT_EIGHTH) {
            fragmentTransaction.add(R.id.framelayout, new Supevise_First_Web_Fragment());
            tempCount = Const.COUNT_FIRST_ONE;
            string = Supevise_StringUtils.returnActivityH5Url(itemCount, Const.COUNT_FIRST_ONE);
            ll_item_tab_1.setBackgroundColor(getResources().getColor(R.color.list_divider));
        } else if (itemCount == Const.COUNT_SECOND) {
            fragmentTransaction.add(R.id.framelayout, new Supevise_Second_Web_Fragment());
            tempCount = Const.COUNT_FIRST_TWO;
            url_two = Supevise_StringUtils.returnActivityH5Url(itemCount, Const.COUNT_FIRST_TWO);
            ll_item_tab_2.setBackgroundColor(getResources().getColor(R.color.list_divider));
        } else if (itemCount == Const.COUNT_THIRD) {
            fragmentTransaction.add(R.id.framelayout, new Supevise_Third_Web_Fragment());
            tempCount = Const.COUNT_FIRST_THREE;
            url_two = Supevise_StringUtils.returnActivityH5Url(itemCount, Const.COUNT_FIRST_THREE);
            ll_item_tab_3.setBackgroundColor(getResources().getColor(R.color.list_divider));
        }

        activityTitle = Supevise_StringUtils.returnActivityTitle(itemCount, tempCount, item_first_Name,
                item_fourth_Name, item_fifth_Name, item_sixth_Name, item_seventh_Name);
        tv_title.setText(activityTitle);

        fragmentTransaction.addToBackStack(null);

        //提交事务
        fragmentTransaction.commit();

    }

    private void initViewID() {
        ll_item_tab_1 = findViewById(R.id.ll_item_tab_1);
        ll_item_tab_2 = findViewById(R.id.ll_item_tab_2);
        ll_item_tab_3 = findViewById(R.id.ll_item_tab_3);
        ll_item_tab_4 = findViewById(R.id.ll_item_tab_4);
        tv_title = findView(R.id.tv_title);

        tv_rv_item_num_1 = findView(R.id.tv_rv_item_num_1);
        tv_rv_item_num_2 = findView(R.id.tv_rv_item_num_2);
        tv_rv_item_num_3 = findView(R.id.tv_rv_item_num_3);
        tv_rv_item_num_1.setVisibility(View.GONE);
        tv_rv_item_num_2.setVisibility(View.GONE);
        tv_rv_item_num_3.setVisibility(View.GONE);
        myGrad1 = (GradientDrawable) tv_rv_item_num_1.getBackground();
        myGrad2 = (GradientDrawable) tv_rv_item_num_2.getBackground();
        myGrad3 = (GradientDrawable) tv_rv_item_num_3.getBackground();

        tv_item_name_1 = findView(R.id.tv_item_name_1);
        tv_item_name_2 = findView(R.id.tv_item_name_2);
        tv_item_name_3 = findView(R.id.tv_item_name_3);
        tv_item_name_4 = findView(R.id.tv_item_name_4);

        iv_sudoku_logo_1 = findView(R.id.iv_sudoku_logo_1);
        iv_sudoku_logo_2 = findView(R.id.iv_sudoku_logo_2);
        iv_sudoku_logo_3 = findView(R.id.iv_sudoku_logo_3);
        iv_sudoku_logo_4 = findView(R.id.iv_sudoku_logo_4);
    }

    private void initListDatas(int itemCount) {
        item_first_Name = Arrays.asList(getResources().getString(R.string.netdevice_url_unit),
                getResources().getString(R.string.firealarm_url),
                getResources().getString(R.string.deviceorder_url));
        item_fourth_Name = Arrays.asList(getResources().getString(R.string.netdevice_url_online)
                , getResources().getString(R.string.netdevice_url_trouble));

        item_fifth_Name = Arrays.asList(getResources().getString(R.string.netdevice_url_a),
                getResources().getString(R.string.firealarm_url),
                getResources().getString(R.string.deviceorder_url));

        item_sixth_Name = Arrays.asList(getString(R.string.year_grade),
                getString(R.string.mouth_grade), getString(R.string.mouth_trend));
        item_seventh_Name = Arrays.asList(getString(R.string.msg_decument), getString(R.string.change_decument)
                , getString(R.string.msg_list), getString(R.string.change_list));

//        tempCount = Const.COUNT_FIRST_ONE;
//        activityTitle = Supevise_StringUtils.returnActivityTitle(itemCount, tempCount, item_first_Name,
//                item_fourth_Name, item_fifth_Name, item_sixth_Name, item_seventh_Name);
//        tv_title.setText(activityTitle);
    }

    @SuppressLint("SetTextI18n")

    private void showItemNameAndImages(int itemCount, TextView tv_item_name_1, TextView tv_item_name_2,
                                       TextView tv_item_name_3, ImageView iv_sudoku_logo_1,
                                       ImageView iv_sudoku_logo_2, ImageView iv_sudoku_logo_3) {
        Log.d(TAG, "showItemNameAndImages: " + itemCount);

        if (itemCount == Const.COUNT_FIRST || itemCount == Const.COUNT_SECOND || itemCount == Const.COUNT_THIRD) {
            tv_rv_item_num_1.setVisibility(View.INVISIBLE);
            tv_rv_item_num_2.setVisibility(View.INVISIBLE);
            tv_rv_item_num_3.setVisibility(View.INVISIBLE);
            tv_rv_item_num_1.setText(Const.COUNT_FIRST + " ");
            tv_rv_item_num_2.setText(Const.COUNT_SECOND + " ");
            tv_rv_item_num_3.setText(Const.COUNT_THIRD + " ");
            myGrad1.setColor(Color.GREEN);
            myGrad2.setColor(Color.RED);
            myGrad3.setColor(Color.YELLOW);
            tv_item_name_1.setText(item_first_Name.get(0));
            tv_item_name_2.setText(item_first_Name.get(1));
            tv_item_name_3.setText(item_first_Name.get(2));
            iv_sudoku_logo_1.setImageResource(Supevise_StringUtils.imagesCommon[0]);
            iv_sudoku_logo_2.setImageResource(Supevise_StringUtils.imagesCommon[1]);
            iv_sudoku_logo_3.setImageResource(Supevise_StringUtils.imagesCommon[2]);

        } else if (itemCount == Const.COUNT_FOURTH) {
            ll_item_tab_3.setVisibility(View.GONE);
            tv_item_name_1.setText(item_fourth_Name.get(0));
            tv_item_name_2.setText(item_fourth_Name.get(1));
            iv_sudoku_logo_1.setImageResource(Supevise_StringUtils.imagesCommon[9]);
            iv_sudoku_logo_2.setImageResource(Supevise_StringUtils.imagesCommon[10]);

        } else if (itemCount == Const.COUNT_FIFTH) {
            tv_item_name_1.setText(item_fifth_Name.get(0));
            tv_item_name_2.setText(item_fifth_Name.get(1));
            tv_item_name_3.setText(item_fifth_Name.get(2));
            iv_sudoku_logo_1.setImageResource(Supevise_StringUtils.imagesCommon[11]);
            iv_sudoku_logo_2.setImageResource(Supevise_StringUtils.imagesCommon[12]);
            iv_sudoku_logo_3.setImageResource(Supevise_StringUtils.imagesCommon[13]);

        } else if (itemCount == Const.COUNT_SIXTH) {
            tv_item_name_1.setText(item_sixth_Name.get(0));
            tv_item_name_2.setText(item_sixth_Name.get(1));
            tv_item_name_3.setText(item_sixth_Name.get(2));
            iv_sudoku_logo_1.setImageResource(Supevise_StringUtils.imagesCommon[14]);
            iv_sudoku_logo_2.setImageResource(Supevise_StringUtils.imagesCommon[15]);
            iv_sudoku_logo_3.setImageResource(Supevise_StringUtils.imagesCommon[16]);

        } else if (itemCount == Const.COUNT_SEVENTH) {
            ll_item_tab_4.setVisibility(View.VISIBLE);
            tv_item_name_1.setText(item_seventh_Name.get(0));
            tv_item_name_2.setText(item_seventh_Name.get(1));
            tv_item_name_3.setText(item_seventh_Name.get(2));
            tv_item_name_4.setText(item_seventh_Name.get(3));
            iv_sudoku_logo_1.setImageResource(Supevise_StringUtils.imagesCommon[17]);
            iv_sudoku_logo_2.setImageResource(Supevise_StringUtils.imagesCommon[18]);
            iv_sudoku_logo_3.setImageResource(Supevise_StringUtils.imagesCommon[19]);
            iv_sudoku_logo_4.setImageResource(Supevise_StringUtils.imagesCommon[20]);
        } else if (itemCount == Const.COUNT_EIGHTH) {
            tv_item_name_1.setText(item_sixth_Name.get(0));
            tv_item_name_2.setText(item_sixth_Name.get(1));
            tv_item_name_3.setText(item_sixth_Name.get(2));
            iv_sudoku_logo_1.setImageResource(Supevise_StringUtils.imagesCommon[14]);
            iv_sudoku_logo_2.setImageResource(Supevise_StringUtils.imagesCommon[15]);
            iv_sudoku_logo_3.setImageResource(Supevise_StringUtils.imagesCommon[16]);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void initListener() {
        ll_item_tab_1.setOnClickListener(this);
        ll_item_tab_2.setOnClickListener(this);
        ll_item_tab_3.setOnClickListener(this);
        ll_item_tab_4.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v, int id) {
        fragmentTransaction = mFragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.ll_item_tab_1:
                tempCount = Const.COUNT_FIRST_ONE;
                string = Supevise_StringUtils.returnActivityH5Url(itemCount, Const.COUNT_FIRST_ONE);
                fragmentTransaction.replace(R.id.framelayout, new Supevise_First_Web_Fragment());
                ll_item_tab_1.setBackgroundColor(getResources().getColor(R.color.list_divider));
                ll_item_tab_2.setBackgroundColor(getResources().getColor(R.color.white));
                ll_item_tab_3.setBackgroundColor(getResources().getColor(R.color.white));
                ll_item_tab_4.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case R.id.ll_item_tab_2:
                tempCount = Const.COUNT_FIRST_TWO;
                url_two = Supevise_StringUtils.returnActivityH5Url(itemCount, Const.COUNT_FIRST_TWO);
                fragmentTransaction.replace(R.id.framelayout, new Supevise_Second_Web_Fragment());
                ll_item_tab_1.setBackgroundColor(getResources().getColor(R.color.white));
                ll_item_tab_2.setBackgroundColor(getResources().getColor(R.color.list_divider));
                ll_item_tab_3.setBackgroundColor(getResources().getColor(R.color.white));
                ll_item_tab_4.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case R.id.ll_item_tab_3:
                tempCount = Const.COUNT_FIRST_THREE;
                url_third = Supevise_StringUtils.returnActivityH5Url(itemCount, Const.COUNT_FIRST_THREE);
                fragmentTransaction.replace(R.id.framelayout, new Supevise_Third_Web_Fragment());
                ll_item_tab_1.setBackgroundColor(getResources().getColor(R.color.white));
                ll_item_tab_2.setBackgroundColor(getResources().getColor(R.color.white));
                ll_item_tab_3.setBackgroundColor(getResources().getColor(R.color.list_divider));
                ll_item_tab_4.setBackgroundColor(getResources().getColor(R.color.white));
                break;

            case R.id.ll_item_tab_4:
                tempCount = Const.COUNT_FOURTH;
                url_four = Supevise_StringUtils.returnActivityH5Url(itemCount, Const.COUNT_FOURTH);
                fragmentTransaction.replace(R.id.framelayout, new Fourth_Web_Fragment());
                ll_item_tab_1.setBackgroundColor(getResources().getColor(R.color.white));
                ll_item_tab_2.setBackgroundColor(getResources().getColor(R.color.white));
                ll_item_tab_3.setBackgroundColor(getResources().getColor(R.color.white));
                ll_item_tab_4.setBackgroundColor(getResources().getColor(R.color.list_divider));
                break;
        }


        activityTitle = Supevise_StringUtils.returnActivityTitle(itemCount, tempCount, item_first_Name,
                item_fourth_Name, item_fifth_Name, item_sixth_Name, item_seventh_Name);
        tv_title.setText(activityTitle);
        fragmentTransaction.commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
