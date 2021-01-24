package com.demo.recyclerview.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * @author wpq
 * @version 1.0
 */
public class MainHandler extends Handler {

    private static final MainHandler instance = new MainHandler(Looper.getMainLooper());

    public MainHandler(Looper looper) {
        super(looper);
    }

    public static MainHandler getInstance() {
        return instance;
    }

    public static void runOnUiThread(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        if (Looper.getMainLooper() == Looper.myLooper()) {
            runnable.run();
        } else {
            instance.post(runnable);
        }
    }
}
