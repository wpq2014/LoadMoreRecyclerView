package com.ab.lib.widget.recyclerview.itemdecoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 空白分割线
 *
 * @date 2021/1/24
 */
public abstract class ABaseSpaceDecoration extends RecyclerView.ItemDecoration {

    @RecyclerView.Orientation
    private final int orientation;

    // 两边是否需要画分割线
    private final boolean includeEdge;

    /**
     * @param orientation {@link RecyclerView.Orientation}
     * @param includeEdge 两边是否需要画分割线
     */
    public ABaseSpaceDecoration(@RecyclerView.Orientation int orientation, boolean includeEdge) {
        this.orientation = orientation;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
        getItemOffsets(outRect, view, parent, state, orientation, includeEdge);
    }

    /**
     * @param orientation {@link RecyclerView.Orientation}
     * @param includeEdge 两边是否需要画分割线
     */
    public abstract void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state,
                                        @RecyclerView.Orientation int orientation, boolean includeEdge);
}
