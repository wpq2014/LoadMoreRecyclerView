package com.ab.lib.widget.recyclerview;

import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * @author wpq
 * @version 1.0
 */
public class ABaseViewHolder extends RecyclerView.ViewHolder{

    private final SparseArrayCompat<View> mViews;

    public ABaseViewHolder(@NonNull View itemView) {
        super(itemView);
        mViews = new SparseArrayCompat<>();
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (null == view) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

}
