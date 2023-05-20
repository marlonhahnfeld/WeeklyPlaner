package com.example.weeklyplaner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import items.Termin;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button button_for_days;
    private ImageButton addButton;
    private RecyclerView recyclerView;
    private static ArrayList<Termin>[] terminListe = new ArrayList[7];
    private Termin_RecyclerView_Adapter adapter;
    private Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Berlin"));
    public static ArrayList<Termin> montag_terminliste = new ArrayList<>();
    public static ArrayList<Termin> dienstag_terminliste = new ArrayList<>();
    public static ArrayList<Termin> mittwoch_terminliste = new ArrayList<>();
    public static ArrayList<Termin> donnerstag_terminliste = new ArrayList<>();
    public static ArrayList<Termin> freitag_terminliste = new ArrayList<>();
    public static ArrayList<Termin> samstag_terminliste = new ArrayList<>();
    public static ArrayList<Termin> sonntag_terminliste = new ArrayList<>();

    private ImageButton filterButton;


    // REFRESH PAGE CODE
    @Override
    protected void onResume() {
        super.onResume();
        refreshMainActivity();
    }

    private void refreshMainActivity() {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        adapter = new Termin_RecyclerView_Adapter(this, terminListe[dayOfWeek - 1]);
        recyclerView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filterButton = findViewById(R.id.SortButtonFilterForMainActivity);
        filterButton.setOnClickListener(this);

        button_for_days = findViewById(R.id.MoSoButton);
        button_for_days.setOnClickListener(this);

        addButton = findViewById(R.id.AddButton);
        addButton.setOnClickListener(this);
        recyclerView = findViewById(R.id.TerminlisteRecyclerView);

        terminListe[0] = sonntag_terminliste;
        terminListe[1] = montag_terminliste;
        terminListe[2] = dienstag_terminliste;
        terminListe[3] = mittwoch_terminliste;
        terminListe[4] = donnerstag_terminliste;
        terminListe[5] = freitag_terminliste;
        terminListe[6] = samstag_terminliste;

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshMainActivity();
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();

        if (id == R.id.MoSoButton) {
            intent = new Intent(this, Week.class);
            startActivity(intent);
        } else if  (id == R.id.AddButton) {
            intent = new Intent(this, Add.class);
            startActivity(intent);
        } else if (id == R.id.SortButtonFilterForMainActivity) {
            showFilterPopupMenu(v);

        }
    }

    private static final int FILTER_OPTION_1_ID = R.id.filter_option_1;
    private static final int FILTER_OPTION_2_ID = R.id.filter_option_2;

    private void showFilterPopupMenu(View anchorView) {
        PopupMenu popupMenu = new PopupMenu(this, anchorView);
        popupMenu.inflate(R.menu.menu_filter);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == FILTER_OPTION_1_ID) {
                    sortAscendingByPriority();
                    return true;
                } else if (itemId == FILTER_OPTION_2_ID) {
                    sortDescendingByPriority();
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void sortAscendingByPriority() {
        ArrayList<Termin> terminliste = (ArrayList<Termin>) getCurrentDayTerminList();
        TerminSorter.sortAscendingByPriority(terminliste);

        for (Termin termin : terminliste) {
            Log.d("SortAscending", "Termin: " + termin.getTerminname() + " Prio: " + termin.getPrio());
        }

        adapter.setTerminliste(terminliste);
        adapter.notifyDataSetChanged();
    }

    private void sortDescendingByPriority() {
        ArrayList<Termin> terminliste = (ArrayList<Termin>) getCurrentDayTerminList();
        TerminSorter.sortDescendingByPriority(terminliste);

        for (Termin termin : terminliste) {
            Log.d("SortDescending", "Termin: " + termin.getTerminname() + " Prio: " + termin.getPrio());
        }

        adapter.setTerminliste(terminliste);
        adapter.notifyDataSetChanged();
    }
    private List<Termin> getCurrentDayTerminList() {
        List<Termin> terminliste = new ArrayList<>();

        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            terminliste = montag_terminliste;
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            terminliste = dienstag_terminliste;
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            terminliste = mittwoch_terminliste;
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            terminliste = donnerstag_terminliste;
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            terminliste = freitag_terminliste;
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            terminliste = samstag_terminliste;
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            terminliste = sonntag_terminliste;
        }

        return terminliste;
    }


}