package com.project.wisdomfirecontrol.firecontrol.ui.activity_unit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.mvp_0726.project_0726.constant.Constant;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.BaseActivity;
import com.project.wisdomfirecontrol.common.base.Const;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.common.base.UserInfo;
import com.project.wisdomfirecontrol.common.util.StringUtils;
import com.project.wisdomfirecontrol.common.videocompression.MediaController;
import com.project.wisdomfirecontrol.firecontrol.base.MyApplication;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.FileBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.TroubleTypeBean;
import com.project.wisdomfirecontrol.firecontrol.model.bean.other.TroubleTypesBean;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.CommonProtocol;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.IHttpService;
import com.project.wisdomfirecontrol.firecontrol.ui.adaper_Lv.TroubleTypeAdapter;
import com.project.wisdomfirecontrol.firecontrol.ui.adapter_Rv.GridImageAdapter;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.PhotoUtils;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.Unit_StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2018/3/20.
 * 隐患上报
 */

public class HiddenTroubleReportActivity extends BaseActivity {

    private static final String TAG = "tag";
    private TextView tv_title, tv_submit, tv_decument_type, tv_video_orselect, tv_auto_time;
    private ImageView iv_video, iv_select_photo, iv_select_video, iv_play_voice;
    private EditText et_trouble_des;
    private ImageView iv_select_voice;
    private List<LocalMedia> selectList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PhotoUtils photoUtils;
    private CommonProtocol commonProtocol;
    private String name;
    private String pid;
    private String imageurl = "";
    private String voice = "";
    private String memo;
    private String video = "";
    private StringBuffer stringBuffer;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private TroubleTypeAdapter adapter;
    private String pid1;
    private String terminalNO;
    private String mIsPhotoOrVedio;
    private TextView tv_xunjian_person;

    private UserInfo userInfos;
    private String userName;

    //话筒的图片
    private ImageView mic_image, iv_delect_voice, iv_sure;
    private TextView recording_hint, tv_title_right;
    private LinearLayout ll_trouble, ll_voice_play;
    private PopupWindow mRecordWindow;
    private AnimationDrawable animationDrawable;
    private int lengthVoice;

    //线程操作
    private ExecutorService mExecutorService;
    //录音API
    private MediaRecorder mMediaRecorder;
    //录音开始时间与结束时间
    private long startTime, endTime;
    //录音所保存的文件
    private File mAudioFile;
    //文件列表数据
    private List<FileBean> dataVList;
    //录音文件数据列表适配器
//    private AudioAdapter mAudioAdapter;
    //录音文件保存位置
    private String mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audio/";
    //当前是否正在播放
    private volatile boolean isPlaying;
    //播放音频文件API
    private MediaPlayer mediaPlayer;
    //使用Handler更新UI线程
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.RECORD_SUCCESS:
                    //录音成功，展示数据

                    break;
                //录音失败
                case Constant.RECORD_FAIL:
                    showToast(getString(R.string.record_fail));
                    break;
                //录音时间太短
                case Constant.RECORD_TOO_SHORT:
                    showToast(getString(R.string.time_too_short));
                    break;
                case Constant.PLAY_COMPLETION:
                    showToast(getString(R.string.play_over));
                    break;
                case Constant.PLAY_ERROR:
                    showToast(getString(R.string.play_error));
                    break;

            }
        }
    };

    @Override
    public int getLayoutRes() {
        return R.layout.activity_hiddentroblereport;
    }

    @Override
    public void initView() {
        tv_title = findView(R.id.tv_title);
        tv_title_right = findView(R.id.tv_title_right);
        iv_sure = findView(R.id.iv_sure);
        tv_title_right.setVisibility(View.GONE);
        iv_sure.setVisibility(View.VISIBLE);

        ll_trouble = findView(R.id.ll_trouble);
        tv_decument_type = findView(R.id.tv_decument_type);
        tv_video_orselect = findView(R.id.tv_video_orselect);
        iv_video = findView(R.id.iv_video);
        tv_submit = findView(R.id.tv_submit);
        tv_xunjian_person = findView(R.id.tv_xunjian_person);
        et_trouble_des = findView(R.id.et_trouble_des);
        recyclerView = findView(R.id.recycler);

        ll_voice_play = findView(R.id.ll_voice_play);
        iv_play_voice = findView(R.id.iv_play_voice);
        iv_delect_voice = findView(R.id.iv_delect_voice);

        iv_select_voice = findView(R.id.iv_select_voice);
        iv_select_photo = findView(R.id.iv_select_photo);
        iv_select_video = findView(R.id.iv_select_video);
        tv_auto_time = findView(R.id.tv_auto_time);
        tv_title.setText(R.string.hiddentroublereport_title);

        //录音及播放要使用单线程操作
        mExecutorService = Executors.newSingleThreadExecutor();
        dataVList = new ArrayList<>();
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
//            mIsPhotoOrVedio = "photo";
//            selectPhotoOrVideo("photo");
        }

    };

    private void selectPhotoOrVideo(final String type) {
        requestPermission(1, Manifest.permission.CAMERA, new Runnable() {
            @Override
            public void run() {
                if (photoUtils != null) {
                    photoUtils.showSelectVideoOrPhoto(type, "activity");
                }
            }
        }, new Runnable() {
            @Override
            public void run() {
                Global.showToast(getResources().getString(R.string.no_photo_perssion));
            }
        });
    }

    @Override
    public void initListener() {
        tv_title_right.setOnClickListener(this);
        iv_sure.setOnClickListener(this);
        iv_select_photo.setOnClickListener(this);
        iv_select_video.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        tv_decument_type.setOnClickListener(this);
        iv_video.setOnClickListener(this);
        iv_play_voice.setOnClickListener(this);
        iv_delect_voice.setOnClickListener(this);
        ll_voice_play.setOnClickListener(this);

        iv_select_voice.setOnTouchListener(new PressToSpeakListen());
    }

    @Override
    public void initData() {

        userInfos = Unit_StringUtils.getUserInfos(Global.mContext);
        if (userInfos != null) {
            userName = userInfos.getUserName();
            if (!TextUtils.isEmpty(userName)) {
                tv_xunjian_person.setText(userName);
            }
        }
    }


    @Override
    public void onClick(View v, int id) {
        switch (v.getId()) {
            case R.id.iv_sure:
                iv_sure();
                break;
            case R.id.tv_decument_type:
                tv_decument_type();
                break;

            case R.id.ll_voice_play:
                ll_voice_play();
                break;
            case R.id.iv_select_photo:
                iv_select_photo();
                break;
            case R.id.iv_select_video:
                iv_select_video();
                break;
        }

    }


    //    视频
    private void iv_select_video() {
        iv_video();
    }

    //    图片
    private void iv_select_photo() {
        mIsPhotoOrVedio = "photo";
        selectPhotoOrVideo("photo");
    }


    private int countTemp = 0;

    //    播放语音
    @SuppressLint("SetTextI18n")
    private void ll_voice_play() {
//                使用MediaPlayer播放声音文件
        if (dataVList.size() > 0) {
            startPlay(dataVList.get(dataVList.size() - 1).getFile());
        }
    }


    private Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            countTemp++;
            if (handler != null) {
                handler.postDelayed(this, 1000);
                Log.d(TAG, "ll_voice_play: " + countTemp);
                if (animationDrawable != null) {
                    if (countTemp > lengthVoice) {
                        animationDrawable.stop();
                        handler.removeCallbacks(runnable);
                        countTemp = 0;
                    }
                }
            }
        }
    };


    /*选择photo or video*/
    private void iv_video() {
        mIsPhotoOrVedio = "vedio";
        selectPhotoOrVideo(mIsPhotoOrVedio);
    }

    /*选择类型*/
    private void tv_decument_type() {
        boolean networkConnected = MyApplication.getInstance().isNetworkConnected();
        if (!networkConnected) {
            showToast(getResources().getString(R.string.no_net));
            return;
        }
        pid = Unit_StringUtils.getUserPid(Global.mContext);

        commonProtocol = new CommonProtocol();
        showWaitDialog(this, getString(R.string.submit_in));
        commonProtocol.getSensorList(this, pid);

    }

    //    提交
    private void iv_sure() {
        boolean networkConnected = MyApplication.getInstance().isNetworkConnected();
        if (!networkConnected) {
            showToast(getResources().getString(R.string.no_net));
            return;
        }
        name = Unit_StringUtils.getUserId(Global.mContext);
        pid = Unit_StringUtils.getUserPid(Global.mContext);

        memo = et_trouble_des.getText().toString().trim();

        terminalNO = tv_xunjian_person.getText().toString().trim();
        if (TextUtils.isEmpty(memo)) {
            showToast(getString(R.string.trouble_des));
            return;
        }
        commonProtocol = new CommonProtocol();
        showWaitDialog(this, getString(R.string.submit_in));
        Log.d(TAG, "tv_submit: " + name + " +++ " + memo + " +++ " + imageurl + "\r\n +++ " + pid + " +++ ");
        commonProtocol.troubleReport(this, name, imageurl, video, pid, memo, terminalNO, voice);

    }

    @Override
    public void onHttpSuccess(int reqType, Message msg) {
        super.onHttpSuccess(reqType, msg);
        dismissWaitDialog();
        if (reqType == IHttpService.TYPE_TROUBLE_TYPE_NUM) {

            TroubleTypeBean bean = (TroubleTypeBean) msg.obj;
            List<TroubleTypesBean> beanData = bean.getData();
            if (beanData.size() > 0) {
                showTypeList(beanData);
            } else {
                showToast(getString(R.string.no_trouble_type));
            }

        } else if (reqType == IHttpService.TYPE_TROUBLE) {
            dismissWaitDialog();
            showSuccessDialog(this, getString(R.string.success_submit));
        }
    }

    private void showTypeList(final List<TroubleTypesBean> beanData) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.formcommonlist, null);
        ListView myListView = (ListView) layout.findViewById(R.id.formcustomspinner_list);
        adapter = new TroubleTypeAdapter(HiddenTroubleReportActivity.this, beanData);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int positon, long id) {
                //在这里面就是执行点击后要进行的操作,这里只是做一个显示
                tv_decument_type.setText(beanData.get(positon).getName());
//                terminalNO = beanData.get(positon).getTerminalNO();
                if (!isFinishing() && alertDialog != null) {
                    alertDialog.dismiss();
                }
            }
        });
        builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        alertDialog = builder.create();
        if (!isFinishing()) {
            alertDialog.show();
        }
    }

    @Override
    public void onHttpError(int reqType, String error) {
        super.onHttpError(reqType, error);
        dismissWaitDialog();
        Log.d(TAG, "onHttpError: " + error);
    }

    @Override
    protected void onStart() {
        super.onStart();
        photoUtils = new PhotoUtils(this, recyclerView, onAddPicClickListener,
                selectList, tv_submit, Const.COUNT_THIRD, Const.COUNT_THIRD, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

                    if (mIsPhotoOrVedio.contains("photo")) {
                        selectList = PictureSelector.obtainMultipleResult(data);
                        if (selectList.size() > 0) {
                            showWaitDialog(HiddenTroubleReportActivity.this, "图片处理中...");
                            imageurl = reducePhoto(selectList);
                            Log.d(TAG, "onActivityResult: " + imageurl);
                        }
                        if (photoUtils != null) {
                            photoUtils.setList(selectList);
                            photoUtils.notifyChanged();
                        }

                    } else {
                        selectList = PictureSelector.obtainMultipleResult(data);
//                        视频
                        for (LocalMedia media : selectList) {
                            String mediaVedioPath = media.getPath();
                            Glide.with(HiddenTroubleReportActivity.this)
                                    .load(mediaVedioPath)
                                    .into(iv_video);

                            video = returnVideoStr(mediaVedioPath);

                        }
                    }

                    break;
            }
        }
    }

    private String returnVideoStr(final String mediaVedioPath) {
        new Thread(new Runnable() {
            boolean convertVideo;
            File file = null;

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showWaitDialog(HiddenTroubleReportActivity.this, getString(R.string.video_dispose));
                    }
                });
                convertVideo = MediaController.getInstance().convertVideo(mediaVedioPath);
                Log.d(TAG, "run:111 " + (new File(mediaVedioPath)).length());

                if ((MediaController.cachedFile.length() / 1024) > 200) {
                    MediaController.getInstance().convertVideo(MediaController.cachedFile.getPath());
                    file = MediaController.cachedFile;
                    Log.d(TAG, "run: 333333");
                }
                Log.d(TAG, "run:222 " + file.length() + " ++ "
                        + MediaController.cachedFile.getAbsolutePath() + "\r\n" + MediaController.cachedFile.getPath());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissWaitDialog();
                    }
                });
                if (!file.exists()) {
                    Global.showToast(getString(R.string.video_format));
                    return;
                }
                if (file.length() > 3 * 1024 * 1024) {
                    Global.showToast(getString(R.string.video_lenght_3m));
                    return;
                }
                try {
                    video = StringUtils.fileBase64String(MediaController.cachedFile.getPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.d(TAG, "run: 555" + video.length());
            }

        }).start();

        return video;
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


    /**
     * 按住说话listener
     */
    class PressToSpeakListen implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    //安卓6.0以上录音相应权限处理
                    if (Build.VERSION.SDK_INT > 22) {
                        permissionForM();
                    } else {
                        setmStartRecordVoisce();
                    }
                    recording_hint.setText("手指上滑，取消录音");
                    recording_hint.setBackgroundColor(Color.TRANSPARENT);
                    return true;
                case MotionEvent.ACTION_MOVE: {
                    //在这里只是做了监听，并没有做与发送相关的处理
                    if (event.getY() < 0) {
                        recording_hint.setText("松开手指，取消录音");
                        mic_image.setBackgroundResource(R.drawable.ic_volume_cancel);

                    } else {
                        recording_hint.setText("手指上滑，取消录音");
                        recording_hint.setBackgroundColor(Color.TRANSPARENT);

                    }
                    return true;
                }
                case MotionEvent.ACTION_UP:
                    //抬起手指，停止录音,同时取消Pupo
                    stoVoiceRecours();
                    return true;
                default:

                    return false;
            }
        }
    }

    /*******6.0以上版本手机权限处理***************************/
    /**
     * @description 兼容手机6.0权限管理
     * @author ldm
     * @time 2016/5/24 14:59
     */
    private void permissionForM() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constant.PERMISSIONS_REQUEST_FOR_AUDIO);
        } else {
            setmStartRecordVoisce();
        }

    }

    /*开始录音*/
    private void setmStartRecordVoisce() {
        Log.d("tag", "onLongClick: aaaaa");
        View view = Global.inflate(R.layout.pupo_voice_layout, null);
        mic_image = (ImageView) view.findViewById(R.id.mic_image);
        recording_hint = (TextView) view.findViewById(R.id.recording_hint);
        mRecordWindow = new PopupWindow(view, -1, -1);
        mRecordWindow.showAtLocation(ll_trouble, 17, 0, 0);
        mRecordWindow.setFocusable(true);
        mRecordWindow.setOutsideTouchable(false);
        mRecordWindow.setTouchable(false);

        mic_image.setBackgroundResource(R.drawable.animation_list);//其中R.drawable.animation_list就是上一步准备的动画描述文件的资源名


//获得动画对象
        AnimationDrawable animationDrawable = (AnimationDrawable) mic_image.getBackground();
        animationDrawable.setOneShot(false);
        animationDrawable.start();

        //异步任务执行录音操作
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                //播放前释放资源
                releaseRecorder();
                //执行录音操作
                recordOperation();
            }
        });
    }

    /**
     * @description 翻放录音相关资源
     * @author ldm
     * @time 2017/2/9 9:33
     */
    private void releaseRecorder() {
        if (null != mMediaRecorder) {
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }

    /**
     * @description 录音操作
     * @author ldm
     * @time 2017/2/9 9:34
     */
    private void recordOperation() {
        //创建MediaRecorder对象
        mMediaRecorder = new MediaRecorder();
        //创建录音文件,.m4a为MPEG-4音频标准的文件的扩展名
        mAudioFile = new File(mFilePath + System.currentTimeMillis() + ".m4a");
        //创建父文件夹
        mAudioFile.getParentFile().mkdirs();
        try {
            //创建文件
            mAudioFile.createNewFile();
            //配置mMediaRecorder相应参数
            //从麦克风采集声音数据
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //设置保存文件格式为MP4
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            //设置采样频率,44100是所有安卓设备都支持的频率,频率越高，音质越好，当然文件越大
            mMediaRecorder.setAudioSamplingRate(44100);
            //设置声音数据编码格式,音频通用格式是AAC
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            //设置编码频率
            mMediaRecorder.setAudioEncodingBitRate(96000);
            //设置录音保存的文件
            mMediaRecorder.setOutputFile(mAudioFile.getAbsolutePath());
            //开始录音
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            //记录开始录音时间
            startTime = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
            recordFail();
        }
    }

    /**
     * @description 录音失败处理
     * @author ldm
     * @time 2017/2/9 9:35
     */
    private void recordFail() {
        mAudioFile = null;
        mHandler.sendEmptyMessage(Constant.RECORD_FAIL);
    }


    @SuppressLint("SetTextI18n")
    private void stoVoiceRecours() {
        //停止录音
        if (mMediaRecorder == null) {
            return;
        }
        mMediaRecorder.stop();
        //记录停止时间
        endTime = System.currentTimeMillis();
        //录音时间处理，比如只有大于2秒的录音才算成功
        int time = (int) ((endTime - startTime) / 1000);
        if (time >= 3) {
            //录音成功,添加数据
            FileBean bean = new FileBean();
            bean.setFile(mAudioFile);
            bean.setFileLength(time);
            dataVList.add(bean);
            //录音成功,发Message
            mHandler.sendEmptyMessage(Constant.RECORD_SUCCESS);
        } else {
            mAudioFile = null;
            mHandler.sendEmptyMessage(Constant.RECORD_TOO_SHORT);
        }
        //录音完成释放资源
        releaseRecorder();

        if (mRecordWindow != null) {
            mRecordWindow.dismiss();
            mRecordWindow = null;
        }

        ll_voice_play.setVisibility(View.VISIBLE);
        if (dataVList.size() > 0) {
            FileBean fileBean = dataVList.get(dataVList.size() - 1);
            tv_auto_time.setText(fileBean.getFileLength() + "s");
            File file = fileBean.getFile();
            String filePath = file.getPath();
            try {
                voice = StringUtils.fileBase64String(filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        iv_delect_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_voice_play.setVisibility(View.GONE);
                voice = "";
                dataVList.clear();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == Constant.PERMISSIONS_REQUEST_FOR_AUDIO) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setmStartRecordVoisce();
            } else {
                Global.showToast("请前往权限管理设置录音权限");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * @description 开始播放音频文件
     * @author ldm
     * @time 2017/2/9 16:56
     */
    private void startPlay(File mFile) {

        iv_play_voice.setBackgroundResource(R.drawable.animation_list_wlan);//其中R.drawable.animation_list就是上一步准备的动画描述文件的资源名
//       //获得动画对象
        animationDrawable = (AnimationDrawable) iv_play_voice.getBackground();
        animationDrawable.setOneShot(false);
        animationDrawable.start();
        if (handler != null) {
            handler.postDelayed(runnable, 1000);
        }

        try {
            //初始化播放器
            mediaPlayer = new MediaPlayer();
            //设置播放音频数据文件
            mediaPlayer.setDataSource(mFile.getAbsolutePath());
            //设置播放监听事件
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    //播放完成
                    playEndOrFail(true);
                }
            });
            //播放发生错误监听事件
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    playEndOrFail(false);
                    return true;
                }
            });
            //播放器音量配置
            mediaPlayer.setVolume(1, 1);
            //是否循环播放
            mediaPlayer.setLooping(false);
            //准备及播放
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
            //播放失败正理
            playEndOrFail(false);
        }

    }

    /**
     * @description 停止播放或播放失败处理
     * @author ldm
     * @time 2017/2/9 16:58
     */
    private void playEndOrFail(boolean isEnd) {
        isPlaying = false;
        if (isEnd) {
            mHandler.sendEmptyMessage(Constant.PLAY_COMPLETION);
        } else {
            mHandler.sendEmptyMessage(Constant.PLAY_ERROR);
        }
        if (null != mediaPlayer) {
            mediaPlayer.setOnCompletionListener(null);
            mediaPlayer.setOnErrorListener(null);
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //页面销毁，线程要关闭
        mExecutorService.shutdownNow();
    }

}
