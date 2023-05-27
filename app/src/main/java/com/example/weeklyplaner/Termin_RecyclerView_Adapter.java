package com.example.weeklyplaner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new Termin_RecyclerView_Adapter.TerminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Termin_RecyclerView_Adapter.TerminViewHolder holder, int position) {
        // assign values to view with pos
        Termin termin = terminliste.get(position);
        holder.TerminnameTextView.setText(termin.getTerminname());
        holder.TerminPrioTextView.setText(termin.getPrio());
        holder.checkbox.setChecked(termin.isMarked());
        if (termin.isMarked()) {
            holder.itemView.setBackgroundColor(Color.GRAY);
            holder.TerminnameTextView.setTextColor(Color.GRAY);
            holder.TerminPrioTextView.setTextColor(Color.GRAY);
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#1C1F28"));
            holder.TerminnameTextView.setTextColor(Color.WHITE);
            holder.TerminPrioTextView.setTextColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        // number of items
        return terminliste.size();
    }

    public class TerminViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView TerminnameTextView, TerminPrioTextView;
        CheckBox checkbox;

        public TerminViewHolder(@NonNull View itemView) {
            super(itemView);
            TerminnameTextView = itemView.findViewById(R.id.TerminnameTextView);
            TerminPrioTextView = itemView.findViewById(R.id.TerminPrioritätTextView);
            checkbox = itemView.findViewById(R.id.checkbox);

            // Checkbox operations
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Termin termin = terminliste.get(position);
                        termin.setMarked(isChecked);
                        if (isChecked) {
                            itemView.setBackgroundColor(Color.GRAY);
                            TerminnameTextView.setTextColor(Color.GRAY);
                            TerminPrioTextView.setTextColor(Color.GRAY);
                        } else {
                            itemView.setBackgroundColor(Color.parseColor("#1C1F28"));
                            TerminnameTextView.setTextColor(Color.WHITE);
                            TerminPrioTextView.setTextColor(Color.WHITE);
                        }
                    }
                }
            });

            itemView.setOnClickListener(this);  // Set click listener on the itemView
        }

        @Override
        public void onClick(View v) {
            checkbox.setChecked(!checkbox.isChecked()); // Checkbox markieren/entmarkieren
        }
    }

    public void setTerminliste(ArrayList<Termin> terminliste) {
        this.terminliste = terminliste;
    }
}
