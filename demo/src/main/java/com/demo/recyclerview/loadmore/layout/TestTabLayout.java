package com.demo.recyclerview.loadmore.layout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ab.lib.widget.recyclerview.ABaseAdapter;
import com.ab.lib.widget.recyclerview.ABaseViewHolder;
import com.ab.lib.widget.recyclerview.LoadMoreRecyclerView;
import com.ab.lib.widget.recyclerview.itemdecoration.AGridSpaceDecoration;
import com.ab.lib.widget.recyclerview.layoutmanger.AGridLayoutManager;
import com.demo.recyclerview.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 2021/1/23
 */
public class TestTabLayout extends LoadMoreRecyclerView {

    public TestTabLayout(@NonNull Context context) {
        super(context);
        addContentView();
    }

    public TestTabLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestTabLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void addContentView() {
        setNestedScrollingEnabled(false); // 嵌套在NestedScrollView或RecycleView中时取消自身滑动
        AGridLayoutManager layoutManager = new AGridLayoutManager(getContext(), 3);
        setLayoutManager(layoutManager);
        // 不要底部分割线
        AGridSpaceDecoration spaceDecoration = new AGridSpaceDecoration(layoutManager.getOrientation(), ScreenUtil.dp2px(10), 0);
        setItemDecoration(spaceDecoration);
        List<String> list = new ArrayList<String>() {{
            add("Tab1");
            add("Tab2");
            add("Tab3");
        }};
        Tabdapter tabdapter = new Tabdapter(list);
        setAdapter(tabdapter);
    }

    public void setData() {

    }

    private static class Tabdapter extends ABaseAdapter<String> {

        public Tabdapter(List<String> list) {
            super(list);
        }

        @Override
        protected ABaseViewHolder onCreate(@NonNull ViewGroup parent) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.dp2px(80));
            TextView textView = new TextView(parent.getContext());
            textView.setLayoutParams(lp);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundColor(Color.GREEN);
            return new ABaseViewHolder(textView);
        }

        @Override
        protected void onBind(@NonNull ABaseViewHolder viewHolder, int position, @NonNull String itemData) {
            TextView textView = (TextView) viewHolder.itemView;
            textView.setText(itemData);
        }
    }
}
