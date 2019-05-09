package com.project.wisdomfirecontrol.firecontrol.ui.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.base.Global;
import com.project.wisdomfirecontrol.firecontrol.ui.adapter_Rv.GridImageAdapter;
import com.project.wisdomfirecontrol.firecontrol.ui.view.FullyGridLayoutManager;

import java.io.File;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/3/21.
 */

public class PhotoUtils {

    private Context context;
    private FragmentActivity fragmentActivity;
    private PictureSelector pictureSelector;

    private View mPopView1;
    private PopupWindow popWindow1;
    private TextView mBtn_take_photo;
    private TextView mBtn_from_gallery;
    private TextView mBtn_pop_exit;
    private View bottom;
    private List<LocalMedia> selectList;
    private GridImageAdapter adapter;
    private int count;
    private PopupWindow popupWindow;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public PhotoUtils(Context context, View contentView, int weight, int height, View bottom, boolean isWhere, boolean iscenter) {
        this.context = context;
        this.bottom = bottom;
        if (isWhere) {
            popupWindow = initPopWindow(context, contentView, weight, height);
        } else {
            popupWindow = initPopWindowBlueLine(context, contentView, weight, height, iscenter);
        }
        showPopupWindwShowType(popupWindow, bottom, isWhere, iscenter);
    }


    public PhotoUtils(Context context, RecyclerView recyclerView, GridImageAdapter.onAddPicClickListener
            onAddPicClickListener, List<LocalMedia> selectList, View bottom, int count, int countTemp, boolean isGoneOrVi) {
        this.context = context;
        this.bottom = bottom;
        this.selectList = selectList;
        this.count = count;
        initPopupWindow(context);
        selectPhoto(context, recyclerView, selectList, onAddPicClickListener, countTemp, isGoneOrVi);
    }

    public PhotoUtils(final Context context, final List<LocalMedia> selectList,
                      View imageView, View bottom, int count) {

        this.context = context;
        this.bottom = bottom;
        this.selectList = selectList;
        this.count = count;
        initPopupWindow(context);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (selectList.size() > 0) {
//                    LocalMedia media = selectList.get(0);
//                    String pictureType = media.getPictureType();
//                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
//                    switch (mediaType) {
//                        case 1:
//
//                            // 预览图片 可自定长按保存路径
//                            //PictureSelector.create(Main_UnitActivity.this).externalPicturePreview(position, "/custom_file", selectList);
//                            PictureSelector.create((Activity) context).externalPicturePreview(0, selectList);
//                            break;
//                        case 2:
//                            // 预览视频
//                            PictureSelector.create((Activity) context).externalPictureVideo(media.getPath());
//                            break;
//                    }
//                }
//            }
//        });
    }

    public void showSelectVideoOrPhoto(final String type, final String activity) {
        if (type.contains("vedio")) {
            mBtn_take_photo.setText("录像");
            mBtn_from_gallery.setText("视频");
        }

        mBtn_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrDismiss();
                playPhotoOrVedio(type, activity);
            }
        });

        mBtn_from_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrDismiss();
                seletePhotoOrVedio(type, activity);
            }
        });
        mBtn_pop_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrDismiss();
            }
        });
        showPopupWindwShow(popWindow1, bottom);
    }

    public void showOrDismiss() {
        if (popWindow1.isShowing()) {
            popWindow1.dismiss();
        }
    }

    public void showTypeOrDismiss() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    private void selectPhoto(final Context context, RecyclerView recyclerView,
                             final List<LocalMedia> selectList, GridImageAdapter.onAddPicClickListener onAddPicClickListener,
                             int countTemp, boolean isGoneOrVi) {

        FullyGridLayoutManager manager = new FullyGridLayoutManager(context, countTemp, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(context, onAddPicClickListener, isGoneOrVi);
        adapter.setList(selectList);
        adapter.setSelectMax(count);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d("tag", "onItemClick:11 " + selectList.size());
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    Global.showToast(String.valueOf(position));
                    Log.d("tag", "onItemClick: " + pictureType + " ++ " + mediaType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(Main_UnitActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create((Activity) context).externalPicturePreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create((Activity) context).externalPictureVideo(media.getPath());
                            break;
                    }
                }
            }
        });
    }

    private void seletePhotoOrVedio(String type, String activity) {
        if (activity.contains("fragment")) {
            pictureSelector = PictureSelector.create(fragmentActivity);
        } else {
            pictureSelector = PictureSelector.create((Activity) context);
        }
        // 选择图片or视频
        if (type.contains("vedio")) {
            pictureSelector.openGallery(PictureMimeType.ofVideo())
                    .selectionMode(PictureConfig.SINGLE)
                    .videoQuality(0)
                    .videoMaxSecond(15)
                    .recordVideoSecond(15)
                    .previewVideo(true)
                    .forResult(PictureConfig.CHOOSE_REQUEST);

        } else {
            pictureSelector.openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .maxSelectNum(count)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                    .previewImage(true)// 是否可预览图片
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .compress(true)// 是否压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    .compressSavePath(getPath())//压缩图片保存地址
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .selectionMedia(selectList)// 是否传入已选图片
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        }
    }

    private void playPhotoOrVedio(String type, String activity) {
        if (activity.contains("fragment")) {
            pictureSelector = PictureSelector.create(fragmentActivity);
        } else {
            pictureSelector = PictureSelector.create((Activity) context);
        }
        if (type.contains("vedio")) {
            pictureSelector.openCamera(PictureMimeType.ofVideo())
                    .selectionMode(PictureConfig.SINGLE)
                    .videoQuality(0)
                    .videoMaxSecond(15)
                    .previewVideo(true)
                    .recordVideoSecond(15)
                    .withAspectRatio(16, 16)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .forResult(PictureConfig.CHOOSE_REQUEST);
            Log.d("tag", "playPhotoOrVedio:11 " + type);
        } else {
            pictureSelector.openCamera(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .maxSelectNum(count)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .previewImage(true)// 是否可预览图片
                    .compress(true)// 是否压缩
                    .glideOverride(100, 100)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .withAspectRatio(16, 9)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .selectionMedia(selectList)// 是否传入已选图片
                    .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        }
    }

    private void initPopupWindow(Context context) {
        mPopView1 = (View) LayoutInflater.from(context).inflate(R.layout.layout_photo_upload, null);
        popWindow1 = initPopWindow(context, mPopView1, (int) Global.mScreenWidth * 2 / 3, ActionBar.LayoutParams.WRAP_CONTENT);
        mBtn_take_photo = (TextView) mPopView1.findViewById(R.id.btn_take_photo);
        mBtn_from_gallery = (TextView) mPopView1.findViewById(R.id.btn_from_gallery);
        mBtn_pop_exit = (TextView) mPopView1.findViewById(R.id.btn_Pop_exit);
    }

    public static PopupWindow initPopWindow(Context mContext, View contentView, int weight, int height) {
        PopupWindow mPopupWindow = new PopupWindow(contentView, weight, height, true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(mContext.getResources().getColor(R.color.gray_99)));
        mPopupWindow.setAnimationStyle(R.style.app_pop);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.update();
        return mPopupWindow;
    }

    public static PopupWindow initPopWindowBlueLine(Context mContext, View contentView, int weight, int height, boolean iscenter) {
        PopupWindow mPopupWindow = new PopupWindow(contentView, weight, height, true);
        if (iscenter) {
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(mContext.getResources().getColor(R.color.blue)));
        } else {
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(mContext.getResources().getColor(R.color.white)));
        }
        mPopupWindow.setAnimationStyle(R.style.app_pop);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.update();
        return mPopupWindow;
    }

    private void showPopupWindwShow(PopupWindow mPopupWindow, View locationView) {
        //mPopupWindow.showAsDropDown(locationView, 0, -3);
        mPopupWindow.showAtLocation(locationView, android.view.Gravity.BOTTOM, 0, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("RtlHardcoded")
    public static void showPopupWindwShowType(PopupWindow mPopupWindow, View locationView, boolean isWhere, boolean iscenter) {
        if (isWhere) {
            mPopupWindow.showAtLocation(locationView, android.view.Gravity.BOTTOM, 0, 0);
        } else {
            if (iscenter) {
                mPopupWindow.showAsDropDown(locationView);
            } else {
//                mPopupWindow.showAsDropDown(locationView, (int) Global.mScreenWidth / 5, (int) Global.mScreenWidth / 5, Gravity.CENTER);
//                mPopupWindow.showAsDropDown(locationView, (int) Global.mScreenWidth / 5, (int) Global.mScreenWidth / 5, Gravity.CENTER_HORIZONTAL);
                mPopupWindow.showAsDropDown(locationView, 0, 0, Gravity.CENTER_HORIZONTAL);
            }
        }
    }


    // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
    public void delectSelectPhotos() {
        RxPermissions permissions = new RxPermissions((Activity) context);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(context);
                } else {
                    Global.showToast(context.getResources().getString(R.string.picture_jurisdiction));
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    /**
     * 自定义压缩存储地址
     *
     * @return
     */
    private static String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Fire/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    public void setList(List<LocalMedia> selectList) {
        this.selectList = selectList;
        if (adapter != null) {
            adapter.setList(this.selectList);
        }
    }

    public void notifyChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
