package com.ab.lib.widget.recyclerview.layoutmanger;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fondesa.recyclerviewdivider.RecyclerViewDivider;

/**
 * @author wupuquan
 * @version 1.0
 * @since 2018/9/8 15:07
 */
public class ALinearLayoutManager extends LinearLayoutManager {

    public ALinearLayoutManager(Context context) {
        super(context);
    }

    public ALinearLayoutManager(Context context, @RecyclerView.Orientation int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public ALinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            // bug: https://www.jianshu.com/p/8f4afac1cd12
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            Log.e("LinearLayoutManager", e.getMessage());
        }
    }

}
