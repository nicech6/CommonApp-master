package com.project.wisdomfirecontrol.firecontrol.ui.activity_setting;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.util.StringUtils;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.GetmonitorAreaBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.GetmonitorAreaDataBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.SubmitBean;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.CommonProtocol;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.IHttpService;
import com.project.wisdomfirecontrol.firecontrol.ui.adaper_Lv.GetmonitorAreaDataBeanLvAdapter;
import com.project.wisdomfirecontrol.firecontrol.ui.adaper_Lv.StringDatasLvAdapter;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.DatasUtils;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.PhotoUtils;
import com.project.wisdomfirecontrol.firecontrol.ui.view.dialog.SuccessDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/20.
 * 新增设备
 */

public class AddNewSettingActivity extends BaseActivity {

    private static final String TAG = "tag";
    private EditText tv_company_name, tv_addpesoanl_name;
    private TextView tv_personal_name, tv_phone_tel, tv_select_area, tv_next;
    private StringDatasLvAdapter stringDatasLvAdapter;
    private String pid;
    private CommonProtocol commonProtocol;
    private List<GetmonitorAreaDataBean> areaData = new ArrayList<>();
    private GetmonitorAreaDataBeanLvAdapter areaAdapter;
    private String monitorAreaid;
    private SuccessDialog successDialog;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_addnewsetting;
    }

    @Override
    public void initView() {
        title.setText("新增设备");

        tv_company_name = findView(R.id.tv_company_name);//安装位置
        tv_personal_name = findView(R.id.tv_personal_name);//类型
        tv_addpesoanl_name = findView(R.id.tv_addpesoanl_name);//设备号
        tv_phone_tel = findView(R.id.tv_phone_tel);//重新扫描
        tv_select_area = findView(R.id.tv_select_area);
        tv_next = findView(R.id.tv_next);
    }

    @Override
    public void initListener() {
        tv_phone_tel.setOnClickListener(this);
        tv_select_area.setOnClickListener(this);
        tv_personal_name.setOnClickListener(this);
        tv_next.setOnClickListener(this);
    }

    @Override
    public void initData() {

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        String result = (String) bundle.get("result");
        tv_addpesoanl_name.setText(result);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v, int id) {
        switch (v.getId()) {
            case R.id.tv_phone_tel:
                tv_phone_tel();
                break;
            case R.id.btn_back:
                btn_back();
                break;
            case R.id.tv_select_area:
                tv_select_area();
                break;

            case R.id.tv_personal_name:
                tv_personal_name();
                break;
            case R.id.tv_next:
                tv_next();
                break;
        }

    }


    private void btn_back() {
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("finish", "1");
        resultIntent.putExtras(bundle);
        AddNewSettingActivity.this.setResult(RESULT_OK, resultIntent);
        super.finish();
    }

    /*类型*/
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void tv_personal_name() {
        View typeView = LayoutInflater.from(this).inflate(R.layout.lv_item_type_login, null);
        final PhotoUtils photoUtils = new PhotoUtils(this, typeView, (int) Global.mScreenWidth / 2,
                ActionBar.LayoutParams.WRAP_CONTENT, tv_personal_name, false, true);
        ListView listView = typeView.findViewById(R.id.lv_item_type);
        final List<String> list = new ArrayList<>();
        list.add("消防栓");
        list.add("烟感器");
        list.add("气感器");
        list.add("灭火器");
        list.add("电气火灾");

        if (stringDatasLvAdapter == null) {
            stringDatasLvAdapter = new StringDatasLvAdapter(this, list);
        }
        listView.setAdapter(stringDatasLvAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_personal_name.setText(list.get(position));
                if (photoUtils != null) {
                    photoUtils.showTypeOrDismiss();
                }
            }
        });
    }

    /*提交*/
    private void tv_next() {
        String type = tv_personal_name.getText().toString().trim();
        if (TextUtils.isEmpty(type)) {
            showToast("请选择类型");
            return;
        }
        String sensorPosition = tv_company_name.getText().toString().trim();
        if (TextUtils.isEmpty(sensorPosition)) {
            showToast("请填写安装位置");
            return;
        }
        String terminalNO = tv_addpesoanl_name.getText().toString().trim();
        if (TextUtils.isEmpty(terminalNO)) {
            showToast("请输入设备号");
            return;
        }
        String areaName = tv_select_area.getText().toString().trim();
        if (TextUtils.isEmpty(areaName)) {
            showToast("请选择监控区域");
            return;
        }
        String createTime = StringUtils.getCurrentTime();
        sumbit(type, createTime, terminalNO, sensorPosition, monitorAreaid);

    }

    /*提交*/
    private void sumbit(String type, String createTime, String terminalNO, String sensorPosition, String monitorAreaid) {
        pid = DatasUtils.getUserPid(this);
        showWaitDialog(this, getString(R.string.inupdate));
        commonProtocol = new CommonProtocol();
//        pid = "yun";
        commonProtocol.savesensor(this, "", pid, type, createTime, terminalNO, sensorPosition, monitorAreaid);
    }

    /*选择监控区*/
    private void tv_select_area() {
        pid = DatasUtils.getUserPid(this);
        showWaitDialog(this, getString(R.string.inupdate));
        commonProtocol = new CommonProtocol();
//        pid = "yun";
        commonProtocol.getmonitorarea(this, pid);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onHttpSuccess(int reqType, Message obj) {
        super.onHttpSuccess(reqType, obj);
        dismissWaitDialog();

        if (reqType == IHttpService.TYPE_GETAREAPERSON) {
            SubmitBean bean = (SubmitBean) obj.obj;
            if (bean != null) {
                if (bean.getMsg().equals("操作成功!")) {
                    showDelectDialog("新增设备成功，退出当前界面?");

                } else {
                    showToast("提交失败，请重试");
                }
            }
        } else if (reqType == IHttpService.TYPE_SETTINGMANAGER) {
            GetmonitorAreaBean bean = (GetmonitorAreaBean) obj.obj;
            if (bean != null) {
                areaData = bean.getData();
                if (areaData.size() > 0) {
                    showAreaName(areaData);
                } else {
                    showToast("暂无监控区域");
                }
            }
        }
    }

    /*成功*/
    private void showDelectDialog(String txt) {
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
                Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();

                bundle.putString("areaName", "");
                resultIntent.putExtras(bundle);
                AddNewSettingActivity.this.setResult(RESULT_OK, resultIntent);
                AddNewSettingActivity.this.finish();

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

    /*选择区域*/
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showAreaName(final List<GetmonitorAreaDataBean> areaData) {
        View typeView = LayoutInflater.from(this).inflate(R.layout.lv_item_type_login, null);
        final PhotoUtils photoUtils = new PhotoUtils(this, typeView, (int) Global.mScreenWidth / 2,
                ActionBar.LayoutParams.WRAP_CONTENT, tv_select_area, false, true);
        ListView listView = typeView.findViewById(R.id.lv_item_type);

        if (areaAdapter == null) {
            areaAdapter = new GetmonitorAreaDataBeanLvAdapter(this, areaData);
        }
        listView.setAdapter(areaAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_select_area.setText(areaData.get(position).getAreaname());
                monitorAreaid = areaData.get(position).getId();
                if (photoUtils != null) {
                    photoUtils.showTypeOrDismiss();
                }
            }
        });
    }


    @Override
    public void onHttpError(int reqType, String error) {
        super.onHttpError(reqType, error);
        dismissWaitDialog();
    }

    /*重新扫描*/
    private void tv_phone_tel() {
        finish();
    }
}
