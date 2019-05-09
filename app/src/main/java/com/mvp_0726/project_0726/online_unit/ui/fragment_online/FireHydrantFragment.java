package com.mvp_0726.project_0726.online_unit.ui.fragment_online;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mvp_0726.common.base.codereview.BaseFragment;
import com.mvp_0726.common.utils.Constans;
import com.mvp_0726.project_0726.online_unit.contract.SettingOnlinePoliceContract;
import com.mvp_0726.project_0726.online_unit.event.SettingFragmentEvent;
import com.mvp_0726.project_0726.online_unit.presenter.SettingOnlinePolicePresenter;
import com.mvp_0726.project_0726.online_unit.ui.activity.SettingPoliceOnlineActivity;
import com.mvp_0726.project_0726.utils.StringUtils;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.firecontrol.base.MyApplication;
import com.project.wisdomfirecontrol.firecontrol.model.bean.setting.SettingManagerDataBean;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_online.SettingCallOnlineInfosActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_setting.MonitoringAreaInfosActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.activity_setting.SettingInfosActivity;
import com.project.wisdomfirecontrol.firecontrol.ui.adaper_Lv.SettingOnlinePoliceLvAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/26.
 * 消防栓
 */

public class FireHydrantFragment extends BaseFragment implements SettingOnlinePoliceContract.View {

    private static final String TAG = "tag";
    private ListView rv_list_setting;
    private TextView tv_item_time, tv_item_name, tv_item_type, tv_item_type_name;

    private SettingOnlinePoliceLvAdapter settingAdapter;
    private String pid;
    private SettingOnlinePolicePresenter presenter;
    private List<SettingManagerDataBean> data =new ArrayList<>();
    private View view_gone;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_allsettingpolicetruble;
    }

    @Override
    public void initView() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        presenter = new SettingOnlinePolicePresenter(this, (SettingPoliceOnlineActivity) getActivity());
        tv_item_name = rootView.findViewById(R.id.tv_item_name);
        tv_item_type = rootView.findViewById(R.id.tv_item_type);
        tv_item_time = rootView.findViewById(R.id.tv_item_time);
        tv_item_type_name = rootView.findViewById(R.id.tv_item_type_name);
        view_gone = rootView.findViewById(R.id.view_gone);
        rv_list_setting = rootView.findViewById(R.id.lv_list_address);
        tv_item_time.setText("设备状态");
        showFourTv(tv_item_name, tv_item_type, tv_item_time, tv_item_type_name);
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
    protected void setLisenter() {

    }

    @Override
    protected void widgetClick(View v) {

    }

    @Override
    public void initData() {
        getsensor(SettingPoliceOnlineFragment.state);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void disposeSettingFragmentEvent(SettingFragmentEvent event) {
        switch (event.getWhat()) {
            case Constans.XIAOFANGSUCESS://展示数据
                showViewGoneVisible(false);
                data.clear();
                data = (List<SettingManagerDataBean>) event.getData();
                if (data.size() > 0) {
                    showNetDatas(data);
                } else {
                    if (settingAdapter != null) {
                        settingAdapter.notifyDataSetChanged();
                    }
                    showSuccessToast("暂无信息");
                }
                break;

            case Constans.XIAOFANGERROR://展示数据
                showViewGoneVisible(true);
                break;
        }
    }

    private void showViewGoneVisible(boolean b) {
        if (b) {
            view_gone.setVisibility(View.VISIBLE);
            rv_list_setting.setVisibility(View.GONE);
        } else {
            view_gone.setVisibility(View.GONE);
            rv_list_setting.setVisibility(View.VISIBLE);
        }
    }

    private void getsensor(String state) {
        boolean networkConnected = MyApplication.getInstance().isNetworkConnected();
        if (!networkConnected) {
            showErrorToast("无网络，请检查网络失败");
            return;
        }

        pid = StringUtils.getUserPid(getContext());
        presenter.getSensor(pid, "消防栓", state, "ishave",Constans.XIAOFANGSUCESS,Constans.XIAOFANGERROR);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
   /* private void getNetDatas() {
        pid = DatasUtils.getUserPid(getContext());
//        showWaitDialog(getActivity(), getString(R.string.inupdate));
        commonProtocol = new CommonProtocol();
//        pid = "yun";
        Log.d(TAG, "getNetDatas: " + pid);
//        commonProtocol.getsensor(this, pid, "消防栓", SettingPoliceOnlineActivity.state, "ishave");
    }*/

   /* @Override
    public void onHttpSuccess(int reqType, Message obj) {
        super.onHttpSuccess(reqType, obj);
        dismissWaitDialog();

        if (reqType == IHttpService.TYPE_SETTINGMANAGER) {
            SettingManagerBean bean = (SettingManagerBean) obj.obj;
            if (bean != null) {
                data = bean.getData();
                if (data.size() > 0) {
                    showNetDatas(data);
                } else {
                    showToast("暂无信息");
                }
            }
//        } else if (reqType == IHttpService.TYPE_DELETESENSOR) {
//            SubmitBean bean = (SubmitBean) obj.obj;
//            if (bean != null) {
//                if (bean.getMsg().equals("操作成功!")) {
//                    showToast("删除成功");
//                    tv_title_right_des.setText("选择");
//                    tv_title_right_des.setTextColor(getResources().getColor(R.color.white));
//                    btn_add.setText("新增");
//                    isShow = true;
//                    getNetDatas();
//                } else {
//                    showToast("删除失败，请重试");
//                }
//            }
//        } else {
//            GetCompanyRegisterInfosBean bean = (GetCompanyRegisterInfosBean) obj.obj;
//            if (bean != null) {
//                String address = bean.getData().getAddress();
//                if (!TextUtils.isEmpty(address)) {
//                    SharedPreUtil.saveString(Global.mContext, "address", address);
//                    SharedPreUtil.saveString(Global.mContext, "address", address);
//                }
//            }
        }
    }*/


    /*展示数据*/
    private void showNetDatas(final List<SettingManagerDataBean> data) {
        Log.d(TAG, "showNetDatas: " + data.size());
        settingAdapter = new SettingOnlinePoliceLvAdapter(getActivity(), data);
        settingAdapter.setOnInnerItemOnClickListener(new SettingOnlinePoliceLvAdapter.InnerItemOnclickListener() {
            @Override
            public void itemClick(View v) {
                int position;
                position = (Integer) v.getTag();
                switch (v.getId()) {
                    case R.id.tv_item_name:
                    case R.id.tv_item_type:
                    case R.id.rv_item_time:
                    case R.id.iv_item_time:
                    case R.id.tv_item_time:
                        if (data.size() > 0) {
                            SettingManagerDataBean settingManagerDataBean = data.get(position);
                            if (settingManagerDataBean != null) {
                                String state = settingManagerDataBean.getState();
                                Intent intent = new Intent();
                                if (state.equals("2")) {
                                    tv_item_time(data,position);
                                } else {
                                    tv_item_name(data,position);
                                }
                            }
                        }
                        break;

                    case R.id.tv_item_type_name:
                        tv_item_type_name(data,position);
                        break;
                    default:
                        break;
                }
            }
        });
//        settingAdapter.setOnNOInnerItemOnClickListener(new SettingManagerAddressLvAdapter.NoInnerItemOnclickListener() {
//            @Override
//            public void noItemClick(View v) {
//
//            }
//        });
        rv_list_setting.setAdapter(settingAdapter);
//        rv_list_setting.setOnItemClickListener(getActivity());
//        rv_list_setting.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                Log.d(TAG, "onScrollStateChanged222: " + firstVisibleItem + " +++ " + visibleItemCount + " ++ " + totalItemCount);
                //isLoad为是否滚动到底部
//                isLoad = ((firstVisibleItem+visibleItemCount)==totalItemCount);
//                if(isLoad){
//                settingAdapter.showCheck(view, isShow);
//                settingAdapter.showNosettingCheck(view, isShow);
//                settingAdapter.notifyDataSetChanged();
//                }
//            }
//        });

    }

    /*报警时间*/
    private void tv_item_time(List<SettingManagerDataBean> data, int position) {
        if (data.size() > 0) {
            SettingManagerDataBean settingManagerDataBean = data.get(position);
            if (settingManagerDataBean != null) {
                Intent intent = new Intent();
                String state = settingManagerDataBean.getState();
                if (state.equals("2")) {
                    intent.setClass(getContext(), SettingCallOnlineInfosActivity.class);
                } else {
                    intent.setClass(getContext(), SettingInfosActivity.class);
                }
                intent.putExtra("settingManagerDataBean", settingManagerDataBean);
                super.startActivity(intent);
            }
        }
    }

  /*  @Override
    public void onHttpError(int reqType, String error) {
        super.onHttpError(reqType, error);
        dismissWaitDialog();
        if (reqType == IHttpService.TYPE_SETTINGMANAGER) {
//            rv_list_setting.setVisibility(View.GONE);
//            tv_no_datas.setVisibility(View.VISIBLE);
//            tv_no_datas.setText("服务器访问出错");
        }
    }*/


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


    /*监控区*/
    private void tv_item_type_name(List<SettingManagerDataBean> data, int position) {
        if (data.size() > 0) {
            SettingManagerDataBean settingManagerDataBean = data.get(position);
            if (settingManagerDataBean != null) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MonitoringAreaInfosActivity.class);
                intent.putExtra("settingManagerDataBean", settingManagerDataBean);
                super.startActivity(intent);
//            } else {
//                showToast("该设备未分配监控区");
            }
        }
    }

    /*设备信息*/
    private void tv_item_name(List<SettingManagerDataBean> data, int position) {
        if (data.size() > 0) {
            SettingManagerDataBean settingManagerDataBean = data.get(position);
            if (settingManagerDataBean != null) {
                Intent intent = new Intent();
                intent.setClass(getContext(), SettingInfosActivity.class);
                intent.putExtra("settingManagerDataBean", settingManagerDataBean);
                super.startActivity(intent);
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
