package com.ab.lib.widget.recyclerview;

import android.content.Context;
import androidx.annotation.NonNull;

import com.ab.lib.widget.recyclerview.LoadMoreLayout;

/**
 * @author wupuquan
 * @version 1.0
 * @since 2018/6/8 09:56
 */
public interface LoadMoreLayoutCreator {

    LoadMoreLayout createLoadMoreLayout(@NonNull Context context);
}
