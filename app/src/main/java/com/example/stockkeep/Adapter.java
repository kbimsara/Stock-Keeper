package com.example.stockkeep;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private Context context;
    private ArrayList itm,qt,date;

    public Adapter(Context context, ArrayList itm, ArrayList qt, ArrayList date) {
        this.context = context;
        this.itm = itm;
        this.qt = qt;
        this.date = date;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.userentry,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itm.setText(String.valueOf(itm.get(position)));
        holder.date.setText(String.valueOf(date.get(position)));
        holder.qt.setText(String.valueOf(qt.get(position)));
    }

    @Override
    public int getItemCount() {
        return itm.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itm,date,qt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itm=itemView.findViewById(R.id.out_itm);
            date=itemView.findViewById(R.id.out_dt);
            qt=itemView.findViewById(R.id.out_qt);
        }
    }
}
