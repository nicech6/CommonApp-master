package com.project.wisdomfirecontrol.firecontrol.ui.activity_unit;

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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
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
import com.project.wisdomfirecontrol.firecontrol.ui.activity_common.DecumentManageActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_common.FireInspectionActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.fragment_unit.First_Web_Fragment;
import com.project.wisdomfirecontrol.firecontrol.ui.fragment_unit.Second_Web_Fragment;
import com.project.wisdomfirecontrol.firecontrol.ui.fragment_unit.Third_Web_Fragment;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.Unit_StringUtils;
import com.tencent.smtt.sdk.WebView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/3/1.
 */

public class Unit_CommonConnectUnitActivity extends BaseActivity {

    private TextView tv_title, tv_item_name_1, tv_item_name_2, tv_item_name_3,
            tv_rv_item_num_1, tv_rv_item_num_2, tv_rv_item_num_3;
    private GradientDrawable myGrad1, myGrad2, myGrad3;
    private WebView webView;
    private ProgressBar mWebProgressbar;
    private String TAG = "tag";
    private String stringExtra, itemNameTitle;
    private LinearLayout ll_title, ll_common, ll_item, ll_item_tab_1, ll_item_tab_2, ll_item_tab_3;
    private ImageView iv_sudoku_logo_1, iv_sudoku_logo_2, iv_sudoku_logo_3;

    private List<String> online = Arrays.asList(IHttpService.NETDEVICE_URL, IHttpService.FIREALARM_URL, IHttpService.DEVICEORDER_URL);
    private List<String> item_first_Name;
    private List<String> item_fourth_Name;
    private List<String> item_fifth_Name;
    private List<String> item_sixth_Name;
    private List<String> item_seventh_Name;
    private int itemCount;
    private String activityTitle;
    private FrameLayout framelayout;
    private FragmentManager mFragmentManager;
    private First_Web_Fragment mFragmentOne;
    private Second_Web_Fragment mFragmentTwo;
    private Third_Web_Fragment mFragmentThird;
    private Fragment fragmentNow;
    public static String url_two;
    public static String url_third;
    public static String string;
    public static int tempCount;
    private FragmentTransaction fragmentTransaction;
    private String type_key;
    private List<String> item_fourth_name1;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_common_connectunit;
    }

    @Override
    public void initView() {
        ll_common = findViewById(R.id.ll_common);
        ll_item = findViewById(R.id.ll_item);
        ll_title = findViewById(R.id.ll_title);


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
        itemCount = Unit_StringUtils.returnActivityItemBgStr(stringExtra);
        if (itemCount < 4) {
            startAnimationTitle(ll_title);
            startAnimation(ll_item, framelayout, mWebProgressbar);
        }
        initListDatas(itemCount);
        string = Unit_StringUtils.returnActivityH5Url(itemCount, Const.COUNT_FIRST_ONE);
        showItemNameAndImages(itemCount, tv_item_name_1, tv_item_name_2, tv_item_name_3,
                iv_sudoku_logo_1, iv_sudoku_logo_2, iv_sudoku_logo_3);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

//        loadWebH5(SERVICE_URL);
        initDefaultFragment(itemCount);
    }

    /*动画*/
    private void startAnimation(LinearLayout ll_common, FrameLayout framelayout, ProgressBar mWebProgressbar) {
        // type = Animation.RELATIVE_TO_SELF 表示x坐标移动到相对自己的0.5倍距离
        // type = Animation.RELATIVE_TO_PARENT 表示x坐标移动到自己父控件x轴的0.5倍距离 即 x + 0.5ParentX
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1.2f, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(500);
//        animation.setRepeatMode(Animation.REVERSE);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setFillAfter(true);
        ll_common.setAnimation(animation);
        framelayout.setAnimation(animation);
        mWebProgressbar.setAnimation(animation);

    }

    /*动画*/
    private void startAnimationTitle(LinearLayout ll_title) {
        // type = Animation.RELATIVE_TO_SELF 表示x坐标移动到相对自己的0.5倍距离
        // type = Animation.RELATIVE_TO_PARENT 表示x坐标移动到自己父控件x轴的0.5倍距离 即 x + 0.5ParentX
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, -0.5f, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(500);
//        animation.setRepeatMode(Animation.REVERSE);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setFillAfter(true);
        ll_title.setAnimation(animation);

    }

    //初始化默认fragment的加载
    private void initDefaultFragment(int itemCount) {
        //实例化FragmentOne
        mFragmentOne = new First_Web_Fragment();
        mFragmentTwo = new Second_Web_Fragment();
        mFragmentThird = new Third_Web_Fragment();
        //获取碎片管理者
        mFragmentManager = getSupportFragmentManager();

        //开启一个事务
        fragmentTransaction = mFragmentManager.beginTransaction();
        //add：往碎片集合中添加一个碎片；
        //replace：移除之前所有的碎片，替换新的碎片（remove和add的集合体）(很少用，不推荐，因为是重新加载，所以消耗流量)
        //参数：1.公共父容器的的id  2.fragment的碎片
        if (itemCount == Const.COUNT_FIRST || itemCount == Const.COUNT_FOURTH
                || itemCount == Const.COUNT_FIFTH || itemCount == Const.COUNT_SIXTH
                || itemCount == Const.COUNT_NINTH || itemCount == Const.COUNT_SEVENTH
                || itemCount == Const.COUNT_EIGHTH_TEN) {
            fragmentTransaction.add(R.id.framelayout, new First_Web_Fragment());
            tempCount = Const.COUNT_FIRST_ONE;
            string = Unit_StringUtils.returnActivityH5Url(itemCount, Const.COUNT_FIRST_ONE);
            ll_item_tab_1.setBackgroundColor(getResources().getColor(R.color.list_divider));
        } else if (itemCount == Const.COUNT_SECOND) {
            fragmentTransaction.add(R.id.framelayout, new Second_Web_Fragment());
            tempCount = Const.COUNT_FIRST_TWO;
            url_two = Unit_StringUtils.returnActivityH5Url(itemCount, Const.COUNT_FIRST_TWO);
            ll_item_tab_2.setBackgroundColor(getResources().getColor(R.color.list_divider));
        } else if (itemCount == Const.COUNT_THIRD) {
            fragmentTransaction.add(R.id.framelayout, new Third_Web_Fragment());
            tempCount = Const.COUNT_FIRST_THREE;
            url_two = Unit_StringUtils.returnActivityH5Url(itemCount, Const.COUNT_FIRST_THREE);
            ll_item_tab_3.setBackgroundColor(getResources().getColor(R.color.list_divider));
        }
//        else if (itemCount == Const.COUNT_EIGHTH) {
//            fragmentTransaction.add(R.id.framelayout, new Third_Web_Fragment());
//            tempCount = Const.COUNT_FIRST_THREE;
//            url_two = Unit_StringUtils.returnActivityH5Url(itemCount, Const.COUNT_FIRST_THREE);
//            ll_item.setVisibility(View.GONE);
//            ll_item_tab_3.setBackgroundColor(getResources().getColor(R.color.list_divider));
//        }

        activityTitle = Unit_StringUtils.returnActivityTitle(itemCount, tempCount, item_first_Name,
                item_fourth_Name, item_fifth_Name, item_sixth_Name, item_seventh_Name, item_fourth_name1);
        tv_title.setText(activityTitle);

        fragmentTransaction.addToBackStack(null);

        //提交事务
        fragmentTransaction.commit();
//        fragmentNow = mFragmentOne;
    }

    private void initViewID() {
        ll_item_tab_1 = findViewById(R.id.ll_item_tab_1);
        ll_item_tab_2 = findViewById(R.id.ll_item_tab_2);
        ll_item_tab_3 = findViewById(R.id.ll_item_tab_3);
        tv_title = findView(R.id.tv_title);

        tv_rv_item_num_1 = findView(R.id.tv_rv_item_num_1);
        tv_rv_item_num_2 = findView(R.id.tv_rv_item_num_2);
        tv_rv_item_num_3 = findView(R.id.tv_rv_item_num_3);
        myGrad1 = (GradientDrawable) tv_rv_item_num_1.getBackground();
        myGrad2 = (GradientDrawable) tv_rv_item_num_2.getBackground();
        myGrad3 = (GradientDrawable) tv_rv_item_num_3.getBackground();

        tv_item_name_1 = findView(R.id.tv_item_name_1);
        tv_item_name_2 = findView(R.id.tv_item_name_2);
        tv_item_name_3 = findView(R.id.tv_item_name_3);

        iv_sudoku_logo_1 = findView(R.id.iv_sudoku_logo_1);
        iv_sudoku_logo_2 = findView(R.id.iv_sudoku_logo_2);
        iv_sudoku_logo_3 = findView(R.id.iv_sudoku_logo_3);
    }

    private void initListDatas(int itemCount) {
        item_first_Name = Arrays.asList(getResources().getString(R.string.netdevice_url),
                getResources().getString(R.string.firealarm_url),
                getResources().getString(R.string.deviceorder_url));

        item_fourth_Name = Arrays.asList(getResources().getString(R.string.netdevice_url_a),
                getResources().getString(R.string.firealarm_url),
                getResources().getString(R.string.deviceorder_url));

        item_fourth_name1 = Arrays.asList(getResources().getString(R.string.netdevice_url_online)
                , getResources().getString(R.string.netdevice_url_trouble));

        item_fifth_Name = Arrays.asList(getString(R.string.common_activity_year),
                getString(R.string.common_activity_mouth),
                getString(R.string.common_activity_zz));
        item_sixth_Name = Arrays.asList(getString(R.string.common_activity_personnal), getString(R.string.common_activity_fire));
        item_seventh_Name = Arrays.asList(getString(R.string.common_activity_msg), getString(R.string.common_activity_zhegai));

    }

    @SuppressLint("SetTextI18n")
    private void showItemNameAndImages(int itemCount, TextView tv_item_name_1, TextView tv_item_name_2,
                                       TextView tv_item_name_3, ImageView iv_sudoku_logo_1,
                                       ImageView iv_sudoku_logo_2, ImageView iv_sudoku_logo_3) {
        Log.d(TAG, "showItemNameAndImages: " + itemCount);
        if (itemCount == Const.COUNT_FIRST || itemCount == Const.COUNT_SECOND || itemCount == Const.COUNT_THIRD) {
            tv_rv_item_num_1.setVisibility(View.GONE);
            tv_rv_item_num_2.setVisibility(View.GONE);
            tv_rv_item_num_3.setVisibility(View.GONE);
            if (Unit_StatementChangeActivity.listCount.size() == 3) {
                tv_rv_item_num_1.setText(Unit_StatementChangeActivity.listCount.get(0));
                tv_rv_item_num_2.setText(Unit_StatementChangeActivity.listCount.get(1));
                tv_rv_item_num_3.setText(Unit_StatementChangeActivity.listCount.get(2));
            }
            myGrad1.setColor(Color.GREEN);
            myGrad2.setColor(Color.RED);
            myGrad3.setColor(Color.YELLOW);
            tv_item_name_1.setText(item_first_Name.get(0));
            tv_item_name_2.setText(item_first_Name.get(1));
            tv_item_name_3.setText(item_first_Name.get(2));
            iv_sudoku_logo_1.setImageResource(Unit_StringUtils.imagesCommon[23]);
            iv_sudoku_logo_2.setImageResource(Unit_StringUtils.imagesCommon[1]);
            iv_sudoku_logo_3.setImageResource(Unit_StringUtils.imagesCommon[2]);

            /*统计分析*/
        } else if (itemCount == Const.COUNT_FOURTH) {
            tv_item_name_1.setText(item_fourth_Name.get(0));
            tv_item_name_2.setText(item_fourth_Name.get(1));
            tv_item_name_3.setText(item_fourth_Name.get(2));
            iv_sudoku_logo_1.setImageResource(Unit_StringUtils.imagesCommon[9]);
            iv_sudoku_logo_2.setImageResource(Unit_StringUtils.imagesCommon[10]);
            iv_sudoku_logo_3.setImageResource(Unit_StringUtils.imagesCommon[11]);

        /*安全等级*/
        } else if (itemCount == Const.COUNT_FIFTH) {
            tv_item_name_1.setText(item_fifth_Name.get(0));
            tv_item_name_2.setText(item_fifth_Name.get(1));
            tv_item_name_3.setText(item_fifth_Name.get(2));
            iv_sudoku_logo_1.setImageResource(Unit_StringUtils.imagesCommon[12]);
            iv_sudoku_logo_2.setImageResource(Unit_StringUtils.imagesCommon[13]);
            iv_sudoku_logo_3.setImageResource(Unit_StringUtils.imagesCommon[14]);

        /*安全人员*/
        } else if (itemCount == Const.COUNT_SIXTH) {
            ll_item_tab_3.setVisibility(View.GONE);
            tv_item_name_1.setText(item_sixth_Name.get(0));
            tv_item_name_2.setText(item_sixth_Name.get(1));
//            tv_item_name_3.setText(item_sixth_Name.get(2));
            iv_sudoku_logo_1.setImageResource(Unit_StringUtils.imagesCommon[15]);
            iv_sudoku_logo_2.setImageResource(Unit_StringUtils.imagesCommon[16]);
//            iv_sudoku_logo_3.setImageResource(Unit_StringUtils.imagesCommon[17]);
            /*行政公文*/
        } else if (itemCount == Const.COUNT_SEVENTH) {
            ll_item_tab_3.setVisibility(View.GONE);
            tv_item_name_1.setText(item_seventh_Name.get(0));
            tv_item_name_2.setText(item_seventh_Name.get(1));
//            tv_item_name_3.setText(item_seventh_Name.get(2));
            iv_sudoku_logo_1.setImageResource(Unit_StringUtils.imagesCommon[21]);
            iv_sudoku_logo_2.setImageResource(Unit_StringUtils.imagesCommon[22]);
//            iv_sudoku_logo_3.setImageResource(Unit_StringUtils.imagesCommon[20]);

        }
        /*暂无这个功能 ============== begin ===============*/
        else if (itemCount == Const.COUNT_EIGHTH_TEN) {
            tv_item_name_1.setText(item_fifth_Name.get(0));
            tv_item_name_2.setText(item_fifth_Name.get(1));
            tv_item_name_3.setText(item_fifth_Name.get(2));
            iv_sudoku_logo_1.setImageResource(Unit_StringUtils.imagesCommon[15]);
            iv_sudoku_logo_2.setImageResource(Unit_StringUtils.imagesCommon[16]);
            iv_sudoku_logo_3.setImageResource(Unit_StringUtils.imagesCommon[16]);

        } else if (itemCount == Const.COUNT_NINTH) {
            ll_item_tab_3.setVisibility(View.GONE);
            tv_item_name_1.setText(item_fourth_name1.get(0));
            tv_item_name_2.setText(item_fourth_name1.get(1));
            iv_sudoku_logo_1.setImageResource(Unit_StringUtils.imagesCommon[24]);
            iv_sudoku_logo_2.setImageResource(Unit_StringUtils.imagesCommon[25]);

        }
        /*暂无这个功能 =============== end ================*/
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

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v, int id) {
        fragmentTransaction = mFragmentManager.beginTransaction();
        int tepmOnclick_Count = 0;

        switch (v.getId()) {
            case R.id.ll_item_tab_1:
                tempCount = Const.COUNT_FIRST_ONE;
                string = Unit_StringUtils.returnActivityH5Url(itemCount, Const.COUNT_FIRST_ONE);
                fragmentTransaction.replace(R.id.framelayout, new First_Web_Fragment());
                ll_item_tab_1.setBackgroundColor(getResources().getColor(R.color.list_divider));
                ll_item_tab_2.setBackgroundColor(getResources().getColor(R.color.white));
                ll_item_tab_3.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case R.id.ll_item_tab_2:
                tempCount = Const.COUNT_FIRST_TWO;
                url_two = Unit_StringUtils.returnActivityH5Url(itemCount, Const.COUNT_FIRST_TWO);
                if (url_two.contains(IHttpService.CHECKING_CLOCK_URL)) {
                    Intent intent = new Intent(Unit_CommonConnectUnitActivity.this, FireInspectionActivity.class);
                    super.startActivity(intent);
                } else {
                    fragmentTransaction.replace(R.id.framelayout, new Second_Web_Fragment());
                }
                ll_item_tab_1.setBackgroundColor(getResources().getColor(R.color.white));
                ll_item_tab_2.setBackgroundColor(getResources().getColor(R.color.list_divider));
                ll_item_tab_3.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case R.id.ll_item_tab_3:
                tempCount = Const.COUNT_FIRST_THREE;
                url_third = Unit_StringUtils.returnActivityH5Url(itemCount, Const.COUNT_FIRST_THREE);
                if (url_third.contains(IHttpService.FIRE_DECUMENT_MANAGE_URL)) {
                    Intent intent = new Intent(Unit_CommonConnectUnitActivity.this, DecumentManageActivity.class);
                    super.startActivity(intent);
                } else {
                    fragmentTransaction.replace(R.id.framelayout, new Third_Web_Fragment());
                }
                ll_item_tab_1.setBackgroundColor(getResources().getColor(R.color.white));
                ll_item_tab_2.setBackgroundColor(getResources().getColor(R.color.white));
                ll_item_tab_3.setBackgroundColor(getResources().getColor(R.color.list_divider));
                break;
        }

        activityTitle = Unit_StringUtils.returnActivityTitle(itemCount, tempCount, item_first_Name,
                item_fourth_Name, item_fifth_Name, item_sixth_Name, item_seventh_Name, item_fourth_name1);
        tv_title.setText(activityTitle);
        fragmentTransaction.commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
