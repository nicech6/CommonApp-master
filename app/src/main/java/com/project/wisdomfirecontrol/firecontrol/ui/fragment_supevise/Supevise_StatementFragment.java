package com.project.wisdomfirecontrol.firecontrol.ui.fragment_supevise;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseFragment;
import com.project.wisdomfirecontrol.common.base.Const;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.util.SharedPreUtil;
import com.project.wisdomfirecontrol.firecontrol.ui.adapter_Rv.Supevise_StatementRvAdapter;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.GlideImageLoader;

/**
 * Created by Administrator on 2018/1/30.
 */

public class Supevise_StatementFragment extends BaseFragment implements OnBannerListener {
    private Banner banner;
    private String TAG = "tag";
    private List<?> images;
    private RecyclerView recyclerview;
    private TextView tv_app_company;
    public static List<String> listData;
    private Supevise_StatementRvAdapter statementAdapter;
    List<String> list = new ArrayList<>();
    private String imagthpath;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_statement;
    }

    @Override
    public void initView() {
        recyclerview = findView(R.id.recyclerview);
        tv_app_company = findView(R.id.tv_app_company);
        tv_app_company.setText(getResources().getString(R.string.home_text_company));

        GridLayoutManager manager = new GridLayoutManager(getActivity(), Const.COUNT_THREE);
        recyclerview.setLayoutManager(manager);
        String[] sudoku_h5 = getResources().getStringArray(R.array.home_sudoku);

        listData = Arrays.asList(sudoku_h5);

        if (statementAdapter == null) {
            statementAdapter = new Supevise_StatementRvAdapter(getActivity(), listData);
        }
        recyclerview.setAdapter(statementAdapter);
    }

    @Override
    public void initData() {
        imagthpath = SharedPreUtil.getString(Global.mContext, "imagthpath", "");
        if (TextUtils.isEmpty(imagthpath)) {
            list = useStytemResourceImages();
            bannerAnimotion(list);
        } else {
            String imageUrl = imagthpath.replace(" ", "");//去掉所用空格
            String[] split = imageUrl.split(",");
            list.addAll(Arrays.asList(split));
            bannerAnimotion(list);
        }

    }

    /*使用本地*/

    private List<String> useStytemResourceImages() {
        String path = "android.resource://" + getActivity().getPackageName() + "/" + R.drawable.baner_image_first;
        String path1 = "android.resource://" + getActivity().getPackageName() + "/" + R.drawable.banner_image_second;
        String path2 = "android.resource://" + getActivity().getPackageName() + "/" + R.drawable.banner_image_third;
        list.add(path);
        list.add(path1);
        list.add(path2);
        return list;
    }

    private void bannerAnimotion(List<String> list) {
        banner = (Banner) findView(R.id.banner);

        images = new ArrayList<>(list);

        //设置banner样式
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）

        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
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
    public void OnBannerClick(int position) {

    }
}
