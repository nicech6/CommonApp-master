package com.mvp_0726.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * 全局公共类, 封装：屏幕宽高获取，单位转换，主线程运行等
 *
 * @author LMX
 */
public class Global {

    @SuppressLint("StaticFieldLeak")
    public static Context mContext;

    private static float mDensity;
    public static float mScreenWidth;
    public static float mScreenHeight;

    public static void init(Context context) {
        mContext = context;
        initScreenSize();
    }

    private static void initScreenSize() {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        mDensity = dm.density;
        mScreenHeight = dm.heightPixels;
        mScreenWidth = dm.widthPixels;
    }

    /**
     * 判断是否为平板
     *
     * @return
     */
    public static boolean isPad() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        // 屏幕尺寸
        double screenInches = Math.sqrt(x + y);
        // 大于6尺寸则为Pad
        if (screenInches >= 6.0) {
            return true;
        }
        return false;
    }

    public static View inflate(int layoutResID, ViewGroup parent) {
        return LayoutInflater.from(mContext).inflate(layoutResID, parent, false);
    }

    public static View inflate(int layoutResID) {
        return inflate(layoutResID, null);
    }

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static Handler getMainHandler() {
        return mHandler;
    }

    /**
     * 判断当前线程是否是主线程
     *
     * @return true表示当前是在主线程中运行
     */
    public static boolean isUIThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    public static void runOnUIThread(Runnable run) {
        if (isUIThread()) {
            run.run();
        } else {
            mHandler.post(run);
        }
    }

    private static Toast mToast;

    /**
     * 可以在子线程中调用
     *
     * @param msg toast内容
     */
    public static void showToast(final String msg) {
        runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
                }
                mToast.setText(msg);
                mToast.show();
            }
        });
    }

    public static String getString(int stringId) {
        return mContext.getResources().getString(stringId);
    }

    public static int getColor(int colorId) {
        return mContext.getResources().getColor(colorId);
    }

}
