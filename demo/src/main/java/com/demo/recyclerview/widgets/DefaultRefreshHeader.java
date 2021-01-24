package com.demo.recyclerview.widgets;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.demo.recyclerview.R;
import com.demo.recyclerview.utils.ScreenUtil;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/**
 * @author wupuquan
 * @since 2021/1/24
 */
public class DefaultRefreshHeader extends LinearLayout implements RefreshHeader {
    private static final String TAG = DefaultRefreshHeader.class.getSimpleName();

    private TextView mTvHeader = null;
    private ImageView mIvHeader = null;
    private OnHeaderRefreshListener mOnHeaderRefreshListener = null;

    protected RefreshKernel mRefreshKernel;

    public DefaultRefreshHeader(Context context) {
        super(context);
        init();
    }

    public DefaultRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DefaultRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public DefaultRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
        setPadding(0, ScreenUtil.dp2px(10), 0, ScreenUtil.dp2px(10));

        mTvHeader = new TextView(getContext());
        mTvHeader.setGravity(Gravity.CENTER);
        addView(mTvHeader);

        mIvHeader = new ImageView(getContext());
        mIvHeader.setImageResource(R.drawable.loading_anim_list);
        LinearLayout.LayoutParams ivLp = new LinearLayout.LayoutParams(ScreenUtil.dp2px(48), ScreenUtil.dp2px(48));
        ivLp.topMargin = ScreenUtil.dp2px(10);
        addView(mIvHeader, ivLp);
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {
        Log.e(TAG, "onInitialized");
        mRefreshKernel = kernel;
    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
//        Log.e(TAG, "onMoving, isDragging=" + isDragging + ", percent=" + percent);
//        // 设置缩放中心点
//        mIvHeader.setPivotX(mIvHeader.getMeasuredWidth() / 2f);
//        mIvHeader.setPivotY(mIvHeader.getMeasuredHeight());
//        // 下拉的百分比小于100%时，不断调用 setScale 方法改变图片大小
//        if (percent <= 1.0) {
//            mIvHeader.setScaleX(percent);
//            mIvHeader.setScaleY(percent);
//        } else {
//            mIvHeader.setScaleX(1.0F);
//            mIvHeader.setScaleY(1.0F);
//        }
        if (isDragging) {
//            showText();
            startAnim();
        } else if (percent < 1.0) {
            stopAnim(); // 没拉到1.0松手了
        }
    }

    private void showText() {
        if (mTvHeader.getVisibility() != View.VISIBLE) {
            mTvHeader.setVisibility(View.VISIBLE);
        }
    }

    private void startAnim() {
        Log.e(TAG, "startAnim");
        Drawable drawable = mIvHeader.getDrawable();
        if (drawable instanceof AnimationDrawable) {
            AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
            if (!animationDrawable.isRunning()) {
                animationDrawable.start();
            }
        }
    }

    private void stopAnim() {
        Log.e(TAG, "stopAnim");
        Drawable drawable = mIvHeader.getDrawable();
        if (drawable instanceof AnimationDrawable) {
            AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
            if (animationDrawable.isRunning()) {
                animationDrawable.stop();
            }
        }
    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        Log.e(TAG, "onReleased");
//        mTvHeader.setVisibility(View.GONE);
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        Log.e(TAG, "onStartAnimator");
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        Log.e(TAG, "onFinish");
        stopAnim();
        return 0;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    private static String REFRESH_HEADER_PULLING = null; //"下拉刷新";
    private static String REFRESH_HEADER_REFRESHING = null; //"刷新中...";
    private static String REFRESH_HEADER_LOADING = null; //"加载中...";
    private static String REFRESH_HEADER_RELEASE = null; //"释放即可刷新";
    private static String REFRESH_HEADER_FINISH = null; //"刷新完成";
    private static String REFRESH_HEADER_FAILED = null; //"刷新失败";
    private static String REFRESH_HEADER_SECONDARY = null; //"继续下拉进入二楼";

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
//        DKLog.e("onStateChanged: $newState")
        switch (newState) {
            case None:
            case PullDownToRefresh:
                mTvHeader.setText("下拉即可刷新");
                break;
            case Refreshing:
            case RefreshReleased:
                mTvHeader.setText("正在刷新...");
                break;
            case ReleaseToRefresh:
                mTvHeader.setText("松手即可刷新");
                break;
            case ReleaseToTwoLevel:
                mTvHeader.setText("继续下拉进入二楼");
                break;
            case Loading:
                mTvHeader.setText("加载中...");
                break;
        }
        if (mOnHeaderRefreshListener != null) {
            mOnHeaderRefreshListener.onStateChanged(refreshLayout, oldState, newState);
        }
    }

    public interface OnHeaderRefreshListener {
        void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState);
    }

    public void setOnHeaderRefreshListener(OnHeaderRefreshListener onHeaderRefreshListener) {
        this.mOnHeaderRefreshListener = onHeaderRefreshListener;
    }
}
