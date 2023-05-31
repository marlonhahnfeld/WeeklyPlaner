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
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import items.Termin;

public class Termin_RecyclerView_Adapter extends RecyclerView.Adapter<Termin_RecyclerView_Adapter.TerminViewHolder> {
    Context context;


    CardView cardView;
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

        // Set the checkbox state
        holder.checkbox.setChecked(termin.isChecked());
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
            cardView = itemView.findViewById(R.id.cardview);

            // Set click listener on the itemView
            itemView.setOnClickListener(this);

            // Set the checkbox listener
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Update the checked state of the Termin object
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Termin termin = terminliste.get(position);
                        termin.setChecked(isChecked);

                        if (isChecked) {
                            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray));
                            TerminnameTextView.setTextColor(Color.DKGRAY);
                            TerminPrioTextView.setTextColor(Color.DKGRAY);
                        } else {
                            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray_Termin));
                            TerminnameTextView.setTextColor(Color.WHITE);
                            TerminPrioTextView.setTextColor(Color.WHITE);
                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Termin termin = terminliste.get(position);
                openTerminDetailsScreen(termin);
            }
        }

        private void openTerminDetailsScreen(Termin termin) {
            // Erstelle den Intent für die TerminDetailsActivity
            Intent intent = new Intent(context, TerminDetailsActivity.class);

            // Füge die Daten des Termins als Extras hinzu
            intent.putExtra("termin_name", termin.getTerminname());
            intent.putExtra("termin_beschreibung", termin.getBeschreibung());
            intent.putExtra("termin_prio", termin.getPrio());
            intent.putExtra("termin_tag", termin.getTag());
            intent.putExtra("termin_id", termin.getId());
            context.startActivity(intent);
        }
    }

    public void setTerminliste(ArrayList<Termin> terminliste) {
        this.terminliste = terminliste;
    }
}
