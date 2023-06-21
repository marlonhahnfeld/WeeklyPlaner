package com.example.weeklyplaner;

import static com.example.weeklyplaner.Utils.getSpecificTerminlisteInCurrentWeek;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    private ProgressBar progressBar;
    private TextView tasksDone;
    private TextView percentView;

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
        ArrayList<Termin> currentWeekTerminliste =
                getSpecificTerminlisteInCurrentWeek(dayOfWeek - 1);
        adapter = new Termin_RecyclerView_Adapter(this, currentWeekTerminliste,
                progressBar, tasksDone, percentView);
        adapter.setTerminliste(currentWeekTerminliste);
        adapter.updateProgress();
        adapter.updateTasksDone();
        adapter.updateProgressInPercent();
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
        progressBar = findViewById(R.id.progressBar);
        tasksDone = findViewById(R.id.textViewTasksDone);
        percentView = findViewById(R.id.textViewPercent);

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
        if (currentDayOfWeek == 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(true);
            builder.setTitle("Eine neue Woche hat begonnen!");
            String abgelaufeneTermine = "";

            // 15.06.2023, 16.05.2023, 15.05.2022, 30.06.2022, 30.05.2023, 30.12.2022
            /// 19.06.2023, 19.07.2023, 19.07.2024, 5.07.2023, 5.05.2024, 30.04.2024 funktionieren
            for (int i = 0; i < terminListe.length-2; i++) {
                for (int j = 0; j < terminListe[i].size(); j++) {
                    if ((!terminListe[i].get(j).isChecked()) &&
                  /* Fall 1: Tag kleiner  */  ((((terminListe[i].get(j).getActualDatum().getDayOfMonth() < (currentDate.getDayOfMonth() - (currentDate.getDayOfWeek().getValue()-1))))&& ((((terminListe[i].get(j).getActualDatum().getMonth().getValue()) <= currentDate.getMonth().getValue()) && (terminListe[i].get(j).getActualDatum().getYear() <= currentDate.getYear()))|| terminListe[i].get(j).getActualDatum().getYear() < currentDate.getYear())) ||
                 /* Fall 2: Monat kleiner */  ((terminListe[i].get(j).getActualDatum().getMonth().getValue() < currentDate.getMonth().getValue()) &&(terminListe[i].get(j).getActualDatum().getYear() <= currentDate.getYear())) ||
                  /* Fall 3: Jahr kleiner */  (terminListe[i].get(j).getActualDatum().getYear() < currentDate.getYear()))
                    ){
                        abgelaufeneTermine += terminListe[i].get(j).getTerminname()+"\n";
                        abgelaufene_terminliste.add(terminListe[i].get(j));
                        // (< getday && (<=getmonth && <=getyear || <getyear)) || (<getMonth && <=getyear) || (<getyear)
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
        // 19.06.2023, 19.07.2023, 19.07.2024, 5.07.2023, 5.05.2024, 30.04.2024
        // 19.06.2023, 19.07.2023, 19.07.2024, 5.07.2023, 5.05.2024, 30.04.2024 funktionieren jetzt
        for (int i = 0; i < terminListe.length - 2; i++) {
            for (int j = 0; j < terminListe[i].size(); j++) {
                if (    /* Fall 1: Tag größer  */  ((((terminListe[i].get(j).getActualDatum().getDayOfMonth() > (currentDate.getDayOfMonth() + (DayOfWeek.SUNDAY.getValue() - currentDate.getDayOfWeek().getValue()))))&& ((((terminListe[i].get(j).getActualDatum().getMonth().getValue()) >= currentDate.getMonth().getValue()) && (terminListe[i].get(j).getActualDatum().getYear() >= currentDate.getYear()))|| terminListe[i].get(j).getActualDatum().getYear() > currentDate.getYear())) ||
                        /* Fall 2: Monat größer */  ((terminListe[i].get(j).getActualDatum().getMonth().getValue() > currentDate.getMonth().getValue()) &&(terminListe[i].get(j).getActualDatum().getYear() >= currentDate.getYear())) ||
                        /* Fall 3: Jahr größer */  (terminListe[i].get(j).getActualDatum().getYear() > currentDate.getYear()))
                ) {
                    MainActivity.zukuenftige_terminliste.add(terminListe[i].get(j));
                    // (> getday && />= getmonth && >= getyear || >getyear)) || (>getMonth && >= getyear) || (>getyear)
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
        ArrayList<Termin> currentWeekTerminliste =
                getSpecificTerminlisteInCurrentWeek(dayOfWeek - 1);
        TerminSorter.sortAscendingByPriority(currentWeekTerminliste);
        adapter.setTerminliste(currentWeekTerminliste);
        adapter.notifyDataSetChanged();
    }

    private void sortDescendingByPriority() {
        ArrayList<Termin> currentWeekTerminliste =
                getSpecificTerminlisteInCurrentWeek(dayOfWeek - 1);
        TerminSorter.sortDescendingByPriority(currentWeekTerminliste);
        adapter.setTerminliste(currentWeekTerminliste);
        adapter.notifyDataSetChanged();
    }


}