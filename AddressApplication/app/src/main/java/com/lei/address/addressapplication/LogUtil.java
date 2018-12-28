package com.lei.address.addressapplication;

import android.util.Log;

/**
 * Created by Vincent.Lei on 2018/12/27.
 * Title：
 * Note：
 */
public class LogUtil {
    private static final String TAG = "ADDRESS-INFO";

    public static void d(String msg) {
        if (msg != null)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (msg != null)
            Log.e(TAG, msg);
    }
}
