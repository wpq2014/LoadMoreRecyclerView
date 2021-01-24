package com.demo.recyclerview.widgets;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ab.lib.widget.recyclerview.LoadMoreLayout;
import com.demo.recyclerview.R;
import com.demo.recyclerview.utils.ScreenUtil;

/**
 * @date 2021/1/24
 */
public class DefaultLoadMore implements LoadMoreLayout {

    private Context mContext;

    public DefaultLoadMore(Context context) {
        this.mContext = context;
    }

    @Override
    public View getLoadingView() {
        return null;
    }

    @Override
    public View getCompleteView() {
        return null;
    }

    @Override
    public View getErrorView() {
        return null;
    }

    @Override
    public View getNoMoreView() {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(0, ScreenUtil.dp2px(10), 0, ScreenUtil.dp2px(10));
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(lp);

        ImageView imageView = new ImageView(mContext);
        LinearLayout.LayoutParams imageLp = new LinearLayout.LayoutParams(ScreenUtil.dp2px(30), ScreenUtil.dp2px(30));
        imageView.setLayoutParams(imageLp);
        imageView.setImageResource(R.mipmap.ic_launcher);
        linearLayout.addView(imageView);

        TextView textView = new TextView(mContext);
        textView.setText("一 木有更多啦 一");
        textView.setGravity(Gravity.CENTER);
        linearLayout.addView(textView);

        return linearLayout;
    }
}
