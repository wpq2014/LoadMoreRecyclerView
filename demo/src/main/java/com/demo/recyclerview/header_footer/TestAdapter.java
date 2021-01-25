package com.demo.recyclerview.header_footer;

import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ab.lib.widget.recyclerview.adapter.ABaseAdapter;
import com.ab.lib.widget.recyclerview.ABaseViewHolder;

import java.util.List;

/**
 * @author wupuquan
 * @version 1.0
 * @since 2018/8/3 18:08
 */
public class TestAdapter extends ABaseAdapter<String> {

    public TestAdapter(List<String> list) {
        super(list);
    }

    @Override
    protected ABaseViewHolder onCreate(@NonNull ViewGroup parent) {
        TextView textView = new TextView(parent.getContext());
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 140));
        return new ABaseViewHolder(textView);
    }

    @Override
    protected void onBind(@NonNull ABaseViewHolder viewHolder, int position, @NonNull String itemData) {
        TextView textView = (TextView) viewHolder.itemView;
        textView.setText(itemData);
    }
}
