package com.demo.recyclerview;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

import com.ab.lib.widget.recyclerview.LoadMoreLayout;
import com.ab.lib.widget.recyclerview.LoadMoreLayoutCreator;
import com.ab.lib.widget.recyclerview.LoadMoreRecyclerView;
import com.demo.recyclerview.utils.GlobalContext;
import com.demo.recyclerview.widgets.DefaultLoadMore;
import com.demo.recyclerview.widgets.DefaultRefreshHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

/**
 * @date 2021/1/23
 */
public class TestApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        GlobalContext.setApplication(this);

        initRefreshAndLoadMore();
    }

    private void initRefreshAndLoadMore() {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true); // 启用矢量图兼容
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.purple_500); // 全局设置主题颜色
                layout.setEnableOverScrollBounce(false); // 是否启用越界回弹
                return new DefaultRefreshHeader(context);
            }
        });

        LoadMoreRecyclerView.setDefaultLoadMoreLayoutCreator(new LoadMoreLayoutCreator() {
            @Override
            public LoadMoreLayout createLoadMoreLayout(@NonNull Context context) {
                return new DefaultLoadMore(context);
            }
        });
    }

}
