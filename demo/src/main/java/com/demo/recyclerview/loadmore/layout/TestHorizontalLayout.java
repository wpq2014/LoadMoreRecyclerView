package com.demo.recyclerview.loadmore.layout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.ab.lib.widget.recyclerview.adapter.ABaseAdapter;
import com.ab.lib.widget.recyclerview.ABaseViewHolder;
import com.ab.lib.widget.recyclerview.LoadMoreRecyclerView;
import com.ab.lib.widget.recyclerview.itemdecoration.ALinearSpaceDecoration;
import com.ab.lib.widget.recyclerview.layoutmanger.ALinearLayoutManager;
import com.demo.recyclerview.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 2021/1/23
 */
public class TestHorizontalLayout extends LoadMoreRecyclerView {

    public TestHorizontalLayout(Context context) {
        super(context);
        addContentView();
    }

    public TestHorizontalLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestHorizontalLayout(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void addContentView() {
        ALinearLayoutManager layoutManager = new ALinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        setLayoutManager(layoutManager);
        ALinearSpaceDecoration spaceDecoration = new ALinearSpaceDecoration(layoutManager.getOrientation(), ScreenUtil.dp2px(10));
        setItemDecoration(spaceDecoration);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("横向列表" + i);
        }
        HorizontalAdapter horizontalAdapter = new HorizontalAdapter(list);
        setAdapter(horizontalAdapter);
        horizontalAdapter.setOnItemClickListener(new ABaseAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(@NonNull ABaseViewHolder viewHolder, int position, @NonNull String itemData) {
                Toast.makeText(getContext(), itemData, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setData() {

    }

    private static class HorizontalAdapter extends ABaseAdapter<String> {

        public HorizontalAdapter(List<String> list) {
            super(list);
        }

        @Override
        protected ABaseViewHolder onCreate(@NonNull ViewGroup parent) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ScreenUtil.dp2px(80), ScreenUtil.dp2px(96));
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
