package com.example.weeklyplaner;

import static com.example.weeklyplaner.Utils.getSpecificTerminlisteInCurrentWeek;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import items.Termin;

public class SpecificDay extends AppCompatActivity implements View.OnClickListener {
    private ImageButton BackButton;
    private ImageButton filterButton;
    private ImageButton addButton;
    private ProgressBar progressBar;
    private TextView tasksDone;
    private TextView percentView;
    public TextView heutigerButton;
    static boolean refresh_needed = false;
    private Termin_RecyclerView_Adapter adapter_sort;
    public RecyclerView specificDay_TerminListe_RecyclerView;

    public int currentDay() {
        Intent intent = getIntent();
        switch (intent.getStringExtra("button_text")) {
            case "Montag":
                return 1;
            case "Dienstag":
                return 2;
            case "Mittwoch":
                return 3;
            case "Donnerstag":
                return 4;
            case "Freitag":
                return 5;
            case "Samstag":
                return 6;
            case "Sonntag":
                return 7;
            case "Offene Termine":
                return 8;
            case "Zukünftige Termine":
                return 9;
            default:
                return -99;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the activity here
        refreshSpecificDay();
    }

    public void refreshSpecificDay() {
        // Refresh the specific day's data here
        RecyclerView.Adapter adapter = specificDay_TerminListe_RecyclerView.getAdapter();
        if (adapter instanceof Termin_RecyclerView_Adapter) {
            Termin_RecyclerView_Adapter terminAdapter = (Termin_RecyclerView_Adapter) adapter;
            terminAdapter.setTerminliste(getSpecificTerminlisteInCurrentWeek(currentDay()));
            terminAdapter.updateProgress();
            terminAdapter.updateTasksDone();
            terminAdapter.updateProgressInPercent();
            terminAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_day);

        BackButton = findViewById(R.id.backButtonSpecificDayActivity);
        BackButton.setOnClickListener(this);

        filterButton = findViewById(R.id.filterButton);
        filterButton.setOnClickListener(this);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(this);

        heutigerButton = findViewById(R.id.textViewEdit);
        progressBar = findViewById(R.id.progressBar);
        tasksDone = findViewById(R.id.textViewTasksDone);
        percentView = findViewById(R.id.textViewPercent);

        String buttonText = getIntent().getStringExtra("button_text");
        this.heutigerButton.setText(buttonText);

        specificDay_TerminListe_RecyclerView = findViewById(R.id.terminlisteRecyclerView);
        Termin_RecyclerView_Adapter adapter;

        adapter = new Termin_RecyclerView_Adapter(this,
                getSpecificTerminlisteInCurrentWeek(currentDay()),
                progressBar, tasksDone, percentView);
        specificDay_TerminListe_RecyclerView.setAdapter(adapter);
        specificDay_TerminListe_RecyclerView
                .setLayoutManager(new LinearLayoutManager(this));

        adapter = getAdapterForCurrentDay();
        specificDay_TerminListe_RecyclerView.setAdapter(adapter);


        adapter_sort = getAdapterForCurrentDay();
        specificDay_TerminListe_RecyclerView.setAdapter(adapter_sort);
        refreshSpecificDay();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();

        if (id == R.id.backButtonSpecificDayActivity) {
            onBackPressed();
        } else if (id == R.id.addButton) {
            intent = new Intent(this, Add.class);
            startActivity(intent);
        } else if (id == R.id.filterButton) {
            showFilterPopupMenu(v);
        }
    }

    private static final int FILTER_OPTION_1_ID = R.id.filter_option_1;
    private static final int FILTER_OPTION_2_ID = R.id.filter_option_2;

    private void showFilterPopupMenu(View anchorView) {
        PopupMenu popupMenu = new PopupMenu(this, anchorView);
        popupMenu.inflate(R.menu.menu_filter);

        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == FILTER_OPTION_1_ID) {
                sortAscendingByPriority();
                return true;
            } else if (itemId == FILTER_OPTION_2_ID) {
                sortDescendingByPriority();
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    private void sortAscendingByPriority() {
        List<Termin> terminliste = getSpecificTerminlisteInCurrentWeek(currentDay());
        TerminSorter.sortAscendingByPriority(terminliste);

        for (Termin termin : terminliste) {
            Log.d("SortAscending", "Termin: " + termin.getTerminname() + " Prio: " +
                    termin.getPrio());
        }

        adapter_sort.setTerminliste((ArrayList<Termin>) terminliste);
        adapter_sort.notifyDataSetChanged();
    }

    private void sortDescendingByPriority() {
        List<Termin> terminliste = getSpecificTerminlisteInCurrentWeek(currentDay());
        TerminSorter.sortDescendingByPriority(terminliste);

        for (Termin termin : terminliste) {
            Log.d("SortDescending", "Termin: " + termin.getTerminname() + " Prio: " +
                    termin.getPrio());
        }

        adapter_sort.setTerminliste((ArrayList<Termin>) terminliste);
        adapter_sort.notifyDataSetChanged();
    }

    private Termin_RecyclerView_Adapter getAdapterForCurrentDay() {
        List<Termin> terminliste = getSpecificTerminlisteInCurrentWeek(currentDay());
        Termin_RecyclerView_Adapter adapter = new Termin_RecyclerView_Adapter(this,
                (ArrayList<Termin>) terminliste, progressBar, tasksDone, percentView);
        specificDay_TerminListe_RecyclerView
                .setLayoutManager(new LinearLayoutManager(this));
        return adapter;
    }
}