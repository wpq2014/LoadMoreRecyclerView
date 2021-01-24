package com.ab.lib.widget.recyclerview;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author wpq
 * @version 1.0
 */
public abstract class ABaseAdapter<T> extends RecyclerView.Adapter<ABaseViewHolder> {

    private List<T> mList;
    private OnItemClickListener<T> mOnItemClickListener;
    private OnItemLongClickListener<T> mOnItemLongClickListener;

    public ABaseAdapter(List<T> list) {
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

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    protected T getItem(int position) {
        if (position >= 0 && position < getItemCount()) {
            return mList.get(position);
        }
        return null;
    }

    protected abstract ABaseViewHolder onCreate(@NonNull ViewGroup parent);

    protected abstract void onBind(@NonNull ABaseViewHolder viewHolder, int position, @NonNull T itemData);

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(@NonNull ABaseViewHolder viewHolder, int position, @NonNull T itemData);
    }

    public interface OnItemLongClickListener<T> {
        boolean onItemLongClick(@NonNull ABaseViewHolder viewHolder, int position, @NonNull T itemData);
    }
}
