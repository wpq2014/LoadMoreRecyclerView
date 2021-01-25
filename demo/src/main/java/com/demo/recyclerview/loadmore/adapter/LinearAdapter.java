package com.demo.recyclerview.loadmore.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
public class LinearAdapter extends ABaseTypeAdapter<BaseTestTypeBean> {

    public LinearAdapter(List<BaseTestTypeBean> list) {
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
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
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
                ((TestListLayout) itemView).setData(position);
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
}
