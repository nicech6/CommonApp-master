package com.project.wisdomfirecontrol.firecontrol.ui.activity_unit;

import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.common.base.Const;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.util.SharedPreUtil;
import com.project.wisdomfirecontrol.firecontrol.base.MyApplication;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.EquipmentCount;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.GetCompanyRegisterInfosBean;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.CommonProtocol;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.IHttpService;
import com.project.wisdomfirecontrol.firecontrol.ui.adapter_Rv.Unit_StatementAdapter;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.DatasUtils;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.GlideImageLoader;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.Unit_StringUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */

public class Unit_StatementChangeActivity extends BaseActivity {
    private Banner banner;
    private String TAG = "tag";
    private List<?> images;
    private RecyclerView recyclerview;
    private TextView tv_app_company;
    public static List<String> listData;
    private Unit_StatementAdapter statementAdapter;
    private String imagthpath;
    List<String> list = new ArrayList<>();
    public static List<String> listCount = new ArrayList<>();
    private String pid;
    private CommonProtocol commonProtocol;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_statement;
    }

    @Override
    public void initView() {
        recyclerview = findView(R.id.recyclerview);
        tv_app_company = findView(R.id.tv_app_company);
        tv_app_company.setText(getResources().getString(R.string.home_text_company));
        GridLayoutManager manager = new GridLayoutManager(Unit_StatementChangeActivity.this, Const.COUNT_THREE);
        recyclerview.setLayoutManager(manager);
        String[] sudoku_h5 = getResources().getStringArray(R.array.home_sudoku_item);

        listData = Arrays.asList(sudoku_h5);

        if (statementAdapter == null) {
            statementAdapter = new Unit_StatementAdapter(Unit_StatementChangeActivity.this, listData);
        }
//        recyclerview.setAdapter(statementAdapter);
    }

    @Override
    public void initData() {
        imagthpath = SharedPreUtil.getString(Global.mContext, "imagthpath", "");
        if (TextUtils.isEmpty(imagthpath)) {
            list = useStytemResourceImages();
        } else {
            String imageUrl = imagthpath.replace(" ", "");//去掉所用空格
            list.clear();
            String[] split = imageUrl.split(",");
            list.addAll(Arrays.asList(split));
        }
        bannerAnimotion(list);
        getCompanyInfosDatas();
    }


    private void getCompanyInfosDatas() {
        pid = DatasUtils.getUserPid(this);
        commonProtocol = new CommonProtocol();
        commonProtocol.getorgandetailbyid(this, pid);
    }


    //    获取条数
    private void getEquipmentCount() {

        boolean networkConnected = MyApplication.getInstance().isNetworkConnected();
        if (!networkConnected) {
            showToast("请检查网络是否正常");
        }
        commonProtocol = new CommonProtocol();
        pid = Unit_StringUtils.getUserPid(Global.mContext);
        showWaitDialog(Unit_StatementChangeActivity.this, getResources().getString(R.string.inupdate));
        commonProtocol.getequipmentcount(this, pid);
    }

    /*使用本地*/

    private List<String> useStytemResourceImages() {
        String path = "android.resource://" + getPackageName() + "/" + R.drawable.baner_image_first;
        String path1 = "android.resource://" + getPackageName() + "/" + R.drawable.banner_image_second;
        String path2 = "android.resource://" + getPackageName() + "/" + R.drawable.banner_image_third;
        list.clear();
        list.add(path);
        list.add(path1);
        list.add(path2);
        return list;
    }

    private void bannerAnimotion(List<String> list) {
        banner = (Banner) findView(R.id.banner);
        images = new ArrayList<>(list);
        Log.d(TAG, "bannerAnimotion: " + images.size());
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
//        //设置标题集合（当banner样式有显示title时）

//        banner.setBannerTitles(titles);
//        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        if (banner != null) {
            banner.startAutoPlay();
        }
        getEquipmentCount();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        if (banner != null) {
            banner.stopAutoPlay();
        }
    }

    @Override
    public void initListener() {

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
        dismissWaitDialog();
        if (reqType == IHttpService.TYPE_TROUBLE_TYPE_NUM) {
            EquipmentCount bean = (EquipmentCount) obj.obj;
            if (bean == null) {
                return;
            }
            EquipmentCount.DataBean data = bean.getData();
            listCount.clear();
            listCount.add(String.valueOf(data.getSensorcount()));
            listCount.add(String.valueOf(data.getBaojingcount()));
            listCount.add(String.valueOf(data.getGuzhangcount()));

            recyclerview.setAdapter(statementAdapter);
            if (statementAdapter != null || listCount.size() == 3) {
                statementAdapter.notifyDataSetChanged();
            }
        } else {
            GetCompanyRegisterInfosBean bean = (GetCompanyRegisterInfosBean) obj.obj;
            if (bean != null) {
                String companyName = bean.getData().getOrgName();
                String principal = bean.getData().getPrincipal();
                String principalTel = bean.getData().getPrincipalTel();
                String firstName = bean.getData().getName1();
                String firstPhone = bean.getData().getPhone1();
                String address = bean.getData().getAddress();
                if (!TextUtils.isEmpty(companyName) && !TextUtils.isEmpty(principal) &&
                        !TextUtils.isEmpty(principalTel) && !TextUtils.isEmpty(address)
                        && !TextUtils.isEmpty(firstPhone) && !TextUtils.isEmpty(firstPhone)) {
                    SharedPreUtil.saveString(Global.mContext, "address", address);
                    SharedPreUtil.saveString(Global.mContext, "companyName", companyName);
                    SharedPreUtil.saveString(Global.mContext, "principal", principal);
                    SharedPreUtil.saveString(Global.mContext, "principalTel", principalTel);
                    SharedPreUtil.saveString(Global.mContext, "firstName", firstName);
                    SharedPreUtil.saveString(Global.mContext, "firstPhone", firstPhone);
                }
            }
        }
    }

    @Override
    public void onHttpError(int reqType, String error) {
        super.onHttpError(reqType, error);
        dismissWaitDialog();
//        showToast(error);
        recyclerview.setAdapter(statementAdapter);
    }

    /**
     * 重写返回键，实现双击退出效果
     */

    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                showToast(this.getResources().getString(R.string.btn_again_out));
                exitTime = System.currentTimeMillis();
            } else {
                Unit_StatementChangeActivity.this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
