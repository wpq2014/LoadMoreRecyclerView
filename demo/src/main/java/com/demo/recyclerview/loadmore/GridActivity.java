package com.demo.recyclerview.loadmore;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ab.lib.widget.recyclerview.LoadMoreRecyclerView;
import com.ab.lib.widget.recyclerview.itemdecoration.AGridSpaceDecoration;
import com.ab.lib.widget.recyclerview.layoutmanger.AGridLayoutManager;
import com.demo.recyclerview.R;
import com.demo.recyclerview.loadmore.adapter.GridAdapter;
import com.demo.recyclerview.loadmore.bean.BaseTestTypeBean;
import com.demo.recyclerview.loadmore.bean.TestBannerBean;
import com.demo.recyclerview.loadmore.bean.TestHeaderBean;
import com.demo.recyclerview.loadmore.bean.TestListBean;
import com.demo.recyclerview.loadmore.bean.TestTabBean;
import com.demo.recyclerview.utils.MainHandler;
import com.demo.recyclerview.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @date 2021/1/23
 */
public class GridActivity extends AppCompatActivity {

    private LoadMoreRecyclerView recyclerview;
    private GridAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_loadmore);
        initViews();
        initListeners();
        doBusiness();
    }

    private void initViews() {
        recyclerview = findViewById(R.id.recyclerview);
    }

    private void initListeners() {
        AGridLayoutManager layoutManager = new AGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(layoutManager);
        AGridSpaceDecoration itemDecoration = new AGridSpaceDecoration(layoutManager.getOrientation(), ScreenUtil.dp2px(10));
        recyclerview.setItemDecoration(itemDecoration);
        recyclerview.setLoadMoreEnabled(true);
        recyclerview.setOnLoadListener(new LoadMoreRecyclerView.OnLoadListener() {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
        mAdapter = new GridAdapter(null);
        recyclerview.setAdapter(mAdapter);
    }

    private void doBusiness() {
        mAdapter.setData(initData());
        mAdapter.notifyDataSetChanged();
    }

    private List<BaseTestTypeBean> initData() {
        List<BaseTestTypeBean> list = new ArrayList<>();
        // banner
        list.add(new TestBannerBean());
        // tab
        list.add(new TestTabBean());
        // header
        list.add(new TestHeaderBean());
        // list
        list.addAll(getListData());
        return list;
    }

    private List<BaseTestTypeBean> getListData() {
        List<BaseTestTypeBean> listData = new ArrayList<>();
        Random random = new Random();
        int baseHeight = ScreenUtil.dp2px(96);
        for (int i = 0; i < 15; i++) {
            TestListBean listBean = new TestListBean();
            listBean.height = baseHeight + random.nextInt(ScreenUtil.dp2px(96));
            listData.add(listBean);
        }
        return listData;
    }

    private void loadMoreData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                MainHandler.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.addData(getListData());
                        mAdapter.notifyItemInserted(mAdapter.getItemCount());
                        if (mAdapter.getItemCount() > 45) {
                            recyclerview.noMore(true);
                        } else {
                            recyclerview.loadMoreComplete();
                        }
                    }
                });
            }
        }).start();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_staggeredgridlayoutmanager, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_more_than_one_page: // 一页以上
//                page = 2653;
//                isRefresh = true;
//                mSwipeRefreshLayout.setRefreshing(true);
//                showTime();
//                break;
//            case R.id.action_one_page: // 只有一页
//                page = 2654;
//                isRefresh = true;
//                mSwipeRefreshLayout.setRefreshing(true);
//                showTime();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
