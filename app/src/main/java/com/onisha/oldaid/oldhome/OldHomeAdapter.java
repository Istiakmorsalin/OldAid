package com.onisha.oldaid.oldhome;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onisha.oldaid.R;
import com.onisha.oldaid.model.OldHome;

import java.util.List;

public class OldHomeAdapter extends RecyclerView.Adapter<OldHomeAdapter.MyViewHolder> {

    private List<OldHome> oldHomeList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, location, expense;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            location = (TextView) view.findViewById(R.id.location);
            expense = (TextView) view.findViewById(R.id.expense);
        }
    }


    public OldHomeAdapter(List<OldHome> moviesList) {
        this.oldHomeList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.oldhome_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OldHome oldHome = oldHomeList.get(position);
        holder.name.setText( oldHome.getName());
        holder.location.setText("Location: " + oldHome.getLocation());
        holder.expense.setText("Expense:" + oldHome.getExpense());
    }

    @Override
    public int getItemCount() {
        return oldHomeList.size();
    }
}
