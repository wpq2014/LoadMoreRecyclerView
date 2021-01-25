package com.ab.lib.widget.recyclerview;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * @author wupuquan
 * @since 2021/1/25
 */
public abstract class ABaseTypeAdapter<T> extends AInnerBaseAdapter<T>{

    private static final String TAG = ABaseTypeAdapter.class.getSimpleName();

    public ABaseTypeAdapter(List<T> list) {
        super(list);
    }

    @NonNull
    @Override
    public ABaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ABaseViewHolder viewHolder = onCreate(parent, viewType);
        if (viewHolder == null) {
            Log.e(TAG, "onCreateViewHolder return null !");
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
    public int getItemViewType(int position) {
        if (position >= 0 && position < getItemCount()) {
            return getViewType(position);
        }
        return super.getItemViewType(position);
    }

    protected abstract ABaseViewHolder onCreate(@NonNull ViewGroup parent, int viewType);

    protected abstract void onBind(@NonNull ABaseViewHolder viewHolder, int position, @NonNull T itemData);

    protected abstract int getViewType(int position);
}
