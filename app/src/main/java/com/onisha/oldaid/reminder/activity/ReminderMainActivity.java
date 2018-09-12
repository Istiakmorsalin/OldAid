package com.onisha.oldaid.reminder.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.onisha.oldaid.R;
import com.onisha.oldaid.reminder.adapter.ListingsAdapter;
import com.onisha.oldaid.reminder.model.EventModelDB;
import com.onisha.oldaid.reminder.model.ListingsModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class ReminderMainActivity extends AppCompatActivity {

    private ArrayList<ListingsModel> list;
    private RecyclerView rvListings;
    private ListingsAdapter adapter;
    private ListingsModel model;
    private LinearLayoutManager mLayoutManager;
    private Realm myRealm;
    private RealmResults<EventModelDB> results1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_listing);

        rvListings = (RecyclerView) findViewById(R.id.events);

        list = new ArrayList<ListingsModel>();

        // read the saved values in database
        myRealm = Realm.getInstance(getBaseContext());
        results1 = myRealm.where(EventModelDB.class).findAll();

        for(int i = results1.size()-1;  i >= 0; i--) {
            EventModelDB c = results1.get(i);
            model = new ListingsModel();
            model.setEvent(c.getEvent());
            model.setTime(c.getTime());
            model.setDate(c.getDate());
            model.setTimestamp(c.getTimestamp());

            list.add(model);
        }


        adapter = new ListingsAdapter(list, getBaseContext());
        rvListings.setAdapter(adapter);
        mLayoutManager = new LinearLayoutManager(this);
     //   mLayoutManager.setReverseLayout(true);
      //  mLayoutManager.setStackFromEnd(true);
        rvListings.setLayoutManager(mLayoutManager);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.add) {
            startActivity(new Intent(getBaseContext(), AddEvent.class));
            return true;
        }
        else if (id == R.id.info) {
            Snackbar.make(findViewById(android.R.id.content), "Long Press an event to delete it", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.WHITE)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
