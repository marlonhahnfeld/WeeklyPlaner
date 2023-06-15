package com.example.weeklyplaner;

import static com.example.weeklyplaner.Utils.getSpecificTerminliste;
import static com.example.weeklyplaner.Utils.getSpecificTerminlisteInCurrentWeek;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import items.Termin;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button_for_days;
    private ImageButton addButton;
    private RecyclerView recyclerView;
    protected static ArrayList<Termin>[] terminListe = new ArrayList[9];
    private Termin_RecyclerView_Adapter adapter;
    private Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Berlin"));
    public static ArrayList<Termin> montag_terminliste = new ArrayList<>();
    public static ArrayList<Termin> dienstag_terminliste = new ArrayList<>();
    public static ArrayList<Termin> mittwoch_terminliste = new ArrayList<>();
    public static ArrayList<Termin> donnerstag_terminliste = new ArrayList<>();
    public static ArrayList<Termin> freitag_terminliste = new ArrayList<>();
    public static ArrayList<Termin> samstag_terminliste = new ArrayList<>();
    public static ArrayList<Termin> sonntag_terminliste = new ArrayList<>();
    public static ArrayList<Termin> abgelaufene_terminliste = new ArrayList<>();
    public static ArrayList<Termin> zukuenftige_terminliste = new ArrayList<>();
    protected int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

    public static LocalDate currentDate = LocalDate.now();
    private ImageButton filterButton;

    // REFRESH PAGE CODE
    @Override
    protected void onResume() {
        super.onResume();
        refreshMainActivity();
    }

    private void refreshMainActivity() {
        ArrayList<Termin> currentWeekTerminliste = getSpecificTerminlisteInCurrentWeek(dayOfWeek - 1);
        adapter = new Termin_RecyclerView_Adapter(this, currentWeekTerminliste);
        adapter.setTerminliste(currentWeekTerminliste);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filterButton = findViewById(R.id.sortButtonFilterMainActivity);
        filterButton.setOnClickListener(this);

        button_for_days = findViewById(R.id.buttonMoSo);
        button_for_days.setOnClickListener(this);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(this);
        recyclerView = findViewById(R.id.terminlisteRecyclerView);

        terminListe[0] = sonntag_terminliste;
        terminListe[1] = montag_terminliste;
        terminListe[2] = dienstag_terminliste;
        terminListe[3] = mittwoch_terminliste;
        terminListe[4] = donnerstag_terminliste;
        terminListe[5] = freitag_terminliste;
        terminListe[6] = samstag_terminliste;
        terminListe[7] = abgelaufene_terminliste;
        terminListe[8] = zukuenftige_terminliste;

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshMainActivity();

        // Abgelaufene Termine TODO
        // TODO alertTextView für neuer Wochenstart wie viele Termine wurden verpasst
        // TODO augenmerkliche Kennzeichnung für Week-Screen zur independent List die alle abgelaufenen beinhaltet
        // TODO abgelaufene Termine letzter Woche kommen in die independent List + alert? -> nach neuen Wochenstart (refreshOnMonday-Methode)
        // TODO alle abgelaufene Termine auf einmal abhaken können bzw die gesamte List abhaken

        int currentDayOfWeek = currentDate.getDayOfWeek().getValue();
        if (currentDayOfWeek == 4) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(true);
            builder.setTitle("Eine neue Woche hat begonnen!");
            String abgelaufeneTermine = "";

            for (int i = 0; i < terminListe.length-3; i++) {
                for (int j = 0; j < terminListe[i].size()-1; j++) {
                    if ((!terminListe[i].get(j).isChecked()) && (terminListe[i].get(j).getActualDatum().getDayOfMonth() <
                            (currentDate.getDayOfMonth() - (currentDate.getDayOfWeek().getValue()-1)))){
                        abgelaufeneTermine += terminListe[i].get(j).getTerminname()+"\n";
                        abgelaufene_terminliste.add(terminListe[i].get(j));
                    }
                }

            }
            builder.setMessage("du hast folgende Termine aus der letzten Woche verpasst: \n"+
                    abgelaufeneTermine);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i){
                    dialogInterface.cancel();
                }
            });
            if (!(abgelaufene_terminliste.size() == 0)){
                builder.show();}
        }
        // ------------------------------------------------------------------------------------
        // Zukunftsplanung TODO
        // TODO zukünftige Termine anzeigen -> Independent List für alle vorausliegenden Termine wie für abgelaufene List erstellen
        // TODO intelligente Positionierung die nicht heraussticht aber klar erkannt wird falls man fehlerhaften Termin hat
        //  und bearbeiten möchte (automatisch nach Prio sortiert?)
        ArrayList<Termin>[] terminListe = MainActivity.terminListe;
        LocalDate currentDate = LocalDate.now();
        for (int i = 0; i < terminListe.length - 3; i++) {
            for (int j = 0; j < terminListe[i].size() - 1; j++) {
                if ((terminListe[i].get(j).getActualDatum().getDayOfMonth() >
                        (currentDate.getDayOfMonth() + (DayOfWeek.SUNDAY.getValue() - currentDate.getDayOfWeek().getValue())))) {
                    MainActivity.zukuenftige_terminliste.add(terminListe[i].get(j));
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();

        if (id == R.id.buttonMoSo) {
            intent = new Intent(this, Week.class);
            startActivity(intent);
        } else if (id == R.id.addButton) {
            intent = new Intent(this, Add.class);
            startActivity(intent);
        } else if (id == R.id.sortButtonFilterMainActivity) {
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
        ArrayList<Termin> currentWeekTerminliste = getSpecificTerminlisteInCurrentWeek(dayOfWeek - 1);
        TerminSorter.sortAscendingByPriority(currentWeekTerminliste);
        adapter.setTerminliste(currentWeekTerminliste);
        adapter.notifyDataSetChanged();
    }

    private void sortDescendingByPriority() {
        ArrayList<Termin> currentWeekTerminliste = getSpecificTerminlisteInCurrentWeek(dayOfWeek - 1);
        TerminSorter.sortDescendingByPriority(currentWeekTerminliste);
        adapter.setTerminliste(currentWeekTerminliste);
        adapter.notifyDataSetChanged();
    }


}