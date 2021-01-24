package com.ab.lib.widget.recyclerview;

import android.content.Context;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ab.lib.widget.recyclerview.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author WuPuquan
 * @version 1.0
 * @since 2017/10/11 19:40
 */

public class LoadMoreView extends FrameLayout implements View.OnClickListener {

    @IntDef({State.LOADING, State.COMPLETE, State.ERROR, State.NO_MORE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State{
        /** 加载中... */
        int LOADING = 0;
        /** 加载完成 */
        int COMPLETE = 1;
        /** 加载失败，点击重试 */
        int ERROR = 2;
        /** 没有更多了 */
        int NO_MORE = 3;
    }

    private View loadingView;
    private View completeView;
    private View errorView;
    private View noMoreView;

    private OnClickListener mOnClickListener;

    private LoadMoreView(@NonNull Context context) {
        super(context);
    }

    public LoadMoreView(@NonNull Context context, OnClickListener onClickListener) {
        this(context);
        this.mOnClickListener = onClickListener;
        init();
    }

    private void init() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        initLoadingView();
        initCompleteView();
        initErrorView();
        initNoMoreView();

        setOnClickListener(this);
        setClickable(false);

        setState(State.LOADING);
    }

    private void initLoadingView() {
        LinearLayout loading = new LinearLayout(getContext());
        loading.setOrientation(LinearLayout.HORIZONTAL);
        loading.setGravity(Gravity.CENTER);
        loading.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        ProgressBar progressBar = new ProgressBar(getContext());
        LinearLayout.LayoutParams lpPb = new LinearLayout.LayoutParams(dp2px(20), dp2px(20));
        progressBar.setLayoutParams(lpPb);
        loading.addView(progressBar);

        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams lpLoading = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpLoading.setMargins(dp2px(10), dp2px(16), dp2px(10), dp2px(16));
        textView.setLayoutParams(lpLoading);
        textView.setTextSize(14.0f);
        textView.setTextColor(0xFF666666);
        textView.setText(getResources().getString(R.string.LoadMoreRecyclerView_loading));
        loading.addView(textView);

        loadingView = loading;
        addView(loadingView);
    }

    private void initCompleteView() {
        LinearLayout complete = new LinearLayout(getContext());
        complete.setOrientation(LinearLayout.HORIZONTAL);
        complete.setGravity(Gravity.CENTER);
        complete.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams lpComplete = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpComplete.setMargins(dp2px(10), dp2px(16), dp2px(10), dp2px(16));
        textView.setLayoutParams(lpComplete);
        textView.setTextSize(14.0f);
        textView.setTextColor(0xFF666666);
        textView.setText(getResources().getString(R.string.LoadMoreRecyclerView_complete));
        complete.addView(textView);

        completeView = complete;
        addView(completeView);
    }

    private void initErrorView() {
        LinearLayout error = new LinearLayout(getContext());
        error.setOrientation(LinearLayout.HORIZONTAL);
        error.setGravity(Gravity.CENTER);
        error.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams lpError = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpError.setMargins(dp2px(10), dp2px(16), dp2px(10), dp2px(16));
        textView.setLayoutParams(lpError);
        textView.setTextSize(14.0f);
        textView.setTextColor(0xFF666666);
        textView.setText(getResources().getString(R.string.LoadMoreRecyclerView_error));
        error.addView(textView);

        errorView = error;
        addView(errorView);
    }

    private void initNoMoreView() {
        LinearLayout noMore = new LinearLayout(getContext());
        noMore.setOrientation(LinearLayout.HORIZONTAL);
        noMore.setGravity(Gravity.CENTER);
        noMore.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams lpNoMore = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpNoMore.setMargins(dp2px(10), dp2px(16), dp2px(10), dp2px(16));
        textView.setLayoutParams(lpNoMore);
        textView.setTextSize(14.0f);
        textView.setTextColor(0xFF666666);
        textView.setText(getResources().getString(R.string.LoadMoreRecyclerView_noMore));
        noMore.addView(textView);

        noMoreView = noMore;
        addView(noMoreView);
    }

    public void setLoadingView(View loadingView) {
        try {
            removeView(this.loadingView);
            this.loadingView = loadingView;
            addView(this.loadingView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCompleteView(View completeView) {
        try {
            removeView(this.completeView);
            this.completeView = completeView;
            addView(this.completeView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setErrorView(View errorView) {
        try {
            removeView(this.errorView);
            this.errorView = errorView;
            addView(this.errorView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNoMoreView(View noMoreView) {
        try {
            removeView(this.noMoreView);
            this.noMoreView = noMoreView;
            addView(this.noMoreView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnClickListener != null) {
            mOnClickListener.onLoadMoreViewClick();
        }
    }

    /**
     * 切换状态
     */
    public void setState(@State int state) {
        switch (state) {
            case State.LOADING:
                loadingView.setVisibility(View.VISIBLE);
                completeView.setVisibility(View.GONE);
                errorView.setVisibility(View.GONE);
                noMoreView.setVisibility(View.GONE);
                this.setClickable(false);
                break;
            case State.COMPLETE:
                loadingView.setVisibility(View.GONE);
                completeView.setVisibility(View.VISIBLE);
                errorView.setVisibility(View.GONE);
                noMoreView.setVisibility(View.GONE);
                this.setClickable(true);
                break;
            case State.ERROR:
                loadingView.setVisibility(View.GONE);
                completeView.setVisibility(View.GONE);
                errorView.setVisibility(View.VISIBLE);
                noMoreView.setVisibility(View.GONE);
                this.setClickable(true);
                break;
            case State.NO_MORE:
                loadingView.setVisibility(View.GONE);
                completeView.setVisibility(View.GONE);
                errorView.setVisibility(View.GONE);
                noMoreView.setVisibility(View.VISIBLE);
                this.setClickable(false);
                break;
        }
    }

    private int dp2px(float dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    public interface OnClickListener{
        /** 加载更多 | 加载失败，点击重新加载 */
        void onLoadMoreViewClick();
    }

}
