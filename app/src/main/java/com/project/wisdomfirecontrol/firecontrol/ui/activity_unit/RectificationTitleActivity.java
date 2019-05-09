package com.project.wisdomfirecontrol.firecontrol.ui.activity_unit;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.common.base.Const;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.base.UserInfo;
import com.project.wisdomfirecontrol.common.base.UserManage;
import com.project.wisdomfirecontrol.firecontrol.base.MyApplication;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.SubmitBean;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.CommonProtocol;
import com.project.wisdomfirecontrol.firecontrol.ui.adapter_Rv.GridImageAdapter;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.PhotoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/23.
 */

public class RectificationTitleActivity extends BaseActivity {

    private static final String TAG = "tag";
    private TextView tv_title, tv_title_des, tv_submit;
    private EditText et_title_des;
    private String title, gwid;
    private List<LocalMedia> selectList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PhotoUtils photoUtils;
    private CommonProtocol commonProtocol;
    private String imageurl;
    private StringBuffer stringBuffer;
    private String title_des;
    private String userid;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_rectificationdocument_title;
    }

    @Override
    public void initView() {
        tv_title = findView(R.id.tv_title);
        tv_title.setText(R.string.title_zhenggai);
        tv_title_des = findView(R.id.tv_title_des);
        et_title_des = findView(R.id.et_title_des);
        tv_submit = findView(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
        tv_title_des.setMovementMethod(new ScrollingMovementMethod());
        recyclerView = findView(R.id.recycler);
        if (photoUtils == null) {
            photoUtils = new PhotoUtils(this, recyclerView,
                    onAddPicClickListener, selectList, tv_submit,
                    Const.MAXSELECTNUM_SIX, Const.COUNT_FOURTH, true);
        }
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            requestPermission(1, Manifest.permission.CAMERA, new Runnable() {
                @Override
                public void run() {
                    if (photoUtils != null) {
                        photoUtils.showSelectVideoOrPhoto("photo", "activity");
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
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        title = bundle.getString("title");
        gwid = bundle.getString("gwid");
        Log.d(TAG, "initData: " + title + " ++ " + gwid);
        if (!TextUtils.isEmpty(title) && tv_title_des != null) {
            tv_title_des.setText(title);
        }
    }

    @Override
    public void onClick(View v, int id) {
        switch (v.getId()) {
            case R.id.tv_submit:
                tv_submit();
                break;
        }

    }

    //    提交
    private void tv_submit() {
        title_des = et_title_des.getText().toString().trim();
        if (TextUtils.isEmpty(title_des)) {
            showToast(getString(R.string.trouble_des));
            return;
        }

        boolean hasUserInfo = UserManage.getInstance().hasUserInfo(Global.mContext);
        if (hasUserInfo) {
            UserInfo userIdInfo = UserManage.getInstance().getUserIdInfo(Global.mContext);
            userid = userIdInfo.getUserid();
            if (TextUtils.isEmpty(userid)) {
                showToast(getString(R.string.your_idea));
                return;
            }
        }
        boolean networkConnected = MyApplication.getInstance().isNetworkConnected();
        if (!networkConnected) {
            showToast(getResources().getString(R.string.no_net));
            return;
        }
        commonProtocol = new CommonProtocol();
        showWaitDialog(this, getString(R.string.submit_in));
        Log.d(TAG, "tv_submit: " + userid + " +++ " + title_des + " +++ " + gwid + "\r\n +++ " + imageurl);
        commonProtocol.rectificationTitle(this, gwid, userid, title_des, imageurl);

    }

    @Override
    public void onHttpSuccess(int reqType, Message msg) {
        super.onHttpSuccess(reqType, msg);
        dismissWaitDialog();
        SubmitBean bean = (SubmitBean) msg.obj;
        Log.d(TAG, "onHttpSuccess: " + bean.getMsg());
        showSuccessDialog(this, getString(R.string.success_submit));
    }

    @Override
    public void onHttpError(int reqType, String error) {
        super.onHttpError(reqType, error);
        dismissWaitDialog();
        Log.d(TAG, "onHttpError: " + error);
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
                        showWaitDialog(RectificationTitleActivity.this, "图片处理中...");
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
