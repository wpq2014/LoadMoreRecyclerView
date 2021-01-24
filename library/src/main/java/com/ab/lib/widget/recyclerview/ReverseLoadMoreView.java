package com.ab.lib.widget.recyclerview;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * 反向加载更多，一般是顶部加载更多布局，适用于聊天界面
 * @author wpq
 * @version 1.0
 */
public class ReverseLoadMoreView extends LinearLayout{

    /** 加载中... */
    public final static int STATE_LOADING = 0;
    /** 加载完成 */
    public final static int STATE_COMPLETE = 1;
    /** 没有更多了 */
    public final static int STATE_NOMORE = 3;

    private ProgressBar mPbLoadMore;

    public ReverseLoadMoreView(Context context) {
        super(context);

        init();
    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mPbLoadMore = new ProgressBar(getContext());
        LayoutParams lpPb = new LayoutParams(dp2px(20), dp2px(20));
        lpPb.setMargins(dp2px(10), dp2px(10), dp2px(10), dp2px(10));
        mPbLoadMore.setLayoutParams(lpPb);
        addView(mPbLoadMore);

    }

    /**
     * 切换状态
     * @param state Pass {@link #STATE_LOADING} or {@link #STATE_COMPLETE} or {@link #STATE_NOMORE}
     */
    public void setState(int state) {
        switch (state) {
            case STATE_LOADING:
                mPbLoadMore.setVisibility(View.VISIBLE);
                break;
            case STATE_COMPLETE:
                mPbLoadMore.setVisibility(View.VISIBLE);
                break;
            case STATE_NOMORE:
                mPbLoadMore.setVisibility(View.GONE);
                break;
        }
    }

    private int dp2px(float dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

}
