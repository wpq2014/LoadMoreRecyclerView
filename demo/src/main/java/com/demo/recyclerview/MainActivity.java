package com.demo.recyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.demo.recyclerview.header_footer.TestHeaderFooterActivity;
import com.demo.recyclerview.loadmore.GridActivity;
import com.demo.recyclerview.loadmore.LinearActivity;
import com.demo.recyclerview.loadmore.StaggeredGridActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_header_footer;
    private Button btn_linearLayoutManager;
    private Button btn_gridLayoutManager;
    private Button btn_staggeredLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initListeners();
    }

    private void initViews() {
        btn_header_footer = findViewById(R.id.btn_header_footer);
        btn_linearLayoutManager = findViewById(R.id.btn_linearLayoutManager);
        btn_gridLayoutManager = findViewById(R.id.btn_gridLayoutManager);
        btn_staggeredLayoutManager = findViewById(R.id.btn_staggeredLayoutManager);
    }

    private void initListeners() {
        btn_header_footer.setOnClickListener(this);
        btn_linearLayoutManager.setOnClickListener(this);
        btn_gridLayoutManager.setOnClickListener(this);
        btn_staggeredLayoutManager.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_header_footer) {
            startActivity(new Intent(MainActivity.this, TestHeaderFooterActivity.class));
        } else if (id == R.id.btn_linearLayoutManager) {
            startActivity(new Intent(MainActivity.this, LinearActivity.class));
        } else if (id == R.id.btn_gridLayoutManager) {
            startActivity(new Intent(MainActivity.this, GridActivity.class));
        } else if (id == R.id.btn_staggeredLayoutManager) {
            startActivity(new Intent(MainActivity.this, StaggeredGridActivity.class));
        }
    }
}