package com.project.wisdomfirecontrol.firecontrol.ui.activity_common;

import android.Manifest;
import android.content.Intent;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.common.base.Const;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.firecontrol.base.MyApplication;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.ImgNumBean;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.CommonProtocol;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.IHttpService;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_unit.HistoryDecumentActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.adapter_Rv.GridImageAdapter;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.PhotoUtils;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.Unit_StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/23.
 */

public class DecumentManageActivity extends BaseActivity {

    private static final String TAG = "tag";
    private TextView tv_title, tv_title_right, tv_title_des, tv_submit, tv_net_again;
    private LinearLayout ll_decument;
    private String title, gwid;
    private List<LocalMedia> selectList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PhotoUtils photoUtils;
    private CommonProtocol commonProtocol;
    private String imageurl;
    private StringBuffer stringBuffer;
    private String title_des;
    private String pid;
    private int photoNum;
    private String userid;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_decumentmanage;
    }

    @Override
    public void initView() {
        tv_title = findView(R.id.tv_title);
        ll_decument = findView(R.id.ll_decument);
        ll_decument.setVisibility(View.VISIBLE);
        tv_net_again = findView(R.id.tv_net_again);
        tv_net_again.setText(getString(R.string.update_again));
        tv_net_again.setOnClickListener(this);
        tv_title_right = findView(R.id.tv_title_right);
        tv_title_right.setVisibility(View.VISIBLE);
        tv_title_right.setBackgroundColor(getResources().getColor(R.color.mvp_title_blue));
        tv_title.setText(R.string.title_decumentmanage);
        tv_title_right.setText(R.string.title_right_txt_history);
        tv_title_right.setOnClickListener(this);
        tv_title_des = findView(R.id.tv_title_des);
        tv_submit = findView(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
        tv_title_des.setMovementMethod(new ScrollingMovementMethod());
        recyclerView = findView(R.id.recycler);
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            requestPermission(1, Manifest.permission.CAMERA, new Runnable() {
                @Override
                public void run() {
                    if (photoUtils != null) {
                        photoUtils.showSelectVideoOrPhoto("photo","activity");
                    }
                }
            }, new Runnable() {
                @Override
                public void run() {
                    Global.showToast(getResources().getString(R.string.no_photo_perssion));
                }
            });
        }

    };

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
    }

    @Override
    protected void onStart() {
        super.onStart();

        pid = Unit_StringUtils.getUserPid(this);
        if (TextUtils.isEmpty(pid)) {
            return;
        }
        boolean networkConnected = MyApplication.getInstance().isNetworkConnected();
        if (!networkConnected) {
            showToast(getResources().getString(R.string.no_net));
            return;
        }
        commonProtocol = new CommonProtocol();
        showWaitDialog(this, getString(R.string.inupdate));
        commonProtocol.getImgNum(this, pid);
    }

    @Override
    public void onClick(View v, int id) {
        switch (v.getId()) {
            case R.id.tv_submit:
                tv_submit();
                break;
            case R.id.tv_net_again:
                tv_submit();
                break;
            case R.id.tv_title_right:
                tv_title_right();
                break;
        }

    }

    private void tv_title_right() {
        boolean networkConnected = MyApplication.getInstance().isNetworkConnected();
        if (!networkConnected) {
            showToast(getResources().getString(R.string.no_net));
            return;
        }
        pid = Unit_StringUtils.getUserPid(this);
        Intent intent = new Intent(this, HistoryDecumentActivity.class);
        intent.putExtra("pid", pid);
        startActivity(intent);
    }

    //    提交
    private void tv_submit() {
        boolean networkConnected = MyApplication.getInstance().isNetworkConnected();
        if (!networkConnected) {
            showToast(getResources().getString(R.string.no_net));
            return;
        }
        tv_net_again.setVisibility(View.GONE);
        ll_decument.setVisibility(View.VISIBLE);
        userid = Unit_StringUtils.getUserId(this);
        if (TextUtils.isEmpty(imageurl)) {
            showToast(getString(R.string.select_photos));
            return;
        }
        commonProtocol = new CommonProtocol();
        showWaitDialog(this, getString(R.string.submit_in));

        commonProtocol.saveOrganImg(this, userid, pid, imageurl);

    }

    @Override
    public void onHttpSuccess(int reqType, Message msg) {
        super.onHttpSuccess(reqType, msg);
        Log.d(TAG, "onHttpSuccess: " + reqType);
        dismissWaitDialog();
        if (reqType == IHttpService.TYPE_IMG_NUM) {
            ImgNumBean bean = (ImgNumBean) msg.obj;
            int data = bean.getData();
            if (data < Const.MAXSELECTNUM_NINE) {
                tv_title_des.setText(R.string.update_decument);
                tv_title_des.setTextColor(getResources().getColor(R.color.black));
            } else {
                tv_title_des.setText(R.string.history_delect);
                tv_title_des.setTextColor(getResources().getColor(R.color.red));
            }
            photoNum = Const.MAXSELECTNUM_NINE - data;
            Log.d(TAG, "onHttpSuccess: 2" + data + "+++ " + photoNum);
            photoUtils = new PhotoUtils(this, recyclerView, onAddPicClickListener, selectList
                    , tv_submit, photoNum,Const.COUNT_FOURTH,true);
        }
        if (reqType == IHttpService.TYPE_IMG_SUBMIT_NUM) {
            showSuccessDialog(this, getString(R.string.success_submit));
        }

    }

    @Override
    public void onHttpError(int reqType, String error) {
        super.onHttpError(reqType, error);
        if (reqType == IHttpService.TYPE_IMG_NUM) {
            tv_net_again.setVisibility(View.VISIBLE);
            ll_decument.setVisibility(View.GONE);
        }
        dismissWaitDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList.size() > 0) {
                        showWaitDialog(DecumentManageActivity.this, "图片处理中...");
                        imageurl = reducePhoto(selectList);
                        Log.d(TAG, "onActivityResult: " + imageurl);
                    }
                    if (photoUtils != null) {
                        photoUtils.setList(selectList);
                        photoUtils.notifyChanged();
                    }
                    break;
            }
        }
    }

    public String reducePhoto(final List<LocalMedia> dataList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (stringBuffer == null) {
                    stringBuffer = new StringBuffer();
                }
                stringBuffer.setLength(0);
                for (int i = 0; i < dataList.size(); i++) {
                    if (i == (dataList.size() - 1)) {
                        stringBuffer.append(com.project.wisdomfirecontrol.common.util.StringUtils.getimage(dataList.get(i).getCompressPath()));
                    } else {
                        stringBuffer.append(com.project.wisdomfirecontrol.common.util.StringUtils.getimage(dataList.get(i).getCompressPath())).append(",");
                    }
                }
                imageurl = "";
                imageurl = stringBuffer.toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissWaitDialog();
                    }
                });
            }
        }).start();

        return imageurl;
    }
}
