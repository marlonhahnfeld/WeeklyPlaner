package com.example.weeklyplaner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import items.Termin;

public class Termin_RecyclerView_Adapter extends RecyclerView.Adapter<Termin_RecyclerView_Adapter.TerminViewHolder> {
    Context context;
    ArrayList<Termin> terminliste;

    public Termin_RecyclerView_Adapter(Context context, ArrayList<Termin> terminliste) {
        this.context = context;
        this.terminliste = terminliste;
    }

    @NonNull
    @Override
    public Termin_RecyclerView_Adapter.TerminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // infalte layout (look for row)
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
//        return new Termin_RecyclerView_Adapter.TerminViewHolder(view);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new Termin_RecyclerView_Adapter.TerminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Termin_RecyclerView_Adapter.TerminViewHolder holder, int position) {
        // assign values to view with pos
        holder.TerminnameTextView.setText(terminliste.get(position).getTerminname());
        holder.TerminPrioTextView.setText(terminliste.get(position).getPrio());

    }

    @Override
    public int getItemCount() {
        // number of items
        return terminliste.size();
    }

    public class TerminViewHolder extends RecyclerView.ViewHolder {
        // grab views form our recycler row

        TextView TerminnameTextView, TerminPrioTextView;

        public TerminViewHolder(@NonNull View itemView) {
            super(itemView);
            TerminnameTextView = itemView.findViewById(R.id.TerminnameTextView);
            TerminPrioTextView = itemView.findViewById(R.id.TerminPrioritätTextView);
        }
    }
}
