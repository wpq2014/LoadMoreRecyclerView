package com.ab.lib.widget.recyclerview.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ab.lib.widget.recyclerview.ABaseViewHolder;

import java.util.List;

/**
 * @author wupuquan
 * @since 2021/1/25
 */
public abstract class ABaseAdapter<T> extends AInnerBaseAdapter<T> {

    private ABaseAdapter.OnItemClickListener<T> mOnItemClickListener;
    private ABaseAdapter.OnItemLongClickListener<T> mOnItemLongClickListener;

    public ABaseAdapter(List<T> list) {
        super(list);
    }

    @NonNull
    @Override
    public ABaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return onCreate(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull final ABaseViewHolder holder, final int position) {
        try {
            T t = mList.get(position);
            if (t == null) {
                return;
            }
            onBind(holder, position, t);
            // 虽然在这里设置OnClickListener不太好，但是
            // mList.get(position)不受RecyclerView添加header影响，避免了空指针
            setListeners(holder, position, t);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ABaseViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    private void setListeners(@NonNull final ABaseViewHolder holder, final int position, @NonNull final T t) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        mOnItemClickListener.onItemClick(holder, position, t);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    try {
                        return mOnItemLongClickListener.onItemLongClick(holder, position, t);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            });
        }
    }

    protected abstract ABaseViewHolder onCreate(@NonNull ViewGroup parent);

    protected abstract void onBind(@NonNull ABaseViewHolder viewHolder, int position, @NonNull T itemData);

    public void setOnItemClickListener(ABaseAdapter.OnItemClickListener<T> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(ABaseAdapter.OnItemLongClickListener<T> onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(@NonNull ABaseViewHolder viewHolder, int position, @NonNull T itemData);
    }

    public interface OnItemLongClickListener<T> {
        boolean onItemLongClick(@NonNull ABaseViewHolder viewHolder, int position, @NonNull T itemData);
    }
}
