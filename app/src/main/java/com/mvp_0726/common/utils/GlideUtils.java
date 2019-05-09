package com.mvp_0726.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.mvp_0726.common.base.MvpApplication;
import com.project.wisdomfirecontrol.GlideApp;
import com.project.wisdomfirecontrol.R;

/**
 * Created  on 2018-05-28.
 */

public class GlideUtils {
    /*
    * 正常加载
    * */
    public static void loadImageview(ImageView imageview, String path) {
        Glide.with(MvpApplication.getContext()).load(path).into(imageview);
    }

    public static void loadImageviewGlideAPP(Context context, ImageView imageview, String path,int errorPath,int loadingPath) {
        RequestBuilder<Bitmap> bitmapRequestBuilder = GlideApp.with(context)
                .asBitmap()//指定Bitmap类型的RequestBuilder
                .load(path)//网络URL
                .placeholder(errorPath)//占位图片
                .error(R.drawable.icon_common)//异常图片
                .fallback(errorPath);//当url为空时，显示图片

        bitmapRequestBuilder.into(imageview);
    }


    /*
    *
    * 加载带有占位图的view
    * */
    public static void loadImageViewLoading(ImageView imageView, Object path, int errorPath, int loadingPath) {
        RequestOptions requestOptions = new RequestOptions();
        Glide.with(MvpApplication.getContext()).load(path)
                .apply(requestOptions.placeholder(loadingPath).error(errorPath))
                .into(imageView);
    }

    /*
    * 加载指定大小的image
    * */
    public static void loadImageViewSize(ImageView imageView, String path, int loadingPath, int errorPath, int width, int height) {
        RequestOptions requestOptions = new RequestOptions();
        Glide.with(MvpApplication.getContext()).load(path).apply(requestOptions.override(width, height)
                .placeholder(loadingPath).error(errorPath))
                .into(imageView);
    }

    /*
    * 裁剪大小，设置缓存模式，防止oom(不缓存)
    *
    * */
    public static void loadImageViewOOM(ImageView imageView, int loadingPath, int errorPath, String path, int height, int width) {
        RequestOptions requestOptions = new RequestOptions();
        Glide.with(MvpApplication.getContext()).load(path)
                .apply(requestOptions.override(width, height).skipMemoryCache(true)
                        .placeholder(loadingPath).error(errorPath))
                .into(imageView);
    }

    /*
    * 加载圆形图片
    * */
    public static void loadImageViewCircle(ImageView imageView, String path, int loadingPath, int errorPath) {
        RequestOptions requestOptions = new RequestOptions();
        Glide.with(MvpApplication.getContext()).asBitmap().load(path)
                .apply(requestOptions.skipMemoryCache(true).optionalCircleCrop()
                        .placeholder(loadingPath).error(errorPath))
                .into(imageView);
    }
}
