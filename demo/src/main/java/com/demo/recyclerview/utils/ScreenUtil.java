package com.demo.recyclerview.utils;

/**
 * @author wpq
 * @version 1.0
 */
public final class ScreenUtil {

    private ScreenUtil() {
        throw new AssertionError("cannot be instantiated");
    }

    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(final float dpValue) {
        final float scale = GlobalContext.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(final float pxValue) {
        final float scale = GlobalContext.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(final float spValue) {
        final float fontScale = GlobalContext.getAppContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转sp
     *
     * @param pxValue px值
     * @return sp值
     */
    public static int px2sp(final float pxValue) {
        final float fontScale = GlobalContext.getAppContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int getScreenWidth() {
        return GlobalContext.getAppContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return GlobalContext.getAppContext().getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 根据尺寸资源ID获取它的px值
     *
     * @param resId 尺寸资源ID
     * @return 尺寸资源ID的px值
     */
    public static int getDimension(int resId) {
        return GlobalContext.getAppContext().getResources().getDimensionPixelSize(resId);
    }

    /**
     * 根据颜色的资源ID获取这个颜色的整型值
     *
     * @param resId 颜色的资源ID
     * @return 颜色的整型值
     */
    public static int getColor(int resId) {
        return GlobalContext.getAppContext().getResources().getColor(resId);
    }
}
