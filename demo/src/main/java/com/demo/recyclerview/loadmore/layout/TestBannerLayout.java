package com.demo.recyclerview.loadmore.layout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.demo.recyclerview.utils.ScreenUtil;

/**
 * @date 2021/1/23
 */
public class TestBannerLayout extends LinearLayout {

    public TestBannerLayout(Context context) {
        super(context);
        addContentView();
    }

    public TestBannerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestBannerLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TestBannerLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void addContentView() {
        TextView textView = new TextView(getContext());
        textView.setText("Banner");
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.RED);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ScreenUtil.dp2px(120));
        textView.setLayoutParams(lp);
        addView(textView);
    }

    public void setData() {

    }
}
