package com.ab.lib.widget.recyclerview.layoutmanger;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author wupuquan
 * @version 1.0
 * @since 2018/9/8 15:46
 */
public class AFlexboxLayoutManager extends FlexboxLayoutManager {

    public AFlexboxLayoutManager(Context context) {
        super(context);
    }

    public AFlexboxLayoutManager(Context context, @FlexDirection int flexDirection) {
        super(context, flexDirection);
    }

    public AFlexboxLayoutManager(Context context, @FlexDirection int flexDirection, @FlexWrap int flexWrap) {
        super(context, flexDirection, flexWrap);
    }

    public AFlexboxLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            // bug: https://www.jianshu.com/p/8f4afac1cd12
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            Log.e("FlexboxLayoutManager", e.getMessage());
        }
    }
}
