package com.ab.lib.widget.recyclerview.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.ab.lib.widget.recyclerview.ABaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wupuquan
 * @since 2021/1/25
 */
@SuppressWarnings("UnusedReturnValue")
abstract class AInnerBaseAdapter<T> extends RecyclerView.Adapter<ABaseViewHolder> {

    protected final List<T> mList;

    public AInnerBaseAdapter(List<T> list) {
        if (list == null) {
            mList = new ArrayList<>();
        } else {
            mList = list;
        }
    }

    public List<T> getData() {
        return mList;
    }

    public boolean setData(List<T> list) {
        if (list == null) {
            return false;
        }
        if (!mList.isEmpty()) {
            mList.clear();
            notifyDataSetChanged(); // 每次数据源改变要刷新
        }
        return mList.addAll(list);
    }

    public boolean addData(T data) {
        if (data == null) {
            return false;
        }
        return mList.add(data);
    }

    public void addData(int position, T data) {
        if (data == null) {
            return;
        }
        if (position > getItemCount()) {
            addData(data);
            return;
        }
        if (position < 0) {
            position = 0;
        }
        mList.add(position, data);
    }

    public boolean addData(List<T> list) {
        if (list == null) {
            return false;
        }
        return mList.addAll(list);
    }

    public boolean addData(int position, List<T> list) {
        if (list == null) {
            return false;
        }
        if (position > getItemCount()) {
            return addData(list);
        }
        if (position < 0) {
            position = 0;
        }
        return mList.addAll(position, list);
    }

    public boolean removeData(T data) {
        if (data == null) {
            return false;
        }
        return mList.remove(data);
    }

    @Nullable
    public T removeData(int position) {
        if (position < 0 || position >= getItemCount()) {
            return null;
        }
        return mList.remove(position);
    }

    public void clearData() {
        mList.clear();
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Nullable
    protected T getItem(int position) {
        if (position >= 0 && position < getItemCount()) {
            return mList.get(position);
        }
        return null;
    }
}
