package com.project.wisdomfirecontrol.firecontrol.ui.activity_video;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.base.UserManage;
import com.project.wisdomfirecontrol.firecontrol.model.bean.video.ChangeVideoEquipmentDataBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.video.VideoEquipmentBean;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.CommonProtocol;
import com.project.wisdomfirecontrol.firecontrol.treesList.Node;
import com.project.wisdomfirecontrol.firecontrol.treesList.OnTreeNodeClickListener;
import com.project.wisdomfirecontrol.firecontrol.treesList.adapter.SimpleTreeRecyclerAdapter;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.ChangeDatasUtils;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.Unit_StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/4/28.
 */

public class VideoSytemListActivity extends BaseActivity {

    private static final String TAG = "tag";
    private TextView tv_title;
    private String pid;

    private RecyclerView recyclerview;
    private String type;
    private SimpleTreeRecyclerAdapter mAdapter;
    private CommonProtocol mCommonProtocol;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_videosytemlist;
    }

    @Override
    public void initView() {
        tv_title = findView(R.id.tv_title);
        recyclerview = findView(R.id.recyclerview);
        recyclerview.setVisibility(View.VISIBLE);
        tv_title.setText("视频设备列表");
        type = UserManage.getInstance().getUserType(Global.mContext).getType();
//        if (type.contains("1")) {
//            lv_videolist.setVisibility(View.GONE);
//
//        } else {
//            lv_videolist.setVisibility(View.VISIBLE);
//            recyclerview.setVisibility(View.GONE);
//        }
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

        getEquipmentCount();
    }

    @Override
    public void onClick(View v, int id) {

    }

    //    获取视频列表条数
    private void getEquipmentCount() {
        mCommonProtocol = new CommonProtocol();
        pid = Unit_StringUtils.getUserPid(Global.mContext);
        showWaitDialog(VideoSytemListActivity.this, getResources().getString(R.string.inupdate));
//        RetrofitManager.changeApiBaseUrl(IHttpService.HOST_URL_CHANGE);
        mCommonProtocol.getvideoequipment(this, pid, type);
    }

    @Override
    public void onHttpSuccess(int reqType, Message obj) {
        super.onHttpSuccess(reqType, obj);
        dismissWaitDialog();
        VideoEquipmentBean bean = (VideoEquipmentBean) obj.obj;
        List<ChangeVideoEquipmentDataBean> beanList = bean.getData();
        if (beanList.size() > 0) {
            initRVdatas(beanList);
        }

    }

    private void initRVdatas(List<ChangeVideoEquipmentDataBean> beanList) {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
//        //第一个参数  RecyclerView
//        //第二个参数  上下文
//        //第三个参数  数据集
//        //第四个参数  默认展开层级数 0为不展开
//        //第五个参数  展开的图标
//        //第六个参数  闭合的图标
        List<Node> mDatas = ChangeDatasUtils.ReturnTreesDatas(beanList);

        mAdapter = new SimpleTreeRecyclerAdapter(recyclerview, this,
                mDatas, 0, R.drawable.tree_open, R.drawable.tree_close,true);

        recyclerview.setAdapter(mAdapter);

        mAdapter.setOnTreeNodeClickListener(new OnTreeNodeClickListener() {
            @Override
            public void onClick(Node node, int position) {
                showSelectItemDatas();
            }

        });
    }

    private void showSelectItemDatas() {
        String tenNum = "";
        if (mAdapter == null) {
            return;
        }
        final List<Node> allNodes = mAdapter.getAllNodes();
        for (int i = 0; i < allNodes.size(); i++) {
            if (allNodes.get(i).isChecked()) {
                tenNum = allNodes.get(i).getCount();
            }
        }
        if (!TextUtils.isEmpty(tenNum)) {
            Intent intent = new Intent();
            intent.setClass(VideoSytemListActivity.this, VideoCameraActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("tp", tenNum);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public void onHttpError(int reqType, String error) {
        super.onHttpError(reqType, error);
        dismissWaitDialog();
        showToast(error);
    }


}
