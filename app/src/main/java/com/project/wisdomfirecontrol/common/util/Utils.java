package com.project.wisdomfirecontrol.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.project.wisdomfirecontrol.firecontrol.model.protocol.IHttpService;

import java.text.NumberFormat;

/**
 * 工具类，封装常用的一些方法
 *
 * @author LMX
 */
public class Utils {

    /**
     * 查找一个布局里的所有的按钮并设置点击事件
     *
     * @param rootView
     * @param listener
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    public static void findButtonAndSetOnClickListener(View rootView,
                                                       OnClickListener listener) {
        if (rootView instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) rootView;
            for (int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                // 如果是按钮设置点击事件
                if (child instanceof Button || child instanceof ImageButton) {
                    // 没有设置过监听器才设置
                    if (!child.hasOnClickListeners())
                        child.setOnClickListener(listener); // 设置点击事件
                }
                if (child instanceof ViewGroup) {
                    findButtonAndSetOnClickListener(child, listener);
                }
            }
        }
    }

    /** 格式化日期显示 */
    public static String formatDate(long date) {
        if (DateUtils.isToday(date)) {
            return DateFormat.format("今天 kk:mm", date).toString();
        } else {
            return DateFormat.format("yyyy-MM-dd kk:mm:ss", date).toString();
        }
    }

    /** 格式化金额显示 */
    public static String formatAmount(double amount) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(1);     // 金额只保存一位有效数字
        return format.format(amount);
    }


    /** 匹配ip地址的正则表达式 */
    private static final String IP_REGEXP =
            "(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9" +
            "])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\" +
            ".(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\." +
            "(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])";

    /**
     * 替换一个url中的ip地址 <br/>
     * 例如：<br/>
     *      url为： http://127.0.0.1:8080/jinquan/1.jpg <br/>
     *      调用 replaceIp(url, "192.168.1.1")后，<br/>
     *      则得到新的url为： http://192.168.1.1:8080/jinquan/1.jpg
     */
    public static String replaceIp(String url, String ip) {
        // 匹配ip的正则表达式
        return url.replaceAll(IP_REGEXP, ip);
    }

    public static String replaceIp(String url) {
        // 匹配ip的正则表达式
        return url.replaceAll(IP_REGEXP, IHttpService.HOST_IP);
    }

    private static Boolean _isTablet = null;

    public static boolean isTablet(Context context) {
        if (_isTablet == null) {
            boolean flag;
            if ((0xf & context.getResources().getConfiguration().screenLayout) >= 3)
                flag = true;
            else
                flag = false;
            _isTablet = Boolean.valueOf(flag);
        }
        return _isTablet.booleanValue();
    }

    public static float dpToPixel(Context context, float dp) {
        return dp * (getDisplayMetrics(context).densityDpi / 160F);
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(displaymetrics);
        return displaymetrics;
    }

    public static float getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }
}