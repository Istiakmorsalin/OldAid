package com.onisha.oldaid.oldhome;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.onisha.oldaid.BaseActivity;
import com.onisha.oldaid.R;
import com.onisha.oldaid.model.OldHome;

import java.util.ArrayList;
import java.util.List;

public class OldHomeActivity extends BaseActivity {
    private List<OldHome> oldHomeList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OldHomeAdapter mAdapter;

    @Override
    protected int layoutResId() {
        return R.layout.content_main;
    }

    @Override
    protected int menuResId() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new OldHomeAdapter(oldHomeList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        prepareOldHomeData();
    }

    private void prepareOldHomeData() {
        OldHome oldHome = new OldHome("Home1", "Dhaka", 2015);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home2", "Khulna", 200);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home3", "Bogra", 210);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home4", "Khushtia", 300);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home5", "Chittagong", 320);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home6", "Mymensing", 330);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home7", "Jamalpur", 2009);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home8", "Tarakandi", 550);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home9", "Haluaghat", 303);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home10", "CoxsBazar", 345);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home11", "Chittagong", 320);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home12", "Mymensing", 330);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home13", "Jamalpur", 2009);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home14", "Tarakandi", 550);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home15", "Haluaghat", 303);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home2", "Khulna", 200);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home3", "Bogra", 210);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home4", "Khushtia", 300);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home5", "Chittagong", 320);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home6", "Mymensing", 330);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home7", "Jamalpur", 2009);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home8", "Tarakandi", 550);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home9", "Haluaghat", 303);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home10", "CoxsBazar", 345);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home11", "Chittagong", 320);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home12", "Mymensing", 330);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home13", "Jamalpur", 2009);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home14", "Tarakandi", 550);
        oldHomeList.add(oldHome);

        oldHome = new OldHome("Home15", "Haluaghat", 303);
        oldHomeList.add(oldHome);


        mAdapter.notifyDataSetChanged();
    }
}