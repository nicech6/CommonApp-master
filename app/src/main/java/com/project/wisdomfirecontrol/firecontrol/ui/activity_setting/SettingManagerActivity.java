package com.project.wisdomfirecontrol.firecontrol.ui.activity_setting;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.SubmitBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.setting.SettingManagerBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.setting.SettingManagerDataBean;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.CommonProtocol;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.IHttpService;
import com.project.wisdomfirecontrol.firecontrol.ui.adaper_Lv.SettingManagerAddressLvAdapter;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.DatasUtils;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.PhotoUtils;
import com.project.wisdomfirecontrol.firecontrol.ui.view.dialog.SuccessDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/18.
 * 设备管理
 */

public class SettingManagerActivity extends BaseActivity implements AdapterView.OnItemClickListener,
        SettingManagerAddressLvAdapter.InnerItemOnclickListener, SettingManagerAddressLvAdapter.NoInnerItemOnclickListener {

    private static final String TAG = "tag";
    private TextView tv_title_right, tv_title_right_des, tv_no_datas;
    private ListView rv_list_setting;
    private ListView lv_list_no_setting;
    private TextView btn_add, tv_item_time, tv_item_name, tv_item_type, tv_item_type_name;
    private View view_time;
    private SettingManagerAddressLvAdapter settingAdapter;
    private SuccessDialog successDialog;
    private CommonProtocol commonProtocol;
    private String pid;
    private StringBuffer stringBuffer;
    private String sensorid;
    private List<SettingManagerDataBean> data = new ArrayList<>();

    @Override
    public int getLayoutRes() {
        return R.layout.activity_settingmanager;
    }

    @Override
    public void initView() {
        title.setText("设备管理");
        tv_title_right = findView(R.id.tv_title_right);
        tv_title_right_des = findView(R.id.tv_title_right_des);
        tv_title_right.setVisibility(View.GONE);
        tv_title_right_des.setVisibility(View.VISIBLE);
        tv_title_right_des.setText("选择");

        rv_list_setting = findView(R.id.lv_list_address);
        lv_list_no_setting = findView(R.id.lv_list_no_setting);

        tv_no_datas = findView(R.id.tv_no_datas);
        btn_add = findView(R.id.btn_add);
        btn_add.setText("新增");

        view_time = findView(R.id.view_time);
        tv_item_name = findView(R.id.tv_item_name);
        tv_item_type = findView(R.id.tv_item_type);
        tv_item_time = findView(R.id.tv_item_time);
        tv_item_type_name = findView(R.id.tv_item_type_name);

        isShow = true;
        showFourTv(view_time, tv_item_name, tv_item_type, tv_item_time, tv_item_type_name);
    }

    private void showFourTv(View view_time, TextView tv_item_name, TextView tv_item_type,
                            TextView tv_item_time, TextView tv_item_type_name) {

        ViewGroup.LayoutParams layoutParams5 = view_time.getLayoutParams();
        layoutParams5.width = (int) Global.mScreenWidth / 4;
        view_time.setLayoutParams(layoutParams5);

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
        commonProtocol.getsensor(this, pid);
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
        }
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
        if (!isShow) {
            if (settingAdapter != null) {
                view_time.setVisibility(View.GONE);
                tv_item_time.setVisibility(View.VISIBLE);
                tv_title_right_des.setTextColor(getResources().getColor(R.color.white));
                tv_title_right_des.setText("选择");
                btn_add.setText("新增");
                isShow = true;
                settingAdapter.showCheck(view, isShow);
                settingAdapter.showNosettingCheck(view, isShow);
                settingAdapter.notifyDataSetChanged();
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

    private boolean isShow = true;


    /*选择*/
    private void tv_title_right(View view) {

        if (settingAdapter != null) {
            if (isShow) {
                tv_item_time.setVisibility(View.GONE);
                view_time.setVisibility(View.VISIBLE);
                tv_title_right_des.setText("删除");
                tv_title_right_des.setTextColor(getResources().getColor(R.color.red));
                isShow = false;
                btn_add.setText("取消");
                settingAdapter.showCheck(view, isShow);
                settingAdapter.showNosettingCheck(view, isShow);
                settingAdapter.notifyDataSetChanged();
            } else {
                isDelectSelectorSetting();
            }
        }
    }

    private void isDelectSelectorSetting() {
        sensorid = "";
        if (stringBuffer == null) {
            stringBuffer = new StringBuffer();
        }
        stringBuffer.setLength(0);
        if (settingAdapter != null) {
            int size = settingAdapter.getCheckBoxIDList().size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    if (i == (size - 1)) {
                        stringBuffer.append(settingAdapter.getCheckBoxIDList().get(i));
                    } else {
                        stringBuffer.append(settingAdapter.getCheckBoxIDList().get(i)).append(",");
                    }
                }
                sensorid = stringBuffer.toString().trim();
                showDelectDialog(sensorid, "确认删除已选设备?" + "\n" + "删除后将无法恢复", true);
            } else {
                showToast("请先选择设备");
            }
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
                data = bean.getData();
                if (data.size() > 0) {
                    rv_list_setting.setVisibility(View.VISIBLE);
                    tv_no_datas.setVisibility(View.GONE);
                    showNetDatas(data);
                } else {
                    showToast("暂无信息");
                    rv_list_setting.setVisibility(View.GONE);
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
                    isShow = true;
                    getNetDatas();
                } else {
                    showToast("删除失败，请重试");
                }
            }
//        } else {
//            GetCompanyRegisterInfosBean bean = (GetCompanyRegisterInfosBean) obj.obj;
//            if (bean != null) {
//                String address = bean.getData().getAddress();
//                if (!TextUtils.isEmpty(address)) {
//                    SharedPreUtil.saveString(Global.mContext, "address", address);
//                }
//            }
        }
    }


    //是否需要加载更多
    private boolean isLoad = false;

    /*展示数据*/
    private void showNetDatas(List<SettingManagerDataBean> data) {

        settingAdapter = new SettingManagerAddressLvAdapter(this, data);
        settingAdapter.setOnInnerItemOnClickListener(this);
        settingAdapter.setOnNOInnerItemOnClickListener(this);
        rv_list_setting.setAdapter(settingAdapter);
        rv_list_setting.setOnItemClickListener(this);
        rv_list_setting.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                Log.d(TAG, "onScrollStateChanged222: " + firstVisibleItem + " +++ " + visibleItemCount + " ++ " + totalItemCount);
                //isLoad为是否滚动到底部
//                isLoad = ((firstVisibleItem+visibleItemCount)==totalItemCount);
//                if(isLoad){
                settingAdapter.showCheck(view, isShow);
                settingAdapter.showNosettingCheck(view, isShow);
                settingAdapter.notifyDataSetChanged();
//                }
            }
        });

    }

    @Override
    public void onHttpError(int reqType, String error) {
        super.onHttpError(reqType, error);
        dismissWaitDialog();
        if (reqType == IHttpService.TYPE_SETTINGMANAGER) {
            rv_list_setting.setVisibility(View.GONE);
            tv_no_datas.setVisibility(View.VISIBLE);
            tv_no_datas.setText("服务器访问出错");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        updateSingle(position);
    }

    /**
     * 第一种方法 更新对应view的内容
     *
     * @param position 要更新的位置
     */
    private void updateSingle(int position) {
        /**第一个可见的位置**/
        int firstVisiblePosition = rv_list_setting.getFirstVisiblePosition();
        /**最后一个可见的位置**/
        int lastVisiblePosition = rv_list_setting.getLastVisiblePosition();

        /**在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
        Log.d(TAG, "showNosettingChec333: " + position);
        if (position >= firstVisiblePosition && position <= lastVisiblePosition && settingAdapter != null) {
            /**获取指定位置view对象**/
            View view = rv_list_setting.getChildAt(position - firstVisiblePosition);
            settingAdapter.getView(position, view, rv_list_setting);
        }
    }

    /*Item点击事件*/
    @Override
    public void itemClick(View v) {
        int position;
        position = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.tv_item_name:
            case R.id.tv_item_type:
            case R.id.tv_item_time:
                tv_item_name(position);
                break;
            case R.id.tv_item_type_name:
                tv_item_type_name(position);
                break;
            default:
                break;
        }
    }

    /*Item点击事件*/
    @Override
    public void noItemClick(View v) {
        int position;
        position = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.tv_item_name1:
            case R.id.tv_item_time1:
                tv_item_name_no(position);
                break;
            case R.id.tv_item_type_name1:
                tv_item_type_name(position);
                break;
            default:
                break;
        }
    }

    /*删除未绑定设备区域
     */
    private void tv_item_name_no(int position) {
        sensorid = "";
        sensorid = data.get(position).getAreaid();
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
        if (data.size() > 0) {
            SettingManagerDataBean settingManagerDataBean = data.get(position);
            String areaname = settingManagerDataBean.getAreaname();
            if (areaname.equals("未分配监控区")) {
                showToast("未分配监控区");
            } else {
                Intent intent = new Intent();
                intent.setClass(SettingManagerActivity.this, MonitoringAreaInfosActivity.class);
                intent.putExtra("settingManagerDataBean", settingManagerDataBean);
                super.startActivity(intent);
            }
        }
    }

    /*设备信息*/
    private void tv_item_name(int position) {
        if (data.size() > 0) {
            SettingManagerDataBean settingManagerDataBean = data.get(position);
            if (settingManagerDataBean != null) {
                Intent intent = new Intent();
                intent.setClass(SettingManagerActivity.this, SettingInfosActivity.class);
                intent.putExtra("settingManagerDataBean", settingManagerDataBean);
                super.startActivity(intent);
            }
        }
    }

}
