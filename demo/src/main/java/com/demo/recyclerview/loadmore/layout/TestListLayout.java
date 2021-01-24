package com.demo.recyclerview.loadmore.layout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.demo.recyclerview.utils.ScreenUtil;

/**
 * @date 2021/1/23
 */
public class TestListLayout extends LinearLayout {

    public TestListLayout(Context context) {
        super(context);
        addContentView();
    }

    public TestListLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestListLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TestListLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private TextView textView;

    private void addContentView() {
        textView = new TextView(getContext());
        textView.setText("List");
        textView.setMinHeight(ScreenUtil.dp2px(64));
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.GRAY);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(lp);
        addView(textView);
//        int padding = ScreenUtil.dp2px(5);
//        setPadding(padding, padding, padding, padding);
    }

    public void setData(int index) {
        textView.setText("List" + index);
    }

    public void setData(int index, int height) {
        textView.setText("List" + index);
        LinearLayout.LayoutParams lp = (LayoutParams) textView.getLayoutParams();
        lp.height = height;
    }
}
