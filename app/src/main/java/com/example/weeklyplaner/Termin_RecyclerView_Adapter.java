package com.example.weeklyplaner;

import static com.example.weeklyplaner.DatabaseOp.updateCheckedInDB;
import static com.example.weeklyplaner.Utils.getSpecificTerminlisteInCurrentWeek;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import items.Termin;

public class Termin_RecyclerView_Adapter extends
        RecyclerView.Adapter<Termin_RecyclerView_Adapter.TerminViewHolder> {
    Context context;
    CardView cardView;
    ArrayList<Termin> terminliste;
    private ProgressBar progressBar;
    private TextView tasksDone;
    private TextView percentView;

    public Termin_RecyclerView_Adapter(Context context, ArrayList<Termin> terminliste,
                                       ProgressBar progressBar, TextView tasksDone,
                                       TextView percentView) {
        this.context = context;
        this.terminliste = terminliste;
        this.progressBar = progressBar;
        this.tasksDone = tasksDone;
        this.percentView = percentView;
    }

    @NonNull
    @Override
    public Termin_RecyclerView_Adapter.TerminViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row,
                parent, false);
        return new Termin_RecyclerView_Adapter.TerminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Termin_RecyclerView_Adapter.TerminViewHolder holder,
                                 int position) {
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

    public static int howManyDone(int day) {
        int done = 0;
        for (Termin t : getSpecificTerminlisteInCurrentWeek(day)) {
            if (t.isChecked()) {
                done++;
            }
        }
        return done;
    }

    public void updateProgress() {
        int counter = 0;
        if (terminliste.size() == 0) {
            progressBar.setProgress(0);
        } else {
            for (Termin t : terminliste) {
                if (t.isChecked()) {
                    counter++;
                }
            }

            int progress = (int) (((float) counter / terminliste.size()) * 100);
            progressBar.setProgress(progress, true);
        }
    }

    public void updateTasksDone() {
        if (terminliste.size() == 0) {
            String text = "No Appointments";
            tasksDone.setText(text);
            return;
        }
        int doneCounter = 0;
        for (Termin t : terminliste) {
            if (t.isChecked()) {
                doneCounter++;
            }
        }
        String text = doneCounter + " of " + terminliste.size() + " completed";
        tasksDone.setText(text);
    }

    public void updateProgressInPercent() {
        int doneCounter = 0;
        for (Termin t : terminliste) {
            if (t.isChecked()) {
                doneCounter++;
            }
        }
        float percentage = ((float) doneCounter / terminliste.size()) * 100;
        String text = (int) percentage + "%";
        percentView.setText(text);
    }

    public class TerminViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView TerminnameTextView, TerminPrioTextView;
        CheckBox checkbox;

        public TerminViewHolder(@NonNull View itemView) {
            super(itemView);
            TerminnameTextView = itemView.findViewById(R.id.textViewTerminNameRecyclerView);
            TerminPrioTextView = itemView.findViewById(R.id.textViewPriority);
            checkbox = itemView.findViewById(R.id.checkbox);
            cardView = itemView.findViewById(R.id.cardView);

            // Set click listener on the itemView
            itemView.setOnClickListener(this);

            // Set the checkbox listener
            checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                // Update the checked state of the Termin object
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Termin termin = terminliste.get(position);
                    termin.setChecked(isChecked);

                    if (isChecked) {
                        updateCheckedInDB(LoginScreen.email, termin.getId(), true);
                        updateProgress();
                        updateTasksDone();
                        updateProgressInPercent();
                        CardView c = itemView.findViewById(R.id.cardView);
                        c.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray));
                        checkbox.setButtonTintList(ColorStateList.valueOf(
                                ContextCompat.getColor(context, R.color.green)));
                        TerminnameTextView.setTextColor(Color.DKGRAY);
                        TerminPrioTextView.setTextColor(Color.DKGRAY);
                    } else {
                        updateCheckedInDB(LoginScreen.email, termin.getId(), false);
                        updateProgress();
                        updateTasksDone();
                        updateProgressInPercent();
                        CardView c = itemView.findViewById(R.id.cardView);
                        c.setCardBackgroundColor(ContextCompat.getColor(context,
                                R.color.light_blue));
                        checkbox.setButtonTintList(ColorStateList.valueOf(
                                ContextCompat.getColor(context, R.color.black)));
                        TerminnameTextView.setTextColor(Color.BLACK);
                        TerminPrioTextView.setTextColor(Color.BLACK);
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
            intent.putExtra("termin_datum", termin.getDatum().toString());
            intent.putExtra("termin_tag", termin.getActualDatum().getDayOfWeek().getValue());
            intent.putExtra("termin_montastag", termin.getActualDatum().getDayOfMonth());
            context.startActivity(intent);
        }
    }

    public void setTerminliste(ArrayList<Termin> terminliste) {
        this.terminliste = terminliste;
    }
}
