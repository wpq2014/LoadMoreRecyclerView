package com.demo.recyclerview.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;

/**
 * @author WuPuquan
 * @version 1.0
 * @since 2017/9/14 18:41
 */
public final class GlobalContext {

    @SuppressLint("StaticFieldLeak")
    private volatile static Application sApplication;

    private GlobalContext() {
        throw new AssertionError("cannot be instantiated");
    }

    public static void setApplication(Application application) {
        sApplication = application;
    }

    public static Application getApplication() {
        return sApplication;
    }

    public static Context getAppContext() {
        return sApplication.getApplicationContext();
    }

    /** 获取当前版本号 */
    public static int getAppVersionCode() {
        int localVerCode = -1;
        try {
            PackageInfo packageInfo = sApplication.getPackageManager().getPackageInfo(sApplication.getPackageName(), 0);
            localVerCode = packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localVerCode;
    }

    /** 获取当前版本名称 */
    public static String getAppVersionName() {
        String localVerName = "";
        try {
            PackageInfo packageInfo = sApplication.getPackageManager().getPackageInfo(sApplication.getPackageName(), 0);
            localVerName = packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localVerName;
    }
}
