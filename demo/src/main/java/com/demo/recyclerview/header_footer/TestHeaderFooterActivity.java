package com.demo.recyclerview.header_footer;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ab.lib.widget.recyclerview.adapter.ABaseAdapter;
import com.ab.lib.widget.recyclerview.ABaseViewHolder;
import com.ab.lib.widget.recyclerview.LoadMoreRecyclerView;
import com.ab.lib.widget.recyclerview.layoutmanger.ALinearLayoutManager;
import com.demo.recyclerview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 2021/1/23
 */
public class TestHeaderFooterActivity extends AppCompatActivity {

    private List<View> mHeaderViews = new ArrayList<>();
    private List<View> mFooterViews = new ArrayList<>();

    private Button btn_addHeader;
    private Button btn_removeHeader;
    private Button btn_addFooter;
    private Button btn_removeFooter;
    private Button btn_addItem;
    private Button btn_addItemRanged;
    private Button btn_removeItemRanged;
    private LoadMoreRecyclerView recyclerview;

    private TestAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_header_footer);
        initViews();
        initListeners();
        doBusiness();
    }

    private void initViews() {
        btn_addHeader = findViewById(R.id.btn_addHeader);
        btn_removeHeader = findViewById(R.id.btn_removeHeader);
        btn_addFooter = findViewById(R.id.btn_addFooter);
        btn_removeFooter = findViewById(R.id.btn_removeFooter);
        btn_addItem = findViewById(R.id.btn_addItem);
        btn_addItemRanged = findViewById(R.id.btn_addItemRanged);
        btn_removeItemRanged = findViewById(R.id.btn_removeItemRanged);
        recyclerview = findViewById(R.id.recyclerview);
    }

    private void initListeners() {
        btn_addHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View headerView = getHeader();
                recyclerview.addHeaderView(headerView);
                mHeaderViews.add(headerView);
            }
        });

        btn_removeHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//            DKLog.e("headers: " + mHeaderViews.size)
                if (!mHeaderViews.isEmpty()) {
                    int index = mHeaderViews.size() - 1;
                    recyclerview.removeHeaderView(mHeaderViews.get(index));
                    mHeaderViews.remove(index);
                }
            }
        });

        btn_addFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View footerView = getFooter();
                recyclerview.addFooterView(footerView);
                mFooterViews.add(footerView);
            }
        });

        btn_removeFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//            DKLog.e("headers: " + mHeaderViews.size)
                if (!mFooterViews.isEmpty()) {
                    int index = mFooterViews.size() - 1;
                    recyclerview.removeFooterView(mFooterViews.get(index));
                    mFooterViews.remove(index);
                }
            }
        });

        btn_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.addData(1, "新增项");
                recyclerview.notifyItemInserted(1, mAdapter.getItemCount());
            }
        });

        btn_addItemRanged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> list = new ArrayList<String>() {
                    {
                        add("新增项1");
                        add("新增项2");
                    }
                };
                mAdapter.addData(1, list);
                recyclerview.notifyItemRangeInserted(1, 2, mAdapter.getItemCount());
            }
        });

        btn_removeItemRanged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int removeCount = 0;
                for (int i = 0; i <= 1; i++) {
                    String removed = mAdapter.removeData(1);
                    if (removed != null) {
                        removeCount++;
                    }
                }
                if (removeCount > 0) {
                    recyclerview.notifyItemRangeRemoved(1, removeCount, mAdapter.getItemCount());
                }
            }
        });

        recyclerview.setLayoutManager(new ALinearLayoutManager(this));
//        recyclerview.itemAnimator = null // 尝试fix：Called attach on a child which is not detached: ViewHolder
        mAdapter = new TestAdapter(null);
        recyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ABaseAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(@NonNull ABaseViewHolder viewHolder, int position, @NonNull String itemData) {
                mAdapter.removeData(itemData);
                Toast.makeText(TestHeaderFooterActivity.this, "remove: " + position, Toast.LENGTH_SHORT).show();
//            mAdapter.notifyItemRemoved(recyclerview.headersCount + position)
                recyclerview.notifyItemRemoved(position, mAdapter.getItemCount());
            }
        });
    }

    private void doBusiness() {
        for (int i = 0; i < 4; i++) {
            mAdapter.addData("第" + i + "项，点我删除");
        }
        mAdapter.notifyDataSetChanged();
    }

    private View getHeader() {
        ImageView imageView = new ImageView(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 88);
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(R.drawable.ic_room_filter_tab_arrow_down_normal);
        return imageView;
    }

    private View getFooter() {
        ImageView imageView = new ImageView(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 88);
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setRotationY(180);
        imageView.setImageResource(R.drawable.ic_room_filter_tab_arrow_down_selected);
        return imageView;
    }

}
