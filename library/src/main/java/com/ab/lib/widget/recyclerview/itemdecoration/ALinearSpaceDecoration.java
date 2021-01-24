package com.ab.lib.widget.recyclerview.itemdecoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 空白分割线 for {@link androidx.recyclerview.widget.LinearLayoutManager}
 *
 * @date 2021/1/24
 */
public class ALinearSpaceDecoration extends ABaseSpaceDecoration {

    private static final String TAG = ALinearSpaceDecoration.class.getSimpleName();
    private final int space;

    /**
     * @param orientation {@link RecyclerView.Orientation}
     * @param space       item间距
     */
    public ALinearSpaceDecoration(@RecyclerView.Orientation int orientation, int space) {
        this(orientation, false, space);
    }

    /**
     * @param orientation {@link RecyclerView.Orientation}
     * @param includeEdge 两边是否需要画分割线
     * @param space       item间距
     */
    public ALinearSpaceDecoration(@RecyclerView.Orientation int orientation, boolean includeEdge, int space) {
        super(orientation, includeEdge);
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int orientation, boolean includeEdge) {
        if (orientation == RecyclerView.VERTICAL) {
            if (includeEdge) {
                outRect.left = space;
                outRect.right = space;
            }
            outRect.bottom = space;
        } else {
            if (includeEdge) {
                outRect.top = space;
                outRect.bottom = space;
            }
            outRect.right = space;
        }
    }
}
