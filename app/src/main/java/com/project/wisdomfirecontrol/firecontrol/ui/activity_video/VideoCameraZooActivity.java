package com.project.wisdomfirecontrol.firecontrol.ui.activity_video;

import android.os.Bundle;
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

public class VideoCameraZooActivity extends BaseActivity {

  private VideoView mImageView;

    private UpdateViewThread mUpdateViewThread = null;
    private String tp = "";
    private String num;
    private TextView tv_title;


    @Override
    public int getLayoutRes() {
        return R.layout.avtivity_videocamerazoo;
    }

    @Override
    public void initView() {
        tv_title = findView(R.id.tv_title);
        mImageView = findView(R.id.imageView);

        tv_title.setText("视频监控");
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        num = bundle.getString("tp");
        int channel = bundle.getInt("channel");
        String channum = bundle.getString("channum");
        if (StringUtils.isEmpty(num)) {
            Global.showToast("终端号获取失败");
        } else {
            NetClient.Initialize();
            NetClient.SetDirSvr(IHttpService.VIDEO_IP, IHttpService.VIDEO_IP, 6605, 0);//114.55.118.196
            mImageView.setViewInfo(num, num, channel, channum);
            mImageView.StartAV();
            mUpdateViewThread = new UpdateViewThread();
            mUpdateViewThread.start();
        }
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v, int id) {
        switch (v.getId()) {
            case R.id.btn_back:
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        mUpdateViewThread.setExit(true);
        mUpdateViewThread = null;
        mImageView.StopAV();
        NetClient.UnInitialize();
        super.onDestroy();
    }


    public class UpdateViewThread extends Thread {
        private boolean isExit = false;
        private boolean isPause = false;

        void setExit(boolean isExit) {
            this.isExit = isExit;
        }

        public void setPause(boolean isPause) {
            this.isPause = isPause;
        }

        public void run() {
            while (!isExit) {
                try {
                    if (!isPause) {
                        mImageView.updateView();
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
}
