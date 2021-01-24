package com.demo.recyclerview.loadmore.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ab.lib.widget.recyclerview.ABaseTypeAdapter;
import com.ab.lib.widget.recyclerview.ABaseViewHolder;
import com.demo.recyclerview.loadmore.TestItemType;
import com.demo.recyclerview.loadmore.bean.BaseTestTypeBean;
import com.demo.recyclerview.loadmore.bean.TestListBean;
import com.demo.recyclerview.loadmore.layout.TestBannerLayout;
import com.demo.recyclerview.loadmore.layout.TestHorizontalLayout;
import com.demo.recyclerview.loadmore.layout.TestListLayout;
import com.demo.recyclerview.loadmore.layout.TestTabLayout;
import com.demo.recyclerview.utils.ScreenUtil;

import java.util.List;

/**
 * @date 2021/1/23
 */
public class GridAdapter extends ABaseTypeAdapter<BaseTestTypeBean> {

    public GridAdapter(List<BaseTestTypeBean> list) {
        super(list);
    }

    @Override
    protected ABaseViewHolder onCreate(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;
        switch (viewType) {
            case TestItemType.TYPE_BANNER: {
                view = new TestBannerLayout(context);
                break;
            }
            case TestItemType.TYPE_TAB: {
                view = new TestTabLayout(context);
                break;
            }
            case TestItemType.TYPE_HEADER: {
                view = new TestHorizontalLayout(context);
                break;
            }
            case TestItemType.TYPE_LIST:
            default: {
                view = new TestListLayout(context);
                break;
            }
        }
        if (view != null) {
            return new ABaseViewHolder(view);
        }
        return null;
    }

    @Override
    protected void onBind(@NonNull ABaseViewHolder viewHolder, int position, @NonNull BaseTestTypeBean itemData) {
        View itemView = viewHolder.itemView;
        switch (itemData.getItemType()) {
            case TestItemType.TYPE_LIST: {
                TestListBean listBean = (TestListBean) itemData;
                ((TestListLayout) itemView).setData(position, ScreenUtil.dp2px(112));
                break;
            }
        }
    }

    @Override
    protected int getViewType(int position) {
        BaseTestTypeBean testTypeBean = getItem(position);
        if (testTypeBean != null) {
            return testTypeBean.getItemType();
        }
        return getItemViewType(position);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            int spanCount = gridLayoutManager.getSpanCount();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getViewType(position);
                    switch (viewType) {
                        case TestItemType.TYPE_BANNER:
                        case TestItemType.TYPE_TAB:
                        case TestItemType.TYPE_HEADER: {
                            return spanCount;
                        }
                        case TestItemType.TYPE_LIST: {
                            return 1;
                        }
                    }
                    return spanCount;
                }
            });
        }
    }
}
