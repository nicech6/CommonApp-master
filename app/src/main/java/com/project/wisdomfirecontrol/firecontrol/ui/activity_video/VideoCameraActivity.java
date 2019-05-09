package com.project.wisdomfirecontrol.firecontrol.ui.activity_video;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.babelstar.gviewer.NetClient;
import com.babelstar.gviewer.VideoView;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.util.StringUtils;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.IHttpService;

/**
 * Created by Administrator on 2017/7/2.
 */

public class VideoCameraActivity extends BaseActivity {

    private TextView tv_title;
    private VideoView mImageView1;
    private VideoView mImageView2;
    private VideoView mImageView3;
    private VideoView mImageView4;

    private UpdateViewThread mUpdateViewThread = null;
    private String num;


    @Override
    public int getLayoutRes() {
        return R.layout.avtivity_videocamera;
    }

    @Override
    public void initView() {

        tv_title = findView(R.id.tv_title);
        mImageView1 = findView(R.id.imageView1);
        mImageView2 = findView(R.id.imageView2);
        mImageView3 = findView(R.id.imageView3);
        mImageView4 = findView(R.id.imageView4);
        tv_title.setText("视频监控");
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        num = bundle.getString("tp");
        Log.v("tag", "  videoterminalNO " + num);
        if (StringUtils.isEmpty(num)) {
            Global.showToast("终端号获取失败");
        } else {

            NetClient.Initialize();
            NetClient.SetDirSvr(IHttpService.HOST_IP, IHttpService.HOST_IP, 6605, 0);//114.55.118.196
            mImageView1.setViewInfo(num, num, 0, "CH1");
            mImageView1.StartAV();
            mImageView2.setViewInfo(num, num, 1, "CH2");
            mImageView3.setViewInfo(num, num, 2, "CH3");
            mImageView4.setViewInfo(num, num, 3, "CH4");
            mImageView2.StartAV();
            mImageView3.StartAV();
            mImageView4.StartAV();
            mUpdateViewThread = new UpdateViewThread();
            mUpdateViewThread.start();
        }

    }

    @Override
    public void initListener() {
        mImageView1.setOnClickListener(this);
        mImageView2.setOnClickListener(this);
        mImageView3.setOnClickListener(this);
        mImageView4.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v, int id) {
        switch (v.getId()) {
            case R.id.imageView1:
                showFirstImageView();
                break;
            case R.id.imageView2:
                showSecondImageView();
                break;
            case R.id.imageView3:
                showThirdImageView();
                break;
            case R.id.imageView4:
                showFourthImageView();
                break;
        }

    }

    private void showFirstImageView() {
        jumpZooVideoCamera(0, "CH1");
    }

    private void showSecondImageView() {
        jumpZooVideoCamera(1, "CH2");
    }

    private void showThirdImageView() {
        jumpZooVideoCamera(2, "CH3");
    }

    private void showFourthImageView() {
        jumpZooVideoCamera(3, "CH4");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void jumpZooVideoCamera(int channel, String channum) {
        if (!StringUtils.isEmpty(num)) {
            Intent intent = new Intent(VideoCameraActivity.this, VideoCameraZooActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("tp", num);
            bundle.putInt("channel", channel);
            bundle.putString("channum", channum);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    /*
     */
    public class UpdateViewThread extends Thread {
        private boolean isExit = false;
        private boolean isPause = false;

        public void setExit(boolean isExit) {
            this.isExit = isExit;
        }

        public void setPause(boolean isPause) {
            this.isPause = isPause;
        }

        public void run() {
            while (!isExit) {
                try {
                    if (!isPause) {
                        mImageView1.updateView();
                        mImageView2.updateView();
                        mImageView3.updateView();
                        mImageView4.updateView();
                        Thread.sleep(20);
                    } else {
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.isExit = true;
        }
    }

    @Override
    protected void onDestroy() {
        mUpdateViewThread.setExit(true);
        mUpdateViewThread = null;
        mImageView1.StopAV();
        mImageView2.StopAV();
        mImageView3.StopAV();
        mImageView4.StopAV();
        NetClient.UnInitialize();
        super.onDestroy();
    }
}
