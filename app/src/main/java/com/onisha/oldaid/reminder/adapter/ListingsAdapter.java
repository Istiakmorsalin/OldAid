package com.onisha.oldaid.reminder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onisha.oldaid.R;
import com.onisha.oldaid.reminder.model.EventModelDB;
import com.onisha.oldaid.reminder.model.ListingsModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by onisha on 15-Aug-16.
 */
public class ListingsAdapter extends RecyclerView.Adapter<ListingsAdapter.Viewholder> {


    private ArrayList<ListingsModel> data;
    private ListingsModel model;
    private Context context;

    public ListingsAdapter(ArrayList<ListingsModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        private TextView eventText, timeAndDateText;
        private LinearLayout toplayout;

        public Viewholder(View itemView) {
            super(itemView);

            eventText = (TextView)itemView.findViewById(R.id.event);
            timeAndDateText = (TextView)itemView.findViewById(R.id.time_and_date);
            toplayout = (LinearLayout)itemView.findViewById(R.id.toplayout);

        }
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listings_row, parent, false);

        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(final Viewholder holder, final int position) {

        model = data.get(position);

        holder.eventText.setText(model.getEvent());
        holder.timeAndDateText.setText("At  "+model.getTime() +"  On  "+model.getDate());

        // long click an item to delete it from database
        holder.toplayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                // deleting the selected row from Realm database
                Realm realm = Realm.getInstance(context);
                RealmResults<EventModelDB> result = realm.where(EventModelDB.class)
                        .equalTo("timestamp", data.get(position).getTimestamp())
                        .findAll();

                if(result != null){
                    if(result.size() > 0){
                        realm.beginTransaction();
                        result.remove(0);
                        realm.commitTransaction();

                    }
                }
                Log.i("clickedposn", ""+position);

                // Delete the row data from the ArrayList passed to this Adapter
                 data.remove(position);

                // Refresh the RecyclerView after row deletion
                 notifyDataSetChanged();
               // context.startActivity(new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));


                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }



}
