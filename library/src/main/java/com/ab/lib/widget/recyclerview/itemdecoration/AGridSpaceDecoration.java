package com.ab.lib.widget.recyclerview.itemdecoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 空白分割线 for {@link androidx.recyclerview.widget.GridLayoutManager}
 *
 * @date 2021/1/24
 */
public class AGridSpaceDecoration extends ABaseSpaceDecoration {

    private static final String TAG = AGridSpaceDecoration.class.getSimpleName();
    private static final int SPAN_COUNT_INIT = -1;

    private final int horizontalSpace;
    private final int verticalSpace;
    private int spanCount = SPAN_COUNT_INIT;

    /**
     * @param orientation {@link RecyclerView.Orientation}
     * @param space       item间距
     */
    public AGridSpaceDecoration(@RecyclerView.Orientation int orientation, int space) {
        this(orientation, false, space);
    }

    /**
     * @param orientation     {@link RecyclerView.Orientation}
     * @param horizontalSpace 横间距
     * @param verticalSpace   纵间距
     */
    public AGridSpaceDecoration(@RecyclerView.Orientation int orientation, int horizontalSpace, int verticalSpace) {
        this(orientation, false, horizontalSpace, verticalSpace);
    }

    /**
     * @param orientation {@link RecyclerView.Orientation}
     * @param includeEdge 两边是否需要画分割线
     * @param space       item间距
     */
    public AGridSpaceDecoration(@RecyclerView.Orientation int orientation, boolean includeEdge, int space) {
        this(orientation, includeEdge, space, space);
    }

    /**
     * @param orientation     {@link RecyclerView.Orientation}
     * @param includeEdge     两边是否需要画分割线
     * @param horizontalSpace 横间距
     * @param verticalSpace   纵间距
     */
    public AGridSpaceDecoration(@RecyclerView.Orientation int orientation, boolean includeEdge, int horizontalSpace, int verticalSpace) {
        super(orientation, includeEdge);
        this.horizontalSpace = horizontalSpace;
        this.verticalSpace = verticalSpace;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int orientation, boolean includeEdge) {
        GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
        if (spanCount == SPAN_COUNT_INIT) {
            spanCount = gridLayoutManager.getSpanCount();
        }
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        // 宽度是否撑满RecyclerView
        boolean isFullSpan = lp.getSpanSize() == spanCount;
//        if (lp.isFullSpan()) {
//            return;
//        }
        if (orientation == RecyclerView.VERTICAL) {
            if (!isFullSpan) {
                int spanIndex = lp.getSpanIndex();
//              Log.d(TAG, "spanIndex=" + spanIndex);
                if (includeEdge) {
                    outRect.left = horizontalSpace - spanIndex * horizontalSpace / spanCount;
                    outRect.right = (spanIndex + 1) * horizontalSpace / spanCount;
                } else {
                    outRect.left = spanIndex * horizontalSpace / spanCount;
                    outRect.right = horizontalSpace - (spanIndex + 1) * horizontalSpace / spanCount;
                }
            }

            int position = lp.getViewLayoutPosition();
            boolean isLastItem = position == (state.getItemCount() - 1);
//          Log.d(TAG, "position=" + position + ", itemCount=" + ", "+ state.getItemCount());
            // 最后一个item可能是加载更多，底部也加上space
            if (!isFullSpan || isLastItem) {
                outRect.bottom = verticalSpace;
            }
        } else {
            if (!isFullSpan) {
                int spanIndex = lp.getSpanIndex();
//              Log.d(TAG, "spanIndex=" + spanIndex);
                if (includeEdge) {
                    outRect.top = verticalSpace - spanIndex * verticalSpace / spanCount;
                    outRect.bottom = (spanIndex + 1) * verticalSpace / spanCount;
                } else {
                    outRect.top = spanIndex * verticalSpace / spanCount;
                    outRect.bottom = verticalSpace - (spanIndex + 1) * verticalSpace / spanCount;
                }
            }

            int position = lp.getViewLayoutPosition();
            boolean isLastItem = position == (state.getItemCount() - 1);
//          Log.d(TAG, "position=" + position + ", itemCount=" + ", "+ state.getItemCount());
            // 最后一个item可能是加载更多，底部也加上space
            if (!isFullSpan || isLastItem) {
                outRect.right = horizontalSpace;
            }
        }
    }
}
