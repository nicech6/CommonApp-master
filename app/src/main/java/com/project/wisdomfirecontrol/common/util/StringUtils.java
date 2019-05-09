package com.project.wisdomfirecontrol.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/2/7.
 */

public class StringUtils {

    /**
     * 判断给定字符串是否空白串。
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        String telRegex = "[1][3456789]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 获取当前时间，标准时间
     *
     * @return
     */
    public static String getCurrentTime() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String curD = formatter.format(curDate);
        return curD;
    }

    public static String showTime(Date date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static boolean isGoodJson(String json) {

        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            return false;
        }
    }

    public static boolean isSuccessDataJson(String result) {
        JSONObject obj = null;
        boolean success;
        try {
            try {
                obj = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            success = obj.getBoolean("success");
            String data = obj.getString("data");
            if (isEmpty(data)) {
                success = false;
            }
            return success;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断返回json成功与否
     */
    public static boolean isSuccessJson(String result) {
        JSONObject obj = null;
        boolean success;
        try {
            try {
                new JsonParser().parse(result);
                obj = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            assert obj != null;
            success = obj.getBoolean("success");
            return success;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 图片按比例大小压缩方法（根据路径获取图片并压缩）
     *
     * @param srcPath
     * @return
     */
    public static String getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static String compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 30, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        byte[] buffer = baos.toByteArray();
        String string = Base64.encodeToString(buffer, Base64.DEFAULT);
        return string;
    }


    public static String byettoString(File file) {
        String string = "";
        byte[] byetsFromFile = getByetsFromFile(file);
        string = Base64.encodeToString(byetsFromFile, Base64.DEFAULT);
        return string;
    }

    /**
     * 图片文件转Base64字符串 * @param path 文件所在的绝对路径加文件名　 * @return
     */
    public static String fileBase64String(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            //转换成输入流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = fis.read(buffer)) >= 0) {
                baos.write(buffer, 0, count);//读取输入流并写入输出字节流中
            }
            fis.close();//关闭文件输入流
            // 进行Base64编码
            return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            return null;
        }
    }


    //    文件转流
    private static byte[] getByetsFromFile(File file) {
        FileInputStream is = null;
        // 获取文件大小
        long length = file.length();
        Log.d("tag", "getByetsFromFile: " + length);
        // 创建一个数据来保存文件数据
        byte[] fileData = new byte[(int) length];

        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int bytesRead = 0;
        // 读取数据到byte数组中
        while (bytesRead != fileData.length) {
            try {
                bytesRead += is.read(fileData, bytesRead, fileData.length - bytesRead);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return fileData;
    }

    /**
     * 获取应用详情页面intent
     *
     * @return
     */
    public static Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return localIntent;
    }

    /**
     * listview同步滑动效果
     * */
    public static void setListViewOnTouchAndScrollListener(final ListView listView1, final ListView listView2){


        //设置listview2列表的scroll监听，用于滑动过程中左右不同步时校正
        listView2.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //如果停止滑动
                if(scrollState == SCROLL_STATE_IDLE /*|| scrollState == 1*/){
                    //获得第一个子view
                    View subView = view.getChildAt(0);

                    if(subView !=null){
                        final int top = subView.getTop();
                        final int top1 = listView1.getChildAt(0).getTop();
                        final int position = view.getFirstVisiblePosition();

                        //如果两个首个显示的子view高度不等
                        if(top != top1){
                            listView1.setSelectionFromTop(position, top);
                        }
                    }
                }

            }

            public void onScroll(AbsListView view, final int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                View subView = view.getChildAt(0);
                if(subView != null){
                    final int top = subView.getTop();
                    //如果两个首个显示的子view高度不等
                    int top1 = listView1.getChildAt(0).getTop();
                    if(!(top1 - 7 < top &&top < top1 + 7)){
                        listView1.setSelectionFromTop(firstVisibleItem, top);
                        listView2.setSelectionFromTop(firstVisibleItem, top);
                    }

                }
            }
        });

        //设置listview1列表的scroll监听，用于滑动过程中左右不同步时校正
        listView1.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == SCROLL_STATE_IDLE /*|| scrollState == 1*/){
                    //获得第一个子view
                    View subView = view.getChildAt(0);

                    if(subView !=null){
                        final int top = subView.getTop();
                        final int top1 = listView2.getChildAt(0).getTop();
                        final int position = view.getFirstVisiblePosition();

                        //如果两个首个显示的子view高度不等
                        if(top != top1){
                            listView1.setSelectionFromTop(position, top);
                            listView2.setSelectionFromTop(position, top);
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, final int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                View subView = view.getChildAt(0);
                if(subView != null){
                    final int top = subView.getTop();
                    listView1.setSelectionFromTop(firstVisibleItem, top);
                    listView2.setSelectionFromTop(firstVisibleItem, top);

                }
            }
        });
    }

    public static String returnStrTime(String string) {
        String[] strings = string.split("\\.");
        return strings[0];
    }

}
