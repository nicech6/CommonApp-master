package com.project.wisdomfirecontrol.firecontrol.ui.view.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

public class T {
    @SuppressLint("StaticFieldLeak")
    private static TPrompt tPrompt;
    @SuppressLint("StaticFieldLeak")
    private static TPromptSuccess tPromptSuccess;
    @SuppressLint("StaticFieldLeak")
    private static TPromptError tPromptError;

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showAnimToast(Context mContext, String msg) {
        if (!isFastDoubleClick()) {
            if (tPrompt == null) {
                tPrompt = new TPrompt(mContext);
            }
            tPrompt.showToast(msg);
        }
    }

    public static void showAnimSuccessToast(Context mContext, String msg) {
        if (!isFastDoubleClick()) {
            if (tPromptSuccess == null) {
                tPromptSuccess = new TPromptSuccess(mContext);
            }
            tPromptSuccess.showToast(msg);
        }
    }

    public static void showAnimErrorToast(Context mContext, String msg) {
        if (!isFastDoubleClick()) {
            if (tPromptError == null) {
                tPromptError = new TPromptError(mContext);
            }
            tPromptError.showToast(msg);
        }
    }

    protected static long lastClickTime;

    protected static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
