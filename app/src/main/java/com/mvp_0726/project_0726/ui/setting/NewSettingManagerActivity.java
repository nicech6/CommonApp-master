package com.mvp_0726.project_0726.ui.setting;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mvp_0726.project_0726.ui.adapter.MultiNewManageAdapter;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.base.UserInfo;
import com.project.wisdomfirecontrol.common.base.UserManage;
import com.project.wisdomfirecontrol.common.util.SharedPreUtil;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.SubmitBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.setting.SettingManagerBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.setting.SettingManagerDataBean;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.CommonProtocol;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.IHttpService;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.RetrofitManager;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_setting.MonitoringAreaInfosActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_setting.NewAddMonitoringAreaFirstActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_setting.QRcodeActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_setting.SettingInfosActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.DatasUtils;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.PhotoUtils;
import com.project.wisdomfirecontrol.firecontrol.ui.view.dialog.SuccessDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/18.
 * 二期设备管理
 */

public class NewSettingManagerActivity extends BaseActivity {

    private static final String TAG = "tag";
    private TextView tv_title_right, tv_title_right_des, tv_no_datas;
    private RecyclerView recyclerview;
    private TextView btn_add, tv_item_time, tv_item_name, tv_item_type, tv_item_type_name;
    private MultiNewManageAdapter adapter;
    private SuccessDialog successDialog;
    private CommonProtocol commonProtocol;
    private String pid;
    private StringBuffer stringBuffer;
    private String sensorid;
    private List<SettingManagerDataBean> listData = new ArrayList<>();

    private Button btn_all_select, btn_all_delect;
    private LinearLayout ll_select;

    private boolean isLongClick = false;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_newsettingmanager;
    }

    @Override
    public void initView() {
        title.setText("设备管理");
        tv_title_right = findView(R.id.tv_title_right);
        tv_title_right_des = findView(R.id.tv_title_right_des);
        tv_title_right.setVisibility(View.GONE);
        tv_title_right_des.setVisibility(View.VISIBLE);
        tv_title_right_des.setText("选择");

        recyclerview = findView(R.id.recyclerview);

        tv_no_datas = findView(R.id.tv_no_datas);
        btn_add = findView(R.id.btn_add);
        btn_add.setText("新增");

        tv_item_name = findView(R.id.tv_item_name);
        tv_item_type = findView(R.id.tv_item_type);
        tv_item_time = findView(R.id.tv_item_time);
        tv_item_type_name = findView(R.id.tv_item_type_name);


        ll_select = findView(R.id.ll_select);
        btn_all_select = findView(R.id.btn_all_select);
        btn_all_delect = findView(R.id.btn_all_delect);

        isSelectOrNor = true;
        showFourTv(tv_item_name, tv_item_type, tv_item_time, tv_item_type_name);
//        initRecycleView();
    }


    private void showFourTv(TextView tv_item_name, TextView tv_item_type,
                            TextView tv_item_time, TextView tv_item_type_name) {

        ViewGroup.LayoutParams layoutParams0 = tv_item_name.getLayoutParams();
        layoutParams0.width = (int) Global.mScreenWidth / 4;
        tv_item_name.setLayoutParams(layoutParams0);

        ViewGroup.LayoutParams layoutParams1 = tv_item_type.getLayoutParams();
        layoutParams1.width = (int) Global.mScreenWidth / 4;
        tv_item_type.setLayoutParams(layoutParams1);

        ViewGroup.LayoutParams layoutParams2 = tv_item_time.getLayoutParams();
        layoutParams2.width = (int) Global.mScreenWidth / 4;
        tv_item_time.setLayoutParams(layoutParams2);

        ViewGroup.LayoutParams layoutParams3 = tv_item_type_name.getLayoutParams();
        layoutParams3.width = (int) Global.mScreenWidth / 4;
        tv_item_type_name.setLayoutParams(layoutParams3);
    }

    @Override
    public void initListener() {
        tv_title_right_des.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_all_select.setOnClickListener(this);
        btn_all_delect.setOnClickListener(this);
    }

    @Override
    public void initData() {
//        getCompanyInfosDatas();
        getNetDatas();
    }

    private void getCompanyInfosDatas() {
        pid = DatasUtils.getUserPid(this);
        commonProtocol = new CommonProtocol();
        commonProtocol.getorgandetailbyid(this, pid);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void getNetDatas() {
        pid = DatasUtils.getUserPid(this);
        showWaitDialog(this, getString(R.string.inupdate));
        commonProtocol = new CommonProtocol();
//        pid = "yun";
//        Log.d(TAG, "getNetDatas: " + pid);
//        RetrofitManager.changeApiBaseUrl(NetworkUrl.ANDROID_BAIDU_SERVICE);
        UserInfo userIdInfo = UserManage.getInstance().getUserIdInfo(com.project.wisdomfirecontrol.common.base.Global.mContext);
        String userid = userIdInfo.getUserid();
        if (1 == SharedPreUtil.getInt(this, "isuser", 0)) {
            commonProtocol.getClientsensor(this, userid);
        } else {
            commonProtocol.getsensor(this, pid);
        }
    }

    @Override
    public void initTitleHeight(LinearLayout linearLayout) {

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v, int id) {
        switch (v.getId()) {
            case R.id.tv_title_right_des:
                tv_title_right(v);
                break;
            case R.id.btn_add:
                btn_add(v);
                break;
            case R.id.btn_take_photo:
                newAddSeeting();
                break;
            case R.id.btn_from_gallery:
                newAddLookArea();
                break;
            case R.id.btn_Pop_exit:
                popWindowDismiss();
                break;

            case R.id.btn_all_select:
                btn_all_select();
                break;
            case R.id.btn_all_delect:
                btn_all_delect();
                break;
        }
    }

    /*删除*/
    private void btn_all_delect() {
//        showToast("删除");
        tv_title_right_des.setText("选择");
        isSelectOrNor = true;
        ll_select.setVisibility(View.GONE);
        btn_add.setVisibility(View.VISIBLE);
        isLongClick = false;
        adapter.isShow = false;
        adapter.notifyDataSetChanged();

        isDelectSelectorSetting();
    }

    /*全选*/
    private void btn_all_select() {
        selectDatas.clear();
        for (int i = 0; i < listData.size(); i++) {
            MultiNewManageAdapter.isSelected.put(i, true);
            selectDatas.add(listData.get(i).getSensorid());
        }
        adapter.notifyDataSetChanged();
    }

    /*新增监控区域*/
    private void newAddLookArea() {
        popWindowDismiss();
        SettingManagerDataBean settingManagerDataBean = null;
        Intent intent = new Intent();
        intent.setClass(this, NewAddMonitoringAreaFirstActivity.class);
        intent.putExtra("settingManagerDataBean", settingManagerDataBean);
        super.startActivity(intent);
    }

    /*新增设备*/
    private void newAddSeeting() {
        popWindowDismiss();
        Intent intent = new Intent();
        intent.setClass(this, QRcodeActivity.class);
        super.startActivity(intent);
    }

    /*取消*/
    private void popWindowDismiss() {
        if (popWindow != null) {
            popWindow.dismiss();
        }
    }

    /*新增*/
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void btn_add(View view) {
        if (!isSelectOrNor) {
            if (adapter != null) {
                tv_item_time.setVisibility(View.VISIBLE);
                tv_title_right_des.setTextColor(getResources().getColor(R.color.white));
                btn_add.setText("新增");
                tv_title_right_des.setText("选择");
                isSelectOrNor = true;
            }
        } else {
            initPopupWindow(this);
        }
    }

    private View mPopView;
    private PopupWindow popWindow;
    private TextView mBtn_take_photo;
    private TextView mBtn_from_gallery;
    private TextView mBtn_pop_exit;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initPopupWindow(Context context) {
        mPopView = Global.inflate(R.layout.layout_photo_upload, null);
        popWindow = PhotoUtils.initPopWindow(context, mPopView, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        mBtn_take_photo = (TextView) mPopView.findViewById(R.id.btn_take_photo);
        mBtn_from_gallery = (TextView) mPopView.findViewById(R.id.btn_from_gallery);
        mBtn_pop_exit = (TextView) mPopView.findViewById(R.id.btn_Pop_exit);

        mBtn_take_photo.setTextColor(getResources().getColor(R.color.blue));
        mBtn_from_gallery.setTextColor(getResources().getColor(R.color.blue));
        mBtn_pop_exit.setTextColor(getResources().getColor(R.color.red));

        mBtn_take_photo.setText("新增设备");
        mBtn_from_gallery.setText("新增监控区");

        mBtn_take_photo.setOnClickListener(this);
        mBtn_from_gallery.setOnClickListener(this);
        mBtn_pop_exit.setOnClickListener(this);

        PhotoUtils.showPopupWindwShowType(popWindow, btn_add, true, true);
    }

    private boolean isSelectOrNor = true;

    /*选择*/
    private void tv_title_right(View view) {

        if (adapter != null) {
            if (isSelectOrNor) {
                btn_add.setVisibility(View.GONE);
                ll_select.setVisibility(View.VISIBLE);
                tv_title_right_des.setText("取消");
                tv_title_right_des.setTextColor(getResources().getColor(R.color.red));
                isSelectOrNor = false;
                showDelectWhat(1);
            } else {
                isSelectOrNor = true;
                tv_title_right_des.setText("选择");
                showDelectWhat(2);

                ll_select.setVisibility(View.GONE);
                btn_add.setVisibility(View.VISIBLE);
            }
        }
    }

    private void showDelectWhat(int type) {
        if (type == 1) {
            isLongClick = true;
            adapter.isShow = true;
            adapter.notifyDataSetChanged();
        } else {
            isLongClick = false;
            adapter.isShow = false;
            selectDatas.clear();
            for (int i = 0; i < listData.size(); i++) {
                MultiNewManageAdapter.isSelected.put(i, false);
            }
            adapter.notifyDataSetChanged();
        }

//        layout_delete.setVisibility(View.VISIBLE);
//        layout_select.setVisibility(View.VISIBLE);


    }

    private void isDelectSelectorSetting() {
        sensorid = "";
        if (stringBuffer == null) {
            stringBuffer = new StringBuffer();
        }
        stringBuffer.setLength(0);
        int size = selectDatas.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                if (i == (size - 1)) {
                    stringBuffer.append(selectDatas.get(i));
                } else {
                    stringBuffer.append(selectDatas.get(i)).append(",");
                }
            }
            sensorid = stringBuffer.toString().trim();
            showDelectDialog(sensorid, "确认删除已选设备?" + "\n" + "删除后将无法恢复", true);
        } else {
            showToast("请先选择设备");
        }
    }

    /*删除*/
    private void showDelectDialog(final String sensorid, String txt, final boolean isAreaOrSetting) {
        if (successDialog == null) {
            successDialog = new SuccessDialog(this);
        }
        successDialog.setContent(txt);
//        successDialog.hideCancle();
        successDialog.setDialogCallback(new SuccessDialog.Dialogcallback() {
            @Override
            public void dialogdo(LinearLayout container) {
                if (!isFinishing()) {
                    successDialog.dismiss();
                }
                if (isAreaOrSetting) {
                    delectSettingWhat(sensorid);
                } else {
                    delectAreaWhat(sensorid);
                }
            }

            @Override
            public void dialogcancle() {
                if (!isFinishing()) {
                    successDialog.dismiss();
                }
            }

        });
        if (!isFinishing()) {
            successDialog.show();
        }
    }

    /*删除已选设备*/
    private void delectSettingWhat(String sensorid) {
        showWaitDialog(this, getString(R.string.delecting));
        commonProtocol = new CommonProtocol();
        commonProtocol.deletesensor(this, sensorid);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onHttpSuccess(int reqType, Message obj) {
        super.onHttpSuccess(reqType, obj);
        dismissWaitDialog();

        if (reqType == IHttpService.TYPE_SETTINGMANAGER) {
            SettingManagerBean bean = (SettingManagerBean) obj.obj;
            if (bean != null) {
                listData = bean.getData();
                if (listData.size() > 0) {
                    tv_no_datas.setVisibility(View.GONE);
                    showNetDatas(listData);
                } else {
                    showToast("暂无信息");
                    tv_no_datas.setVisibility(View.VISIBLE);
                }
            }
        } else if (reqType == IHttpService.TYPE_DELETESENSOR) {
            SubmitBean bean = (SubmitBean) obj.obj;
            if (bean != null) {
                if (bean.getMsg().equals("操作成功!")) {
                    showToast("删除成功");
                    tv_title_right_des.setText("选择");
                    tv_title_right_des.setTextColor(getResources().getColor(R.color.white));
                    btn_add.setText("新增");
                    selectDatas.clear();
                    for (int i = 0; i < listData.size(); i++) {
                        MultiNewManageAdapter.isSelected.put(i, false);
                    }
                    adapter.notifyDataSetChanged();
                    isSelectOrNor = true;
                    getNetDatas();
                } else {
                    showToast("删除失败，请重试");
                }
            }
        }
    }

    private void showNetDatas(List<SettingManagerDataBean> list) {
//        listData.addAll(list);
        initRecycleView();
    }


    private List<String> selectDatas = new ArrayList<>();

    private void initRecycleView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MultiNewManageAdapter(listData);
//        adapter = new NewSettingManagerAdapter(R.layout.lv_item_settring_add, listData);
        recyclerview.setAdapter(adapter);
        adapter.setOnItemClickLitener(new MultiNewManageAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isLongClick) {
                    if (!MultiNewManageAdapter.isSelected.get(position)) {
                        MultiNewManageAdapter.isSelected.put(position, true); // 修改map的值保存状态
                        adapter.notifyItemChanged(position);
                        selectDatas.add(listData.get(position).getSensorid());

                    } else {
                        MultiNewManageAdapter.isSelected.put(position, false); // 修改map的值保存状态
                        adapter.notifyItemChanged(position);
                        selectDatas.remove(listData.get(position));
                    }
                } else {

//                    showToast(position + "==000");
                    tv_item_name(position);
                }
            }

            @Override
            public void onAreaNameClick(int position) {
//                showToast(listData.get(position).getAreaname());
                tv_item_type_name(position);
            }
        });

//        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                if (listData.size() > 0) {
//                    OverdueInfosDataBean msgListDataBean = listData.get(position);
//                    toOnlyDetailActivity(msgListDataBean);
//                }
//            }
//        });
    }


    @Override
    public void onHttpError(int reqType, String error) {
        super.onHttpError(reqType, error);
        dismissWaitDialog();
        if (reqType == IHttpService.TYPE_SETTINGMANAGER) {
            tv_no_datas.setVisibility(View.VISIBLE);
            tv_no_datas.setText("服务器访问出错");
        }
    }


//    /*Item点击事件*/
//    @Override
//    public void itemClick(View v) {
//        int position;
//        position = (Integer) v.getTag();
//        switch (v.getId()) {
//            case R.id.tv_item_name:
//            case R.id.tv_item_type:
//            case R.id.tv_item_time:
//                tv_item_name(position);
//                break;
//            case R.id.tv_item_type_name:
//                tv_item_type_name(position);
//                break;
//            default:
//                break;
//        }
//    }
//
//    /*Item点击事件*/
//    @Override
//    public void noItemClick(View v) {
//        int position;
//        position = (Integer) v.getTag();
//        switch (v.getId()) {
//            case R.id.tv_item_name1:
//            case R.id.tv_item_time1:
//                tv_item_name_no(position);
//                break;
//            case R.id.tv_item_type_name1:
//                tv_item_type_name(position);
//                break;
//            default:
//                break;
//        }
//    }

    /*删除未绑定设备区域
     */
    private void tv_item_name_no(int position) {
        sensorid = "";
        sensorid = listData.get(position).getAreaid();
        showDelectDialog(sensorid, "确认删除该区域?" + "\n" + "删除后将无法恢复", false);
    }

    /*删除监控区*/
    private void delectAreaWhat(String sensorid) {
        showWaitDialog(this, getString(R.string.delecting));
        commonProtocol = new CommonProtocol();
        commonProtocol.deletearea(this, sensorid);
    }

    /*监控区*/
    private void tv_item_type_name(int position) {
        if (listData.size() > 0) {
            SettingManagerDataBean settingManagerDataBean = listData.get(position);
            String areaname = settingManagerDataBean.getAreaname();
            if (areaname.equals("未分配监控区")) {
                showToast("未分配监控区");
            } else {
                Intent intent = new Intent();
                intent.setClass(NewSettingManagerActivity.this, MonitoringAreaInfosActivity.class);
                intent.putExtra("settingManagerDataBean", settingManagerDataBean);
                super.startActivity(intent);
            }
        }
    }

    /*设备信息*/
    private void tv_item_name(int position) {
        if (listData.size() > 0) {
            SettingManagerDataBean settingManagerDataBean = listData.get(position);
            if (settingManagerDataBean != null) {
                Intent intent = new Intent();
                intent.setClass(NewSettingManagerActivity.this, SettingInfosActivity.class);
                intent.putExtra("settingManagerDataBean", settingManagerDataBean);
                super.startActivity(intent);
            }
        }
    }

}
