package com.ab.lib.widget.recyclerview;

import android.view.View;

/**
 * @author wupuquan
 * @version 1.0
 * @since 2018/6/7 16:51
 */
public interface LoadMoreLayout {

    /**
     * 加载中...
     */
    View getLoadingView();

    /**
     * 加载完成
     */
    View getCompleteView();

    /**
     * 加载失败，点击重试
     */
    View getErrorView();

    /**
     * 没有更多数据了
     */
    View getNoMoreView();

}
