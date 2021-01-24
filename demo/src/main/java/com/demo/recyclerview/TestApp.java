package com.demo.recyclerview;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.ab.lib.widget.recyclerview.LoadMoreLayout;
import com.ab.lib.widget.recyclerview.LoadMoreLayoutCreator;
import com.ab.lib.widget.recyclerview.LoadMoreRecyclerView;
import com.demo.recyclerview.utils.GlobalContext;

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
        LoadMoreRecyclerView.setDefaultLoadMoreLayoutCreator(new LoadMoreLayoutCreator() {
            @Override
            public LoadMoreLayout createLoadMoreLayout(@NonNull Context context) {
                return new DefaultLoadMore(context);
            }
        });
    }

}
