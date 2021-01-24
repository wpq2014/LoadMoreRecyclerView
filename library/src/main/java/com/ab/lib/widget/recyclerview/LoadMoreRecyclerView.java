package com.ab.lib.widget.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * refs: https://github.com/XRecyclerView/XRecyclerView
 * <p>
 * 只封装加载更多，下拉刷新可配合 {@link SwipeRefreshLayout}
 * 和 https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh
 * <p>
 *
 * @author wpq
 * @version 1.0
 */
public class LoadMoreRecyclerView extends RecyclerView {

    private static final String TAG = LoadMoreRecyclerView.class.getSimpleName();

    /**
     * headers viewType，取值较大，避免跟数据区域的viewType重复，如有重复则需调整
     */
    private static final int VIEW_TYPE_HEADER_INIT = 100001;
    /**
     * footers viewType
     */
    private static final int VIEW_TYPE_FOOTER_INIT = 200001;
    /**
     * LoadMore viewType
     */
    private static final int VIEW_TYPE_LOAD_MORE = 200000;
    // 默认加载更多
    private static LoadMoreLayoutCreator sLoadMoreLayoutCreator;
    private final SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private final SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();
    private LoadMoreView mLoadMoreView;
    private boolean loadMoreEnabled = false;
    private WrapAdapter mWrapAdapter;
    private final DataObserver mAdapterDataObserver = new DataObserver();
    private OnLoadListener mOnLoadListener;
    /**
     * 是否正在执行网络请求，切换标记位保证滚动到底部时不会频繁触发网络请求
     */
    private boolean isLoading = false;
    // 没有更多了
    private boolean noMore = false;
    // 是否显示没有更多的布局
    private boolean showNoMoreView = false;
    // 自动加载更多(快滚动到底部时就加载)
    private boolean autoLoadMore;
    // 自动加载更多触发机制，滑到倒数某个位置触发自动加载更多
    private int autoLoadLimit = 6;

    public LoadMoreRecyclerView(Context context) {
        this(context, null);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public static void setDefaultLoadMoreLayoutCreator(LoadMoreLayoutCreator loadMoreLayoutCreator) {
        sLoadMoreLayoutCreator = loadMoreLayoutCreator;
    }

    public void addHeaderView(@NonNull View headerView) {
        synchronized (mHeaderViews) {
            mHeaderViews.put(VIEW_TYPE_HEADER_INIT + mHeaderViews.size(), headerView);
            if (mWrapAdapter != null && mWrapAdapter.getInnerAdapter() != null) {
                try {
                    mWrapAdapter.getInnerAdapter().notifyItemInserted(mHeaderViews.size() - 1);
                } catch (Exception e) {
                    Log.e(TAG, "addHeaderView notifyItemInserted exception: " + e.getMessage());
                    mWrapAdapter.getInnerAdapter().notifyDataSetChanged();
                }
            }
        }
    }

    public void removeHeaderView(@NonNull View headerView) {
        synchronized (mHeaderViews) {
            for (int i = 0; i < mHeaderViews.size(); i++) {
                if (headerView.equals(mHeaderViews.valueAt(i))) {
                    mHeaderViews.removeAt(i);
                    if (mWrapAdapter != null && mWrapAdapter.getInnerAdapter() != null) {
                        try {
                            mWrapAdapter.getInnerAdapter().notifyItemRemoved(i);
                        } catch (Exception e) {
                            Log.e(TAG, "removeHeaderView notifyItemRemoved exception: " + e.getMessage());
                            mWrapAdapter.getInnerAdapter().notifyDataSetChanged();
                        }
                    }
                    break;
                }
            }
        }
    }

    public void addFooterView(@NonNull View footerView) {
        // 如果加载更多为true，则不能有FooterView
        if (loadMoreEnabled) {
            return;
        }
        synchronized (mFooterViews) {
            mFooterViews.put(VIEW_TYPE_FOOTER_INIT + mFooterViews.size(), footerView);
            if (mWrapAdapter != null && mWrapAdapter.getInnerAdapter() != null) {
                try {
                    mWrapAdapter.getInnerAdapter().notifyItemInserted(mWrapAdapter.getItemCount() - 1);
                } catch (Exception e) {
                    Log.e(TAG, "addFooterView notifyItemInserted exception: " + e.getMessage());
                    mWrapAdapter.getInnerAdapter().notifyDataSetChanged();
                }
            }
        }
    }

    public void removeFooterView(@NonNull View footerView) {
        synchronized (mFooterViews) {
            for (int i = 0; i < mFooterViews.size(); i++) {
                if (footerView.equals(mFooterViews.valueAt(i))) {
                    mFooterViews.removeAt(i);
                    if (mWrapAdapter != null && mWrapAdapter.getInnerAdapter() != null) {
                        try {
                            mWrapAdapter.getInnerAdapter().notifyItemRemoved(getHeadersCount() + mWrapAdapter.getInnerItemCount() + i);
                        } catch (Exception e) {
                            Log.e(TAG, "removeFooterView notifyItemRemoved exception: " + e.getMessage());
                            mWrapAdapter.getInnerAdapter().notifyDataSetChanged();
                        }
                    }
                    break;
                }
            }
        }
    }

    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFooterViews.size();
    }

    /**
     * 必须在 {@link #addFooterView(View)} 之前调用才生效
     *
     * @param loadMoreEnabled 是否加载更多
     */
    public void setLoadMoreEnabled(boolean loadMoreEnabled) {
        // 如果FooterView不为空，则不能有加载更多
        if (getFootersCount() > 0) {
            return;
        }
        this.loadMoreEnabled = loadMoreEnabled;
        this.showNoMoreView = loadMoreEnabled;
        if (this.loadMoreEnabled) {
            mLoadMoreView = new LoadMoreView(getContext(), new LoadMoreView.OnClickListener() {
                @Override
                public void onLoadMoreViewClick() {
                    doLoadMore();
                }
            });
            // 默认配置
            if (sLoadMoreLayoutCreator != null) {
                LoadMoreLayout loadMoreLayout = sLoadMoreLayoutCreator.createLoadMoreLayout(getContext());
                if (loadMoreLayout == null) {
                    return;
                }
                if (loadMoreLayout.getLoadingView() != null) {
                    mLoadMoreView.setLoadingView(loadMoreLayout.getLoadingView());
                }
                if (loadMoreLayout.getCompleteView() != null) {
                    mLoadMoreView.setCompleteView(loadMoreLayout.getCompleteView());
                }
                if (loadMoreLayout.getErrorView() != null) {
                    mLoadMoreView.setErrorView(loadMoreLayout.getErrorView());
                }
                if (loadMoreLayout.getNoMoreView() != null) {
                    mLoadMoreView.setNoMoreView(loadMoreLayout.getNoMoreView());
                }
            }
        }
    }

    /**
     * 自动加载更多，快滚动到底部时就加载
     *
     * @param autoLoadMore  是否预加载
     * @param autoLoadLimit 自动加载更多触发机制，滑到倒数某个位置触发加载更多
     */
    public void setAutoLoadMore(boolean autoLoadMore, int autoLoadLimit) {
        this.autoLoadMore = autoLoadMore;
        this.autoLoadLimit = autoLoadLimit;
    }

    public void setLoadingView(View loadingView) {
        if (mLoadMoreView != null && loadingView != null) {
            mLoadMoreView.setLoadingView(loadingView);
        }
    }

    public void setCompleteView(View completeView) {
        if (mLoadMoreView != null && completeView != null) {
            mLoadMoreView.setCompleteView(completeView);
        }
    }

    public void setErrorView(View errorView) {
        if (mLoadMoreView != null && errorView != null) {
            mLoadMoreView.setErrorView(errorView);
        }
    }

    public void setNoMoreView(View noMoreView) {
        if (mLoadMoreView != null && noMoreView != null) {
            mLoadMoreView.setNoMoreView(noMoreView);
        }
    }

    public void doLoadMore() {
        isLoading = true;
        noMore = false;
        showNoMoreView = true;
        if (mLoadMoreView != null) {
            mLoadMoreView.setState(LoadMoreView.State.LOADING);
        }
        if (mOnLoadListener != null) {
            mOnLoadListener.onLoadMore();
        }
    }

    public void loadMoreComplete() {
        isLoading = false;
        noMore = false;
        showNoMoreView = true;
        if (mLoadMoreView != null) {
            mLoadMoreView.setState(LoadMoreView.State.COMPLETE);
        }
    }

    public void loadMoreError() {
        isLoading = false;
        noMore = false;
        showNoMoreView = true;
        if (mLoadMoreView != null) {
            mLoadMoreView.setState(LoadMoreView.State.ERROR);
        }
    }

    /**
     * 没有更多了
     *
     * @param showNoMoreView 是否显示没有更多的布局
     */
    public void noMore(boolean showNoMoreView) {
        isLoading = false;
        noMore = true;
        this.showNoMoreView = showNoMoreView;
        if (this.showNoMoreView && mLoadMoreView != null) {
            mLoadMoreView.setState(LoadMoreView.State.NO_MORE);
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Adapter getAdapter() {
        if (mWrapAdapter != null) {
            return mWrapAdapter.getInnerAdapter();
        }
        return super.getAdapter();
    }

    @SuppressWarnings("EmptyCatchBlock")
    @Override
    public void setAdapter(Adapter adapter) {
        mWrapAdapter = new WrapAdapter(adapter);
        super.setAdapter(mWrapAdapter);
        try {
            adapter.unregisterAdapterDataObserver(mAdapterDataObserver);
        } catch (Exception e) {
            Log.e(TAG, "unregisterAdapterDataObserver exception: " + e.getMessage());
        }
        try {
            adapter.registerAdapterDataObserver(mAdapterDataObserver);
        } catch (Exception e) {
            Log.e(TAG, "registerAdapterDataObserver exception: " + e.getMessage());
        }
        mAdapterDataObserver.onChanged();
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        /*  state：
            SCROLL_STATE_IDLE     = 0 ：静止,没有滚动
            SCROLL_STATE_DRAGGING = 1 ：正在被外部拖拽,一般为用户正在用手指滚动
            SCROLL_STATE_SETTLING = 2 ：自动滚动开始
         */

        /*
            RecyclerView.canScrollVertically(1)的值表示是否能向上滚动，false表示已经滚动到底部
            RecyclerView.canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
         */

//        Log.e(TAG, state + ", " + this.canScrollVertically(1));
        // 判断RecyclerView滚动到底部，参考：http://www.jianshu.com/p/c138055af5d2
        if (state == RecyclerView.SCROLL_STATE_IDLE && !this.canScrollVertically(1) && loadMoreEnabled && !noMore && !isLoading) {
            doLoadMore();
        }
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if (!autoLoadMore) {
            return;
        }
        int endCompletelyPosition = -1;
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            endCompletelyPosition = ((StaggeredGridLayoutManager) layoutManager)
                    .findLastCompletelyVisibleItemPositions(null)[((StaggeredGridLayoutManager) layoutManager).getSpanCount() - 1];
        } else if (layoutManager instanceof GridLayoutManager) { // GridLayoutManager继承自LinearLayoutManager
            endCompletelyPosition = ((GridLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            endCompletelyPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        }
        // 提前加载更多
        if (loadMoreEnabled && !noMore && !isLoading && endCompletelyPosition == mWrapAdapter.getItemCount() - autoLoadLimit) {
            doLoadMore();
        }
    }

    public boolean isHeader(int position) {
        return position < getHeadersCount();
    }

    public boolean isFooter(int position) {
        return getFootersCount() > 0 && position >= getHeadersCount() + mWrapAdapter.getInnerItemCount();
    }

    public boolean isLoadMore(int position) {
        // 如果加载更多 && 是最后一项，就是LoadMore
        return loadMoreEnabled && position == mWrapAdapter.getItemCount() - 1 && showNoMoreView;
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        // 解决自动滚动问题 http://jcodecraeer.com/a/anzhuokaifa/androidkaifa/2018/0130/9270.html
    }

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.mOnLoadListener = onLoadListener;
    }

    public interface OnLoadListener {
        void onLoadMore();
    }

    /**
     * wrap header、footer、loadMore
     */
    @SuppressWarnings("rawtypes")
    private class WrapAdapter extends Adapter<ViewHolder> {

        private final Adapter mInnerAdapter;

        public WrapAdapter(Adapter innerAdapter) {
            this.mInnerAdapter = innerAdapter;
        }

        public Adapter getInnerAdapter() {
            return mInnerAdapter;
        }

        public int getInnerItemCount() {
            return mInnerAdapter.getItemCount();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (mHeaderViews.get(viewType) != null) {
                return new ABaseViewHolder(mHeaderViews.get(viewType));
            }
            if (mFooterViews.get(viewType) != null) {
                return new ABaseViewHolder(mFooterViews.get(viewType));
            }
            if (viewType == VIEW_TYPE_LOAD_MORE) {
                return new ABaseViewHolder(mLoadMoreView);
            }
            return mInnerAdapter.onCreateViewHolder(parent, viewType);
        }

        @SuppressWarnings("unchecked")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (isHeader(position) || isFooter(position) || isLoadMore(position)) {
                return;
            }
            int adjPosition = position - getHeadersCount();
            if (mInnerAdapter != null) {
                int adapterCount = mInnerAdapter.getItemCount();
                if (adjPosition < adapterCount) {
                    mInnerAdapter.onBindViewHolder(holder, adjPosition);
                }
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
            if (isHeader(position) || isFooter(position) || isLoadMore(position)) {
                return;
            }
            int adjPosition = position - getHeadersCount();
            if (mInnerAdapter != null) {
                int adapterCount = mInnerAdapter.getItemCount();
                if (adjPosition < adapterCount) {
                    if (payloads.isEmpty()) {
                        mInnerAdapter.onBindViewHolder(holder, adjPosition);
                    } else {
                        mInnerAdapter.onBindViewHolder(holder, adjPosition, payloads);
                    }
                }
            }
        }

        @Override
        public int getItemCount() {
            if (loadMoreEnabled) {
                // 如果是加载更多，相当于加载更多布局是一个FooterView
                return getHeadersCount() + getInnerItemCount() + (showNoMoreView ? 1 : 0);
            } else {
                // 如果不是加载更多，那就可能有多个FooterView
                return getHeadersCount() + getInnerItemCount() + getFootersCount();
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (isHeader(position)) {
                return mHeaderViews.keyAt(position);
            }
            if (isFooter(position)) {
                return mFooterViews.keyAt(position - getHeadersCount() - getInnerItemCount());
            }
            if (isLoadMore(position)) {
                return VIEW_TYPE_LOAD_MORE;
            }
            return mInnerAdapter.getItemViewType(position - getHeadersCount());
        }

        @Override
        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                final int spanCount = gridLayoutManager.getSpanCount();
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return isHeader(position) || isFooter(position) || isLoadMore(position) ? spanCount : 1;
                    }
                });
            }
            mInnerAdapter.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            int position = holder.getLayoutPosition();
            boolean isHeaderFooterOrLoadMore = isHeader(position) || isFooter(position) || isLoadMore(position);
            if (isHeaderFooterOrLoadMore) {
                ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
                if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                    StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                    p.setFullSpan(true);
                }
                return;
            }
            //noinspection unchecked
            mInnerAdapter.onViewAttachedToWindow(holder);
        }

        @Override
        public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
            mInnerAdapter.onDetachedFromRecyclerView(recyclerView);
        }

        @Override
        public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
            //noinspection unchecked
            mInnerAdapter.onViewDetachedFromWindow(holder);
        }

        @Override
        public void onViewRecycled(@NonNull ViewHolder holder) {
            //noinspection unchecked
            mInnerAdapter.onViewRecycled(holder);
        }

        @Override
        public boolean onFailedToRecycleView(@NonNull ViewHolder holder) {
            //noinspection unchecked
            return mInnerAdapter.onFailedToRecycleView(holder);
        }

        @Override
        public void unregisterAdapterDataObserver(@NonNull AdapterDataObserver observer) {
            mInnerAdapter.unregisterAdapterDataObserver(observer);
        }

        @Override
        public void registerAdapterDataObserver(@NonNull AdapterDataObserver observer) {
            mInnerAdapter.registerAdapterDataObserver(observer);
        }
    }

    private class DataObserver extends AdapterDataObserver {
        @Override
        public void onChanged() {
            if (mWrapAdapter != null) {
                mWrapAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (mWrapAdapter != null) {
                mWrapAdapter.notifyItemRangeInserted(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            if (mWrapAdapter != null) {
                mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            if (mWrapAdapter != null) {
                mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
            }
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (mWrapAdapter != null) {
                mWrapAdapter.notifyItemRangeRemoved(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            if (mWrapAdapter != null) {
                mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
            }
        }
    }

    @Override
    public void setLayoutManager(@Nullable LayoutManager layout) {
        super.setLayoutManager(layout);
        if (layout == null) {
            return;
        }
        if (layout instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layout;
            // 解决滚动时item跳动、左右切换的问题
            staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
            // 滑到底部的时候，最后一个item等一小会才出来，这是RecyclerView使用瀑布流布局时自带的动画效果，去掉这个等待
            this.setItemAnimator(null);
        }
    }

    public void clearItemDecoration() {
        try {
            for (int i = 0; i < getItemDecorationCount(); i++) {
                removeItemDecorationAt(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setItemDecoration(RecyclerView.ItemDecoration decor) {
        if (decor == null) return;
        try {
            for (int i = 0; i < getItemDecorationCount(); i++) {
                removeItemDecorationAt(i);
            }
            addItemDecoration(decor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解决notify问题 https://www.jianshu.com/p/a49e407474bb
     */
    public void notifyItemRemoved(int position, int totalCount) {
        notifyItemRangeRemoved(position, 1, totalCount);
    }

    public void notifyItemRangeRemoved(int positionStart, int removeCount, int totalCount) {
        if (mWrapAdapter.getInnerAdapter() == null) {
            return;
        }
        int headerCount = getHeadersCount();
        int adjustPosition = headerCount + positionStart;
        int adjustTotalCount = headerCount + totalCount;
        mWrapAdapter.getInnerAdapter().notifyItemRangeRemoved(adjustPosition, removeCount);
        mWrapAdapter.getInnerAdapter().notifyItemRangeChanged(adjustPosition, adjustTotalCount, new Object());
    }

    public void notifyItemInserted(int position, int totalCount) {
        notifyItemRangeInserted(position, 1, totalCount);
    }

    public void notifyItemRangeInserted(int positionStart, int insertCount, int totalCount) {
        if (mWrapAdapter.getInnerAdapter() == null) {
            return;
        }
        int headerCount = getHeadersCount();
        int adjustPosition = headerCount + positionStart;
        int adjustTotalCount = headerCount + totalCount;
        mWrapAdapter.getInnerAdapter().notifyItemRangeInserted(adjustPosition, insertCount);
        // 加上下面这行，如果不加可以试试快速调用notifyItemInserted(1)，点击Toast提示position会发现动画没执行完时后面新增的position也是1
        mWrapAdapter.getInnerAdapter().notifyItemRangeChanged(adjustPosition, adjustTotalCount, new Object());
    }

    public void notifyItemChanged(int position) {
        notifyItemChanged(position, null);
    }

    public void notifyItemChanged(int position, Object o) {
        notifyItemChanged(position, 1, o);
    }

    public void notifyItemChanged(int positionStart, int changeCount) {
        notifyItemChanged(positionStart, changeCount, null);
    }

    public void notifyItemChanged(int positionStart, int changeCount, Object o) {
        if (mWrapAdapter.getInnerAdapter() == null)
            return;
        int headerCount = getHeadersCount();
        int adjustPosition = headerCount + positionStart;
        mWrapAdapter.getInnerAdapter().notifyItemRangeChanged(adjustPosition, changeCount, o);
    }
}
