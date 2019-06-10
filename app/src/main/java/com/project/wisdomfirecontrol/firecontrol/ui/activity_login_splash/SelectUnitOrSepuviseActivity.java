package com.project.wisdomfirecontrol.firecontrol.ui.activity_login_splash;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.client.RegisterClientActivity;
import com.mvp_0726.project_0726.login.ui.LoginActivity;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.common.base.Global;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Administrator on 2018/4/20.
 */

public class SelectUnitOrSepuviseActivity extends BaseActivity {

    private LinearLayout ll_title, ll_person_type;
    private GifImageView gifimage;
    private TextView tv_login_type_unit, tv_login_type_supevice, tv_spase;
    private RelativeLayout.LayoutParams params;
    private TextView mTextViewClient;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_selectunitorsepuvise;
    }

    @Override
    public void initView() {
        ll_title = findView(R.id.ll_title);
        tv_spase = findView(R.id.tv_spase);
        ll_title.setVisibility(View.GONE);
        ll_person_type = findView(R.id.ll_person_type);
        mTextViewClient = (TextView) findViewById(R.id.tv_login_type_client);

        ll_person_type.setBackgroundResource(R.drawable.bg);
        tv_login_type_unit = findView(R.id.tv_login_type_unit);
        tv_login_type_supevice = findView(R.id.tv_login_type_supevice);
        gifimage = findView(R.id.gifimage);

        boolean pad = Global.isPad();
        if (pad) {
            //两个400分别为添加图片的大小
            params = new RelativeLayout.LayoutParams((int) Global.mScreenWidth / 2,
                    (int) Global.mScreenWidth / 2);
            ViewGroup.LayoutParams layoutParams = tv_spase.getLayoutParams();
            layoutParams.height = (int) Global.mScreenWidth / 3;
            tv_spase.setLayoutParams(layoutParams);
        } else {
            //两个400分别为添加图片的大小
            params = new RelativeLayout.LayoutParams((int) Global.mScreenWidth,
                    (int) Global.mScreenWidth);
        }
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        gifimage.setLayoutParams(params);

        gifimage.setImageResource(R.drawable.gifphoto);

        AlphaAnimation alp = new AlphaAnimation(0.0f, 1.0f);
        AlphaAnimation alp1 = new AlphaAnimation(0.0f, 1.0f);
        alp.setDuration(3000);
        alp1.setDuration(4000);
        tv_login_type_unit.setAnimation(alp);
        tv_login_type_supevice.setAnimation(alp1);
        alp.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
            }
        });
        alp1.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
            }
        });
    }

    @Override
    public void initListener() {
        tv_login_type_unit.setOnClickListener(this);
        tv_login_type_supevice.setOnClickListener(this);
        mTextViewClient.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v, int id) {
        switch (v.getId()) {
            case R.id.tv_login_type_unit:
                login();
                break;
            case R.id.tv_login_type_supevice:
                register();
                break;
            case R.id.tv_login_type_client:
                registerclient();
                break;
        }
    }

    private void registerclient() {
        Intent intent = new Intent(SelectUnitOrSepuviseActivity.this, RegisterClientActivity.class);
        super.startActivity(intent);
    }

    private void login() {
        Intent intent = new Intent(SelectUnitOrSepuviseActivity.this, LoginActivity.class);
        super.startActivity(intent);
        finish();
    }

    /*注册*/
    private void register() {
        Intent intent = new Intent(SelectUnitOrSepuviseActivity.this, RegisterFirstActivity.class);
        super.startActivity(intent);
    }


    /**
     * 重写返回键，实现双击退出效果
     */

    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

}
