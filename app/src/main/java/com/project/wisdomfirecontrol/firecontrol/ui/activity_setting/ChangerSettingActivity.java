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
import com.project.wisdomfirecontrol.common.base.UserInfo;
import com.project.wisdomfirecontrol.common.base.UserManage;
import com.project.wisdomfirecontrol.common.util.SharedPreUtil;
import com.project.wisdomfirecontrol.common.util.StringUtils;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.GetmonitorAreaBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.GetmonitorAreaDataBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.setting.SettingManagerDataBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.SubmitBean;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.CommonProtocol;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.IHttpService;
import com.project.wisdomfirecontrol.firecontrol.ui.adaper_Lv.GetmonitorAreaDataBeanLvAdapter;
import com.project.wisdomfirecontrol.firecontrol.ui.adaper_Lv.StringDatasLvAdapter;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.DatasUtils;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.PhotoUtils;
import com.project.wisdomfirecontrol.firecontrol.ui.view.dialog.SuccessDialog;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/20.
 * 修改设备
 */

public class ChangerSettingActivity extends BaseActivity {

    private static final String TAG = "tag";
    private EditText tv_company_name, tv_addpesoanl_name;
    private TextView tv_personal_name, tv_phone_tel, tv_select_area, tv_next;
    private StringDatasLvAdapter stringDatasLvAdapter;
    private SettingManagerDataBean settingManagerDataBean;

    private String pid;
    private CommonProtocol commonProtocol;
    private List<GetmonitorAreaDataBean> areaData = new ArrayList<>();
    private GetmonitorAreaDataBeanLvAdapter areaAdapter;
    private String monitorAreaid;
    private String createTime;
    private String sensorid;
    private String type;
    private String sensorPosition;
    private String terminalNO;
    private String areaName;
    private SuccessDialog successDialog;
    private String personal;
    private String address;
    private String tel;
    private String lat;
    private String lng;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_addnewsetting;
    }

    @Override
    public void initView() {
        title.setText("修改设备");

        tv_company_name = findView(R.id.tv_company_name);//安装位置
        tv_personal_name = findView(R.id.tv_personal_name);//类型
        tv_addpesoanl_name = findView(R.id.tv_addpesoanl_name);//设备号
        tv_phone_tel = findView(R.id.tv_phone_tel);//重新扫描
        tv_select_area = findView(R.id.tv_select_area);//监控区
        tv_next = findView(R.id.tv_next);
        mEditTextName = findViewById(R.id.tv_client_name);
        mEditTextPhone = findViewById(R.id.tv_client_phone);
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
        if (bundle != null) {
            settingManagerDataBean = (SettingManagerDataBean) bundle.get("settingManagerDataBean");
            if (settingManagerDataBean != null) {

                /*安装位置*/
                String setposition = settingManagerDataBean.getSetposition();
                String type = settingManagerDataBean.getType();
                String terminalno = settingManagerDataBean.getTerminalno();
                personal = settingManagerDataBean.getPersonal();
                tel = settingManagerDataBean.getTel();
                address = settingManagerDataBean.getAddress();
                monitorAreaid = settingManagerDataBean.getAreaid();
                sensorid = settingManagerDataBean.getSensorid();
                tv_company_name.setText(setposition);
                tv_personal_name.setText(type);
                tv_addpesoanl_name.setText(terminalno);
                /*监控区*/
                String areaname = settingManagerDataBean.getAreaname();
                tv_select_area.setText(areaname);
            } else {
                String result = (String) bundle.get("result");
                tv_addpesoanl_name.setText(result);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v, int id) {
        switch (v.getId()) {
            case R.id.tv_phone_tel:
                tv_phone_tel();
                break;
            case R.id.tv_select_area:
                tv_select_area();
                break;
            case R.id.tv_next:
                tv_next();
                break;

            case R.id.tv_personal_name:
                tv_personal_name();
                break;
        }

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
        type = tv_personal_name.getText().toString().trim();
        if (TextUtils.isEmpty(type)) {
            showToast("请选择类型");
            return;
        }
        sensorPosition = tv_company_name.getText().toString().trim();
        if (TextUtils.isEmpty(sensorPosition)) {
            showToast("请填写安装位置");
            return;
        }
        terminalNO = tv_addpesoanl_name.getText().toString().trim();
        if (TextUtils.isEmpty(terminalNO)) {
            showToast("请输入设备号");
            return;
        }
        areaName = tv_select_area.getText().toString().trim();
        if (TextUtils.isEmpty(areaName)) {
            showToast("请选择监控区域");
            return;
        }
        createTime = StringUtils.getCurrentTime();
        sumbit(type, createTime, terminalNO, sensorPosition, monitorAreaid);

    }

    private EditText mEditTextName, mEditTextPhone;

    /*提交*/
    private void sumbit(String type, String createTime, String terminalNO, String sensorPosition, String monitorAreaid) {
        pid = DatasUtils.getUserPid(this);
        showWaitDialog(this, getString(R.string.inupdate));
        commonProtocol = new CommonProtocol();
//        pid = "yun";
        if (1 == SharedPreUtil.getInt(this, "isuser", 0)) {
            if (null == mEditTextName.getText().toString()) {
                showToast("请先输入负责人名字");
                return;
            }
            if (null == mEditTextPhone.getText().toString()) {
                showToast("请先输入负责人电话");
                return;
            }
            UserInfo userIdInfo = UserManage.getInstance().getUserIdInfo(Global.mContext);
            String userid = userIdInfo.getUserid();
            commonProtocol.savesensor(this, "", pid, type, createTime, terminalNO, sensorPosition, monitorAreaid, userid,
                    mEditTextName.getText().toString(),
                    mEditTextPhone.getText().toString());
        } else {
            commonProtocol.savesensor(this, sensorid, pid, type, createTime, terminalNO, sensorPosition, monitorAreaid, null, null, null);
        }

    }

    /*选择监控区*/
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void tv_select_area() {
        if (areaData.size() > 0) {
            showAreaName(areaData);
        } else {
            pid = DatasUtils.getUserPid(this);
            showWaitDialog(this, getString(R.string.inupdate));
            commonProtocol = new CommonProtocol();
//        pid = "yun";
            commonProtocol.getmonitorarea(this, pid);
        }
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
                    showDelectDialog("修改设备成功，是否退出当前界面?");
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

                bundle.putString("type", type);
                bundle.putString("sensorPosition", sensorPosition);
                bundle.putString("terminalNO", terminalNO);
                bundle.putString("createTime", createTime);
                bundle.putString("areaName", areaName);

                bundle.putString("personal", personal);
                bundle.putString("tel", tel);
                bundle.putString("address", address);
                bundle.putString("lat", lat);
                bundle.putString("lng", lng);
                resultIntent.putExtras(bundle);
                ChangerSettingActivity.this.setResult(RESULT_OK, resultIntent);
                ChangerSettingActivity.this.finish();

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
                monitorAreaid = "";
                tv_select_area.setText(areaData.get(position).getAreaname());
                monitorAreaid = areaData.get(position).getId();
                personal = areaData.get(position).getPersonal();
                tel = areaData.get(position).getTel();
                lat = areaData.get(position).getLat();
                lng = areaData.get(position).getLng();
                address = areaData.get(position).getAddress() + areaData.get(position).getDetailaddress();
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


    public static final int REQUEST_CODE = 111;

    /*重新扫描*/
    private void tv_phone_tel() {
        Intent intent = new Intent(this, QRChangecodeActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    tv_addpesoanl_name.setText(result);
                }
            }
        }
    }
}
