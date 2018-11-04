package com.example.minh.android_home_test;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.minh.android_home_test.adapter.AdapterItemList;
import com.example.minh.android_home_test.config.Config;
import com.example.minh.android_home_test.config.PermissionRequest;
import com.example.minh.android_home_test.model.Item;
import com.example.minh.android_home_test.utils.ParseContent;

import java.net.MalformedURLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout lyRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if (Build.VERSION.SDK_INT >= 23) {
            PermissionRequest permissionRequest = new PermissionRequest(this);
            permissionRequest.checkInternet();
        }
        onRefreshingLayout();
        lyRefresh.setRefreshing(false);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Config.PERMISSION_REQUEST_INTERNET:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initData();
                } else {
                    Log.e("Minh", "onRequestPermissionsResult: permission not granted");
                    finish();
                }
                break;

        }
    }

    private void initData() {
        ParseContent parseContent = new ParseContent(this, lyRefresh);
        try {
            parseContent.parseJson();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void initView() {

        recyclerView = findViewById(R.id.rcv_list_item);
        lyRefresh = findViewById(R.id.lyRefresh);

    }

    private void onRefreshingLayout(){
        lyRefresh.post(new Runnable() {
            @Override
            public void run() {

            }
        });
        lyRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });




    }

    public void initAdapterData(List<Item> itemList) {
        AdapterItemList adapterItemList = new AdapterItemList(this, itemList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterItemList);
    }



}
