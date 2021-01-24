package com.ab.lib.widget.recyclerview;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 多类型item
 *
 * @author wupuquan
 * @version 1.0
 * @since 2018/8/3 16:18
 */
public abstract class ABaseTypeAdapter<T> extends RecyclerView.Adapter<ABaseViewHolder> {

    private final List<T> mList;

    public ABaseTypeAdapter(List<T> list) {
        if (list == null) {
            mList = new ArrayList<>();
        } else {
            mList = list;
        }
    }

    public List<T> getData() {
        return mList;
    }

    public void setData(List<T> list) {
        if (list == null) {
            return;
        }
        mList.clear();
        mList.addAll(list);
    }

    public void addData(T data) {
        mList.add(data);
    }

    public void addData(List<T> list) {
        mList.addAll(list);
    }

    public void clearData() {
        mList.clear();
    }

    @NonNull
    @Override
    public ABaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ABaseViewHolder viewHolder = onCreate(parent, viewType);
        if (viewHolder == null) {
            Log.e(this.getClass().getSimpleName(), "onCreateViewHolder return null !");
            viewHolder = new ABaseViewHolder(new View(parent.getContext()));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ABaseViewHolder holder, int position) {
        try {
            T t = mList.get(position);
            if (t == null) {
                return;
            }
            onBind(holder, position, t);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ABaseViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= 0 && position < getItemCount()) {
            return getViewType(position);
        }
        return super.getItemViewType(position);
    }

    @Nullable
    protected T getItem(int position) {
        if (position >= 0 && position < getItemCount()) {
            return mList.get(position);
        }
        return null;
    }

    protected abstract ABaseViewHolder onCreate(@NonNull ViewGroup parent, int viewType);

    protected abstract void onBind(@NonNull ABaseViewHolder viewHolder, int position, @NonNull T itemData);

    protected abstract int getViewType(int position);
}
