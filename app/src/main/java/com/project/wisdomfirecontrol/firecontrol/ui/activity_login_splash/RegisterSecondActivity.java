package com.project.wisdomfirecontrol.firecontrol.ui.activity_login_splash;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.mvp_0726.common.utils.AppManager;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.common.base.Const;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.util.PwdCheckUtil;
import com.project.wisdomfirecontrol.common.util.StringUtils;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.RegisterFirstCompanyBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.SubmitBean;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.CommonProtocol;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.IHttpService;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.PhotoUtils;
import com.project.wisdomfirecontrol.firecontrol.ui.view.dialog.SuccessDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

/**
 * Created by Administrator on 2018/6/12.
 */

public class RegisterSecondActivity extends BaseActivity {

    private static final String TAG = "tag";
    private EditText tv_unit_personal, tv_unit_personal_call, tv_unit_personal_phone;
    private EditText tv_unit_personal_first_name, tv_unit_personal_frst_phone, tv_unit_personal_second_name, tv_unit_personal_second_phone;
    private EditText tv_unit_personal_third_name, tv_unit_personal_third_phone, tv_unit_area, tv_user_pwd_again;
    private EditText tv_user_phone, edt_msg_pwd, edt_user_pwd, tv_unit_personal_count;
    private CheckBox cb_pwd, cb_agree;
    private ImageView iv_user_logo;
    private View view;
    private TextView tv_play_select_photo;
    private TextView tv_send_msg_pwd, tv_xieye, tv_success;
    private RegisterFirstCompanyBean bean;
    private String lng;
    private String lat;
    private String address_detail;
    private String province;
    private String city;
    private String district;
    private String address_detail_room;
    private String company_name;
    private String company_name_six;
    private String editTextDes;

    private boolean cheche_pwd = false;
    private boolean isRemberPwd;
    private boolean cheche_pwd_xieye = false;
    private boolean isRemberPwd_xieye;
    private String phone;

    private long exitTime;
    private String user_pwd;
    private String user_pwd_again;

    private List<LocalMedia> selectList = new ArrayList<>();
    private PhotoUtils photoUtils;
    private StringBuffer stringBuffer;
    private String imageurl;
    private LinearLayout ll_parent, ll, ll_children;
    private ScrollView scrollview;


    @Override
    public int getLayoutRes() {
        return R.layout.activity_register_second;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initView() {
        ll_parent = findView(R.id.ll_parent);
        scrollview = findView(R.id.scrollview);
        ll = findView(R.id.ll);
        ll_children = findView(R.id.ll_children);
        tv_play_select_photo = findView(R.id.tv_play_select_photo);

        tv_unit_personal = findView(R.id.tv_unit_personal);
        tv_unit_personal_call = findView(R.id.tv_unit_personal_call);
        tv_unit_personal_phone = findView(R.id.tv_unit_personal_phone);

        tv_unit_personal_first_name = findView(R.id.tv_unit_personal_first_name);
        cb_pwd = findView(R.id.cb_pwd);
        tv_unit_personal_frst_phone = findView(R.id.tv_unit_personal_frst_phone);

        tv_unit_personal_second_name = findView(R.id.tv_unit_personal_second_name);
        tv_unit_personal_second_phone = findView(R.id.tv_unit_personal_second_phone);

        tv_unit_personal_third_name = findView(R.id.tv_unit_personal_third_name);
        tv_unit_personal_third_phone = findView(R.id.tv_unit_personal_third_phone);

        tv_unit_area = findView(R.id.tv_unit_area);
        tv_unit_personal_count = findView(R.id.tv_unit_personal_count);

        iv_user_logo = findView(R.id.iv_user_logo);
        iv_user_logo.setImageResource(R.drawable.image_view);

        tv_user_phone = findView(R.id.tv_user_phone);
        edt_msg_pwd = findView(R.id.edt_msg_pwd);
        tv_send_msg_pwd = findView(R.id.tv_send_msg_pwd);
        tv_send_msg_pwd.setText("发送动态验证码");
        edt_user_pwd = findView(R.id.edt_user_pwd);
        tv_user_pwd_again = findView(R.id.tv_user_pwd_again);

        cb_agree = findView(R.id.cb_agree);
        tv_xieye = findView(R.id.tv_xieye);
        tv_success = findView(R.id.tv_success);
        view = findView(R.id.view);

        inintSMSSDK();

        initCheckBox();

        initEditText();
        tv_play_select_photo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //允许ScrollView截断点击事件，ScrollView可滑动
                    scrollview.requestDisallowInterceptTouchEvent(false);
                    ll.getParent().requestDisallowInterceptTouchEvent(false);//屏蔽父控件拦截onTouch事件
                    ll_children.getParent().requestDisallowInterceptTouchEvent(false);//屏蔽父控件拦截onTouch事件
                    tv_play_select_photo();

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.d(TAG, "onTouch2222: " + event.getAction());
                    //不允许ScrollView截断点击事件，点击事件由子View处理
                    scrollview.getParent().requestDisallowInterceptTouchEvent(true);//屏蔽父控件拦截onTouch事件
                    ll.getParent().requestDisallowInterceptTouchEvent(true);//屏蔽父控件拦截onTouch事件
                    ll_children.getParent().requestDisallowInterceptTouchEvent(true);//屏蔽父控件拦截onTouch事件
                }

                return false;
            }
        });
        tv_play_select_photo.getParent().requestDisallowInterceptTouchEvent(true);//屏蔽父控件拦截onTouch事件

    }

    private void initEditText() {

        edt_msg_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() < 4) {
                    return;
                }
                try {
                    String verifity = edt_msg_pwd.getText().toString().trim();
                    String thePhone = tv_user_phone.getText().toString().trim();
                    SMSSDK.submitVerificationCode(Const.COUNTRY, thePhone, verifity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        edt_user_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() < 8) {
                    return;
                }
                try {
                    String user_pwd = edt_user_pwd.getText().toString().trim();
                    boolean letterDigit = PwdCheckUtil.isLetterDigit(user_pwd);
                    Log.d(TAG, "onTextChanged: " + letterDigit);
                    if (!letterDigit) {
                        showToast("密码需包含母及数字");
                        return;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initCheckBox() {
        if (isRemberPwd) {
            cheche_pwd = true;
        } else {
            cheche_pwd = false;
        }
        cb_pwd.setChecked(cheche_pwd);

        if (isRemberPwd_xieye) {
            cheche_pwd_xieye = true;
        } else {
            cheche_pwd_xieye = false;
        }
        cb_agree.setChecked(cheche_pwd_xieye);
    }


    @Override
    public void initListener() {
        tv_xieye.setOnClickListener(this);
        cb_pwd.setOnClickListener(this);
        cb_agree.setOnClickListener(this);
        tv_send_msg_pwd.setOnClickListener(this);
        tv_success.setOnClickListener(this);
        tv_play_select_photo.setOnClickListener(this);
    }

    @Override
    public void initData() {
        title.setText("联网单位注册");

        Bundle bundle = getIntent().getExtras();
        bean = (RegisterFirstCompanyBean) bundle.get("bean");
        if (bean != null) {
            lng = bean.getLng();
            lat = bean.getLat();
            province = bean.getProvince();
            city = bean.getCity();
            district = bean.getArea();
            address_detail = bean.getAddress_detail();
            address_detail_room = bean.getAddress_detail_room();
            company_name = bean.getCompany_name();
            company_name_six = bean.getCompany_name_six();
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
        } else {//申请权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v, int id) {
        switch (v.getId()) {
            case R.id.tv_xieye:
                tv_xieye();
                break;
            case R.id.cb_pwd:
                cb_pwd();
                break;
            case R.id.cb_agree:
                cb_agree();
                break;
            case R.id.tv_play_select_photo:
//                tv_play_select_photo();
                break;
            case R.id.tv_send_msg_pwd:
                tv_send_msg_pwd();
                break;
            case R.id.tv_success:
                tv_success();
                break;
        }

    }

    /*同一联系电话*/
    private void cb_pwd() {
        if (!cheche_pwd) {
            cheche_pwd = true;
            tv_unit_personal_frst_phone.setText(tv_unit_personal_phone.getText().toString().trim());

        } else {
            cheche_pwd = false;
            tv_unit_personal_frst_phone.setText("");
        }
        cb_pwd.setChecked(cheche_pwd);
    }

    /*同意并遵守*/
    private void cb_agree() {
        if (!cheche_pwd_xieye) {
            cheche_pwd_xieye = true;
        } else {
            cheche_pwd_xieye = false;
        }
        cb_agree.setChecked(cheche_pwd_xieye);
    }


    /*浏览协议*/
    private void tv_xieye() {
    }


    /*选择图片*/
    private void tv_play_select_photo() {
        takeSelectPhoto();
    }

    private void takeSelectPhoto() {
        photoUtils = new PhotoUtils(RegisterSecondActivity.this, selectList, tv_play_select_photo, view, 1);
        photoUtils.showSelectVideoOrPhoto("photo", "activity");
        Log.d(TAG, "takeSelectPhoto: ");
    }


    //权限请求结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takeSelectPhoto();
                } else {
                    showToast("暂无权限，会影响部分功能使用");
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    selectList = PictureSelector.obtainMultipleResult(data);
                    LocalMedia media = selectList.get(0);
                    String compressPath = media.getCompressPath();
                    Glide.with(this).load(compressPath).into(iv_user_logo);
                    if (selectList.size() > 0) {
                        imageurl = reducePhoto(selectList);
                    }
                    break;
            }
        }
    }

    public String reducePhoto(final List<LocalMedia> dataList) {
        showWaitDialog(RegisterSecondActivity.this, "图片处理中...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (stringBuffer == null) {
                    stringBuffer = new StringBuffer();
                }
                stringBuffer.setLength(0);
                for (int i = 0; i < dataList.size(); i++) {
                    if (i == (dataList.size() - 1)) {
                        stringBuffer.append(com.project.wisdomfirecontrol.common.util.StringUtils.getimage(dataList.get(i).getCompressPath())).append(",");
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


    /*完成*/
    private void tv_success() {
        String unit_personal = tv_unit_personal.getText().toString().trim();
        if (TextUtils.isEmpty(unit_personal)) {
            showToast("请填写单位法人");
            return;
        }
        String unit_personal_call = tv_unit_personal_call.getText().toString().trim();
        if (TextUtils.isEmpty(unit_personal_call)) {
            showToast("请填写单位联系人");
            return;
        }
        String unit_personal_phone = tv_unit_personal_phone.getText().toString().trim();
        boolean isPhoneNum = StringUtils.isMobileNO(unit_personal_phone);
        if (!isPhoneNum) {
            showToast("请输入有效的手机号码！");
        }
        if (TextUtils.isEmpty(unit_personal_phone)) {
            showToast("请填写联系人电话");
            return;
        }

        String unit_personal_first_name = tv_unit_personal_first_name.getText().toString().trim();
        if (TextUtils.isEmpty(unit_personal_first_name)) {
            showToast("请填写消防第一联系人");
            return;
        }
        String unit_personal_frst_phone = tv_unit_personal_frst_phone.getText().toString().trim();
        boolean isPhoneNum1 = StringUtils.isMobileNO(unit_personal_frst_phone);
        if (!isPhoneNum1) {
            showToast("请输入有效的手机号码！");
        }
        if (TextUtils.isEmpty(unit_personal_frst_phone)) {
            showToast("请填写消防联系人电话");
            return;
        }

        String unit_personal_second_name = tv_unit_personal_second_name.getText().toString().trim();
        String unit_personal_second_phone = tv_unit_personal_second_phone.getText().toString().trim();

        String unit_personal_third_name = tv_unit_personal_third_name.getText().toString().trim();
        String unit_personal_third_phone = tv_unit_personal_third_phone.getText().toString().trim();

        String unit_area = tv_unit_area.getText().toString().trim();
        String unit_personal_count = tv_unit_personal_count.getText().toString().trim();
        String phone = tv_user_phone.getText().toString().trim();
        boolean isPhoneNu = StringUtils.isMobileNO(phone);
        if (!isPhoneNu) {
            showToast("请输入有效的手机号码！");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            showToast("请填写手机号");
            return;
        }

        user_pwd = edt_user_pwd.getText().toString().trim();
        user_pwd_again = tv_user_pwd_again.getText().toString().trim();
        if (TextUtils.isEmpty(user_pwd) && TextUtils.isEmpty(user_pwd_again)) {
            showToast("请填写密码");
            return;
        }
        if (!user_pwd.equals(user_pwd_again)) {
            showToast("两次密码不一致");
            return;
        }
        String msg = tv_send_msg_pwd.getText().toString().trim();
//        验证成功
        if (!msg.equals("验证成功")) {
            showToast("请先验证手机号码");
            return;
        }

        beginSubit(unit_personal, unit_personal_call, unit_personal_phone, unit_personal_first_name,
                unit_personal_frst_phone, unit_personal_second_name, unit_personal_second_phone, unit_personal_third_name, unit_personal_third_phone,
                "", "", unit_area, unit_personal_count, phone, user_pwd_again);

    }


    private void beginSubit(String legalperson, String principal, String principalTel, String name1,
                            String phone1, String name2, String phone2, String name3, String phone3,
                            String name4, String phone4, String area, String renshu, String tel, String password) {
        showWaitDialog(this, getString(R.string.register_in));
        CommonProtocol commonProtocol = new CommonProtocol();
        commonProtocol.userregister(this, province, city, district, "",
                address_detail + address_detail_room, company_name, company_name_six,
                legalperson, principal, principalTel, name1, phone1,
                name2, phone2, name3, phone3, name4, phone4, area, renshu, imageurl, tel, password, lat, lng);
    }

    @Override
    public void onHttpSuccess(int reqType, Message msg) {
        super.onHttpSuccess(reqType, msg);
        dismissWaitDialog();
        if (reqType == IHttpService.TYPE_TROUBLE_TYPE_NUM) {
            SubmitBean bean = (SubmitBean) msg.obj;
            String beanMsg = bean.getMsg();
            if (beanMsg.equals("操作成功!")) {
                tv_user_back();

            } else {
                showToast("注册失败");
            }
        }
    }

    private boolean isRestart = true;

    private void tv_user_back() {
        final SuccessDialog successDialog = new SuccessDialog(RegisterSecondActivity.this);
        successDialog.setContent("注册成功，是否退出注册界面?");
        if (!isFinishing()) {
            successDialog.show();
        }
        successDialog.setDialogCallback(new SuccessDialog.Dialogcallback() {
            @Override
            public void dialogdo(LinearLayout container) {
                if (isRestart) {
                    Intent intent = new Intent(RegisterSecondActivity.this, SelectUnitOrSepuviseActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    AppManager.getAppManager().removedAlllActivity(RegisterSecondActivity.this);
                    isRestart = false;
                    finish();
                }
            }

            @Override
            public void dialogcancle() {
                if (!isFinishing()) {
                    successDialog.dismiss();
                }
            }
        });
        successDialog.isCanceledOnTouchOutside(false);
    }

    @Override
    public void onHttpError(int reqType, String error) {
        super.onHttpError(reqType, error);
        dismissWaitDialog();
        showToast(error);
    }

    /*获取动态码*/
    private void tv_send_msg_pwd() {
        phone = tv_user_phone.getText().toString().trim();
//        phone = "13726870018";
        if (TextUtils.isEmpty(phone)) {
            showToast("手机号码不能为空");
            return;
        }
        boolean isPhoneNum = StringUtils.isMobileNO(phone);

        if (!isPhoneNum) {
            showToast("请输入有效的手机号码！");
        } else {
            phone = tv_user_phone.getText().toString().trim();
//            phone = "13726870018";
            getCode(Const.COUNTRY, phone);
        }
    }

    @SuppressLint("SetTextI18n")
    private void getCode(String country, String phone) {
        // 2.通过sdk发送短信验证
        // 请求获取短信验证码
        SMSSDK.getVerificationCode(country, phone);
        // 3.把按钮变成不可点击，并且显示倒计时
        try {
            handler.sendEmptyMessage(YANZHENGMA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inintSMSSDK() {
        SMSSDK.initSDK(this, Const.MOB_APPKEY, Const.MOB_APP_SECRET, false);
        try {
            EventHandler eventHandler = new EventHandler() {
                public void afterEvent(int event, int result, Object data) {
                    Message msg = new Message();
                    msg.arg1 = event;
                    msg.arg2 = result;
                    msg.obj = data;
                    handler.sendMessage(msg);
                }
            };
            SMSSDK.registerEventHandler(eventHandler); // 注册短信回调
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private int count = 60;
    private final int YANZHENGMA = 10;
    private final int YANZHENGIN = 12;
    private final int YANZHENGSUCCESS = 15;
    /*** 处理UI线程更新Handle **/
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressLint("SetTextI18n")
        public void handleMessage(android.os.Message msg) {
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    tv_send_msg_pwd.setText("验证成功");

                    count = 0;
                    handler.sendEmptyMessageDelayed(YANZHENGSUCCESS, 2500);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    showToast("验证码已经发送");

                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                } else if (event == SMSSDK.RESULT_ERROR) {
                    showToast("请检查验证码是否正确");
                }
            } else {
                int status = 0;
                try {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;

                    JSONObject object = new JSONObject(throwable.getMessage());
                    String des = object.optString("detail");
                    status = object.optInt("status");
                    if (!TextUtils.isEmpty(des)) {
                        Global.showToast(des);
                    }
                } catch (Exception e) {
                    SMSLog.getInstance().w(e);
                }
            }

            switch (msg.what) {
                case YANZHENGMA://等待获取验证码
                    if (count > 0) {
                        count--;
                        handler.sendEmptyMessageDelayed(YANZHENGMA, 1000);
                    } else {
                        handler.sendEmptyMessageDelayed(YANZHENGIN, 1000);
                    }
                    if (count == 0) {
                        count = 0;
                    }

                    tv_send_msg_pwd.setText("" + count + " S");

                    break;
                case YANZHENGIN:
                    tv_send_msg_pwd.setText("重新获取验证码");
                    count = 60;
                    break;
                case YANZHENGSUCCESS:
                    tv_send_msg_pwd.setText("验证成功");
                    break;

                default:
                    break;
            }
        }

        ;
    };


    protected void onDestroy() {
        super.onDestroy();
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler();
    }
}
