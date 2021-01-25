package com.demo.recyclerview.loadmore.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.ab.lib.widget.recyclerview.adapter.ABaseTypeAdapter;
import com.ab.lib.widget.recyclerview.ABaseViewHolder;
import com.demo.recyclerview.loadmore.TestItemType;
import com.demo.recyclerview.loadmore.bean.BaseTestTypeBean;
import com.demo.recyclerview.loadmore.bean.TestListBean;
import com.demo.recyclerview.loadmore.layout.TestBannerLayout;
import com.demo.recyclerview.loadmore.layout.TestHorizontalLayout;
import com.demo.recyclerview.loadmore.layout.TestListLayout;
import com.demo.recyclerview.loadmore.layout.TestTabLayout;

import java.util.List;

/**
 * @date 2021/1/23
 */
public class StaggeredGridAdapter extends ABaseTypeAdapter<BaseTestTypeBean> {

    public StaggeredGridAdapter(List<BaseTestTypeBean> list) {
        super(list);
    }

    @Override
    protected ABaseViewHolder onCreate(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;
        StaggeredGridLayoutManager.LayoutParams params = new StaggeredGridLayoutManager.LayoutParams(StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT, StaggeredGridLayoutManager.LayoutParams.WRAP_CONTENT);
        switch (viewType) {
            case TestItemType.TYPE_BANNER: {
                view = new TestBannerLayout(context);
                params.setFullSpan(true);
                break;
            }
            case TestItemType.TYPE_TAB: {
                view = new TestTabLayout(context);
                params.setFullSpan(true);
                break;
            }
            case TestItemType.TYPE_HEADER: {
                view = new TestHorizontalLayout(context);
                params.setFullSpan(true);
                break;
            }
            case TestItemType.TYPE_LIST:
            default: {
                view = new TestListLayout(context);
                break;
            }
        }
        if (view != null) {
            view.setLayoutParams(params);
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
                ((TestListLayout) itemView).setData(position, listBean.height);
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

//    @Override
//    public void onViewAttachedToWindow(@NonNull BaseViewHolder holder) {
//        super.onViewAttachedToWindow(holder);
//        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
//        if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
//            StaggeredGridLayoutManager.LayoutParams stagLp = (StaggeredGridLayoutManager.LayoutParams) lp;
//            int position = holder.getLayoutPosition();
//            int viewType = getViewType(position);
//            switch (viewType) {
//                case TestItemType.TYPE_BANNER:
//                case TestItemType.TYPE_TAB:
//                case TestItemType.TYPE_HEADER: {
//                    stagLp.setFullSpan(true);
//                    break;
//                }
//            }
//        }
//    }
}
