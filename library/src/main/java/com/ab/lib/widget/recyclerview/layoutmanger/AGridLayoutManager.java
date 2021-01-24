package com.ab.lib.widget.recyclerview.layoutmanger;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author wupuquan
 * @version 1.0
 * @since 2018/9/8 15:20
 */
public class AGridLayoutManager extends GridLayoutManager {

    public AGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public AGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public AGridLayoutManager(Context context, int spanCount, @RecyclerView.Orientation int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            // bug: https://www.jianshu.com/p/8f4afac1cd12
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            Log.e("GridLayoutManager", e.getMessage());
        }
    }
}
