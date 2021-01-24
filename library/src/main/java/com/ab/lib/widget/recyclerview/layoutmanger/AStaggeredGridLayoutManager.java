package com.ab.lib.widget.recyclerview.layoutmanger;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @author wupuquan
 * @version 1.0
 * @since 2018/9/8 15:42
 */
public class AStaggeredGridLayoutManager extends StaggeredGridLayoutManager {

    public AStaggeredGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public AStaggeredGridLayoutManager(int spanCount, @RecyclerView.Orientation int orientation) {
        super(spanCount, orientation);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            // bug: https://www.jianshu.com/p/8f4afac1cd12
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            Log.e("StaggeredGridManager", e.getMessage());
        }
    }
}
